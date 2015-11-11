package Obstacle;

import java.util.Random;
import java.util.Scanner;


public class Enemy {

	private boolean enemyIsDead, inBattle;
	private String eName;
	private int eHP, eDodge, eAttack, ePoints, eID;
	private int pHP, pMaxHP, pDodge, pAttack;
	private Object eReward = new Object();
	//private Inventory Subsystem.Item eReward;
	private Object rewardTest = new Object();
	Scanner s = new Scanner(System.in);
	Random rand = new Random();
	String [] hitOutput = new String[4];
	int in = 0;
	
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
	}
	public Enemy(int iD, String name, int hp, int attack, int dodge, 
			Object reward, int points, String[] hitList) {
		this.eID = iD;
		eName = name;
		this.eHP = hp;
		this.eAttack = attack;
		this.eDodge = dodge;
		this.rewardTest = reward;
		this.ePoints = points;
		hitOutput = hitList;
		enemyIsDead = false;
		inBattle = false;
	}

	public void fight(int max, int current, int attack, int dodge) {
		pMaxHP = max;
		pHP = current;
		pDodge = dodge;
		pAttack = attack;
		inBattle = true;
		System.out.println("You have engaged in combat with " + eName + "."+
				" (Press H for Help)");
		listener();
	}

	public void enemyAttack() {
		if(hitMiss(pDodge)){
			int damage = damage();
			double damageDescription = damage / 10;
			Long L = Math.round(damageDescription);
			int i = Integer.valueOf(L.intValue());
			Double doubDamage = (double)damage;
			Double actualDamage = (eAttack *((doubDamage / 100) + .7));
			pHP = (int)(pHP - actualDamage);
			System.out.println(eName + " " + hitOutput[i] + " you. " + 
					actualDamage.intValue() + " damage leaves you with " +
					pHP + " hit points.");
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
			Double actualDamage = (pAttack *((doubDamage / 100) + .7));
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
		int temp = rand.nextInt(3);
		if(temp == 0){
			System.out.println("You wave your arms wildly in the air.");
			tempo(650);
			System.out.println(eName + " is not impressed.");
			listener();
		}
		else if(temp == 1){
			System.out.println("You shout obsenities at " + eName + ".");
			tempo(750);
			System.out.println("Offended, " + eName + " uses this as an"
					+ " opportunity to take a free hit on you.");
			enemyAttack();
		}
		else if(temp == 2){
			System.out.println("You bite your thumb at " + eName + ".");
			tempo(750);
			System.out.println("Undetered, " + eName + " makes a comment about"
					+ " you being the son of a motherless goat.");
			tempo(450);
			enemyAttack();		
		}
	}

	public void escape() {
		if(rand.nextInt(pDodge) > (rand.nextInt(eDodge) + 3)){
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
		if(pHP <= 0){
			System.out.println("You are dead.");
		}
		else{
			listener();
		}
	}
	
	public void useItem() {

	}

	public void helpMenu(){
		System.out.println("A - Attack    E - Escape    T - Taunt    I - Inventory");
		System.out.println(pHP + "/" + pMaxHP + " HP");
		listener();
	}
	
	public boolean inBattle() {
		return inBattle;
	}

	public int getPlayerHP(){
		return pHP;
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
