package collectable;

import entity.Player;
import utils.PropertiesLoader;

/**
 * Invincible power up item in the game.
 * When collected, the player will be invincible for a certain number of frames.
 * The player will not be able to take damage during this time.
 * @author Michael Ren
 */
public class InvinciblePowerUp extends Item {
  private final static int MAX_FRAMES = Integer.parseInt(PropertiesLoader.getGameProperty("gameObjects.invinciblePower.maxFrames"));
  private final static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.invinciblePower.radius"));
  private final static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.invinciblePower.speed"));
  private final static String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.invinciblePower.image");

  /**
   * Create a new invincible power up item
   * @param x x position
   * @param y y position
   */
  public InvinciblePowerUp(double x, double y) {
    super(x, y, RADIUS, SPEED, IMAGE_SRC);
  }

  /**
   * Collect the item and set invincibility duration for the player
   * @param player the player object
   */
  @Override
  public void collect(Player player) {
    super.collect(player);
    player.useInvinciblePower(MAX_FRAMES);
  }
}