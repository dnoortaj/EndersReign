package UserInteraction;
	import java.io.*;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Random;
	import java.util.Scanner;
	import org.apache.commons.lang3.text.WordUtils;

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
		private String helpFormat, resume, helpMe, roaming, puzzleFailed, 
		startupText, lineBreak, winningText, losingText;

		/** file to be loaded, for use with save/load methods */
		private String gameFile = null;

		/** current game's player */
		private Player currentPlayer = null;
		
		private boolean mainMenu = true;
		private boolean displayTitle = true;

		/** current game's player location */
		private Room currentRoom = null;

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
		tauntBug, bzzOutput, tauntFlee1;

		/** game enemies */
		Enemy mazer, jerry, peter, dissenter, droid, bonzo, hyrum, vader, queen, 
		beatle, cadet, bullies, ant, centipede, bee, housefly, mosquito, bedBug;

		/** game puzzles */
		Puzzle wombPuzzle, cribPuzzle, namePuzzle, mathPuzzle, sciencePuzzle, battleStrategyPuzzle,
		bullyPuzzle, spaceshipPuzzle, giantPuzzle, gunPuzzle, battlePuzzle, preliminaryPuzzle, genocidePuzzle, bonzoPuzzle;

		/** game rooms */
		Room womb, deliveryRoom, crib, livingRoom, orientation, hallway, battleStrategyClass,
		scienceClass, mathClass, hallway2, infirmary, bedroom, livingRoom2, spaceship,
		bunkroomL, combatArena, strategyClass, bunkroomL2, hallwayS, bunkroomS, combatArena2,
		bunkroomD, combatArena3, shower, cabin, sleepingQuarters, battleSimulatorRoom, sleepingQuarters2,
		battleSimulatorRoom2, commandRoom, airlock, outside, formicCastle, orientation2, hallway3, 
		hallway4, adminOffice, emptyClassroom, homeHallway, sisterRoom, kitchen, diningRoom, launchieHallway, 
		strategyClass2, salamanderHallway, hallwayD, lake, commandHallway, commandHallway2, queenRoom;

		/*********************************************************************
		Default constructor method for GameController.

		@param none
		@return none
		 *********************************************************************/
		public GameController()
		{
			helpMe = "Press \"H\" for help.";
			resume = "Gameplay resumed. " + helpMe;
			roaming = "\nYou're free to roam around. " + helpMe;
			puzzleFailed = "Good job, you scored 0 points in that last encounter. " +
					"Maybe your cat walked on the keyboard, or you just have a case of terminal derpies; " +
					"whichever it is, you're going to have to try that over again.";
			startupText = "Greetings to you, the unlucky finder of this terrible game. Present this game at " +
					"your ITEC 3860 classroom at eight-thirty in the morning of the eleventh day of December, " +
					"and do not be late. You may bring with you several excuses as to your content choices, but nothing else. " +
					"In your wildest nightmares you could not imagine the god-awful coding horror that awaits you! " +
					".. see, it's a golden ticket parody, from.. nevermind. Anyway, you're Ender Wiggin, and we're " +
					"starting from the very-very beginning. For reals.";
			lineBreak = "\n=================================================="
					+ "==================================================\n";
			winningText = "   _____                      \n"
					+ "  / ____|                     \n"
					+ " | |  __  __ _ _ __ ___   ___ \n"
					+ " | | |_ |/ _` | '_ ` _ \\ / _ \\\n"
					+ " | |__| | (_| | | | | | |  __/\n"
					+ "  \\_____|\\__,_|_| |_| |_|\\___|\n"
					+ "  / __ \\                      \n"
					+ " | |  | |_   _____ _ __       \n"
					+ " | |  | \\ \\ / / _ \\ '__|      \n"
					+ " | |__| |\\ V /  __/ |         \n"
					+ "  \\____/  \\_/ \\___|_|         ";
			losingText = " __     __                        \n"
					+ " \\ \\   / /                        \n"
					+ "  \\ \\_/ /__  _   _                \n"
					+ "   \\   / _ \\| | | |               \n"
					+ "    | | (_) | |_| |               \n"
					+ "  __|_|\\___/ \\__,_|            _  \n"
					+ " |  ____|        | |          | | \n"
					+ " | |__ _   _  ___| | _____  __| | \n"
					+ " |  __| | | |/ __| |/ / _ \\/ _` | \n"
					+ " | |  | |_| | (__|   <  __/ (_| | \n"
					+ " |_|   \\__,_|\\___|_|\\_\\___|\\__,_| \n"
					+ " | |  | |     | |                 \n"
					+ " | |  | |_ __ | |                 \n"
					+ " | |  | | '_ \\| |                 \n"
					+ " | |__| | |_) |_|                 \n"
					+ "  \\____/| .__/(_)                 \n"
					+ "        | |                       \n"
					+ "        |_|";
		}

		/*********************************************************************
		Method for main menu output. Interacts with user to determine game
		startup.

		@param none
		@return none
		 *********************************************************************/
		public void mainMenu()
		{		 
			if(displayTitle)
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
						"Welcome to Ender's Reign: Wiggin's Formic Rage!");
						displayTitle = false;
			}
			System.out.println("Would you like to:\n> Start [New] Game \n> [Load] Game \n> [Exit]");
			System.out.print("> ");
			String input = scanner.nextLine();

			while (!input.equalsIgnoreCase("new")
						&& !input.equalsIgnoreCase("load")
						&& !input.equalsIgnoreCase("exit"))
			{
				System.out.println("Your input did not match available options." +
						"\n Please type \"New\", \"Load\", or \"Exit\"");
				System.out.print("> ");
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
//	 			TODO actually make a default save file
				
				gameFile = "DEFAULT.dat";
				fileReader = new FileInputStream(gameFile);
				deserializer = new ObjectInputStream(fileReader);
				loadObjects();
				
				mainMenu = false;
				System.out.print("Please enter your desired profile name. (Leave blank for default name)\n> ");
				String input = scanner.nextLine();
				if(input.trim().equals("") || input == null)
				{
					currentPlayer.setPlayerName("Ender");
				}
				else
				{
					currentPlayer.setPlayerName(input);
				}
		
				System.out.println(lineBreak);
				wait(1000);
				System.out.println(wrapIt(startupText));
				autoSave();
				wait(1000);
				forceMove(womb);
				listener();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("ERROR - COULD NOT START NEW GAME");
				mainMenu();
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
			System.out.print("Please select a save file to load. (Enter a value 1-3, or 0 to cancel)\n> ");
			String input = scanner.nextLine();
			
			while(!input.equalsIgnoreCase("0") && !input.equalsIgnoreCase("1") && 
					!input.equalsIgnoreCase("2") && !input.equalsIgnoreCase("3"))
			{
				System.out.print("Valid value not detected; please try again. (Enter a value 1-3, or 0 to cancel)\n> ");
				input = scanner.nextLine();
			}

			if(input.equalsIgnoreCase("0"))
			{
				System.out.println("Load operation cancelled.");
				if(mainMenu)
				{
					mainMenu();
				}
				else
				{
					System.out.println(resume);
				}
				return;
			}
			else if(input.equalsIgnoreCase("1"))
			{
				gameFile = "game1.dat";
			}
			else if(input.equalsIgnoreCase("2"))
			{
				gameFile = "game2.dat";
			}
			else if(input.equalsIgnoreCase("3"))
			{
				gameFile = "game3.dat";	
			}
			
			try
			{
				fileReader = new FileInputStream(gameFile);
				deserializer = new ObjectInputStream(fileReader);
				loadObjects();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("ERROR - COULD NOT READ FROM FILE\n");
				loadGame();
			}
			
			System.out.println(lineBreak);
			System.out.println("Save file " + gameFile + " successfully loaded.");
			mainMenu = false;
			System.out.println(resume);
			autoSave();
			listener();
		}

		/*********************************************************************
		Method for loading a game state from file specified by gameFile.

		@param none
		@return none
		 *********************************************************************/
		private void loadObjects()
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
				e.printStackTrace();
				System.out.println("ERROR - COULD NOT LOAD OBJECTS FROM FILE");
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
			bonzoPuzzle = puzzleList.get(13);

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
			orientation2 = roomList.get(33);
			hallway3 = roomList.get(34);
			hallway4 = roomList.get(35);
			adminOffice = roomList.get(36);
			emptyClassroom = roomList.get(37);
			homeHallway = roomList.get(38);
			sisterRoom = roomList.get(39);
			kitchen = roomList.get(40);
			diningRoom = roomList.get(41);
			launchieHallway = roomList.get(42);
			strategyClass2 = roomList.get(43);
			salamanderHallway = roomList.get(44);
			hallwayD = roomList.get(45);
			lake = roomList.get(46);
			commandHallway = roomList.get(47);
			commandHallway2 = roomList.get(48);
			queenRoom = roomList.get(49);
		}

		public void saveGame()
		{
			System.out.print("Please select a save slot to use. (Enter a value 1-3, or 0 to cancel)\n> ");
			String input = scanner.nextLine();
			
			while(!input.equalsIgnoreCase("0") && !input.equalsIgnoreCase("1") && 
					!input.equalsIgnoreCase("2") && !input.equalsIgnoreCase("3"))
			{
				System.out.print("Valid value not detected; please try again. (Enter a value 1-3, or 0 to cancel)\n> ");
				input = scanner.nextLine();
			}

			if(input.equalsIgnoreCase("0"))
			{
				System.out.println("Save operation cancelled.\n" + resume);
				return;
			}
			else if(input.equalsIgnoreCase("1"))
			{
				gameFile = "game1.dat";
			}
			else if(input.equalsIgnoreCase("2"))
			{
				gameFile = "game2.dat";
			}
			else if(input.equalsIgnoreCase("3"))
			{
				gameFile = "game3.dat";	
			}

			try
			{
				fileWriter = new FileOutputStream(gameFile);
				serializer = new ObjectOutputStream(fileWriter);
				saveObjects();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("ERROR - COULD NOT SAVE TO FILE\n");
				saveGame();
			}

			System.out.println("Game successfully saved to " + gameFile + ".");
			System.out.println(resume);
		}

		/*********************************************************************
		Method for saving the current game state to a specified file.

		@param none
		@return none
		 *********************************************************************/
		private void saveObjects()
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
				e.printStackTrace();
				System.out.println("ERROR - COULD NOT SAVE OBJECTS TO FILE");
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
			puzzleList = new ArrayList<Puzzle>();
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
			puzzleList.add(bonzoPuzzle);

			// room section
			roomList = new ArrayList<Room>();
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
			roomList.add(orientation2);
			roomList.add(hallway3);
			roomList.add(hallway4);
			roomList.add(adminOffice);
			roomList.add(emptyClassroom);
			roomList.add(homeHallway);
			roomList.add(sisterRoom);
			roomList.add(kitchen);
			roomList.add(diningRoom);
			roomList.add(launchieHallway);
			roomList.add(strategyClass2);
			roomList.add(salamanderHallway);
			roomList.add(hallwayD);
			roomList.add(lake);
			roomList.add(commandHallway);
			roomList.add(commandHallway2);
			roomList.add(queenRoom);
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
				String input = scanner.nextLine();
				switch (input.toLowerCase()) {
				case "w":
				case "north":
					move(0);
					break;
				case "s":
				case "south":
					move(1);
					break;
				case "a":
				case "west":
					move(2);
					break;
				case "d":
				case "east":
					move(3);
					break;
				case "h":
				case "help":
					displayHelp();
					break;
				case "i":
				case "inventory":
					useItem();
					wait(500);
					System.out.println(wrapIt(roaming));
					break;
				case "l":
				case "look":
					System.out.println(wrapIt(currentRoom.getRoomDescription(1)));
					System.out.print(pathFinder());
					break;
				case "1":
				case "save":
					saveGame();
					break;
				case "2":
				case "load":
					loadGame();
					break;
				case "0":
				case "exit":
					System.exit(0);
				default:
					System.out.println("Command not recognized.");
					break;
				}
			}
		}

		/*********************************************************************
		Method for handling special item cases.

		@param none
		@return none
		 *********************************************************************/
		private void useItem()
		{
			Item item = currentPlayer.getPlayerInventory().useItem();
			String name = null;
			
			if(item != null)
			{
				name = item.getItemName();
			}
			
			if(name != null)
			{
				if(name.equalsIgnoreCase("tablet") && currentRoom.equals(bunkroomL2))
				{
					if(!tablet.getIsUsed())
					{
						tablet.setIsUsed(true);
						wait(1000);
						System.out.println(wrapIt("You find a curious-looking game loaded on your tablet and proceed to investigate."));
						wait(1000);
						System.out.println(lineBreak);
						currentPlayer.addToScore(currentRoom.getRoomPuzzle().solvePuzzle());
						// updates score
						int points = currentRoom.getRoomPuzzle().getPuzzlePoints();
						System.out.println(wrapIt("Your score just increased by " + points
								+ " points for a total of " + currentPlayer.getPlayerScore() + "!"));

						// retrieves the room's puzzle reward and adds to current player inventory
						if(currentRoom.getRoomPuzzle().getPuzzleReward() != null && points != 0)
						{
							currentPlayer.getPlayerInventory().addToInventory(currentRoom.getRoomPuzzle().getPuzzleReward());
						}
						wait(1000);
						System.out.print(lineBreak);
					}
				}
				else if(name.equalsIgnoreCase("tablet") && !currentRoom.equals(bunkroomL2))
				{
					if(!tablet.getIsUsed())
					{
						wait(1000);
						System.out.println(wrapIt("Nothing in particular catches your interest and you soon decide to turn off the tablet."));
						wait(1000);
					}
				}
				else if(name.equalsIgnoreCase("phoenix down"))
				{
					wait(1000);
					System.out.println("Your gain an additional life. Go figure.");
					wait(1000);
					currentPlayer.setPlayerLives(currentPlayer.getPlayerLives() + 1);
				}
				else if(name.equalsIgnoreCase("queen eggs"))
				{
					wait(1000);
					System.out.println("Well, let's pretend you didn't just eat those..");
					wait(1000);
					System.out.println(lineBreak);
					winGame();
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
				Room room = currentRoom.getRoomExits(direction);
				
				// TODO set up special movement conditions here
				if((room.equals(combatArena) || room.equals(combatArena2) || room.equals(combatArena3)) && 
						!currentPlayer.isSuitFlag())
				{
					System.out.println(wrapIt("You must have the combat suit equipped in order to enter this area."));
				}
				else if(room.equals(outside) && !genocidePuzzle.getPuzzleIsCompleted())
				{
					System.out.println("You have no reason to leave the Outpost.");
				}
				else if(room.equals(outside) && !currentPlayer.isOxygenFlag())
				{
					System.out.println(wrapIt("You must have the supplemental O2 device equipped in order to enter this area."));
				}
				else if(room.equals(infirmary) && !bullies.enemyIsDead())
				{
					System.out.println("You have no reason to visit the infirmary at the moment.");
				}
				else if(room.equals(hallwayS) && !giantPuzzle.getPuzzleIsCompleted())
				{
					System.out.println("You're not authorized to visit the Salamander wing of the station.");
				}
				else if(room.equals(hallwayD) && !bonzoPuzzle.getPuzzleIsCompleted())
				{
					System.out.println("You're not authorized to visit the Dragon wing of the station.");
				}
				else if(room.equals(commandRoom) && !genocidePuzzle.getPuzzleIsCompleted())
				{
					System.out.println("You're not authorized to enter the command room.");
				}
				else
				{
					// move in desired direction, update room
					autoSave();
					System.out.println("You head " + dir);
					wait(1000);
					forceMove(currentRoom.getRoomExits(direction));
				}
			}
			else
			{
				// notify player of non-move
				System.out.println("You cannot move " + dir);
			}
		}

		/*********************************************************************
		Helper method for changing the currentRoom. Sets currentRoom to the
		provided Room, triggers appropriate encounter. Recursively calls
		forceMove in the event that a Room redirect is present.

		@param Room room - The new currentRoom.
		@return none
		 *********************************************************************/
		private void forceMove(Room room)
		{
			boolean monsterEncountered = false;
			currentRoom = room;
			System.out.println(lineBreak);
			System.out.println(wrapIt(currentRoom.getRoomDescription(0)));

			if(room != null)
			{
				if(currentRoom.getRoomEnemy() != null)
				{
					// attempt trigger current room's enemy
					if(!currentRoom.getRoomEnemy().enemyIsDead())
					{
						if(random.nextInt(100) + 1 <= currentRoom.getRoomEnemyChance())
						{
							// monster was encountered
							monsterEncountered = true;

							System.out.println(lineBreak);
							// combat flag set prior to fight, updated after fight
							currentPlayer.setBattleFlag(true);
							currentRoom.getRoomEnemy().fight(currentPlayer);
							currentPlayer.setBattleFlag(false);

							if(currentRoom.getRoomEnemy().enemyIsDead())
							{
								// updates score
								currentPlayer.setPlayerScore(currentPlayer.getPlayerScore() + currentRoom.getRoomEnemy().getPoints());
								wait(1000);
								System.out.println("\n" + wrapIt("Your score just increased by " + currentRoom.getRoomEnemy().getPoints()
										+ " points for a total of " + currentPlayer.getPlayerScore() + "!"));

								// retrieves the room's enemy reward and adds to current player inventory
								currentPlayer.getPlayerInventory().addToInventory(currentRoom.getRoomEnemy().getReward());
							}
							else if(currentPlayer.getPlayerCurrentHP() <= 0)
							{
								loadCheckpoint();
								return;
							}
						}
					}
				}

				if(currentRoom.getRoomPuzzle() != null && !monsterEncountered)
				{
					// attempt to trigger current room's puzzle if enemy was not encountered
					if(!currentRoom.getRoomPuzzle().getPuzzleIsCompleted())
					{
						if(random.nextInt(100) + 1 <= currentRoom.getRoomPuzzleChance())
						{
							wait(1000);
							System.out.println(lineBreak);
							
							// triggers the puzzle, adds outcome to player score
							currentPlayer.addToScore(currentRoom.getRoomPuzzle().solvePuzzle());
							int points = currentRoom.getRoomPuzzle().getPuzzlePoints();
							if(currentRoom.getRoomPuzzle().isKeyPuzzle() && points != 0)
							{
								// updates score
								System.out.println(wrapIt("Your score just increased by " + points
										+ " points for a total of " + currentPlayer.getPlayerScore() + "!"));

								// retrieves the room's puzzle reward and adds to current player inventory
								if(currentRoom.getRoomPuzzle().getPuzzleReward() != null)
								{
									currentPlayer.getPlayerInventory().addToInventory(currentRoom.getRoomPuzzle().getPuzzleReward());
								}
							}
							else if(currentRoom.getRoomPuzzle().isKeyPuzzle() && points == 0)
							{
								System.out.println(wrapIt(puzzleFailed));
								loadCheckpoint();
								return;
							}
							else if(!currentRoom.getRoomPuzzle().isKeyPuzzle())
							{
								System.out.println(wrapIt("Your score just increased by " + points
										+ " points for a total of " + currentPlayer.getPlayerScore() + "!"));

								// retrieves the room's puzzle reward and adds to current player inventory
								if(currentRoom.getRoomPuzzle().getPuzzleReward() != null && points != 0)
								{
									currentPlayer.getPlayerInventory().addToInventory(currentRoom.getRoomPuzzle().getPuzzleReward());
								}
							}
						}
					}
				}

				if(currentRoom.getRedirect() != null)
				{
					wait(1000);
					forceMove(currentRoom.getRedirect());
					return;
				}
				wait(1000);
				System.out.println(lineBreak + wrapIt(roaming));
			}
		}
		
		/*********************************************************************
		Method for returning a String of possible movement directions.

		@param none
		@return String - The string of available movement directions.
		 *********************************************************************/
		private String pathFinder()
		{
			String directions = "";
			if(currentRoom != null)
			{
				if(currentRoom.getRoomExits(0) != null)
				{
					directions += wrapIt("To the north lies " + currentRoom.getRoomExits(0).getRoomName() + ".\n");
				}
				if(currentRoom.getRoomExits(1) != null)
				{
					directions += wrapIt("To the south lies " + currentRoom.getRoomExits(1).getRoomName() + ".\n");
				}
				if(currentRoom.getRoomExits(2) != null)
				{
					directions += wrapIt("To the west lies " + currentRoom.getRoomExits(2).getRoomName() + ".\n");
				}
				if(currentRoom.getRoomExits(3) != null)
				{
					directions += wrapIt("To the east lies " + currentRoom.getRoomExits(3).getRoomName() + ".\n");
				}
			}
			return directions;
		}
		
		/*********************************************************************
		Method for saving the current game state to the autosave.dat file.

		@param none
		@return none
		 *********************************************************************/
		public void autoSave()
		{
			// heals currentPlayer to max HP prior to making the save
			try
			{
				fileWriter = new FileOutputStream("autosave.dat");
				serializer = new ObjectOutputStream(fileWriter);
				saveObjects();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("ERROR - AUTOSAVE WRITE ERROR\n");
			}
		}
		
		/*********************************************************************
		Method for resetting the game state using the autosave.dat file.
		Subtracts one from currentPlayer's lives, or notifies player that s/he
		has permanently died.

		@param none
		@return none
		 *********************************************************************/
		public void loadCheckpoint()
		{
			if(currentPlayer.getPlayerLives() > 0)
			{
				System.out.print("Do you wish to continue? (Y/N)\n> ");
				String input = scanner.nextLine();

				while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("yes") && 
						!input.equalsIgnoreCase("n") && !input.equalsIgnoreCase("no"))
				{
					System.out.print("Valid value not detected; please try again. (Y/N)\n> ");
					input = scanner.nextLine();
				}

				if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes"))
				{
					try
					{
						fileReader = new FileInputStream("autosave.dat");
						deserializer = new ObjectInputStream(fileReader);
						loadObjects();
					}
					catch (Exception e)
					{
						e.printStackTrace();
						System.out.println("ERROR - AUTOSAVE READ ERROR\n");
					}
					System.out.println(lineBreak);
					System.out.println("Autosave successfully loaded.");
					currentPlayer.setPlayerLives(currentPlayer.getPlayerLives() - 1);
					currentPlayer.setPlayerCurrentHP(currentPlayer.getPlayerMaxHP());
					System.out.println("You have " + currentPlayer.getPlayerLives() + " lives remaining.");
					wait(1000);
					// if you're at the beginning of the game, reset first puzzle
					if(currentRoom.equals(womb))
					{
						autoSave();
						forceMove(womb);
					}
					else
					{
						System.out.println(resume);
					}
				}
				else if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
				{
					System.exit(0);
				}
			}
			else
			{
				System.out.println(lineBreak);
				System.out.println(wrapIt("You've exhausted all of your extra lives. " +
						"Your journey seems to be at an end. For reals this time."));
				System.out.println("Score: " + currentPlayer.getPlayerScore());
				System.out.println("Rank: Dead Zombie\nBetter luck next time!");

				System.out.println(lineBreak);
				System.out.println(losingText);

				System.out.println(lineBreak);
				System.out.print("Return to title menu? (Y/N)\n> ");
				String input = scanner.nextLine();
				while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("yes") && 
						!input.equalsIgnoreCase("n") && !input.equalsIgnoreCase("no"))
				{
					System.out.print("Valid value not detected; please try again. (Y/N)\n> ");
					input = scanner.nextLine();
				}

				if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes"))
				{
					mainMenu = true;
					displayTitle = true;
					System.out.println(lineBreak);
					mainMenu();
				}
				else if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
				{
					System.exit(0);
				}
			}
		}

		/*********************************************************************
		Method for displaying proper output on winning the game.

		@param none
		@return none
		 *********************************************************************/
		private void winGame()
		{
			String rank = "";
			int score = currentPlayer.getPlayerScore();
			
			if(score < 130)
			{
				rank = "Bugger";
			}
			else if(score >= 130 && score < 160)
			{
				rank = "Launchie";
			}
			else if(score >= 160 && score < 190)
			{
				rank = "Salamander";
			}
			else if(score >= 190 && score < 210)
			{
				rank = "Dragon";
			}
			else if(score >= 210 && score < 240)
			{
				rank = "Admiral";
			}
			else if(score >= 240)
			{
				rank = "Genocidal Maniac";
			}
			System.out.println(wrapIt("Congratulations! You've unwittingly accomplished more than Hitler ever dreamed possible. " +
					"I hope you're proud of yourself, you filthy murderer. But no, really, good job!"));
			System.out.println("Score: " + score);
			System.out.println("Rank: " + rank);

			System.out.println(lineBreak);
			System.out.println(winningText);

			System.out.println(lineBreak);
			System.out.print("Return to title menu? (Y/N)\n> ");
			String input = scanner.nextLine();
			while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("yes") && 
					!input.equalsIgnoreCase("n") && !input.equalsIgnoreCase("no"))
			{
				System.out.print("Valid value not detected; please try again. (Y/N)\n> ");
				input = scanner.nextLine();
			}

			if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes"))
			{

				mainMenu = true;
				displayTitle = true;
				System.out.println(lineBreak);
				mainMenu();
			}
			else if(input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
			{
				System.exit(0);
			}
		}
		
		/*********************************************************************
		Method for displaying the game's help list.

		@param none
		@return none
		 *********************************************************************/
		public void displayHelp()
		{
			helpFormat = "%1$-20s%2$-20s%3$-20s%4$-20s\n";
			System.out.println(wrapIt(currentRoom.getRoomDescription(1)));
			System.out.println(currentPlayer.getPlayerCurrentHP() + "/" 
				+ currentPlayer.getPlayerMaxHP() + " Hit Points.");
			System.out.println(currentPlayer.getPlayerScore() + " magical fairy points.\n");
			System.out.format(helpFormat, " MOVEMENT ", " ACTION ", " FUNCTION", "");
			System.out.format(helpFormat, " > W North ", " > L Look ", " > 1 Save Game ", "");	
			System.out.format(helpFormat," > S South ", " > I Inventory ", " > 2 Load Game ", "");	
			System.out.format(helpFormat, " > D East " , "", " > 0 Exit Game", "");	
			System.out.format(helpFormat, " > A West " , "", "", "");
			System.out.println("\n(Input is not case sensitive.)");
			
		}
		
		/*********************************************************************
		Method for making the program wait.

		@param int time - How long to delay the program, in milliseconds.
		@return none
		 *********************************************************************/
		private void wait(int time)
		{
			try 
			{
			    Thread.sleep(time);
			} 
			catch(Exception e) 
			{
				Thread.currentThread().interrupt();
			}
		}
		
		/*********************************************************************
		Private helper method for wrapping designated Strings at 80 
		characters. Uses the wrap function from apache.

		@param String string - String to wrap.
		@return String - The string with new lines at 80 characters.
		 *********************************************************************/
		private String wrapIt(String string)
		{
			return WordUtils.wrap(string, 100, "\n", true);
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