
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

// The View class creates and manages the GUI for the application.
// It doesn't know anything about the game itself, it just displays
// the current state of the Model, and handles user input
public class View implements EventHandler<KeyEvent>
{ 
    // variables for components of the user interface
    public int width;       // width of window
    public int height;      // height of window

    // usr interface objects
    public Pane pane;                   //           basic layout pane
    public Canvas canvas;               //           canvas to draw game on
    public Label infoText;              //           info at top of screen
    public Label titleText;             //           title for rules of the game
    public Label helpText;              //           rules info displayed on the screen
    public Label levelText;             //           level at top of screen
    public Label livesText;             //           lives at the top of the screen 
    public Label gameoverText;          //           displays game over 
    public Label levelcompletedText;    //           displays level completed
    public Label speedText;             //           displays the ball speed
    public Label gamespeedText;         //           displays game fast
    public Label fastText;              //           displays FAST!
    public Label info2Text;             //           displays text informing player of invisible ball
    public Label info3Text;             //           displays a label to initiate the game
    public Label endText;               //           displays text after all levels have been completed
    public Label end1Text;              //           displays text after all levels have been completed
    public Label scoreText;             //           displays the final score achieved
  
    
    public int col2;  // second bricks colour 
   

    // The other parts of the model-view-controller setup
    public Controller controller;
    public Model model;

    public GameObj   bat;                                  // The bat
    public GameObj   ball;                                 // The ball
    public GameObj   ball2;                                // Second Ball
    public ArrayList<GameObj> bricks;                      // The bricks
    public ArrayList<GameObj> bricks2;                     // The bricks
    public int       score =  0;                           // The score
   
    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public View(int w, int h)
    {
        Debug.trace("View::<constructor>");
        width = w;
        height = h;
    }

    // start is called from Main, to start the GUI up
    // Note that it is important not to create controls etc here and
    // not in the constructor (or as initialisations to instance variables),
    // because we need things to be initialised in the right order
    /** 
     This where I declare all the text I want to be displayed on the screen
     and the position of the text.
     */
    public void start(Stage window) 
    {
         // breakout is basically one big drawing canvas, and all the objects are
         // drawn on it as rectangles, except for the text at the top - this
         // is a label which sits 'on top of' the canvas.
        
        pane = new Pane();       // a simple layout pane
        pane.setId("infoText");  // Id to use in CSS file to style the pane if needed
        
        // canvas object - we set the width and height here (from the constructor), 
        // and the pane and window set themselves up to be big enough
        canvas = new Canvas(width,height);  
        pane.getChildren().add(canvas);     // add the canvas to the pane
        
        // infoText box for the score - a label which we position on 
        //the canvas with translations in X and Y coordinates
        infoText = new Label("Breakout: Score = " + score);
        infoText.setId("breakoutText");
        infoText.setTranslateX(95);
        infoText.setTranslateY(10);
        pane.getChildren().add(infoText);  // add label to the pane
        
        
        titleText = new Label ("RULES OF THE GAME");                            // title for rules
        titleText.setId("titleText");
        titleText.setTranslateX(40);
        titleText.setTranslateY(350);
        titleText.setVisible(true);
        pane.getChildren().add(titleText);
        
        helpText = new Label(                                                   // label for rules
        "1. Left arrow moves bat to the left.\n" +
        "2. Right arrow moves bat to the right.\n" +
        "3. Up arrow decreases angle of ball.\n" +
        "4. Down arrow increases angle of ball.\n" +
        "5. F toggles between two different speeds.\n" +
        "6. M adds a ball to the game.\n" +
        "7. R restarts the game.\n" +
        "8. S starts and stops the game.\n" +
        "         PRESS S TO START");
        helpText.setId("helpText");
        helpText.setTranslateX(140);
        helpText.setTranslateY(430);
        helpText.setVisible(true);
        pane.getChildren().add(helpText);  
        
        levelText = new Label("Level: " + model.ROWS);                           // label for level
        levelText.setId("header");
        levelText.setTranslateX(50);
        levelText.setTranslateY(60);
        pane.getChildren().add(levelText);  
        
        livesText = new Label("Lives: " + model.LIVES);                          // label for lives
        livesText.setId("header");
        livesText.setTranslateX(210);
        livesText.setTranslateY(60);
        pane.getChildren().add(livesText);  
        
        
        speedText = new Label("Ball Speed: " + model.BALL_MOVE);                 // label for ball speed
        speedText.setId("header");
        speedText.setTranslateX(360);
        speedText.setTranslateY(60);
        pane.getChildren().add(speedText);  
        
        
        gameoverText = new Label ("GAME OVER"); // game over                      // game over text
        gameoverText.setId("gameoverText");
        gameoverText.setTranslateX(80);
        gameoverText.setTranslateY(400);
        gameoverText.setVisible(false);
        pane.getChildren().add(gameoverText);
        
        
        levelcompletedText = new Label("Level " + model.ROWS +  " Completed");     // level complete
        levelcompletedText.setId("levelcompletedText");
        levelcompletedText.setTranslateX(50);
        levelcompletedText.setTranslateY(400);
        levelcompletedText.setVisible(false);
        pane.getChildren().add(levelcompletedText);  
        
        
        gamespeedText = new Label ("Game: ");                                     // game text
        gamespeedText.setId("header");
        gamespeedText.setTranslateX(350);
        gamespeedText.setTranslateY(60);
        gamespeedText.setVisible(false);
        pane.getChildren().add(gamespeedText);
        
        
        fastText = new Label (" FAST! ");                                         // fast text 
        fastText.setId("fast");
        fastText.setTranslateX(440);
        fastText.setTranslateY(60);
        fastText.setVisible(false);
        pane.getChildren().add(fastText);
        
        
        info2Text = new Label ("On this level the ball will become\n" +  
        "invisible when it hits the bat and\n" +
        "will become visible again when\n" +
        "             it hits a brick" );                                        // text after level 
        info2Text.setId("info2");
        info2Text.setTranslateX(70);
        info2Text.setTranslateY(400);
        info2Text.setVisible(false);
        pane.getChildren().add(info2Text);
        
        info3Text = new Label (
        "PRESS S TO START GAME");                                                // text after level 
        info3Text.setId("info3");
        info3Text.setTranslateX(60);
        info3Text.setTranslateY(590);
        info3Text.setVisible(false);
        pane.getChildren().add(info3Text);
        
        
        endText = new Label ("You have now completed all 6 levels\n");            // end of game 
        endText.setId("end");
        endText.setTranslateX(40);
        endText.setTranslateY(300);
        endText.setVisible(false);
        pane.getChildren().add(endText);
        
        end1Text = new Label ( "CONGRATULATIONS!");                              // congratulations text 
        end1Text.setId("end1"); 
        end1Text.setTranslateX(30);
        end1Text.setTranslateY(200);
        end1Text.setVisible(false);
        pane.getChildren().add(end1Text);
        
        
        scoreText = new Label ("Your Final Score is: " + score);                // final score text 
        scoreText.setId("score");
        scoreText.setTranslateX(85);
        scoreText.setTranslateY(490);
        scoreText.setVisible(false);
        pane.getChildren().add(scoreText);
        
        
        
        
        
        
        // add the complete GUI to the scene
        Scene scene = new Scene(pane);   
        scene.getStylesheets().add("breakout.css"); // tell the app to use our css file

        // Add an event handler for key presses. We use the View object itself
        // and provide a handle method to be called when a key is pressed.
        scene.setOnKeyPressed(this);

        // put the scene in the winodw and display it
        window.setScene(scene);
        window.show();
   }

    // Event handler for key presses - it just passes th event to the controller
    public void handle(KeyEvent event)
    {
        // send the event to the controller
        controller.userKeyInteraction( event );
    }
    
    // drawing the game
    public void drawPicture()
    {
        // the ball movement is runnng 'i the background' so we have
        // add the following line to make sure
        /**
         * Here is where everything updates.
         
         * The view class is getting information from the model class and here
           is where it updates and displays the update information on the screen.
         */
        synchronized( Model.class )   // Make thread safe (because the bal
        {
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // clear the canvas to redraw
            gc.setFill( Color.WHITE );
            gc.fillRect( 0, 0, width, height );
            
            // update score
            infoText.setText("BreakOut: Score = " + score);
            scoreText.setText(
            "Your Final Score is: " + score);
            
            //update lives 
            livesText.setText("Lives: " + model.LIVES);
            
            //update level
            levelText.setText("Level: " + model.ROWS);
            levelcompletedText.setText( "Level " + model.ROWS +  " Completed");
            
            //update speed
            speedText.setText("Ball Speed: " + model.BALL_MOVE);
            
            //update game over
            scoreText.setText("Your Final Score is: " + score);
            

            // draw the bat and balls
            /**
             The following if statement declares when one or two balls should be drawn.
             In this case it relies on the boolean multi.
             If multi is equal to true then two balls should be drawn or 
             if multi is equal to false then only one should be drawn.
             */
            if (model.multi)  // if 2 balls selected
            {
            displayGameObj( gc, ball2 );   // Display the  second Ball
            displayGameObj( gc, ball ); 
            }else
            {displayGameObj( gc, ball );   // didplays just single ball
            }
            displayGameObj( gc, bat  );   // Display the Bat
            
            
            /**
            * This if statement is being used to see if the booleans 
              once and visible that have been declared in the Game Object
              are false (meaning the brick has been hit and is invisible).
            * If the brick hasn't been hit, then the integer is given the number 2
              and if it has been hit it is given the number 1.
            * These integers will be used further down the page to determine the colour
              of the bricks.
             */
    
            for (GameObj brick: bricks) {
                if (brick.visible) {
                    if (brick.once) {
                        col2 = 2;
                    }else {
                        col2 =1;
                    }
                    displayGameObj(gc, brick);
                }
            } 
        }
    }
    
    
    /** 
     * Here is where the ball is made round, by adding an if statement
       I have stated that if the ball size is equal to the width of 
       the game object ( which will only be the ball ) to draw it with 
       fillOval, and this gives a round ball.
       If this is not the case then to draw all other Game Objects with fillRect
       and this gives rectangles.

     * The colour of the bricks  are determined here, as seen above
       the integer col2 was given two different numbers, with a if statement
       I have stated that if col2 is equal to 2 ( meaning it hasn't been hit)
       and it is a brick than to use "colour" from the Game Object class
       which is stated as blue in the model. If that isn't the case to set 
       the brick as "colour2" from the GameObject class which is declared
       as Magenta in the model.
      
     * The colour of the ball is also determined here. The code is the same
       as the bricks but the second colour for the ball is declared as white
       which is used to make it invisble on level 3 and 5.
       
     */

    // Display a game object - it is just a rectangle on the canvas
    public void displayGameObj( GraphicsContext gc, GameObj go ) {
        if ( model.BALL_SIZE == go.width) {
            if ( model.ballc) {    
          gc.setFill( go.colour );
          gc.fillOval( go.topX, go.topY, go.width, go.height );
        } else {
          gc.setFill( go.colour2 );
          gc.fillOval( go.topX, go.topY, go.width, go.height );
        }
        }  
         else {
         if (col2 ==2 && model.BRICK_WIDTH == go.width )
         {
             gc.setFill(go.colour);
            } else  {
             gc.setFill( go.colour2 );
            }
          gc.fillRect( go.topX, go.topY, go.width, go.height );
        }
    }

    // This is how the Model talks to the View
    // This method gets called BY THE MODEL, whenever the model changes
    // It has to do whatever is required to update the GUI to show the new model status
    /** 
      The following code is linked to the model and if the boolean
      multi is true it displays two balls and if it is false
      then it only displays one ball.
     */
    public void update()
    {
        // Get from the model the ball, bat, bricks & score
        
         if (model.multi)
         {   // Diplays both balls
          ball2   = model.getBall2();  
          ball    = model.getBall();
        }
        else 
        {  // Displays single ball
            ball    = model.getBall();
         } 
        
        bricks  = model.getBricks();            // Bricks
        bat     = model.getBat();               // Bat
        score   = model.getScore();             // Score
        //Debug.trace("Update");
        drawPicture();                     // Re draw game
    }
}
