import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


// Class for our enemy objects
public class Enemy {
	// Instance variables
	private Circle circle;
	private int health;
	private int damage;
	private int points;
	private static ArrayList<int[][]> pathList = new ArrayList<int[][]>(); // list of start and end points for pathing
	private static Pane pane; // used to display enemies
	private static ArrayList<Timeline> timelineList = new ArrayList<Timeline>(); // used for pausing
	private Timeline animation; // animation for every enemy 
	
	// Simple getters and setters
	public double getX() { return this.circle.getCenterX();	}
	public void setX(double xValue)	{ this.circle.setCenterX(xValue); }
	public void setY(double yValue)	{ this.circle.setCenterY(yValue);	}
	public double getY() { return this.circle.getCenterY(); }
	public static void setPane(Pane pane) { Enemy.pane = pane;	}
	public Circle getCircle() {return this.circle;}
	public int getHealth() {return this.health;}
	
	public static ArrayList<int[][]> getList()
	{ 
		return pathList;
	}
	
	// used for pausing and unpausing
	public static ArrayList<Timeline> getTimelineList()
	{
		return Enemy.timelineList;
	}
	
	
	
	
	// damages the enemies
	public void setHealth(int dmg) {
		this.health = this.health - dmg;
	}
	
	/**
	 * Removes the enemy from the canvas and enemylist, stops its animation and adds points
	 * also damages the base if hit is true
	 * @param hit if true damages base
	 */
	public void removeEnemy(boolean hit) 
	{	
		if (hit)
		{
			Base.setHealth(this.damage); // damages the base the set damage
		}
		else
		{
			TextGame.setMoney(TextGame.getMoney() + this.points);
		}
		
		Enemy.pane.getChildren().remove(this.circle);
		Enemy.timelineList.remove(this.animation);
		this.animation.stop();
		Game.getEnemyList().remove(this);
	}
	
	/**
	 * used by RandomPath to create a list of start and end points for enemy pathing
	 * @param an int list to be added to the pathList
	 */
	public static void addToList(int[][] a)
	{
		pathList.add(a);
	}
	
	
	/**
	 * Constructor creates a circle for each enemy then sets the animation by calling enemyAnimation
	 * @param TILE_SIZE used by enemyAnimation
	 * @param health assigns the health variable
	 * @param filename assigns the image fill to the circle
	 * @param damage how much damage the enemy does to the base
	 * @param radius size of the enemy
	 */
	public Enemy(int TILE_SIZE, int health, String filename, int damage, int radius) {
		this.circle = new Circle(radius);
		this.health = health;
		this.damage = damage;
		this.points = damage;
		ImageLoader.setImage(filename, this.circle);
		

		this.animation = enemyAnimation(TILE_SIZE); // sets the animation
		this.circle.setTranslateX(pathList.get(0)[0][0] * TILE_SIZE); // these set the circles start position
		this.circle.setTranslateY(pathList.get(0)[0][1] * TILE_SIZE);
    	
    	animation.setAutoReverse(false);
    	animation.setOnFinished(e -> this.removeEnemy(true)); // remove enemy with hit = true is called on finish
    	    	
	}

	
	/**
	 * displays the enemy, adds it to the enemyList, adds an enemy to textgame, and starts the animation
	 */
	public void displayEnemy()
	{
    	pane.getChildren().add(this.circle);
    	timelineList.add(animation);
    	
    	animation.play();
    	Game.getEnemyList().add(this);
    	TextGame.addEnemies();
	}
	
	/**
	 * creates the animation for each enemy using the pathList of start and end points
	 * @param TILE_SIZE used for the spacing of the enemies along the path
	 * @return animation the animation(timeline) to be used for every enemy
	 */
	public Timeline enemyAnimation(double TILE_SIZE)
	{
		double TILE_ADJ = TILE_SIZE / 2.0 - this.circle.getRadius(); // spacing for the enemy
		Timeline animation = new Timeline();
		KeyFrame initial = new KeyFrame (Duration.ZERO,  // starting keyframe with initial position
				new KeyValue(this.circle.translateXProperty(), pathList.get(0)[0][0] * TILE_SIZE ), 
	            new KeyValue(this.circle.translateYProperty(), pathList.get(0)[0][1] * TILE_SIZE));
		animation.getKeyFrames().addAll(initial);
		int size = 0; // used in the for loop for size of each path section
		int dur = 0; // used in the for loop to keep movement constant
		for (int i = 0; i < pathList.size(); i++) 
		{
			// size found by getting the max of absolute of y2-y1 and absolute of x2-x1
			size = Math.max(Math.abs(pathList.get(i)[1][0] - pathList.get(i)[0][0]), 
					Math.abs(pathList.get(i)[1][1] - pathList.get(i)[0][1] ));
			dur += size * 1000; // increases by size * 1000 so they move 1 square per second
			
			// default moves depending on if the section is horizontal or vertical (these keep the circle in place one direction)
			KeyValue moveY = new KeyValue(this.circle.translateYProperty(), pathList.get(i)[0][1] *TILE_SIZE);
			KeyValue moveX = new KeyValue(this.circle.translateXProperty(), pathList.get(i)[0][0] * TILE_SIZE + TILE_ADJ);
			
			if (pathList.get(i)[0][0] == pathList.get(i)[1][0]) // if x stays the same
			{
				moveY = new KeyValue(this.circle.translateYProperty(), pathList.get(i)[1][1] * TILE_SIZE);

			}
			if (pathList.get(i)[0][1] == pathList.get(i)[1][1]) // if y stays the same
			{
				moveX = new KeyValue(this.circle.translateXProperty(), pathList.get(i)[1][0] * TILE_SIZE + TILE_ADJ);
				if (pathList.get(i) == pathList.get(pathList.size() - 1))
					moveX = new KeyValue(this.circle.translateXProperty(), pathList.get(i)[1][0] * TILE_SIZE - this.circle.getRadius());
			}
			
			KeyFrame frame = new KeyFrame(new Duration(dur), moveX, moveY); // putting it all together
			animation.getKeyFrames().add(frame);
		}
		return animation;

	}


	

	
}
