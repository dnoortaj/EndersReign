package Inventory;
import Inventory.Weapon;
import UserInteraction.Player;

/**
 * @author Ethan Patterson
 *
 */
public class WeaponTester
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Weapon wep = new Weapon("Sword", "Diamond", false, 9999);
		Player playa = new Player();
		wep.useItem(playa);
		
		Weapon wep2 = new Weapon("Feather", "Duck", false, 3);
		wep2.useItem(playa);
		
		Weapon wep3 = new Weapon("Scroll", "Ancient", true, 0);
		wep3.useItem(playa);
		

	}

}
