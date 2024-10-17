package scene;

import bagel.Input;
import camera.Camera;

/**
 * Abstract class to represent a scene in the game.
 * Each scene has its own camera which allows a portion of the scene to be rendered.
 * The update function is called every frame to update the scene, and provides user input.
 * @author Michael Ren
 */
public abstract class Scene {
  protected final SceneManager sceneManager;
  private final Camera camera = new Camera(0, 0);

  /**
   * Create a new Scene
   * @param sceneManager scene manager
   */
  public Scene(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  /**
   * Update the scene
   * @param input user input
   */
  public void update(Input input) {}

  /**
   * Get the camera object
   * @return the camera object
   */
  public Camera getCamera() {
    return camera;
  }
}