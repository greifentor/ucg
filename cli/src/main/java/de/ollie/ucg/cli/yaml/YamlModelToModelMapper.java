package de.ollie.ucg.cli.yaml;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.cli.yaml.model.YamlAttribute;
import de.ollie.ucg.cli.yaml.model.YamlClassDefinition;
import de.ollie.ucg.cli.yaml.model.YamlModel;
import de.ollie.ucg.cli.yaml.model.YamlProperty;
import de.ollie.ucg.cli.yaml.model.YamlType;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Model;
import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.core.model.TypeModel;
import jakarta.inject.Named;
import java.util.List;

@Named
class YamlModelToModelMapper {

	Model map(YamlModel yamlModel) {
		ensure(yamlModel != null, "YAML model cannot be null!");
		return new Model().setClasses(getClasses(yamlModel)).updateReferences();
	}

	private List<ClassModel> getClasses(YamlModel yamlModel) {
		return yamlModel.getClasses() != null
			? yamlModel.getClasses().stream().map(this::getClassModel).toList()
			: List.of();
	}

	private ClassModel getClassModel(YamlClassDefinition classDefinition) {
		return new ClassModel()
			.setAttributes(getAttributes(classDefinition.getAttributes()))
			.setName(classDefinition.getName())
			.setProperties(getProperties(classDefinition.getProperties()));
	}

	private List<AttributeModel> getAttributes(List<YamlAttribute> attributes) {
		return attributes != null ? attributes.stream().map(this::getAttributeModel).toList() : List.of();
	}

	private AttributeModel getAttributeModel(YamlAttribute yamlAttribute) {
		return new AttributeModel()
			.setEnumType(yamlAttribute.isEnumType())
			.setName(yamlAttribute.getName())
			.setProperties(getProperties(yamlAttribute.getProperties()))
			.setReference(yamlAttribute.isReference())
			.setType(getTypeModel(yamlAttribute.getType()));
	}

	private TypeModel getTypeModel(YamlType yamlType) {
		return new TypeModel().setName(yamlType.getName()).setProperties(getProperties(yamlType.getProperties()));
	}

	private List<Property> getProperties(List<YamlProperty> properties) {
		return properties != null ? properties.stream().map(this::getProperty).toList() : List.of();
	}

	private Property getProperty(YamlProperty yamlProperty) {
		return new Property(yamlProperty.getName(), yamlProperty.getValue());
	}
}
