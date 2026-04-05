package de.ollie.ucg.gui.swing.edit.model;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Generated;

@Generated
public class ColumnPanel<T> extends JPanel {

	ColumnPanel(T... objects) {
		setLayout(new GridLayout(objects.length, 1, HGAP, VGAP));
		for (T o : objects) {
			if (o instanceof String) {
				add(new JLabel((String) o));
			} else if (o instanceof Component) {
				add((Component) o);
			}
		}
	}
}
