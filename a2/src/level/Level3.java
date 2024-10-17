package level;

import entity.Player;
import scene.SceneManager;

/**
 * The third level.
 * To win this level, the player must reach the flag and defeat the boss.
 * @author Michael Ren
 */
public class Level3 extends Level {
  /**
   * Create a new level which loads data from level 3 file.
   * @param sceneManager the scene manager
   */
  public Level3(SceneManager sceneManager) {
    super(sceneManager, "level3File");
  }

  /**
   * Check if player has won the level.
   * Player only wins if the the flag is reached, the boss has been defeated,
   * and that the boss must have completed its death animation.
   * @return true if the player has met the conditions
   */
  @Override
  protected boolean checkGameWon() {
    Player player = getPlayer();

    // The player must reach the flag after defeating the boss enemy.
    // If player reached flag before beating the boss enemy, reset the flag reached status.
    if (player.getReachedFlag() && !getBoss().getIsDeathAnimationComplete()) {
      player.setReachedFlag(false);
    }

    return super.checkGameWon() && getBoss().getIsDeathAnimationComplete();
  }
}
