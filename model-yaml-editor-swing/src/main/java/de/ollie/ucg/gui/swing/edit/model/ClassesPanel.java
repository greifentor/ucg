package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

@Generated
class ClassesPanel extends AbstractPanel<ClassModel, AbstractDetailsPanel> {

	public ClassesPanel(Model model) {
		super(model);
	}

	@Override
	List<ClassModel> getItems() {
		return getModel().getClasses();
	}

	@Override
	ClassDetailsPanel createDetailsPanel(ClassModel selectedItem) {
		return new ClassDetailsPanel(selectedItem).init();
	}

	@Override
	ClassModel createNewItem() {
		return new ClassModel().setName("NEW_CLASS").setAttributes(new ArrayList<>(List.of())).setProperties(List.of());
	}
}
