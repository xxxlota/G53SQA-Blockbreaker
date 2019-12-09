package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Menu.gameMenu;

public class Main extends Application {


    @Override
    public void start(Stage theStage) throws Exception{

    	//Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    	
    	//Setting window name 
        theStage.setTitle( "Block Buster" );
    	
        //Creating canvas to pass the image and the game to
    	final Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        //Setting background image
        Rectangle background = new Rectangle(1225, 600);
        Image backgroundImage = new Image("file:src/sample/images/start.png");
        background.setFill(new ImagePattern(backgroundImage));
        root.getChildren().add( background );
        
        //Implementing Game Menu on a starting screen
    	gameMenu gameMenu = new gameMenu(root);
    	gameMenu.createMenu(root);
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