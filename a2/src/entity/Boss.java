package entity;

import bagel.Input;
import bagel.Window;
import camera.Camera;
import level.Level;
import text.Text;
import utils.PropertiesLoader;

/**
 * Boss enemy, the final enemy in the game.
 * Automatically shoots fireballs at the player if the player is within a certain radius.
 * Player must defeat the boss enemy to win the last level.
 * @author Michael Ren
 */
public class Boss extends Entity {
  private static double RADIUS = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.enemyBoss.radius"));
  private final static double SPEED = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.enemyBoss.speed"));
  private final static String IMAGE_SRC = PropertiesLoader.getGameProperty("gameObjects.enemyBoss.image");
  private final static double INITIAL_HEALTH = Double.parseDouble(PropertiesLoader.getGameProperty("gameObjects.enemyBoss.health"));
  private final static int ACTIVATION_RADIUS = Integer.parseInt(PropertiesLoader.getGameProperty("gameObjects.enemyBoss.activationRadius"));
  private final static int MAX_FRAMES = 100; // As per project specs
  private final Level level;
  private int shootTimer = MAX_FRAMES;

  /**
   * Create a new boss
   * @param level the level object
   * @param x x position
   * @param y y position
   */
  public Boss(Level level, double x, double y) {
    super(level, x, y, RADIUS, SPEED, IMAGE_SRC, IMAGE_SRC);
    this.level = level;
    setHealth(INITIAL_HEALTH);
    setDirection(getLeftDirection());

    // Create health message
    Text message = new Text("health", "enemyBossHealth.fontSize", "enemyBossHealth.x", "enemyBossHealth.y") {
      @Override
      public String createMessage(String message) {
        return message + " " + Math.round((getHealth() * 100));
      }
    };
    message.setColor(255, 0, 0);
    addMessage(message);
  }

  /**
   * Activate shoot ability for self and player if player is within activation radius
   */
  private void activateShoot() {
    Player player = level.getPlayer();
    if (Math.abs(getX() - player.getX()) <= ACTIVATION_RADIUS) {
      // Allow player to shoot
      player.setCanShoot(true);

      // Change direction to always shoot towards the player
      if (getX() < player.getX()) {
        setDirection(getRightDirection());
      } else {
        setDirection(getLeftDirection());
      }

      // Randomly shoot fireball
      if (shootTimer <= 0) {
        if (Math.round(Math.random()) == 1) {
          shoot();
        }
        shootTimer = MAX_FRAMES;
      } else {
        shootTimer--;
      }
    }
  }

  /**
   * Update and draw the boss enemy.
   * If the boss enemy's health is 0, the boss enemy will move off the screen.
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
    } else {
      activateShoot();
    }
  }
}
