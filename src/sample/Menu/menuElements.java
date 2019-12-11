package sample.Menu;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class menuElements {
	
	private static final Font FONT = Font.font("", FontWeight.BOLD, 27);
	
	//Constructor - won't be used
	menuElements(){
		
	}
	
	//Class for a background
	public static class background extends Rectangle{
		
		//Constructor
		public background() {
			this.setWidth(1225);
			this.setHeight(600);
	    	Image backgroundImage = new Image("file:src/sample/SystemElements/Images/start.png");
	    	this.setFill(new ImagePattern(backgroundImage));
		}
		
	}
	
	//Class for a menu item
	public static class menuItem extends HBox {
    	
		private menuChooser chooserLeft = new menuChooser(), chooserRight = new menuChooser();
        private Text text;
        private Runnable script;
        
        //Constructor
        public menuItem(String itemName) {
            super(15);
            setAlignment(Pos.BASELINE_LEFT);

            text = new Text(itemName);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2)); 
            
            getChildren().addAll(chooserLeft, text, chooserRight);
            setActive(false);
        }
        
        //Function to highlight chosen element of the menu
        public void setActive(boolean active) {
        	chooserLeft.setVisible(active);
        	chooserRight.setVisible(active);
            text.setFill(active ? Color.GREEN : Color.GREY);
        }
        
        //Function to adjust a script for menu item
        public void setOnActivate(Runnable r) {
            script = r;
        }
        
        //Function to execute adjusted script
        public void activate() {
            if (script != null)
                script.run();
        }
    }
	
	//Class for a leaderboard elements
	public static class leaderboardItem extends HBox {
    	
        private Text text;
        
        //Constructor
        public leaderboardItem(String score) {
            super(15);
            setAlignment(Pos.BASELINE_LEFT);

            text = new Text(score);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));
            text.setFill(Color.BLUE);

            getChildren().addAll(text);
        }
    }
		
	//Class for a ball next to a chosen menu element
	public static class menuChooser extends Circle {
		
		//Constructor
        public menuChooser() {
        	this.setRadius(10);
            Image img = new Image("file:src/sample/SystemElements/Images/ball.png");
            this.setFill(new ImagePattern(img));
            setEffect(new GaussianBlur(2));
        }
    }
	
}
