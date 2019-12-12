package sample.Menu;

import javafx.scene.text.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import sample.Main;
import sample.Menu.Settings.pointerFloat;
//import sample.Menu.Settings.pointerInt;

public class MenuElements {
	
	public static final Font FONT = Font.font("", FontWeight.BOLD, 40);
	
	//Constructor - won't be used
	MenuElements() {
		
	}
	
	//Class for a menu item
	public static class menuItem extends HBox {
    	
		private menuChooser chooserLeft = new menuChooser(), chooserRight = new menuChooser();
        private Text text;
        private Runnable script;
        private boolean selectable;
        private boolean canBeChanged;
        private boolean active;
        
        //Contructor for basic menu items
        public menuItem(String itemName) {
            super(15);
            setAlignment(Pos.CENTER_LEFT);
            this.selectable = true;
            this.canBeChanged = false;
            
            text = new Text(itemName);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2)); 

            getChildren().addAll(chooserLeft, text, chooserRight);

            setActive(false);
        }
        
        //Constructor for option menu ites
        public menuItem(String itemName, boolean selectable) {
        	
            super(15);
            setAlignment(Pos.CENTER_LEFT);
            this.selectable = selectable;
            if(this.selectable) this.setPadding(new Insets(15, 0, 30, 0));

            text = new Text(itemName);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2)); 
            
            if(this.selectable) {
            	this.canBeChanged = true;
            	getChildren().addAll(chooserLeft);
            }
            else {
            	this.canBeChanged = false;
            	getChildren().addAll(chooserLeft, text);
            }

            setActive(false);
        }
        
        
        
        //Function to highlight chosen element of the menu
        public void setActive(boolean active) {
        	
        	this.active = active;
        	
        	chooserLeft.setVisible(this.active);
        	chooserRight.setVisible(this.active);
            text.setFill(this.active ? Color.GREEN : Color.GREY);
        }
        
        
        //Function that shows if item is currently active in settings menu
        public boolean isActive() {
        	
        	return this.active;
        }
        
        //Function that shows if item is available for changing (Slider, Element menu)
        public boolean isChangeable() {
        	
        	return this.canBeChanged;
        }
        
        //Function to adjust a script for menu item
        public void setOnActivate(Runnable r) {
        	
            script = r;
        }
        
        public boolean selectableItem() {
        	
        	return this.selectable;
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
	
	//Class for a slider
	public static class sliderElement extends Slider {
		
		pointerFloat pointer;
		
		//Constructor
		public sliderElement(pointerFloat changeableValue) {
			
			this.setMin(0);
			this.setMax(100);
			this.setValue(changeableValue.value);
			
			this.setSnapToTicks(true);
	        this.setMinorTickCount(1);
	        this.setMajorTickUnit(1);
	        this.setBlockIncrement(1);
	        
	        this.setMinWidth(500);
	        this.setMinHeight(50);
	        
	        pointer = changeableValue;
	        
	        this.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                Number oldValue, Number newValue) {
	            	pointer.value = newValue.floatValue();
	            	Main.musicPlayerObject.setVolumeFunction(pointer.value / 100);
	            }
	        });
		}
	}
	
	
		
	//Class for a ball next to a chosen menu element
	public static class menuChooser extends Circle {
		
		//Constructor
        public menuChooser() {
        	
        	this.setRadius(15);
            Image img = new Image("file:src/sample/SystemElements/Images/Ball.png");
            this.setFill(new ImagePattern(img));
        }
    }
}
