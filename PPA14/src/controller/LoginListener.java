package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import auth.Auth;
import model.Model;
import view.ApplicationWindow;

/**
 * This is our listener class for logins. It extends 'ActionListener'.
 * All listeners are in controller of our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel & Patrick
 *
 */
public class LoginListener implements ActionListener {

	private ApplicationWindow view;
	private Model model;
	private static HashMap<String, String> userList;
	
	/**
	 * Constructor method for LoginListener. Sets values of above fields & initialises userList HashMap.
	 * @param view
	 * @param model
	 */
	public LoginListener(ApplicationWindow view, Model model) {
		
		System.out.println("Constructor call");
		this.view = view;
		this.model = model;
		userList = new HashMap<String, String>();
		
		readUserList();
	}
	
	public static void readUserList() {
				
		try (BufferedReader br = new BufferedReader(new FileReader("src/res/userlist.txt"))){
            			
			String credentials;
			
            while ((credentials = br.readLine()) != null) {
            	
            	String[] seperated = credentials.split(", ");
                
            	if (seperated.length != 2) continue;
            	
            	userList.put(seperated[0], seperated[1]);
            
            }
            
        } catch (Exception e) { e.printStackTrace(); }
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		readUserList();
		
		String username = view.getUser();
		
		model.setUser(username);
		
		char[] password = view.getPasswordBytes();
		
		String passwordString = String.valueOf(password);
		
		passwordString = Auth.hashPassword(passwordString);
		model.setPassword(passwordString);
		

		for (Map.Entry<String, String> entry : userList.entrySet()) {
			
			System.out.println(entry.getKey());
			
		}
		
		if (userList.containsKey(username)) {
			
			String foundPassword = userList.get(username);
			
			System.out.println(foundPassword);
			
			if (passwordString.equals(foundPassword)) {
				model.readUserFiles();
				view.showMainPanel();
				
			} else {
				
				view.resetPasswordField();
				
			}
			
		} else {
			
			
		}
		
	}

}
