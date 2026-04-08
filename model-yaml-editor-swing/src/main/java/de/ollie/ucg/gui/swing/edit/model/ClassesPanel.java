package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerList.SelectedItemObserver;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import lombok.Generated;

@Generated
class ClassesPanel extends JSplitPane implements SelectedItemObserver<ClassModel> {

	private final Model model;

	private ClassDetailsPanel currentDetailsPanel;

	ClassesPanel(Model model) {
		super(JSplitPane.HORIZONTAL_SPLIT, new JLabel(""), new JLabel(""));
		setOneTouchExpandable(true);
		setDividerLocation(0.2);
		this.model = model;
	}

	ClassesPanel init() {
		setLeftComponent(new NamedOwnerList<ClassModel>(model.getClasses(), this, null));
		setRightComponent(new JLabel(""));
		return this;
	}

	@Override
	public void itemSelected(ClassModel selectedItem) {
		transferContentToModel();
		currentDetailsPanel = new ClassDetailsPanel(selectedItem).init();
		setRightComponent(currentDetailsPanel);
	}

	public void transferContentToModel() {
		if (currentDetailsPanel != null) {
			currentDetailsPanel.transferContentToModel();
		}
	}
}
