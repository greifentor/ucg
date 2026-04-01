package de.ollie.ucg.gui.swing;

import javax.swing.SwingUtilities;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@Generated
@SpringBootApplication
@ComponentScans({ @ComponentScan("de.ollie.ucg"), @ComponentScan("de.ollie.baselib") })
public class ModelManagerSwingGUIStarter {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ModelManagerSwingGUIStarter.class);
		app.setHeadless(false); // GUI erlauben
		ConfigurableApplicationContext context = app.run(args);

		// GUI aus dem Spring Context holen
		ModelManagerSwingMainFrame mainFrame = context.getBean(ModelManagerSwingMainFrame.class);
		SwingUtilities.invokeLater(mainFrame::showFrame);
	}
}
