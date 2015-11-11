package Obstacle;
import java.util.Random;
import java.util.Scanner;

import Inventory.*;
import UserInteraction.*;


public class EnemyTester {
	
	static String [] hitOutput = new String [] {"barely grazed", "scored a major hit on", 
			"landed a solid strike on", "whacked the crap out of"};
	
	public static Player playerUpdate(Enemy badGuy){
		if(badGuy.enemyIsDead()){
			badGuy.getPlayer().addToScore(badGuy.getPoints());
			badGuy.getPlayer().getPlayerInventory().addToInventory(badGuy.getReward());
		}
		return badGuy.getPlayer();
	}
	
	//public Enemy(int iD, String name, int hp, int attack, int dodge, 
	//		Item reward, int points, Array hitList[]) {
	
	
	
	
	public static void main(String[] args) {
		Inventory inv = new Inventory();
		Consumable potion = new Consumable("drugs", "good drugs", false, 40);
		inv.addToInventory(potion);
		Player currentPlayer = new Player("Bobby", 7, 65, 65, 20, 10, 10, inv);
		currentPlayer.setPlayerInventory(inv);
		inv.setOwner(currentPlayer);
		Weapon yourNameHere = new Weapon("Cudgle", "Crude beating stick", false, 12);
	
		Enemy plucifer = new Enemy(01, "Plucifer", 50, 10, 10, yourNameHere, 10, hitOutput);

		plucifer.fight(currentPlayer);
		currentPlayer = playerUpdate(plucifer);
		System.out.println("You have returned from combat.");
		System.out.println("Player score is now " + currentPlayer.getPlayerScore());
		System.out.println("Player HP is " + currentPlayer.getPlayerCurrentHP());
		currentPlayer.getPlayerInventory().useItem();
		currentPlayer.getPlayerInventory().useItem();
	}

}