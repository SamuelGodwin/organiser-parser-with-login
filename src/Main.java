import view.ApplicationWindow;
import controller.LoginListener;
import controller.TextFieldListener;
import model.Model;

/**
 * 
 * 'Main' class. This is the driver class for our PPA14 solution. It contains
 * our 'main' method. This class is the only class that is to be compiled and
 * run directly, as the compiler will automatically compile our other referenced
 * classes. This class and its method are public, so are accessible anywhere.
 * 
 * @author Patrick & Samuel
 *
 */
public class Main {

	public static void main(String[] args) {

		Model model = new Model();
		ApplicationWindow appWindow = new ApplicationWindow(model);
		// Adds 'appWindow' as an Observer of Model.
		model.addObserver(appWindow);

	}

}
