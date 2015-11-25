package UserInteraction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Inventory.*;
import Obstacle.*;

/*********************************************************************
 * GameController class. Facilitates game construction,
 * user interaction with game objects, saving, loading.
 * 
 * @author Dale Burke
 * @author John-Paul Sprouse
 * @author Danyelle Noortajalli
 * @author Ethan Patterson
 * 
 * @version November 2015
 *********************************************************************/
public class GameController
{
	/** game command list */
	private String helpList;

	/** file to be loaded, for use with save/load methods */
	private String gameFile = null;

	/** current game's player */
	private Player currentPlayer = null;

	/** current game's player location */
	private Room currentRoom = null;

	/** inventory used with currentPlayer */
	private Inventory inv = null;

	/** lists for use with serialization */
	private List<Item> itemList = null;
	private List<Enemy> enemyList = null;
	private List<Puzzle> puzzleList = null;
	private List<Room> roomList = null;

	/** scanner to read user input */
	private Scanner scanner = new Scanner(System.in);

	/** random number generator */
	private Random random = new Random();

	/** for use with serialization and de-serialization of files */
	private FileInputStream fileReader;
	private FileOutputStream fileWriter;
	private ObjectInputStream deserializer; 
	private ObjectOutputStream serializer;

	/** game items */
	Tablet tablet;
	Suit suit;
	Oxygen oxygen;
	Accessory academy, writ, goldStar, buggerMask, launchie, salamander, dragon,
	admiralsCrest, hat;
	Weapon fisticuffs, bluntObject, laserPistol, dualLaser, theBird, lightSaber;
	Consumable bandAid,	morphine, potion, stimpak, surgeryKit, phoenixDown, queenEggs;

	/** enemy taunt text arrays */
	String[] bluntOutput, hitOutput, laserOutput, fistOutput, birdOutput, saberOutput, 
	tauntFlee, tauntEnrage, tauntHide, tauntConcentration, tauntStandard, bugOutput, 
	tauntBug, bzzOutput;

	/** game enemies */
	Enemy mazer, jerry, peter, dissenter, droid, bonzo, hyrum, vader, queen, 
	beatle, cadet, bullies, ant, centipede, bee, housefly, mosquito, bedBug;

	/** game puzzles */
	Puzzle wombPuzzle, cribPuzzle, namePuzzle, mathPuzzle, sciencePuzzle, historyPuzzle,
	bullyPuzzle, spaceshipPuzzle, giantPuzzle, gunPuzzle, battlePuzzle, preliminaryPuzzle, genocidePuzzle;

	/** game rooms */
	Room womb, deliveryRoom, crib, livingRoom, orrientation, battleStrategyClass,
	scienceClass, mathClass, hallway, infirmary, bedroom, livingRoom2, spaceship,
	bunkroomL, combatArena, strategyClass, bunkroomL2, hallwayS, bunkroomS, combatArena2,
	bunkroomD, combatArena3, shower, cabin, sleepingQuarters, battleSimulatorRoom, sleepingQuarters2,
	battleSimulatorRoom2, commandRoom, airlock, outside, formicCastle;

	/*********************************************************************
	Default constructor method for GameController.

	@param none
	@return none
	 *********************************************************************/
	public GameController()
	{
		// null
	}

	/*********************************************************************
	Method for main menu output. Interacts with user to determine game
	startup.

	@param none
	@return none
	 *********************************************************************/
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
					"/_____/  /_____/  /_____/  /_____/  /_____/  /_____/  /_____/     \n\n\n\n" +
					"Welcome to Ender's Reign: Wiggin's Formic Rage! \nWould you like to:" +
					"\n> Start New Game \n> Load Game \n> Exit");
		String input = scanner.nextLine();

		while (!input.equalsIgnoreCase("new")
					&& !input.equalsIgnoreCase("load")
					&& !input.equalsIgnoreCase("exit"))
		{
			System.out.println("Your input did not match available options." +
						"\n Please type \"New\", \"Load\", or \"Exit\"");
			input = scanner.nextLine();
		}

		if (input.toLowerCase().contains("new"))
		{
			startNewGame();
		}
		else if (input.toLowerCase().contains("load"))
		{
			loadGame();
		}
		else if (input.toLowerCase().contains("exit"))
		{
			System.exit(0);
		}
	}

	/*********************************************************************
	Method for starting a new game. Loads default game state.

	@param none
	@return none
	 *********************************************************************/
	public void startNewGame()
	{	
		try
		{
			gameFile = "DEFAULT.dat";
			fileReader = new FileInputStream(gameFile);
			deserializer = new ObjectInputStream(fileReader);
			loadObjects();
			listener();
			// TODO requires some logic to start game
		}
		catch(IOException e)
		{
			System.out.println("ERROR - COULD NOT START NEW GAME");
		}
	}

	/*********************************************************************
	Method for loading a specified game state. User specifies state 1, 2,
	or 3 and file is subsequently loaded to GameController.

	@param none
	@return none
	 *********************************************************************/
	public void loadGame()
	{
		int input = -1;
		boolean errorFlag = false;
		System.out.println("Please select a save file to load. (Enter a value 1-3, or 0 to cancel)\n> ");
		do
		{
			try
			{
				input = Integer.parseInt(scanner.nextLine());
			}
			catch (Exception e)
			{
				input = -1;
				errorFlag = true;
			}

			if (input == 0)
			{
				return;
			}
			else if (input == 1)
			{
				gameFile = "game1.dat";
				errorFlag = false;
			}
			else if (input == 2)
			{
				gameFile = "game2.dat";	
				errorFlag = false;		
			}
			else if (input == 3)
			{
				gameFile = "game3.dat";	
				errorFlag = false;		
			}
			else
			{
				System.out.println("Valid value not detected; please try again. (Enter a value 1-3, or 0 to cancel)\n> ");
				errorFlag = true;
			}
		}
		while(errorFlag);

		// TODO game loaded message, cancellation procedure

		try
		{
			fileReader = new FileInputStream(gameFile);
			deserializer = new ObjectInputStream(fileReader);
			loadObjects();
			listener();
			// TODO requires some logic to start game
		}
		catch (Exception e)
		{
			System.out.println("ERROR - COULD NOT READ FROM FILE\n");
			loadGame();
		}
	}

	/*********************************************************************
	Method for loading a game state from file specified by gameFile.

	@param none
	@return none
	 *********************************************************************/
	public void loadObjects()
	{
		try 
		{
			currentPlayer = (Player) deserializer.readObject();
			currentRoom = (Room) deserializer.readObject();
			itemList = (List<Item>) deserializer.readObject();
			enemyList = (List<Enemy>) deserializer.readObject();
			puzzleList = (List<Puzzle>) deserializer.readObject();
			roomList = (List<Room>) deserializer.readObject();
			loadLists();
		} 
		catch (Exception e) 
		{
			System.out.println("ERROR - COULD NOT LOAD OBJECTS FROM FILE");
			e.printStackTrace();
		}
	}

	/*********************************************************************
	Helper method for loading object lists into appropriate variables.

	@param none
	@return none
	 *********************************************************************/
	private void loadLists()
	{
		// item section
		tablet = (Tablet) itemList.get(0);
		suit = (Suit) itemList.get(1);
		oxygen = (Oxygen) itemList.get(2);
		academy = (Accessory) itemList.get(3);
		writ = (Accessory) itemList.get(4);
		goldStar = (Accessory) itemList.get(5);
		buggerMask = (Accessory) itemList.get(6);
		launchie = (Accessory) itemList.get(7);
		salamander = (Accessory) itemList.get(8);
		dragon = (Accessory) itemList.get(9);
		admiralsCrest = (Accessory) itemList.get(10);
		hat = (Accessory) itemList.get(11);
		fisticuffs = (Weapon) itemList.get(12);
		bluntObject = (Weapon) itemList.get(13);
		laserPistol = (Weapon) itemList.get(14);
		dualLaser = (Weapon) itemList.get(15);
		theBird = (Weapon) itemList.get(16);
		lightSaber = (Weapon) itemList.get(17);
		bandAid = (Consumable) itemList.get(18);
		morphine = (Consumable) itemList.get(19);
		potion = (Consumable) itemList.get(20);
		stimpak = (Consumable) itemList.get(21);
		surgeryKit = (Consumable) itemList.get(22);
		phoenixDown = (Consumable) itemList.get(23);
		queenEggs = (Consumable) itemList.get(24);

		// enemy section
		mazer = enemyList.get(0);
		jerry = enemyList.get(1);
		peter = enemyList.get(2);
		dissenter = enemyList.get(3);
		droid = enemyList.get(4);
		bonzo = enemyList.get(5);
		hyrum = enemyList.get(6);
		vader = enemyList.get(7);
		queen = enemyList.get(8);
		beatle = enemyList.get(9);
		cadet = enemyList.get(10);
		bullies = enemyList.get(11);
		ant = enemyList.get(12);
		centipede = enemyList.get(13);
		bee = enemyList.get(14);
		housefly = enemyList.get(15);
		mosquito = enemyList.get(16);
		bedBug = enemyList.get(17);

		// puzzle section
		wombPuzzle = puzzleList.get(0);
		cribPuzzle = puzzleList.get(1);
		namePuzzle = puzzleList.get(2);
		mathPuzzle = puzzleList.get(3);
		sciencePuzzle = puzzleList.get(4);
		historyPuzzle = puzzleList.get(5);
		bullyPuzzle = puzzleList.get(6);
		spaceshipPuzzle = puzzleList.get(7);
		giantPuzzle = puzzleList.get(8);
		gunPuzzle = puzzleList.get(9);
		battlePuzzle = puzzleList.get(10);
		preliminaryPuzzle = puzzleList.get(11);
		genocidePuzzle = puzzleList.get(12);

		// room section
		womb = roomList.get(0);
		deliveryRoom = roomList.get(1);
		crib = roomList.get(2);
		livingRoom = roomList.get(3);
		orrientation = roomList.get(4);
		battleStrategyClass = roomList.get(5);
		scienceClass = roomList.get(6);
		mathClass = roomList.get(7);
		hallway = roomList.get(8);
		infirmary = roomList.get(9);
		bedroom = roomList.get(10);
		livingRoom2 = roomList.get(11);
		spaceship = roomList.get(12);
		bunkroomL = roomList.get(13);
		combatArena = roomList.get(14);
		strategyClass = roomList.get(15);
		bunkroomL2 = roomList.get(16);
		hallwayS = roomList.get(17);
		bunkroomS = roomList.get(18);
		combatArena2 = roomList.get(19);
		bunkroomD = roomList.get(20);
		combatArena3 = roomList.get(21);
		shower = roomList.get(22);
		cabin = roomList.get(23);
		sleepingQuarters = roomList.get(24);
		battleSimulatorRoom = roomList.get(25);
		sleepingQuarters2 = roomList.get(26);
		battleSimulatorRoom2 = roomList.get(27);
		commandRoom = roomList.get(28);
		airlock = roomList.get(29);
		outside = roomList.get(30);
		formicCastle = roomList.get(31);
	}

	public void saveGame()
	{
		int input = -1;
		boolean errorFlag = false;
		System.out.println("Please select a save slot to use. (Enter a value 1-3, or 0 to cancel)\n> ");
		do
		{
			try
			{
				input = Integer.parseInt(scanner.nextLine());
			}
			catch (Exception e)
			{
				input = -1;
				errorFlag = true;
			}

			if (input == 0)
			{
				return;
			}
			else if (input == 1)
			{
				gameFile = "game1.dat";
				errorFlag = false;
			}
			else if (input == 2)
			{
				gameFile = "game2.dat";	
				errorFlag = false;		
			}
			else if (input == 3)
			{
				gameFile = "game3.dat";	
				errorFlag = false;		
			}
			else
			{
				System.out.println("Valid value not detected; please try again. (Enter a value 1-3, or 0 to cancel)\n> ");
				errorFlag = true;
			}
		}
		while(errorFlag);

		// TODO save confirmation message, cancellation procedure

		try
		{
			fileWriter = new FileOutputStream(gameFile);
			serializer = new ObjectOutputStream(fileWriter);
			saveObjects();
		}
		catch (Exception e)
		{
			System.out.println("ERROR - COULD NOT SAVE TO FILE\n");
			saveGame();
		}
	}

	/*********************************************************************
	Method for saving the current game state to a specified file.

	@param none
	@return none
	 *********************************************************************/
	public void saveObjects()
	{
		try
		{
			saveLists();
			serializer.writeObject(currentPlayer);
			serializer.writeObject(currentRoom);
			serializer.writeObject(itemList);
			serializer.writeObject(enemyList);
			serializer.writeObject(puzzleList);
			serializer.writeObject(roomList);
			serializer.flush();
		}
		catch(Exception e)
		{
			System.out.println("ERROR - COULD NOT SAVE OBJECTS TO FILE");
			e.printStackTrace();
		}
	}

	/*********************************************************************
	Helper method for saving object lists into appropriate lists.

	@param none
	@return none
	 *********************************************************************/
	private void saveLists()
	{
		// item section
		itemList = new ArrayList<Item>();
		itemList.add(tablet);
		itemList.add(suit);
		itemList.add(oxygen);
		itemList.add(academy);
		itemList.add(writ);
		itemList.add(goldStar);
		itemList.add(buggerMask);
		itemList.add(launchie);
		itemList.add(salamander);
		itemList.add(dragon);
		itemList.add(admiralsCrest);
		itemList.add(hat);
		itemList.add(fisticuffs);
		itemList.add(bluntObject);
		itemList.add(laserPistol);
		itemList.add(dualLaser);
		itemList.add(theBird);
		itemList.add(lightSaber);
		itemList.add(bandAid);
		itemList.add(morphine);
		itemList.add(potion);
		itemList.add(stimpak);
		itemList.add(surgeryKit);
		itemList.add(phoenixDown);
		itemList.add(queenEggs);

		// enemy section
		enemyList = new ArrayList<Enemy>();
		enemyList.add(mazer);
		enemyList.add(jerry);
		enemyList.add(peter);
		enemyList.add(dissenter);
		enemyList.add(droid);
		enemyList.add(bonzo);
		enemyList.add(hyrum);
		enemyList.add(vader);
		enemyList.add(queen);
		enemyList.add(beatle);
		enemyList.add(cadet);
		enemyList.add(bullies);
		enemyList.add(ant);
		enemyList.add(centipede);
		enemyList.add(bee);
		enemyList.add(housefly);
		enemyList.add(mosquito);
		enemyList.add(bedBug);

		// puzzle section
		puzzleList.add(wombPuzzle);
		puzzleList.add(cribPuzzle);
		puzzleList.add(namePuzzle);
		puzzleList.add(mathPuzzle);
		puzzleList.add(sciencePuzzle);
		puzzleList.add(historyPuzzle);
		puzzleList.add(bullyPuzzle);
		puzzleList.add(spaceshipPuzzle);
		puzzleList.add(giantPuzzle);
		puzzleList.add(gunPuzzle);
		puzzleList.add(battlePuzzle);
		puzzleList.add(preliminaryPuzzle);
		puzzleList.add(genocidePuzzle);

		// room section
		roomList.add(womb);
		roomList.add(deliveryRoom);
		roomList.add(crib);
		roomList.add(livingRoom);
		roomList.add(orrientation);
		roomList.add(battleStrategyClass);
		roomList.add(scienceClass);
		roomList.add(mathClass);
		roomList.add(hallway);
		roomList.add(infirmary);
		roomList.add(bedroom);
		roomList.add(livingRoom2);
		roomList.add(spaceship);
		roomList.add(bunkroomL);
		roomList.add(combatArena);
		roomList.add(strategyClass);
		roomList.add(bunkroomL2);
		roomList.add(hallwayS);
		roomList.add(bunkroomS);
		roomList.add(combatArena2);
		roomList.add(bunkroomD);
		roomList.add(combatArena3);
		roomList.add(shower);
		roomList.add(cabin);
		roomList.add(sleepingQuarters);
		roomList.add(battleSimulatorRoom);
		roomList.add(sleepingQuarters2);
		roomList.add(battleSimulatorRoom2);
		roomList.add(commandRoom);
		roomList.add(airlock);
		roomList.add(outside);
		roomList.add(formicCastle);
	}

	/*********************************************************************
	Method for receiving and directing player input.

	@param none
	@return none
	 *********************************************************************/
	public void listener() 
	{
		while(true)
		{
			System.out.print("> ");
			String input = scanner.next();
			switch (input.toLowerCase()) {
			case "w":
				move(0);
				break;
			case "s":
				move(1);
				break;
			case "a":
				move(3);
				break;
			case "d":
				move(2);
				break;
			case "h":
				displayHelp();
				break;
			case "i":
				currentPlayer.getPlayerInventory().useItem();
				break;
			case "l":
				System.out.println(currentRoom.getRoomDescription(1));
			case "1":
				saveGame();
				break;
			case "2":
				loadGame();
				break;
			case "0":
				//exit 
				break;
			default:
				System.out.println("Command not recognized.");
				break;
			}
		}
	}

	/*********************************************************************
	Method for moving between rooms. Accepts an integer corresponding to
	an index position of the room's roomExits[].

	@param int direction - Index position of currentRoom roomExits[] to
						   move to.
	@return none
	 *********************************************************************/
	public void move(int direction)
	{
		boolean hasMoved = false;
		boolean monsterEncountered = false;
		String dir = "nowhere.";

		// set direction string element
		if(direction == 0)
		{
			dir = "north.";
		}
		else if(direction == 1)
		{
			dir = "south.";
		}
		else if(direction == 2)
		{
			dir = "west.";
		}
		else if(direction == 3)
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

	/*********************************************************************
	Method for displaying the game's helpList String.

	@param none
	@return none
	 *********************************************************************/
	public void displayHelp()
	{
		System.out.println(helpList);
	}

	/*********************************************************************
	Method for initializing all game objects.

	@param none
	@return none
	 *********************************************************************/
	public void constructGame()
	{

		hitOutput = new String [] {"You barely grazed doe.", "You scored a major hit on doe.", 
				"You landed a solid strike on doe.", "You whacked the crap out of doe."};
		bluntOutput = new String [] {"You tapped doe.", "You whacked doe.", "You smashed doe's head.",
				"You bludgeoned doe's face in."};	
		laserOutput = new String [] {"You just grazed doe", "You beamed doe in the leg", 
				"You zapped doe in the chest", "You zapped doe square in the forehead."};
		fistOutput = new String [] {"You jabbed at doe and just clipped his shoulder", 
				"You socked doe.", "You gut-punched doe.", "You decked em right in the kisser, poor doe"};
		birdOutput = new String [] {"You shot the bird but doe barely noticed", "You gave the finger to doe.",
				"You flicked off doe", "You shot doe the double bird."};
		saberOutput = new String [] {"You poked doe with a lightsaber.", 
				"You swing your laser sword at doe and just managed to remove his eyebrows.",
				"You cut off one of doe/'s appendages.", "You scalped doe"};
		bugOutput = new String [] {"The You pesters doe.", 
				"You sneaks up on you and bites you on the back of the neck.", 
				"You crawls up your arm and it feels all ichy.", 
				"You crawls in your ear and lays eggs in your brain."};
		bzzOutput = new String [] {"\"Bzzzz, Bzzz\" says the You.", 
				"You flys in close and darts off at the last moment.  /n In an attempt to smack it you manage"
				+ " to slap yourself across the face.", "You stings you in the eyeball."};

		
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
		
		tauntBug = new String [] {"z", "0", " ", " ", 
				"You threaten to stomp ",
				"You wave your arms wildly in the air.",
				"You buzz mockingly ",
				" is a bug, thus is impervious to your misguided attempts at psychological warfare.",
				"says \"bzzzz\".",
				" uses this as an opportunity to take a free hit on you." };


		
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
		fisticuffs = new Weapon("Fisticuffs", "Have at thee, coward!", false, 1,fistOutput);
		bluntObject = new Weapon("Blunt Object", "No, not that kind of \"blunt.\"", false, 2,hitOutput);
		laserPistol = new Weapon("Laser Pistol", "Cautionary note reads: \"NOT FOR ACTUAL USE.\"", false, 0, laserOutput);
		dualLaser = new Weapon("Dual Laser Pistols", "What's 0 times 2? Because that's how much damage you'll"
				+ " be doing with these.", false, 0, laserOutput);
		theBird = new Weapon("The Bird", "Mazer's weapon of choice, used to devastating effect.", false, 3, birdOutput);
		lightSaber = new Weapon("Lightsaber", "Vader's lightsaber. You should be ticketed for all the physical"
				+ " laws you're violating with this.", false, 5, saberOutput);

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
					10, writ, 10, bluntOutput, tauntFlee);
		peter = new Enemy(02, "Peter", 40, 8, 10, bluntObject, 10, hitOutput,
					tauntHide);
		dissenter = new Enemy(03, "Dissenter", 40, 10, 10, fisticuffs, 10, 
					fistOutput, tauntStandard);
		droid = new Enemy(04, "Hand-to-Hand Combat Droid", 50, 10, 10, 
					morphine, 10, hitOutput, tauntConcentration);
		bonzo = new Enemy(05, "Bonzo and two of his buddies", 55, 25, 10,
					laserPistol, 10, bluntOutput, tauntFlee);
		mazer = new Enemy(06, "Mazer Rackham", 70, 14, 
					10, theBird, 10, birdOutput, tauntEnrage);
		hyrum = new Enemy(07, "Colonel Hyrum Graff", 55, 12, 12, hat, 10, 
					laserOutput, tauntStandard);
		vader = new Enemy(8, "Darth Vader", 65, 17, 12, lightSaber, 20, 
					saberOutput, tauntStandard);
		queen = new Enemy(9, "Formic Queen", 70, 11, 15, queenEggs, 20, 
					hitOutput, tauntStandard);
		cadet = new Enemy(10, "Cadet", 30, 10, 
					110, surgeryKit, 10, laserOutput, tauntHide);
		beatle = new Enemy(11, "Rock Beatle", 35, 7, 10, stimpak, 6, 
					bugOutput, tauntBug);
		ant = new Enemy(12, "Really Big Ant", 35, 10, 10, bandAid, 6, 
					bugOutput, tauntBug);
		centipede = new Enemy(13, "Centipede", 35, 6, 10, morphine, 6, 
					bugOutput, tauntBug);
		bee = new Enemy(14, "Large Africanized Killer Bee", 35, 12, 19, 
					potion, 6, bzzOutput, tauntBug);
		housefly = new Enemy(15, "Annoying HouseFly", 35, 8, 19, stimpak,
					6, bzzOutput, tauntBug);
		bedBug = new Enemy(16, "Bed Bug", 35, 12, 8, bandAid,
					6, bugOutput, tauntBug);
		mosquito = new Enemy(17, "Swarm of Mosquitos", 35, 8, 17, stimpak,
					6, bzzOutput, tauntBug);

		
		//puzzles
		String[][] wombPuzzle = new String[3][5];

		wombPuzzle[0][0] = "'You are in the womb and the umbilical cord is wrapped around your neck. How do you escape?'\n";
		wombPuzzle[0][1] = "You can either: \n'a' \tStruggle to get free \n'b' \tKeep as still as possible \n'c' \tEat your way out\n";
		wombPuzzle[0][2] = "\n'You escape the clutches of death...even though technically you weren't born yet'\n";
		wombPuzzle[0][3] = "\n'You are stillborn'\n";
		wombPuzzle[0][4] = "b";

		String[][] cribPuzzle = new String[3][5];

		cribPuzzle[0][0] = "'After a few months have passed, you have grown to the point where your crib has become too small to sleep in. In order to find better sleeping arrangement, you must convince you parents you are too big for the crib by escaping it. How will you go about doing this?'\n";
		cribPuzzle[0][1] = "Would you: \n'a' \tClimb up the sides \n'b' \tRock the cradle \n'c' \tEndlessly scream until relocated\n";
		cribPuzzle[0][2] = "\n'Very good!'\n";
		cribPuzzle[0][3] = "\n'Not quite'\n";
		cribPuzzle[0][4] = "a";

		String[][] namePuzzle = new String[3][5];

		namePuzzle[0][0] = "'Upon frEeing yourself of the coNfinement of the crib, you Discover a namE inscRibed into the wood on the side of the crib. You presume that the name has to be yours, what is it?'\n";
		namePuzzle[0][1] = "Options: \n'a' \tDaren \n'b' \tEnder \n'c' \tErnie\n";
		namePuzzle[0][2] = "\n'I sense something very special about you Ender, but you still have much to learn at the Academy!'\n";
		namePuzzle[0][3] = "\n'You must search deeper within yourself'\n";
		namePuzzle[0][4] = "b";

		String[][] mathPuzzle = new String[3][5];

		mathPuzzle[0][0] = "'During one of your math lessons you come across a mind boggling problem: Two fathers and two sons sat down to eat eggs for breakfast. They ate exactly three eggs, each person had an egg. How do you explain this?'\n";
		mathPuzzle[0][1] = "Options: \n'a' \tThey split the eggs into multiple parts \n'b' \tThe 'sons' are conjoined twins \n'c' \tOne of the 'fathers' is also a grandfather\n";
		mathPuzzle[0][2] = "\n'Way to think logically!'\n";
		mathPuzzle[0][3] = "\n'Ok so math might not be your forte…'\n";
		mathPuzzle[0][4] = "c";

		String[][] sciencePuzzle = new String[3][5];

		sciencePuzzle[0][0] = "'Science class is proving to be no walk in the park either, and one question in particular seems to keep tripping you up: If you boiled some ice in a hot frying pan, would it become a gas or a liquid?'\n";
		sciencePuzzle[0][1] = "Options: \n'a' \tLiquid \n'b' \tBoth \n'c' \tGas\n";
		sciencePuzzle[0][2] = "\n'Yes! It melts and becomes a liquid. Then the liquid gets hot and evaporates into a vapor, which is a gas!'\n";
		sciencePuzzle[0][3] = "\n'Well at least you can match your socks'\n";
		sciencePuzzle[0][4] = "b";

		String[][] historyPuzzle = new String[3][5];

		historyPuzzle[0][0] = "'Although history class is relatively simple, it can be arduous at times: What walks on four feet in the morning, two in the afternoon and three at night?'\n";
		historyPuzzle[0][1] = "Options: \n'a' \tA Man \n'b' \tA Formic \n'c' \tA Monkey\n";
		historyPuzzle[0][2] = "\n'A wise choice! as an infant, he crawls on all fours; as an adult, he walks on two legs and; in old age, he uses a 'walking' stick.'\n";
		historyPuzzle[0][3] = "\n'It's better to focus on the future instead of the past anyway'\n";
		historyPuzzle[0][4] = "a";

		String[][] bullyPuzzle = new String[3][5];

		bullyPuzzle[0][0] = "'After the incident with Stilson(the bully) in science class, Colonel Graff and Major Anderson inquire as to the reason why you retaliated the way you did. With you on the verge of being kicked out of the academy, how would you explain your actions?'\n";
		bullyPuzzle[0][1] = "Options: \n'a' \tIt was purely self-defense \n'b' \tHe deserved it \n'c' \tTo end all future fights\n";
		bullyPuzzle[0][2] = "\n'A very effective battle tactic, Battle School can use more minds like yours. Congratulations!'\n";
		bullyPuzzle[0][3] = "\n'That is not tactic we are looking for in our future leaders'\n";
		bullyPuzzle[0][4] = "c";

		String[][] spaceshipPuzzle = new String[3][5];

		spaceshipPuzzle[0][0] = "'A fellow cadet pukes on the spaceship during the flight to battle school, how do you avoid getting enveloped by this disgusting substance?'\n";
		spaceshipPuzzle[0][1] = "Options: \n'a' \tLean/Dodge Left \n'b' \tSit there and take it like a real man \n'c' \tLean/Dodge Right\n";
		spaceshipPuzzle[0][2] = "\n'You avoided a very unpleasant situation'\n";
		spaceshipPuzzle[0][3] = "\n'That's just nasty, I wouldn't want to be you right now'\n";
		spaceshipPuzzle[0][4] = "a";

		String[][] giantPuzzle = new String[3][5];

		giantPuzzle[0][0] = "'You are now a mouse and encounter a giant barring your way. He presents you with two chalices in order to get past him, which one do you choose?'\n";
		giantPuzzle[0][1] = "Options: \n'a' \tThe Left Chalice \n'b' \tThe Right Chalice \n'c' \tAttack the Giant\n";
		giantPuzzle[0][2] = "\n'Congratulations, you have successfully murdered the giant by crawling up his arm and dismantling his brain though his eye socket'\n";
		giantPuzzle[0][3] = "\n'Sorry, wrong choice'\n";
		giantPuzzle[0][4] = "c";

		String[][] gunPuzzle = new String[3][5];

		gunPuzzle[0][0] = "'You need to know how to assemble your laser pistol if you want to be a part of the Salamanders. How would you go about constructing it?'\n";
		gunPuzzle[0][1] = "Options: \n'a' \t1. Crystal, 2. Aluminum Cylinder, 3. Barrel, 4. Magazine \n'b' \t1. Aluminum Cylinder, 2. Barrel, 3. Crystal, 4. Magazine \n'c' \t1. Magazine, 2. Barrel, 3. Aluminum Cylinder, 4. Crystal\n";
		gunPuzzle[0][2] = "\n'Welcome to the Salamander Squad!'\n";
		gunPuzzle[0][3] = "\n'You might need a bit more training'\n";
		gunPuzzle[0][4] = "a";

		String[][] battlePuzzle = new String[3][5];

		battlePuzzle[0][0] = "'You are now the new leader of the Dragon squad and are currently engaged in a battle simulator against your old squad (the Salamanders). In order to defeat your adversary and advance to command school, you must reach their gate by choosing the correct formation. Which formation will lead you to victory?'\n";
		battlePuzzle[0][1] = "Options: \n'a' \tArrowhead Formation \n'b' \tBunch Formation \n'c' \tCrossfire Formation\n";
		battlePuzzle[0][2] = "\n'We are proud to accept you into Command School!'\n";
		battlePuzzle[0][3] = "\n'TODO'\n";
		battlePuzzle[0][4] = "b";

		String[][] preliminaryPuzzle = new String[3][5];

		preliminaryPuzzle[0][0] = "'TODO'\n";
		preliminaryPuzzle[0][1] = "Options: \n'a' \tTODO \n'b' \tTODO \n'c' \tTODO\n";
		preliminaryPuzzle[0][2] = "\n'TODO'\n";
		preliminaryPuzzle[0][3] = "\n'TODO'\n";
		preliminaryPuzzle[0][4] = "b";

		String[][] genocidePuzzle = new String[3][5];

		genocidePuzzle[0][0] = "'TODO'\n";
		genocidePuzzle[0][1] = "Options: \n'a' \tTODO \n'b' \tTODO \n'c' \tTODO\n";
		genocidePuzzle[0][2] = "\n'TODO'\n";
		genocidePuzzle[0][3] = "\n'TODO'\n";
		genocidePuzzle[0][4] = "b";

		// set up test player 1
		Inventory inv = new Inventory();
		currentPlayer = new Player("Test Player", 7, 65, 65, 20, 8, 10, false, false, false, false, inv);
	}

	/*********************************************************************
	Main method for running GameController.

	@param String[] args - General main method argument.
	@return none
	 *********************************************************************/
	public static void main(String[] args)
	{
		GameController gameCont = new GameController();
		gameCont.mainMenu();
	}
}
