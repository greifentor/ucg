package de.ollie.ucg.gui.swing;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;

import de.ollie.ucg.gui.swing.ModelManagerMainMenuBar.MainMenuItem;
import de.ollie.ucg.gui.swing.ModelManagerMainMenuBar.MainMenuObserver;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@Named
class ModelManagerSwingMainFrame extends JFrame implements MainMenuObserver {

	private JDesktopPane desktopPane;

	public void showFrame() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		createDesktopPane();
		buildMainPanel();
		prepareMainFrame();
		setVisible(true);
	}

	private void createDesktopPane() {
		desktopPane = new JDesktopPane();
		desktopPane.setMinimumSize(new Dimension(200, 100));
	}

	private void buildMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		mainPanel.add(desktopPane, BorderLayout.CENTER);
		setJMenuBar(new ModelManagerMainMenuBar(this));
		setContentPane(mainPanel);
	}

	private void prepareMainFrame() {
		setTitle("UCG Model Manager");
		setBounds(100, 100, 800, 800);
	}

	@Override
	public void menuItemSelected(MainMenuItem itemSelected) {
		if (itemSelected == MainMenuItem.FILE_QUIT) {
			System.exit(0);
		}
	}
}
