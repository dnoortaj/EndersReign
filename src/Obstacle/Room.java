package Obstacle;

import java.util.ArrayList;

public class Room
{
	private String roomDescription;
	private int roomMonster;
	private int roomMonsterChance;
	private int roomPuzzle;
	private int roomPuzzleChance;
	private ArrayList<Room> roomExits;
	
	public Room()
	{
		roomDescription = "";
		roomMonster = 0;
		roomMonsterChance = 0;
		roomPuzzle = 0;
		roomPuzzleChance = 0;
		roomExits = new ArrayList<Room>();
	}
	
	public void look()
	{
		
	}
	
	public void determineEncounter()
	{
		if (getRoomMonster() > 0)
		{
			Enemy currentEnemy = new Enemy();
			//call for starting the battle...
		}
		else if (getRoomPuzzle() > 0)
		{
			Puzzle currentPuzzle = new Puzzle();
			//call for starting the puzzle..
		}
		else
		{
			
		}
	}
	
	public void setRoomDescription(String roomDescription)
	{
		this.roomDescription = roomDescription;
	}

	public void setRoomMonster(int roomMonster)
	{
		this.roomMonster = roomMonster;
	}

	public void setRoomMonsterChance(int roomMonsterChance)
	{
		this.roomMonsterChance = roomMonsterChance;
	}

	public void setRoomPuzzle(int roomPuzzle)
	{
		this.roomPuzzle = roomPuzzle;
	}

	public void setRoomPuzzleChance(int roomPuzzleChance)
	{
		this.roomPuzzleChance = roomPuzzleChance;
	}

	public ArrayList<Room> getRoomExits()
	{
		return roomExits;
	}
	
	public void setRoomExits(ArrayList<Room> roomExits)
	{
		this.roomExits = roomExits;
	}
	
	public String getRoomDescription()
	{
		return roomDescription;
	}
	
	public int getRoomMonster()
	{
		return roomMonster;
	}
	
	public int getRoomMonsterChance()
	{
		return roomMonsterChance;
	}
	
	public int getRoomPuzzle()
	{
		return roomPuzzle;
	}
	
	public int getRoomPuzzleChance()
	{
		return roomPuzzleChance;
	}
}
