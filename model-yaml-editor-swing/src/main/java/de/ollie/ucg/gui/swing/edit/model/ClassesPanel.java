package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerList.SelectedItemObserver;
import de.ollie.ucg.gui.swing.edit.model.NamedOwnerListPopupMenu.PopupMenuObserver;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import lombok.Generated;

@Generated
class ClassesPanel extends JSplitPane implements PopupMenuObserver, SelectedItemObserver<ClassModel> {

	private final Model model;

	private ClassDetailsPanel currentDetailsPanel;
	private NamedOwnerList<ClassModel> classList;

	ClassesPanel(Model model) {
		super(JSplitPane.HORIZONTAL_SPLIT, new JLabel(""), new JLabel(""));
		setOneTouchExpandable(true);
		setDividerLocation(0.2);
		this.model = model;
	}

	ClassesPanel init() {
		classList = new NamedOwnerList<ClassModel>(model.getClasses(), this, this);
		setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		setOneTouchExpandable(true);
		setDividerLocation(0.2);
		setLeftComponent(classList);
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

	@Override
	public void onDelete() {
		classList.getSelectedItem().ifPresent(e -> classList.remove(e));
	}

	@Override
	public void onNew() {
		classList.add(
			new ClassModel().setName("NEW_CLASS").setAttributes(new ArrayList<>(List.of())).setProperties(List.of())
		);
	}
}
