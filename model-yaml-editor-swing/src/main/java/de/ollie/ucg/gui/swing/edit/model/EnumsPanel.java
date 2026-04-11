package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.EnumModel;
import de.ollie.ucg.core.model.Model;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

@Generated
class EnumsPanel extends AbstractPanel<EnumModel, AbstractDetailsPanel> {

	public EnumsPanel(Model model) {
		super(model);
	}

	@Override
	List<EnumModel> getItems() {
		return getModel().getEnums();
	}

	@Override
	EnumDetailsPanel createDetailsPanel(EnumModel selectedItem) {
		return new EnumDetailsPanel(selectedItem).init();
	}

	@Override
	EnumModel createNewItem() {
		return new EnumModel().setName("NEW_ENUM").setIdentifiers(new ArrayList<>(List.of("NEW_IDENTIFIER")));
	}
}
