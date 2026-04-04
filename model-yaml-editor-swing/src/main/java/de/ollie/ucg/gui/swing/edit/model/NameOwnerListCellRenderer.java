package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.baselib.swing.Constants;
import de.ollie.ucg.core.model.NameOwner;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

class NameOwnerListCellRenderer<T extends NameOwner> implements ListCellRenderer<T> {

	@Override
	public Component getListCellRendererComponent(
		JList<? extends T> list,
		T value,
		int index,
		boolean isSelected,
		boolean cellHasFocus
	) {
		JLabel label = new JLabel();
		if (isSelected) {
			label.setBackground(Constants.COLOR_LIST_SELECTED_BACKGROUND);
			label.setForeground(Constants.COLOR_LIST_SELECTED_FOREGROUND);
			label.setOpaque(true);
		} else {
			label.setBackground(Constants.COLOR_LIST_BACKGROUND);
			label.setForeground(Constants.COLOR_LIST_FOREGROUND);
			label.setOpaque(false);
		}
		label.setText(value.getName());
		return label;
	}
}
