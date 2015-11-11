package Inventory;
import UserInteraction.*; 

/*********************************************************************
Defines generic oxygen mask item layout.
@author Dale Burke
@version November 2015
*********************************************************************/
public class Oxygen extends KeyItem {
	
	/*********************************************************************
	Default constructor method for Oxygen class. Creates a blank Oxygen
	object that cannot be discarded.
	
	@param none
	@return none
	*********************************************************************/
	public Oxygen()
	{
		super("", "", true, false);
	}

	/*********************************************************************
	Constructor method for Oxygen class. Creates a Oxygen object with the 
	provided attributes.
	
	@param String itemName - The item display name.
	@param String itemDescription - The item display description.
	@param boolean isKeyItem - Whether or not the item may be removed.
		   false for can discard, true for cannot discard.
	@param boolean isUsed - Whether or not the item has been used. false
		   for is not used, true for is used.
	@return none
	*********************************************************************/
	public Oxygen(String itemName, String itemDescription, 
			boolean isKeyItem, boolean isUsed)
	{
		super(itemName, itemDescription, isKeyItem, isUsed);
	}

	/*********************************************************************
	Defines the action to be take on Oxygen use. Checks to see if player
	has equipped the suit item.
	@param none
	@return none
	*********************************************************************/
	public void useItem(Player p)
	{
		if(isUsed)
		{
			System.out.println("The oxygen mask is already equipped.");
		}
		else
		{
			p.getPlayerGame().setOxygenFlag(true);
			System.out.println("You equip the oxygen mask.");
		}
	}
}