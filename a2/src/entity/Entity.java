package entity;

import java.util.ArrayList;
import bagel.Image;
import bagel.Input;
import camera.Camera;
import core.GameObject;
import level.Level;
import text.Text;


/**
 * An entity is a character in the game that can move and interact with the environment.
 * Entities have directions, health, death animation, and can shoot fireballs.
 * @author Michael Ren
 */
public abstract class Entity extends GameObject {
  private static enum Direction {
    LEFT,
    RIGHT
  }
  
  private static final int DEATH_ANIMATION_SPEED = 2;
  private final Image RIGHT_IMAGE;
  private final Image LEFT_IMAGE;
  private final ArrayList<Text> messages = new ArrayList<>();
  private final Level level;
  private double health;
  private Direction direction = Direction.RIGHT;
  private boolean isDeathAnimationComplete = false;

  /**
   * Create a new entity
   * @param level the level object
   * @param x the x coordinate
   * @param y the y coordinate
   * @param radius the radius of the entity
   * @param speed the speed of the entity
   * @param leftImageSrc the image source for the left image
   * @param rightImageSrc the image source for the right image
   */
  public Entity (Level level, double x, double y, double radius, double speed, String leftImageSrc, String rightImageSrc) {
    super(x, y, radius, speed);
    this.level = level;

    // Set images
    LEFT_IMAGE = leftImageSrc == null 
      ? null
      : new Image(leftImageSrc);

    RIGHT_IMAGE = rightImageSrc == null 
      ? null
      : new Image(rightImageSrc);
  }

  /**
   * Draw all messages on the screen
   */
  private void showMessages() {
    for (Text message : messages) {
      message.update();
    }
  }

  /**
   * Draw entity (left or right image based on direction)
   * @param camera the camera object
   */
  private void draw(Camera camera) {
    double screenX = getX() - camera.getX();
    double screenY = getY() - camera.getY();

    // Show left or right image based on direction
    if (isDirectionLeft()) {
      if (LEFT_IMAGE != null) {
        LEFT_IMAGE.draw(screenX, screenY);
      }
    } else {
      if (RIGHT_IMAGE != null) {
        RIGHT_IMAGE.draw(screenX, screenY);
      }
    }
  }

  /**
   * Shoot fireball in the current facing direction
   */
  protected void shoot() {
    Fireball fireball = new Fireball(getX(), getY(), direction == Direction.LEFT ? -1 : 1, this);
    level.addFireball(fireball);
  }

  /**
   * Decrease health after an attack.
   * Health cannot go below 0.
   * @param amount the amount of damage to take
   */
  public void takeDamage(double amount) {
    health -= amount;
    if (health < 0) {
      health = 0;
    }
  }

  /**
   * Update the entity and show image and messages
   * @param input the user input
   * @param camera the camera object
   */
  @Override
  public void update(Input input, Camera camera) {
    super.update(input, camera);
    showMessages();
    draw(camera);
  }

  /**
   * Check if the entity is facing left
   * @return true if the entity is facing left, false otherwise
   */
  public boolean isDirectionLeft() {
    return direction == Direction.LEFT;
  }

  /**
   * Check if the entity is facing right
   * @return true if the entity is facing right, false otherwise
   */
  public boolean isDirectionRight() {
    return direction == Direction.RIGHT;
  }

  /**
   * Get the left direction
   * @return the left direction as a Direction enum
   */
  public Direction getLeftDirection() {
    return Direction.LEFT;
  }

  /**
   * Get the right direction
   * @return the right direction as a Direction enum
   */
  public Direction getRightDirection() {
    return Direction.RIGHT;
  }

  /**
   * Get the speed of the death animation
   * @return the speed as an integer
   */
  public int getDeathAnimationSpeed() {
    return DEATH_ANIMATION_SPEED;
  }

  /**
   * Get the health of the entity
   * @return the health as a double
   */
  public double getHealth() {
    return health;
  }

  /**
   * Set the health of the entity
   * @param health the health as a double
   */
  public void setHealth(double health) {
    this.health = health;
  }

  /**
   * Get the direction the entity is facing
   * @param direction the direction as a Direction enum
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  /**
   * Check if the death animation is complete
   * @return true if the death animation is complete, false otherwise
   */
  public boolean getIsDeathAnimationComplete() {
    return isDeathAnimationComplete;
  }

  /**
   * Set the death animation to complete
   * @param isDeathAnimationComplete true if the death animation is complete, false otherwise
   */
  public void setIsDeathAnimationComplete(boolean isDeathAnimationComplete) {
    this.isDeathAnimationComplete = isDeathAnimationComplete;
  }
  
  /**
   * Add a message to the entity
   * @param message the message to add as a Text object
   */
  public void addMessage(Text message) {
    messages.add(message);
  }

  /**
   * Get the width of the entity.
   * If only one image is provided, the width of that image is returned.
   * @return the width as a double
   */
  @Override
  public double getWidth() {
    if (LEFT_IMAGE != null) {
      return LEFT_IMAGE.getWidth();
    }
    return RIGHT_IMAGE.getWidth();
  }

  /**
   * Get the height of the entity.
   * If only one image is provided, the height of that image is returned.
   * @return the height as a double
   */
  @Override
  public double getHeight() {
    if (LEFT_IMAGE != null) { 
      return LEFT_IMAGE.getHeight();
    }
    return RIGHT_IMAGE.getHeight();
  }
}
