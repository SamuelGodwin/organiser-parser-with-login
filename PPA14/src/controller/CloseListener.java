package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.Model;

/**
 * This is our listener class for closure of the program. It extends 'WindowAdapter'.
 * All listeners are in controller of our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel & Patrick
 *
 */
public class CloseListener extends WindowAdapter {

	private Model model;
	
	/**
	 * Constructor method for CloseListener. Sets values of above field.
	 * @param model
	 */
	public CloseListener(Model model) {
		this.model = model;
	}
	
	/**
	 * Writes files upon closing by calling writeUserFiles() through model. Exits after.
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		
		model.writeUserFiles();
		
		System.exit(0);
	}
	
}
