package de.ollie.ucg.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.baselib.util.upn.ExpressionEvaluator;
import de.ollie.baselib.util.upn.StringToExpressionConverter;
import de.ollie.baselib.util.upn.impl.model.Expression;
import de.ollie.ucg.core.model.ClassModel;
import java.util.Map;
import java.util.Stack;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpnEvaluationPortImplTest {

	private static final String EXPRESSION = "expression";

	@Mock
	private ClassModel classModel;

	@Mock
	private Expression expression;

	@Mock
	private ExpressionEvaluator expressionEvaluator;

	@Mock
	private HashMapFactory hashMapFactory;

	@Mock
	private Stack<Object> resultStack;

	@Mock
	private Map<String, Object> valueStore;

	@Mock
	private StringToExpressionConverter stringToExpressionConverter;

	@InjectMocks
	private UpnEvaluationPortImpl unitUnderTest;

	@Nested
	class evaluate_String_ClassModel {

		@Test
		void throwsAnException_passingANullValue_asClassModel() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.evaluate(EXPRESSION, null));
		}

		@Test
		void throwsAnException_passingANullValue_asExpression() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.evaluate(null, classModel));
		}

		@Test
		void setsClassModelToValueStore() {
			// Prepare
			when(hashMapFactory.createMapStringObject()).thenReturn(valueStore);
			// Run
			unitUnderTest.evaluate(EXPRESSION, classModel);
			// Check
			verify(valueStore, times(1)).put("classModel", classModel);
		}

		@Test
		void returnsTheReturnOfTheExpressionEvaluatorCall() {
			// Prepare
			when(expressionEvaluator.evaluate(expression, valueStore)).thenReturn(resultStack);
			when(hashMapFactory.createMapStringObject()).thenReturn(valueStore);
			when(stringToExpressionConverter.convert(EXPRESSION)).thenReturn(expression);
			// Run & Check
			assertSame(resultStack, unitUnderTest.evaluate(EXPRESSION, classModel));
		}
	}
}
