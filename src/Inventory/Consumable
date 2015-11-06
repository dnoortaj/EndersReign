public class Consumable extends Item
{
	private int consumableHPRecovery;
	
	public Consumable(String itemName, String itemDescription, boolean isKeyItem, int consumableHPRecovery)
	{
	  super(itemName, itemDescription, isKeyItem);
	  this.consumableHPRecovery = consumableHPRecovery;
	}

	public int getConsumableHPRecovery()
	{
		return consumableHPRecovery;
	}

	public void setConsumableHPRecovery(int consumableHPRecovery)
	{
		this.consumableHPRecovery = consumableHPRecovery;
	}
	
	@Override
	public void useItem()
	{
		int HP = currentPlayer.getPlayerCurrentHP() + consumableHPRecovery;
		if (currentPlayer.getPlayerMaxHP() >= HP)
		{
		  currentPlayer.setPlayerCurrentHP(HP);
		}
		else
		{
		  currentPlayer.setPlayerCurrentHP(currentPlayer.getPlayerMaxHP());
		}
	}
	
}
