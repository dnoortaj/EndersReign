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
	//	public Player playerUpdate(Puzzle puzz, Player playa)
	//	{
	//		playa.addToScore(puzz.getPuzzlePoints());
	//		if(puzz.getPuzzleIsCompleted())
	//		{
	//			playa.addToScore(puzz.getPuzzlePoints());
	//		}
	//		return playa;
	//	}


	// public Puzzle(boolean puzzleIsCompleted, String[][] puzzleText,
	// Item puzzleReward, int puzzlePoints)

	public static void main(String[] args) 
	{
		String[][] puzzle = new String[3][5];

		puzzle[0][0] = "'You're running a race and pass the person in 2nd place. What place are you in now?'\n";
		puzzle[0][1] = "Choose either: \n'a' \t2nd place \n'b' \t1st place \n'c' \tNot possible\n";
		puzzle[0][2] = "\n'Great Job!'\n";
		puzzle[0][3] = "\n'Not quite'\n";
		puzzle[0][4] = "a";
		puzzle[1][0] = "'Imagine you are in a dark room. How do you get out?'\n";
		puzzle[1][1] = "Choose either: \n'a' \tTurn on the lights \n'b' \tStop Imagining \n'c' \tCall in an AC-130\n";
		puzzle[1][2] = "\n'Way 2 go!'\n";
		puzzle[1][3] = "\n'Looks like you won't be getting out'\n";
		puzzle[1][4] = "b";
		puzzle[2][0] = "'Who makes it, but has no need of it.\n"
					+  " Who buys it, but has no use for it.\n"
					+  " Who uses it but can neither see nor feel it.\n"
					+  " What is it?'\n";
		puzzle[2][1] = "Choose either: \n'a' \tMoney \n'b' \tCalculus \n'c' \tA coffin\n";
		puzzle[2][2] = "\n'Everything is awesome!'\n";
		puzzle[2][3] = "\n'A swing and a miss'a\n";
		puzzle[2][4] = "c";
		
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
		puzzle2[2][0] = "'Johnny's mother had three children. "
					+  "The first was named April and the second was named May. "
					+  "What was the name of the third child?'\n";
		puzzle2[2][1] = "Choose either: \n'a' \tJune \n'b' \tJohnny \n'c' \tA John Cena\n";
		puzzle2[2][2] = "\n'You have chosen wisely!'\n";
		puzzle2[2][3] = "\n'Does your ignorance know no bounds...'a\n";
		puzzle2[2][4] = "b";

		Player currentPlayer = new Player();
		Weapon wep = new Weapon("AWESOMESWORD", "IT'S AWESOME", false, 9999);
		Puzzle runner = new Puzzle(false, puzzle, wep, 0);
		runner.solvePuzzle();
		currentPlayer.addToScore(runner.getPuzzlePoints());
		
		Weapon wep2 = new Weapon("DIAMONDSWORD", "EPICOCITY", false, 9999);
		Puzzle runner2 = new Puzzle(false, puzzle2, wep2, 0);
		runner2.solvePuzzle();
		currentPlayer.addToScore(runner2.getPuzzlePoints());
		System.out.println("The Player's score is now " + currentPlayer.getPlayerScore()+" points");
	}

}
