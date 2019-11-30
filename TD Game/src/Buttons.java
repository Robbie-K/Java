import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Buttons {
	private static int count = 0;
	
	// getters and setters for count
	// count is used to make sure only one tower is queued at a time
	private static int getCount() { return count; }
	private static void incCount() { count++; }
	private static void decCount() { count--; }
	private static void resetCount() { count = 0; }
	
	
	/**
	 * goes from the main menu to the difficulty setting menu
	 * @param stage used by difficultyMenu to keep the stage constant
	 * @return playButton the button itself used by MainMenu to display it
	 */
	public static Button playButton(Stage stage) {
		//Event handler for play button, changes scene to difficulty menu
		Button playButton = new Button("       Play       ");
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				//Difficulty menu constructor
				DifficultyMenu difficultyMenu = new DifficultyMenu();
				try {
					System.out.println("Loading...");	
					difficultyMenu.start(stage);
				} catch (Exception e) {
				e.printStackTrace();
				}
			}
		});
		return playButton;
	}

//	public static Button loadButton()
//	{
//		Button loadButton = new Button("       Load       ");
//		loadButton.setOnAction(new EventHandler<ActionEvent>() {
//			//Placeholder button		
//			@Override
//			public void handle(ActionEvent event) {	
//				
//			}	
//		});
//		return loadButton;
//	}
	
	/**Exit current stage
	 * 
	 * @param stage stage to be closed
	 * @return exitButton the actual button
	 */
	public static Button exitButton(Stage stage) {
		Button exitButton = new Button("       Exit       ");
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			//Exit scene when pressed
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		return exitButton;
	}
	
	/**Start next round
	 * 
	 * @return startRound button
	 */
    public static Button startRoundButton() {
    	Button startRound = new Button("Start Round");
    	startRound.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
        	Game.setState(true); // when true means the game is running
        	Leveling.increaseCurrentLevel(); // increases the current level by one in Leveling (starts at 0)
        	// gets the queueList of enemies from Leveling to be used by game
        	Game.setQueueList(new ArrayList<Enemy>(Game.getScalingAlgo().returnEnemyList(Leveling.returnCurrentLevel())));
//        	System.out.println(Game.getQueueList());
//        	System.out.println(Game.getEnemyList());
        	Game.getTimer().start();
        	startRound.setVisible(false);  
        	
        	}
    	});
    	return startRound;
    }
    
    
    /**Pauses and unpauses the game by pausing(or resuming) all animations at once 
     * 
     * @param timeline
     * @return the pause button
     */
    public static Button pauseButton(Timeline timeline) {
    	Button pause = new Button("     Pause     ");
    	pause.setOnAction(new EventHandler<ActionEvent>() 
    	{
    		
    		// checks whether state is 1 for running or 0 for paused and performs accordingly
    		@Override
    		public void handle(ActionEvent event) {
    			
    			if (Game.getState() == 1)
    			{
    			Game.getTimer().stop();
    			ArrayList<Timeline> enemyList = Enemy.getTimelineList();
    			for (int i = 0; i < enemyList.size(); i++  )
    				enemyList.get(i).pause();
    			ArrayList<Timeline> missleList = Missles.getTimelineList();
    			for (int i = 0; i < missleList.size(); i++)
    				missleList.get(i).pause();
    			Game.setState(false); // when false the game is paused
    			}
    			else if (Game.getState() == 0 && !Game.getEnemyList().isEmpty())
    			{
    				Game.getTimer().start();
    				ArrayList<Timeline> enemyList = Enemy.getTimelineList();
    				for (int i = 0; i < enemyList.size(); i++  )
    					enemyList.get(i).play();
    				ArrayList<Timeline> missleList = Missles.getTimelineList();
    				for (int i = 0; i < missleList.size(); i++)
    					missleList.get(i).play();
    				Game.setState(true); // when true game is running
    			}
    		}});
		return pause;
    }
    	
    			



    /**Creates a button to buy a tower
     * When pressed, buy button creates an 
     * event handler for placing the tower
     * Event handler checkes if the current 
     * position of the mouse pointer is 
     * within game boundaries and is not
     * on top of another tower, enemy path, or base
     * After tower is placed event handler is removed from scene
     * 
     * @param price Cost of the tower
     * @param type	Type of the tower
     * @param dmg	Tower damage output
     * @param range	Tower range
     * @param towerList List of all current towers 
     * @param image	Image of the tower type
     * @return Buy button for current tower
     */
	public static Button placeTower(int price, int type, int dmg, int range, ArrayList<Tower> towerList, String image) 
	    {
	    	Button twr = new Button("               Buy               ");
	    	twr.setOnAction(new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent event) {
        		Game.getGridpane().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {  
                    @Override
                    public void handle(MouseEvent e) {
                    	incCount();
                    	if (getCount() == 1) {
                        for(Node node: Game.getGridpane().getChildren()) {
                            if(node instanceof Rectangle) {
                            		if(node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY()) 
                                 		&& e.getSceneX() <= 1050 
                                 		&& e.getSceneY() <= 650 
                                 		&& ((TextGame.getTextgame().get(GridPane.getRowIndex(node)).get(GridPane.getColumnIndex(node)) != "X")
                                 		&& (TextGame.getTextgame().get(GridPane.getRowIndex(node)).get(GridPane.getColumnIndex(node)) != " ") 
                                 		&& (TextGame.getTextgame().get(GridPane.getRowIndex(node)).get(GridPane.getColumnIndex(node)) != "B" ))) {
                            		 if ((TextGame.getMoney() - price) >= 0 && getCount() == 1){
//					                    	System.out.println(Double.toString(node.getLayoutX()) + "/" + Double.toString(node.getLayoutY()));
//					                        System.out.println( "Tower at:  " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex(node));
					                    	Tower t1 = new Tower(GridPane.getColumnIndex(node), GridPane.getRowIndex(node), price, type, dmg, range, image, Game.getGridpane(),towerList);
					                    	TextGame.editGridTower(GridPane.getRowIndex(node), GridPane.getColumnIndex(node), "X");
//					                    	System.out.println("HERE" + TextGame.getTextgame().get(GridPane.getRowIndex(node)).get(GridPane.getColumnIndex(node)));
					                    	TextGame.drawGame();
					                    	towerList.add(t1);
					                    	decCount();
					                    	Game.getGridpane().removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
//					                    	TextGame.setHealth(Base.getHealth()); //For testing 
					                    	TextGame.setMoney(TextGame.getMoney()-price);
        								}
                            		 else {
                            			 System.out.println("Insufficient Funds");
                            			 Game.getGridpane().removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
                            		 }
        
                                	}
                          
                            }
                            
                        }
                    }
                    	else
                    		resetCount();
        		}});
	        }
	    });
	    	
	
	    return twr;
	}

}
