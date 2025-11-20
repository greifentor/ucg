package de.ollie.ucg.core.model;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.NameSeparator;
import de.ollie.ucg.core.exception.GenerationFailedException;
import de.ollie.ucg.core.exception.GenerationFailedException.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.Generated;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
@ToString
public class ClassModel implements PropertyOwner {

	private static final String MSG_NAME_IS_NULL = "name cannot be null!";

	private String name;
	private List<AttributeModel> attributes = new ArrayList<>();
	private List<Property> properties = new ArrayList<>();

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

	public Optional<AttributeModel> findAttributeWithProperty(String name) {
		return getAttributes().stream().filter(a -> a.hasProperty(name)).findFirst();
	}

	public Optional<String> findPropertyValue(String propertyName) {
		ensure(propertyName != null, "property name cannot be null!");
		return getProperties().stream().filter(p -> p.getName().equals(propertyName)).map(Property::getValue).findFirst();
	}

	public String getAttributeNameWithPropertySeparated(String name) {
		return NameSeparator.INSTANCE.getNameSeparated(getAttributeTypeNameByPropertyName(name), "_");
	}
}
