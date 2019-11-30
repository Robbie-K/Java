import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Missles{
	private Circle missle =  new Circle(5);
	private int dmg; // damage of the missle, based on tower type
	private Pane pane;
	private static ArrayList<Timeline> timelineList = new ArrayList<Timeline>(); //used for pause
	private Timeline animation;
	
	
	
	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	
	public void setCircle()  {
		this.missle =  new Circle(5);
	
	}
	
	
	public void setPane(Pane pane) {
		this.pane = pane;	
	}
	
	
	public static ArrayList<Timeline> getTimelineList()
	{
		return Missles.timelineList;
	}
	
	
	
	/**Missile Constructor
	 * 
	 * @param pane, pane where the game is played
	 * @param enemy, the enemy the missle is shooting
	 * @param x, x coord of the tower
	 * @param y, y coord of the tower
	 * @param dmg, tower's damage
	 */
	public Missles(Pane pane, Enemy enemy, int x, int y, int dmg) {
		
		ImageLoader.setImage("ball.png", this.missle);
		this.pane = pane;
		missle.setTranslateX(x*50+26);
		missle.setTranslateY(y*50);
    	this.pane.getChildren().add(this.missle);

		setDmg(dmg);
		Timeline animation = new Timeline();

    	
    	animation.setAutoReverse(false);
    	animation.setOnFinished(e -> this.removeMissle(enemy));
    	//Starts at tower
    	KeyFrame initial = new KeyFrame (Duration.ZERO, 
    			new KeyValue(this.missle.translateXProperty(), x*50+26),//center on the tower square
                new KeyValue(this.missle.translateYProperty(), y*50));
    	//Finishes at enemy 
    	KeyFrame Final = new KeyFrame (new Duration(150), //takes half a second to get to enemy
    			new KeyValue(this.missle.translateXProperty(), enemy.getCircle().getTranslateX()),
                new KeyValue(this.missle.translateYProperty(), enemy.getCircle().getTranslateY()));
    			
    			
    			
    			
    	
    	animation.getKeyFrames().addAll(initial, Final);
    	
    	this.animation = animation;
    	timelineList.add(animation);
    	animation.play();
	}
	
	/**When the enemy animation is finished,damage is delt to the enemy
	 * the missle is removed from the pane, and the time line list
	 * @param enemy, enemy that the tower has targeted
	 */
	public void removeMissle(Enemy enemy) 
	{	
		enemy.setHealth(this.dmg);
		this.pane.getChildren().remove(this.missle);
		Missles.timelineList.remove(this.animation);
	}
	
	
}
