package Obstacle;
import java.util.Random;
import java.util.Scanner;

import Inventory.*;
import UserInteraction.*;


public class EnemyTester {
	

	
	//"You wave your arms wildly in the air." " is not impressed." "You shout obsenities at " +
	//sentence;
//	"Offended, uses this as an" + " opportunity to take a free hit on you."
//	"You bite your thumb at " + eName + "."
//	"Undetered, " + eName + " makes a comment about"
//					+ " you being the son of a motherless goat."
	

	public Player playerUpdate(Enemy badGuy){
		if(badGuy.enemyIsDead()){
			badGuy.getPlayer().addToScore(badGuy.getPoints());
			badGuy.getPlayer().getPlayerInventory().addToInventory(badGuy.getReward());
		}
		return badGuy.getPlayer();
	}

	public static void main(String[] args) {

		EnemyTester et = new EnemyTester();
		

		
		String [] hitOutput = new String [] {"barely grazed", "scored a major hit on", 
				"landed a solid strike on", "whacked the crap out of"};
		
		String [] tauntFlee = new String [] {"attDown", "10", "You take a kungfu stance "
				+ "and grin menacingly. \n"
				+ "Two of the bullies show their true colors and flee.", "Jerry",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any affect.",
				"is dumbfounded.", "is disgusted but unmoved."};
		
		/*********************************************************************
		taunt String array description
		
		0- describes special taunt cases "tauntFlee" alters attack upon first taunt
		by reducing number of opponents, tauntEnrage increases enemy attack to kill,
			"tauntHide" alters dodge, " anything else taunt has no real consequence
		1- how much the taunt type is altered
		2- string output for special taunt case
		3- new enemy name for "a" special taunt case 
				taunt # corresponds with response #
		4- taunt 1, should end open for enemy name eg. "You shout at" place name here.
		5- taunt 2, closed sentence
		6- taunt 3, open ended for enemy name
		7- response 1, begins open for enemy name eg. enemy name here "is angered."
		8- response 2, begins open
		9- response 3, begins open
		*********************************************************************/
		String [] tauntEnrage = new String [] {"enrage", "50", "You show your opponent "
				+ "the full moon. \nPerhaps that wasn't your best move yet, now he is "
				+ "really mad.", 
				"ENRAGED Mazer Rackham", "You do your best to taunt", 
				"You flaunt your puny muscles.", "",
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any affect.",
				 "is like full-on Super Seiyan berserker mode mad right now."};
		
		String [] tauntHide = new String [] {"dodgeDown", "100", "You shout insults about your foes "
				+ "maternal unit. \n"
				+ "Enraged, he is done playing the hiding game.", "Plucifer",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any affect.",
				"is dumbfounded.", "is disgusted but unmoved."};


		Inventory inv = new Inventory();
		Consumable potion = new Consumable("drugs", "good drugs", false, 40);
		inv.addToInventory(potion);
		Player currentPlayer = new Player("Bobby", 7, 65, 65, 20, 8, 10, inv);
		currentPlayer.setPlayerInventory(inv);
		inv.setOwner(currentPlayer);
		Weapon yourNameHere = new Weapon("Cudgle", "Crude beating stick", false, 12);
	
		Enemy mazer = new Enemy(01, "Mazer Rackham", 70, 15, 
				10, yourNameHere, 10, hitOutput, tauntEnrage);
		
		Enemy plucifer = new Enemy(01, "Plucifer", 30, 10, 
				110, yourNameHere, 10, hitOutput, tauntHide);
		
		Enemy bullies = new Enemy(01, "Jerry and two of his cohorts", 50, 19, 
				10, yourNameHere, 10, hitOutput, tauntFlee);

		mazer.fight(currentPlayer);
		plucifer.fight(currentPlayer);
		currentPlayer = et.playerUpdate(plucifer);
		System.out.println("You have disengaged from combat.");
		System.out.println("Player score is now " + currentPlayer.getPlayerScore());
		System.out.println("Player HP is " + currentPlayer.getPlayerCurrentHP());
		
		bullies.fight(currentPlayer);
		currentPlayer = et.playerUpdate(bullies);
		currentPlayer.getPlayerInventory().useItem();
		currentPlayer.getPlayerInventory().useItem();
	}

}