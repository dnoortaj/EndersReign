package Obstacle;

public class TauntTester {

	String[] bluntOutput, hitOutput, laserOutput, fistOutput, birdOutput, saberOutput, 
	tauntFlee, tauntEnrage, tauntHide, tauntConcentration, tauntStandard, bugOutput, 
	tauntBug, bzzOutput, tauntFlee1;

	public TauntTester(){

		tauntFlee = new String [] {"attDown", "10", "You take a kungfu stance "
				+ "and grin menacingly. \n"
				+ "Two of the bullies show their true colors and flee.", "Jerry",
				"You bite your thumb.", "You flaunt your puny muscles.", 
				"You give your best menacing scowl.",
				"is mildly amused that you thought that would have any affect.",
				"is dumbfounded.", "is not really paying enough attention to notice."};

		tauntFlee1 = new String [] {"attDown", "10", "You take a karate kid krane stance "
				+ "and grin menacingly. \n"
				+ "Two of the bullies show their true colors and flee.", "Bonzo",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any affect.",
				"is dumbfounded.", "is disgusted but unmoved."};

		tauntEnrage = new String [] {"enrage", "50", "You show your opponent "
				+ "the full moon. \nPerhaps that wasn't your best move yet, now he is "
				+ "really mad.", 
				"ENRAGED Mazer Rackham", "You add insult to injury and persist with the taunting.", 
				"You double down on the insults.","You fling poo at",
				"barely noticed the gesture.",		
				"is really too focused on killing you to care.",
				"is like full-on Super Seiyan berserker mode mad right now."};

		tauntHide = new String [] {"dodgeDown", "100", "You shout insults about your foes "
				+ "maternal unit. \n"
				+ "Enraged, he is done playing the hiding game.", "Peter",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"is mildly amused that you thought that would have any effect.",
				"is dumbfounded.", "is disgusted but unmoved."};

		tauntConcentration = new String [] {"dodgeDown", "25", 
				"You shout insults about your foes maternal unit. \n"
				+ "Enraged, he is having trouble concentrating.", "Plucifer",
				"You do your best to taunt", "You flaunt your puny muscles.", 
				"You expell flatulence in the general direction of",
				"makes a comment about you being the son of a motherless goat.",
				"is dumbfounded.", "is disgusted but unmoved."};

		tauntStandard = new String [] {"z", "0", " ", " ", 
				"You bite your thumb at ",
				"You wave your arms wildly in the air.",
				"You shout obsenities at ",
				"is not impressed.",
				"makes a comment about you being the son of a motherless goat.",
				"uses this as an" + " opportunity to take a free hit on you." };

		tauntBug = new String [] {"z", "0", " ", " ", 
				"You threaten to stomp ",
				"You wave your arms wildly in the air.",
				"You buzz mockingly ",
				"is a bug, thus is impervious to your misguided attempts at psychological warfare.",
				"says \"bzzzz\".",
				"uses this as an opportunity to take a free hit on you." };
	}
	public void tempo(int time){
		try {
			Thread.sleep(time);                 
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	
	public void print(String[] taunt){
		int i = 4;
		
			System.out.println("Taunt type = " + taunt[0] + ". Change Amount " + taunt[1] + ". Next line is the special taunt Output.");
			System.out.println(taunt[2]);
			System.out.println("If taunt type is attDown then new name is " + taunt[3]);
			while(6 >= i){
				System.out.println(i + " and " + (i+3));
				if(i == 4 || i == 6){
				System.out.println(taunt[i] + " Plucifer.");
				}else{
					System.out.println(taunt[i]);
				}
				tempo(650);
				System.out.println( "Plucifer " + taunt[i+3]);
				i = i + 1;
			}

		}

	public void go(){
		System.out.println("\nFlee");
		print(tauntFlee);
		System.out.println("\nEnrage");
		print(tauntEnrage);
		System.out.println("\nhide");
		print(tauntHide);
		System.out.println("\nConcentration");
		print(tauntConcentration);
		System.out.println("\nStandard");
		print(tauntStandard);
		System.out.println("\nBug");
		print(tauntBug);
		System.out.println("\nFlee1");
		print(tauntFlee1);
	}
	

	/*********************************************************************
	 * 	tauntFlee, tauntEnrage, tauntHide, tauntConcentration, tauntStandard, 
	tauntBug, bzzOutput, tauntFlee1
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TauntTester tt = new TauntTester();
		tt.go();

	}

}
