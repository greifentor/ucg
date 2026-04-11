package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.NameOwner;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerList.SelectedItemObserver;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerListPopupMenu.PopupMenuObserver;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Generated
@RequiredArgsConstructor
abstract class AbstractPanel<T extends NameOwner, P extends AbstractDetailsPanel>
	extends JSplitPane
	implements PopupMenuObserver, SelectedItemObserver<T> {

	@Getter
	protected final Model model;

	private P currentDetailsPanel;
	private NamedOwnerList<T> list;

	AbstractPanel<T, P> init() {
		list = new NamedOwnerList<>(getItems(), this, this);
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		setOneTouchExpandable(true);
		setDividerLocation(0.2);
		setLeftComponent(list);
		setRightComponent(new JLabel(""));
		return this;
	}

	abstract List<T> getItems();

	abstract P createDetailsPanel(T selectedItem);

	@Override
	public void itemSelected(T selectedItem) {
		transferContentToModel();
		currentDetailsPanel = createDetailsPanel(selectedItem);
		setRightComponent(currentDetailsPanel);
	}

	public void transferContentToModel() {
		if (currentDetailsPanel != null) {
			currentDetailsPanel.transferContentToModel();
		}
	}

	@Override
	public void onDelete() {
		list.getSelectedItem().ifPresent(e -> list.remove(e));
	}

	@Override
	public void onNew() {
		list.add(createNewItem());
	}

	abstract T createNewItem();
}
