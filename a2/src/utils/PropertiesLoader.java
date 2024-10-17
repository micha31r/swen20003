package utils;

import java.util.Properties;

/**
 * A class that loads properties files and provides access to the properties.
 * Property files are only loaded once and are stored in memory for the lifetime of the program.
 * @author Michael Ren
 */
public class PropertiesLoader {
  private static Properties gameProps;
  private static Properties messageProps;

  /**
   * Get all game properties
   * @return Properties object containing all game properties
   */
  public static Properties getGameProperties() {
    if (gameProps == null)
      gameProps = IO.readPropertiesFile("res/app.properties");
    return gameProps;
  }
  
  /**
   * Get all message properties
   * @return Properties object containing all message properties
   */
  public static Properties getMessageProperties() {
    if (messageProps == null)
      messageProps = IO.readPropertiesFile("res/message_en.properties");
    return messageProps;
  }

  /**
   * Get a specific game property
   * @param name Name of the property
   * @return Property value as string
   */
  public static String getGameProperty(String name) {
    return getGameProperties().getProperty(name);
  }
  
  /**
   * Get a specific message property
   * @param name Name of the property
   * @return Property value as string
   */
  public static String getMessageProperty(String name) {
    return getMessageProperties().getProperty(name);
  }
}
