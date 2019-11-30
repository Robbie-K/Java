import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DifficultyMenu extends MainMenu{
	

	private static int WIDTH = 1250, HEIGHT = 700; //Set window dimensions
	
	private static String defaultDifficulty = "Normal";
	private static String difficulty = defaultDifficulty;
	private static int defaultNumRounds = 20;
	private static int numRounds = defaultNumRounds;
	private static String endlessMode = "Disabled"; //Default setting 
	
	//String properties for updating UI
	private static StringProperty DIFFICULTYstr = new SimpleStringProperty("Difficulty: " + difficulty); 
	private static StringProperty ROUNDSstr = new SimpleStringProperty("Number of rounds: " + Integer.toString(numRounds));
	private static StringProperty ENDLESSstr = new SimpleStringProperty("Endless mode: " + endlessMode);
		
	
	public void start(Stage primaryStage) throws Exception {
			
			Pane background = new Pane();
			background.getChildren().add(ImageLoader.menuBackgroundImage("galaxy.jpg")); //Set scene background image	
			
			GridPane menu = new GridPane(); 
			menu.setMinSize(WIDTH, HEIGHT);
			menu.setPadding(new Insets(10, 10, 10, 10));
			menu.setVgap(10);
			menu.setHgap(10);
			menu.setAlignment(Pos.CENTER);
			
			HBox difficultyButtons = new HBox();
			difficultyButtons.setSpacing(100);
			
			
			Button start = new Button("  Start Game  ");
			Button normal = new Button("Normal");
			Button hard = new Button("Hard");
			Button extreme = new Button("Extreme");
			Button endlessMode = new Button("Endless Mode");
			
			GridPane.setHalignment(start, HPos.CENTER);
			GridPane.setHalignment(endlessMode, HPos.CENTER); 
			
			Label difficulty = new Label("Choose Difficulty: ");
			difficulty.setFont(new Font("Arial", 30));
			difficulty.setTextFill(Color.WHITE);
			
			Label name = new Label("Enter your name: ");
			name.setFont(new Font("Arial", 19));
			name.setTextFill(Color.WHITE);
			TextField nameInput = new TextField();
			
			Label rounds = new Label("Enter number of rounds (default 20): ");
			TextField roundsInput = new TextField(); //Textbox to set custom number of rounds
			rounds.setFont(new Font("Arial", 19));
			rounds.setTextFill(Color.WHITE);
			
			Label currentDifficulty = new Label(); //Displays current difficulty
			currentDifficulty.setFont(new Font("Arial", 19));
			currentDifficulty.setTextFill(Color.LIMEGREEN);
			currentDifficulty.textProperty().bind(DIFFICULTYstr); //Binds Difficulty string property to label 
			
			Label currentRounds= new Label(); //Not currently used
			currentRounds.setFont(new Font("Arial", 19));
			currentRounds.textProperty().bind(ROUNDSstr); //Binds number of rounds string property to label 
			
			Label isEndlessMode = new Label(); //Displays if endless mode is enabled/disabled
			isEndlessMode.setFont(new Font("Arial", 19));
			isEndlessMode.setTextFill(Color.WHITE);
			isEndlessMode.textProperty().bind(ENDLESSstr); //Binds Endless mode string property to label 
		
			//Set position of all button/labels
			GridPane.setConstraints(difficulty, 0, 0, 3, 1);
			GridPane.setConstraints(normal, 3, 0);
			GridPane.setConstraints(hard, 4, 0);
			GridPane.setConstraints(extreme, 5, 0);
			GridPane.setConstraints(rounds, 0, 2, 6, 1);
			GridPane.setConstraints(roundsInput, 4, 2, 2, 1);
			GridPane.setConstraints(endlessMode, 0, 3);
			GridPane.setConstraints(start, 0, 4);
			GridPane.setConstraints(currentDifficulty, 2, 4, 2, 1);
			GridPane.setConstraints(currentRounds, 0, 5);
			GridPane.setConstraints(isEndlessMode, 2, 3, 3, 1);
			GridPane.setConstraints(name, 0, 5, 2, 1);
			GridPane.setConstraints(nameInput, 2, 5, 2, 1);
			
			//Add button/labels to menu gridpane
			menu.getChildren().addAll(
					difficulty, 
					normal, 
					hard, 
					extreme, 
					rounds, 
					roundsInput, 
					endlessMode,
					start,
					currentDifficulty, 
//					currentRounds, 
					isEndlessMode,
					name,
					nameInput);
			
//			menu.setGridLinesVisible(true);
			
			//Add menu to background image
			background.getChildren().add(menu);
			
			//Create scene and add to stage
			Scene scene = new Scene(background);
			primaryStage.setTitle("The Python Wizards");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Event handlers for all buttons			
			normal.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {	
					setDifficulty("Normal");
					currentDifficulty.setTextFill(Color.LIMEGREEN);
					Game.setDifficulty("Normal");
				}	
			});
			
			hard.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					setDifficulty("Hard");
					currentDifficulty.setTextFill(Color.YELLOW);
					Game.setDifficulty("Hard");
				}	
			});
			
			extreme.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {	
					setDifficulty("Extreme");
					currentDifficulty.setTextFill(Color.RED);
					Game.setDifficulty("Extreme");
				}	
			});
			
			endlessMode.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (getEndlessMode().equals("Disabled")) {
						roundsInput.clear();
						setEndlessMode("Enabled");
						isEndlessMode.setTextFill(Color.RED);
						setNumRounds(999);
						Game.setNumRounds(999);
					}else { //Resets number of rounds to default
						setEndlessMode("Disabled");
						isEndlessMode.setTextFill(Color.WHITE);
						setNumRounds(defaultNumRounds);
						Game.setNumRounds(defaultNumRounds);
					}
				}	
			});
			
			start.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					if (getEndlessMode().equals("Disabled") && !roundsInput.getText().isEmpty()) { //If endless mode is disabled and user has entered a custom round number
						//Start game with custom number of rounds							
						if (!nameInput.getText().isEmpty()) { //If user name is not empty
							setNumRounds(Integer.parseInt(roundsInput.getText()));
							Game.setNumRounds(Integer.parseInt(roundsInput.getText()));
							HighScore.setName(nameInput.getText());
							if(Integer.parseInt(roundsInput.getText()) <= 0) {
								setNumRounds(20);
								Game.setNumRounds(20);
							} else if(Integer.parseInt(roundsInput.getText()) > 999) {
								setNumRounds(999);
								Game.setNumRounds(999);
							}
							//Game construtor
							Game game = new Game(scene);
							try {
								System.out.println("Loading...");		
								game.start(primaryStage); 
								System.out.println("Done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else { //If user name input is empty
							HighScore.setName("Generic Name");
							setNumRounds(Integer.parseInt(roundsInput.getText()));
							Game.setNumRounds(Integer.parseInt(roundsInput.getText()));

							if(Integer.parseInt(roundsInput.getText()) <= 0) {
								setNumRounds(20);
								Game.setNumRounds(20);
							} else if(Integer.parseInt(roundsInput.getText()) > 999) {
								setNumRounds(999);
								Game.setNumRounds(999);
							}
							//Game construtor
							Game game = new Game(scene);
							try {
								System.out.println("Loading...");	
								game.start(primaryStage); 
								System.out.println("Done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}					
					}else if (getEndlessMode().equals("Disabled") && roundsInput.getText().isEmpty()) { //If endless mode is disabled and user did not enter custom number of rounds

						//Start game with default number of rounds
						if (!nameInput.getText().isEmpty()) { //If user name is not empty
							setNumRounds(defaultNumRounds);
							Game.setNumRounds(defaultNumRounds);
							System.out.println(defaultNumRounds);
							HighScore.setName(nameInput.getText());
							//Game construtor
							Game game = new Game(scene);
							try {
								System.out.println("Loading...");	
								game.start(primaryStage); 
								System.out.println("Done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else { //If user name input is empty
							HighScore.setName("Generic Name");
							setNumRounds(defaultNumRounds);
							Game.setNumRounds(defaultNumRounds);	
							System.out.println(defaultNumRounds);
							//Game construtor
							Game game = new Game(scene);
							try {
								System.out.println("Loading...");	
								game.start(primaryStage); 
								System.out.println("Done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}	
							
					}else if (getEndlessMode().equals("Enabled")){
						if (!nameInput.getText().isEmpty()){
							HighScore.setName(nameInput.getText());
							//Start game in endless mode
							Game game = new Game(scene);
							try {
								System.out.println("Loading...");	
								game.start(primaryStage);
								System.out.println("Done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else {
							HighScore.setName("Generic Name");
							Game game = new Game(scene);
							try {
								System.out.println("Loading...");	
								game.start(primaryStage);
								System.out.println("Done");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
//					else {
//						if (!nameInput.getText().isEmpty()){
//							setNumRounds(defaultNumRounds);
//							Game.setNumRounds(defaultNumRounds);
//							HighScore.setName(nameInput.getText());
//							//Start game in endless mode
//							Game game = new Game(scene);
//							try {
//								System.out.println("Loading...");	
//								game.start(primaryStage);
//								System.out.println("Done");
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}else {
//							HighScore.setName("Generic Name");
//							//Start game with deafult number of rounds
//							setNumRounds(defaultNumRounds);
//							Game.setNumRounds(defaultNumRounds);
//							Game game = new Game(scene);
//							try {
//								System.out.println("Loading...");	
//								game.start(primaryStage);
//								System.out.println("Done");
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//					}
//				}
			}	
		});
			
			/*From https://stackoverflow.com/a/30796829/8645685
			 * Makes roundsInput Textbox only allow numbers to be entered
			 */
			roundsInput.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			            roundsInput.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			    }
			});
			
	}
	
	public static String getDefaultDifficulty() {
		return defaultDifficulty;
	}
	
	public static StringProperty getDifficultyStr() {
		return DIFFICULTYstr;
	}
	
    public static void setDifficulty(String difficultyVal) {
        difficulty = difficultyVal;
        DIFFICULTYstr.set("Difficulty: " + difficultyVal);
    }
    
    public static int getDefaultNumRounds() {
		return defaultNumRounds;
	}
    
    public static int getNumRounds() {
		return numRounds;
	}
    
    public static StringProperty getNumRoundsStr() {
		return ROUNDSstr;
	}
    
    public static void setNumRounds(int NumRoundsVal) {
    	if (getEndlessMode().equals("Disabled")) {
    		numRounds = NumRoundsVal;
    		ROUNDSstr.set("Number of rounds: " + Integer.toString(NumRoundsVal));
    	}else {
    		numRounds = NumRoundsVal;
    		ROUNDSstr.set("Number of rounds: Endless");
    	}
    }
    
    public static void setEndlessMode(String isEndlessModeVal) {
        endlessMode = isEndlessModeVal;
        ENDLESSstr.set("Endless mode: " + isEndlessModeVal);
    }  
    
    public static String getEndlessMode() {
    	return endlessMode;
    }

	public static String getDifficulty() {
		return difficulty;
	}
}
