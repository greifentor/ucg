package de.ollie.ucg.template.processor.velocity.adapter;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import java.io.StringWriter;
import java.nio.file.Paths;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

public class VelocityTemplateProcessorAdapter implements TemplateProcessorPort {

	@Override
	public String process(GeneratorConfiguration generatorConfiguration, ClassModel classModel) {
		VelocityContext context = new VelocityContext();
		String templatePathName = "velocity-template-processing-adapter/src/test/resources/templates";
		Velocity.init();
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loaders", "file");
		velocityEngine.setProperty("resource.loader.file.class", FileResourceLoader.class.getName());
		velocityEngine.setProperty("resource.loader.file.path", Paths.get(templatePathName).toAbsolutePath().toString());
		velocityEngine.init();
		Template t = velocityEngine.getTemplate("TestTemplate.vc");
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}
}
