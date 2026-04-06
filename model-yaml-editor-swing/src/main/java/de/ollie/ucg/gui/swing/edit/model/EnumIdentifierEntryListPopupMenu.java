package de.ollie.ucg.gui.swing.edit.model;

import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import lombok.Generated;

@Generated
public class EnumIdentifierEntryListPopupMenu {

	public interface PopupMenuObserver {
		void onDelete();

		void onMoveDown();

		void onMoveUp();

		void onNew();
	}

	private final JPopupMenu popupMenu = new JPopupMenu();

	public EnumIdentifierEntryListPopupMenu(PopupMenuObserver observer) {
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem moveDownItem = new JMenuItem("Move Down");
		JMenuItem moveUpItem = new JMenuItem("Move Up");
		JMenuItem newItem = new JMenuItem("New");
		deleteItem.addActionListener(e -> observer.onDelete());
		moveDownItem.addActionListener(e -> observer.onMoveDown());
		moveUpItem.addActionListener(e -> observer.onMoveUp());
		newItem.addActionListener(e -> observer.onNew());
		popupMenu.add(newItem);
		popupMenu.add(new JSeparator());
		popupMenu.add(deleteItem);
		popupMenu.add(new JSeparator());
		popupMenu.add(moveUpItem);
		popupMenu.add(moveDownItem);
	}

	public void show(Component parent, int x, int y) {
		popupMenu.show(parent, x, y);
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}
}
