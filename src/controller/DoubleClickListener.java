
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import view.ApplicationWindow;

/**
 * This is our listener class for a double click. It extends 'MouseAdapter'.
 * All listeners are in controller of our MVC structure.
 * This class and its method are public, so are accessible anywhere.
 * @author Samuel & Patrick
 *
 */
public class DoubleClickListener extends MouseAdapter {
	
	public void mouseClicked(MouseEvent e) {
		
		JList list = (JList)e.getSource();
		
		if (e.getClickCount() == 2) {
			
			int index = list.locationToIndex(e.getPoint());
			System.out.println("Index " + index + "has been double clicked.");
			DefaultListModel model = (DefaultListModel)list.getModel();
			
			if (index != -1) model.remove(index);
			
		}
		
	}
	
}