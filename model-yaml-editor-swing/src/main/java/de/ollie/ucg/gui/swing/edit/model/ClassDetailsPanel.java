package de.ollie.ucg.gui.swing.edit.model;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;
import static de.ollie.ucg.gui.swing.UcgGuiConstants.TEXTFIELD_WIDTH;

import de.ollie.baselib.swing.Constants;
import de.ollie.ucg.core.model.ClassModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Generated;

@Generated
class ClassDetailsPanel extends AbstractDetailsPanel {

	private final ClassModel classModel;

	private JTextField textFieldName;
	private PropertiesPanel propertiesPanel;

	ClassDetailsPanel(ClassModel classModel) {
		this.classModel = classModel;
	}

	@Override
	ClassDetailsPanel init() {
		propertiesPanel = new PropertiesPanel(classModel.getProperties());
		setLayout(new BorderLayout(HGAP, VGAP));
		setBorder(Constants.createEmptyBorder());
		add(createHeaderPanel(), BorderLayout.NORTH);
		add(propertiesPanel.init(), BorderLayout.CENTER);
		return this;
	}

	private JPanel createHeaderPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		textFieldName = new JTextField(TEXTFIELD_WIDTH);
		textFieldName.setText(classModel.getName());
		p.add(new ColumnPanel<String>("Name:"), BorderLayout.WEST);
		p.add(new ColumnPanel<JComponent>(textFieldName), BorderLayout.CENTER);
		return p;
	}

	@Override
	void transferContentToModel() {
		classModel.setName(textFieldName.getText());
		classModel.setProperties(new ArrayList<>(propertiesPanel.getElements()));
	}
}
