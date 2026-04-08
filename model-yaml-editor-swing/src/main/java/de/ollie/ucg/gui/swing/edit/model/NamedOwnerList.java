package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.NameOwner;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerListPopupMenu.PopupMenuObserver;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lombok.Generated;

@Generated
public class NamedOwnerList<T extends NameOwner> extends JList<T> implements ListSelectionListener, MouseListener {

	public interface SelectedItemObserver<T> {
		void itemSelected(T selectedItem);
	}

	private final SelectedItemObserver<T> observer;
	private final PopupMenuObserver popupMenuObserver;
	private final DefaultListModel<T> listModel;

	NamedOwnerList(List<T> list, SelectedItemObserver<T> observer, PopupMenuObserver popupMenuObserver) {
		super();
		this.observer = observer;
		this.popupMenuObserver = popupMenuObserver;
		listModel = new DefaultListModel<>();
		listModel.addAll(list);
		setModel(listModel);
		setCellRenderer(new NameOwnerListCellRenderer<NameOwner>());
		setPreferredSize(new Dimension(200, 200));
		addListSelectionListener(this);
		addMouseListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if ((observer != null) && e.getValueIsAdjusting()) {
			observer.itemSelected(getSelectedValue());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isRightButton(e) && (popupMenuObserver != null)) {
			new NamedOwnerListPopupMenu(popupMenuObserver).show(this, e.getX(), e.getY());
		}
	}

	private boolean isRightButton(MouseEvent e) {
		return e.getButton() == MouseEvent.BUTTON3;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public Optional<T> getSelectedItem() {
		return Optional.ofNullable(getSelectedValue());
	}

	public void remove(T toRemove) {
		listModel.removeElement(toRemove);
	}

	public void add(T toAdd) {
		listModel.addElement(toAdd);
	}
}
