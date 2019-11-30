import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

public class HighScore {
	// all the variables for the file to be written into, the hashmap for string and integers
	private static String fileToWriteOnto = "output.txt";
	private static HashMap<String, BigInteger> scores = new HashMap<String, BigInteger>();
	private static String name;
	private static String highscorestring;
	
	/**
	 * returns the name of the person playing
	 * @return - name of the person playing
	 */
	public static String getName() {
		return name;
	}
	
	/**
	 * sets the name of the person playing, used in game to set the name written to the file
	 * @param s
	 */
	public static void setName(String s) {
		name = s;
	}
	
	/**
	 * constructor that sets the file output to the variable assigned at the top
	 */
	public HighScore() {
		fileToWriteOnto = "output.txt";
	}
	
	/**
	 * writes to a certain file, according to the output file and the score that is given
	 * @param output - the file to be written to
	 * @param newScore - the score that the person with the name set has gotten 
	 */
	public static void writeFile(String output, String newScore) {
		File outputFile = new File(output);
		PrintWriter pw = null;
		try { pw = new PrintWriter(new FileWriter(outputFile, true)); } 
		catch (IOException e)
		{
			System.out.println("Cannot open file to write");
			return;
		}
		pw.println(newScore);
		pw.close();
	}
	
	/**
	 * reads the file according to the fileoutput and checks what the values are
	 * @param output
	 */
	public static void readFile(String output) {
		Scanner scan;
		try { scan = new Scanner(new FileReader(output));
		while (scan.hasNextLine()) {
            String nextline = scan.nextLine();
            List<String> lst = Arrays.asList(nextline.split(" "));
            String score = lst.get(lst.size() - 2);
            BigInteger number = new BigInteger(score);
            String name = lst.get(0) + " " + lst.get(1);
            scores.put(name, number);
		}
		scan.close();
		}
		catch(IOException e) {
			System.out.println("cannot open file to read");
		}
		
	}
	
	/**
	 * arranges the values that were taken, gets the highest value and sets the high score to be that value
	 */
	public static void sortValues() {
		List<BigInteger> values = new ArrayList<BigInteger>();
		
		for (Entry<String, BigInteger> iterate : scores.entrySet()) {
			BigInteger Score = iterate.getValue();
			values.add(Score);		
		}

		//sorting
		Collections.sort(values);
		Collections.reverse(values);
		//reversing to get the highest value
		
		ArrayList<String> testingsorting = new ArrayList<String>();
		for(int a =0; a < values.size(); a++) {
			BigInteger sortedScores = values.get(a);
			// going through the hashmap and getting the high score name
			for( String nameInScore : scores.keySet()) {
				if(scores.get(nameInScore).equals(sortedScores)) {
					testingsorting.add(nameInScore);
				}
			}
			
		}
		//returning the highest value and setting the highscorestring to be the highscore value
		String testingname = testingsorting.get(0);
		BigInteger scoretoreturn = scores.get(testingname);
		highscorestring = testingname + " " + scoretoreturn + " pts";
}
	/**
	 * prints out the hashmap, used for testing mainly or to check if the hashmap has the values necessary
	 */
	public void printHashMap() {
		System.out.println(scores.toString());
	}
	
	/**
	 * returns the highscore text that is used in game to set to the highscore. 
	 * @param points
	 * @return - the string that is used to set the highscore: in game
	 */
	public static String returnHighScore(int points) {
		String nametowrite = name + " " + points + " pts";
		writeFile(fileToWriteOnto, nametowrite);
		readFile(fileToWriteOnto);
		sortValues();
		return highscorestring;
	}

}
