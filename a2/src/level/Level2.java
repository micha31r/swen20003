package level;

import scene.SceneManager;

/**
 * The second level
 * @author Michael Ren
 */
public class Level2 extends Level {
  /**
   * Create a new level which loads data from level 2 file.
   * @param sceneManager the scene manager
   */
  public Level2(SceneManager sceneManager) {
    super(sceneManager, "level2File");
  }
}
