package UserInteraction;

/**
 * @author Ethan Patterson
 *
 */
public class PlayerTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Player playa = new Player();
		playa.addToScore(15);
		
		System.out.println("The first score is: " + playa.getPlayerScore() + " points");
		
		Player playa2 = new Player();
		playa2.addToScore(25);
		
		System.out.println("The second score is: " + playa2.getPlayerScore() + " points");
		System.out.println("The scores added together: " + (playa.getPlayerScore() + playa2.getPlayerScore()) + " points");

	}

}
