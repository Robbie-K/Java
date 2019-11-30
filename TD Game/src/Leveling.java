import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Leveling {

  // all the variables used in leveling for the various programs
    private double normal = 1.00;
    private double hard = 1.33;
    private double extereme = 1.66;
    private static int totalLevels;
    private static int currentLevel = 0;
    // round 10: 11950
    // round 5: 4300
    private int points = 0;
    private int num_enemyTiers;
    private int num_bossTiers;
    private int baseHealth = 1000;
    private int enemyBaseHealth = 2000;
    private int enemyBaseDamage = 10 ;
    private int enemyBaseRadius = 15;
    private int TILE_SIZE = 50;


    //boss remainder determiner, set every four levels right now
    private int boss_rounds = 4;// makes it so that bosses spawn every 4 rounds
    private int current_boss_tier = 0;
    // bosses

    // the list of enemies, basically has all the stats for how enemies are spawned
    // also includes the list of sprites for enemies and sprites for bosses
    private HashMap<Integer, HashMap<String, Integer>> num_EnemiesList = new HashMap<Integer, HashMap<String, Integer>>();
    private ArrayList<String> list_colors_e = new ArrayList<String>();
    private ArrayList<String> list_colors_b = new ArrayList<String>();

    /**
     * setting the constructor that generates the total rounds and the enemies in those round according to the algyorthym
     * @param difficulty - normal, hard, extereme, etc. sets the difficulty of the game through the string
     * @param levels- sets the number of total rounds that are played in the game
     */
    public Leveling(String difficulty, int levels){
        totalLevels = levels;
        setDifficulty(difficulty);
        setTieredEnemies();
        setTieredBosses();
        addTieredEnemies();
        populateColorList();
        TextGame.setHealth(baseHealth);

    }
    
    /**
     * returns the max number of levels
     * @return
     */
    public static int getTotalLevels()
    {
    	return totalLevels;
    }

    /**
     * sets the difficulty of the game from normal, hard and exterme
     * @param difficulty - the string that is used to set the difficulty
     */
    public void setDifficulty(String difficulty){
        if(difficulty == "Normal")
            this.enemyBaseHealth *= this.normal;
        else if(difficulty == "Hard"){
        	this.enemyBaseHealth *= this.hard;
        }
        else if(difficulty == "Extreme")
            this.enemyBaseHealth *= this.extereme;
    }
    
    /**
     * sets the number of tiered enemies
     */
    public void setTieredEnemies() {
    	//this.num_enemyTiers = totalLevels /4 + 2;
    	this.num_enemyTiers = 7;
    }
    
    /**
     * sets the numner of tiered bosses
     */
    public void setTieredBosses() {
    	//this.num_bossTiers = this.num_enemyTiers/3+1;
    	this.num_bossTiers = 4;
    }
    
    /**
     * adds to the total number of points
     * @param pts - the numbe rof points to be added to according to the current level also
     */
    public void addPoints(int pts){
        this.points += pts + ((currentLevel*2)/3);
    }
    
    /**
     * returns the total number of points
     * @return - returns points that you have
     */
    public int returnPoints(){
        return this.points;
    }
    
    /**
     * adds tiered enemies based on the hashmap<hashmap>> where it creates a hashmap of rounds
     * then it creates a hashmap of enemies for that round
     * ex: hashmap<1> = the number of enemies and tiers for that round 
     * 1 = 30 tiered 0 enemies, etc etc
     * 
     */
    public void addTieredEnemies(){
        for(int a = 0; a < totalLevels; a++) {
        	HashMap<String, Integer> enemy = new HashMap<String, Integer>();
        	this.num_EnemiesList.put(a, enemy);
        }
        //testing to see if things can be added in

        for(int b = 0; b < this.num_EnemiesList.size(); b++) {
        	HashMap<String, Integer> round = this.num_EnemiesList.get(b);
        	int counter = 0;
        	for(int c = 0; c <= b; c++) {
        		String num_currentRound = Integer.toString(c);
        		if(c==0) {
        			//if the current round is 0, then it adds the initial amount of 30 enemies
        			round.put(num_currentRound, 30);
        		}
        		else if(c < this.num_enemyTiers && c!= 0) {
        			//if the current round is not 0 and less than 7, then it increments the first tier of the 0th place enemy by -3 and adds 6 of the new tier
        			// if the first one subtracted 3 is =< 0, it just removes that key
        			round.put(num_currentRound, 6);
        			int num_firstTier = round.get(String.valueOf(counter));
        			if((num_firstTier - 3) > 0) {
        				round.put(Integer.toString(counter), num_firstTier-3);
        			}
        			else {
        				round.remove(Integer.toString(counter));
        				counter++;
        			}
        		}

        		else if(c > this.num_enemyTiers-1 && (c% 4 != 2) && (c% 4 != 0)) {
        			// after the highest tier enemy spawns, after that round, that tier enemy gets added +3 while the lowest
        			// gets -3
        			String highiest_tier = String.valueOf(this.num_enemyTiers-1);
        			int num_highTierEnmy = round.get(highiest_tier);
        			round.put(Integer.toString(this.num_enemyTiers-1), num_highTierEnmy+2);
        			int num_firstTier = round.get(String.valueOf(counter));
        			if((num_firstTier - 3) > 0) {
        				round.put(Integer.toString(counter), num_firstTier-3);
        			}
        			else {
        				round.remove(Integer.toString(counter));
        				counter++;
        			}

        		}
        	}
        }
        //printRoundInfo();
        //printoutNums();
    }
    
    /**
     * prints out the information in terms of the number of enemeis of that round, used for testing mainly
     */
    public void printRoundInfo() {
    	for(int c = 0; c < this.num_EnemiesList.size(); c++) {
        	System.out.println(Integer.toString(c) + this.num_EnemiesList.get(c).toString());
        }

    }
    
    /**
     * populates the bosses list and enemies list with the text names of the images that are used for sprites 
     */
    public void populateColorList() {
    	Collections.addAll(list_colors_e, "alien1W.png", "alien1B.png", "alien1G.png", "alien1O.png",
    			"alien1P.png", "alien1R.png", "alien1Y.png");
    	Collections.addAll(list_colors_b, "alien2W.png","alien2B.png", "alien2G.png", "alien2P.png");

    	}

    /**
     * takes in round, and uses hashmap<round> = the amount of tiered enemies for that round
     * @param round
     * @return - the list of enemies after they are sent to be shuffled
     */
    public ArrayList<Enemy> returnEnemyList(int round) {
    	ArrayList<Enemy> list_enemies = new ArrayList<Enemy>();
    	int arraylistnum = round -1;
    	// returns the hashmap <string, integers> associated with the round through hashmap<round, <string,integers>
    	// once you have the hashmap, you go through each "String", "value" and add it to the arraylist according to the vals
    	
    	//iretating through the number of enemies that are spawned this round for each tier
		HashMap<String, Integer> enemiesThisRound = new HashMap<String, Integer>(this.num_EnemiesList.get(arraylistnum));

		for (Map.Entry<String, Integer> iterate : enemiesThisRound.entrySet()) {
			//adding the total number of enemies for each tier of enemies
			
			String tier = iterate.getKey();
			int numEnemies = iterate.getValue();
			int tier_num = Integer.valueOf(tier);
			String filename = list_colors_e.get(tier_num);
			int health = this.enemyBaseHealth + ((this.enemyBaseHealth * tier_num)/2);
			int damage = this.enemyBaseDamage + ((this.enemyBaseDamage * tier_num)/2);
			int radius = this.enemyBaseRadius;
			for(int a = 0; a < numEnemies; a++) {
				//  Enemy(int TILE_SIZE, int heath, Color color, int damage, int radius)
				Enemy newenemy = new Enemy(this.TILE_SIZE, health, filename, damage, radius );
				list_enemies.add(newenemy);
			}
		}

		//boss stuff
		//setting the round that the boss spawns at
		int bossSpawnRound = arraylistnum%this.boss_rounds;
		int maxlevel = totalLevels -1;
		// checking if the level is the right level to spawn the boss, according to see if the remainder is 2
		//or if the current round is the max number of rounds in the game
		if((bossSpawnRound == 2 && arraylistnum>this.num_enemyTiers)|| arraylistnum == maxlevel) {
			if(this.current_boss_tier < this.num_bossTiers) {
				this.current_boss_tier += 1;
			}
			for(int a = 0; a < this.current_boss_tier; a++) {
				//setting the values for the boss varibles for scaling purposes
				int boss_health = this.enemyBaseHealth * 30 + ((this.current_boss_tier + 1) * 5000);
				int boss_damage = this.enemyBaseDamage * 25 + ((this.current_boss_tier + 1) * 100);
				String bosstier = list_colors_b.get(a);
				int bossradius = this.enemyBaseRadius;
				// set color/ sprite for the bosses here, according to current_boss_tier
				Enemy bossenemy = new Enemy(this.TILE_SIZE, boss_health, bosstier, boss_damage, bossradius );
				list_enemies.add(bossenemy);
			}
			
		}
		//System.out.println(list_enemies);
		ArrayList<Enemy> shuffledList = new ArrayList<Enemy>(returnShuffledEnemyList(list_enemies));
		return shuffledList;
    }
    
    /**
     * shuffles enemies that are gotten from the return enemies list when shuffled is called
     * @param list_enemies
     * @return - a shuffled list of enemies where any tiered enemies can be in any location
     */
    public ArrayList<Enemy> returnShuffledEnemyList(ArrayList<Enemy> list_enemies){
    	Random rand = new Random();
    	ArrayList<Enemy> shuffledList = new ArrayList<Enemy>();
    	// gets and removes each enemy from the original list to a new lsit untill the original list is empty
    	while(list_enemies.size() > 0) {
    		int  n = rand.nextInt(list_enemies.size()) + 0;
    		shuffledList.add(list_enemies.get(n));
    		list_enemies.remove(n);

    	}
    	return shuffledList;
    }

  /**
   * returns the current level, used in game to check for the current level to see if it needs to be increased
   * @return - current level of the round
   */
    public static int returnCurrentLevel() {
    	return currentLevel;
    }
    
    /**
     * increases the current level by 1, making the round increase by 1
     */
    public static void increaseCurrentLevel() {
    	currentLevel +=1;
    	TextGame.setLevel();
    }
    
    /**
     * prints out the values of the hashmap, mainly used for testing and game balancing. checks the amount of enemies
     * that are in one hashmap round location
     */
    public void printoutNums() {
    	//https://stackoverflow.com/questions/18429684/remove-a-value-from-hashmap-based-on-key

    	for(int a = 0; a < this.num_EnemiesList.size(); a++) {
    		System.out.println("Round" +  Integer.toString(a) + " : ");
    		HashMap<String, Integer> round = new HashMap<String, Integer>(this.num_EnemiesList.get(a));

    		for (Map.Entry<String, Integer> iterate : round.entrySet()) {
    		    System.out.println("Tier = " + iterate.getKey() + ", Number of Enemies = " + iterate.getValue());
    		}
    		System.out.println();
    	}
    }


}
