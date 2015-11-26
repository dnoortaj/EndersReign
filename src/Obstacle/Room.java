package Obstacle;

import java.io.Serializable;

/** Class: Room.java
 * @author Danyelle Noortajalli
 * @version 1.0
 * Course: ITEC 3150 Fall 2015
 * Written: Nov 12, 2015
 * 
 * 
 * This class - Room
 * 
 * 
 * Purpose: creates a room with the current player, any possible enemies,
 * any possible puzzles, and a description
 *
 */
@SuppressWarnings("serial")
public class Room implements Serializable
{
	private String roomName;
	private String[] roomDescription = {null, null};
	private Enemy roomEnemy;
	private int roomEnemyChance;
	private Puzzle roomPuzzle;
	private int roomPuzzleChance;
	private Room[] roomExits = {null, null, null, null};
	
	/** Method: Room
	 * 
	 * general constructor
	 */
	public Room()
	{
		roomName = "";
		roomEnemy = new Enemy();
		roomEnemyChance = 0;
		roomPuzzle = new Puzzle();
		roomPuzzleChance = 0;
	}
	
	/** Method: Room
	 * loaded constructor, takes in all attributes of room
	 * 
	 * @param roomName
	 * @param roomDescription
	 * @param roomEnemy
	 * @param roomEnemyChance
	 * @param roomPuzzle
	 * @param roomPuzzleChance
	 */
	
	public Room(String roomName, String[] roomDescription, Enemy roomEnemy, int roomEnemyChance, 
			Puzzle roomPuzzle, int roomPuzzleChance, Room[] roomExits)
	{
		this.roomName = roomName;
		this.roomDescription = roomDescription;
		this.roomEnemy = roomEnemy;
		this.roomEnemyChance = roomEnemyChance;
		this.roomPuzzle = roomPuzzle;
		this.roomPuzzleChance = roomPuzzleChance;
		this.roomExits = roomExits;
	}
	
	/**
	 * Method: look
	 * allows player to see around the room, gives second description
	 * of roomDescription
	 */
	public void look()
	{
		System.out.println(this.getRoomDescription(1));
	}
	
	/**
	 * Method: setRoomName
	 * sets the roomName for the room
	 *  @param roomName
	 */
	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}
	
	/**
	 * Method: setRoomDescription
	 * sets both Strings in roomDescription
	 *  @param roomDescription
	 */
	public void setRoomDescription(String[] roomDescription)
	{
		this.roomDescription = roomDescription;
	}

	/**
	 * Method: setRoomEnemy
	 * sets the roomEnemy in a room
	 *  @param roomEnemy
	 */
	public void setRoomEnemy(Enemy roomEnemy)
	{
		this.roomEnemy = roomEnemy;
	}

	/**
	 * Method: setRoomEnemyChance
	 * sets the chance of a roomEnemy engaging in combat
	 *  @param roomEnemyChance
	 */
	public void setRoomEnemyChance(int roomEnemyChance)
	{
		this.roomEnemyChance = roomEnemyChance;
	}

	/**
	 * Method: setRoomPuzzle
	 * setter for roomPuzzle
	 *  @param roomPuzzle
	 */
	public void setRoomPuzzle(Puzzle roomPuzzle)
	{
		this.roomPuzzle = roomPuzzle;
	}

	/**
	 * Method: setRoomPuzzleChance
	 * setter for roomPuzzleChance
	 *  @param roomPuzzleChance
	 */
	public void setRoomPuzzleChance(int roomPuzzleChance)
	{
		this.roomPuzzleChance = roomPuzzleChance;
	}

	/**
	 * Method: getRoomExits
	 * gets roomExit in array
	 *  @param n
	 *  @return
	 */
	public Room getRoomExits(int n)
	{
		return roomExits[n];
	}

	/**
	 * Method: setRoomExits
	 * setter for roomExits
	 *  @param roomExits
	 */
	public void setRoomExits(Room[] roomExits)
	{
		this.roomExits = roomExits;
	}
	
	/**
	 * Method: getRoomName
	 * gets roomName
	 *  @return
	 */
	public String getRoomName()
	{
		return roomName;
	}
	
	/**
	 * Method: getRoomDescription
	 * retrieves roomDescription based on the position in the array
	 *  @param number
	 *  @return
	 */
	public String getRoomDescription(int number)
	{
		return roomDescription[number];
	}
	
	/**
	 * Method: getRoomEnemy
	 * getter for roomEnemy
	 *  @return
	 */
	public Enemy getRoomEnemy()
	{
		return roomEnemy;
	}
	
	/**
	 * Method: getRoomEnemyChance
	 * getter for chance of roomEnemy engaging in combat
	 *  @return
	 */
	public int getRoomEnemyChance()
	{
		return roomEnemyChance;
	}
	
	/**
	 * Method: getRoomPuzzle
	 * getter for roomPuzzle
	 *  @return
	 */
	public Puzzle getRoomPuzzle()
	{
		return roomPuzzle;
	}
	
	/**
	 * Method: getRoomPuzzleChance
	 * getter for chance of a puzzle in a room
	 *  @return
	 */
	public int getRoomPuzzleChance()
	{
		return roomPuzzleChance;
	}
}
