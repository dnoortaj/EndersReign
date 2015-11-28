package Obstacle;
import Inventory.*;

import java.io.Serializable;
import java.util.Scanner;

/**
 * @author Ethan Patterson
 * 
 *         Class Description: The puzzle classes contain the puzzle challenges. The individual
 *         puzzles will be custom written. The instances of puzzles will be determined by the Room
 *         classes. The puzzle object prompt, answering/response system, and reward functionality
 *         will be handled within this class.
 */
@SuppressWarnings("serial")
public class Puzzle implements Serializable
{
	private boolean puzzleIsCompleted = false;
	private String[][] puzzleText = new String[3][5];
	private Item puzzleReward;
	private int questionWeight = 0;
	private int puzzlePoints = 0;
	private boolean keyPuzzle = false;
	transient private Scanner input;

	public Puzzle()
	{
		puzzleIsCompleted = false;
		puzzleText = new String[3][5];
		questionWeight = 0;
		puzzlePoints = 0;
		puzzleReward = null;
		setKeyPuzzle(false);
	}

	/**
	 * General constructor used to instantiate puzzle objects. Sets state values for
	 * puzzleIsCompleted, puzzleText, puzzleReward, and puzzlePoints.
	 */
	public Puzzle(boolean puzzleIsCompleted, String[][] puzzleText,
			Item puzzleReward, int questionWeight, int puzzlePoints, boolean keyPuzzle)
	{
		this.puzzleIsCompleted = puzzleIsCompleted;
		this.puzzleText = puzzleText;
		this.questionWeight = questionWeight;
		this.puzzleReward = puzzleReward;
		this.puzzlePoints = puzzlePoints;
		this.setKeyPuzzle(keyPuzzle);
	}

	/**
	 * This method will encapsulate the entire puzzle challenge process including prompting the
	 * puzzle, receiving user response, and checking correctness. Upon completion it will dole out
	 * the appropriate reward and points.
	 */
	public int solvePuzzle()
	{
		input = new Scanner(System.in);
		for (int i = 0; i < 3; i++)
		{
			if(puzzleText[i][0] != null)
			{
				System.out.println(puzzleText[i][0]);
				System.out.println(puzzleText[i][1]);
				String answer = input.nextLine();

				while (!answer.equalsIgnoreCase("a") && !answer.equalsIgnoreCase("b") && !answer.equalsIgnoreCase("c"))
				{
					System.out.println("Your input was invalid.\nPlease try again.");
					answer = input.nextLine();
				}
				if (answer.equals(puzzleText[i][4]))
				{
					System.out.println(puzzleText[i][2]);
					puzzlePoints += questionWeight;

				} else
				{
					System.out.println(puzzleText[i][3]);
				}
			}

		} 
		puzzleIsCompleted = true;
		return puzzlePoints;
	}

	/**
	 * @return Returns a boolean value that describes whether the puzzle has been completed.
	 */
	public boolean getPuzzleIsCompleted()
	{
		return puzzleIsCompleted;
	}

	/**
	 * @param puzzleIsCompleted the puzzleIsCompleted to set
	 * 
	 * Description: Toggles the puzzleIsComplete state value.
	 */
	public void setPuzzleIsCompleted(boolean puzzleIsCompleted)
	{
		this.puzzleIsCompleted = puzzleIsCompleted;
	}


	/**
	 * @return the puzzleReward
	 * 
	 *         Description: Returns the reward granted for successful completion of a puzzle
	 *         challenge.
	 */
	public Item getPuzzleReward()
	{
		return puzzleReward;
	}

	/**
	 * @return the puzzlePoints
	 * 
	 *         Description: Returns the points that remain after the completion of a puzzle
	 *         challenge.
	 */
	public int getPuzzlePoints()
	{
		return puzzlePoints;
	}

	/**
	 * @return whether or not this puzzle can be failed
	 * 
	 *         Description: Returns the puzzle's keyPuzzle status.
	 */
	public boolean isKeyPuzzle() 
	{
		return keyPuzzle;
	}

	/**
	 * @param whether or not the puzzle may be failed
	 * 
	 *         Description: Sets the puzzle's keyPuzzle status
	 */
	public void setKeyPuzzle(boolean keyPuzzle) 
	{
		this.keyPuzzle = keyPuzzle;
	}

	/**
	 * @return how many points each question in 
	 *         the puzzle is worth
	 * 
	 *         Description: Returns how many points each correct
	 *         answer in this puzzle is worth.
	 */
	public int getQuestionWeight() {
		return questionWeight;
	}

	/**
	 * @param the point weight of each puzzle part
	 * 
	 *         Description: Sets how many points each question in 
	 *         the puzzle is worth.
	 */
	public void setQuestionWeight(int questionWeight) {
		this.questionWeight = questionWeight;
	}

}