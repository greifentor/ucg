package de.ollie.ucg.core.upn;

import de.ollie.baselib.util.upn.CommandFactory;
import de.ollie.ucg.core.upn.command.Load;
import jakarta.inject.Named;

@Named
class LoadCommandFactory implements CommandFactory<Load> {

	@Override
	public Load create() {
		return new Load();
	}
}
