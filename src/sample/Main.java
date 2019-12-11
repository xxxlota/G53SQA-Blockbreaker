package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Menu.gameMenu;

public class Main extends Application {

	public static MediaPlayer musicPlayer;

    @Override
    public void start(Stage theStage) throws Exception{

    	//Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    	
    	//Setting window name 
        theStage.setTitle( "Block Buster" );
    	
        //Setting background music
        String path = "src/sample/SystemElements/Sounds/Background.mp3";  
        Media musicFile = new Media(new File(path).toURI().toString());
        musicPlayer = new MediaPlayer(musicFile);
        musicPlayer.setAutoPlay(true);
        
        //Setting to play on repeat
        musicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                musicPlayer.seek(Duration.ZERO);
                musicPlayer.play();  
            }
        });
        
        
        BufferedReader reader = new BufferedReader(new FileReader("src/sample/SystemElements/Textfiles/Settings.txt"));
    	
		String[] parts = reader.readLine().split(" ", 4);
        reader.close();
        musicPlayer.setVolume(Float.parseFloat(parts[2])/100);
        
        //Creating canvas to pass the image and the game to
    	final Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        //Setting background image
        Rectangle background = new Rectangle(1225, 600);
        Image backgroundImage = new Image("file:src/sample/SystemElements/Images/start.png");
        background.setFill(new ImagePattern(backgroundImage));
        root.getChildren().add( background );
        
        //Implementing Game Menu on a starting screen
    	gameMenu gameMenu = new gameMenu(root);
    	gameMenu.createMenu(root, true);
    	theScene.addEventHandler(KeyEvent.KEY_PRESSED, gameMenu.changeItemHandler);
    	
    	//Passing Game Menu to the window and making it visible
    	theStage.setResizable(false);
        theStage.sizeToScene();
        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}