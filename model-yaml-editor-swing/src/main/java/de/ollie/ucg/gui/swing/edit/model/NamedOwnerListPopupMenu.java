package de.ollie.ucg.gui.swing.edit.model;

import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import lombok.Generated;

@Generated
public class NamedOwnerListPopupMenu {

	public interface PopupMenuObserver {
		void onDelete();

		void onNew();
	}

	private final JPopupMenu popupMenu = new JPopupMenu();

	public NamedOwnerListPopupMenu(PopupMenuObserver observer) {
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem newItem = new JMenuItem("New");
		deleteItem.addActionListener(e -> observer.onDelete());
		newItem.addActionListener(e -> observer.onNew());
		popupMenu.add(newItem);
		popupMenu.add(new JSeparator());
		popupMenu.add(deleteItem);
	}

	public void show(Component parent, int x, int y) {
		popupMenu.show(parent, x, y);
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}
}
