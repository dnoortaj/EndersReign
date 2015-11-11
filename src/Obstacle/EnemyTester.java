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
		}
		return badGuy.getPlayer();
	}
	
	//public Enemy(int iD, String name, int hp, int attack, int dodge, 
	//		Item reward, int points, Array hitList[]) {
	
	public static void main(String[] args) {
		Inventory inv = new Inventory();
		
		Player currentPlayer = new Player();
		currentPlayer.setPlayerInventory(inv);
		inv.setOwner(currentPlayer);
		Object o = new Object();
		
		Enemy plucifer = new Enemy(01, "Plucifer", 50, 10, 10, o, 10, hitOutput);

		plucifer.fight(currentPlayer);
		currentPlayer = playerUpdate(plucifer);
		System.out.println("You have returned from combat.");
		System.out.println("Player score is now " + currentPlayer.getPlayerScore());
		System.out.println("Player HP is " + currentPlayer.getPlayerCurrentHP());
	}

}