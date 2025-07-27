package de.ollie.ucg.template.processor.velocity.adapter;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.service.CodeGeneratorService;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.AttributeModelWrapper;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.ClassModelWrapper;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.ModelWrapper;
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
	public GenerationResult process(
		GeneratorConfiguration generatorConfiguration,
		GeneratorSetting generatorSetting,
		Model model,
		ClassModel classModel
	) {
		VelocityContext context = new VelocityContext();
		context.put("Attributes", getAttributeWrappers(classModel, model));
		context.put("Class", new ClassModelWrapper(classModel, model));
		context.put("ClassName", classModel.getName());
		context.put("ClassNameAsAttribute", toAttributeName(classModel.getName()));
		context.put("GeneratedCodeMarker", CodeGeneratorService.GENERATED_CODE_MARKER);
		context.put("Imports", getImports(classModel));
		context.put("Model", new ModelWrapper(model));
		context.put("PackageName", generatorSetting.getPackageName());
		context.put("Properties", generatorConfiguration.getPropertiesByNames());
		String templatePathName = generatorSetting.getTemplatePath();
		Velocity.init();
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loaders", "file");
		velocityEngine.setProperty("resource.loader.file.class", generatorSetting.getResourceLoaderClass());
		if (templatePathName != null) {
			velocityEngine.setProperty("resource.loader.file.path", Paths.get(templatePathName).toAbsolutePath().toString());
		}
		velocityEngine.init();
		Template t = velocityEngine.getTemplate(generatorSetting.getTemplateFileName());
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return new GenerationResult().setCode(writer.toString()).setUnitName(classModel.getName());
	}

	private String toAttributeName(String s) {
		return s.length() < 2 ? s.toLowerCase() : s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	private List<AttributeModelWrapper> getAttributeWrappers(ClassModel classModel, Model model) {
		return classModel.getAttributes().stream().map(a -> new AttributeModelWrapper(a, model)).toList();
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
