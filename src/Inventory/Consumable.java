package Inventory;
import java.io.Serializable;

import UserInteraction.*;  

public class Consumable extends Item implements Serializable
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
		int initial = p.getPlayerCurrentHP();
		int totalHealed = 0;
		if (p.getPlayerMaxHP() >= HP)
		{
			p.setPlayerCurrentHP(HP);
		}
		else
		{
			p.setPlayerCurrentHP(p.getPlayerMaxHP());
		}
		System.out.println("You use the " + itemType.toLowerCase() +" [" + itemName + "].");
		totalHealed = p.getPlayerCurrentHP() - initial;

		// message displayed on use
		if(totalHealed == 0)
		{
			System.out.println("It must be nice to have full health and an abundance of healing items,\n" + 
					"huh? 50 DKP minus (for first world problems), in addition to healing 0\n" +
					"health. You now have overwhelming sadness and a total of " + 
					p.getPlayerCurrentHP() + "/" + p.getPlayerMaxHP() + " HP.");
		}
		else
		{
			System.out.println("You health increases by " + totalHealed + 
					" for a total of " + p.getPlayerCurrentHP() + "/" + 
					p.getPlayerMaxHP() + " HP.");
		}
	}
}