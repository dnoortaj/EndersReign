package UserInteraction;

import Obstacle.*;

import java.util.ArrayList;
import java.util.Scanner;

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
	Scanner scan = new Scanner(System.in);
	
	public Game()
	{
		gameStage = 0;
		currentPlayer = new Player();
		gameCurrentRoom = new Room();
	}
	
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

	public void listener() {
		System.out.print("> ");
		String input = scan.next();
		switch (input.toLowerCase()) {
        case "w":
        	move();
        	break;
        case "s":
        	move();
        	break;
        case "a":
        	move();
        	break;
        case "d":
        	move();
        	break;
        case "f":
        	//fight the things
        	break;
        case "h":
        	displayHelp();
        	break;
        case "i":
        	currentPlayer.getPlayerInventory().useItem();
        	break;
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
}