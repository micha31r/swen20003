package collectable;

import entity.Player;
import utils.PropertiesLoader;

/**
 * Double score power up item in the game.
 * When collected, the player's score will be doubled for a certain number of frames.
 * @author Michael Ren
 */
public class DoubleScorePowerUp extends Item {
  private final static int MAX_FRAMES = Integer.parseInt(PropertiesLoader.getGameProperty("gameObjects.doubleScore.maxFrames"));
  private final static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.doubleScore.radius"));
  private final static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.doubleScore.speed"));
  private final static String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.doubleScore.image");

  /**
   * Create a new double score power up item
   * @param x x position
   * @param y y position
   */
  public DoubleScorePowerUp(double x, double y) {
    super(x, y, RADIUS, SPEED, IMAGE_SRC);
  }

  /**
   * Collect the item, and set double score duration for the player
   * @param player the player object
   */
  @Override
  public void collect(Player player) {
    super.collect(player);
    player.useDoubleScore(MAX_FRAMES);
  }
}