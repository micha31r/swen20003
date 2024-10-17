package entity;

import java.util.Random;
import bagel.Input;
import camera.Camera;
import core.GameObject;
import utils.PropertiesLoader;

/**
 * Slime enemy that moves horizontally and randomly within a certain range
 * and damages player on collision.
 * @author Michael Ren
 */
public class Slime extends GameObject {
  private static double DAMAGE_SIZE = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.enemy.damageSize"));
  private static int RANDOM_SPEED = Integer.parseInt(PropertiesLoader.getGameProperty("gameObjects.enemy.randomSpeed"));
  private static int MAX_DISPLACEMENT = Integer.parseInt(PropertiesLoader.getGameProperty("gameObjects.enemy.maxRandomDisplacementX"));
  private final static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.enemy.radius"));
  private final static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.enemy.speed"));
  private final static String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.enemy.image");
  private final double INITIAL_X;
  
  private boolean canDamage = true;
  private int direction = (new Random()).nextBoolean() ? 1 : -1;

  /**
   * Create a new Slime given initial position
   * @param x x position
   * @param y y position
   */
  public Slime(double x, double y) {
    super(x, y, RADIUS, SPEED, IMAGE_SRC);
    INITIAL_X = x;
  }

  /**
   * Move the slime within a certain range, changing direction when it reaches the edge.
   * The intial direction is set randomly.
   */
  private void randomMove() {
    // Move the object
    setX(getX() + direction * RANDOM_SPEED);

    // Change direction when reached maximum displacement
    if (Math.abs(getX() - INITIAL_X) > MAX_DISPLACEMENT) {
      direction = (direction == 1) ? -1 : 1;
    }
  }

  /**
   * Draw the slime, and update random movement.
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
   * Damage the player if it does not have the invincibile power up.
   * Set state so the slime can only damage the player once.
   * @param object the game object to resolve collision with
   */
  @Override
  protected void resolveCollision(GameObject object) {
    super.resolveCollision(object);
    Player player = (Player) object;
    if (player.canTakeDamage() && canDamage) {
      player.takeDamage(DAMAGE_SIZE);
      canDamage = false;
    }
  }
}
