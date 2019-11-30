import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;




public class ImageLoader {
	
	private static MediaPlayer music;
	
	/**Takes image filename from "res/images/" directory and sets it as window background
	 * 
	 * @param filename
	 * @return ImageView of background image
	 * @throws FileNotFoundException
	 */
	public static ImageView menuBackgroundImage(String filename) throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream("res/images/" + filename);
		Image image = new Image(inputStream);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(MainMenu.getWidth()); //Set image to current window dimensions 
		imageView.setFitHeight(MainMenu.getHeight());
		
		return imageView;
		
	}
	
	/** Fills shape object with image from filename
	 *
	 * @param filename Name of image
	 * @param s Shape object to be filled
	 */
	public static void setImage(String filename, Shape s) 
	{
		FileInputStream inputStream;
		ImagePattern img = null; 
		try {
			inputStream = new FileInputStream("res/images/" + filename);
			Image image = new Image(inputStream);
			img = new ImagePattern(image);
			s.setFill(img);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
	}
	/**
	 * creates a mediaPlayer with the song file from the specified path
	 * @param path Sound file path
	 * @return MediaPlayer object
	 */
	public static void playMusic(String path)
	{
		File source = new File(path);
	 	Media media = new Media(source.toURI().toString()); 
	 	music = new MediaPlayer(media);
		music.setCycleCount(MediaPlayer.INDEFINITE);
	 	music.play(); 
	}
}
