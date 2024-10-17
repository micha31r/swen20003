package platform;

import java.util.Random;
import bagel.Input;
import camera.Camera;
import core.GameObject;
import entity.Player;
import utils.PropertiesLoader;

/**
 * A platform that moves horizontally and randomly within a certain range.
 * @author Michael Ren
 */
public class FlyingPlatform extends GameObject {
  private static final double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.flyingPlatform.randomSpeed"));
  private static final String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.flyingPlatform.image");
  private static final double RANDOM_SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.flyingPlatform.randomSpeed"));
  private static final int MAX_DISPLACEMENT = Integer.parseInt(PropertiesLoader.getGameProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
  private final double INITIAL_X;
  private int direction = (new Random()).nextBoolean() ? 1 : -1;

  /**
   * Create a new FlyingPlatform given initial position
   * @param x x position
   * @param y y position
   */
  public FlyingPlatform(double x, double y) {
    // Set radius to -1 to use rect based collision
    super(x, y, -1, SPEED, IMAGE_SRC);
    INITIAL_X = x;
  }

  /**
   * Move the platform within a certain range, changing direction when it reaches the edge.
   * The intial direction is set randomly.
   */
  private void randomMove() {
    // Move the object
    setX(getX() + direction * RANDOM_SPEED);

    // Change direction when reached the maximum displacement
    if (Math.abs(getX() - INITIAL_X) > MAX_DISPLACEMENT) {
      direction = (direction == 1) ? -1 : 1;
    }
  }

  /**
   * Draw the platform, and update random movement.
   * @param input user input
   * @param camera camera object
   */
  @Override
  public void update(Input input, Camera camera) {
    super.update(input, camera);
    randomMove();
  }

  /**
   * Resolve collision with player.
   * Allow the player to land on top if the player is jumping from a lower platform,
   * and landing on the top surface.
   * @param object the game object to resolve collision with
   */
  @Override
  protected void resolveCollision(GameObject object) {
    super.resolveCollision(object);
    Player player = (Player) object;

    // Ignore collision if player is jumping upwards or has died
    if (player.getVelocityY() < 0 || player.getHealth() <= 0)
      return;

    // Only land player on platform if it is above or at the platform. 
    // This prevents the player from glitching up when colliding with the platform from the sides.
    // https://edstem.org/au/courses/15916/discussion/1968729
    // Use getPreviousPlatformY to check if the player is jumping from a higher platform
    if (player.getY() + player.getHeight() / 2 <= getY() && player.getPreviousPlatformY() >= getY()) {
      player.setY((getY() - (getHeight() / 2) - (player.getHeight() / 2)));
      player.setVelocityY(0);
      player.setIsOnPlatform(true);
      player.setPreviousPlatformY(getY());
    }
  }

  /**
   * Get the height of the platform
   * @return height in pixels
   */
  @Override
  public double getHeight() {
    // The flying platform image has some transparent pixels at the top,
    // so we need to adjust the height the when the player lands, it looks 
    // like it is in contact with the platform.
    return super.getHeight() - 3;
  }
}
