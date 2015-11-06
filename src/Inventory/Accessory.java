package Inventory;


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
	
	public void useItem(){
		int hP = currentPlayer.getPlayerMaxHP() + accessoryHPIncrease;
		int aP = currentPlayer.getPlayerAttack() + accessoryAttackIncrease;
		int dP = currentPlayer.getPlayerDodge() + accessoryDodgeIncrease;
	
		currentPlayer.setPlayerMaxHP(hP);
		currentPlayer.setPlayerAttack(aP);
		currentPlayer.setPlayerDodge(dP);
	}
}
