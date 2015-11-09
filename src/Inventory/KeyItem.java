package Inventory;

/*********************************************************************
Defines generic key item layout. Superclass to be extended by all key 
item classes.

@author Dale Burke
@version November 2015
*********************************************************************/
public abstract class KeyItem extends Item {
	
	/** item name as it is to appear in game */
	boolean isUsed;
	
	/*********************************************************************
	Default constructor method for KeyItem superclass. Creates a blank 
	KeyItem object that cannot be discarded.

	@param none
	@return none
	*********************************************************************/
	public KeyItem()
	{
		super("", "", true);
		isUsed = false;
	}

	/*********************************************************************
	Constructor method for KeyItem superclass. Creates a KeyItem object 
	with the provided attributes.
	
	@param String itemName - The item display name.
	@param String itemDescription - The item display description.
	@param boolean isKeyItem - Whether or not the item may be removed.
		   false for can discard, true for cannot discard.
	@param boolean isUsed - Whether or not the item has been used. false
		   for is not used, true for is used.
	@return none
	*********************************************************************/
	public KeyItem(String itemName, String itemDescription, 
			boolean isKeyItem, boolean isUsed)
	{
		super(itemName, itemDescription, isKeyItem);
		this.isUsed = isUsed;
	}

	/*********************************************************************
	Method to set whether or not this item has been used.

	@param boolean isUsed - false for is not used, true for is used.
	@return none
	*********************************************************************/
	public void setIsUsed(boolean isUsed)
	{
		this.isUsed = isUsed;
	}
	
	/*********************************************************************
	Method to get whether or not this item has been used.

	@param none
	@return boolean isUsed - false for is not used, true for is used.
	*********************************************************************/
	public boolean setIsUsed()
	{
		return isUsed;
	}
	
	/*********************************************************************
	Abstract method to be defined in each subclass. Defines the action to
	be take on item use.

	@param none
	@return none
	*********************************************************************/
	public abstract void useItem(Game g);
}
