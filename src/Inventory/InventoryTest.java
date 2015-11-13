package Inventory; 

import static org.junit.Assert.*;

import java.awt.List;

import org.junit.Test;

import UserInteraction.*;

public class InventoryTest {

	@Test
	public void testInventory() {
		Weapon hat = new Weapon("wapn", "it's cool", false, 23);
		Weapon hdt = new Weapon("aapn", "it's cool", false, 23);
		Weapon hgt = new Weapon("bapn", "it's cool", false, 23);
		Weapon hht = new Weapon("capn", "it's cool", false, 23);
		Accessory der = new Accessory("itens", "it's lame", false, 4, 23, 3);
		Accessory wot = new Accessory("hello", "coolbean", false, 39, 4, 5);
		Consumable c1 = new Consumable("heals1", "heals for reals", false, 30);
		Consumable c2 = new Consumable("heals2", "heals for reals", false, 30);
		Consumable c3 = new Consumable("heals3", "heals for reals", false, 30);
		Consumable c4 = new Consumable("heals4", "heals for reals", true, 30);
		Consumable c5 = new Consumable("heals5", "heals for reals", true, 30);
		Consumable c6 = new Consumable(null, "Maybe heals.", false, 20);
		Player guy = new Player();
		Inventory inv = new Inventory();
		inv.setOwner(guy);
		inv.addToInventory(hdt);
		inv.addToInventory(hgt);
		inv.addToInventory(hht);
		inv.addToInventory(hat);
		inv.addToInventory(der);
		inv.addToInventory(wot);
		inv.addToInventory(c1);
		inv.addToInventory(c2);
		inv.addToInventory(c3);
		inv.addToInventory(c4);
		inv.addToInventory(c5);
		inv.addToInventory(c6);
		for(int i = 0; i < 20; i++)
		{
			inv.useItem();
		}
		System.out.println(guy.getPlayerAttack());
		System.out.println(guy.getPlayerDodge());
		System.out.println(guy.getPlayerMaxHP());
	}

	@Test
	public void testInventoryListOfItemPlayerWeaponAccessory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUseItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddToInventory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOwner() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetOwner() {
		fail("Not yet implemented");
	}

}
