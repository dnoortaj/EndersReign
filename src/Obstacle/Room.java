package Obstacle;

public class Room
{
	private String roomDescription;
	private Enemy roomEnemy;
	private int roomEnemyChance;
	private Puzzle roomPuzzle;
	private int roomPuzzleChance;
	private Room[] roomExits;
	
	public Room()
	{
		roomDescription = "";
		roomEnemy = new Enemy();
		roomEnemyChance = 0;
		roomPuzzle = new Puzzle();
		roomPuzzleChance = 0;
		roomExits = new Room[4];
	}
	
	public void look()
	{
		
	}
	
	public void setRoomDescription(String roomDescription)
	{
		this.roomDescription = roomDescription;
	}

	public void setroomEnemy(Enemy roomEnemy)
	{
		this.roomEnemy = roomEnemy;
	}

	public void setroomEnemyChance(int roomEnemyChance)
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
	
	public String getRoomDescription()
	{
		return roomDescription;
	}
	
	public Enemy getroomEnemy()
	{
		return roomEnemy;
	}
	
	public int getroomEnemyChance()
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
