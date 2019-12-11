package sample.Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Main;
import sample.Menu.menuElements.*;

public class options {

	    public VBox menuBox;
	    private int currentItem;
		private Group root;
		
		private int paddleColor;
		private int brickColor;
		private float musicVolume;
		private float effectsVolume;
		

		public EventHandler<KeyEvent> changeItemHandler;

		//Constructor
	    public options(Group root)
	    {
	    	this.root = root;
	    	this.currentItem = 0;
	    	this.menuBox = new VBox();
	    	
	    	
	    	changeItemHandler = new EventHandler<KeyEvent>() {  
	            public void handle(KeyEvent event) { 
	            	changeMenuItem(event);
	            }
	        };
	    }
	    
	    //Function to create options menu
	    public void createMenu(Group root) throws IOException {
	        
	    	getSettings();
	    	
	        menuItem saveSettings = new menuItem("SAVE");
	        saveSettings.setOnActivate(() -> this.saveSettings());
	        
	        menuItem restoreDefaults = new menuItem("RESTORE DEFAULTS");
	        restoreDefaults.setOnActivate(() -> this.restoreDefaults());
	        
	        menuItem gotoMenu = new menuItem("RETURN");
	        gotoMenu.setOnActivate(() -> {
				try {
					this.returnToMain();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
	        
	        menuBox = new VBox(1,
	        		saveSettings,
	                restoreDefaults,
	                gotoMenu);
	        menuBox.setTranslateX(10);
	        menuBox.setTranslateY(470);
	        
	        getMenuItem(0).setActive(true);
	        

	        root.getChildren().addAll(menuBox);
	    }

	   //Function to change menu item
	   public void changeMenuItem(KeyEvent event)
	   {
		   switch(event.getCode()) {
	       case UP:
	    	   if (this.currentItem > 0) {
	               this.getMenuItem(this.currentItem).setActive(false);
	               this.getMenuItem(--this.currentItem).setActive(true);
	           } break;   
	       case DOWN:
	    	   if (this.currentItem < menuBox.getChildren().size() - 1) {
	               this.getMenuItem(this.currentItem).setActive(false);
	               this.getMenuItem(++this.currentItem).setActive(true);
	           }  break; 
	       case ENTER:
	    	   	   this.getMenuItem(this.currentItem).activate();
	    	   	   break;
	       default:
			break;
	    	   }
	   }
	    
	   //Function that highlights chosen element
		private menuItem getMenuItem(int index) {
	        return (menuItem)this.menuBox.getChildren().get(index);
	    }
		
		//Function that gets the settings from a textfile
		private void getSettings() throws IOException {
			
			BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Settings.txt"));
	    	
			String[] parts = reader.readLine().split(" ", 4);
			paddleColor = Integer.parseInt(parts[0]);
			brickColor = Integer.parseInt(parts[1]);
			musicVolume = Float.parseFloat(parts[2]);
			effectsVolume = Float.parseFloat(parts[3]);
			
			musicVolume /=  100;
			effectsVolume /= 100;
	        reader.close();
		}
	    
	    //Function to change settings and store into textfile
	    private void saveSettings() {
	    	float asd = (float) Main.musicPlayer.getVolume();
	    	System.out.println(asd);
	    	Main.musicPlayer.setVolume(musicVolume);
	    }
	    
	    //Function to restore default settings
	    private void restoreDefaults() {
	    	//To do: manual coloring menu
	    }
	    
	    //Function that returns to main
	    private void returnToMain() throws IOException 
	    {
	    	this.root.getChildren().clear();
	    	
	    	Rectangle bg = new Rectangle(1225, 600);
	        Image background = new Image("file:src/sample/images/start.png");
	        bg.setFill(new ImagePattern(background));
	        
	        Scene scene = this.root.getScene();
	        Stage theStage = (Stage) scene.getWindow();
	        
	        root.getChildren().add( bg );
	        
	        gameMenu gameMenu = new gameMenu(root);
	        gameMenu.createMenu(root, true);
	        
	        scene.removeEventHandler(KeyEvent.KEY_PRESSED, changeItemHandler);
	    	scene.addEventHandler(KeyEvent.KEY_PRESSED, gameMenu.changeItemHandler);
	    	
	    	theStage.setResizable(false);
	        theStage.sizeToScene();
	    }
}
