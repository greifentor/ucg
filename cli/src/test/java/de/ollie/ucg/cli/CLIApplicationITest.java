package de.ollie.ucg.cli;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled // TODO OLI: Make it run ...
@SpringBootTest(args = { "config" })
class CLIApplicationITest {

	@Test
	void applicationStarts() {
		// NOP
	}
}
