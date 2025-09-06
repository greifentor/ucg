package de.ollie.ucg.cli.upn;

import de.ollie.baselib.util.upn.CommandFactory;
import de.ollie.ucg.cli.upn.command.HasProperty;
import jakarta.inject.Named;

@Named
class HasPropertyCommandFactory implements CommandFactory<HasProperty> {

	@Override
	public HasProperty create() {
		return new HasProperty();
	}
}
