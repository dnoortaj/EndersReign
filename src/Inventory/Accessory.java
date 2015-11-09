package Inventory;
import UserInteraction.*;


public class Accessory extends Item{

	int accessoryHPIncrease, accessoryAttackIncrease, accessoryDodgeIncrease;
	
	public Accessory(){
		super("", "", false);
		accessoryHPIncrease = 0;
		accessoryAttackIncrease = 0;
		accessoryDodgeIncrease = 0;
	}
	
	public Accessory(String name, String description, boolean isKeyItem, 
			int hp, int attack, int dodge){
		super(name, description, isKeyItem);
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
	}
}
