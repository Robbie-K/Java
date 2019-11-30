import java.io.FileNotFoundException;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Store {
	
	/**Adds a new tower to the store menu (ScrollPane)
	 * Displays all tower info as labels next to tower image
	 * Creates buy button by calling placeTower in Buttons class 
	 * 
	 * @param name Name of the tower
	 * @param image Image of the tower
	 * @param position Position in the storegrid
	 * @param price Price of the tower
	 * @param DMG_val How much damage tower does 
	 * @param Range_val How much range the tower has
	 * @throws FileNotFoundException
	 */
	public static void newTower(String name, String image, int position, int price, int DMG_val, int Range_val) throws FileNotFoundException {
		int pos = (((position * 3) +1) - 3); //Allows for proper spacing between differnt towers
		Shape item = new Rectangle(Game.getTileSize() * 2, Game.getTileSize() * 2);
		ImageLoader.setImage(image, item);
		item.setStroke(Color.BLACK);
		GridPane.setConstraints(item, 0, pos-1, 2, 2);
		
		Label NAME = new Label(name);
		GridPane.setConstraints(NAME, 2, pos-1, 1, 1, HPos.CENTER, VPos.CENTER);

		Label RANGE = new Label("RANGE: " + Range_val);
		GridPane.setConstraints(RANGE, 2, pos, 1, 1, HPos.CENTER, VPos.TOP);
		
		Label DMG = new Label("DMG: " + DMG_val);
		GridPane.setConstraints(DMG, 2, pos, 1, 1, HPos.CENTER, VPos.CENTER);
		
		Label ROF = new Label("COST: " + price);
		GridPane.setConstraints(ROF, 2, pos, 1, 1, HPos.CENTER, VPos.BOTTOM);
		
		Button BUY = Buttons.placeTower(price, position, DMG_val, Range_val, Game.getTowerList(), image);
		GridPane.setConstraints(BUY, 0, pos+1, 3, 1, HPos.CENTER, VPos.CENTER);
		Game.getStoregrid().getChildren().addAll(item, NAME, RANGE, DMG, ROF, BUY);
		
//		Game.getStoregrid().setGridLinesVisible(true);
	}
}
