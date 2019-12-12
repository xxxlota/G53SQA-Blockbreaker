package sample.Menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Menu.MenuElements.*;
import sample.Menu.Settings.pointerInt;


public class BrickColor {

    public VBox menuBox;
    private int currentItem;
	private Group root;

	public EventHandler<KeyEvent> changeItemHandler;

	private int colorIdVariable;

	//Constructor
    public BrickColor(Group root, pointerInt brickIdPointer)
    {
    	this.root = root;
    	this.currentItem = 0;
    	this.menuBox = new VBox();
    	
    	this.colorIdVariable = brickIdPointer.value;
    		
    	changeItemHandler = new EventHandler<KeyEvent>() {  
            public void handle(KeyEvent event) { 
            	changeMenuItem(event);
            }
        };
    }
    
    //Function that highlights chosen element
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
   
   //Function that creates colors menu
   public void createMenu(Group root) {
        menuItem red = new menuItem("RED");
        red.setOnActivate(() -> {
			try {
				returnBack(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem orange = new menuItem("ORANGE");
        orange.setOnActivate(() -> {
			try {
				returnBack(2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem yellow = new menuItem("YELLOW");
        yellow.setOnActivate(() -> {
			try {
				returnBack(3);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem green = new menuItem("GREEN");
        green.setOnActivate(() -> {
			try {
				returnBack(4);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem ltblue = new menuItem("LIGHT BLUE");
        ltblue.setOnActivate(() -> {
			try {
				returnBack(5);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem blue = new menuItem("BLUE");
        blue.setOnActivate(() -> {
			try {
				returnBack(6);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem purple = new menuItem("PURPLE");
        purple.setOnActivate(() -> {
			try {
				returnBack(7);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem rainbow = new menuItem("RAINBOW");
        rainbow.setOnActivate(() -> {
			try {
				returnBack(1); //Value: red. Currently unavailable
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem returnToPrev = new menuItem("RETURN");
        returnToPrev.setOnActivate(() -> {
			try {
				returnBack(colorIdVariable);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

        menuBox = new VBox(1,
        		red,
        		orange,
        		yellow,
        		green,
        		ltblue,
        		blue,
        		purple,
        		rainbow,
                returnToPrev);
        menuBox.setTranslateX(50);
        menuBox.setTranslateY(30);
        
        getMenuItem(0).setActive(true);

        root.getChildren().addAll(menuBox);
    }
    
    
   //Function that gets menu item id
	private menuItem getMenuItem(int index) {
		
        return (menuItem)this.menuBox.getChildren().get(index);
    }
    
	//Function that returns to settings menu
    private void returnBack(int colorId) throws IOException {
    	
    	setColor(colorId);
    	
        this.root.getChildren().clear();
        
        Settings options = new Settings(this.root);
    	options.createMenu(root);

    	Scene scene = this.root.getScene();
    	Stage theStage = (Stage) scene.getWindow();

    	theStage.sizeToScene();
    	theStage.setResizable(false);

    	scene.removeEventHandler(KeyEvent.KEY_PRESSED, changeItemHandler);
    	scene.addEventHandler(KeyEvent.KEY_PRESSED, options.changeItemHandler);
    }
    
    //Function that writes colorId to a textfile
	private void setColor(int colorId) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Settings.txt"));
    	
		String[] parts = reader.readLine().split(" ", 4);
		
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/sample/SystemElements/Textfiles/Settings.txt"));

		PrintWriter printWriter = new PrintWriter(writer);

		printWriter.printf(Integer.parseInt(parts[0]) + " " + colorId + " " + Integer.parseInt(parts[2]) + " " + Integer.parseInt(parts[3]) );
		
		printWriter.close();
		writer.close();
	}
}


