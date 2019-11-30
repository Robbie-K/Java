import java.util.Random;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RandomPath {
	private static int baseRow = TextGame.getBaseRow();; //Row position of base
	private static int baseCol = TextGame.getBaseCol();; //Column position of base 
	private static int finalCol = baseCol - 4; //Random path will stop 4 columns away from base
	private static int startingRow; //Choose a random row to start path
	private static int currentCol = 0; //Random path always starts at column 0
	private static int currentRow; //Current row after a path has been drawn
	private static String[] moveV = {"up", "down"}; //Vertical directions 
	private static String nextVmove; //Stores the next vertical movement of the path
	private static String right = "right";
	private static int randLength; //Stores the length of the next path to be drawn
	private static int turn = 1; //When turn is 1 path moves up/down, when turn is 2 path moves right

	Random rand = new Random();

		public RandomPath() {
			
		
			//Draws the first part of the path
			//Always starts at (startingRow(random), 0) and moves right 
			startingRow = rand.nextInt((11+1)-1)+1; //Pick a random starting row between 1 and 11
			currentRow = startingRow; 
			moveRight(startingRow, currentCol, randomLength(right)); //First right move
			
			//Generates random paths until endpoint of last drawn path is on the same 
			//row as base and is 4 columns to the left of the base
			while(currentRow != baseRow || currentCol != finalCol) {
				if (turn == 1) { //Move up/down
				nextVmove = moveV[randomDirection()]; //Pick up/down
				randLength = randomLength(nextVmove); //Pick a random length 
				if (nextVmove.equals("up") && currentRow - (randLength) >= 1) { //Check if path is moving up and will not go out of bounds
					moveUp(currentRow, currentCol, randLength); //Move up random length
					turn = 2;
				}else if (nextVmove.equals("down") && currentRow + (randLength) <= 11) { //Check if path is moving down and will not go out out of bounds 
					moveDown(currentRow, currentCol, randLength); //Move down random length
					turn = 2;
				}
			}else if (turn == 2) { //Move right
				randLength = randomLength(right);
				if (currentCol + randLength < finalCol) {//Move right with random length if move is not going to go past final column
					moveRight(currentRow, currentCol, randLength);
					turn = 1; 
				}else if (currentRow > baseRow) {//If next move will go past final column and current row is above base row
					moveRight(currentRow, currentCol, 2); //Move right 2 units
					moveUp(currentRow, currentCol, (currentRow - baseRow)); //Move path up to base row and break loop
					break;
				}else if (currentRow < baseRow) {//If next move will go past final column and current row is below base row
					moveRight(currentRow, currentCol, 2); //Move path right 2 units
					moveDown(currentRow, currentCol, (baseRow - currentRow)); //Move path down to baseRow and break loop
					break;
				}else { //If currentRow = 5 move right as many units as needed to get to final column and break loop
					moveRight(currentRow, currentCol, (finalCol - currentCol));
					break;
				}
			}
		}			
			//Finish path from final column to base position 
			enemyPath(right, currentRow, currentCol, (baseCol-currentCol));			
	
}
			/**Draw path up 
			 * Sets currentRow to endpoint of drawn path
			 * @param row Starting row
			 * @param col Starting col
			 * @param length Length of path to be drawn
			 */
			public void moveUp(int row, int col, int length) {
				enemyPath("up", row, col, length);
				RandomPath.setCurrentRow(RandomPath.getCurrentRow() - (length));
				
			}
			
			/**Draw path down 
			 * Sets currentRow to endpoint of drawn path
			 * @param row Starting row
			 * @param col Starting col
			 * @param length Length of path to be drawn
			 */
			public void moveDown(int row, int col, int length) {
				enemyPath("down", row, col, length);
				RandomPath.setCurrentRow(RandomPath.getCurrentRow() + (length));
			}
			
			/**Draw path Left 
			 * Sets currentRow to endpoint of drawn path
			 * @param row Starting row
			 * @param col Starting col
			 * @param length Length of path to be drawn
			 */
			public void moveLeft(int row, int col, int length) {
				enemyPath("left", row, col, length);
				RandomPath.setCurrentCol(RandomPath.getCurrentCol() - (length));
			}
			
			/**Draw path Right
			 * Sets currentRow to endpoint of drawn path
			 * @param row Starting row
			 * @param col Starting col
			 * @param length Length of path to be drawn
			 */
			public void moveRight(int row, int col, int length) {
				enemyPath("right", row, col, length);
				RandomPath.setCurrentCol(RandomPath.getCurrentCol() + (length));
			}
		
			/**Choose a random up/down direction
			 * 
			 * @return Integer 0 or 1
			 */
			public int randomDirection() {
				return rand.nextInt(2);
			}
			
			/**Choose a random length when drawing path
			 * If path is moving right random length can be between 2 and 4
			 * If path is moving up/down random length can be between 2 and 7
			 * @param direction Which direction path is moving 
			 * @return Integer for random length 
			 */
			public int randomLength(String direction) {
				if (direction.equals("right") || direction.equals("left") ) {
					return rand.nextInt((4+1)-2)+2;
				}
				else if (direction.equals("up") || direction.equals("down")) {
					return rand.nextInt((7+1)-2)+2;
				}
				else {
					System.out.println("Invalid direction");
					return 0;
				}
			}
			
			/**Draws a path onto the Game grid
			 * 
			 * @param direction Which direction path is moving 
			 * @param row Starting row of the path
			 * @param col Starting row of the column
			 * @param length Length of the path to be drawn
			 */
			public static void enemyPath(String direction, int row, int col, int length) {

				int[] start = {col, row}; // start point of each section
				if (direction == "right") {
					int[] end = {col + length, row}; // end point for each section which changes based on direction
					int[][] temp = new int[][] {start, end}; // putting both together into an int[][]
					Enemy.addToList(temp); // adds to the enemy pathList
					
					for (int i = col; i < (length + col); i++) {
						Rectangle enemypath = new Rectangle(Game.getTileSize(), Game.getTileSize());
						enemypath.setFill(Color.BROWN);
						GridPane.setRowIndex(enemypath, row);
						GridPane.setColumnIndex(enemypath, i);
						Game.getGridpane().getChildren().addAll(enemypath);
						Game.getTextgame().editGridPath(row, i, " ");
					}
				}
				if (direction == "left") {
					
					int[] end = {col - length, row};
					int[][] temp = new int[][] {start, end};
					Enemy.addToList(temp);
					
					for (int i = col; i > (col - length); i--) {
						Rectangle enemypath = new Rectangle(Game.getTileSize(), Game.getTileSize());
						enemypath.setFill(Color.BROWN);
						GridPane.setRowIndex(enemypath, row);
						GridPane.setColumnIndex(enemypath, i);
						Game.getGridpane().getChildren().addAll(enemypath);
						Game.getTextgame().editGridPath(row, i, " ");
					}
				}
				if (direction == "up") {
					
					int[] end = {col, row - length};
					int[][] temp = new int[][] {start, end};
					Enemy.addToList(temp);
					
					for (int i = row; i > (row - length); i--) {
						Rectangle enemypath = new Rectangle(Game.getTileSize(), Game.getTileSize());
						enemypath.setFill(Color.BROWN);
						GridPane.setRowIndex(enemypath, i);
						GridPane.setColumnIndex(enemypath, col);
						Game.getGridpane().getChildren().addAll(enemypath);
						Game.getTextgame().editGridPath(i, col, " ");

					}
				}
				
				if (direction == "down") {
					
					int[] end = {col, row + length};
					int[][] temp = new int[][] {start, end};
					Enemy.addToList(temp);
					
					for (int i = row; i < (length + row); i++) {
						Rectangle enemypath = new Rectangle(Game.getTileSize(), Game.getTileSize());
						enemypath.setFill(Color.BROWN);
						GridPane.setRowIndex(enemypath, i);
						GridPane.setColumnIndex(enemypath, col);
						Game.getGridpane().getChildren().addAll(enemypath);
						Game.getTextgame().editGridPath(i, col, " ");
					}
				}
			}

			public static int getCurrentCol() {
				return currentCol;
			}

			public static void setCurrentCol(int currentCol) {
				RandomPath.currentCol = currentCol;
			}

			public static int getCurrentRow() {
				return currentRow;
			}

			public static void setCurrentRow(int currentRow) {
				RandomPath.currentRow = currentRow;
			}
	}
