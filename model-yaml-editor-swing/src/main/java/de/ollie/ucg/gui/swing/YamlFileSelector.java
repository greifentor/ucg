package de.ollie.ucg.gui.swing;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

class YamlFileSelector {

	String select(Component parent) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select Model File");
		FileNameExtensionFilter yamlFilter = new FileNameExtensionFilter("Model Files (*.yml, *.yaml)", "yml", "yaml");
		chooser.setFileFilter(yamlFilter);
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = chooser.showOpenDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
}
