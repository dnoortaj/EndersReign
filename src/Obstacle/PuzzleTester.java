/**
 * @author Ethan Patterson
 *
 */
package Obstacle;

import Inventory.Item;
import UserInteraction.Player;
import Inventory.*;

/**
 * @author Ethan Patterson
 *
 */
public class PuzzleTester
{

	public static void main(String[] args) 
	{
		String[][] puzzle = new String[3][5];

		puzzle[0][0] = "'You are in the womb and the umbilical cord is wrapped around your neck. How do you escape?'\n";
		puzzle[0][1] = "You can either: \n'a' \tStruggle to get free \n'b' \tKeep as still as possible \n'c' \tEat your way out\n";
		puzzle[0][2] = "\n'You escape the clutches of death...even though technically you weren't born yet'\n";
		puzzle[0][3] = "\n'You are stillborn'\n";
		puzzle[0][4] = "b";
		
		String[][] puzzle2 = new String[3][5];

		puzzle2[0][0] = "'You answer me, but I never ask you a question. What am I?'\n";
		puzzle2[0][1] = "Choose either: \n'a' \tA question \n'b' \tA phone \n'c' \tA crying baby\n";
		puzzle2[0][2] = "\n'Took you long enough...'\n";
		puzzle2[0][3] = "\n'Maybe try being a little less stupid?'\n";
		puzzle2[0][4] = "b";
		puzzle2[1][0] = "'What can travel around the world while staying in a corner?'\n";
		puzzle2[1][1] = "Choose either: \n'a' \tLight \n'b' \tThe Moon \n'c' \tA Stamp\n";
		puzzle2[1][2] = "\n'Impressive!'\n";
		puzzle2[1][3] = "\n'You have failed me for the last time'\n";
		puzzle2[1][4] = "c";

		//added this to get rid of errors
		String[] output = {"yeah some stuff", "and more stuff"};
		
		Player currentPlayer = new Player();
		Weapon wep = new Weapon("AWESOMESWORD", "IT'S AWESOME", false, 9999, output);
		Puzzle runner = new Puzzle(false, puzzle, wep, 5, 0, false);
		runner.solvePuzzle();
		currentPlayer.addToScore(runner.getPuzzlePoints());
		
		Weapon wep2 = new Weapon("DIAMONDSWORD", "EPICOCITY", false, 9999, output);
		Puzzle runner2 = new Puzzle(false, puzzle2, wep2, 5, 0, false);
		runner2.solvePuzzle();
		currentPlayer.addToScore(runner2.getPuzzlePoints());
		System.out.println("The Player's score is now " + currentPlayer.getPlayerScore()+" points");
	}

}