package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.core.exception.GenerationFailedException;
import de.ollie.ucg.core.exception.GenerationFailedException.Type;
import de.ollie.ucg.core.model.AttributeModel;
import de.ollie.ucg.core.model.ClassModel;
import de.ollie.ucg.core.model.Property;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassModelWrapper {

	private static final String MSG_NAME_IS_NULL = "name cannot be null!";

	@NonNull
	private final ClassModel model;

	public String getName() {
		return model.getName();
	}

	public List<AttributeModelWrapper> getAttributes() {
		return model.getAttributes().stream().map(a -> new AttributeModelWrapper(a)).toList();
	}

	public String getAttributeTypeNameByPropertyName(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return findAttributeWithProperty(name)
			.map(a -> a.getType().getName())
			.orElseThrow(() ->
				new GenerationFailedException(
					getName(),
					null,
					Type.NO_ATTRIBUTE_WITH_PROPERTY,
					List.of(new Property().setName("property").setValue("id"))
				)
			);
	}

	private Optional<AttributeModel> findAttributeWithProperty(String name) {
		return model.getAttributes().stream().filter(a -> a.hasProperty(name)).findFirst();
	}

	public List<AttributeModelWrapper> getAttributesWithPropertyNotSet(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return model
			.getAttributes()
			.stream()
			.filter(a -> !a.getProperties().isEmpty())
			.filter(a -> !a.hasProperty(name))
			.map(AttributeModelWrapper::new)
			.toList();
	}

	public String getNameSeparated(String separator) {
		return NameSeparator.INSTANCE.getNameSeparated(model.getName(), separator);
	}

	public boolean isPropertyWithNameInAttributesPresent(String name) {
		ensure(name != null, MSG_NAME_IS_NULL);
		return model.getAttributes().isEmpty()
			? false
			: getAllProperties().stream().anyMatch(p -> name.equals(p.getName()));
	}

	private List<Property> getAllProperties() {
		return model.getAttributes().stream().flatMap(a -> a.getProperties().stream()).toList();
	}
}
