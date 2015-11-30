package Obstacle;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
import Inventory.*;
import UserInteraction.*;

@SuppressWarnings("serial")
public class Enemy implements Serializable {

	private boolean enemyIsDead, inBattle, firstTaunt;
	private String eName;
	private int eHP, eDodge, eAttack, ePoints, eID, intro;
	private String weaponOut, weaponOut2;

	private Item eReward;

	transient private Scanner s;
	private Random rand = new Random();
	private String [] hitOutput = new String[4];
	private String [] taunt = new String[7];
	private Player playa;

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
		weaponOut = "";
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
		weaponOut = "";
	}

	public void fight(Player p) {
		playa = p;
		inBattle = true;
		if((eID % 2) == 0){
			System.out.println(eName + " is mean-mugging you.");
			tempo(1250);
		}
		else{
			System.out.println("There is " + eName + ", approaching you with an apparent degree of malice.");
			tempo(1250);
		}
		System.out.println(eName + " engaged you in combat."+
				" (Press H for Help)");
		if(taunt[0].equalsIgnoreCase("dodgeDown")){
			System.out.println(eName + " is agile and very elusive.  You would do well"
					+ " to be mindful that your opponent is hiding.");
		}else if (taunt[0].equalsIgnoreCase("attDown")){
			System.out.println(eName + " have you out numbered. \n" + taunt[3] + ", the apparent alpha, is "
					+ "resolved to take you down however his pals are just following his lead.");
		}
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
			weaponOut = hitOutput[i].replace("You", eName);
			weaponOut2 = weaponOut.replace("doe", "you");
			playa.setPlayerCurrentHP((playa.getPlayerCurrentHP() - actualDamage.intValue()));
			System.out.println(weaponOut2 + " " + 
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
			Weapon temp = ((Weapon) playa.getPlayerInventory().getCurrentWeapon());
			if(temp != null){
				System.out.println(damageDescription);
				weaponOut =(temp.getOutput(damageDescription));
				weaponOut2  = weaponOut.replace("doe", eName);
				System.out.println(weaponOut2 + " Inflicting " + actualDamage.intValue()
				+ " points of damage.");
			}
			else{
				System.out.println("You non-descriptly attacked " + eName + ". Inflicting "
						+ actualDamage.intValue() + " points of damage.");
			}
			tempo(650);
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
		
		if(rand.nextInt(101) > dodge){
			return true;
		}
		else{
			if(dodge >= 100){
				System.out.println("Its hard to hit what you can't see.");	
			}
			return false;
		}
	}

	public int damage(){
		return rand.nextInt(40);
	}

	/*********************************************************************
	taunt String array description

	0- describes special taunt cases "a" alters attack upon first taunt, 
		"d" alters dodge, anything else taunt has no real consequence
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

	public void taunt(){
		int temp = rand.nextInt(3) + 4;
		if(taunt[0].equalsIgnoreCase("attDown") && firstTaunt){
			eAttack = eAttack - Integer.parseInt(taunt[1]);
			eName = taunt[3];
			System.out.println(taunt[2]);
			firstTaunt = false;
			listener();
		}
		else if(taunt[0].equalsIgnoreCase("dodgeDown") && firstTaunt){
			if((eDodge - Integer.parseInt(taunt[1])) > 0)
			{
				eDodge = eDodge - Integer.parseInt(taunt[1]);
			}
			else
			{
				eDodge = 1;
			}

			eName = taunt[3];
			System.out.println(taunt[2]);
			firstTaunt = false;
			listener();
		}
		else if(taunt[0].equalsIgnoreCase("enrage") && firstTaunt){
			eAttack = eAttack + Integer.parseInt(taunt[1]);
			eName = taunt[3];
			System.out.println(taunt[2]);
			tempo(1250);
			System.out.println(eName + " " + taunt[9]);
			firstTaunt = false;
			listener();
		}
		else{
			if(temp == 4){
				System.out.println(taunt[4] + " " + eName + ".");
				tempo(650);
				System.out.println(eName + " " + taunt[7]);
				listener();
			}
			else if(temp == 5){
				System.out.println(taunt[5]);
				tempo(550);
				System.out.println(eName + " " + taunt[8]);
				enemyAttack();
			}
			else if(temp == 6){
				System.out.println(taunt[6] + " " + eName + ".");
				tempo(550);
				System.out.println(eName + " " + taunt[9]);
				tempo(350);
				enemyAttack();		
			}
		}
	}

	public void escape() {
		if((rand.nextInt(playa.getPlayerDodge()) > rand.nextInt(eDodge)) && eID != 9 && eID != 8)
		{
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
			tempo(150);
			System.out.println("You are no longer in combat.");
		}
		else{
			enemyAttack();
		}
	}


	public void playerIsAlive(){
		if(playa.getPlayerCurrentHP() < 0 && playa.getPlayerCurrentHP() >= -5){
			playa.setPlayerCurrentHP(0);
			System.out.println("You are dead.");
		}
		else if(playa.getPlayerCurrentHP() <= -6){
			System.out.println("You are very dead.");
		}
		else{
			listener();
		}
	} 

	public void useItem() {
		if(playa.getPlayerInventory().useItem() != null){
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
		s = new Scanner(System.in);
		System.out.print("> ");
		String input = s.nextLine();
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