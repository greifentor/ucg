package de.ollie.ucg.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import lombok.Generated;

@Generated
class ModelManagerMainMenuBar extends JMenuBar {

	interface MainMenuObserver {
		void menuItemSelected(MainMenuItem itemSelected);
	}

	enum MainMenuItem {
		FILE_OPEN,
		FILE_QUIT,
	}

	private final transient MainMenuObserver observer;

	ModelManagerMainMenuBar(MainMenuObserver observer) {
		this.observer = observer;
		createFileMenu();
	}

	private void createFileMenu() {
		addFileMenu();
	}

	private void addFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createMenuItem("Open ...", e -> fireEventToObserver(e, MainMenuItem.FILE_OPEN)));
		menu.add(new JSeparator());
		menu.add(createMenuItem("Quit", e -> fireEventToObserver(e, MainMenuItem.FILE_QUIT)));
		add(menu);
	}

	private JMenuItem createMenuItem(String text, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.addActionListener(listener);
		return menuItem;
	}

	private void fireEventToObserver(ActionEvent e, MainMenuItem mainMenuItem) {
		if (observer != null) {
			observer.menuItemSelected(mainMenuItem);
		}
	}
}
