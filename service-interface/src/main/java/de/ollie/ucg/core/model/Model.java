package de.ollie.ucg.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class Model {

	private List<ClassModel> classes;
	private Map<String, Property> properties;

	public Model updateReferences() {
		Map<String, ClassModel> m = new HashMap<>();
		classes.forEach(c -> m.put(c.getName(), c));
		classes.forEach(c ->
			c
				.getAttributes()
				.stream()
				.filter(AttributeModel::isReference)
				.forEach(a -> a.getType().setClassType(m.get(a.getType().getName())))
		);
		return this;
	}
}
