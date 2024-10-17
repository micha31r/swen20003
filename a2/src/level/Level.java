package level;

import java.util.ArrayList;
import bagel.Input;
import camera.Camera;
import collectable.Coin;
import collectable.DoubleScorePowerUp;
import collectable.InvinciblePowerUp;
import core.GameObject;
import entity.Boss;
import entity.Fireball;
import entity.Player;
import entity.Slime;
import flag.Flag;
import platform.FlyingPlatform;
import platform.Platform;
import scene.Scene;
import scene.SceneManager;
import utils.IO;
import utils.PropertiesLoader;

/**
 * A level is a scene responsible for the main gameplay.
 * It contains the game objects such as the player, enemies, platforms, and collectables.
 * It also checks for win and lose conditions and switches scenes accordingly.
 * @author Michael Ren
 */
public abstract class Level extends Scene {
  // As specified in project specs
  private final static int PLATFORM_WIDTH = 6000;

  private final ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
  private final ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
  private Player player;
  private Boss boss;

  /**
   * Create a new Level
   * @param sceneManager the scene manager
   * @param dataName the level csv file property name, as defined in app.properties
   */
  public Level(SceneManager sceneManager, String dataName) {
    super(sceneManager);
    getCamera().setBoundaryX(0, PLATFORM_WIDTH);
    String[][] data = IO.readCsv(PropertiesLoader.getGameProperty(dataName));
    createLevel(data);
  }

  /**
   * Create the level from the parsed level data
   * @param data 2D array of level data
   */
  private void createLevel(String[][] data) {
    for (String[] item : data) {
      String type = item[0];
      double x = Double.parseDouble(item[1]);
      double y = Double.parseDouble(item[2]);
      
      switch (type) {
        case "PLAYER":
          player = new Player(this, x, y);
          player.setBoundaryX(0, PLATFORM_WIDTH);
          gameObjects.add(player);
          break;
        case "PLATFORM":
          gameObjects.add(new Platform(x, y));
          break;
        case "ENEMY":
          gameObjects.add(new Slime(x, y));
          break;
        case "COIN":
          gameObjects.add(new Coin(x, y));
          break;
        case "INVINCIBLE_POWER":
          gameObjects.add(new InvinciblePowerUp(x, y));
          break;
        case "DOUBLE_SCORE":
          gameObjects.add(new DoubleScorePowerUp(x, y));
          break;
        case "FLYING_PLATFORM":
          gameObjects.add(new FlyingPlatform(x, y));
          break;
        case "ENEMY_BOSS":
          boss = new Boss(this, x, y);
          gameObjects.add(boss);
          break;
        case "END_FLAG":
          gameObjects.add(new Flag(x, y));
          break;
      }
    }
  }

  /**
   * Check if player has won
   * @return true if the player has reached the flag
   */
  protected boolean checkGameWon() {
    return getPlayer().getReachedFlag();
  }

  /**
   * Check if player has lost
   * @return true if the player has died, and death animation is complete
   */
  protected boolean checkGameLost() {
    return getPlayer().getIsDeathAnimationComplete();
  }

  /**
   * Update all game objects in the level (excluding fireballs).
   * Game objects are rendered, and checked for collision with the player.
   * Unused objects are added to the unusedObjects list for removal.
   * @param input user input
   * @param camera camera object
   * @param unusedObjects list of unused objects to remove
   */
  private void updateGameObjects(Input input, Camera camera, ArrayList<GameObject> unusedObjects) {
    for (GameObject object : gameObjects) {
      object.update(input, camera);

      // Collision detection between player and other game objects
      if (object != player) {
        object.collideWith(getPlayer());
      }

      // Remove unused objects
      if (object.getCanRemove()) {
        unusedObjects.add(object);
      }
    }
  }

  /**
   * Update all fireballs in the level.
   * Fireballs are rendered, and checked for collision with the player and boss enemy.
   * Unused fireballs are added to the unusedObjects list for removal.
   * @param input
   * @param camera
   * @param unusedObjects
   */
  private void updateFireballs(Input input, Camera camera, ArrayList<GameObject> unusedObjects) {
    Boss boss = getBoss();
    if (boss == null)
      return;

    for (Fireball fireball : fireballs) {
      fireball.update(input, camera);
      fireball.collideWith(getPlayer());
      fireball.collideWith(boss);

      // Remove unused objects
      if (fireball.getCanRemove()) {
        unusedObjects.add(fireball);
      }
    }
  }

  /**
   * Update and render all game objects.
   * Remove any game objects that are no longer needed.
   * Check if player has won or lost the level, and switch scenes accordingly.
   * @param input user input
   */
  @Override
  public void update(Input input) {
    ArrayList<GameObject> unusedObjects = new ArrayList<GameObject>();

    Camera camera = getCamera();
    updateGameObjects(input, camera, unusedObjects);
    updateFireballs(input, camera, unusedObjects);
    
    // Destroy unused objects
    gameObjects.removeAll(unusedObjects);
    fireballs.removeAll(unusedObjects);

    // Switch scene if won or lost level
    if (checkGameWon()) {
      sceneManager.setScene(SceneManager.SceneId.WIN);
    } else if (checkGameLost()) {
      sceneManager.setScene(SceneManager.SceneId.LOSE);
    }
  }
  
  /**
   * Add a game object
   * @param object the GameObject instance to add
   */
  public void addGameObject(GameObject object) {
    gameObjects.add(object);
  }

  /**
   * Add a fireball object
   * @param fireball the Fireball instance to add
   */
  public void addFireball(Fireball fireball) {
    fireballs.add(fireball);
  }

  /**
   * Get player object
   * @return Player object
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Get boss object
   * @return Boss object
   */
  public Boss getBoss() {
    return boss;
  }
}
