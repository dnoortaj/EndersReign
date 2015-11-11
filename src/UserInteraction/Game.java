package UserInteraction;

import Obstacle.*;

import java.util.ArrayList;

import Inventory.*;

public class Game 
{
	private int gameStage;
	private Player currentPlayer;
	private Room gameCurrentRoom;
	private ArrayList<Enemy> gameEnemies;
	private ArrayList<Puzzle> gamePuzzles;
	private boolean oxygenFlag = false;
	private boolean suitFlag = false;
	private boolean tabletFlag = false;
	private boolean battleFlag = false;
	
	public boolean isOxygenFlag() {
		return oxygenFlag;
	}

	public void setOxygenFlag(boolean oxygenFlag) {
		this.oxygenFlag = oxygenFlag;
	}

	public boolean isSuitFlag() {
		return suitFlag;
	}

	public void setSuitFlag(boolean suitFlag) {
		this.suitFlag = suitFlag;
	}

	public boolean isTabletFlag() {
		return tabletFlag;
	}

	public void setTabletFlag(boolean tabletFlag) {
		this.tabletFlag = tabletFlag;
	}

	public boolean isBattleFlag() {
		return battleFlag;
	}

	public void setBattleFlag(boolean battleFlag) {
		this.battleFlag = battleFlag;
	}

	public Game()
	{
		gameStage = 0;
		currentPlayer = new Player();
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
		return gameCurrentRoom;
	}

	public void setGameCurrentRoom(Room gameCurrentRoom)
	{
		this.gameCurrentRoom = gameCurrentRoom;
	}

}
