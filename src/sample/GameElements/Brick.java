package sample.GameElements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Brick extends Rectangle {

	Random dice = new Random();
	//To do: set up double randomizer. First to determine if block has powerUp second for powerUp itself
	
	private Color brickColor;
	public static int THICKNESS = 30;
	public static int LENGTH = 45;
	
	private boolean powerUp;
	public boolean destroyed;
	
	//Constructor
    public Brick(double x, double y, Color color) {
    	
    	this.destroyed = false;
    	this.setVisible(!destroyed);
    	this.brickColor = color;
    } 
    
    public void destroy() {
    	
    	if(powerUp) dropPowerUp();
    	destroyed = true;
    	this.setVisible(!destroyed);
    }
    
    private void dropPowerUp() {
    	
    }
}


