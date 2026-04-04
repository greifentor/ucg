package de.ollie.ucg.gui.swing;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;

import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.gui.swing.ModelManagerMainMenuBar.MainMenuItem;
import de.ollie.ucg.gui.swing.ModelManagerMainMenuBar.MainMenuObserver;
import de.ollie.ucg.gui.swing.edit.model.ModelEditorJInternalFrame;
import de.ollie.ucg.yaml.YamlModelReader;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

@Generated
@Named
@RequiredArgsConstructor
class ModelManagerSwingMainFrame extends JFrame implements MainMenuObserver {

	private final transient YamlModelReader yamlModelReader;

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
		if (itemSelected == MainMenuItem.FILE_OPEN) {
			openAndEditModelFile();
		} else if (itemSelected == MainMenuItem.FILE_QUIT) {
			quitApplication();
		}
	}

	private void openAndEditModelFile() {
		String fileNameToLoad = new YamlFileSelector().select(this);
		if (fileNameToLoad != null) {
			Model model;
			try {
				model = yamlModelReader.read(fileNameToLoad);
				new ModelEditorJInternalFrame(model, desktopPane);
			} catch (IOException e) {
				showErrorMessage("I/O Error", "Error while reading the model file: " + e.getMessage());
			}
		}
	}

	private void showErrorMessage(String title, String message) {
		JOptionPane.showConfirmDialog(this, message, title, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
	}

	private void quitApplication() {
		setVisible(false);
		System.exit(0);
	}
}
