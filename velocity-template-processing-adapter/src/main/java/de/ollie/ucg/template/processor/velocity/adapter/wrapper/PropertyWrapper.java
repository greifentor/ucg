package de.ollie.ucg.template.processor.velocity.adapter.wrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PropertyWrapper {

	private final String name;
	private final String value;
}
