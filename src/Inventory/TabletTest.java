package Inventory;

import static org.junit.Assert.*;

import org.junit.*;
import UserInteraction.*;

public class TabletTest {

	/** tester attributes */
	Tablet tablet;
	Player p;

	/** setUp method to run before each test */
	@Before
	public void setUp() throws Exception 
	{
		p = new Player();
	}
	
	@Test
	public void testTablet1() 
	{
		tablet = new Tablet();
		assertEquals(tablet.getItemType(), "Key Item");
		assertEquals(tablet.getItemName(), "");
		assertEquals(tablet.getItemDescription(), "");
		assertEquals(tablet.getIsKeyItem(), true);
		assertEquals(tablet.getIsUsed(), false);
	}

	@Test
	public void testTablet2() 
	{
		tablet = new Tablet("Test", "Test Item", true, true);
		assertEquals(tablet.getItemType(), "Key Item");
		assertEquals(tablet.getItemName(), "Test");
		assertEquals(tablet.getItemDescription(), "Test Item");
		assertEquals(tablet.getIsKeyItem(), true);
		assertEquals(tablet.getIsUsed(), true);
	}
	
	@Test
	public void testUseItem() 
	{
		tablet = new Tablet("Test", "Test Item", true, false);
		tablet.useItem(p);
		assertEquals(tablet.getIsUsed(), true);
		assertEquals(p.isTabletFlag(), true);
		tablet.useItem(p);
		assertEquals(tablet.getIsUsed(), true);
		assertEquals(p.isTabletFlag(), true);
	}
}
