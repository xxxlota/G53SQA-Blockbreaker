package sample.GameElements;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

	public static int THICKNESS = 30;
	public static int LENGTH = 45;
	
	Rectangle paddle; 
	Image paddleImage;
	String paddleImagefile;
    
    
    //root.getChildren().add( bg );
	
    public Paddle(Group root, int paddleId, double x, double y, String orientation, int colorId) {
    	
    	setUpColor(colorId);
    	paddle = new Rectangle(1225, 600); 
    	paddleImage = new Image("file:src/sample/images/" + paddleImagefile);
    	paddle.setFill(new ImagePattern(paddleImage));
    	
    	root.getChildren().add( paddle );
    }
    
    private void setUpColor(int colorId) {
    	switch(colorId) {
    	case 1:
    		this.paddleImagefile = "Red.png";
    		break;
    	case 2:
    		this.paddleImagefile = "Yellow.png";
    		break;
    	case 3:
    		this.paddleImagefile = "Green.png";
    		break;
    	case 4:
    		this.paddleImagefile = "Blue.png";
    		break;
    	case 5:
    		this.paddleImagefile = "Ball.png";
    		break;
    	}
    }
    
}



