package Inventory;

import static org.junit.Assert.*;

import org.junit.*;
import UserInteraction.*;

public class OxygenTest {

	/** tester attributes */
	Oxygen o2;
	Player p;

	/** setUp method to run before each test */
	@Before
	public void setUp() throws Exception 
	{
		p = new Player();
	}
	
	@Test
	public void testOxygen1() 
	{
		o2 = new Oxygen();
		assertEquals(o2.getItemType(), "Key Item");
		assertEquals(o2.getItemName(), "");
		assertEquals(o2.getItemDescription(), "");
		assertEquals(o2.getIsKeyItem(), true);
		assertEquals(o2.getIsUsed(), false);
	}

	@Test
	public void testOxygen2() 
	{
		o2 = new Oxygen("Test", "Test Item", true, true);
		assertEquals(o2.getItemType(), "Key Item");
		assertEquals(o2.getItemName(), "Test");
		assertEquals(o2.getItemDescription(), "Test Item");
		assertEquals(o2.getIsKeyItem(), true);
		assertEquals(o2.getIsUsed(), true);
	}
	
	@Test
	public void testUseItem() 
	{
		o2 = new Oxygen("Test", "Test Item", true, false);
		o2.useItem(p);
		assertEquals(o2.getIsUsed(), true);
		assertEquals(p.isOxygenFlag(), true);
		o2.useItem(p);
		assertEquals(o2.getIsUsed(), true);
		assertEquals(p.isOxygenFlag(), true);
	}
}
