
import javafx.scene.input.KeyEvent;

// The breakout controller receives KeyPress events from the GUI (via
// the KeyEventHandler). It maps the keys onto methods in the model and
// calls them appropriately
public class Controller
{
  public Model model;
  public View  view;
  
  // we don't really need a constructor method, but include one to print a 
  // debugging message if required
  public Controller()
  {
    Debug.trace("Controller::<constructor>");
  }
  
  // This is how the View talks to the Controller
  // AND how the Controller talks to the Model
  // This method is called by the View to respond to key presses in the GUI
  // The controller's job is to decide what to do. In this case it converts
  // the keypresses into commands which are run in the model
  
  /**
   
   * This is where all cases to play the game have been declared.
 
   * Cases F, S and M are toggles.
   
   */
  public void userKeyInteraction(KeyEvent event )
  {
    Debug.trace("Controller::userKeyInteraction: keyCode = " + event.getCode() );
      
     switch ( event.getCode() )             
     { 
      case LEFT:                     // Left Arrow
        model.moveBat( -2);          // move bat left
        break;
      case RIGHT:                    // Right arrow
        model.moveBat( +2 );         // Move bat right
        break;
      case UP:                        // Up Arrow
        model.moveBall( +1);           // increasing speed in X direction
        break;
      case DOWN:                       // Right arrow
        model.moveBall( -1 );         // decreasing speed in X direction
        break;
      case F :                       // toggle between normal and fast
        model.fast=!model.fast;
        break;
      case R :   
      model.restart = true;           //restarts the game
       model.initialiseGame();
        break;
      case S :                          // stop and start the game
        model.start = !model.start;
        view.helpText.setVisible(false);    // delete text when key is pressed
        view.titleText.setVisible(false);   // delete text when key is pressed 
        view.info2Text.setVisible(false);
        view.info3Text.setVisible(false);
        break;
      case M :
        model.multi = !model.multi;          // Toggle single or double balls 
        break;
      
     }
  }
}
