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
import java.util.Scanner;

import Inventory.*;
import Obstacle.*;

public class GameController
{
	private ArrayList<Game> games = new ArrayList<Game>();
	private Game game;
	private boolean run;
	Scanner sc = new Scanner(new InputStreamReader(System.in));
	

	/**
	 * Method: GameController
	 * constructor for GameController class
	 */
	public GameController()
	{
		games = new ArrayList<Game>();
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
		int newFile;
		String answer;
		try
		{

			if (run == false)
			{
				System.out.println("Which save spot would you like to utilize? (Enter a value 1-3)");
				System.out.print("> ");			
				newFile = Integer.parseInt(sc.nextLine());
				if (newFile <= 3 && newFile > 0 )
				{
					if (games.get(newFile-1) != null)
					{
						System.out.println("Are you sure you wish to overwrite this file?");
						answer = sc.nextLine();
						if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y"))
						{
							//readFromFile();
							games.remove(newFile-1);
							games.add((newFile-1), game);
							//writeToFile(games);
						}
						else
						{
							System.out.println("Do you still want to start a new game?");
							answer = sc.nextLine();
							if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y"))
							{
								startNewGame();
							}
							else
							{
								System.exit(0);
							}
						}
					}

				}
			}
			else
			{
				System.out.println("Sorry, you've entered an incorrect value. \nPlease enter a value 1-3.");
				System.out.print("> ");			
				newFile = Integer.parseInt(sc.nextLine());
				if (newFile <= 3 && newFile > 0 )
				{
					//readFromFile();
					games.remove(newFile-1);
					games.add((newFile-1), game);
					//writeToFile(games);
				}
			}
		}
		catch (Exception e)
		{
			run = true;
			startNewGame();
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

			if (run == false)
			{
				System.out.println("Which file would you like to open? (Enter a value 1-3)");
				System.out.print("> ");			
				loadFile = Integer.parseInt(sc.nextLine());
				if (loadFile <= 3 && loadFile > 0 )
				{
					//readFromFile();
					game = games.get((loadFile-1));
				}
			}
			else
			{
				System.out.println("Sorry, you've entered an incorrect value. \nPlease enter a value 1-3.");
				System.out.print("> ");			
				loadFile = Integer.parseInt(sc.nextLine());
				if (loadFile <= 3 && loadFile > 0 )
				{
					//readFromFile();
					game = games.get((loadFile-1));
				}
			}

		}
		catch (Exception e)
		{
			run = true;
			loadGame();
		}
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
