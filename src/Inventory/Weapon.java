package Inventory;
import UserInteraction.*; 

/**
 * @author Ethan Patterson
 *
 */
public class Weapon extends Item
{
	private int weaponAttackBonus;

	/**
	 * Creates an instance of Weapon and fills said instance with its name, description, key item
	 * property, and attack value. Used in for weapon pool creation on starting a new game.
	 */
	public Weapon(String name, String description, boolean isKeyItem,
				int attack)
	{
		super("Weapon", name, description, isKeyItem);
		weaponAttackBonus = attack;
	}

	/**
	 * Method Name: useItem
	 * 
	 * Method Description: Returns any currently equipped weapon to inventory, and removes any
	 * associated bonuses to player statistics. Equips this weapon to the associated slot and
	 * modifies the current player’s attack statistic based on the weapon’s attack. Invoked when
	 * player attempts to use / equip this weapon.
	 */
	public void useItem(Player p)
	{
		int attack = p.getPlayerAttack() + weaponAttackBonus;
		p.setPlayerAttack(attack);
		System.out.println("You equipped the " + itemDescription + " " + itemName + ".");
	}
	
	
	/**
	 * @return the weaponAttackBonus
	 */
	public int getWeaponAttackBonus()
	{
		return weaponAttackBonus;
	}

	/**
	 * @param weaponAttackBonus
	 *            the weaponAttackBonus to set
	 */
	public void setWeaponAttackBonus(int weaponAttackBonus)
	{
		this.weaponAttackBonus = weaponAttackBonus;
	}
}