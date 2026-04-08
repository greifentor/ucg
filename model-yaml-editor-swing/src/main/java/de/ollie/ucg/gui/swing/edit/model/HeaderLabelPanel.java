package de.ollie.ucg.gui.swing.edit.model;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Generated;

@Generated
public class HeaderLabelPanel extends JPanel {

	private final JPanel contentPanel;

	public HeaderLabelPanel(String labelContent) {
		super(new BorderLayout(HGAP, VGAP));
		contentPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		add(new JLabel(labelContent), BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
	}

	public HeaderLabelPanel addContent(Component component, String orientation) {
		contentPanel.add(component, orientation);
		return this;
	}
}
