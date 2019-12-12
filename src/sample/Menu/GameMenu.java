package sample.Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Menu.MenuElements.*;

public class GameMenu {

	public MusicPlayer musicPlayerObject;
	
    public VBox menuBox;
    private int currentItem;
	private Group root;
	
	public EventHandler<KeyEvent> changeItemHandler;

	//Constructor
    public GameMenu(Group root) throws IOException {
    	
    	this.root = root;
    	this.currentItem = 0;
    	this.menuBox = new VBox();
        
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
    public void createMenu(Group root, boolean showHighScoreBool) throws IOException {
    	
    	this.root.getChildren().clear();
    	
        menuItem startGame = new menuItem("PLAY");
        startGame.setOnActivate(() -> this.runGame());
        
        menuItem options = new menuItem("OPTIONS");
        options.setOnActivate(() -> {
			try {
				this.openOptions();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
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
        menuBox.setTranslateX(50);
        menuBox.setTranslateY(30);
        
        getMenuItem(0).setActive(true);
        
        if(showHighScoreBool) showHighScore();
        
        root.getChildren().addAll(menuBox);
    }
    
    //Function to show realtime high score 
    private void showHighScore() throws IOException {
    	
    	BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Leaderboard.txt"));
    	
    	VBox leaderboardBox = new VBox();
    	
    	leaderboardItem highScoreItem = new leaderboardItem("Current High Score:");
    	leaderboardBox.getChildren().add(highScoreItem);
    	
    	String currentHighString = reader.readLine();
    	String[] scoreStorage = currentHighString.split(" ", 2);
    	leaderboardItem leaderboardItem = new leaderboardItem(scoreStorage[1]);
    	leaderboardBox.getChildren().add(leaderboardItem);

        leaderboardBox.setTranslateX(1050);
        leaderboardBox.setTranslateY(30);

        root.getChildren().addAll(leaderboardBox);
        reader.close();
    }
    
	//Function to run game   
	private void runGame() {
		
		this.root.getChildren().clear();
	    	
	    	//To do:Set up difficulies, levels and etc.
	}
	    
	//Function to open options
	private void openOptions() throws IOException {
	    	
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
	
	//Function to open leaderboard  
	private void openLeaderboard() throws IOException {
		
		this.root.getChildren().clear();
	
		Leaderboard leaderboard = new Leaderboard(this.root, 0);
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
    
    //Function to change music volume
    public void changeVolume(float volume) {
    	
    	musicPlayerObject.setVolumeFunction(volume);
    }
    
    //Function to stop music
    public void stopMusic() {
    	
    	musicPlayerObject.stopPlaying();
    }
    
    //Function to play music
    public void playMusic() {
    	
    	musicPlayerObject.beginPlaying();
    }
    
}
