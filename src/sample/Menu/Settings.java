
package sample.Menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.Menu.MenuElements.*;

public class Settings {
	
	    public VBox menuBox;
	    private int currentItem;
		private Group root;
		
		sliderElement musicVolumeSlider;    
		sliderElement effectsVolumeSlider;
		
		DecimalFormat formatter;
		
		public pointerInt paddleColor;
		public pointerInt brickColor;
		public pointerFloat musicVolume;
		public pointerFloat effectsVolume;
		
		public EventHandler<KeyEvent> changeItemHandler;

		//Constructor
	    public Settings(Group root) throws IOException {
	    	
	    	this.root = root;
	    	this.currentItem = 0;
	    	this.menuBox = new VBox();
	    
	    	paddleColor = new pointerInt();
			brickColor = new pointerInt();
	    	musicVolume = new pointerFloat();
	    	effectsVolume = new pointerFloat();
	    	
			
	    	getSettings();
	    	
	    	musicVolumeSlider = new sliderElement(musicVolume); 
	    	effectsVolumeSlider = new sliderElement(effectsVolume); 
	    	
	    	changeItemHandler = new EventHandler<KeyEvent>() {  
	            public void handle(KeyEvent event) { 
	            	changeMenuItem(event);
	            }
	        };
	    }
	    
	    
	    //Function to create settings menu
	    public void createMenu(Group root) throws IOException {
	        
	    	//Can be restored for advanced menu mechanics
	    	//menuItem paddleColorLabel = new menuItem("PADDLE COLOR:", false);
	    	//menuItem paddleColorChanger = new menuItem("", true);
	    	//menuItem brickColorLabel = new menuItem("BRICK COLOR:", false);
	    	//menuItem brickColorChanger = new menuItem("", true);
	    	
	    	menuItem paddleColorLabel = new menuItem("CHANGE PADDLE COLOR");
	    	paddleColorLabel.setOnActivate(() -> {
				try {
					this.paddleColoring();
				} catch (IOException e2) {
					
					e2.printStackTrace();
				}
			});
	    	
	    	menuItem brickColorLabel = new menuItem("CHANGE BRICK COLOR");
	    	brickColorLabel.setOnActivate(() -> {
				try {
					this.brickColoring();
				} catch (IOException e2) {
					
					e2.printStackTrace();
				}
			});
	    	
	    	menuItem musicVolumeLabel = new menuItem("MUSIC VOLUME:", false);
	    	
	    	menuItem musicVolumeChanger = new menuItem("", true);
	    	musicVolumeChanger.getChildren().add(musicVolumeSlider);
	    	
	    	menuItem effectsVolumeLabel = new menuItem("EFFECTS VOLUME:", false);
	    	
	    	menuItem effectsVolumeChanger = new menuItem("", true);
	    	effectsVolumeChanger.getChildren().add(effectsVolumeSlider);
	    	
	        menuItem saveSettings = new menuItem("SAVE");
	        saveSettings.setOnActivate(() -> {
				try {
					this.saveSettings();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			});
	        
	        menuItem restoreDefaults = new menuItem("RESTORE DEFAULTS");
	        restoreDefaults.setOnActivate(() -> {
				try {
					this.restoreDefaults();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			});
	        
	        menuItem gotoMenu = new menuItem("RETURN");
	        gotoMenu.setOnActivate(() -> {
				try {
					this.returnToMain();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
	        
	        menuBox = new VBox(1,
	        		paddleColorLabel,
	        		//paddleColorChanger,
	        		brickColorLabel,
	        		//brickColorChanger,
	        		musicVolumeLabel,
	        		musicVolumeChanger,
	        		effectsVolumeLabel,
	        		effectsVolumeChanger,
	        		saveSettings,
	                restoreDefaults,
	                gotoMenu);
	        menuBox.setTranslateX(50);
	        menuBox.setTranslateY(30);
	        
	        getMenuItem(currentItem).setActive(true);
	        

	        root.getChildren().addAll(menuBox);
	        checkIfEnabled();
	    }

	   //Function to change menu item
	    public void changeMenuItem(KeyEvent event) {
	    	
	 	   switch(event.getCode()) {
	        case UP:
	     	   if (this.getMenuItem(this.currentItem - 1).selectableItem()) {
	     		   
	                this.getMenuItem(this.currentItem).setActive(false);
	                this.getMenuItem(--this.currentItem).setActive(true);
	            }
	     	   else if (this.currentItem - 1 > 0 ) {
	     		   
	     		   this.getMenuItem(this.currentItem).setActive(false);
	     		   this.currentItem--;
	                this.getMenuItem(--this.currentItem).setActive(true);
	     	   }
	     	  checkIfEnabled();
	     	   break;
	        case DOWN:
	        	if(this.currentItem < menuBox.getChildren().size() - 1) {
	        		
	        		if (this.getMenuItem(this.currentItem + 1).selectableItem()) {
	        			
		                this.getMenuItem(this.currentItem).setActive(false);
		                this.getMenuItem(++this.currentItem).setActive(true);
		            }
		     	   else {
		     		   
		     		   this.getMenuItem(this.currentItem).setActive(false);
		     		   this.currentItem++;
		               this.getMenuItem(++this.currentItem).setActive(true);
		     	   }
	        	}
	        	checkIfEnabled();
	        	break;
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
			paddleColor.value = Integer.parseInt(parts[0]);
			brickColor.value = Integer.parseInt(parts[1]);
			musicVolume.value = Float.parseFloat(parts[2]);
			effectsVolume.value = Float.parseFloat(parts[3]);
			
	        reader.close();
		}
	    
	    //Function to change settings and store into textfile
		private void saveSettings() throws IOException {
			
			Main.musicPlayerObject.setVolumeFunction(musicVolume.value / 100);

			BufferedWriter writer = new BufferedWriter(new FileWriter("src/sample/SystemElements/Textfiles/Settings.txt"));

			PrintWriter printWriter = new PrintWriter(writer);

			printWriter.printf(paddleColor.value + " " + brickColor.value + " " + (int) musicVolume.value + " " + (int) effectsVolume.value );
			
			printWriter.close();
			writer.close();
			}
			
	    
	    //Function to restore default settings
	    private void restoreDefaults() throws IOException {
	    	
	    	Main.musicPlayerObject.setVolumeFunction((float) 0.4);
	    	
	    	musicVolumeSlider.setValue(40);
	    	effectsVolumeSlider.setValue(40);
	    	
	    	BufferedWriter writer = new BufferedWriter(new FileWriter("src/sample/SystemElements/Textfiles/Settings.txt"));

			PrintWriter printWriter = new PrintWriter(writer);

			printWriter.printf(1 + " " + 1 + " " + 40 + " " + 40 );
			
			printWriter.close();
			writer.close();
	    }
	    
	    //Function that oppens paddle color menu
	    private void paddleColoring() throws IOException {
	    	
	    	this.getSettings();
	    	this.saveSettings();
		
	    	this.root.getChildren().clear();
		   
	    	Scene scene = this.root.getScene();
		    Stage theStage = (Stage) scene.getWindow();
		        
		    PaddleColor paddleColorMenu = new PaddleColor(root, this.paddleColor);
		    paddleColorMenu.createMenu(root);
		        
		    scene.removeEventHandler(KeyEvent.KEY_PRESSED, changeItemHandler);
		    scene.addEventHandler(KeyEvent.KEY_PRESSED, paddleColorMenu.changeItemHandler);
		  
		    theStage.setResizable(false);
		    theStage.sizeToScene();
		}
	   
	    //Function that opens brick color menu
	    private void brickColoring() throws IOException {
		   
	    	this.getSettings();
	    	this.saveSettings();
	    	
	    	this.root.getChildren().clear();
		   
			Scene scene = this.root.getScene();
		    Stage theStage = (Stage) scene.getWindow();
		        
		    BrickColor brickColorMenu = new BrickColor(root, this.brickColor);
		    brickColorMenu.createMenu(root);
		        
		    scene.removeEventHandler(KeyEvent.KEY_PRESSED, changeItemHandler);
		    scene.addEventHandler(KeyEvent.KEY_PRESSED, brickColorMenu.changeItemHandler);
		    	
		    theStage.setResizable(false);
		    theStage.sizeToScene();
		}
    
	    //Function that returns to main
	    private void returnToMain() throws IOException {
	    	
	    	this.getSettings();
	    	this.saveSettings();
	    	
	    	this.root.getChildren().clear();
     
	        Scene scene = this.root.getScene();
	        Stage theStage = (Stage) scene.getWindow();
	        
	        GameMenu gameMenu = new GameMenu(root);
	        gameMenu.createMenu(root, true);
	        
	        scene.removeEventHandler(KeyEvent.KEY_PRESSED, changeItemHandler);
	    	scene.addEventHandler(KeyEvent.KEY_PRESSED, gameMenu.changeItemHandler);
	    	
	    	theStage.setResizable(false);
	        theStage.sizeToScene();
	    }
	    
	    //Funtion that checks if slider or menu bar is chosen and activates/deactivates them
	    private void checkIfEnabled() {
	    	
	    	musicVolumeSlider.setDisable(true);
	    	effectsVolumeSlider.setDisable(true);
    		
	    	if(this.currentItem == 3) {
	    		musicVolumeSlider.setDisable(false);
	    	}
	    	else if (this.currentItem == 5) {
	    		effectsVolumeSlider.setDisable(false);
	    	}
	    		
	    }
	    
	    //Classes for pointers
	  	public class pointerInt {
	  			
	  		public int value;
	  		    
	  		public pointerInt() {
	  		    	
	  		    value = 0;
	  		}
	  	}
	  		
	  	public class pointerFloat {
	  			
  		    public float value;
  		    
  		    public pointerFloat() {
  		    	
  		    	value = 0;
  		    }
  		}
}