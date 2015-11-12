package Obstacle;

import java.util.Random;
import java.util.Scanner;
import Inventory.*;
import UserInteraction.*;


public class Enemy {

	private boolean enemyIsDead, inBattle, firstTaunt;
	private String eName;
	private int eHP, eDodge, eAttack, ePoints, eID;
	//private int pHP, pMaxHP, pDodge, pAttack;

	private Item eReward;
	
	Scanner s = new Scanner(System.in);
	Random rand = new Random();
	String [] hitOutput = new String[4];
	String [] taunt = new String[7];
	Player playa;
	
	public Enemy(){
		this.eID = 00;
		eName = "Bob";
		this.eHP = 1;
		this.eAttack = 1;
		this.eDodge = 1;
		this.eReward = eReward;
		this.ePoints = 1;
		hitOutput = hitOutput;
		enemyIsDead = false;
		inBattle = false;
		firstTaunt = true;
	}
	public Enemy(int iD, String name, int hp, int attack, int dodge, 
			Item reward, int points, String[] hitList, String[] taunt) {
		this.eID = iD;
		eName = name;
		this.eHP = hp;
		this.eAttack = attack;
		this.eDodge = dodge;
		this.eReward = reward;
		this.ePoints = points;
		hitOutput = hitList;
		this.taunt = taunt;
		enemyIsDead = false;
		inBattle = false;
		firstTaunt = true;
	}

	public void fight(Player p) {
		//pMaxHP = playa.getPlayerMaxHP();
		//pHP = playa.;
		//pDodge = dodge;
		//pAttack = attack;
		playa = p;
		inBattle = true;
		System.out.println("You have engaged in combat with " + eName + "."+
				" (Press H for Help)");
		listener();
	}

	public void enemyAttack() {
		if(hitMiss(playa.getPlayerDodge())){
			int damage = damage();
			double damageDescription = damage / 10;
			Long L = Math.round(damageDescription);
			int i = Integer.valueOf(L.intValue());
			Double doubDamage = (double)damage;
			Double actualDamage = (eAttack *((doubDamage / 100) + .7));
			playa.setPlayerCurrentHP((int)(playa.getPlayerCurrentHP() - actualDamage));
			System.out.println(eName + " " + hitOutput[i] + " you. " + 
					actualDamage.intValue() + " damage leaves you with " +
					playa.getPlayerCurrentHP() + " hit points.");
			playerIsAlive();
		}
		else{
			System.out.println(eName + " tried to hit you but failed "
					+ "to make contact.");
			listener();
		}
	}

	public void playerAttack() {
		if(hitMiss(eDodge)){
			int damage = damage();
			int damageDescription = damage / 10;
			Double doubDamage = (double)damage;
			Double actualDamage = (playa.getPlayerAttack() *((doubDamage / 100) + .7));
			eHP = (int)(eHP - actualDamage);
			System.out.println("You " + hitOutput[damageDescription] + " " +
				eName + ", inflicting " + actualDamage.intValue() + " points of damage.");
			tempo(850);
			enemyIsAlive();
		}
		else{
			System.out.println("You tried to hit " + eName + " but missed.");
			tempo(50);
			enemyAttack();
		}
	}
	
	public void tempo(int time){
		try {
		    Thread.sleep(time);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	public boolean hitMiss(int dodge){
		int chance = rand.nextInt(15);
		if(chance <= dodge){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int damage(){
		return rand.nextInt(40);
	}
	
	public void taunt(){
		int temp = rand.nextInt(3) + 3;
		if(Integer.parseInt(taunt[0]) > 0 && firstTaunt ){
			eAttack = eAttack - Integer.parseInt(taunt[0]);
			eName = taunt[2];
			System.out.println(taunt[1]);
			firstTaunt = false;
			listener();
		}
		else{
		if(temp == 3){
			System.out.println(taunt[3] + " " + eName + ".");
			tempo(650);
			System.out.println(eName + " " + taunt[6]);
			listener();
		}
		else if(temp == 4){
			System.out.println(taunt[4]);
			tempo(550);
			System.out.println(eName + " " + taunt[7]);
			enemyAttack();
		}
		else if(temp == 5){
			System.out.println(taunt[5] + " " + eName + ".");
			tempo(550);
			System.out.println(eName + " " + taunt[8]);
			tempo(350);
			enemyAttack();		
		}
	}
}

	public void escape() {
		if(rand.nextInt(playa.getPlayerDodge()) > (rand.nextInt(eDodge) + 3)){
			System.out.println("As the French so often do, you turned tail"
					+ " and ran like the coward that you are. ");
			System.out.println(eName + " doesn't seem to know where you went.");
			ePoints = ePoints - (ePoints / 5);
		}
		else{
			System.out.println(eName + " proved too quick, your attempt to flee"
					+ " failed miserably.");
			ePoints = ePoints - (ePoints / 5);
			enemyAttack();
		}
	}
	
	public Boolean enemyIsDead() {
		return enemyIsDead;
	}
	
	public void enemyIsAlive() {
		if(eHP <= 0){
			System.out.println(eName + " is dead.");
			enemyIsDead = true;
			inBattle = false;
		}
		else{
			enemyAttack();
		}
	}
	

	public void playerIsAlive(){
		if(playa.getPlayerCurrentHP() <= 0){
			System.out.println("You are dead.");
		}
		else{
			listener();
		}
	}
	
	public void useItem() {
		if(playa.getPlayerInventory().useItem()){
			enemyAttack();
		}
		else{
			listener();
		}
	}
	
	public Item getReward(){
		return eReward;
	}
	
	public void helpMenu(){
		System.out.println("A - Attack    E - Escape    T - Taunt    I - Inventory");
		System.out.println(playa.getPlayerCurrentHP() + "/" + playa.getPlayerMaxHP() + " HP");
		listener();
	}
	
	public boolean inBattle() {
		return inBattle;
	}

	public Player getPlayer(){
		return playa;
	}
	public int getPoints(){
		return ePoints;
	}
	public void listener() {
		System.out.print(">");
		String input = s.next();
		switch (input.toLowerCase()) {
        case "a":
        	playerAttack();
        	break;
        case "t":
        	taunt();
        	break;
        case "e":
        	escape();
        	break;
        case "h":
        	helpMenu();
        	break;
        case "i":
        	useItem();
        	break;
        case "u":
        	System.out.println("That command is not valid whilst engaged"
        			+ " in combat.");
        	listener();
        	break;
        default:
            System.out.println("Command not recognized.");
            listener();
            break;
	}
}
}
