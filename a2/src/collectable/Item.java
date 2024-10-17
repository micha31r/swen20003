package collectable;

import bagel.Input;
import camera.Camera;
import core.GameObject;
import entity.Player;

/**
 * Item represents a class of game objects that can be collected by the player,
 * and provides a animation when collected.
 * @author Michael Ren
 */
public abstract class Item extends GameObject implements Collectable<Player> {
  private int COLLECT_ANIMATION_SPEED = 10; // As specified in project specs
  private boolean isConsumed = false;

  /**
   * Create a new item
   * @param x x position
   * @param y y position
   * @param radius radius of the item
   * @param speed scroll speed
   * @param imageSrc image file path
   */
  public Item(double x, double y, double radius, double speed, String imageSrc) {
    super(x, y, radius, speed, imageSrc);
  }

  /**
   * Animate item position when collected.
   * Flag item for deletion when it goes off screen.
   */
  private void collectAnimation() {
    if (getY() + getHeight() < 0)
      setCanRemove(true);
    else
      setY(getY() - COLLECT_ANIMATION_SPEED);
  }

  /**
   * Collect the item
   * @param player the player object
   */
  @Override
  public void collect(Player player) {
    isConsumed = true;
  }

  /**
   * Handle collision with the Player object.
   * Collect the item if it's not already consumed.
   * @param object the game object to resolve collision with
   */
  @Override
  protected void resolveCollision(GameObject object) {
    super.resolveCollision(object);
    if (!isConsumed) {
      collect((Player) object);
    }
  }

  /**
   * Update the item's position and animation
   * @param input user input
   * @param camera camera object
   */
  @Override
  public void update(Input input, Camera camera) {
    super.update(input, camera);
    if (isConsumed) {
      collectAnimation();
    }
  }
}
