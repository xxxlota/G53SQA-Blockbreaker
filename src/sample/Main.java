package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import sample.Menu.GameMenu;
import sample.Menu.MusicPlayer;

public class Main extends Application {
	
	public MusicPlayer musicPlayerObject;

    @Override
    public void start(Stage theStage) throws Exception{

    	//Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    	
    	//Setting window name 
        theStage.setTitle( "Block Buster" );
    	
        //Setting background music
        musicPlayerObject = new MusicPlayer("Background.mp3", true);
        playMusic();
        
        //Creating scene for background and setting screen size. 
        Group root = new Group();
        Scene theScene = new Scene( root, 1500, 1000 );
        theScene.getStylesheets().add("file:src/sample/Main.css");
        
        //Note: Would be a great practice to make it stretch according to the screen
        //Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        //Scene theScene = new Scene( root, screenBounds.getMaxX(), screenBounds.getMaxY() );
        
        
        //Setting background image
        Image backgroundImage = new Image("file:src/sample/SystemElements/Images/Start.png");
        theScene.setFill(new ImagePattern(backgroundImage));
        theStage.setScene( theScene );
        
        //Implementing Game Menu on a starting screen
    	GameMenu gameMenu = new GameMenu(root);
    	gameMenu.createMenu(root, true);
    	theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameMenu.changeItemHandler);
    	
    	//Passing Game Menu to the window and making it visible
    	theStage.setResizable(false);
        theStage.sizeToScene();
        theStage.show(); 
    }
    
    public MusicPlayer getMusicPlayerObject() {
    	return musicPlayerObject;
    }
    
    public void changeVolume(float volume) {
    	musicPlayerObject.setVolumeFunction(volume);
    }
    
    public void stopMusic() {
    	musicPlayerObject.stopPlaying();
    }
    
    public void playMusic() {
    	musicPlayerObject.beginPlaying();
    }

    public static void main(String[] args) {
        launch(args);
    }
}