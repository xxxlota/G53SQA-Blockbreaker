package sample.Menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Menu.menuElements.*;

public class leaderboard {
	
	
	public VBox menuBox;
    public VBox leaderboardBox;
    private int currentItem;
	private Group root;
	private background backgroundRendered;
	
	TextArea results;

    private int[] scoresArray;

	public EventHandler<KeyEvent> changeItemHandler;
	
	//Constructor
    public leaderboard(Group root, int lifesLeft) throws FileNotFoundException {
    	
    	this.backgroundRendered = new background();
    	this.root = root;
    	this.currentItem = 0;
    	this.menuBox = new VBox();
    	
    	changeItemHandler = new EventHandler<KeyEvent>() {  
            public void handle(KeyEvent event) { 
            	changeMenuItem(event);
            }
        };
        
        scoresArray = new int[16];
    }

	//Function to create a menu
	public void createMenu(Group root) throws IOException {
	 	   
	   	menuItem gotoMenu = new menuItem("RETURN");
	   	gotoMenu.setOnActivate(() -> {
			try {
				this.returnToMain();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	   	
        menuItem clear = new menuItem("CLEAR LEADERBOARD");
        clear.setOnActivate(() -> {
			try {
				this.clearLeaderboard();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
        

        menuBox = new VBox(1,
                clear,
                gotoMenu);
        menuBox.setTranslateX(10);
        menuBox.setTranslateY(30);
        
        getMenuItem(0).setActive(true);

        root.getChildren().addAll(menuBox);
        this.getLeaderboard();
        showScores();
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
	           }  break; 
	       case ENTER:
	    	   	   this.getMenuItem(this.currentItem).activate();
	    	   	   break;
	       default:
			break;
	    	   }
	}
	
	//Function that highlights chosen menu option
	private menuItem getMenuItem(int index) {
		
        return (menuItem)this.menuBox.getChildren().get(index);
    }
	
	//Function to clear leaderboard file and the leaderboard itself
	private void clearLeaderboard() throws IOException {
		
    	this.writeScoresToFile(0, false);
        this.root.getChildren().clear();
        
        this.setBackground();
        
        this.createMenu(root);
        this.showScores();
    }
    
    //Function to add the score to a leaderboard
    private void addScore(int score) {
    	
    	for(int i = 14; i >=0; i--)
    	{
    		if(scoresArray[i] < score)
    		{
    			scoresArray[i+1] = scoresArray[i];
    			scoresArray[i] = score;
    		}
    		else
    		{
    			break;
    		}
    	}
    }
    
    //Function that writes scores to a text file 
    public void writeScoresToFile(int score, boolean write) throws IOException {
    	
    	BufferedWriter writer = new BufferedWriter(new FileWriter("src/sample/Leaderboard.txt"));
	    PrintWriter printWriter = new PrintWriter(writer);
	    
    	if(write)
    	{
			addScore(score);
			
		    for(int i = 1; i <= 15; i++)
    		{	
		    	if(scoresArray[i-1] > 0)
		    	{
		    		printWriter.printf("" + i + ". " + scoresArray[i-1]);
		    	}
		    	else
		    	{
		    		printWriter.printf("" + i + ". ---\n");
		    	}
    		}
    	}
    	else
    	{
    		for(int i = 1; i <= 15; i++)
    		{
    			printWriter.printf("" + i + ". ---\n");
    		}
    	}
    	printWriter.close();
    }
 
 	//Function to create a leaderboard
    private void showScores() throws IOException {
    	
    	BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Leaderboard.txt"));
    	VBox leaderboardBox = new VBox();
    	for( int i=0; i < 15; i++) {
    		leaderboardItem leaderboardItem = new leaderboardItem(reader.readLine());
    		leaderboardBox.getChildren().add(leaderboardItem);
        }

        leaderboardBox.setTranslateX(800);
        leaderboardBox.setTranslateY(30);

        root.getChildren().addAll(leaderboardBox);
        reader.close();
    }
    
    
	//Function to read all scores from leaderboard file and store them into array
    public void getLeaderboard() throws IOException {
    	
    	BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Leaderboard.txt"));
    	for(int i = 0; i < 15; i++)
		{
	        String var = reader.readLine();
	        boolean empty = (var.contains("-") ? true : false);
	        
	        if(empty)
	        {
	        	scoresArray[i] = 0;
	        }
	        else
	        {
	        	String[] parts = var.split(" ", 2);
	        	scoresArray[i] = Integer.parseInt(parts[1]);
	        	
	        }
		}
    	reader.close();
    	
   }

	
	//Function that returns back to gameEndMenu with ability to proceed to the next stage if succeeded to pass, or restart if lost
	private void returnBack(int level, int lifesLeft) {
    	
    	//To do: implement different leaderboard return mechanics, such as returning to main menu or to a mid-game menu
    	
    }
    
	//Function that returns to main menu
    private void returnToMain() throws IOException {
    	
    	this.root.getChildren().clear();
        
        Scene scene = this.root.getScene();
        Stage theStage = (Stage) scene.getWindow();
        
        gameMenu gameMenu = new gameMenu(root);
        gameMenu.createMenu(root, true);
        
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, changeItemHandler);
    	scene.addEventHandler(KeyEvent.KEY_PRESSED, gameMenu.changeItemHandler);
    	
    	theStage.setResizable(false);
        theStage.sizeToScene();
    }
    
    //Function to set a background
    private void setBackground() {
    	
	this.root.getChildren().clear();
	this.root.getChildren().add(backgroundRendered);
    }
}
