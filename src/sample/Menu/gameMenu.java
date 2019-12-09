package sample.Menu;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Menu.menuElements.background;
import sample.Menu.menuElements.menuItem;

public class gameMenu {

    public VBox menuBox;
    private int currentItem;
	private Group root;
	private background backgroundRendered;
	
	public EventHandler<KeyEvent> changeItemHandler;

	//Constructor
    public gameMenu(Group root)
    {
    	this.root = root;
    	this.currentItem = 0;
    	this.menuBox = new VBox();
    	
    	backgroundRendered = new background();
    	this.setBackground();
    	
    	changeItemHandler = new EventHandler<KeyEvent>() {  
            public void handle(KeyEvent event) { 
            	changeMenuItem(event);
            }
        };
    }
    
    /*
     * FUNCTIONS
     */
    
    //Function to create a menu
    public void createMenu(Group root) {
        menuItem startGame = new menuItem("PLAY");
        startGame.setOnActivate(() -> this.runGame());
        
        menuItem options = new menuItem("OPTIONS");
        options.setOnActivate(() -> this.openOptions());
        
        menuItem leaderboard = new menuItem("LEADERBOARD");
        leaderboard.setOnActivate(() -> {
			try {
				this.openLeaderboard();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        
        menuItem exitGame = new menuItem("EXIT");
        exitGame.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(1,
                startGame,
                options,
                leaderboard,
                exitGame);
        menuBox.setAlignment(Pos.TOP_LEFT);
        menuBox.setTranslateX(10);
        menuBox.setTranslateY(30);
        
        getMenuItem(0).setActive(true);

        root.getChildren().addAll(menuBox);
    }
    
	//Function to run game   
	private void runGame() {
	    	
		setBackground();
	    	
	    	//To do:Set up difficulies, levels and etc.
	}
	    
	//Function to open options
	private void openOptions() {
	    	
		setBackground();
	
	    	//To do:Set up options menu
	}
	
	//Function to open leaderboard  
	private void openLeaderboard() throws IOException {
	    	
		setBackground();
	
	    	//To do:Set up Leaderboard
		leaderboard leaderboard = new leaderboard(this.root, 0);
    	leaderboard.getLeaderboard();
    	leaderboard.createMenu(root);

    	Scene scene = this.root.getScene();
    	Stage theStage = (Stage) scene.getWindow();

    	theStage.sizeToScene();
    	theStage.setResizable(false);

    	scene.removeEventHandler(KeyEvent.KEY_PRESSED, changeItemHandler);
    	scene.addEventHandler(KeyEvent.KEY_PRESSED, leaderboard.changeItemHandler);
	}
    
	//Function to change menu item
	public void changeMenuItem(KeyEvent event) {
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
			} break; 
		case ENTER:
			this.getMenuItem(this.currentItem).activate();
			break;
		default:
			break;
		}
	}

	//Highlighting Menu item
    private menuItem getMenuItem(int index) {
        return (menuItem)this.menuBox.getChildren().get(index);
    }
    
    //Function to set a background
    private void setBackground() {
    	
	this.root.getChildren().clear();
	this.root.getChildren().add(backgroundRendered);
    }
    
}
