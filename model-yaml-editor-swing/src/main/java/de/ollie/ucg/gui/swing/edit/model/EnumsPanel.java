package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.EnumModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerList.SelectedItemObserver;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerListPopupMenu.PopupMenuObserver;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

@Generated
@RequiredArgsConstructor
class EnumsPanel extends JSplitPane implements PopupMenuObserver, SelectedItemObserver<EnumModel> {

	private final Model model;

	private EnumDetailsPanel currentDetailsPanel;
	private NamedOwnerList<EnumModel> enumList;

	EnumsPanel init() {
		enumList = new NamedOwnerList<EnumModel>(model.getEnums(), this, this);
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		setOneTouchExpandable(true);
		setDividerLocation(0.2);
		setLeftComponent(enumList);
		setRightComponent(new JLabel(""));
		return this;
	}

	@Override
	public void itemSelected(EnumModel selectedItem) {
		transferContentToModel();
		currentDetailsPanel = new EnumDetailsPanel(selectedItem).init();
		setRightComponent(currentDetailsPanel);
	}

	public void transferContentToModel() {
		if (currentDetailsPanel != null) {
			currentDetailsPanel.transferContentToModel();
		}
	}

	@Override
	public void onDelete() {
		enumList.getSelectedItem().ifPresent(e -> enumList.remove(e));
	}

	@Override
	public void onNew() {
		enumList.add(new EnumModel().setName("NEW_ENUM").setIdentifiers(new ArrayList<>(List.of("NEW_IDENTIFIER"))));
	}
}
