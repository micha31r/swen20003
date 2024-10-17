package platform;

import bagel.Input;
import camera.Camera;
import core.GameObject;
import entity.Player;
import utils.PropertiesLoader;

/**
 * The base platform which does not move.
 * @author Michael Ren
 */
public class Platform extends GameObject {
  private static final double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.platform.speed"));
  private static final String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.platform.image");

  /**
   * Create a new Platform given position
   * @param x x position
   * @param y y position
   */
  public Platform(double x, double y) {
    // Set radius to -1 to use rect based collision
    super(x, y, -1, SPEED, IMAGE_SRC);
  }

  /**
   * Resolve collision with player.
   * Land the player on top of the platform on collision.
   * Ignore collision if player is jumping upwards or has died.
   * @param object the game object to resolve collision with
   */
  @Override
  protected void resolveCollision(GameObject object) {
    super.resolveCollision(object);
    Player player = (Player) object;

    // Ignore collision if player is jumping upwards or has died
    if (player.getVelocityY() < 0 || player.getHealth() <= 0)
      return;

    // Land player on platform
    player.setY((getY() - (getHeight() / 2) - (player.getHeight() / 2)));
    player.setVelocityY(0);
    player.setIsOnPlatform(true);
    player.setPreviousPlatformY(getY());
  }

  /**
   * Draw the platform.
   */
  @Override
  public void update(Input input, Camera camera) {
    super.update(input, camera);
  }
}
