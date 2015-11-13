package Inventory; 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import UserInteraction.*;

/*********************************************************************
Defines generic inventory layout. Allows management and storage of all
player items.

@author Dale Burke
@version November 2015
 *********************************************************************/
public class Inventory {

	/** list to store items */
	private List<Item> list = new ArrayList<Item>();

	/** owner of the inventory */
	private Player owner = null;

	/** owner's equipped weapon */
	private Weapon currentWeapon = null;

	/** owner's equipped accessory */
	private Accessory currentAccessory = null;
	
	/** maximum inventory capacity */
	private int maxItems = 10;

	/** scanner used to receive user input */
	private Scanner scanner = new Scanner(System.in);

	/*********************************************************************
	Default constructor method. Creates a blank Inventory object.

	@param none
	@return none
	 *********************************************************************/
	public Inventory()
	{
		// null
	}

	/*********************************************************************
	Constructor method for Inventory class. Creates an Inventory object 
	with the provided attributes.

	@param List list - List to store all inventory items.
	@param Player owner - Owner of the inventory.
	@param Weapon currentWeapon - Owner's currently equipped weapon.
	@param Accessory currentAccessory - Owner's currently equipped
		   accessory.
	@param int maxItems - Maximum capacity for inventory.
	@return none
	 *********************************************************************/
	public Inventory(List<Item> list, Player owner, Weapon currentWeapon, 
			Accessory currentAccessory, int maxItems)
	{
		this.list = list;
		this.owner = owner;
		this.currentWeapon = currentWeapon;
		this.currentAccessory = currentAccessory;
		this.maxItems = maxItems;
	}

	/*********************************************************************
	Method for item selection and use. User inputs a numeric value
	corresponding to an item to be used. Uses and removes selected item
	when appropriate.

	@param none
	@return boolean - False if no item has been used, true if item used.
	 *********************************************************************/
	public boolean useItem()
	{
		String input = "";
		int number = 0;
		boolean numberFlag = false;
		Weapon tempWeapon = null;
		Accessory tempAccessory = null;

		// check to see if player owns any items
		if(list.size() > 0)
		{
			// displays the item list
			Collections.sort(list);
			System.out.print(displayItemList());
			System.out.print("Which item would you like to use?\n> ");
			input = scanner.next();

			// attempts to parse an appropriate integer from user input
			do
			{
				try
				{
					number = Integer.parseInt(input);
					numberFlag = true;
				}

				// attempts to get an integer value again
				catch(NumberFormatException e)
				{
					System.out.println("Integer value not detected.");
					System.out.print("Please enter an integer value (0 to cancel).\n> ");
					input = scanner.next();
					numberFlag = false;
				}

				if(numberFlag)
				{
					// make the number align with indexes
					number --;

					// if number checks out, see if item use was cancelled
					if(number == -1)
					{
						System.out.println("You decide not to use an item.");
						return false;
					}

					// if it is a key item or number is outside of bounds
					else if(number < -1 || number >= list.size())
					{
						System.out.println("Value out of bounds.");

						// ask for new input
						System.out.print("Please select a valid number (0 to cancel).\n> ");
						input = scanner.next();
						numberFlag = false;
					}

					// if trying to use a key item in battle
					else if(list.get(number).getItemType().equalsIgnoreCase("Key Item") && 
							owner.getPlayerGame().isBattleFlag())	
					{
						// ask for new input
						System.out.println("You cannot use that item in battle.");
						Collections.sort(list);
						System.out.print(displayItems());
						System.out.print("Please select a different item (0 to cancel).\n> ");
						input = scanner.next();
						numberFlag = false;
					}
					else
					{
						if(list.get(number).getItemType().equalsIgnoreCase("Weapon"))
						{
							// use the weapon
							list.get(number).useItem(owner);
							tempWeapon = currentWeapon;

							// if there is a weapon equipped, swap it out
							if(currentWeapon != null)
							{
								owner.setPlayerAttack(owner.getPlayerAttack() - currentWeapon.getWeaponAttackBonus());
								currentWeapon = (Weapon)list.get(number);
								list.remove(number);
								list.add(tempWeapon);
							}

							// or just remove it if not
							else
							{
								currentWeapon = (Weapon)list.get(number);
								list.remove(number);
							}
						}
						else if(list.get(number).getItemType().equalsIgnoreCase("Accessory"))
						{
							// use the accessory
							list.get(number).useItem(owner);
							tempAccessory = currentAccessory;

							// if there is an accessory equipped, swap it out
							if(currentAccessory != null)
							{
								owner.setPlayerAttack(owner.getPlayerAttack() - currentAccessory.getAccessoryAttackIncrease());
								owner.setPlayerDodge(owner.getPlayerDodge() - currentAccessory.getAccessoryDodgeIncrease());
								owner.setPlayerMaxHP(owner.getPlayerMaxHP() - currentAccessory.getAccessoryHPIncrease());
								currentAccessory = (Accessory)list.get(number);
								list.remove(number);
								list.add(tempAccessory);
							}

							// or just remove it if not
							else
							{
								currentAccessory = (Accessory)list.get(number);
								list.remove(number);
							}
						}

						// consumables are removed on use
						else if(list.get(number).getItemType().equalsIgnoreCase("Consumable"))
						{
							list.get(number).useItem(owner);
							list.remove(number);
						}

						// key items are not removed on use
						else if(list.get(number).getItemType().equalsIgnoreCase("Key Item"))
						{
							list.get(number).useItem(owner);
						}
					}
				}
			}
			while(!numberFlag);
			return true;
		}
		else
		{
			System.out.print(displayItemList());
			return false;
		}
	}

	/*********************************************************************
	Private helper method for use with useItem method. Generates a list of
	player statistics, equipped items, and all items in inventory.

	@param none
	@return String - Comprehensive inventory list with player statistics.
	 *********************************************************************/
	private String displayItemList()
	{
		String weapon = "Current Weapon: ";
		String accessory = "Current Accessory: ";
		String itemList = "Inventory:\n" + displayItems();
		String playerStats = "";
		
		// determines player statistics
		if(owner != null)
		{
			if(owner.getPlayerName()!= null && owner.getPlayerName() != "")
			{
				playerStats += owner.getPlayerName() + "'s Statistics:\n";
			}
			else
			{
				playerStats += "Nobody's Statistics:\n";
			}
			playerStats += "\tHit Points: " + owner.getPlayerCurrentHP() + "/" +
					owner.getPlayerMaxHP() + "\n\tAttack: " + owner.getPlayerAttack() +
					"\n\tDodge: " + owner.getPlayerDodge() + "\n";
		}

		// determines if weapon and accessory are equipped
		if(currentWeapon == null)
		{
			weapon += "None\n";
		}
		else
		{
			weapon += "[" + currentWeapon.getItemName() + "] (ATTACK " + currentWeapon.getWeaponAttackBonus() + ")\n";
		}
		if(currentAccessory == null)
		{
			accessory += "None\n";
		}
		else
		{
			accessory += "[" + currentAccessory.getItemName() + "] (ATTACK " + 
					currentAccessory.getAccessoryAttackIncrease() +
					" / MAX HP " + currentAccessory.getAccessoryHPIncrease() + 
					" / DODGE " + currentAccessory.getAccessoryDodgeIncrease() + ")\n";
		}
		return playerStats + weapon + accessory + itemList;
	}
	
	/*********************************************************************
	Private helper method for use with displayItemList. Generates a single 
	list of all items in inventory.

	@param none
	@return String - List of items with item numbers.
	 *********************************************************************/
	private String displayItems()
	{
		String itemList = "";

		// fills out the itemList
		if(list != null)
		{
			if(list.size() == 0)
			{
				itemList = "\tYou do not currently possess any usable items.\n";
			}
			else
			{
				for(int i = 0; i < list.size(); i++)
				{
					// construct the item profile
					itemList += "\t" + (i + 1) + ": [";

					if(list.get(i).getItemName() != null)
					{
						itemList += list.get(i).getItemName();
					}
					else 
					{
						itemList += "NO NAME";
					}
					itemList += "] - " + list.get(i).getItemType();

					// assign parameters if appropriate
					if(list.get(i).getItemType().equalsIgnoreCase("Weapon"))
					{
						itemList += " (ATTACK " + ((Weapon)list.get(i)).getWeaponAttackBonus() + ")";
					}
					else if(list.get(i).getItemType().equalsIgnoreCase("Accessory"))
					{
						itemList += " (ATTACK " + ((Accessory)list.get(i)).getAccessoryAttackIncrease() +
								" / MAX HP " + ((Accessory)list.get(i)).getAccessoryHPIncrease() + " / DODGE " + 
								((Accessory)list.get(i)).getAccessoryDodgeIncrease() + ")";
					}
					else if(list.get(i).getItemType().equalsIgnoreCase("Consumable"))
					{
						itemList += " (HEALS " + ((Consumable) list.get(i)).getConsumableHPRecovery() + " HP)";
					}
					itemList += " - ";

					if(list.get(i).getItemDescription() != null)
					{
						itemList += list.get(i).getItemDescription();
					}
					else
					{
						itemList += "NO DESCRIPTION";
					}
					itemList += "\n";
				}
			}
		}
		return itemList;
	}

	/*********************************************************************
	Method for adding an item to inventory. Checks to ensure provided item
	may be acquired (does not exceed inventory maximum), and provides
	option for item replacement in the aforementioned case.

	@param Item item - The item to be acquired.
	@return none
	 *********************************************************************/
	public void addToInventory(Item item)
	{
		String input = "";
		String replacedItem = "a nameless ";
		String newItem = "a nameless ";
		int number = 0;
		boolean numberFlag = false;
		
		// set new item name string		
		if(item.getItemName() != null)
		{
			newItem = "[" + item.getItemName() + "]";
		}
		else
		{
			newItem += item.getItemType().toLowerCase();
		}

		// general check to see if the list is valid
		if(list != null)
		{
			// acquisition message
				if(item.getItemName() != null)
				{
					System.out.println("You acquire the " + item.getItemType().toLowerCase() + " " + newItem + ".");
				}
				else
				{
					System.out.println("You acquire " + newItem + ".");
				}
			
			// if the item is a key item, allocate space
			if(item.getIsKeyItem())
			{
				maxItems ++;
				list.add(item);
			}
			// if inventory space is available, add the new item
			else if(list.size() < maxItems)
			{
				list.add(item);		
			}
			else
			{
				System.out.println("Your inventory is full.");
				// get user input for item to discard
				Collections.sort(list);
				System.out.print(displayItems());
				System.out.print("Please select an item to discard (0 to refuse " + newItem + ").\n> ");
				input = scanner.next();

				// attempts to parse an appropriate integer from user input
				do
				{
					try
					{
						number = Integer.parseInt(input);
						numberFlag = true;
					}

					// attempts to get an integer value again
					catch(NumberFormatException e)
					{
						System.out.println("Integer value not detected.");
						Collections.sort(list);
						System.out.print(displayItems());
						System.out.print("Please select an item to discard (0 to refuse " + newItem + ").\n> ");
						input = scanner.next();
						numberFlag = false;
					}

					if(numberFlag)
					{
						// make the number align with indexes
						number --;

						// if number checks out, see if it is abandoned
						if(number == -1)
						{
							System.out.println("You abandon the new item.");
						}

						// if it is a key item or number is outside of bounds
						else if(number < -1 || number >= list.size())
						{
							System.out.println("Value out of bounds.");

							// ask for new input
							Collections.sort(list);
							System.out.print(displayItems());
							System.out.print("Please select an item to discard (0 to refuse " + newItem + ").\n> ");
							input = scanner.next();
							numberFlag = false;
						}

						// if trying to replace a key item
						else if(list.get(number).getIsKeyItem())	
						{
							// ask for new input
							System.out.println("You cannot discard a key item.");
							Collections.sort(list);
							System.out.print(displayItems());
							System.out.print("Please select an item to discard (0 to refuse " + newItem + ").\n> ");
							input = scanner.next();
							numberFlag = false;
						}
						
						// replace an item with the new item
						else
						{
							// set replaced item name string
							if(list.get(number).getItemName() != null)
							{
								replacedItem = "[" + list.get(number).getItemName() + "]";
							}
							else
							{
								replacedItem += list.get(number).getItemType().toLowerCase();
							}
							
							// output replace message
							System.out.println("You replace " + replacedItem + " with " + newItem + ".");
							
							// remove and replace the specified item with new item
							list.remove(number);
							list.add(item);
						}
					}
				}
				while(!numberFlag);
			}
		}
	}

	/*********************************************************************
	Method for getting the player that owns this instance of Inventory.

	@param none
	@return Player - The owner of this Inventory instance.
	 *********************************************************************/
	public Player getOwner() {
		return owner;
	}

	/*********************************************************************
	Method for setting the player that owns this instance of Inventory.

	@param Player owner - The player that owns this instance of Inventory.
	@return none
	 *********************************************************************/
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/*********************************************************************
	Method for getting the maximum item capacity of this Inventory.

	@param none
	@return Player - The maximum amount of items in this Inventory.
	 *********************************************************************/
	public int getMaxItems() {
		return maxItems;
	}

	/*********************************************************************
	Method for setting the maximum item capacity of this Inventory.

	@param int maxItems - The maximum amount of items in this Inventory.
	@return none
	 *********************************************************************/
	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}
}