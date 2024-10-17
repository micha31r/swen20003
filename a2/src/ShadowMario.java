import bagel.*;
import scene.SceneManager;
import utils.PropertiesLoader;
import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2024
 *
 * Please enter your name below
 * @author Michael Ren
 */
public class ShadowMario extends AbstractGame {
	private final Image BACKGROUND_IMAGE;
	private final SceneManager sceneManager = new SceneManager();

	/**
	 * Create a new ShadowMario game
	 * @param gameProps The game properties
	 * @param messageProps The message properties
	 */
	public ShadowMario(Properties gameProps, Properties messageProps) {
		super(Integer.parseInt(gameProps.getProperty("windowWidth")),
			  Integer.parseInt(gameProps.getProperty("windowHeight")),
			  messageProps.getProperty("title"));

		BACKGROUND_IMAGE = new Image(gameProps.getProperty("backgroundImage"));

		sceneManager.setScene(SceneManager.SceneId.START);
	}

	/**
	 * The entry point for the program.
	 */
	public static void main(String[] args) {
		Properties gameProps = PropertiesLoader.getGameProperties();
		Properties messageProps = PropertiesLoader.getMessageProperties();
		ShadowMario game = new ShadowMario(gameProps, messageProps);
		game.run();
	}

	/**
	 * Performs a state update of the selected level.
	 * Allows the game to exit when the escape key is pressed.
	 * Handle screen navigation between levels and instruction pages here.
	 * @param input user input
	 */
	@Override
	protected void update(Input input) {
		// Close window
		if (input.wasPressed(Keys.ESCAPE)){
			Window.close();
		}

		// Refresh screen
		BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

		// Update the current scene
		sceneManager.update(input);
	}
}
