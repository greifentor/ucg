package de.ollie.ucg.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class ImportModel {

	private String content;
	private boolean staticImport;
}
