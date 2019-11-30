import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application{
	
	private static final int WIDTH = 1250; 
	private static final int HEIGHT = 700; 
	private static final int TILE_SIZE = 50;

	private static GridPane gridpane;
	private static GridPane storegrid;
	private static Scene scene;
	private static TextGame textgame;
	private static Timeline timeline;
	private static AnimationTimer timer;
	private static int framecount = 0;
	private static int STATE = 0;

	
	
	private static ArrayList<Enemy> enemyList;
	private static ArrayList<Tower> towerList;
	private static ArrayList<Enemy> queueList;
	
	private static String difficulty = DifficultyMenu.getDefaultDifficulty(); //Default difficulty
	private static int numRounds = DifficultyMenu.getDefaultNumRounds(); //Default number of rounds 
	private static Leveling scalingAlgo;
	
	/**Game Constructor
	 * 
	 * @param gameScene Current scene
	 */
	public Game(Scene gameScene) {
		scene = gameScene;
		textgame = new TextGame(HEIGHT-50, WIDTH-200, TILE_SIZE);
	}
	
	
	@Override 
	public void start(Stage stage) throws Exception {
		
		TextGame.setLevel(Leveling.returnCurrentLevel());
		
		System.out.println(difficulty + " " + numRounds);
		scalingAlgo = new Leveling(difficulty, numRounds);
		setGridpane(new GridPane());
		setStoregrid(new GridPane());
		getStoregrid().setVgap(10);
		getStoregrid().setHgap(10);
		getStoregrid().setPadding(new Insets(15, 0, 15, 10));
		
		Game.enemyList = new ArrayList<Enemy>();
		Game.timeline = new Timeline();
		setTowerList(new ArrayList<Tower>());

		Enemy.setPane(gridpane);
		drawGrid();
		textgame.setBase(5, 20, "B"); // does text base
		Base b1 = new Base(getGridpane());

		//
		VBox buttonBar = new VBox();
		buttonBar.setStyle("-fx-border-color: black");
		buttonBar.setStyle("-fx-background-color: orange");
		buttonBar.setAlignment(Pos.CENTER);
		buttonBar.setSpacing(5);
		
        Button start = Buttons.startRoundButton();
        Button pause = Buttons.pauseButton(timeline);
        Button exit = Buttons.exitButton(stage);
        buttonBar.getChildren().addAll(start, pause, exit);
        GridPane.setConstraints(buttonBar, 21, 0, 4, 2, HPos.CENTER, VPos.CENTER);
        getGridpane().getChildren().addAll(buttonBar);
//        MediaPlayer player = ImageLoader.getPlayer("res/sound/game.mp3");
		
		
		//Generate a random path
        new RandomPath();

		// Draw the path in textgame
		TextGame.drawGame();
		//
				
		//game Timer
		setTimer(new AnimationTimer() {
			
			@Override
			public void handle(long arg0) {
				//Towers Shooting
				if (framecount % 25 == 0) {
					getTowerList().forEach(Tower -> Tower.checkInRange(enemyList));
				}
				
				//SpawnEnemys
				if (framecount % 40 == 0 && !getQueueList().isEmpty() ) {
					
					queueList.get(0).displayEnemy();
					queueList.remove(getQueueList().get(0));

				}
				
				//if enemy list is empty makes new list
				else if (framecount % 40 == 0 && enemyList.isEmpty())
				{
					Base.checkHealth(gridpane, timer);
					setState(false);
					start.setVisible(true);
					timer.stop();
				}
				
				/*
				if (framecount % 100 == 0) 
					TextGame.drawGame();
				*/
				
				//checks if base still has hp, pauses the game if it doesnt
				Base.checkHealth(gridpane, timer);
				
				
				
				// If you beat all the Rounds ends the game with win screen
				if (Leveling.returnCurrentLevel() == Leveling.getTotalLevels() 
						&& enemyList.isEmpty() && getQueueList().isEmpty()
						&& Base.getHealth() > 0)
				{
					timer.stop();
					start.setVisible(false);
					Pane winPane = Base.winGame();
					gridpane.getChildren().add(winPane);
//					HighScore.returnHighScore(TextGame.getMoney());
				}
				
				removeEnemies(enemyList);
				framecount++;
			}
		});
		
		
//		Store Menu	  (NAME, TEXTURE IMAGE, STORE POSITION, PRICE, DMG, RANGE)
//		Add towers to the store menu
		Store.newTower("Tower 1", "tank1.png", 1, 500, 500, 100);
		Store.newTower("Tower 2", "tank2.png", 2, 750, 650, 110);
		Store.newTower("Tower 3", "tank3.png", 3, 1000, 800, 75);
		Store.newTower("Tower 4", "tank4.png", 4, 1300, 250, 200);
		
		ScrollPane shoppane = new ScrollPane();
		shoppane.setStyle("-fx-border-color: black");
		shoppane.setContent(getStoregrid());
		shoppane.setFitToWidth(true);
		GridPane.setConstraints(shoppane, 21, 2, 5, 11);
		getGridpane().getChildren().addAll(shoppane);
		
//
		
		//Game Info Bar
	
		HBox infobar = new HBox();
		infobar.setPadding(new Insets(15, 10, 15, 10));
		infobar.setSpacing(150);
		infobar.setStyle("-fx-border-color: black");
		infobar.setStyle("-fx-background-color: orange");
		
		Label currentHealth= new Label();
		currentHealth.setFont(new Font("Arial", 15));
		currentHealth.textProperty().bind(TextGame.getHealthStr());
		
		Label currentMoney = new Label();
		currentMoney.setFont(new Font("Arial", 15));
		currentMoney.textProperty().bind(TextGame.getMoneyStr());
		
		Label currentLevel= new Label();
		currentLevel.setFont(new Font("Arial", 15));
		currentLevel.textProperty().bind(TextGame.getLevelStr());
		
		Label currentDifficulty = new Label("Difficulty: " + DifficultyMenu.getDifficulty());
		currentDifficulty.setFont(new Font("Arial", 15));
		
		Label currentMaxRounds = new Label();
		currentMaxRounds.setFont(new Font("Arial", 15));
		currentMaxRounds.textProperty().bind(DifficultyMenu.getNumRoundsStr());
				
		ScrollPane infopane = new ScrollPane();
		infopane.setContent(infobar);
		infopane.setFitToWidth(true);
		infobar.getChildren().addAll(currentHealth, currentMoney, currentLevel, currentDifficulty, currentMaxRounds);
		GridPane.setConstraints(infopane, 0, 13, 25, 1);
		getGridpane().getChildren().add(infopane);
		
		//
		 	
		scene = new Scene(getGridpane(), WIDTH, HEIGHT);
		
		stage.setTitle("The Python Wizards");
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static ArrayList<Enemy> getEnemyList() { return Game.enemyList; }
	
	
	
	/**If an enemy dies, remove it from the enemy list
	 * 
	 * @param enemyList, list of enemies on screen
	 * @throws ConcurrentModificationException
	 */
	public void removeEnemies (ArrayList<Enemy> enemyList) { //throws ConcurrentModificationException {
		for (int i = 0; i < enemyList.size(); i++)
		{
			if(enemyList.get(i).getHealth() <= 0) {
				TextGame.removeEnemies();
				enemyList.get(i).removeEnemy(false);
			}
			
		}
		
	}
	
	public static int getState() { return STATE; }
	
	public static void setState(boolean isRunning)
	{
		if (isRunning)
			STATE = 1;
		else 
			STATE = 0;
	}
	
	/**Draws the grid the game is played on
	 * 
	 * @throws FileNotFoundException, image files
	 */
	public void drawGrid() throws FileNotFoundException {
		//Grid builder - Creates a grid of Rectangles, each rectangle is a node with its own texture 	
		for (int col = 0; col < ((WIDTH)/ getTileSize()); col++) {
			for (int row = 0; row < ((HEIGHT)/ getTileSize()); row++) {
				Shape rec = new Rectangle(getTileSize(), getTileSize());
				ImageLoader.setImage("grass.jpg", rec);
				GridPane.setRowIndex(rec, row);
				GridPane.setColumnIndex(rec, col);
				getGridpane().getChildren().addAll(rec);
			}
		}
	}
	
	/**Draws the Random path on the grid
	 * 
	 * 
	 * 
	 * @param textgame, 
	 * @throws FileNotFoundException
	 */
	
	public void drawPath(ArrayList<ArrayList<String>> textgame) throws FileNotFoundException
	{
		for (int col = 0; col < TextGame.getNumCols(); col++) {
			for (int row = 0; row < TextGame.getNumRows(); row++) {
				if (textgame.get(row).get(col) == " ")
				{
					Shape rec = new Rectangle(getTileSize(), getTileSize());
					ImageLoader.setImage("enemypath.jpg", rec);
					GridPane.setRowIndex(rec, row);
					GridPane.setColumnIndex(rec, col);
					getGridpane().getChildren().addAll(rec);
				}
			}
		}
	}

	
	//All getters and setters

	public static GridPane getGridpane() {
		return gridpane;
	}


	public static void setGridpane(GridPane gridpane) {
		Game.gridpane = gridpane;
	}
	
	public static TextGame getTextgame() {
		return textgame;
	}


	public static ArrayList<Enemy> getQueueList() {
		return queueList;
	}


	public static void setQueueList(ArrayList<Enemy> queueList) {
		Game.queueList = queueList;
	}


	public static Leveling getScalingAlgo() {
		return scalingAlgo;
	}


	public static void setScalingAlgo(Leveling scalingAlgo) {
		Game.scalingAlgo = scalingAlgo;
	}


	public static AnimationTimer getTimer() {
		return timer;
	}


	public static void setTimer(AnimationTimer timer) {
		Game.timer = timer;
	}


	public static int getTileSize() {
		return TILE_SIZE;
	}


	public static ArrayList<Tower> getTowerList() {
		return towerList;
	}


	public static void setTowerList(ArrayList<Tower> towerList) {
		Game.towerList = towerList;
	}


	public static GridPane getStoregrid() {
		return storegrid;
	}


	public static void setStoregrid(GridPane storegrid) {
		Game.storegrid = storegrid;
	}


	public static String getDifficulty() {
		return difficulty;
	}


	public static void setDifficulty(String difficulty) {
		Game.difficulty = difficulty;
	}


	public static int getNumRounds() {
		return numRounds;
	}


	public static void setNumRounds(int numRounds) {
		Game.numRounds= numRounds;
	}
	
}

