package UserInteraction;
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
	
	public GameController()
	{
		games = new ArrayList<Game>();
		game = new Game();
		run = false;
	}
	
	public void mainMenu()
	{		

				System.out.println("Welcome to Ender's Reign: Wiggin's Formic Rage! \nWould you like to:"
						+ "\n> Start New Game \n> Load Game \n> Exit");
				String input = sc.nextLine();
				
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
					
				}

	}
	
	public void startNewGame()
	{
		readFromFile();
		
		
	}
	
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
			}
			else
			{
				System.out.println("Sorry, you've entered an incorrect value. \nPlease enter a value 1-3.");
				System.out.print("> ");			
				loadFile = Integer.parseInt(sc.nextLine());
			}
			readFromFile();
			game = games.get((loadFile-1));
		}
		catch (Exception e)
		{
			run = true;
			loadGame();
		}
	}
	
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
	
    public void writeToFile(ArrayList<Game> games)
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
    }
	
	public static void main(String[] args)
	{
		GameController gameCont = new GameController();
		gameCont.mainMenu();
	}
}
