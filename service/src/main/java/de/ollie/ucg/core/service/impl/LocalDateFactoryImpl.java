package de.ollie.ucg.core.service.impl;

import de.ollie.ucg.core.service.LocalDateFactory;
import jakarta.inject.Named;
import java.time.LocalDate;

@Named
public class LocalDateFactoryImpl implements LocalDateFactory {

	@Override
	public LocalDate now() {
		return LocalDate.now();
	}
}
