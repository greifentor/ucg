package de.ollie.ucg.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ModelTest {

	private static final String ID = "id";
	private static final String REFERENCED_TABLE = "referenced-table";
	private static final String REFERENCING_ATTRIBUTE = "referencing-attribute";

	@InjectMocks
	private Model unitUnderTest;

	@Nested
	class updateReferences {

		@Test
		void happyRun() {
			// Prepare
			ClassModel referencedClass = new ClassModel()
				.setAttributes(
					List.of(
						new AttributeModel().setName(ID).setProperties(List.of(new Property().setName("id").setValue("true")))
					)
				)
				.setName(REFERENCED_TABLE);
			ClassModel referencingClass = new ClassModel()
				.setAttributes(
					List.of(
						new AttributeModel()
							.setName(REFERENCING_ATTRIBUTE)
							.setReference(true)
							.setType(new TypeModel().setName(REFERENCED_TABLE))
					)
				);
			List<ClassModel> classes = List.of(referencedClass, referencingClass);
			Model model = new Model().setClasses(classes);
			// Run
			model.updateReferences();
			// Check
			assertEquals(referencedClass, referencingClass.getAttributes().get(0).getType().getClassType());
		}
	}
}
