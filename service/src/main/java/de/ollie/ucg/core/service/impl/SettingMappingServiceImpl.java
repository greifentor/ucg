package de.ollie.ucg.core.service.impl;

import de.ollie.ucg.core.service.SettingMappingService;
import jakarta.inject.Named;

@Named
public class SettingMappingServiceImpl implements SettingMappingService {

	@Override
	public String map(String setting, String unitName) {
		return setting.replace("${UnitName}", unitName).replace("${UnitNameLowerCase}", unitName.toLowerCase());
	}
}
