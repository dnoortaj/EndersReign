package Inventory;
import UserInteraction.*;
import Obstacle.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner; 

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
	@return none
	 *********************************************************************/
	public Inventory(List<Item> list, Player owner, Weapon currentWeapon, Accessory currentAccessory)
	{
		this.list = list;
		this.owner = owner;
		this.currentWeapon = currentWeapon;
		this.currentAccessory = currentAccessory;
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
			System.out.println(displayItemList());
			System.out.println("Which item would you like to use?");
			input = scanner.next();

			// attempts to parse an integer from user input
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
					System.out.println("Please enter an integer value (0 to cancel).");
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
						System.out.println("Please select a valid number.");
						input = scanner.next();
						numberFlag = false;
					}

					// if trying to use a key item in battle
					else if(list.get(number).getIsKeyItem() && owner.getPlayerGame().isBattleFlag())	
					{
						// ask for new input
						System.out.println("You cannot use that item in battle.");
						System.out.println("Please select a different item.");
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
			System.out.println("\nYou do not own any items.");
			return false;
		}
	}

	/*********************************************************************
	Private helper method for use with useItem method. Generates a single 
	list of all items in inventory.

	@param none
	@return String - Comprehensive list of items with item numbers.
	*********************************************************************/
	private String displayItemList()
	{
		String weapon = "";
		String accessory = "";
		String itemList = "";
		
		// determines if weapon and accessory are equipped
		if(currentWeapon == null)
		{
			weapon += "None";
		}
		else
		{
			weapon += currentWeapon.getItemName();
		}
		if(currentAccessory == null)
		{
			accessory += "None";
		}
		else
		{
			accessory += currentAccessory.getItemName();
		}

		// fills out the itemList
		if(list != null)
		{
			for(int i = 0; i < list.size(); i++)
			{
				itemList += (i + 1) + ": " + list.get(i).getItemName() +"\n";
			}
		}
		return "\nCurrent Weapon: " + weapon + "\nCurrent Accessory: " + accessory +
				"\nInventory:\n" + itemList;
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
		int number = 0;
		boolean numberFlag = false;
		
		// general check to see if the list is valid
		if(list != null)
		{
			// if there are 14 or fewer items, add the new item
			if(list.size() < 15)
			{
				list.add(item);
			}
			else
			{
				// get user input for item to discard
				System.out.println("Please select an item to discard.");
				input = scanner.next();
				
				// attempts to parse an integer from user input
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
						System.out.println("Please select an item to discard.");
						input = scanner.next();
						numberFlag = false;
					}

					if(numberFlag)
					{
						// make the number align with indexes
						number --;
						
						// if number checks out, see if it is abandoned
						if(!item.getIsKeyItem() && number == -1)
						{
							System.out.println("You abandon the new item.");
						}
						
						// if it is a key item or number is outside of bounds
						else if(number < 0 || number >= list.size())
						{
							if(number == -1)
							{
								System.out.println("You cannot refuse a key item.");
							}
							else
							{
								System.out.println("Value out of bounds.");
							}
							
							// ask for new input
							System.out.println("Please select an item to discard.");
							input = scanner.next();
							numberFlag = false;
						}
						
						// if trying to replace a key item
						else if(list.get(number).getIsKeyItem())	
						{
							// ask for new input
							System.out.println("You cannot discard a key item.");
							System.out.println("Please select an item to discard.");
							input = scanner.next();
							numberFlag = false;
						}
						else
						{
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
}