/*********************************************************************
Defines generic item layout. Superclass to be extended by all item
classes.

@author Dale Burke
@version November 2015
*********************************************************************/
public abstract class Item {
	
	/** item name as it is to appear in game */
	String itemName;
	
	/** item description as it is to appear in game */
	String itemDescription;
	
	/** attribute determines if the item may be discarded; true for cannot
	 *  discard, false for cannot discard */
	boolean isKeyItem;
	
	/*********************************************************************
	Default constructor method for Item superclass. Creates a blank Item
	object that may be discarded.

	@param none
	@return none
	*********************************************************************/
	public Item()
	{
		itemName = "";
		itemDescription = "";
		isKeyItem = false;
	}

	/*********************************************************************
	Constructor method for Item superclass. Creates an Item object with
	the provided attributes.
	
	@param String itemName - The item display name.
	@param String itemDescription - The item display description.
	@param boolean isKeyItem - Whether or not the item may be removed.
		   false for can discard, true for cannot discard.
	@return none
	*********************************************************************/
	public Item(String itemName, String itemDescription, boolean isKeyItem)
	{
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.isKeyItem = isKeyItem;
	}

	/*********************************************************************
	Method to set this item's name.

	@param String itemName - The item display name.
	@return none
	*********************************************************************/
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	
	/*********************************************************************
	Method to set this item's description.

	@param String itemDescription - The item display description.
	@return none
	*********************************************************************/
	public void setItemDescription(String itemDescription)
	{
		this.itemDescription = itemDescription;
	}
	
	/*********************************************************************
	Method to set whether or not this item may be discarded.

	@param boolean isKeyItem - false for can discard, true for cannot 
		   discard.
	@return none
	*********************************************************************/
	public void setIsKeyItem(boolean isKeyItem)
	{
		this.isKeyItem = isKeyItem;
	}
	
	/*********************************************************************
	Method to get this item's name.

	@param none
	@return String itemName - The item display name.
	*********************************************************************/
	public String getItemName()
	{
		return itemName;
	}
	
	/*********************************************************************
	Method to get this item's description.

	@param none
	@return String itemDescription - The item display description.
	*********************************************************************/
	public String getItemDescription()
	{
		return itemDescription;
	}
	
	/*********************************************************************
	Method to get whether or not this item may be discarded.

	@param none
	@return boolean isKeyItem - false for can discard, true for cannot 
		    discard.
	*********************************************************************/
	public boolean getIsKeyItem()
	{
		return isKeyItem;
	}
	
	/*********************************************************************
	Abstract method to be defined in each subclass. Defines the action to
	be take on item use.

	@param none
	@return none
	*********************************************************************/
	public abstract void useItem();
}
