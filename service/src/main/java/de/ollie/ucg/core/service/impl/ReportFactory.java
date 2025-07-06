package de.ollie.ucg.core.service.impl;

import de.ollie.ucg.core.model.Report;
import jakarta.inject.Named;

@Named
public class ReportFactory {

	Report create() {
		return new Report();
	}
}
