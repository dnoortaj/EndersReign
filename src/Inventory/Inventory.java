package Inventory; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.text.WordUtils;

import UserInteraction.*;

/*********************************************************************
Defines generic inventory layout. Allows management and storage of all
player items.

@author Dale Burke
@version November 2015
 *********************************************************************/
@SuppressWarnings("serial")
public class Inventory implements Serializable 
{

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
	
	/** scanner for use with Inventory class */
	transient private Scanner scanner;

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
		setMaxItems(maxItems);
	}

	/*********************************************************************
	Method for item selection and use. User inputs a numeric value
	corresponding to an item to be used. Uses and removes selected item
	when appropriate.

	@param none
	@return boolean - False if no item has been used, true if item used.
	 *********************************************************************/
	public Item useItem()
	{
		scanner = new Scanner(System.in);
		String input = "";
		Item item = null;
		int number = 0;
		boolean numberFlag = false;
		Weapon tempWeapon = null;
		Accessory tempAccessory = null;

		// general check to see if the list is valid
		if(list != null)
		{
			// check to see if player owns any items
			if(list.size() > 0)
			{
				// displays the item list
				Collections.sort(list);
				System.out.print(displayItemList());
				System.out.print("Which item would you like to use? (0 to cancel)\n> ");
				input = scanner.nextLine();

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
						System.out.print("Please enter an integer value. (0 to cancel)\n> ");
						input = scanner.nextLine();
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
							return item;
						}

						// if it is a key item or number is outside of bounds
						else if(number < -1 || number >= list.size())
						{
							System.out.println("Value out of bounds.");

							// ask for new input
							System.out.print("Please select a valid number. (0 to cancel)\n> ");
							input = scanner.nextLine();
							numberFlag = false;
						}

						// if trying to use a key item in battle
						else if(list.get(number).getItemType().equalsIgnoreCase("Key Item") && 
								owner.isBattleFlag())	
						{
							// ask for new input
							System.out.println("You cannot use that item in battle.");
							System.out.print("Please select a different item. (0 to cancel)\n> ");
							input = scanner.nextLine();
							numberFlag = false;
						}
						else
						{
							item = list.get(number);
							if(list.get(number).getItemType().equalsIgnoreCase("Weapon"))
							{
								// use the weapon
								list.get(number).useItem(owner);
								tempWeapon = currentWeapon;

								// if there is a weapon equipped, swap it out
								if(currentWeapon != null)
								{
									owner.setPlayerAttack(owner.getPlayerAttack() - 
											currentWeapon.getWeaponAttackBonus());
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
									owner.setPlayerAttack(owner.getPlayerAttack() - 
											currentAccessory.getAccessoryAttackIncrease());
									owner.setPlayerDodge(owner.getPlayerDodge() - 
											currentAccessory.getAccessoryDodgeIncrease());
									owner.setPlayerMaxHP(owner.getPlayerMaxHP() - 
											currentAccessory.getAccessoryHPIncrease());
									if(owner.getPlayerCurrentHP() > owner.getPlayerMaxHP())
									{
										owner.setPlayerCurrentHP(owner.getPlayerMaxHP());
									}
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

								// if key item, adjust max item capacity
								if(list.get(number).getIsKeyItem())
								{
									maxItems --;
								}
								list.remove(number);
							}

							// key item type is not removed on use
							else if(list.get(number).getItemType().equalsIgnoreCase("Key Item"))
							{
								list.get(number).useItem(owner);
							}
						}
					}
				}
				while(!numberFlag);
				return item;
			}
			else
			{
				System.out.print(displayItemList());
				return item;
			}
		}
		else
		{
			System.out.println("INVENTORY LIST NOT FOUND");
			return item;
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
			playerStats += "   Hit Points: " + owner.getPlayerCurrentHP() + "/" +
					owner.getPlayerMaxHP() + "\n   Attack: " + owner.getPlayerAttack() +
					"\n   Dodge: " + owner.getPlayerDodge() + "\n";
		}

		// determines if weapon and accessory are equipped
		if(currentWeapon == null)
		{
			weapon += "None\n";
		}
		else
		{
			weapon += "[" + currentWeapon.getItemName() + "] (Attack " + 
					currentWeapon.getWeaponAttackBonus() + ")\n";
		}
		if(currentAccessory == null)
		{
			accessory += "None\n";
		}
		else
		{
			accessory += "[" + currentAccessory.getItemName() + "] (Attack " + 
					currentAccessory.getAccessoryAttackIncrease() +
					" / Max HP " + currentAccessory.getAccessoryHPIncrease() + 
					" / Dodge " + currentAccessory.getAccessoryDodgeIncrease() + ")\n";
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
				itemList = "   You do not currently possess any usable items.\n";
			}
			else
			{
				for(int i = 0; i < list.size(); i++)
				{
					// construct the item profile
					itemList += "   " + (i + 1) + ":";
					
					// add a space if necessary
					if((i + 1) < 10)
					{
						itemList += " ";
					}
					
					itemList += " [";

					if(list.get(i).getItemName() != null)
					{
						itemList += list.get(i).getItemName();
						itemList += "] ";
						
						// standardize positions
						for(int t = 0; t < (30 - list.get(i).getItemName().length()); t++)
						{
							itemList += "-";
						}
						itemList += " ";
					}
					else 
					{
						itemList += "NO NAME] --------------------- ";
					}
					
					itemList += list.get(i).getItemType().toUpperCase();
					
					// assign parameters if appropriate
					if(list.get(i).getItemType().equalsIgnoreCase("Weapon"))
					{
						itemList += " ----------- (Attack " + ((Weapon)list.get(i)).getWeaponAttackBonus() + ")";
					}
					else if(list.get(i).getItemType().equalsIgnoreCase("Accessory"))
					{
						itemList += " -------- (Attack " + ((Accessory)list.get(i)).getAccessoryAttackIncrease() +
								" / Max HP " + ((Accessory)list.get(i)).getAccessoryHPIncrease() + " / Dodge " + 
								((Accessory)list.get(i)).getAccessoryDodgeIncrease() + ")";
					}
					else if(list.get(i).getItemType().equalsIgnoreCase("Consumable"))
					{
						itemList += " ------- (Heals " + ((Consumable) list.get(i)).getConsumableHPRecovery() + " HP)";
					}
					else if(list.get(i).getItemType().equalsIgnoreCase("Key Item"))
					{
						itemList += " --------- (Special Function)";
					}
					itemList += "\n\t";

					if(list.get(i).getItemDescription() != null)
					{
						itemList += " \"" + WordUtils.wrap(list.get(i).getItemDescription(), 50, "\n\t  ", true) + "\"\n";
					}
					else
					{
						itemList += " \"NO DESCRIPTION\"\n";
					}
//					if(i != list.size() - 1)
//					{
//						itemList += "\n\n";
//					}
//					else
//					{
//						itemList += "\n";
//					}
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
		scanner = new Scanner(System.in);
		String input = "";
		String replacedItem = "a nameless";
		String newItem = "a nameless";
		int number = 0;
		boolean numberFlag = false;

		// general check to see if the item is valid 
		if(item != null)
		{
			// set new item name string		
			if(item.getItemName() != null)
			{
				newItem = "[" + item.getItemName() + "]";
			}
			else
			{
				newItem += " " + item.getItemType().toLowerCase();
			}

			// general check to see if the list is valid
			if(list != null)
			{
				// acquisition message
				if(item.getItemName() != null)
				{
					System.out.println(wrapIt("You acquire the " + item.getItemType().toLowerCase() + 
							" " + newItem + "."));
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
					System.out.print(wrapIt("Please select an item to discard. (0 to refuse " + 
							newItem) + ")\n> ");
					input = scanner.nextLine();

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
							System.out.print(wrapIt("Please select an item to discard. (0 to refuse " + 
									newItem) + ")\n> ");
							input = scanner.nextLine();
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
								System.out.print(wrapIt("Please select an item to discard. (0 to refuse " + 
										newItem) + ")\n> ");
								input = scanner.nextLine();
								numberFlag = false;
							}

							// if trying to replace a key item
							else if(list.get(number).getIsKeyItem())	
							{
								// ask for new input
								System.out.println("You cannot discard a key item.");
								System.out.print(wrapIt("Please select an item to discard. (0 to refuse " + 
										newItem) + ")\n> ");
								input = scanner.nextLine();
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
								System.out.println(wrapIt("You replace " + replacedItem + " with " + newItem + "."));

								// remove and replace the specified item with new item
								list.remove(number);
								list.add(item);
							}
						}
					}
					while(!numberFlag);
				}
			}
			else
			{
				System.out.println("INVENTORY LIST NOT FOUND");
			}
		}
		else
		{
			System.out.println("CANNOT ADD NULL TO LIST");
		}
	}

	/*********************************************************************
	Method for getting the player that owns this instance of Inventory.

	@param none
	@return Player - The owner of this Inventory instance.
	 *********************************************************************/
	public Player getOwner() 
	{
		return owner;
	}

	/*********************************************************************
	Method for setting the player that owns this instance of Inventory.

	@param Player owner - The player that owns this instance of Inventory.
	@return none
	 *********************************************************************/
	public void setOwner(Player owner) 
	{
		this.owner = owner;
	}

	/*********************************************************************
	Method for getting the maximum item capacity of this Inventory.

	@param none
	@return int - The maximum amount of items in this Inventory.
	 *********************************************************************/
	public int getMaxItems() 
	{
		return maxItems;
	}

	/*********************************************************************
	Method for setting the maximum item capacity of this Inventory.

	@param int maxItems - The maximum amount of items in this Inventory.
	@return none
	 *********************************************************************/
	public void setMaxItems(int maxItems) 
	{
		// cannot set below zero
		if(maxItems > -1)
		{
			this.maxItems = maxItems;
		}
		else
		{
			this.maxItems = 0;
		}
	}

	/*********************************************************************
	Method for getting the owner's currently equipped accessory.

	@param none
	@return Item - The owner's currently equipped accessory.
	 *********************************************************************/
	public Item getCurrentAccessory() 
	{
		return currentAccessory;
	}

	/*********************************************************************
	Method for getting the owner's currently equipped weapon.

	@param none
	@return Item - The owner's currently equipped weapon.
	 *********************************************************************/
	public Item getCurrentWeapon() 
	{
		return currentWeapon;
	}

	/*********************************************************************
	Method for setting the owner's currently equipped accessory.

	@param Accessory currentAccessory - The owner's currently equipped 
		   accessory.
	@return none
	 *********************************************************************/
	public void setCurrentAccessory(Accessory currentAccessory) 
	{
		this.currentAccessory = currentAccessory;
	}

	/*********************************************************************
	Method for setting the owner's currently equipped weapon.

	@param Weapon currentWeapon - The owner's currently equipped weapon.
	@return none
	 *********************************************************************/
	public void setCurrentWeapon(Weapon currentWeapon) 
	{
		this.currentWeapon = currentWeapon;
	}

	/*********************************************************************
	Method for getting the inventory list.

	@param none
	@return ArrayList<Item> - The current inventory list.
	 *********************************************************************/
	public List<Item> getInventoryList() 
	{
		return list;
	}

	/*********************************************************************
	Method for setting the inventory list.

	@param ArrayList<Item> list - The inventory list to be used.
	@return none
	 *********************************************************************/
	public void setInventoryList(List<Item> list) 
	{
		this.list = list;
	}
	
	/*********************************************************************
	Private helper method for wrapping designated Strings at 80 
	characters. Uses the wrap function from apache.

	@param String string - String to wrap.
	@return String - The string with new lines at 80 characters.
	 *********************************************************************/
	private String wrapIt(String string)
	{
		return WordUtils.wrap(string, 100, "\n", true);
	}
}