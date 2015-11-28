package Obstacle; 

import java.io.InputStreamReader;
import java.util.Scanner;

import Inventory.*;
import UserInteraction.*;

public class RoomTester
{
	Scanner sc = new Scanner(new InputStreamReader(System.in));
	String sentence;
	
	public void listener(Room room, Player player, Puzzle puzzle, Enemy enemy, String sentence) 
	{
		System.out.print("> ");
		String input = sc.nextLine();
		switch (input.toLowerCase()) {
        case "look":
        	System.out.println(room.getRoomDescription(1));
        	System.out.println(sentence);
    		listener(room, player, puzzle, enemy, sentence);
        	break;
        case "move":
        	if (puzzle != null && puzzle.getPuzzleIsCompleted() != true)
        	{
        		player.addToScore(puzzle.solvePuzzle());
        		System.out.println("You just got " + puzzle.getPuzzlePoints() + " points!");
        		
        		puzzle.setPuzzleIsCompleted(true);
        	}
        	System.out.println("There are no obstacles in your way. \n" + sentence);
    		listener(room, player, puzzle, enemy, sentence);
        	break;
        case "back":
        	if (enemy != null)
        	{
        		enemy.fight(player);
        	}
        	break;
        default:
            System.out.println("Command not recognized.");
            listener(room, player, puzzle, enemy, sentence);
            break;
		}
	}
	
	public static void main(String[] args)
	{
		RoomTester roomTest = new RoomTester();
		Player playa = new Player("Sally", 0, 65, 65, 15, 10, 3, false, false, false, false, null);
		String[] roomDescription = { "You enter the room full of students and tables.", 
				"You look around and notice no one is studying \nand everyone is wasting time playing Magic."};
		String[][] jigsawText = new String[3][5];
		jigsawText[0][0] = "You found a puzzle on a table!\n" + 
				"There are 4 pieces, which one goes in the upper right?\n";
		jigsawText[0][1] = " a. corner piece \n b. middle piece \n c. your only precious";
		jigsawText[0][2] = "\n Yes! It fits!\n";
		jigsawText[0][3] = "\n No! You should be very sad at how dumb you are. \n";
		jigsawText[0][4] = "a";
		jigsawText[1][0] = "There are 3 pieces, which one goes in the middle?\n";
		jigsawText[1][1] = " a. corner piece \n b. middle piece \n c. your middle finger";
		jigsawText[1][2] = "\n Yes! It fits!\n";
		jigsawText[1][3] = "\n No! You should be very sad at how dumb you are. \n";
		jigsawText[1][4] = "b";
		jigsawText[2][0] = "There are 2 pieces, which one goes in your mouth?\n";
		jigsawText[2][1] = " a. corner piece \n b. middle piece \n c. sandwich";
		jigsawText[2][2] = "\n Yummy! That hits the spot.\n";
		jigsawText[2][3] = "\n No! You should be very sad at how dumb you are and now you're starving. \n";
		jigsawText[2][4] = "c";
		String [] hitOutput = new String [] {"barely grazed", "scored a major hit on", 
					"landed a solid strike on", "whacked the crap out of"};
		String[] roomDescription2 = {"You've entered a classroom", "This is a room full of seats and desks. "
				+ "There is no one in here and nothing to do"};
		String[] taunt = {"a", "9", "You take a kungfu stance and grin menacingly. \n"
				+ "Two of the bullies show their true colors and flee.", "Jerry",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any affect.",
				"is dumbfounded.", "is disgusted but unmoved."};
		
		Enemy bully = new Enemy(001, "Bully", 20, 15, 5, null, 5, hitOutput, taunt);
		Puzzle jigsaw = new Puzzle(false, jigsawText,
				null, 5, 0, false);
		Room room1 = new Room("Convocation Area", new String[]{"",""}, 
				bully, 50, jigsaw, 10); 
		Room room2 = new Room("Classroom", roomDescription2, null, 0, null, 0);
		String sentence = ("\n What would you like to do?" +
				"\n> Look around \n> Move forward \n> Go back");
		
		System.out.println(room1.getRoomDescription(0) + sentence);
		roomTest.listener(room1, playa, jigsaw, bully, sentence);
		if (playa.getPlayerCurrentHP() > 0)
		{
			System.out.println(room2.getRoomDescription(0) + sentence);
			roomTest.listener(room2, playa, null, null, sentence);
		}
		
	}
}
