import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TextGame {
	
	// all the static variables that are used throughout the game itself
    public static int HEALTH;
    public static int MONEY = 1500;
    public static int LEVEL = 0;
    public static int ENEMIES = 0;
    private static int HEIGHT;
    private static int WIDTH;
    private static int TILE_SIZE;
    private static int NUM_ROWS;
    private static int NUM_COLOUMS;
    
    private static int baseRow;
    private static int baseCol;
    
    private static StringProperty HEALTHstr = new SimpleStringProperty("Health: " + Integer.toString(HEALTH));
    private static StringProperty MONEYstr = new SimpleStringProperty("Money: " + Integer.toString(MONEY));
    private static StringProperty LEVELstr = new SimpleStringProperty("Round: " + Integer.toString(LEVEL));
    
   
    
    // public arraylist that hosts all of the grid game
    public static ArrayList< ArrayList<String>> textgame;
    
    // all the getters and setters for the game
    
   public static StringProperty getHealthStr() {
	   return HEALTHstr;
   }
   
   public static StringProperty getMoneyStr() {
	   return MONEYstr;
   }
   
   public static StringProperty getLevelStr() {
	   return LEVELstr;
   }
   
   public static int getHealth() {
	   return HEALTH;
   }
  
   
   public static int getMoney() {
	   return MONEY;
   }
   
   public static int getLevel() {
	   return LEVEL;
   }
    // gettes and setters for money, health and level
    public static void setHealth(int healthval) {
        TextGame.HEALTH = healthval;
        TextGame.HEALTHstr.set(" Health: " + Integer.toString(healthval));
    }
    public static void setMoney(int moneyVal) {
        TextGame.MONEY = moneyVal;
        TextGame.MONEYstr.set("Money: " + Integer.toString(moneyVal));
    }
    public static void setLevel() {
        TextGame.LEVEL += 1;
        TextGame.LEVELstr.set("Round: " + Integer.toString(TextGame.LEVEL));
    }
    
    public static void setLevel(int num)
    {
    	TextGame.LEVEL = num;
    }
    
    /**
     * increases enemies by 1
     */
    public static void addEnemies() {
    	TextGame.ENEMIES += 1;
    	
    }
    
    /**
     * decreases enemies by 1
     */
    public static void removeEnemies() { 
    	ENEMIES--;
    	}
    
    /**
     * returns rows and coloums
     * @return
     */
    public static int getNumCols() { return NUM_COLOUMS; }
    public static int getNumRows() { return NUM_ROWS; }
    
    
   /**
    * the constructor that sets the height, width, size, rows and coloums so the text game prints correctly
    * @param height
    * @param width
    * @param tile_size
    */
    // constructor for the class( used in the grid game to create an object of this class)
    public TextGame(int height, int width, int tile_size) {
    	TextGame.HEIGHT = height;
    	TextGame.WIDTH = width;
    	TextGame.TILE_SIZE = tile_size;
    	TextGame.NUM_ROWS = TextGame.HEIGHT/TextGame.TILE_SIZE;
    	TextGame.NUM_COLOUMS = TextGame.WIDTH / TextGame.TILE_SIZE;
        
        textgame = new ArrayList<ArrayList<String>>();
        createArrayList();

    }
    
    /**
     * returns the textgame incase it needs to be edited through refrencing
     * @return
     */
    public static ArrayList<ArrayList<String>> getTextgame()
    {
    	return TextGame.textgame;
    }

    
    /**
     * creates an arraylist, and populates it. this arraylist is then later used to print out the textgame
     */
    public static void createArrayList() {
        for(int a = 0; a < NUM_ROWS; a++)
            textgame.add(new ArrayList<String>());

        for( int b = 0; b < NUM_ROWS; b++){
            for(int c = 0; c < NUM_COLOUMS; c++){
                textgame.get(b).add("#");
            }
        }
    }

    /**
     * simple loop that prints out all the values of the arraylist, hence, printing out the game
     */
    public static void drawGame() {
    	System.out.println();
        for(int a = 0; a < NUM_ROWS; a++){
        	String row = "";
            for(int b = 0; b < NUM_COLOUMS; b++){
            	row += textgame.get(a).get(b);
            }
            System.out.println(row);
        }

        System.out.print("Health = " + HEALTH + " Level = " + LEVEL + " Enemies = " + ENEMIES);

        System.out.println();
        for(int d = 0; d<NUM_COLOUMS; d++) {
        	System.out.print("*");
        }
        System.out.println("");
    }
    
    //editing the game, enemies and paths
    /**
     * edits the xvalue, and y value for when towers are placed into the game so things cant be placed on top of it
     * @param xcord
     * @param ycord
     * @param val - string that represents tower in textgame
     */
    public static void editGridTower(int xcord, int ycord, String val) {
    	textgame.get(xcord).set(ycord, val);
    	
    }
    
    /**
     * adds in spaces for the paths as blank statements to allow for path checking later
     * @param xcord
     * @param ycord
     * @param val - string that represents paths in textgame
     */
    public void editGridPath(int xcord, int ycord, String val) {
    	textgame.get(xcord).set(ycord, val);
    }
    
    /**
     * sets the coordinate for base in the game
     * @param xcord
     * @param ycord
     * @param val - getting the value for base as a string in textgame
     */
    public void setBase(int xcord, int ycord, String val) {
    	textgame.get(xcord).set(ycord, val);
    	setBaseRow(xcord);
    	setBaseCol(ycord);
    }
    

    /**
     * gets the base coloums
     * @return
     */
	public static int getBaseCol() {
		return baseCol;
	}
	
	/**
	 * sets coloums
	 * @param baseCol
	 */
	public static void setBaseCol(int baseCol) {
		TextGame.baseCol = baseCol;
	}
	
	/**
	 * sets base coloums
	 * @return
	 */
	public static int getBaseRow() {
		return baseRow;
	}
	
	/**
	 * sets base rows
	 * @param baseRow
	 */
	public static void setBaseRow(int baseRow) {
		TextGame.baseRow = baseRow;
	}
}


