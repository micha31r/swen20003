package level;

import scene.SceneManager;

/**
 * The first level
 * @author Michael Ren
 */
public class Level1 extends Level {
  /**
   * Create a new level which loads data from level 1 file.
   * @param sceneManager the scene manager
   */
  public Level1(SceneManager sceneManager) {
    super(sceneManager, "level1File");
  }
}
