package UserInteraction;

/** Class: GameController.java
 * @author Danyelle Noortajalli
 * @version 1.0
 * Course: ITEC 3150 Fall 2015
 * Written: Nov 12, 2015
 * 
 * 
 * This class - GameController
 * 
 * 
 * Purpose: creates the game, loads main menu,
 * loads save file, and writes save file.
 *
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.Before;

import Inventory.*;
import Obstacle.*;

public class GameController
{

	private Game game;
	private boolean run;
	String helpList;
	String gameFile;
	
	Inventory inv;
	Inventory invNotFound;
	Player currentPlayer;
	Player playerInvNotFound;
	private Room currentRoom;
	
	List<Enemy> enemyList = new ArrayList<Enemy>();
	List<Puzzle> puzzleList = new ArrayList<Puzzle>();
	
	Scanner sc = new Scanner(new InputStreamReader(System.in));
	Random random = new Random();
	FileInputStream fIn;
	FileOutputStream fOut;
	ObjectInputStream deserializer; 

	Tablet tablet;
	Suit suit;
	Oxygen oxygen;
	
	Accessory academy, writ, goldStar, buggerMask, launchie, salamander, dragon,
	admiralsCrest, hat;
	
	Weapon fisticuffs, bluntObject, laserPistol, dualLaser, theBird, lightSaber;
	
	Consumable bandAid,	morphine, potion, stimpak, surgeryKit, phoenixDown, queenEggs;

	String [] hitOutput, tauntFlee, tauntEnrage, tauntHide, tauntConcentration, 
	tauntStandard;
	
	Enemy mazer, jerry, peter, dissenter, droid, bonzo, hyrum, vader, queen, 
	beatle, cadet, bullies, ant, centipede, bee, housefly, mosquito, bedBug;
	
	/**
	 * Method: GameController
	 * constructor for GameController class
	 */
	public GameController()
	{
		game = new Game();
		run = false;
	}

	/**
	 * Method: mainMenu
	 *builds main menu in the console to the user
	 */
	public void mainMenu()
	{		 


		System.out.println(" ______   ______   ______   ______   ______   ______   ______      \n"+
				"/_____/  /_____/  /_____/  /_____/  /_____/  /_____/  /_____/      \n" +
				"._.__._. /______________/  /_____.________/  /_/\\__/  /__._./ ._.  \n" +
				"| |  | |    \\_   _____/ ____   __| _/__________)/______  | |  | |  \n" +
				"|_|  |_|     |    __)_ /    \\ / __ _/ __ \\_  __ /  ___/  |_|  |_|  \n" +
				"|-|  |-|     |        |   |  / /_/ \\  ___/|  | \\\\___ \\   |-|  |-|  \n" +
				"| |  | |    /_______  |___|  \\____ |\\___  |__| /____  >  | |  | |  \n" +
				"._.  ._.    __________     \\/.__  \\/    \\/          \\/   ._.  ._.  \n" +
				"| |  | |    \\______   \\ ____ |__| ____   ____            | |  | |  \n" +
				"|_|  |_|     |       __/ __ \\|  |/ ___\\ /    \\           |_|  |_|  \n" +
				"|-|  |-|     |    |   \\  ___/|  / /_/  |   |  \\          |-|  |-|  \n" +
				"| |  | |     |____|_  /\\___  |__\\___  /|___|  /          | |  | |  \n" +
				"|______|  ______   ______  \\_______________ \\/______   ______ |_|  \n" +
				"/_____/  /_____/  /_____/  /_____/  /_____/  /_____/  /_____/      \n" +
				"/_____/  /_____/  /_____/  /_____/  /_____/  /_____/  /_____/     \n");
		for (int i = 0; i < 3; i++)
		{
			System.out.println();
		}
		System.out.println("Welcome to Ender's Reign: Wiggin's Formic Rage! \nWould you like to:"
				+ "\n> Start New Game \n> Load Game \n> Exit");
		String input = sc.nextLine();



		while (!input.equalsIgnoreCase("New")
				&& !input.equalsIgnoreCase("Load")
				&& !input.equalsIgnoreCase("Exit"))
		{
			System.out.println("Your input did not match available options." +
					"\n Please type \"New\", \"Load\", or \"Exit\"");
			input = sc.nextLine();
		}

		if (input.contains("New") || input.contains("new"))
		{
			startNewGame();
		}
		else if (input.contains("Load") || input.contains("load"))
		{
			loadGame();
		}
		else if (input.equalsIgnoreCase("Exit"))
		{
			System.exit(0);
		}

	}

	/**
	 * Method: startNewGame
	 * runs when user wants to start a new game. If there is a save file in
	 * that position prior, it asks to delete it before overwriting the new save
	 * file in which a base case of the game will be stored
	 */
	public void startNewGame()
	{	
		try{
			gameFile = "gameDefualt.dat";
			fIn = new FileInputStream(gameFile);
			deserializer = new ObjectInputStream(fIn);
			loadObjects();
		}catch(IOException e){

		}
	}


	/**
	 * Method: loadGame
	 * allows user to access save file and load where they left off last time playing
	 */
	public void loadGame()
	{
		int loadFile;
		try
		{

			System.out.println("Which file would you like to open? (Enter a value 1-3)");
			System.out.print("> ");			
			loadFile = Integer.parseInt(sc.nextLine());
			if (loadFile == 1)
			{
				gameFile = "game1.dat";			
			}
			else if (loadFile == 2)
			{
				gameFile = "game2.dat";			
			}
			else if (loadFile == 3)
			{
				gameFile = "game3.dat";			
			}
			else
			{
				System.out.println("Sorry, you've entered an incorrect value. \nPlease enter a value 1-3.");
				loadGame();
			}
			fIn = new FileInputStream(gameFile);
			deserializer = new ObjectInputStream(fIn);
			loadObjects();
		}
		catch (Exception e)
		{
			System.out.println("Somebody fcuked up");	
		}
	}

	public void loadObjects(){
		try {
			inv = (Inventory) deserializer.readObject();
			enemyList = (List<Enemy>) deserializer.readObject();
		//TODO read player, puzzles, rooms objects
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void saveObject(){
		try{
			fOut = new FileOutputStream(gameFile);
			ObjectOutputStream serializer = new ObjectOutputStream(fOut);
			serializer.writeObject(inv);
			serializer.writeObject(enemyList);
		//TODO write helper method for player, puzzles, rooms objects
			serializer.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void listener() {
		System.out.print("> ");
		String input = sc.next();
		switch (input.toLowerCase()) {
		case "w":
			move(1);
			break;
		case "s":
			move(2);
			break;
		case "a":
			move(4);
			break;
		case "d":
			move(3);
			break;
		case "h":
			displayHelp();
			listener();
			break;
		case "i":
			currentPlayer.getPlayerInventory().useItem();
			break;
		case "l":
			System.out.println(currentRoom.getRoomDescription(1));
		case "1":
			saveObject();
			listener();
			break;
		case "2":
			loadGame();
			listener();
			break;
		case "0":
			//exit 
			break;
		default:
			System.out.println("Command not recognized.");
			listener();
			break;
		}
	}

	public void move(int direction)
	{
		boolean hasMoved = false;
		boolean monsterEncountered = false;
		String dir = "nowhere.";

		// set direction string element
		if(direction == 1)
		{
			dir = "north.";
		}
		else if(direction == 2)
		{
			dir = "south.";
		}
		else if(direction == 3)
		{
			dir = "west.";
		}
		else if(direction == 4)
		{
			dir = "east.";
		}

		// see if player can move, make the move
		if(currentRoom.getRoomExits(direction) != null)
		{
			// move in desired direction, update room
			currentRoom = currentRoom.getRoomExits(direction);
			System.out.println("You head " + dir);
			hasMoved = true;
		}
		else
		{
			// notify player of non-move
			System.out.println("You cannot move " + dir);
		}

		// if player moved
		if(hasMoved)
		{
			System.out.println(currentRoom.getRoomDescription(0));

			// attempt trigger current room's enemy
			if(!currentRoom.getRoomEnemy().enemyIsDead())
			{
				if(random.nextInt(100) + 1 > currentRoom.getRoomEnemyChance())
				{
					// monster was encountered
					monsterEncountered = true;

					// combat flag set prior to fight, updated after fight
					currentPlayer.setBattleFlag(true);
					currentRoom.getRoomEnemy().fight(currentPlayer);
					currentPlayer.setBattleFlag(false);

					if(currentRoom.getRoomEnemy().enemyIsDead())
					{
						// updates score
						System.out.println("Your score just increased by " + currentRoom.getRoomPuzzle().getPuzzlePoints()
								+ " points for a total of " + currentPlayer.getPlayerScore() + "!");

						// retrieves the room's enemy reward and adds to current player inventory
						currentPlayer.getPlayerInventory().addToInventory(currentRoom.getRoomEnemy().getReward());
					}			
				}
			}

			// attempt to trigger current room's puzzle if enemy was not encountered
			if(!currentRoom.getRoomPuzzle().getPuzzleIsCompleted() && !monsterEncountered)
			{
				if(random.nextInt(100) + 1 > currentRoom.getRoomEnemyChance())
				{
					// triggers the puzzle, adds outcome to player score
					currentPlayer.addToScore(currentRoom.getRoomPuzzle().solvePuzzle());

					// updates score
					System.out.println("Your score just increased by " + currentRoom.getRoomPuzzle().getPuzzlePoints()
							+ " points for a total of " + currentPlayer.getPlayerScore() + "!");

					// retrieves the room's puzzle reward and adds to current player inventory
					currentPlayer.getPlayerInventory().addToInventory(currentRoom.getRoomPuzzle().getPuzzleReward());
				}
			}
		}
	}
	
	public void displayHelp()
	{
		System.out.println(helpList);
	}
	
	public void constructGame(){
		
		hitOutput = new String [] {"barely grazed", "scored a major hit on", 
				"landed a solid strike on", "whacked the crap out of"};

		tauntFlee = new String [] {"attDown", "10", "You take a kungfu stance "
				+ "and grin menacingly. \n"
				+ "Two of the bullies show their true colors and flee.", "Jerry",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any affect.",
				"is dumbfounded.", "is disgusted but unmoved."};

		tauntEnrage = new String [] {"enrage", "50", "You show your opponent "
				+ "the full moon. \nPerhaps that wasn't your best move yet, now he is "
				+ "really mad.", 
				"ENRAGED Mazer Rackham", "You do your best to taunt", 
				"You flaunt your puny muscles.", "",
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any effect.",
				"is like full-on Super Seiyan berserker mode mad right now."};

		tauntHide = new String [] {"dodgeDown", "100", "You shout insults about your foes "
				+ "maternal unit. \n"
				+ "Enraged, he is done playing the hiding game.", "Plucifer",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any effect.",
				"is dumbfounded.", "is disgusted but unmoved."};

		tauntConcentration = new String [] {"dodgeDown", "25", 
				"You shout insults about your foes maternal unit. \n"
				+ "Enraged, he is having trouble concentrating.", "Plucifer",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any effect.",
				"is dumbfounded.", "is disgusted but unmoved."};

		tauntStandard = new String [] {"z", "0", " ", " ", 
				"You bite your thumb at ",
				"You wave your arms wildly in the air.",
				"You shout obsenities at ",
				" is not impressed.",
				" makes a comment about you being the son of a motherless goat.",
				" uses this as an" + " opportunity to take a free hit on you." };



		//Items
		//Key items
		tablet = new Tablet ("Tablet", "A cutting-edge computational device, standard issue to Academy"
				+ " enrollees.", true, false);
		suit = new Suit ("Combat Suit", "A suit made of tightly interwoven fibers. Required for zero "
				+ "gravity combat training.", true, false);
		oxygen = new Oxygen ("Supplemental O2 Device", "Vader's voice changer. Makes you sound cool and"
				+ " prevents asphyxiation in thin atmosphere.", true, false);
		
		//accessories
		academy = new Accessory ("Academy Monitor", "Hurts about as much as a papercut to remove."
				+ " If the paper was made of salted knives.", false, -1, -1, 0);
		writ = new Accessory ("Writ of Advanced Bullying", "Beware that, when fighting bullies, you "
				+ "yourself do not become a bully.", false, 2, -1, 2);
		goldStar = new Accessory ("Gold Star", "Grants the title, \"Teacher's Pet.\"", false, 0, 3, 0);
		buggerMask = new Accessory ("Bugger Mask", "Buggers and Astronauts. Get with the program.", false,
				1, 0, 3);
		launchie = new Accessory("Launchie Insignia", "A bright yellow patch. Granted for reaching Battle"
				+ " School without throwing up.", false, 0, 2, 2);
		salamander = new Accessory("Salamander Insignia", "An emerald green patch. Granted for being a pain "
				+ "in Bonzo's ass.", false, 2, 2, 2);
		dragon = new Accessory("Dragon Insignia", "A pale orange patch. Makes you feel like a badass.",
				false, 2, 3, 3);
		admiralsCrest = new Accessory("Admiral's Crest", "You're kind of a big deal now, you murderer.",
				false, 3, 3, 3);
		hat = new Accessory("Harrison Ford's Hat", "Fortune and glory, kid. Fortune and glory.",false, 2, 5, 5);
		
		//weapons
		fisticuffs = new Weapon("Fisticuffs", "Have at thee, coward!", false, 1);
		bluntObject = new Weapon("Blunt Object", "No, not that kind of \"blunt.\"", false, 2);
		laserPistol = new Weapon("Laser Pistol", "Cautionary note reads: \"NOT FOR ACTUAL USE.\"", false, 0);
		dualLaser = new Weapon("Dual Laser Pistols", "What's 0 times 2? Because that's how much damage you'll"
				+ " be doing with these.", false, 0);
		theBird = new Weapon("The Bird", "Mazer's weapon of choice, used to devastating effect.", false, 3);
		lightSaber = new Weapon("Lightsaber", "Vader's lightsaber. You should be ticketed for all the physical"
				+ " laws you're violating with this.", false, 5);
		
		//consumables
		bandAid = new Consumable("Band-Aid", "Cheap, non-effective healing technology from the 20th century.",
				false, 1);
		morphine = new Consumable("Morphine", "Consumable,It's not addictive. Promise.", false, 4);
		potion = new Consumable("Potion", "Cures light wounds for 1d8 health.", false, 8);
		stimpak = new Consumable("Stimpak", "Standard-issue healing medication.", false, 16);
		surgeryKit = new Consumable("Surgery Kit", "Now with 20% more amputation!" , false,32);
		phoenixDown = new Consumable("Phoenix Down", "Reraise sold separately.", false, 999);
		queenEggs = new Consumable("Queen Eggs", "Taste like chicken.", false, 1);

		//EnemYS


		bullies = new Enemy(01, "Jerry and two of his cohorts", 50, 19, 
				10, writ, 10, hitOutput, tauntFlee);
		peter = new Enemy(02, "Peter", 40, 10, 10, bluntObject, 10, hitOutput,
				tauntHide);
		dissenter = new Enemy(03, "Dissenter", 40, 12, 10, fisticuffs, 10, 
				hitOutput, tauntStandard);
		droid = new Enemy(04, "Hand-to-Hand Combat Droid", 50, 10, 10, 
				morphine, 10, hitOutput, tauntConcentration);
		bonzo = new Enemy(05, "Bonzo and two of his buddies", 55, 25, 10,
				laserPistol, 10, hitOutput, tauntFlee);
		mazer = new Enemy(06, "Mazer Rackham", 70, 15, 
				10, theBird, 10, hitOutput, tauntEnrage);
		hyrum = new Enemy(07, "Colonel Hyrum Graff", 55, 12, 12, hat, 10, 
				hitOutput, tauntStandard);
		vader = new Enemy(8, "Darth Vader", 65, 12, 15, lightSaber, 20, 
				hitOutput, tauntStandard);
		queen = new Enemy(9, "Formic Queen", 70, 12, 15, queenEggs, 20, 
				hitOutput, tauntStandard);
		cadet = new Enemy(10, "Cadet", 30, 10, 
				110, surgeryKit, 10, hitOutput, tauntHide);
		beatle = new Enemy(11, "Rock Beatle", 35, 12, 10, stimpak, 6, 
				hitOutput, tauntStandard);
		ant = new Enemy(12, "Really Big Ant", 35, 12, 10, bandAid, 6, 
				hitOutput, tauntStandard);
		centipede = new Enemy(13, "Centipede", 35, 12, 10, morphine, 6, 
				hitOutput, tauntStandard);
		bee = new Enemy(14, "Large Africanized Killer Bee", 35, 12, 10, 
				potion, 6, hitOutput, tauntStandard);
		housefly = new Enemy(15, "Annoying HouseFly", 35, 12, 10, stimpak,
				6, hitOutput, tauntStandard);
		bedBug = new Enemy(16, "Bed Bug", 35, 12, 10, bandAid,
				6, hitOutput, tauntStandard);
		mosquito = new Enemy(17, "Swarm of Mosquitos", 35, 12, 10, stimpak,
				6, hitOutput, tauntStandard);

		
		// set up test player 1
		inv = new Inventory();
		currentPlayer = new Player("Test Player", 7, 65, 65, 20, 8, 10, false, false, false, false, inv);
		inv.setOwner(currentPlayer);

		// set up test player 2
		invNotFound = new Inventory(null, null, null, null, 0);
		playerInvNotFound = new Player("Test Player 2", 0, 0, 0, 0, 0, 0, 
				false, false, false, false, invNotFound);
		invNotFound.setOwner(playerInvNotFound);
	}




	/**
	 * Method: main
	 * runs game
	 *  @param args
	 */
	public static void main(String[] args)
	{
		GameController gameCont = new GameController();
		gameCont.mainMenu();
	}
}
