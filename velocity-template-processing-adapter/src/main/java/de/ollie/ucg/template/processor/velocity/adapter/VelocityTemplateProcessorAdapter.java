package de.ollie.ucg.template.processor.velocity.adapter;

import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.EnumModel;
import de.ollie.ucg.core.model.GenerationResult;
import de.ollie.ucg.core.model.GeneratorConfiguration;
import de.ollie.ucg.core.model.GeneratorSetting;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.service.CodeGeneratorService;
import de.ollie.ucg.core.service.SettingMappingService;
import de.ollie.ucg.core.service.port.TemplateProcessorPort;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.AttributeModelWrapper;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.ClassModelWrapper;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.EnumModelWrapper;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.ModelWrapper;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.PropertyWrapper;
import de.ollie.ucg.template.processor.velocity.adapter.wrapper.ReferenceWrapper;
import jakarta.inject.Named;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

@Named
@RequiredArgsConstructor
public class VelocityTemplateProcessorAdapter implements TemplateProcessorPort {

	private final SettingMappingService settingMappingService;

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
		context.put("References", getReferences(classModel));
		context.put("Model", new ModelWrapper(model));
		context.put("PackageName", settingMappingService.map(generatorSetting.getPackageName(), classModel.getName()));
		context.put("Properties", generatorConfiguration.getPropertiesByNames());
		return generateCode(generatorSetting, context).setUnitName(classModel.getName());
	}

	private GenerationResult generateCode(GeneratorSetting generatorSetting, VelocityContext context) {
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
		return new GenerationResult().setCode(writer.toString());
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

	private List<ReferenceWrapper> getReferences(ClassModel classModel) {
		return classModel
			.getAttributes()
			.stream()
			.filter(a -> a.isReference())
			.map(a -> a.getType())
			.map(t ->
				new ReferenceWrapper(
					t.getName(),
					t.getProperties().stream().map(p -> new PropertyWrapper(p.getName(), p.getValue())).toList()
				)
			)
			.sorted()
			.toList();
	}

	@Override
	public GenerationResult process(
		GeneratorConfiguration generatorConfiguration,
		GeneratorSetting generatorSetting,
		Model model,
		EnumModel enumModel
	) {
		VelocityContext context = new VelocityContext();
		context.put("Enum", new EnumModelWrapper(enumModel, model));
		context.put("EnumName", enumModel.getName());
		context.put("EnumIdentifiers", enumModel.getIdentifiers());
		context.put("GeneratedCodeMarker", CodeGeneratorService.GENERATED_CODE_MARKER);
		context.put("Model", new ModelWrapper(model));
		context.put("PackageName", settingMappingService.map(generatorSetting.getPackageName(), enumModel.getName()));
		context.put("Properties", generatorConfiguration.getPropertiesByNames());
		return generateCode(generatorSetting, context).setUnitName(enumModel.getName());
	}
}
