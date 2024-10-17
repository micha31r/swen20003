package entity;

import bagel.Input;
import camera.Camera;
import core.GameObject;
import utils.PropertiesLoader;

/**
 * Fireball for the player and enemy boss to shoot.
 * @author Michael Ren
 */
public class Fireball extends GameObject {
  private final static double DAMAGE_SIZE = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.fireball.damageSize"));
  private final static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.fireball.radius"));
  private final static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.fireball.speed"));
  private final static String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.fireball.image");
  private final int directionScalar;
  private final Entity owner;

  /**
   * Create a new fireball
   * @param x x position
   * @param y y position
   * @param directionScalar direction given as -1 for left and 1 for right
   * @param owner the entity that shot the fireball
   */
  public Fireball(double x, double y, int directionScalar, Entity owner) {
    super(x, y, RADIUS, SPEED, IMAGE_SRC);
    this.directionScalar = directionScalar;
    this.owner = owner;
  }

  /**
   * Update the fireball's position and check for collision.
   * Flag fireball for deletion if it goes off screen.
   * @param input input from the user
   * @param camera camera object
   */
  @Override
  public void update(Input input, Camera camera) {
    super.update(input, camera);
    setX(getX() + getSpeed() * directionScalar);

    // Remove the fireball if it goes off screen (in the direction it's travelling)
    // This allows fireball to be created outside the screen and removed only when it reaches the other side
    if (directionScalar == -1 && getX() < camera.getX() || directionScalar == 1 && getX() > camera.getX() + camera.getWidth()) {
      setCanRemove(true);
    }
  }

  /**
   * Handle collision with an Entity object that is not the owner of the fireball.
   * Damage the entity on collision, and flag the fireball for deletion.
   * @param object the game object to resolve collision with
   */
  @Override
  protected void resolveCollision(GameObject object) {
    super.resolveCollision(object);
    // Using instanceof here to check if the object is an entity.
    // The object in this case should never be a non-Entity object, 
    // but check to be safe and maintain readability.
    if (object instanceof Entity && object != owner) {
      ((Entity) object).takeDamage(DAMAGE_SIZE);
      setCanRemove(true);
    }
  }
}

