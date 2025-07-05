package de.ollie.ucg.core.service.impl;

import de.ollie.ucg.core.service.LocalTimeFactory;
import jakarta.inject.Named;
import java.time.LocalTime;

@Named
public class LocalTimeFactoryImpl implements LocalTimeFactory {

	@Override
	public LocalTime now() {
		return LocalTime.now();
	}
}
