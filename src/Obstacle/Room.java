package Obstacle;

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
public class Room
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
			Puzzle roomPuzzle, int roomPuzzleChance)
	{
		this.roomName = roomName;
		this.roomDescription = roomDescription;
		this.roomEnemy = roomEnemy;
		this.roomEnemyChance = roomEnemyChance;
		this.roomPuzzle = roomPuzzle;
		this.roomPuzzleChance = roomPuzzleChance;
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
	
	public void setRoomName(String roomName)
	{
		this.roomName = roomName;
	}
	
	public void setRoomDescription(String[] roomDescription)
	{
		this.roomDescription = roomDescription;
	}

	public void setRoomEnemy(Enemy roomEnemy)
	{
		this.roomEnemy = roomEnemy;
	}

	public void setRoomEnemyChance(int roomEnemyChance)
	{
		this.roomEnemyChance = roomEnemyChance;
	}

	public void setRoomPuzzle(Puzzle roomPuzzle)
	{
		this.roomPuzzle = roomPuzzle;
	}

	public void setRoomPuzzleChance(int roomPuzzleChance)
	{
		this.roomPuzzleChance = roomPuzzleChance;
	}

	public Room getRoomExits(int n)
	{
		return roomExits[n];
	}
	
	public void setRoomExits(Room[] roomExits)
	{
		this.roomExits = roomExits;
	}
	
	public String getRoomName()
	{
		return roomName;
	}
	
	public String getRoomDescription(int number)
	{
		return roomDescription[number];
	}
	
	public Enemy getroomEnemy()
	{
		return roomEnemy;
	}
	
	public int getRoomEnemyChance()
	{
		return roomEnemyChance;
	}
	
	public Puzzle getRoomPuzzle()
	{
		return roomPuzzle;
	}
	
	public int getRoomPuzzleChance()
	{
		return roomPuzzleChance;
	}
}
