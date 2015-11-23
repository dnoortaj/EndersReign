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
import java.util.Scanner;

import org.junit.Before;

import Inventory.*;
import Obstacle.*;

public class GameController
{

	private Game game;
	private boolean run;
	Scanner sc = new Scanner(new InputStreamReader(System.in));
	Inventory inv;
	Inventory invNotFound;
	Player player;
	Player playerInvNotFound;
	List<Enemy> enemyList = new ArrayList<Enemy>();
	Enemy mazer, jerry, peter, dissenter, droid, bonzo, hyrum, vader, queen, 
	bugs, cadet, bullies;
	String [] hitOutput, tauntFlee, tauntEnrage, tauntHide, tauntConcentration, 
	tauntStandard;
	String gameFile;
	FileInputStream fIn;
	FileOutputStream fOut;
	ObjectInputStream deserializer; 

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
			game.move(1);
			break;
		case "s":
			game.move(2);
			break;
		case "a":
			game.move(4);
			break;
		case "d":
			game.move(3);
			break;
		case "h":
			game.displayHelp();
			break;
		case "i":
			game.getCurrentPlayer().getPlayerInventory().useItem();
			break;
		case "l":
			System.out.println(game.getGameCurrentRoom().getRoomDescription(1));
		case "1":
			//save the things
			break;
		case "2":
			//load the things
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


//		mazer = new Enemy(06, "Mazer Rackham", 70, 15, 
//				10, w5, 10, hitOutput, tauntEnrage);
//		cadet = new Enemy(11, "Cadet", 30, 10, 
//				110, w5, 10, hitOutput, tauntHide);
//		bullies = new Enemy(01, "Jerry and two of his cohorts", 50, 19, 
//				10, w5, 10, hitOutput, tauntFlee);
//		peter = new Enemy(02, "Peter", 40, 10, 10, w5, 10, hitOutput,
//				tauntHide);
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
//
//
//		// weapons
//		w1 = new Weapon("Weapon 1", "First weapon.", false, 10);
//		w2 = new Weapon("Weapon 2", "Second weapon.", false, 20);
//		w3 = new Weapon("Weapon 3", "Third weapon.", false, 30);
//		w4 = new Weapon("Weapon 4", "Fourth weapon.", false, 40);
//		w5 = new Weapon("Weapon 5", "Fifth weapon.", false, 50);
//
//		// accessories
//		a1 = new Accessory("Accessory 1", "First accessory.", false, 1, 1, 1);
//		a2 = new Accessory("Accessory 2", "Second accessory.", false, 2, 2, 2);
//		a3 = new Accessory("Accessory 3", "Third accessory.", false, 3, 3, 3);
//		a4 = new Accessory("Accessory 4", "Fourth accessory.", false, 4, 4, 4);
//		a5 = new Accessory("Accessory 5", "Fifth accessory.", false, 5, 5, 5);
//
//		// consumables
//		c1 = new Consumable("Consumable 1", "First consumable.", false, 1);
//		c2 = new Consumable("Consumable 2", "Second consumable.", false, 2);
//		c3 = new Consumable("Consumable 3", "Third consumable.", false, 3);
//		c4 = new Consumable("Consumable 4", "Fourth consumable.", false, 4);
//		c5 = new Consumable("Consumable 5", "Fifth consumable.", false, 5);
//		cNull = new Consumable(null, null, false, 100);
//
//		// key items
//		k1 = new Oxygen("O2 Test Key Item 1", "First test key item.", true, false);
//		k2 = new Oxygen("O2 Test Key Item 2", "Second test key item.", true, false);
//		k3 = new Oxygen("O2 Test Key Item 3", "Third test key item.", true, false);

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



	/**
	 * Method: readFromFile
	 *  created to read a binary file of the saved game
	 */
	/*	
	   public  void readFromFile()
	    {
	       ObjectInputStream inFile = null;

	       // open the file
	       try
	       {
	           inFile = new ObjectInputStream(new FileInputStream("games.dat"));
	       }
	       catch (IOException e)
	       {
	           System.out.println("Problem opening games.dat");
	           e.printStackTrace();

	       }

	       // separate try block to make sure if problem occurs we have a better idea at what point it
	       // occurs
	       try
	       {
	           while (true)
	           {
	               Game g = (Game)inFile.readObject();
	               games.add(g);
	           }

	       }
	       catch (EOFException ex)
	       {
	           // this is expected -just close file
	           try
	        {
	            inFile.close();
	        } catch (IOException e)
	        {

	            System.out.println("Problem closing the file");
	            e.printStackTrace();
	        }
	       }
	       catch (Exception e)
	       {
	           // unexpected exception
	           System.out.println("Problem reading from file");
	           e.printStackTrace();
	       }

	       // got all games

	    }

	/**
	 * Method: writeToFile
	 * writes games into a binary file
	 * @param games
	 */

	/*	public void writeToFile(ArrayList<Game> games)
    {
        try
        {
            ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("games.dat"));

            // now write the Game classes from the ArrayList
            for(Game g : games)
            {
                outFile.writeObject(g);
            }
            outFile.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }*/

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
