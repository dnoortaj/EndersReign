package UserInteraction;

/** Class: Game.java
 * @author Danyelle Noortajalli
 * @author Dale Burke
 * @author Jp Sprouse
 * @version 1.0
 * Course: ITEC 3150 Fall 2015
 * Written: Nov 12, 2015
 * 
 * 
 * This class - Game
 * 
 * 
 * Purpose: Brings together all objects of the game, 
 * sets up the game stage, movement, help menu, and
 * run
 *
 */

import Obstacle.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import Inventory.*;

public class Game
{
	private String Username;
	private int gameStage;
	private Player currentPlayer;
	private Room currentRoom;
	private ArrayList<Room> gameRooms;
	private ArrayList<Enemy> gameEnemies;
	private ArrayList<Puzzle> gamePuzzles;
	private ArrayList<Inventory> gameItems;
	private String helpList;
	Random random = new Random();
	Scanner scan = new Scanner(System.in);

	public Game()
	{
		gameStage = 0;
		currentPlayer = new Player();
		currentRoom = new Room();
	}

	public void displayHelp()
	{
		System.out.println(helpList);
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

	public int getGameStage()
	{
		return gameStage;
	}

	public void setGameStage(int gameStage)
	{
		this.gameStage = gameStage;
	}

	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}

	public Room getGameCurrentRoom()
	{
		return currentRoom;
	}

	public void setGameCurrentRoom(Room currentRoom)
	{
		this.currentRoom = currentRoom;
	}

	public void listener() {
		System.out.print("> ");
		String input = scan.next();
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
		case "f":
			//what if roomEnemy is null?
			if(!currentRoom.getRoomEnemy().enemyIsDead()){
				currentRoom.getRoomEnemy().fight(currentPlayer);
			}
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
    
    public String getUsername()
	{
		return Username;
	}

	public void setUsername(String username)
	{
		Username = username;
	}

	public ArrayList<Inventory> getGameItems()
	{
		return gameItems;
	}

	public void setGameItems(ArrayList<Inventory> gameItems)
	{
		this.gameItems = gameItems;
	}

}