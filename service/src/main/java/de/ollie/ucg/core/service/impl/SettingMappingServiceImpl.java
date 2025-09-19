package de.ollie.ucg.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.ucg.core.service.SettingMappingService;
import jakarta.inject.Named;

@Named
public class SettingMappingServiceImpl implements SettingMappingService {

	public static final String REPLACER_UNIT_NAME = "${UnitName}";
	public static final String REPLACER_UNIT_NAME_LOWER_CASE = "${UnitNameLowerCase}";

	@Override
	public String map(String setting, String unitName) {
		ensure(setting != null, "setting cannot be null!");
		ensure(unitName != null, "unit name cannot be null!");
		return setting.replace(REPLACER_UNIT_NAME, unitName).replace(REPLACER_UNIT_NAME_LOWER_CASE, unitName.toLowerCase());
	}
}
