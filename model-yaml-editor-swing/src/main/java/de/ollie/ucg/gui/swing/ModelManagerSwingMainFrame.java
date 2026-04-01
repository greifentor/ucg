package de.ollie.ucg.gui.swing;

import jakarta.inject.Named;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@Named
class ModelManagerSwingMainFrame extends JFrame {

	public void showFrame() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("UCG Model Manager");
		setSize(640, 480);
		setVisible(true);
	}
}
