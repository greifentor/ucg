package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettingMappingServiceImplTest {

	private static final String SETTING_PREFIX = "setting with ";
	private static final String SETTING_POSTFIX = " replaced";
	private static final String SETTING_NO_REPLACERS = "setting no replacers";
	private static final String SETTING_WITH_REPLACER =
		SETTING_PREFIX + SettingMappingServiceImpl.REPLACER_UNIT_NAME + SETTING_POSTFIX;
	private static final String SETTING_WITH_REPLACER_LOWER_CASE =
		SETTING_PREFIX + SettingMappingServiceImpl.REPLACER_UNIT_NAME_LOWER_CASE + SETTING_POSTFIX;
	private static final String UNIT_NAME = "Unit-Name";

	@InjectMocks
	private SettingMappingServiceImpl unitUnderTest;

	@Nested
	class map_String_String {

		@Test
		void throwsAnException_passingANullValue_asSetting() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.map(null, UNIT_NAME));
		}

		@Test
		void throwsAnException_passingANullValue_asUnitName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.map(SETTING_WITH_REPLACER, null));
		}

		@Test
		void replacesNothing_whenSettingDoesNotContainAnyReplacers() {
			assertEquals(SETTING_NO_REPLACERS, unitUnderTest.map(SETTING_NO_REPLACERS, UNIT_NAME));
		}

		@Test
		void replacesToPassedUnitName_whenSettingContainsAReplacers() {
			assertEquals(SETTING_PREFIX + UNIT_NAME + SETTING_POSTFIX, unitUnderTest.map(SETTING_WITH_REPLACER, UNIT_NAME));
		}

		@Test
		void replacesToPassedUnitName_whenSettingContainsALowerCaseReplacers() {
			assertEquals(
				SETTING_PREFIX + UNIT_NAME.toLowerCase() + SETTING_POSTFIX,
				unitUnderTest.map(SETTING_WITH_REPLACER_LOWER_CASE, UNIT_NAME)
			);
		}
	}
}
