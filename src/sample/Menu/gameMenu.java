package sample.Menu;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class gameMenu {

    private static final Font FONT = Font.font("", FontWeight.BOLD, 27);

    public VBox menuBox;
    private int currentItem;
	private Group root;
	
	public EventHandler<KeyEvent> changeItemHandler;

	/**
	 * Constructor
	 */
	 
    public gameMenu(Group root, Color back, Color obst, Color cook)
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
    
    /**
     * Create Menu
     */
    
    public void createMenu(Group root) {
        MenuItem startGame = new MenuItem("PLAY");
        startGame.setOnActivate(() -> this.runGame());
        
        MenuItem options = new MenuItem("OPTIONS");
        options.setOnActivate(() -> this.openOptions());
        
        MenuItem leaderboard = new MenuItem("LEADERBOARD");
        leaderboard.setOnActivate(() -> this.openLeaderboard());
        
        MenuItem exitGame = new MenuItem("EXIT");
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
        //return menuBox;
    }
  
    
    /**
     * Change Menu Item
     */
    
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
    
   /**
    * Get Menu Item (For highlighting)
    */
   
    private MenuItem getMenuItem(int index) {
        return (MenuItem)this.menuBox.getChildren().get(index);
    }
    
    /**
     * Menu Item class
     */

    static class MenuItem extends HBox {
    	 private menuChooser c1 = new menuChooser(), c2 = new menuChooser();
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.BASELINE_LEFT);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2)); //!!! 

            getChildren().addAll(c1, text, c2);
            setActive(false);
        }

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.GREEN : Color.GREY);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }
    
    /**
     * Menu Chooser 
     */

    public static class menuChooser extends Circle {
        public menuChooser() {

        	this.setRadius(10);
            Image img = new Image("file:src/sample/images/ball.png");
            this.setFill(new ImagePattern(img));
            
            setEffect(new GaussianBlur(2));
        }
    }
    
    /**
     * Run Game From this menu
     */
    
    private void runGame()
    {
    	this.root.getChildren().clear();

    	Rectangle bg = new Rectangle(1225, 600);
    	Image background = new Image("file:src/sample/images/start.png");
    	bg.setFill(new ImagePattern(background));
    	this.root.getChildren().add(bg);
    	
    	//Set up difficulies, levels and etc.
    }
    
    /**
     * Open Options Menu
     */
    
    private void openOptions()
    {
    	this.root.getChildren().clear();

    	Rectangle bg = new Rectangle(1225, 600);
    	Image background = new Image("file:src/sample/images/start.png");
    	bg.setFill(new ImagePattern(background));
    	this.root.getChildren().add(bg);

    	//Set up options menu
    }
    
    /**
     * Open Leaderboard
     */
    
    private void openLeaderboard() 
    {
    	this.root.getChildren().clear();

    	Rectangle bg = new Rectangle(1225, 600);
    	Image background = new Image("file:src/sample/images/start.png");
    	bg.setFill(new ImagePattern(background));
    	this.root.getChildren().add(bg);

    	//Set up Leaderboard
    }
}
