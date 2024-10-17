package camera;

import bagel.Window;

/**
 * Camera class to represent the camera in the game.
 * Instead of updating the position of every object in the game, the camera's position is updated.
 * This achieves horizontal scrolling effect by rendering game objects relative to the camera offset.
 * @author Michael Ren
 */
public class Camera {
  private static final double width = Window.getWidth();
  private static final double height = Window.getHeight();
  private double x;
  private double y;
  private boolean useBoundary = false;
  private double boundaryLeft;
  private double boundaryRight;

  /**
   * Create a new camera object, given initial position
   * @param x x position
   * @param y y position
   */
  public Camera(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Set horizontal boundaries for the camera,
   * so it does not move past these boundaries.
   * @param boundaryLeft x position of left boundary
   * @param boundaryRight x position of right boundary
   */
  public void setBoundaryX(double boundaryLeft, double boundaryRight) {
    useBoundary = true;
    this.boundaryLeft = boundaryLeft;
    this.boundaryRight = boundaryRight;
  }

  /**
   * Get x position of the camera
   * @return x position
   */
  public double getX() {
    return x;
  }

  /**
   * Set the x position
   * @param x new x position
   */
  public void setX(double x) {
    this.x = x;
    if (useBoundary) {
      if (x + width > boundaryRight) {
        this.x = boundaryRight - width;
      } else if (x < boundaryLeft) {
        this.x = boundaryLeft;
      }
    }
  }

  /**
   * Get the y position of the camera
   * @return y position
   */
  public double getY() {
    return y;
  }

  /**
   * Get the width of the camera
   * @return camera view width
   */
  public double getWidth() {
    return width;
  }

  /**
   * Get the height of the camera
   * @return camera view height
   */
  public double getHeight() {
    return height;
  }
}