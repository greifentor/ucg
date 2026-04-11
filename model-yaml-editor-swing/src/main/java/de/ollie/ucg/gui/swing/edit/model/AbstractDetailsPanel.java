package de.ollie.ucg.gui.swing.edit.model;

import javax.swing.JPanel;

abstract class AbstractDetailsPanel extends JPanel {

	abstract <T extends AbstractDetailsPanel> T init();

	abstract void transferContentToModel();
}
