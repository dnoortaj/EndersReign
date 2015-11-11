package UserInteraction;

import Obstacle.*;
import Inventory.*;

public class Game
{
	private int gameStage;
	private Player gamePlayer;
	private Room gameCurrentRoom;

	public Game()
	{
		gameStage = 0;
		gamePlayer = new Player();
		gameCurrentRoom = new Room();
	}
	
	public void displayHelp()
	{
		
	}
	
	public void move()
	{
		
	}
	
	public int getGameStage()
	{
		return gameStage;
	}

	public void setGameStage(int gameStage)
	{
		this.gameStage = gameStage;
	}

	public Player getGamePlayer()
	{
		return gamePlayer;
	}

	public void setGamePlayer(Player gamePlayer)
	{
		this.gamePlayer = gamePlayer;
	}

	public Room getGameCurrentRoom()
	{
		return gameCurrentRoom;
	}

	public void setGameCurrentRoom(Room gameCurrentRoom)
	{
		this.gameCurrentRoom = gameCurrentRoom;
	}

}
