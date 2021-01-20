package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import auth.Auth;
import model.Model;
import view.ApplicationWindow;

/**
 * This is our listener class for registrations. It extends 'ActionListener'.
 * All listeners are in controller of our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel & Patrick
 *
 */
public class RegisterListener implements ActionListener {

	private ApplicationWindow view;
	private Model model;
	private HashMap<String, String> userList;
	
	/**
	 * Constructor method for RegisterListener. Sets values of above fields & initialises userList HashMap.
	 * @param view
	 * @param model
	 */
	public RegisterListener(ApplicationWindow view, Model model) {
		
		this.view = view;
		this.model = model;
		userList = new HashMap<String, String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/res/userlist.txt"))){
            
			String credentials;
			
            while ((credentials = br.readLine()) != null) {
            	
            	String[] seperated = credentials.split(", ");
                userList.put(seperated[0], seperated[1]);
            
            }
            
        } catch (Exception e) { }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String username = view.getUser();
		model.setUser(username);
		
		char[] password = view.getPasswordBytes();
		
		String passwordString = String.valueOf(password);
		
		passwordString = Auth.hashPassword(passwordString);
		
		if (passwordString.equals(Auth.hashPassword(""))) return;
		
		model.setPassword(passwordString);
				
		if (username.equals(null) || username.equals("") || passwordString.equals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")) return;
		
		if (userList.containsKey(username)) {
			
			view.resetPasswordField();
			
		} else {
			
			userList.put(username, passwordString);
		
			try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream("src/res/userlist.txt", true), "UTF-8"))) {
	    
				printWriter.println("\n" + username + ", " + passwordString);
	        
			} catch (UnsupportedEncodingException e2) {
			
			} catch (FileNotFoundException e1) {
			
			}
		
		}
		
	}

}
