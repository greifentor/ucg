package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.NameOwner;
import java.awt.Dimension;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

class NamedOwnerList<T extends NameOwner> extends JList<T> {

	NamedOwnerList(List<T> list) {
		super();
		DefaultListModel<T> listModel = new DefaultListModel<>();
		listModel.addAll(list);
		setModel(listModel);
		setCellRenderer(new NameOwnerListCellRenderer<NameOwner>());
		setPreferredSize(new Dimension(200, 200));
	}
}
