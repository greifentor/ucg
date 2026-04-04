package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.Model;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

class EnumsPanel extends JSplitPane {

	private final Model model;

	EnumsPanel(Model model) {
		super(
			JSplitPane.HORIZONTAL_SPLIT,
			new JScrollPane(new NamedOwnerList(model.getEnums())),
			new JLabel("HERE WILL BE DETAILS")
		);
		setOneTouchExpandable(true);
		setDividerLocation(0.2);
		this.model = model;
	}
}
