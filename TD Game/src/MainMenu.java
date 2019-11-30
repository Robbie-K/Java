import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenu extends Application {
	
	
	private static int WIDTH = 1250, HEIGHT = 700; //Set window dimensions

	public static int getWidth() {
		return WIDTH;
	}
	public static int getHeight() { 
		return HEIGHT;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ImageLoader.playMusic("res/sound/game.mp3");
		
		Pane background = new Pane(); //Pane for background image 
		GridPane mainMenu = new GridPane(); //Gridpane for all menu buttons & labels
		mainMenu.setMinSize(WIDTH, HEIGHT); //Set gridpane dimensions to window dimensions
		mainMenu.setPadding(new Insets(10, 10, 10, 10));
		mainMenu.setVgap(50);
		mainMenu.setHgap(50);
		mainMenu.setAlignment(Pos.CENTER);
//			canvas.setGridLinesVisible(true);
		
		Button playButton = Buttons.playButton(primaryStage); //Opens difficulty menu
		Button exitButton = Buttons.exitButton(primaryStage); //Closes the game
		
		//Add misc images to menu
		Shape ship = new Rectangle(200,200);
		Circle alien = new Circle(50);
		Circle alien2 = new Circle(50);
		ImageLoader.setImage("spaceship.png", ship);
		ImageLoader.setImage("alien1W.png", alien);
		ImageLoader.setImage("alien1P.png", alien2);
		alien2.setLayoutX(275);
		alien2.setLayoutY(425);
		alien.setLayoutX(275);
		alien.setLayoutY(300);
		ship.setLayoutX(175);
		ship.setLayoutY(100);
		
		
		Label title = new Label("Tower Defence Game");
		title.setFont(new Font("Arial", 65));
		title.setLayoutX(50);
		GridPane.setHalignment(playButton, HPos.CENTER);
		GridPane.setHalignment(exitButton, HPos.CENTER);
		background.getChildren().add(ImageLoader.menuBackgroundImage("background.jpg")); //Set background image
		mainMenu.add(playButton, 0, 8);	//Place play button
		mainMenu.add(exitButton, 1, 8); //Place exit button
		background.getChildren().addAll(title, mainMenu, ship, alien, alien2); //Add all images, buttons, and title to background
		
		Scene scene = new Scene(background);
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED); 
		primaryStage.show();
					
	}
	public static void main(String args[]) {
		launch(args);
	}
}