import javafx.scene.paint.*;

// An object in the game, represented as a rectangle, with a position,
// a colour, and a direction of movement.
public class GameObj
{
    // state variables for a game object
    
    /** Can see (change to false when the brick gets hit) */
      public boolean visible  = true; 
      
    /** will be used to hit a brick twice  */
      public boolean once = true;      

    /** Position - top left corner X */
     public int topX   = 0;    
     
     /** position - top left corner Y */
     public int topY   = 0;
     
    /** Width of object */
     public int width  = 0;   
     
    /** Height of object */
     public int height = 0;
     
    /** Colour of object */
     public Color colour;            
    
    /**  Second Colour of object */
     public Color colour2;               
    
    /** Direction X (1 or -1) */
     public int   dirX   = 1;   
    
    /** Direction Y (1 or -1) */
     public int   dirY   = 1;    
     
   
    /** 
     * I have added an extra game variable which will be used to give the 
       bricks an extra colour and to make the ball invisble.
     * In the model all Game Objects will now have an extra colour
       as it will be looking for an extra game variable.
     */
    public GameObj( int x, int y, int w, int h, Color c, Color c2)
    {
        topX   = x;       
        topY = y;
        width  = w; 
        height = h; 
        colour = c;
        colour2 = c2;
    }

    // move in x axis
    public void moveX( int units )
    {
        topX += units * dirX;
    }

    // move in y axis
    public void moveY( int units )
    {
        topY += units * dirY;
    }

    // change direction of movement in x axis (-1, 0 or +1)
    public void changeDirectionX()
    {
        dirX = -dirX;
    }

    // change direction of movement in y axis (-1, 0 or +1)
    public void changeDirectionY()
    {
        dirY = -dirY;
    }

    // Detect collision between this object and the argument object
    // It's easiest to work out if they do NOT overlap, and then
    // return the negative (with the ! at the beginning)
    public boolean hitBy( GameObj obj )
    {
        return ! ( topX >= obj.topX+obj.width     ||
            topX+width <= obj.topX         ||
            topY >= obj.topY+obj.height    ||
            topY+height <= obj.topY );

    }

}
