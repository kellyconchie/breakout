/**  
  * The following code was copied from the internet.
  
  * The link for this is {@link https://docs.oracle.com/javafx/2/api/javafx/scene/media/AudioClip.html} and
    the sounds come from {@link http://soundbible.com/royalty-free-sounds-1.html} .
    
  * This class is a child class to the model.
  
*/
public class Sounds

{

    private Sounds()  {}

    public static final String HIT_BRICK = Sounds.class.getResource("sounds/plate2.wav").toExternalForm();

    public static final String BOUNCE = Sounds.class.getResource("sounds/Bounce.wav").toExternalForm();



}
