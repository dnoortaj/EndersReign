package Obstacle;
import java.util.Random;
import java.util.Scanner;

import Inventory.*;
import UserInteraction.*;


public class EnemyTester {
	
	static String [] hitOutput = new String [] {"barely grazed", "scored a major hit on", 
			"landed a solid strike on", "whacked the crap out of"};
	
	static String [] taunt = new String [] {"9", "You take a kungfu stance and grin menacingly. \n"
			+ "Two of the bullies show their true colors and flee.", "Plucifer",
			"You do your best to taunt", "You flaunt your puny muscles.", 
			"You expell flatulence in the general direction of your foe.",
			"is mildly amused that you thought that would have any affect.",
			"is dumbfounded.", "is disgusted but unmoved."};

	
	//"You wave your arms wildly in the air." " is not impressed." "You shout obsenities at " +
	//sentence;
//	"Offended, uses this as an" + " opportunity to take a free hit on you."
//	"You bite your thumb at " + eName + "."
//	"Undetered, " + eName + " makes a comment about"
//					+ " you being the son of a motherless goat."
	
	public static Player playerUpdate(Enemy badGuy){
		if(badGuy.enemyIsDead()){
			badGuy.getPlayer().addToScore(badGuy.getPoints());
			badGuy.getPlayer().getPlayerInventory().addToInventory(badGuy.getReward());
		}
		return badGuy.getPlayer();
	}
	

	public static void main(String[] args) {

		

		Inventory inv = new Inventory();
		Consumable potion = new Consumable("drugs", "good drugs", false, 40);
		inv.addToInventory(potion);
		Player currentPlayer = new Player("Bobby", 7, 65, 65, 20, 10, 10, inv);
		currentPlayer.setPlayerInventory(inv);
		inv.setOwner(currentPlayer);
		Weapon yourNameHere = new Weapon("Cudgle", "Crude beating stick", false, 12);
	
		Enemy plucifer = new Enemy(01, "Plucifer and two of his coherts", 50, 19, 
				10, yourNameHere, 10, hitOutput, taunt);

		plucifer.fight(currentPlayer);
		currentPlayer = playerUpdate(plucifer);
		System.out.println("You have disengaged from combat.");
		System.out.println("Player score is now " + currentPlayer.getPlayerScore());
		System.out.println("Player HP is " + currentPlayer.getPlayerCurrentHP());
		currentPlayer.getPlayerInventory().useItem();
		currentPlayer.getPlayerInventory().useItem();
	}

}