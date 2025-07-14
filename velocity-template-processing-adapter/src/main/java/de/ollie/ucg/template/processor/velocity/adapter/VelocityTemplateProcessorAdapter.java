package de.ollie.ucg.template.processor.velocity.adapter;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import jakarta.inject.Named;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

@Named
public class VelocityTemplateProcessorAdapter implements TemplateProcessorPort {

	@AllArgsConstructor
	@Data
	@Generated
	public static class Attribute {

		private String name;
		private String type;
	}

	@Override
	public String process(GeneratorSetting generatorSetting, ClassModel classModel) {
		VelocityContext context = new VelocityContext();
		context.put(
			"Attributes",
			classModel.getAttributes().stream().map(am -> new Attribute(am.getName(), am.getType().getName())).toList()
		);
		context.put("ClassName", classModel.getName());
		context.put("Imports", getImports(classModel));
		context.put("PackageName", generatorSetting.getPackageName());
		String templatePathName = generatorSetting.getTemplatePath();
		Velocity.init();
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loaders", "file");
		velocityEngine.setProperty("resource.loader.file.class", FileResourceLoader.class.getName());
		velocityEngine.setProperty("resource.loader.file.path", Paths.get(templatePathName).toAbsolutePath().toString());
		velocityEngine.init();
		Template t = velocityEngine.getTemplate(generatorSetting.getTemplateFileName());
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}

	private List<String> getImports(ClassModel classModel) {
		return classModel
			.getAttributes()
			.stream()
			.flatMap(a -> a.getType().getProperties().stream())
			.filter(p -> "import".equalsIgnoreCase(p.getName()))
			.map(Property::getValue)
			.map(Object::toString)
			.sorted()
			.toList();
	}
}
