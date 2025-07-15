package de.ollie.ucg.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Generated
@NoArgsConstructor
public class Property {

	private String name;
	private Object value;
}
