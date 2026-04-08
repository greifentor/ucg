package de.ollie.ucg.gui.swing.edit.model;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;

import de.ollie.ucg.core.model.Property;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

@Generated
@RequiredArgsConstructor
public class PropertiesPanel extends JPanel {

	private final List<Property> properties;

	PropertiesPanel init() {
		setLayout(new BorderLayout(HGAP, VGAP));
		add(
			new HeaderLabelPanel("Properties:")
				.addContent(new JScrollPane(new PropertiesTable(properties)), BorderLayout.CENTER)
		);
		return this;
	}
}
