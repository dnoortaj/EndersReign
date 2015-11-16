package Inventory;

import static org.junit.Assert.*;

import org.junit.*;
import UserInteraction.*;

public class SuitTest {

	/** tester attributes */
	Suit suit;
	Player p;

	/** setUp method to run before each test */
	@Before
	public void setUp() throws Exception 
	{
		p = new Player();
	}
	
	@Test
	public void testSuit1() 
	{
		suit = new Suit();
		assertEquals(suit.getItemType(), "Key Item");
		assertEquals(suit.getItemName(), "");
		assertEquals(suit.getItemDescription(), "");
		assertEquals(suit.getIsKeyItem(), true);
		assertEquals(suit.getIsUsed(), false);
	}

	@Test
	public void testSuit2() 
	{
		suit = new Suit("Test", "Test Item", true, true);
		assertEquals(suit.getItemType(), "Key Item");
		assertEquals(suit.getItemName(), "Test");
		assertEquals(suit.getItemDescription(), "Test Item");
		assertEquals(suit.getIsKeyItem(), true);
		assertEquals(suit.getIsUsed(), true);
	}
	
	@Test
	public void testUseItem() 
	{
		suit = new Suit("Test", "Test Item", true, false);
		suit.useItem(p);
		assertEquals(suit.getIsUsed(), true);
		assertEquals(p.isSuitFlag(), true);
		suit.useItem(p);
		assertEquals(suit.getIsUsed(), true);
		assertEquals(p.isSuitFlag(), true);
	}
}
