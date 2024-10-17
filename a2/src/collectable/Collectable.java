package collectable;

import core.GameObject;

/**
 * Interface for objects that can be collected by another game object (e.g. Player)
 * @param <T> the type of game object that can collect this object
 * @author Michael Ren
 */
public interface Collectable<T extends GameObject> {
  /**
   * Handle the collection of the object by another game object
   * @param object the object that is collecting this object
   */
  public void collect(T object);
}
