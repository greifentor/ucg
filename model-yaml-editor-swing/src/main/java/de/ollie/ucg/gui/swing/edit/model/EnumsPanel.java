package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.EnumModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerList.SelectedItemObserver;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import lombok.Generated;

@Generated
class EnumsPanel extends JSplitPane implements SelectedItemObserver<EnumModel> {

	private final Model model;

	private EnumDetailsPanel currentDetailsPanel;

	EnumsPanel(Model model) {
		super(JSplitPane.HORIZONTAL_SPLIT, new JLabel(""), new JLabel(""));
		setOneTouchExpandable(true);
		setDividerLocation(0.2);
		this.model = model;
	}

	EnumsPanel init() {
		setLeftComponent(new NamedOwnerList<EnumModel>(model.getEnums(), this));
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
}
