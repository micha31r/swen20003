package scene;

import bagel.Input;
import bagel.Keys;
import bagel.Window;
import text.Text;
import utils.PropertiesLoader;

/**
 * A scene to display game won or game over message after the player wins or loses a level
 * @author Michael Ren
 */
public class EndScene extends Scene {
  private final Text text;

  /**
   * Create a new EndScene
   * @param sceneManager scene manager
   * @param messageName message property name as defined in message_en.properties
   */
  public EndScene(SceneManager sceneManager, String messageName) {
    super(sceneManager);
    text = new Text(
      messageName,
      "message.fontSize"
    );

    // Center align text
    double x = (double) ((Window.getWidth() - text.getFont().getWidth(text.getMessage())) / 2);
    double y = Double.parseDouble(PropertiesLoader.getGameProperty("message.y"));
    text.setPosition(x, y);
  }

  /**
   * Display win/lose message.
   * Switch to start scene when space is pressed.
   * @param input user input
   */
  @Override
  public void update(Input input) {
    text.update();

    if (input.wasReleased(Keys.SPACE)) {
      sceneManager.setScene(SceneManager.SceneId.START);
    }
  }
}
