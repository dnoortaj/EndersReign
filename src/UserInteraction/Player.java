package UserInteraction;
import java.io.Serializable;

import Inventory.*; 

/**
 * @author Ethan Patterson
 *
 */
public class Player implements Serializable
{
	private String playerName;
	private int playerScore;
	private int playerMaxHP;
	private int playerCurrentHP;
	private int playerAttack;
	private int playerDodge;
	private int playerLives;
	private boolean battleFlag;
	private boolean suitFlag;
	private boolean oxygenFlag;
	private boolean tabletFlag;
	private Inventory playerInventory;
	
	/**
	 * 
	 */
	public Player()
	{
		  playerName = "";
		  playerScore = 0;
		  playerMaxHP = 0;
		  playerCurrentHP = 0;
		  playerAttack = 0;
		  playerDodge = 0;
		  playerLives = 0;
		  battleFlag = false;
		  suitFlag = false;
		  oxygenFlag = false;
		  tabletFlag = false;
		  playerInventory = new Inventory();
	}

	/**
	 * @param playerName
	 * @param playerScore
	 * @param playerMaxHP
	 * @param playerCurrentHP
	 * @param playerAttack
	 * @param playerDodge
	 * @param playerLives
	 * @param battleFlag
	 * @param suitFlag
	 * @param oxygenFlag
	 * @param tabletFlag
	 * @param playerInventory
	 */
	public Player(String playerName, int playerScore, int playerMaxHP,
				int playerCurrentHP, int playerAttack, int playerDodge,
				int playerLives, boolean battleFlag, boolean suitFlag,
				boolean oxygenFlag, boolean tabletFlag, 
				Inventory playerInventory)
	{
		this.playerName = playerName;
		this.playerScore = playerScore;
		this.playerMaxHP = playerMaxHP;
		this.playerCurrentHP = playerCurrentHP;
		this.playerAttack = playerAttack;
		this.playerDodge = playerDodge;
		this.playerLives = playerLives;
		this.battleFlag = battleFlag;
		this.suitFlag = suitFlag;
		this.oxygenFlag = oxygenFlag;
		this.tabletFlag = tabletFlag;
		this.playerInventory = playerInventory;
	}

	public Inventory getPlayerInventory() {
		return playerInventory;
	}

	public void setPlayerInventory(Inventory playerInventory) {
		this.playerInventory = playerInventory;
	}
	
	/**
	 * @return the playerName
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	/**
	 * @return the playerScore
	 */
	public int getPlayerScore()
	{
		return playerScore;
	}

	/**
	 * @param playerScore the playerScore to set
	 */
	public void setPlayerScore(int playerScore)
	{
		this.playerScore = playerScore;
	}

	/**
	 * @return the playerMaxHP
	 */
	public int getPlayerMaxHP()
	{
		return playerMaxHP;
	}

	/**
	 * @param playerMaxHP the playerMaxHP to set
	 */
	public void setPlayerMaxHP(int playerMaxHP)
	{
		this.playerMaxHP = playerMaxHP;
	}

	/**
	 * @return the playerCurrentHP
	 */
	public int getPlayerCurrentHP()
	{
		return playerCurrentHP;
	}

	/**
	 * @param playerCurrentHP the playerCurrentHP to set
	 */
	public void setPlayerCurrentHP(int playerCurrentHP)
	{
		this.playerCurrentHP = playerCurrentHP;
	}

	/**
	 * @return the playerAttack
	 */
	public int getPlayerAttack()
	{
		return playerAttack;
	}

	/**
	 * @param playerAttack the playerAttack to set
	 */
	public void setPlayerAttack(int playerAttack)
	{
		this.playerAttack = playerAttack;
	}

	/**
	 * @return the playerDodge
	 */
	public int getPlayerDodge()
	{
		return playerDodge;
	}

	/**
	 * @param playerDodge the playerDodge to set
	 */
	public void setPlayerDodge(int playerDodge)
	{
		this.playerDodge = playerDodge;
	}

	/**
	 * @return the playerLives
	 */
	public int getPlayerLives()
	{
		return playerLives;
	}

	/**
	 * @param playerLives the playerLives to set
	 */
	public void setPlayerLives(int playerLives)
	{
		this.playerLives = playerLives;
	}
	
	/**
	 * @param addToScore adds a specified value(points) to the player’s score.
	 */
	public void addToScore(int points)
	{
		playerScore += points;
	}

	public boolean isBattleFlag() {
		return battleFlag;
	}

	public void setBattleFlag(boolean battleFlag) {
		this.battleFlag = battleFlag;
	}

	public boolean isSuitFlag() {
		return suitFlag;
	}

	public void setSuitFlag(boolean suitFlag) {
		this.suitFlag = suitFlag;
	}

	public boolean isOxygenFlag() {
		return oxygenFlag;
	}

	public void setOxygenFlag(boolean oxygenFlag) {
		this.oxygenFlag = oxygenFlag;
	}

	public boolean isTabletFlag() {
		return tabletFlag;
	}

	public void setTabletFlag(boolean tabletFlag) {
		this.tabletFlag = tabletFlag;
	}
}
