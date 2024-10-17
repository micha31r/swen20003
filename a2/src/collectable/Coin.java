package collectable;

import bagel.Input;
import camera.Camera;
import entity.Player;
import utils.PropertiesLoader;

/**
 * Coin item in the game.
 * Player's score will be increased when collected.
 * @author Michael Ren
 */
public class Coin extends Item {
  private final static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.coin.radius"));
  private final static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.coin.speed"));
  private final static String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.coin.image");

  /**
   * Create a new coin
   * @param x x position
   * @param y y position
   */
  public Coin(double x, double y) {
    super(x, y, RADIUS, SPEED, IMAGE_SRC);
  }

  /**
   * Update the coin
   * @param input user input
   * @param camera camera object
   */
  @Override
  public void update(Input input, Camera camera) {
    super.update(input, camera);
  }

  /**
   * Collect the coin and increase player's score
   * @param player the player object
   */
  @Override
  public void collect(Player player) {
    super.collect(player);
    player.increaseScore();
  }
}
