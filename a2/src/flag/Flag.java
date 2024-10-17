package flag;

import core.GameObject;
import entity.Player;
import utils.PropertiesLoader;

/**
 * Flag class represents the flag object in the game.
 * The flag is the goal for the player to reach to win the game.
 * @author Michael Ren
 */
public class Flag extends GameObject {
  private final static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.endFlag.radius"));
  private final static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.endFlag.speed"));
  private final static String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.endFlag.image");

  /**
   * Create a new Flag object
   * @param x x position
   * @param y y position
   */
  public Flag(double x, double y) {
    super(x, y, RADIUS, SPEED, IMAGE_SRC);
  }

  /**
   * Handle collision with the Player object.
   * @param object the game object to resolve collision with
   */
  @Override
  protected void resolveCollision(GameObject object) {
    super.resolveCollision(object);
    ((Player) object).setReachedFlag(true);
  }
}

