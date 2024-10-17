package core;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import camera.Camera;

/**
 * Base class for all game objects in the game.
 * Handles collision detection and resolution
 * @author Michael Ren
 */
public abstract class GameObject {
  private final double RADIUS;
  private final double SPEED;
  private Image IMAGE = null;
  private double x;
  private double y;
  private boolean canRemove = false;

  /**
   * Create a new game object without an image
   * @param x x position
   * @param y y position
   * @param radius radius of the object, -1 if radius is not specified
   * @param speed speed of the object for camera movement
   */
  public GameObject(double x, double y, double radius, double speed) {
    this.x = x;
    this.y = y;
    this.RADIUS = radius;
    this.SPEED = speed;
  }

  /**
   * Create a new game object with an image
   * @param x x position
   * @param y y position
   * @param radius radius of the object, -1 if radius is not specified
   * @param speed speed of the object for camera movement
   * @param imageSrc image file path
   */
  public GameObject(double x, double y, double radius, double speed, String imageSrc) {
    this(x, y, radius, speed);
    this.IMAGE = new Image(imageSrc);
  }

  /**
   * Calculate collision between two rectangles
   * @param object the target object to collide with
   * @return true if there is a collision, false otherwise
   */
  private boolean collideRect(GameObject object) {
    double w1 = getWidth();
    double h1 = getHeight();
    double x1 = getX() - w1 / 2;
    double y1 = getY() - h1 / 2;
    
    double w2 = object.getWidth();
    double h2 = object.getHeight();
    double x2 = object.getX() - w2 / 2;
    double y2 = object.getY() - h2 / 2;

    return (((x1 <= x2 && x1 + w1 >= x2) || (x1 <= x2 + w2 && x1 + w1 >= x2 + w2) || (x1 <= x2 && x1 + w1 >= x2 + w2)) &&
        ((y1 <= y2 && y1 + h1 >= y2) || (y1 <= y2 + h2 && y1 + h1 >= y2 + h2) || (y1 >= y2 && y1 + h1 <= y2 + h2) || (y2 >= y1 && y2 + h2 <= y1 + h1)));
  }

  /**
   * Calculate collision based on Euclidean distance between two points
   * @param object the target object to collide with
   * @return true if there is a collision, false otherwise
   */
  private boolean collideCircle(GameObject object) {
    double range = object.getRadius() + getRadius();
    return (new Point(getX(), getY())).distanceTo((new Point(object.getX(), object.getY()))) < range;
  }

  /**
   * Check if there is a collision with another game object
   * and resolveCollision if collision is detected.
   * @param object target object to collide with
   */
  public void collideWith(GameObject object) {
    // Use circle-based collision if radius is defined for both objects,
    // else use rectangle-based collision
    boolean collision = getRadius() >= 0 && object.getRadius() >= 0 
      ? collideCircle(object)
      : collideRect(object);

    if (collision)
      resolveCollision(object);
  }

  /**
   * Resolve/respond to collisions
   * @param object the game object being collided with
   */
  protected void resolveCollision(GameObject object) {
    // Debug message
    // System.out.printf("'%s' collided with '%s'\n", getClass().getSimpleName(), object.getClass().getSimpleName());
    // Override this method in the child class
  }

  /**
   * Draw the game object
   * @param input user input
   * @param camera the camera object
   */
  public void update(Input input, Camera camera) {
    if (IMAGE != null)
      IMAGE.draw(x - camera.getX(), y - camera.getY());
  }

  /**
   * Get x position
   * @return x position as a double
   */
  public double getX() {
    return x;
  }

  /**
   * Set x position
   * @param x new x position
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Get y position
   * @return y position as a double
   */
  public double getY() {
    return y;
  }

  /**
   * Set y position
   * @param y new y position
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Get the width of the game object (based on image width)
   * @return width in pixels
   */
  public double getWidth() {
    return IMAGE.getWidth();
  }

  /**
   * Get the height of the game object (based on image height)
   * @return height in pixels
   */
  public double getHeight() {
    return IMAGE.getHeight();
  }

  /**
   * Get the radius of the game object
   * @return radius as a double
   */
  public double getRadius() {
    return RADIUS;
  }

  /**
   * Get the speed of the game object
   * @return speed as a double
   */
  public double getSpeed() {
    return SPEED;
  }

  /**
   * Getter for canRemove
   * @return true if the object can be removed, false otherwise
   */
  public boolean getCanRemove() {
    return canRemove;
  }

  /**
   * Setter for canRemove
   * @param canRemove true if the object can be removed, false otherwise
   */
  public void setCanRemove(boolean canRemove) {
    this.canRemove = canRemove;
  }
}
