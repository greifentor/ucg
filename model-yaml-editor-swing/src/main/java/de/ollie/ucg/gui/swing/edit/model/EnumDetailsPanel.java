package de.ollie.ucg.gui.swing.edit.model;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;
import static de.ollie.ucg.gui.swing.UcgGuiConstants.TEXTFIELD_WIDTH;

import de.ollie.ucg.core.model.EnumModel;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Generated;

@Generated
class EnumDetailsPanel extends JPanel {

	private final EnumModel enumModel;

	private JTextField textFieldName;

	EnumDetailsPanel(EnumModel enumModel) {
		this.enumModel = enumModel;
	}

	EnumDetailsPanel init() {
		setLayout(new BorderLayout(HGAP, VGAP));
		add(createHeaderPanel(), BorderLayout.NORTH);
		return this;
	}

	private JPanel createHeaderPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		textFieldName = new JTextField(TEXTFIELD_WIDTH);
		textFieldName.setText(enumModel.getName());
		p.add(new ColumnPanel<String>("Name:"), BorderLayout.WEST);
		p.add(new ColumnPanel<JComponent>(textFieldName), BorderLayout.CENTER);
		return p;
	}

	void transferContentToModel() {
		enumModel.setName(textFieldName.getText());
	}
}
