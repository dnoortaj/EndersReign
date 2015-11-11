package Inventory;
import UserInteraction.*;  

public class Consumable extends Item
{
	private int consumableHPRecovery;

	public Consumable(String itemName, String itemDescription, boolean isKeyItem, int consumableHPRecovery)
	{
		super("Consumable", itemName, itemDescription, isKeyItem);
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
	public void useItem(Player p)
	{
		int HP = p.getPlayerCurrentHP() + consumableHPRecovery;
		if (p.getPlayerMaxHP() >= HP)
		{
			p.setPlayerCurrentHP(HP);
		}
		else
		{
			p.setPlayerCurrentHP(p.getPlayerMaxHP());
		}
		System.out.println("Your HP is now " + p.getPlayerCurrentHP() + " HP.");
	}
}