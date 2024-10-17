package text;

import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Point;
import utils.PropertiesLoader;

/**
 * Text class to load and display text on screen
 * @author Michael Ren
 */
public class Text {
  private final Font FONT;
  private final String MESSAGE;
  private final int SIZE;
  private DrawOptions options;
  private Point pos;

  /**
   * Create a new text object
   * @param messageName name of the message property
   * @param sizeName name of the size property
   */
  public Text(String messageName, String sizeName) {
    MESSAGE = PropertiesLoader.getMessageProperty(messageName);
    SIZE = Integer.parseInt(PropertiesLoader.getGameProperty(sizeName));
    FONT = new Font(PropertiesLoader.getGameProperty("font"), SIZE);
  }

  /**
   * Create a new text object with position
   * @param messageName name of the message property
   * @param sizeName name of the size property
   * @param xName name of the x position property
   * @param yName name of the y position property
   */
  public Text(String messageName, String sizeName, String xName, String yName) {
    this(messageName, sizeName);
    setPosition(
      Double.parseDouble(PropertiesLoader.getGameProperty(xName)),
      Double.parseDouble(PropertiesLoader.getGameProperty(yName)));
  }

  /**
   * Set the color of the text
   * @param r 0 - 255 amount of red
   * @param g 0 - 255 amount of green
   * @param b 0 - 255 amount of blue
   */
  public void setColor(int r, int g, int b) {
    options = new DrawOptions();
    options.setBlendColour(r, g, b);
  }

  /**
   * Set the position of the text, if not already set through the constructor
   * Use this method if the text position is not already defined in the properties file
   * @param x x position
   * @param y y position
   */
  public void setPosition(double x, double y) {
    pos = new Point(x, y);
  }

  /**
   * Create a new message using message string as a template
   * @param message the default message string passed in constructor
   * @return constructed string
   */
  public String createMessage(String message) {
    return message;
  }

  /**
   * Draw text on screen
   */
  public void update() {
    if (options == null) {
      FONT.drawString(createMessage(MESSAGE), pos.x, pos.y);
    } else {
      FONT.drawString(createMessage(MESSAGE), pos.x, pos.y, options);
    }
  }

  /**
   * Get the font object
   * @return bagel font object
   */
  public Font getFont() {
    return FONT;
  }

  /**
   * Get the text message
   * @return message as string
   */
  public String getMessage() {
    return MESSAGE;
  }
}
