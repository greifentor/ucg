package de.ollie.ucg.core.service.impl;

import de.ollie.ucg.core.service.UuidFactory;
import jakarta.inject.Named;
import java.util.UUID;

@Named
class UuidFactoryImpl implements UuidFactory {

	@Override
	public UUID create() {
		return UUID.randomUUID();
	}
}
