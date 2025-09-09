package de.ollie.ucg.core.service.impl;

import jakarta.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
class HashMapFactory {

	Map<String, Object> createMapStringObject() {
		return new HashMap<>();
	}
}
