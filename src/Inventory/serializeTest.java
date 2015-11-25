package Inventory;
import java.io.*;
import java.util.*;
import org.junit.*;

import Obstacle.Enemy;
import UserInteraction.*;

/*********************************************************************
Serialization testing ground.
DO NOT IMPLEMENT IN FINAL PRODUCT.

@author Dale Burke
@version November 2015
 *********************************************************************/
public class serializeTest {

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
	Enemy mazer;
	Enemy cadet;
	Enemy bullies;
	Enemy jerry, peter, dissenter, droid, bonzo, hyrum, vader, queen, bugs;
	
	String [] hitOutput = new String [] {"barely grazed", "scored a major hit on", 
			"landed a solid strike on", "whacked the crap out of"};
	
	String [] tauntFlee = new String [] {"attDown", "10", "You take a kungfu stance "
			+ "and grin menacingly. \n"
			+ "Two of the bullies show their true colors and flee.", "Jerry",
			"You do your best to taunt", "You flaunt your puny muscles.", 
			"You expell flatulence in the general direction of",
			"is mildly amused that you thought that would have any affect.",
			"is dumbfounded.", "is disgusted but unmoved."};
	String [] tauntEnrage = new String [] {"enrage", "50", "You show your opponent "
			+ "the full moon. \nPerhaps that wasn't your best move yet, now he is "
			+ "really mad.", 
			"ENRAGED Mazer Rackham", "You do your best to taunt", 
			"You flaunt your puny muscles.", "",
			"You expell flatulence in the general direction of",
			"is mildly amused that you thought that would have any effect.",
			 "is like full-on Super Seiyan berserker mode mad right now."};
	
	String [] tauntHide = new String [] {"dodgeDown", "100", "You shout insults about your foes "
			+ "maternal unit. \n"
			+ "Enraged, he is done playing the hiding game.", "Plucifer",
			"You do your best to taunt", "You flaunt your puny muscles.", 
			"You expell flatulence in the general direction of",
			"is mildly amused that you thought that would have any effect.",
			"is dumbfounded.", "is disgusted but unmoved."};
	List<Enemy> enemyList = new ArrayList<Enemy>();
	FileOutputStream fOut;
	FileInputStream fIn;

	@Before
	public void setUp() throws Exception {
		mazer = new Enemy(06, "Mazer Rackham", 70, 15, 
				10, w5, 10, hitOutput, tauntEnrage);
		cadet = new Enemy(11, "Cadet", 30, 10, 
				110, w5, 10, hitOutput, tauntHide);
		bullies = new Enemy(01, "Jerry and two of his cohorts", 50, 19, 
				10, w5, 10, hitOutput, tauntFlee);
		peter = new Enemy(02, "Peter", 40, 10, 10, w5, 10, hitOutput,
				tauntHide);
//		dissenter = new Enemy(03, "Dissenter", 40, 12, 10, cBandAid, 10, 
//				hitOutput, tauntStandard);
//		droid = new Enemy(04, "Hand-to-Hand Combat Droid", 50, 10, 10, 
//				cMorphine, 10, hitOutput, tauntConcentration);
//		bonzo = new Enemy(05, "Bonzo and two of his buddies", 55, 25, 10,
//				cBandAid, 10, hitOutput, tauntFlee);
//		hyrum = new Enemy(07, "Colonel Hyrum Graff", 55, 12, 12, wLaserPistol, 10, 
//				hitOutput, tauntStandard);
//		vader = new Enemy(08, "Darth Vader", 65, 12, 15, wLightSaber, 20, 
//				hitOutput, tauntStandard);
//		queen = new Enemy(09, "Formic Queen", 70, 12, 15, cQueenEggs, 20, 
//				hitOutput, tauntWait);
//		bugs = new Enemy(10, "Bugs", 35, 12, 10, cStimpak, 6, hitOutput, tauntStandard);
		
		
		// weapons
		w1 = new Weapon("Weapon 1", "First weapon.", false, 10);
		w2 = new Weapon("Weapon 2", "Second weapon.", false, 20);
		w3 = new Weapon("Weapon 3", "Third weapon.", false, 30);
		w4 = new Weapon("Weapon 4", "Fourth weapon.", false, 40);
		w5 = new Weapon("Weapon 5", "Fifth weapon.", false, 50);

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
		player = new Player("Test Player", 7, 65, 65, 20, 8, 10, false, false, false, false, inv);
		inv.setOwner(player);

		// set up test player 2
		invNotFound = new Inventory(null, null, null, null, 0);
		playerInvNotFound = new Player("Test Player 2", 0, 0, 0, 0, 0, 0, 
				false, false, false, false, invNotFound);
		invNotFound.setOwner(playerInvNotFound);
	}

	@Test
	public void testInventory() throws IOException, ClassNotFoundException
	{
		inv.addToInventory(k1);
		inv.addToInventory(c2);
		inv.addToInventory(c3);
		inv.addToInventory(w4);
		inv.addToInventory(w5);
		inv.addToInventory(a1);
		inv.addToInventory(a2);
		inv.addToInventory(a3);
		inv.addToInventory(a4);
		inv.addToInventory(a5);

		enemyList.add(mazer);
//		enemyList.add(plucifer);
		enemyList.add(bullies);

		fOut = new FileOutputStream("game.dat");
		ObjectOutputStream serializer = new ObjectOutputStream(fOut);
		serializer.writeObject(inv);
		serializer.writeObject(enemyList);
		serializer.flush();

		inv = null;
		enemyList = null;

		fIn = new FileInputStream("game.dat");
		ObjectInputStream deserializer = new ObjectInputStream(fIn);
		inv = (Inventory) deserializer.readObject();
		enemyList = (List<Enemy>) deserializer.readObject();
		
		
		for(int i = 0; i < 10; i++)
		{
			player.getPlayerInventory().useItem();
		}
		
		enemyList.get(0).fight(player);
		enemyList.get(1).fight(player);
//		enemyList.get(2).fight(player);
		System.out.println("You have disengaged from combat.");
		System.out.println("Player score is now " + player.getPlayerScore());
		System.out.println("Player HP is " + player.getPlayerCurrentHP());
	}
}
