package model;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Observable;

import javax.swing.DefaultListModel;
import auth.*;

/**
 * This is my 'Model' class in my model package. It extends 'Observable'.
 * This class forms the prominent part of my model in our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel
 *
 */
public class Model extends Observable {

	private DefaultListModel<String> calendarList;
	private DefaultListModel<String> reminderList;
	private String user;
	private String password;
	
	/**
	 * Constructor method for Model. Initialises calendarList & reminderList
	 * (DefaultListModels).
	 */
	public Model() {
		
		calendarList = new DefaultListModel<String>();
		
		reminderList = new DefaultListModel<String>();
		
	}
	
	/**
	 * Reads user files.
	 */
	public void readUserFiles() {
		readFromFiles("src/res/" + user + "calendar.txt", calendarList);
		readFromFiles("src/res/" + user + "reminder.txt", reminderList);
	}
	
	/**
	 * Reads from files
	 * @param filename
	 * @param list
	 */
	public void readFromFiles(String filename, DefaultListModel<String> list) {
		
		Auth.decrypt(new File(filename), password);
		try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            
			String event;
			
            while ((event = br.readLine()) != null) {
            	
                list.addElement(event);
            
            }
            
        } catch (Exception e) { }

		update();
		
	}
	
	/**
	 * Writes user files.
	 */
	public void writeUserFiles() {
		writeToFiles("src/res/" + user + "calendar.txt", calendarList);
		writeToFiles("src/res/" + user + "reminder.txt", reminderList);
	}
	
	/**
	 * Writes TO files
	 * @param filename
	 * @param list
	 */
	public void writeToFiles(String filename, DefaultListModel<String> list) {
		
		System.out.println("Writing to files");	
		
		try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename, false), "UTF-8"))) {
	        
			for (int i = 0; i < list.size(); i++) {
	        
				printWriter.println(list.getElementAt(i).toString());
	        
			}
	        
		} catch (UnsupportedEncodingException e) {
			
		} catch (FileNotFoundException e) {
			
		}
		
		Auth.encrypt(new File(filename), password);
	}
	
	/**
	 * For calendar file.
	 */
	public void writeToCalendarFile() {
		String filename = "src/res/" + user + "calendar.txt";
		
		try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(filename, false))) {
	        
			for (int i = 0; i < calendarList.size(); i++) {
	        
				printWriter.println(calendarList.getElementAt(i).toString());
	        
			}
	        
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * For reminder file.
	 */
	public void writeToReminderFile() {
		String filename = "src/res/" + user + "reminder.txt";
		
		try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename, false), "UTF-8"))) {
	        
			for (int i = 0; i < reminderList.size(); i++) {
	        
				printWriter.println(reminderList.getElementAt(i).toString());
	        
			}
	        
		} catch (UnsupportedEncodingException e) {
			
		} catch (FileNotFoundException e) {
			
		}
	}
	
	
	public void addToCalendar(String string) {
		calendarList.addElement(string);
		update();
	}
	
	public void addToReminder(String string) {
		reminderList.addElement(string);
		update();
	}
	
	/**
	 * Calls setChanged() & notifies Observers.
	 */
	public void update() {
		setChanged();
		notifyObservers();
	}

	/**
	 * 'getReminderList' method. This is the accessor method for the contents of DefaultListModel
	 * 'reminderList'.
	 * @return reminderList
	 */
	public DefaultListModel<String> getReminderList() {
		return reminderList;
	}
	
	/**
	 * 'getCalendarList' method. This is the accessor method for the contents of DefaultListModel
	 * 'calendarList'.
	 * @return calendarList
	 */
	public DefaultListModel<String> getCalendarList() {
		return calendarList;
	}

	/**
	 * 'setUser' method. This is a mutator method for the 'user' field in this
	 * class. It uses a parameter of type 'String'. Use of 'this' keyword avoids
	 * naming conflicts between the passed parameter and 'user' field.
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 'getUser' method. This is the accessor method for the contents of String
	 * 'user'.
	 * 
	 * @return user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * 
	 * 'setPassword' method. This is a mutator method for the 'password' field
	 * in this class. It uses a parameter of type 'String'. Use of 'this'
	 * keyword avoids naming conflicts between the passed parameter and
	 * 'password' field.
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
