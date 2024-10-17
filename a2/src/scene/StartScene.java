package scene;

import bagel.Input;
import bagel.Keys;
import bagel.Window;
import text.Text;
import utils.PropertiesLoader;

/**
 * The starting scene for the game.
 * Allows the player to choose a level to play and display game instructions.
 * @author Michael Ren
 */
public class StartScene extends Scene {
  private final Text titleText = new Text(
    "title",
    "title.fontSize",
    "title.x",
    "title.y"
  );

  private final Text instructionText = new Text(
    "instruction",
    "instruction.fontSize"
  );

  /**
   * Create a new StartScene
   * @param sceneManager scene manager
   */
  public StartScene(SceneManager sceneManager) {
    super(sceneManager);
    // Center align instruction text
    double x = (double) ((Window.getWidth() - instructionText.getFont().getWidth(instructionText.getMessage())) / 2);
    double y = Double.parseDouble(PropertiesLoader.getGameProperty("instruction.y"));
    instructionText.setPosition(x, y);
  }

  /**
   * Display title and instructions texts
   * Switch to level scenes when the corresponding number keys (1-3) are pressed
   * @param input user input
   */
  @Override
  public void update(Input input) {
    titleText.update();
    instructionText.update();

    if (input.wasReleased(Keys.NUM_1)) {
      sceneManager.setScene(SceneManager.SceneId.LEVEL1);
    } else if (input.wasPressed(Keys.NUM_2)) {
      sceneManager.setScene(SceneManager.SceneId.LEVEL2);
    } else if (input.wasPressed(Keys.NUM_3)) {
      sceneManager.setScene(SceneManager.SceneId.LEVEL3);
    }
  }
}
