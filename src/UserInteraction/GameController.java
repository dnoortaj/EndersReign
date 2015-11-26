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
	Puzzle wombPuzzle, cribPuzzle, namePuzzle, mathPuzzle, sciencePuzzle, battleStrategyPuzzle,
	bullyPuzzle, spaceshipPuzzle, giantPuzzle, gunPuzzle, battlePuzzle, preliminaryPuzzle, genocidePuzzle;

	/** game rooms */
	Room womb, deliveryRoom, crib, livingRoom, orientation, hallway, battleStrategyClass,
	scienceClass, mathClass, hallway2, infirmary, bedroom, livingRoom2, spaceship,
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
			
			// TODO game startup text for new file
			System.out.println("TODO");
			System.out.println(currentRoom.getRoomDescription(0));
			
			// TODO setup solve puzzle for initial room
			currentRoom.getRoomPuzzle().solvePuzzle();
			listener();
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
		battleStrategyPuzzle = puzzleList.get(5);
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
		orientation = roomList.get(4);
		hallway = roomList.get(5);
		battleStrategyClass = roomList.get(6);
		scienceClass = roomList.get(7);
		mathClass = roomList.get(8);
		hallway2 = roomList.get(9);
		infirmary = roomList.get(10);
		bedroom = roomList.get(11);
		livingRoom2 = roomList.get(12);
		spaceship = roomList.get(13);
		bunkroomL = roomList.get(14);
		combatArena = roomList.get(15);
		strategyClass = roomList.get(16);
		bunkroomL2 = roomList.get(17);
		hallwayS = roomList.get(18);
		bunkroomS = roomList.get(19);
		combatArena2 = roomList.get(20);
		bunkroomD = roomList.get(21);
		combatArena3 = roomList.get(22);
		shower = roomList.get(23);
		cabin = roomList.get(24);
		sleepingQuarters = roomList.get(25);
		battleSimulatorRoom = roomList.get(26);
		sleepingQuarters2 = roomList.get(27);
		battleSimulatorRoom2 = roomList.get(28);
		commandRoom = roomList.get(29);
		airlock = roomList.get(30);
		outside = roomList.get(31);
		formicCastle = roomList.get(32);
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
		puzzleList.add(battleStrategyPuzzle);
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
		roomList.add(orientation);
		roomList.add(hallway);
		roomList.add(battleStrategyClass);
		roomList.add(scienceClass);
		roomList.add(mathClass);
		roomList.add(hallway2);
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
				break;
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

			if(currentRoom.getRoomEnemy() != null)
			{
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
			}

			if(currentRoom.getRoomPuzzle() != null && !monsterEncountered)
			{
				// attempt to trigger current room's puzzle if enemy was not encountered
				if(!currentRoom.getRoomPuzzle().getPuzzleIsCompleted())
				{
					if(random.nextInt(100) + 1 > currentRoom.getRoomEnemyChance())
					{
						// triggers the puzzle, adds outcome to player score
						currentPlayer.addToScore(currentRoom.getRoomPuzzle().solvePuzzle());

						// updates score
						System.out.println("Your score just increased by " + currentRoom.getRoomPuzzle().getPuzzlePoints()
								+ " points for a total of " + currentPlayer.getPlayerScore() + "!");

						// retrieves the room's puzzle reward and adds to current player inventory
						if(currentRoom.getRoomPuzzle().getPuzzleReward() != null)
						{
							currentPlayer.getPlayerInventory().addToInventory(currentRoom.getRoomPuzzle().getPuzzleReward());
						}
					}
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

		//Enemies
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


		//puzzles arrays
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
		mathPuzzle[0][3] = "\n'Ok so math might not be your forte�'\n";
		mathPuzzle[0][4] = "c";

		String[][] sciencePuzzle = new String[3][5];

		sciencePuzzle[0][0] = "'Science class is proving to be no walk in the park either, and one question in particular seems to keep tripping you up: If you boiled some ice in a hot frying pan, would it become a gas or a liquid?'\n";
		sciencePuzzle[0][1] = "Options: \n'a' \tLiquid \n'b' \tBoth \n'c' \tGas\n";
		sciencePuzzle[0][2] = "\n'Yes! It melts and becomes a liquid. Then the liquid gets hot and evaporates into a vapor, which is a gas!'\n";
		sciencePuzzle[0][3] = "\n'Well at least you can match your socks'\n";
		sciencePuzzle[0][4] = "b";

		String[][] battleStrategyPuzzle = new String[3][5];

		battleStrategyPuzzle[0][0] = "'Your professor presents a question: A formic appears 50 meters ahead of you. What do you do?'\n";
		battleStrategyPuzzle[0][1] = "Options: \n'a' \tRun out to greet it \n'b' \tSent entire fleet after it \n'c' \tDo nothing\n";
		battleStrategyPuzzle[0][2] = "\n'Brilliant! Now the formic is dead AND you've wasted resources, but good job!'\n";
		battleStrategyPuzzle[0][3] = "\n'You're not quite ready for field experience...'\n";
		battleStrategyPuzzle[0][4] = "b";

		String[][] bullyPuzzle = new String[3][5];

		bullyPuzzle[0][0] = "'After the incident with Jerry(the bully) in science class, Colonel Graff and Major Anderson inquire as to the reason why you retaliated the way you did. With you on the verge of being kicked out of the academy, how would you explain your actions?'\n";
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


		//Constructed puzzles
		this.wombPuzzle = new Puzzle(false, wombPuzzle, surgeryKit, 5);

		this.cribPuzzle = new Puzzle(false, cribPuzzle, bandAid, 5);

		this.namePuzzle = new Puzzle(false, namePuzzle, tablet, 5);

		this.mathPuzzle = new Puzzle(false, mathPuzzle, goldStar, 5);

		this.sciencePuzzle = new Puzzle(false, sciencePuzzle, goldStar, 5);

		this.battleStrategyPuzzle = new Puzzle(false, battleStrategyPuzzle, buggerMask, 5);

		this.bullyPuzzle = new Puzzle(false, bullyPuzzle, writ, 5);

		this.spaceshipPuzzle = new Puzzle(false, spaceshipPuzzle, launchie, 5);

		this.giantPuzzle = new Puzzle(false, giantPuzzle, laserPistol, 5);

		this.gunPuzzle = new Puzzle(false, gunPuzzle, salamander, 5);

		this.battlePuzzle = new Puzzle(false, battlePuzzle, dragon, 5);

		this.preliminaryPuzzle = new Puzzle(false, preliminaryPuzzle, hat, 5);

		this.genocidePuzzle = new Puzzle(false, genocidePuzzle, admiralsCrest, 5);


		// rooms
		womb = new Room("womb", new String[]{"Soon to be born into the world,"
			+ " you, little Ender, are within your mother's womb.", "Amniotic"
			+ " fluid surrounds you, a helpless little fetus."}, null, 100,
			this.wombPuzzle, 100);
		deliveryRoom = new Room("deliveryRoom", new String[]{"After escaping"
				+ " the womb, you cry as the doctor hands you over to your mother.",
				"You are surrounded by adults- nurses, doctors, creepers- oh wait. "
				+ "that's just your family watching to see your next move. You appear"
				+ " to be \"special\" in their eyes."}, null, 100, null, 100);
		crib = new Room("crib", new String[]{"Your family has brought you home and "
				+ "placed you in your crib.  You lay within these bars that confine you, "
				+ "your parents standing idly nearby","You listen closer and overhear them"
				+ " discussing your future. You are meant to be someone, Ender."}, null, 100,
				this.cribPuzzle, 100);
		livingRoom = new Room("livingRoom", new String[]{"As a toddler, you spend your "
				+ "free time in the living room utilizing your constant curiosity of the"
				+ " world around you to learn as much as you can as quickly as possible.",
				"Looking around, you see your brother and sister sitting on the couch reading"
				+ " books."}, null, 100, this.namePuzzle, 100);
		orientation = new Room("orientation", new String[]{"Welcome to the Academy."
				+ " They've installed a monitor in the back of your neck so they can watch your"
				+ " every move. That's not creepy at all.","Surrounding you are a handful of kids"
				+ " just like you- dressed the same, incredibly intelligent, all being monitored"
				+ " very closely."}, null, 100, null, 100);
		hallway = new Room("hallway", new String[]{"Just another brick in the wall. You silently"
				+ " stroll the crowded hallway between classes.","To your left is your math class,"
				+ " to your right is your science class, and all around you are some of the most"
				+ " intelligent kids in the entire country. Careful where you tread."}, null, 100,
				null, 100);
		scienceClass = new Room("scienceClass", new String[]{"Specimens galore and"
				+ " not a thing on the floor.  The science classroom is pristine.","There"
				+ " appear to be multiple pointy objects on desktops.  The classroom is quite"
				+ " normal otherwise."}, bullies, 100, this.sciencePuzzle, 100);
		mathClass = new Room("mathClass", new String[]{"Well, you miscalculated that move.  "
				+ "You are now in the math classroom.  The amount of safety infractions within "
				+ "this room are just adding up.","Minus the bright fluorescence, this math class "
				+ " is pointless. No one here. Just empty sets desks and chairs."}, null, 0,
				this.mathPuzzle, 100);
		hallway2 = new Room("hallway2", new String[]{"The hallway buzzes with the sound of meaningful "
				+ "conversation.","Students. Students everywhere. Battle Strategy class is to your left "
				+ "and the infirmary is to your right."}, null, 100, null, 100);
		battleStrategyClass = new Room("battleStrategyClass", new String[]{"Action time! Battle"
				+ " Strategy class has begun. You take your seat and prepare for today's quiz."
				,"Students around you scribble answers frantically on their quizzes."}, 
				null, 100, this.battleStrategyPuzzle, 100);
		infirmary = new Room("infirmary", new String[]{"Welcome to the infirmary! We've"
				+ " been expecting you...Now lay face down.  This won't hurt a bit.",
				"You feel a major twinge of pain as the nurse rips your monitor out "
				+ "of the back of your neck.  They are no longer watching you. You've "
				+ "been removed from the program and sent home."}, null, 100, null, 100);
		bedroom = new Room("bedroom", new String[]{"You've returned to your family. You "
				+ "enter your shared bedroom with your elder brother, Peter.","Your "
				+ "siblings question your release from the program, and Peter wishes to "
				+ "play a game of 'Formic Invader'"}, peter, 100, null, 100);
		livingRoom2 = new Room("livingRoom2", new String[]{"Colonel Hyrum Graff and "
				+ "Major Anderson came over to talk to you."," Everyone is gathered "
				+ "in the living room, watching as the Colonel begins to question you"},
				null, 100, this.bullyPuzzle, 100);
		spaceship = new Room("spaceship", new String[]{"You step forward and step foot "
				+ "onto the spaceshuttle, seeing all of the other new launchies headed "
				+ "to Battle School","You take your seat and buckle in for the flight."},
				null, 100, this.spaceshipPuzzle, 100);
		bunkroomL = new Room("bunkroomL", new String[]{"When you reach Battle School, "
				+ "everyone walks into the bunkroom.", "Just rows of bunks line either "
				+ "side of the room. The new cadets left you the one closest to the door"
				+ " and on the bottom bunk."}, null, 100, null, 100);
		combatArena = new Room("combatArena", new String[]{"You and your team get to "
				+ "experience the Combat Arena. Zero gravity!","You see barriers and "
				+ "you're tempted to give your gun a real test to see what it can do."},
				dissenter, 100, null, 100);
		strategyClass = new Room("strategyClass", new String[]{"You just walked into "
				+ "Strategy class. This is where you learn hand-to-hand combat.",
				"You see other cadets fighting the droids and one is open for you to fight."},
				droid, 100, null, 100);
		bunkroomL2 = new Room("bunkroomL2", new String[]{"You return to your bunk,"
				+ " exhausted from your day's events. You remove all of your gear "
				+ "and lay down in your bunk.","All of the other Launchies are in "
				+ "their respective bunks, and you lay here wide awake. Now would "
				+ "be a great time to see what your tablet can do."}, null, 100, 
				this.giantPuzzle, 100);
		hallwayS = new Room("hallwayS", new String[]{"After defeating the giant, "
				+ "Colonel Graff and Major Anderson promote you to the Salamander"
				+ " team. You meet your new team and Leader, Bonzo. He isn't too "
				+ "happy to have a new person on his team, so watch your back.","This "
				+ "hallway has all of your teammates playing a game of jacks. None"
				+ " too interested in getting to know you."}, null, 100, null, 100);
		bunkroomS = new Room("bunkroomS", new String[]{"You walk into your new bunkroom"
				+ " and it resembles your old one except now you have this cool "
				+ "salamander on your uniforms.","Bunkbeds. Bunkbeds everywhere. "
				+ "It appears that this team likes to play 'Rebuild your gun as "
				+ "fast as you can'. Bonzo is the champion at this game!"}, null, 
				100, this.gunPuzzle, 100);
		combatArena2 = new Room("combatArena2", new String[]{"Time to battle the "
				+ "Leopards! You and your team begin to enter the combat arena, "
				+ "but Bonzo stops you.'You stay here. You aren't going to get in "
				+ "my way. We are undefeated for a reason. We don't need a Launchie"
				+ " like you'","You decide to step foot into the combat arena in "
				+ "order to protect a teammate that doesn't see what she's about to "
				+ "float into."}, cadet, 100, null, 100);
		bunkroomD = new Room("bunkroomD", new String[]{"Colonel Graff was watching"
				+ " and saw what you did.  He thinks your strategy skills are "
				+ "wonderful and you're ready to run your own team! Welcome to being "
				+ "the leader of the Dragons.","You see a few cadets you recognize "
				+ "and a few new ones, but they're all ready to follow your lead."}, 
				null, 100, null, 100);
		combatArena3 = new Room("combatArena3", new String[]{"They're changing the "
				+ "rules!! Now it's your team versus Salamanders and Leopards! What "
				+ "ever will you do to defeat all of these people?","For some reason "
				+ "the gate already open to the arena when you arrive.. This is very "
				+ "suspicious."}, null, 100, this.battlePuzzle, 100);
		shower = new Room("shower", new String[]{"You defeated Bonzo! And now it's time"
				+ " to clean off all that sweat and grime from that stressful battle.",
				"There are just rows of showers in this room, covered in tile from floor"
				+ " to ceiling."}, bonzo, 100, null, 100);
		cabin = new Room("cabin", new String[]{"Tired of all the games everyone was"
				+ " playing, you quit being a cadet because they wouldn't let you talk"
				+ " to your sister. So here you are, in a cabin in the woods on your own..."
				+ "Somehow you can afford this. It makes total sense that a 13 year old is "
				+ "living out on his own.","There's a nice lake here and you choose to sit"
				+ " on the dock and just think for a while."}, null, 100, null, 100);
		sleepingQuarters = new Room("sleepingQuarters", new String[]{"They convinced you to"
				+ " go to the forward outpost for Command School. So, here's your sleeping"
				+ " quarters.","There is a strange man with tattoos all over his face just"
				+ " sitting in the middle of your room as if he's meditating."}, mazer, 
				100, null, 100);
		battleSimulatorRoom = new Room("battleSimulatorRoom", new String[]{"This is the battle"
				+ " simulation room. They want to see how you perform leading a fleet against "
				+ "the Formics.","You have a team, each in charge of part of the fleet, all "
				+ "awaiting your instructions."},null, 100, this.preliminaryPuzzle, 100);
		sleepingQuarters2 = new Room("sleepingQuarters2", new String[]{"After pretending to "
				+ "lead against the Formics, you've retired to your sleeping quarters to reconsider"
				+ " strategies.","Here you just see your bed. Your room here is pretty barren."}, 
				null, 100, null, 100);
		battleSimulatorRoom2 = new Room("battleSimulatorRoom2", new String[]{"You've entered the Battle"
				+ " Simulator Room.  Your team awaits your commands, ready for more practice.","This is"
				+ " your graduation ceremony. If you perform well, you graduate!"}, 
				null, 100, this.genocidePuzzle, 100);
		commandRoom = new Room("commandRoom", new String[]{"You are outraged to find out that this was"
				+ " all real! You run into the Command Room where you come face to face with Colonel"
				+ " Graff.","This room is filled with your leaders, all proud that you defeated the Formics"
				+ " in the most efficient way possible!"}, hyrum, 100, null, 100);
		airlock = new Room("airlock", new String[]{"You decide you take a look outside of the Forward"
				+ "Outpost. This is the airlock room where you prepare for an oxygen free environment.",
				"There are suits on the wall, but you can't seem to find the oxygen mask."}, vader, 100,
				null, 100);
		outside = new Room("outside", new String[]{"",""}, null, 100, null, 100);
		formicCastle = new Room("formicCastle", new String[]{"This must be the Formic queen's castle..."
				+ "but it's in ruins.","All you see is ruins everywhere..a loving home that you"
				+ " are entirely responsible for destroying. You should feel like a really terrible"
				+ " human being."}, queen, 100, null, 100);

		// room exits
		womb.setRoomExits(new Room[]{null, null, deliveryRoom, null});
		deliveryRoom.setRoomExits(new Room[]{null, null, crib, null});
		crib.setRoomExits(new Room[]{null, null, livingRoom, null});
		livingRoom.setRoomExits(new Room[]{null, orientation, null, null});
		orientation.setRoomExits(new Room[]{null, hallway, null, null});
		hallway.setRoomExits(new Room[]{orientation, hallway2, scienceClass, mathClass});
		scienceClass.setRoomExits(new Room[]{null, null, null, hallway});
		mathClass.setRoomExits(new Room[]{null, null, hallway, null});
		hallway2.setRoomExits(new Room[]{hallway, null, infirmary, battleStrategyClass});
		battleStrategyClass.setRoomExits(new Room[]{null, null, hallway2, null});
		infirmary.setRoomExits(new Room[]{null, bedroom, null, hallway2});
		bedroom.setRoomExits(new Room[]{null, livingRoom2, null, null});
		livingRoom2.setRoomExits(new Room[]{bedroom, spaceship, null, null});
		spaceship.setRoomExits(new Room[]{null, bunkroomL, null, null});
		bunkroomL.setRoomExits(new Room[]{null, hallwayS, combatArena, strategyClass});
		combatArena.setRoomExits(new Room[]{strategyClass, bunkroomS, bunkroomD, bunkroomL});
		strategyClass.setRoomExits(new Room[]{null, null, bunkroomL2, null});
		bunkroomL2.setRoomExits(new Room[]{null, hallwayS, combatArena, strategyClass});
		hallwayS.setRoomExits(new Room[]{bunkroomL, bunkroomS, null, null});
		bunkroomS.setRoomExits(new Room[]{hallwayS, null, combatArena2, null});
		combatArena2.setRoomExits(new Room[]{strategyClass, bunkroomS, bunkroomD, bunkroomL});
		bunkroomD.setRoomExits(new Room[]{shower, null, null, combatArena3});
		combatArena3.setRoomExits(new Room[]{strategyClass, bunkroomS, bunkroomD, bunkroomL});
		shower.setRoomExits(new Room[]{null, bunkroomD, cabin, null});
		cabin.setRoomExits(new Room[]{null, sleepingQuarters, shower, null});
		sleepingQuarters.setRoomExits(new Room[]{cabin, null, battleSimulatorRoom, null});
		battleSimulatorRoom.setRoomExits(new Room[]{null, null, null, sleepingQuarters2});
		sleepingQuarters2.setRoomExits(new Room[]{cabin, null, battleSimulatorRoom2, airlock});
		battleSimulatorRoom2.setRoomExits(new Room[]{null, commandRoom, null, sleepingQuarters2});
		commandRoom.setRoomExits(new Room[]{battleSimulatorRoom, null, null, sleepingQuarters2});
		airlock.setRoomExits(new Room[]{null, null, sleepingQuarters2, outside});
		outside.setRoomExits(new Room[]{null, null, airlock, formicCastle});
		formicCastle.setRoomExits(new Room[]{null, null, outside, null});
		
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
