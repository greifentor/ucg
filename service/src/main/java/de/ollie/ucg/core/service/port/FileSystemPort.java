package de.ollie.ucg.core.service.port;

import de.ollie.ucg.core.model.GeneratorConfiguration;

public interface FileSystemPort {
	void storeClassFile(GeneratorConfiguration generatorConfiguration, String classFileContent);
}
