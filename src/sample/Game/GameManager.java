package sample.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javafx.scene.paint.Color;
import sample.GameElements.*;

public class GameManager {
	
	Random powerUp;
	private int randomNumber;
	private boolean powerUpBoolean;
	
	private static Color brickColor;
	
	private int paddleColorId;
	private int brickColorId;
	
	private float musicVolumeVal;
	private float effectsVolumeVal;
	
	private int level;
	//Constructor 
	GameManager() throws IOException {
		
		this.powerUp = new Random();
		this.level = 1;
		getSettings();
		setupSettings();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Function that ends the game on lose
	public void gameEnd() {
		
	}
	
	//Function that gets the settings from a textfile
	private void getSettings() throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Settings.txt"));
    	
		String[] parts = reader.readLine().split(" ", 4);
		paddleColorId = Integer.parseInt(parts[0]);
		brickColorId = Integer.parseInt(parts[1]);
		musicVolumeVal = Float.parseFloat(parts[2])/100;
		effectsVolumeVal = Float.parseFloat(parts[3])/100;
		
        reader.close();
	}
	
	//Function, that determines brick color
	private void setupSettings() {
		switch(brickColorId)
		{
		case 1:
			brickColor = Color.RED;
			break;
		case 2:
			brickColor = Color.ORANGE;
			break;
		case 3:
			brickColor = Color.YELLOW;
			break;
		case 4:
			brickColor = Color.GREEN;
			break;
		case 5:
			brickColor = Color.LIGHTBLUE;
			break;
		case 6:
			brickColor = Color.BLUE;
			break;
		case 7:
			brickColor = Color.PURPLE;
			break;
		}
		
	}
}

