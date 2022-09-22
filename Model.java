 import java.util.ArrayList;
import javafx.scene.paint.*;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
// The model represents all the actual content and functionality of the app
// For Breakout, it manages all the game objects that the View needs
// (the bat, ball, bricks, and the score), provides methods to allow the Controller
// to move the bat (and a couple of other fucntions - change the speed or stop 
// the game), and runs a background process (a 'thread') that moves the ball 
// every 20 milliseconds and checks for collisions 
public class Model 
{
    // First,a collection of useful values for calculating sizes and layouts etc.

    /** Border round the edge of the panel */
    public int B              = 6;
    
    /** Height of menu bar space at the top */
     public int M              = 90;      
    
    /** The side of the ball */
     public int BALL_SIZE      = 30;
    
    /** Brick width */
     public int BRICK_WIDTH    = 45;  
    
    /** Brick height */
     public int BRICK_HEIGHT   = 30;  
    
    /** Distance to move the bat on each keypress  */
     public int BAT_MOVE       = 7;       
    
    /** Distance or "speed" at which the ball is travelling */
     public int BALL_MOVE      = 3;       
    
    /** Score for hitting blue bricks */
     public int HIT_BRICK      = 50;     
    
    /** Score for hitting pink bricks */
     public int HIT_BRICK2     = 100;     
    
    /** Lives penalty for hitting the floor */
     public int HIT_BOTTOM     = -1;  
     
    /** Score penalty for hitting the floor */
     public int HIT_BOTTOM2    = -200;    
    
    /** Horizontal gap for bricks */
     private int H_GAP          = 5;
    
    /**  Vertical gap for bricks */
     private int V_GAP          = 10;    
    
    /** Number of rows we start off with  */
     public int ROWS           = 1;     
    
    /** Will be used to count number of bricks displayed */
     public int CBRICKS        = 0;       
    
    /** Will be used to count number of bricks hit  */ 
     public int HITBRICKS      = 0;       
    
    /** Number of lives on each level  */
     public int LIVES          = 3;       
    
 
     

    // The other parts of the model-view-controller setup
    View view;
    Controller controller;

    // The game 'model' - these represent the state of the game
    // and are used by the View to display it
    /** The ball */
     public GameObj ball;
    
    /** Second ball */
     public GameObj ball2;   
    
    /** The bricks */
     public ArrayList<GameObj> bricks;   
    
    /** The bat */
     public GameObj bat;       
    
    /** The score */
     public int score = 0;               
            

    // variables that control the game 
    /** Set false to stop the game  */
     public boolean gameRunning = true;  
    
    /** Set true to make the ball go faster  */
     public boolean fast = false;       
    
    /** Toggle to start and stop the game  */
     public boolean start = false; 
    
    /** Will be used to make ball invisble  */
     public boolean ballc = true;      
    
    /** To restart game*/
     public boolean restart = false;
    
    /** Toggles between one or two balls  */
     public boolean multi = false;       
   
    // initialisation parameters for the model
    /** Width of game */
     public int width;  
    
    /** Height of game  */
     public int height;  
    
    // CONSTRUCTOR - needs to know how big the window will be
    public Model( int w, int h )
    {
        Debug.trace("Model::<constructor>");  
        width = w; 
        height = h;


    }

    // Initialise the game - reset the score and create the game objects 
    /** 
       * The following method is  bulding the bricks. I have  added an if statement for my restart 
         boolean that states to take the game back to level 1 if the case for such boolean is pressed (case R), 
         else carry on building another row of bricks with two less from the previous row and counting 
         the number of rows and bricks displayed.
         
       * I have also declared the position of the bat and ball in this method.
    */
    public void initialiseGame()
    { 
        if (restart) {
        CBRICKS = 0;
        HITBRICKS = 0;
        score = 0;
        ROWS = 1;
        LIVES = 3;
        BALL_MOVE = 3;
        BAT_MOVE = 3;
        ball   = new GameObj(width/2-(BALL_SIZE/2), height-(BRICK_HEIGHT*3/2)-BALL_SIZE,BALL_SIZE, BALL_SIZE, Color.RED, Color.WHITE);
        ball2  = new GameObj(40, height/3, BALL_SIZE, BALL_SIZE, Color.BLUE, Color.BLUE );
        bat    = new GameObj(width/2-(BRICK_WIDTH*3/2), height - BRICK_HEIGHT*3/2, BRICK_WIDTH*3,BRICK_HEIGHT/4, Color.BLACK, Color.BLACK);
        bricks = new ArrayList<>();
        
        int WALL_TOP = 140;           //      How far down the screen the wall starts
        int NUM_BRICKS = 12;          //      How many bricks fit in a row 
        int CROWS = 0;                //      Will be used to count rows
        int MX= 0;                    //      Used to move x value on rows
        do {
        for (int i=0; i < NUM_BRICKS; i++) {
            GameObj brick = new GameObj((H_GAP/2+MX)+(BRICK_WIDTH+H_GAP)*i, CROWS*(BRICK_HEIGHT + V_GAP)+WALL_TOP, BRICK_WIDTH, BRICK_HEIGHT, Color.BLUE, Color.MAGENTA);
            bricks.add(brick);      // add this brick to the list of bricks
            CBRICKS++;
        }
        CROWS++;
        MX = CROWS*(BRICK_WIDTH+H_GAP);
        NUM_BRICKS= NUM_BRICKS-2;
        } while (CROWS < ROWS);
        restart = false;
        } else {
        LIVES = 3;
        ball   = new GameObj(width/2-(BALL_SIZE/2), height-(BRICK_HEIGHT*3/2)-BALL_SIZE, BALL_SIZE, BALL_SIZE, Color.RED, Color.WHITE);
        ball2  = new GameObj(40, height/3, BALL_SIZE, BALL_SIZE, Color.BLUE, Color.BLUE);
        bat    = new GameObj(width/2-(BRICK_WIDTH*3/2), height - BRICK_HEIGHT*3/2, BRICK_WIDTH*3,BRICK_HEIGHT/4, Color.BLACK, Color.BLACK);
        bricks = new ArrayList<>();
        
        int WALL_TOP = 140;         //   How far down the screen the wall starts
        int NUM_BRICKS = 12;        //   How many bricks fit in a row 
        int CROWS = 0;              //   Will be used to count rows
        int MX= 0;                  //   Used to move x value on rows
        do {
        for (int i=0; i < NUM_BRICKS; i++) {
            GameObj brick = new GameObj((H_GAP/2+MX)+(BRICK_WIDTH+H_GAP)*i, CROWS*(BRICK_HEIGHT + V_GAP)+WALL_TOP, BRICK_WIDTH, BRICK_HEIGHT, Color.BLUE, Color.MAGENTA);
            bricks.add(brick);      // add this brick to the list of bricks
            CBRICKS++;
        }
        CROWS++;
        MX = CROWS*(BRICK_WIDTH+H_GAP);
        NUM_BRICKS= NUM_BRICKS-2;
        } while (CROWS < ROWS);
        }
    }
    

    // Animating the game
    // The game is animated by using a 'thread'. Threads allow the program to do 
    // two (or more) things at the same time. In this case the main program is
    // doing the usual thing (View waits for input, sends it to Controller,
    // Controller sends to Model, Model updates), but a second thread runs in 
    // a loop, updating the position of the ball, checking if it hits anything
    // (and changing direction if it does) and then telling the View the Model 
    // changed.
    
    // When we use more than one thread, we have to take care that they don't
    // interfere with each other (for example, one thread changing the value of 
    // a variable at the same time the other is reading it). We do this by 
    // SYNCHRONIZING methods. For any object, only one synchronized method can
    // be running at a time - if another thread tries to run the same or another
    // synchronized method on the same object, it will stop and wait for the
    // first one to finish.
    
    // Start the animation thread
    public void startGame()
    {
        
        Thread t = new Thread( this::runGame );     // create a thread runnng the runGame method
        t.setDaemon(true);                          // Tell system this thread can die when it finishes
        t.start();                                  // Start the thread running
    }   
    
    // The main animation loop
       /**
       * The following if statement states at what speed the game should refresh the screen 
         when the boolean is equal to true or when it is equal to false. 
         
       * This boolean is a toggle so it can be switched at any time throughout the game 
         and this gives two different speed options in the game. 
      */
      
      public void runGame()
      {
           try
        {
            // set gameRunning true - game will stop if it is set false (eg from main thread)
            setGameRunning(true);
            while (getGameRunning())
            {
                updateGame();                        // update the game state
                modelChanged();                      // Model changed - refresh screen
                if (fast) { Thread.sleep(10);
                view.gamespeedText.setVisible(true);
                view.speedText.setVisible(false); 
                view.fastText.setVisible(true);
                }  else{Thread.sleep(20);
                 view.gamespeedText.setVisible(false);
                 view.speedText.setVisible(true); 
                 view.fastText.setVisible(false);
             }
            }
           } catch (Exception e) 
           { 
            Debug.error("Model::runAsSeparateThread error: " + e.getMessage() );
           }
          }
  
    // updating the game - this happens about 50 times a second to give the impression of movement
    /** 
      * In this method I have added the function of two booleans
        these being start and multi. Both of them are toggles meaning
        that they can be switched on and off at any time during the game.
        They have both been declared at the top of the screen.
        
      * If start is equal to true it runs the game and if it is equal to 
        false it will stop (pause) the game. Multi works by displaying one ball
        if equal to false or two balls if equal to true. 
    
     */
    public synchronized void updateGame()
    {
           if (start) 
          {
            
             // move the ball one step (the ball knows which direction it is moving in)
             ball.moveX(BALL_MOVE);                      
            ball.moveY(BALL_MOVE);
            // get the current ball possition (top left corner)
            int x = ball.topX;  
            int y = ball.topY;
             // Deal with possible edge of board hit
            if (x >= width - B - BALL_SIZE)  ball.changeDirectionX();
            if (x <= 0 + B)  ball.changeDirectionX();
            if (y >= height - B - BALL_SIZE)  // Bottom
            { 
             ball.changeDirectionY(); 
             updatelives (HIT_BOTTOM);
             addToScore (HIT_BOTTOM2);
             
             /** audio to play sound when ball hits the floor */
              new AudioClip(Sounds.BOUNCE).play();
            }
            if (y <= 0 + M) 
            {
             ballc = true;
             ball.changeDirectionY();
            }
   
             // check whether ball has hit a (visible) brick
             
            /** the boolean will be true when the ball hits a brick */
             boolean hit = false;                    

             // *[2]******************************************************[2]*
             // * Fill in code to check if a visible brick has been hit      *
             // * The ball has no effect on an invisible brick               *
             // * If a brick has been hit, change its 'visible' setting to   *
             // * false so that it will 'disappear'                          * 
             // **************************************************************
             /**
              * The following for loop is used to check if a brick is visible.
              
              * If brick.once = true 
                The colour of this brick is blue and will change to pink
                when it is hit, changing the boolean to false and will 
                add to the score at the same time.
                This will only happen on even levels.
                
              * If ballc = true 
                The ball is visible at all times.
                
              * brick.visible = true
                This brick is still displayed and can be hit.
                
              */
             for (GameObj brick: bricks) { 
              if (brick.visible && brick.hitBy(ball)) { 
                  if ( ROWS%2 == 0 && brick.once ) {
                    hit = true;
                    
                    /** Set the brick invisible */
                     brick.once = false;    
                    
                    /** The colour of the ball will be visible */
                     ballc = true;
                
                    /**  Will add to score for hitting blue brick  */ 
                     addToScore( HIT_BRICK );      
                
                    /** Play audio clip from sounds class */ 
                     new AudioClip(Sounds.HIT_BRICK).play(); 
                  }
                  else 
                {
                   hit = true;
                
                  /** Set the brick invisible  */
                     brick.visible = false;    
                     
                  /** The colour of the ball will be visible */
                     ballc = true;
                
                  /** Will add to score for hitting a pink brick  */
                     addToScore (HIT_BRICK2 );        
                
                  /** Counts number of bricks hit */
                     HITBRICKS++;                      
                
                  /** Play audio clip from sounds class */
                    new AudioClip(Sounds.HIT_BRICK).play();     
                
                 }
              }
             }

             if (hit)
                ball.changeDirectionY();  
            
              // check whether ball has hit the bat
              /**
               * The following if statement is used to declare when I want the boolean 
                 ballc to become true or false in order to make the ball invisible.
                 The boolean will become false when it hits the bat and this information
                 is linked to the view class which then states that "colour2" from the Game Object
                 is used and declared as white in the model.
                 
               * The colour white is what makes the ball invisble as the colour of the screen is white.
              
               * I have chosen to make the ball invisible on rows 3 and 5. The ball will
                 become invisible when it hits the bat but will become visible again when
                 it hits a brick or the top of the screen. 
               */
              if ( ball.hitBy(bat) ) 
               {
                if (ROWS == 3|| ROWS == 5 ) 
                {
                 ballc = false;
                } 
                 else  
                {
                ballc = true; 
                }
                 ball.changeDirectionY();
              }
             /**
               This code is doubled from the original ball
               using integers u and v for the second ball
               for topX and topY locations.
             */
             if (multi)
             {
                  /** 
                   I have added the following code so that
                   if the balls collide, they will "bounce"
                   of eachother.
                   */
                  if (ball.hitBy(ball2)) {
                   ball.changeDirectionX();
                   ball2.changeDirectionX();
                  }
                  // move the ball one step (the ball knows which direction it is moving in)
                  ball2.moveX(BALL_MOVE);                      
                  ball2.moveY(BALL_MOVE);
                  // get the current ball possition (top left corner)
                  int u = ball2.topX;  
                  int v = ball2.topY;
                  // Deal with possible edge of board hit
                  if (u >= width - B - BALL_SIZE)  ball2.changeDirectionX();
                  if (u <= 0 + B)  ball2.changeDirectionX();
                  if (v >= height - B - BALL_SIZE)  // Bottom
                  { 
                   ball2.changeDirectionY(); 
                   updatelives (HIT_BOTTOM);  
                   addToScore (HIT_BOTTOM2);
                   new AudioClip(Sounds.BOUNCE).play();
                  }
                  if (v <= 0 + M)  
                  {
                   ball2.changeDirectionY();
                   }  

                 // check whether ball has hit a (visible) brick
                 hit = false;

                // *[2]******************************************************[2]*
                // * Fill in code to check if a visible brick has been hit      *
                // * The ball has no effect on an invisible brick               *
                // * If a brick has been hit, change its 'visible' setting to   *
                // * false so that it will 'disappear'                          * 
                // **************************************************************
                /**
                  * The following code is doubled as I am using it for the second ball.
                 */
                
                  for (GameObj brick: bricks) 
                  {
                      if (brick.visible && brick.hitBy(ball2)) 
                      {
                      if ( ROWS%2 == 0 && brick.once ) {
                      hit = true;
                      brick.once = false;          // set the brick invisible
                      addToScore( HIT_BRICK );    // add to score for hitting blue brick
                      new AudioClip(Sounds.HIT_BRICK).play();
                      }
                      else 
                      {
                      hit = true;
                      brick.visible = false;       // set the brick invisible
                      addToScore (HIT_BRICK2 );     //add to score for hitting pink brick
                      HITBRICKS++;                 // used to count number of bricks hit
                      new AudioClip(Sounds.HIT_BRICK).play();

                      }
                  }
                }
             }
            
          
        

              if (hit)
                ball2.changeDirectionY();

             /** check whether the second ball has hit the bat */
              if ( ball2.hitBy(bat) ) {
               ball2.changeDirectionY(); 
              }
             /** 
              * The following bit of code is bringing the game to a
                next level or stopping it if 6 rows have already been
                built and all the bricks have been hit.
                
             *  When building our bricks we are also counting the number 
                of bricks displayed on the screen and the number of bricks 
                hit so that if these numbers are equal it means that all the 
                bricks have been hit.
                
             * Once all the bricks have been hit the game will stop for
               four seconds and start again with an extra row of bricks 
               and the CBRICKS AND HITBRICKS back to 0.
             
             * On every even row the ball movement and bat movement
               will speed up, making the game faster.
               
             * On levels 3 and 5 the player will be informed of
               the ball becoming invisble on these levels.
          
              */
             if ( CBRICKS == HITBRICKS ) {
              if ( ROWS == 6) {
              view.endText.setVisible(true);
              view.levelcompletedText.setVisible(false);
              view.end1Text.setVisible(true);
              view.scoreText.setVisible(true);
              ballc = false;
              gameRunning = false;
               
              } 
              else { 
         
              Debug.trace("next level"); 
              ROWS++;
              view.levelcompletedText.setVisible(true);
              try { 
                Thread.sleep(4000);    /** stops the game for four seconds */ 
              }
              catch (InterruptedException ex) {
                Thread.currentThread().interrupt () ;
              }
              view.levelcompletedText.setVisible(false);
            
              CBRICKS = 0; 
              HITBRICKS = 0;
            
              if ((ROWS%2) == 0) {
                 BALL_MOVE++;
                 BAT_MOVE++;
                }
              Debug.trace ("update speed");
              if (ROWS == 3 || ROWS == 5 ) {
             
               view.info2Text.setVisible(true);
               view.info3Text.setVisible(true);
               start = false ;
               initialiseGame();
              }else  {
               initialiseGame();
               }
              }
             }
          }
    }
       


    // This is how the Model talks to the View
    // Whenever the Model changes, this method calls the update method in
    // the View. It needs to run in the JavaFX event thread, and Platform.runLater 
    // is a utility that makes sure this happens even if called from the
    // runGame thread
     public synchronized void modelChanged()
    {
        Platform.runLater(view::update);
    }
    
    
    // Methods for accessing and updating values
    // these are all synchronized so that the can be called by the main thread 
    // or the animation thread safely
    
    // Change game running state - set to false to stop the game
    public synchronized void setGameRunning(Boolean value)
    {  
        gameRunning = value;
    }
    
    // Return game running state
    public synchronized Boolean getGameRunning()
    {  
        return gameRunning;
    }

    // Change game speed - false is normal speed, true is fast
    public synchronized void setFast(Boolean value)
    {  
        fast = value;
    }
    
    // Return game speed - false is normal speed, true is fast
    public synchronized Boolean getFast()
    {  
        return(fast);
    }

    // Return bat object
    public synchronized GameObj getBat()
    {
        return(bat);
    }
    
    // return ball object
    public synchronized GameObj getBall()
    {
        return(ball);
    }
    
    /** 
      This method is acting exactly the same as getBall
      but I am using it for the second ball.
     */
    //return second ball object
    public synchronized GameObj getBall2()
    {
        return(ball2);
    }
    // return bricks
    public synchronized ArrayList<GameObj> getBricks()
    {
        return(bricks);
    }
    
    // return score
    public synchronized int getScore()
    {
        return(score);
    }
    
     // update the score
    public synchronized void addToScore(int n)    
    {
        score += n;        
    }
    
    /**
     The following method has been added to restart the game if 0 lives are reached.
     If this happens, the game will return game over with the final score achieved and then
     wait 4 seconds before initialising the game at level 1.
     */
    // update number of lives 
    private void updatelives (int n)
    {
        LIVES += n;
          if (LIVES == 0){
           Debug.trace("out of lives");
           view.gameoverText.setVisible(true);
           view.scoreText.setVisible(true);
           
           try {
               Thread.sleep(4000);                
           } catch(InterruptedException ex) {
               Thread.currentThread().interrupt();
           }
           view.gameoverText.setVisible(false);
           view.scoreText.setVisible(false);
           CBRICKS = 0;
           HITBRICKS = 0;
           LIVES = 3;
           ROWS = 1;
           score = 0;
           BALL_MOVE = 3;
           BAT_MOVE = 7;
           initialiseGame();
          }
    }
    
    // move the bat one step - -1 is left, +1 is right
    /**
     In this method is where I limit the bat movement by giving it margins
     to which it can move freely between.
     */
     public synchronized void moveBat( int direction )
        {        
        int b = bat.topX;
        if (b < 575-(BRICK_WIDTH*3)&& direction > 0|| b > 25 && direction < 0) {
        int dist = direction * BAT_MOVE;    // Actual distance to move
        Debug.trace( "Model::moveBat: Move bat = " + dist );
        bat.moveX(dist);
        
       
       }
    }   
    
    /** 
     This method is acting the same as the one above but it is increasing
     or decreasing the X angle at which the ball is travelling with the 
     up and down keys which have been declared in the controller.
     */
    public synchronized void moveBall( int direction )
    {        
        int m = ball.topX;
        if (m < 400|| m > 150 ) {
            int dist = direction * BALL_MOVE;    // Actual distance to move
            Debug.trace( "Model::moveBall: Move ball = " + dist );
            ball.moveX(dist);
        
        }
    }   
}

    