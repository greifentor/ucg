package de.ollie.ucg.core.model;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.NameSeparator;
import de.ollie.ucg.core.exception.GenerationFailedException;
import de.ollie.ucg.core.exception.GenerationFailedException.Type;
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
	private List<AttributeModel> attributes;
	private List<Property> properties;

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
		return getAttributes().stream().filter(a -> a.hasProperty(name)).findFirst();
	}

	public String getAttributeNameWithPropertySeparated(String name) {
		return NameSeparator.INSTANCE.getNameSeparated(getAttributeTypeNameByPropertyName(name), "_");
	}
}
