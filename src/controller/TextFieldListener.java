package controller;

import java.awt.event.*;
import java.util.HashMap;

import model.TokenType;
import model.InputType;
import model.Model;
import model.Parser;
import view.ApplicationWindow;

/**
 * This is my listener class for JTextField. It extends 'ActionListener'.
 * All listeners are in controller of my MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel
 *
 */
public class TextFieldListener implements ActionListener {
	
	private ApplicationWindow view;
	private Model model;
	
	/**
	 * Constructor method for TextFieldListener. Sets values of above fields.
	 * @param view
	 * @param model
	 */
	public TextFieldListener(ApplicationWindow view, Model model) {
		
		this.view = view;
		this.model = model;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Method called");
		String contents = view.getEntryField().getText();
		
		InputType type = Parser.classifyInput(contents);
		
		switch(type) {
		
		case REMINDER:
			contents = Parser.formatReminder(contents);
			if (Parser.classifyInput(contents) == InputType.CALENDAR) {
				HashMap<TokenType, String> tokens = Parser.tokenizeInputToMap(contents);
				model.addToCalendar(Parser.tokensToCalendar(tokens));
			}
			
			model.addToReminder(contents);
			view.resetEntryField();
			break;
			
		case CALENDAR:
			HashMap<TokenType, String> tokens = Parser.tokenizeInputToMap(contents);
			model.addToCalendar(Parser.tokensToCalendar(tokens));
			view.resetEntryField();
			break;
			
		default:
			break;
		}
		
		view.updateLists();
	}

}
