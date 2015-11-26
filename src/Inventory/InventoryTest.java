package Inventory;

import static org.junit.Assert.*;
import org.junit.*;
import UserInteraction.*;

/*********************************************************************
Tester class for Inventory.

@author Dale Burke
@version November 2015
 *********************************************************************/
public class InventoryTest {

	/** tester attributes */
	Weapon w1;
	Weapon w2;
	Weapon w3;
	Weapon w4;
	Weapon w5;
	Accessory a1;
	Accessory a2;
	Accessory a3;
	Accessory a4;
	Accessory a5;
	Consumable c1;
	Consumable c2;
	Consumable c3;
	Consumable c4;
	Consumable c5;
	Consumable cNull;
	Oxygen k1;
	Oxygen k2;
	Oxygen k3;
	Inventory inv;
	Inventory invNotFound;
	Player player;
	Player playerInvNotFound;

	/** setUp method to run before each test */
	@Before
	public void setUp() throws Exception {
		// weapons
		w1 = new Weapon("Weapon 1", "First weapon.", false, 10, null);
		w2 = new Weapon("Weapon 2", "Second weapon.", false, 20, null);
		w3 = new Weapon("Weapon 3", "Third weapon.", false, 30, null);
		w4 = new Weapon("Weapon 4", "Fourth weapon.", false, 40, null);
		w5 = new Weapon("Weapon 5", "Fifth weapon.", false, 50, null);

		// accessories
		a1 = new Accessory("Accessory 1", "First accessory.", false, 1, 1, 1);
		a2 = new Accessory("Accessory 2", "Second accessory.", false, 2, 2, 2);
		a3 = new Accessory("Accessory 3", "Third accessory.", false, 3, 3, 3);
		a4 = new Accessory("Accessory 4", "Fourth accessory.", false, 4, 4, 4);
		a5 = new Accessory("Accessory 5", "Fifth accessory.", false, 5, 5, 5);

		// consumables
		c1 = new Consumable("Consumable 1", "First consumable.", false, 1);
		c2 = new Consumable("Consumable 2", "Second consumable.", false, 2);
		c3 = new Consumable("Consumable 3", "Third consumable.", false, 3);
		c4 = new Consumable("Consumable 4", "Fourth consumable.", false, 4);
		c5 = new Consumable("Consumable 5", "Fifth consumable.", false, 5);
		cNull = new Consumable(null, null, false, 100);

		// key items
		k1 = new Oxygen("O2 Test Key Item 1", "First test key item.", true, false);
		k2 = new Oxygen("O2 Test Key Item 2", "Second test key item.", true, false);
		k3 = new Oxygen("O2 Test Key Item 3", "Third test key item.", true, false);

		// set up test player 1
		inv = new Inventory();
		player = new Player("Test Player 1", 0, 0, 0, 0, 0, 0, 
				false, false, false, false, inv);
		inv.setOwner(player);

		// set up test player 2
		invNotFound = new Inventory(null, null, null, null, 0);
		playerInvNotFound = new Player("Test Player 2", 0, 0, 0, 0, 0, 0, 
				false, false, false, false, invNotFound);
		invNotFound.setOwner(playerInvNotFound);
	}

	@Test
	public void testAddToInventory1()
	{
		inv.addToInventory(null);
	}

	@Test
	public void testAddToInventory2()
	{
		inv.addToInventory(w1);
		assertEquals(inv.getMaxItems(), 10);
	}

	@Test
	public void testAddToInventory3()
	{
		inv.addToInventory(k1);
		assertEquals(inv.getMaxItems(), 11);
	}

	@Test
	public void testAddToInventory4()
	{
		inv.addToInventory(cNull);
		inv.useItem();
	}

	@Test
	public void testAddToInventory5()
	{
		invNotFound.addToInventory(w1);
	}

	@Test
	public void testAddToInventory6A()
	{
		inv.addToInventory(w1);
		inv.addToInventory(w2);
		inv.addToInventory(w3);
		inv.addToInventory(w4);
		inv.addToInventory(w5);
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		inv.addToInventory(a3);
		inv.addToInventory(a4);
		inv.addToInventory(a5);
		inv.addToInventory(k1);
		inv.addToInventory(k2);
		inv.addToInventory(k3);
		inv.addToInventory(c1);
	}

	@Test
	public void testAddToInventory6B()
	{
		inv.addToInventory(w1);
		inv.addToInventory(w2);
		inv.addToInventory(w3);
		inv.addToInventory(w4);
		inv.addToInventory(w5);
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		inv.addToInventory(a3);
		inv.addToInventory(a4);
		inv.addToInventory(a5);
		inv.addToInventory(k1);
		inv.addToInventory(k2);
		inv.addToInventory(k3);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
		inv.addToInventory(c1);
	}

	@Test
	public void testAddToInventory6C()
	{
		inv.addToInventory(w1);
		inv.addToInventory(w2);
		inv.addToInventory(w3);
		inv.addToInventory(w4);
		inv.addToInventory(w5);
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		inv.addToInventory(a3);
		inv.addToInventory(a4);
		inv.addToInventory(a5);
		inv.addToInventory(k1);
		inv.addToInventory(k2);
		inv.addToInventory(k3);
		assertEquals(inv.getMaxItems(), 13);
		inv.addToInventory(c1);
		assertEquals(inv.getMaxItems(), 13);
	}

	@Test
	public void testAddToInventory6D()
	{
		inv.addToInventory(w1);
		inv.addToInventory(w2);
		inv.addToInventory(w3);
		inv.addToInventory(w4);
		inv.addToInventory(w5);
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		inv.addToInventory(a3);
		inv.addToInventory(a4);
		inv.addToInventory(a5);
		inv.addToInventory(k1);
		inv.addToInventory(k2);
		inv.addToInventory(k3);
		inv.addToInventory(c1);
	}

	@Test
	public void testAddToInventory6E()
	{
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		inv.addToInventory(a3);
		inv.addToInventory(a4);
		inv.addToInventory(a5);
		inv.addToInventory(c1);
		inv.addToInventory(c2);
		inv.addToInventory(c3);
		inv.addToInventory(c4);
		inv.addToInventory(c5);
		inv.addToInventory(cNull);
		inv.addToInventory(k1);
		inv.addToInventory(k2);
		inv.addToInventory(k3);
		inv.addToInventory(w1);
		inv.addToInventory(w2);
		inv.addToInventory(w3);
		inv.addToInventory(w4);
		inv.addToInventory(w5);
	}

	@Test
	public void testUseItem1() 
	{
		inv.useItem();
	}

	@Test
	public void testUseItem2() 
	{
		inv.addToInventory(c1);
		inv.addToInventory(c2);
		for(int i = 0; i < 2; i++)
		{
			inv.useItem();
		}
	}

	@Test
	public void testUseItem3() {
		inv.addToInventory(w1);
		inv.addToInventory(w2);
		for(int i = 0; i < 3; i++)
		{
			inv.useItem();
		}
	}

	@Test
	public void testUseItem4()
	{
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		for(int i = 0; i < 3; i++)
		{
			inv.useItem();
		}
	}

	@Test
	public void testUseItem5()
	{
		inv.addToInventory(k1);
		for(int i = 0; i < 2; i++)
		{
			inv.useItem();
		}
	}

	@Test
	public void testUseItem6()
	{
		inv.addToInventory(k1);
		player.setBattleFlag(true);
		for(int i = 0; i < 2; i++)
		{
			inv.useItem();
		}
	}

	@Test
	public void testUseItem7A()
	{
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		inv.addToInventory(a3);
		inv.addToInventory(a4);
		inv.addToInventory(a5);
		inv.addToInventory(c1);
		inv.addToInventory(c2);
		inv.addToInventory(c3);
		inv.addToInventory(c4);
		inv.addToInventory(c5);
		inv.addToInventory(k1);
		inv.addToInventory(k2);
		inv.addToInventory(k3);
		
		for(int i = 0; i < 15; i++)
		{
			inv.useItem();
		}
	}

	@Test
	public void testUseItem7B()
	{
		inv.useItem();
	}

	@Test
	public void testSetMaxItems1() {
		inv.setMaxItems(-1);
		assertEquals(inv.getMaxItems(), 0);
	}

	@Test
	public void testSetMaxItems2() {
		inv.setMaxItems(5);
		assertEquals(inv.getMaxItems(), 5);
	}
}
