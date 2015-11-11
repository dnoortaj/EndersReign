package Obstacle;

public class Room
{
	private String roomName;
	private String[] roomDescription = {null, null};
	private Enemy roomEnemy;
	private int roomEnemyChance;
	private Puzzle roomPuzzle;
	private int roomPuzzleChance;
	private Room[] roomExits = {null, null, null, null};
	
	public Room()
	{
		roomName = "";
		roomEnemy = new Enemy();
		roomEnemyChance = 0;
		roomPuzzle = new Puzzle();
		roomPuzzleChance = 0;
	}
	
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
	
	public void look()
	{
		System.out.println(this.getRoomDescription(1));
		
	}
	
	public void setRoomDescription(String[] roomDescription)
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
	
	public String getRoomDescription(int number)
	{
		return roomDescription[number];
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
