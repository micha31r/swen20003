package scene;

import bagel.Input;
import level.Level1;
import level.Level2;
import level.Level3;

/**
 * Manages game scenes.
 * This was not part of my UML diagram because the scene switching was initially done in ShadowMario.java
 * However, because ShadowMario.java cannot be imported because it is not in a package,
 * this class was created to delegate the scene switching logic.
 * @author Michael Ren
 */
public class SceneManager {
  public enum SceneId {
		START,
		WIN,
		LOSE,
		LEVEL1,
		LEVEL2,
		LEVEL3,
	}
  private Scene scene;

	/**
	 * Create a new scene manager with the start scene as the default scene
	 */
  public SceneManager() {
    setScene(SceneId.START);
  }

	/**
	 * Set the current scene
	 * @param id The scene id as a SceneId enum
	 */
  public void setScene(SceneId id) {
		switch (id) {
			case START:
				scene = new StartScene(this);
				break;
			case WIN:
				scene = new EndScene(this, "gameWon");
				break;
			case LOSE:
				scene = new EndScene(this, "gameOver");
				break;
			case LEVEL1:
				scene = new Level1(this);
				break;
			case LEVEL2:
				scene = new Level2(this);
				break;
			case LEVEL3:
				scene = new Level3(this);
				break;
		}
	}

	/**
	 * Update the current scene
	 * @param input user input
	 */
  public void update(Input input) {
    scene.update(input);
  }
}
