package Inventory;
import java.io.Serializable;

import UserInteraction.*; 

public class Accessory extends Item implements Serializable{

	int accessoryHPIncrease, accessoryAttackIncrease, accessoryDodgeIncrease;
	
	public Accessory(){
		super("Accessory", "", "", false);
		accessoryHPIncrease = 0;
		accessoryAttackIncrease = 0;
		accessoryDodgeIncrease = 0;
	}
	
	public Accessory(String name, String description, boolean isKeyItem, 
			int hp, int attack, int dodge){
		super("Accessory", name, description, isKeyItem);
		this.accessoryHPIncrease = hp;
		this.accessoryAttackIncrease = attack;
		this.accessoryDodgeIncrease = dodge;
	}
	
	public void useItem(Player p){
		int hP = p.getPlayerMaxHP() + accessoryHPIncrease;
		int aP = p.getPlayerAttack() + accessoryAttackIncrease;
		int dP = p.getPlayerDodge() + accessoryDodgeIncrease;
	
		p.setPlayerMaxHP(hP);
		p.setPlayerAttack(aP);
		p.setPlayerDodge(dP);
		
		// message displayed on use
		System.out.println("You equip the " + itemType.toLowerCase() +" [" + itemName + "].");
	}

	public int getAccessoryHPIncrease() {
		return accessoryHPIncrease;
	}

	public void setAccessoryHPIncrease(int accessoryHPIncrease) {
		this.accessoryHPIncrease = accessoryHPIncrease;
	}

	public int getAccessoryAttackIncrease() {
		return accessoryAttackIncrease;
	}

	public void setAccessoryAttackIncrease(int accessoryAttackIncrease) {
		this.accessoryAttackIncrease = accessoryAttackIncrease;
	}

	public int getAccessoryDodgeIncrease() {
		return accessoryDodgeIncrease;
	}

	public void setAccessoryDodgeIncrease(int accessoryDodgeIncrease) {
		this.accessoryDodgeIncrease = accessoryDodgeIncrease;
	}
}