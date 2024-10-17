package entity;

import bagel.Input;
import bagel.Keys;
import bagel.Window;
import camera.Camera;
import level.Level;
import text.Text;
import utils.PropertiesLoader;

/**
 * The player entity in the game.
 * @author Michael Ren
 */
public class Player extends Entity {
  private static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.player.radius"));
  private static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.platform.speed"));
  private static String IMAGE_LEFT_SRC = PropertiesLoader.getGameProperty("gameObjects.player.imageLeft");
  private static String IMAGE_RIGHT_SRC = PropertiesLoader.getGameProperty("gameObjects.player.imageRight");
  private static double INITIAL_HEALTH = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.player.health"));
  
  // Values below are specified in project specs
  private static final int GRAVITY = 1;
  private static final int JUMP_SPEED = 20;

  private int score = 0;
  private double velocityY = 0;
  private boolean isOnPlatform = true;
  private double previousPlatformY = 0;

  private final double cameraOffset;
  private int invinciblePowerTimer = 0;
  private int doubleScoreTimer = 0;
  private boolean reachedFlag = false;
  private boolean jumpKeyDown = false;
  private boolean shootKeyDown = false;
  private boolean canShoot = false;

  private boolean useBoundary = false;
  private double boundaryLeft;
  private double boundaryRight;

  /**
   * Create a player entity
   * @param level the level object
   * @param x x position
   * @param y y position
   */
  public Player(Level level, double x, double y) {
    super(level, x, y, RADIUS, SPEED, IMAGE_LEFT_SRC, IMAGE_RIGHT_SRC);
    setHealth(INITIAL_HEALTH);

    // Use initial position as camera offset
    cameraOffset = x;

    // Create health message
    Text healthMessage = new Text("health", "playerHealth.fontSize", "playerHealth.x", "playerHealth.y") {
      @Override
      public String createMessage(String message) {
        return message + " " + Math.round((getHealth() * 100));
      }
    };
    addMessage(healthMessage);
  
    // Create score message
    Text scoreMessage = new Text("score", "score.fontSize", "score.x", "score.y") {
      @Override
      public String createMessage(String message) {
        return message + " " + getScore();
      }
    };
    addMessage(scoreMessage);
  }

  /**
   * Set horizontal boundaries for the player,
   * so the player cannot move off the level (base platform)
   * @param boundaryLeft x position of the left boundary
   * @param boundaryRight x position of the right boundary
   */
  public void setBoundaryX(double boundaryLeft, double boundaryRight) {
    useBoundary = true;
    this.boundaryLeft = boundaryLeft;
    this.boundaryRight = boundaryRight;
  }

  /**
   * Move player to the left
   */
  private void moveLeft() {
    setX(getX() - getSpeed());
    setDirection(getLeftDirection());

    // Prevent player from moving off the level
    if (useBoundary) {
      if (getX() - getWidth() / 2 < boundaryLeft) {
        setX(boundaryLeft + getWidth() / 2);
      }
    }
  }

  /**
   * Move player to the right
   */
  private void moveRight() {
    setX(getX() + getSpeed());
    setDirection(getRightDirection());

    // Prevent player from moving off the level
    if (useBoundary) {
      if (getX() + getWidth() / 2 > boundaryRight) {
        setX(boundaryRight - getWidth() / 2);
      }
    }
  }

  /**
   * Make the player jump if it is on a platform
   */
  private void jump() {
    if (isOnPlatform) {
      isOnPlatform = false;
      velocityY = -Math.abs(JUMP_SPEED);
    }
  }

  /**
   * Update the and draw the player.
   * Move the player base on keyboard inputs, and update camera position.
   * If the player is dead, move the player off the screen.
   * @param input user input
   * @param camera the camera object
   */
  @Override
  public void update(Input input, Camera camera) {
    super.update(input, camera);

    if (getHealth() <= 0) {
      if (getY() - getHeight() < Window.getHeight()) {
        setY(getY() + getDeathAnimationSpeed());
      } else {
        setIsDeathAnimationComplete(true);
      }

      return;
    }

    // Horizontal controls
    if (input.isDown(Keys.LEFT)) {
      moveLeft();
    } else if (input.isDown(Keys.RIGHT)) {
      moveRight();
    }
    
    // Jump control
    if (!jumpKeyDown && input.isDown(Keys.UP)) {
      jump();
      jumpKeyDown = true;
    } else if (input.isUp(Keys.UP)) {
      jumpKeyDown = false;
    }

    // Shoot
    if (!shootKeyDown && input.isDown(Keys.S)) {
      shoot();
      shootKeyDown = true;
    } else if (input.isUp(Keys.S)) {
      shootKeyDown = false;
    }

    // Update camera
    camera.setX(getX() - cameraOffset);

    // Update jump motion
    setY(getY() + velocityY);
    
    // Accelerate player when falling
    if (!isOnPlatform) {
      velocityY += GRAVITY;
    }

    // Update power up timers
    if (doubleScoreTimer > 0) {
      doubleScoreTimer--;
    }

    if (invinciblePowerTimer > 0) {
      invinciblePowerTimer--;
    }

    // Set these attributes to false,
    // so we can re-check if they are true in the next update cycle 
    isOnPlatform = false;
    canShoot = false;
  }

  /**
   * Shoot fireball in the current facing direction only if the player can shoot.
   * The player can shoot if the player is near boss enemy.
   */
  @Override
  protected void shoot() {
    if (canShoot) {
      super.shoot();
    }
  }

  /**
   * Decrease health after an attack if the player is not using invincible power up.
   */
  @Override
  public void takeDamage(double damage) {
    if (invinciblePowerTimer <= 0) {
      super.takeDamage(damage);
    }
  }

  /**
   * Check if the player can take damage (ie not using invincible power up)
   * @return true if the player can take damage, false otherwise
   */
  public boolean canTakeDamage() {
    return invinciblePowerTimer <= 0;
  }

  /**
   * Give the player the double score power up
   * @param duration the duration of the power up
   */
  public void useDoubleScore(int duration) {
    doubleScoreTimer = duration;
  }

  /**
   * Give the player the invincible power up
   * @param duration the duration of the power up
   */
  public void useInvinciblePower(int duration) {
    invinciblePowerTimer = duration;
  }

  /**
   * Increase the player's score
   * If the player has the double score power up, the score is increased by 2
   */
  public void increaseScore() {
    score += doubleScoreTimer > 0 ? 2 : 1;
  }

  /**
   * Get player's vertical velocity
   * @return the player's vertical velocity
   */
  public double getVelocityY() {
    return velocityY;
  }

  /**
   * Set player's vertical velocity
   * @param velocityY the new vertical velocity
   */
  public void setVelocityY(double velocityY) {
    this.velocityY = velocityY;
  }

  /**
   * Getter for isOnPlatform 
   * @return true if the player is on a platform, false otherwise
   */
  public boolean getIsOnPlatform() {
    return isOnPlatform;
  }

  /**
   * Setter for isOnPlatform
   * @param isOnPlatform true if the player is on a platform, false otherwise
   */
  public void setIsOnPlatform(boolean isOnPlatform) {
    this.isOnPlatform = isOnPlatform;
  }

  /**
   * Get the player's score
   * @return the player's score
   */
  public int getScore() {
    return score;
  }

  /**
   * Getter for reachedFlag
   * @return true if the player has reached the flag, false otherwise
   */
  public boolean getReachedFlag() {
    return reachedFlag;
  }

  /**
   * Setter for reachedFlag
   * @param reachedFlag new value, true if the player has reached the flag, false otherwise
   */
  public void setReachedFlag(boolean reachedFlag) {
    this.reachedFlag = reachedFlag;
  }

  public void setCanShoot(boolean canShoot) {
    this.canShoot = canShoot;
  }

  /**
   * Get the previous platform's y position
   * @return the previous platform's y position
   */
  public double getPreviousPlatformY() {
    return previousPlatformY;
  }

  /**
   * Set the previous platform's y position
   * @param previousPlatformY the previous platform's y position
   */
  public void setPreviousPlatformY(double previousPlatformY) {
    this.previousPlatformY = previousPlatformY;
  }
}
