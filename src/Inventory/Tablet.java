package Inventory;
import UserInteraction.*; 

/*********************************************************************
Defines generic tablet item layout.
@author Dale Burke
@version November 2015
*********************************************************************/
public class Tablet extends KeyItem {
	
	/*********************************************************************
	Default constructor method for Tablet class. Creates a blank Tablet
	object that cannot be discarded.
	
	@param none
	@return none
	*********************************************************************/
	public Tablet()
	{
		super("", "", true, false);
	}

	/*********************************************************************
	Constructor method for Tablet class. Creates a Tablet object with the 
	provided attributes.
	
	@param String itemName - The item display name.
	@param String itemDescription - The item display description.
	@param boolean isKeyItem - Whether or not the item may be removed.
		   false for can discard, true for cannot discard.
	@param boolean isUsed - Whether or not the item has been used. false
		   for is not used, true for is used.
	@return none
	*********************************************************************/
	public Tablet(String itemName, String itemDescription, 
			boolean isKeyItem, boolean isUsed)
	{
		super(itemName, itemDescription, isKeyItem, isUsed);
	}

	/*********************************************************************
	Defines the action to be take on Tablet use. Checks to see if player
	is in the correct room and whether or not the Tablet puzzle has been
	triggered. Triggers tabletPuzzle puzzle encounter.
	@param none
	@return none
	*********************************************************************/
	public void useItem(Player p)
	{
		// add correct rooms here
		if(isUsed /*|| p.getPlayerGame().getGameCurrentRoom() != p.getPlayerGame().tabletRoom */)
		{
			System.out.println("You cannot use the Tablet at this time.");
		}
		else
		{
			/* p.getPlayerGame().tabletPuzzle.solvePuzzle(); */
		}
	}
}