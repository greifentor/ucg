package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.NameOwner;
import java.awt.Dimension;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lombok.Generated;

@Generated
public class NamedOwnerList<T extends NameOwner> extends JList<T> implements ListSelectionListener {

	public interface SelectedItemObserver<T> {
		void itemSelected(T selectedItem);
	}

	private final SelectedItemObserver<T> observer;

	NamedOwnerList(List<T> list, SelectedItemObserver<T> observer) {
		super();
		this.observer = observer;
		DefaultListModel<T> listModel = new DefaultListModel<>();
		listModel.addAll(list);
		setModel(listModel);
		setCellRenderer(new NameOwnerListCellRenderer<NameOwner>());
		setPreferredSize(new Dimension(200, 200));
		addListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if ((observer != null) && e.getValueIsAdjusting()) {
			observer.itemSelected(getSelectedValue());
		}
	}
}
