package sample.GameElements;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ball extends Circle{
	
	
	//JavaFX Point2D for movement
	
	public Ball(int radius) {
		
		this.setRadius(15);
        Image img = new Image("file:src/sample/SystemElements/Images/Ball.png");
        this.setFill(new ImagePattern(img));
        
	};
	
}
