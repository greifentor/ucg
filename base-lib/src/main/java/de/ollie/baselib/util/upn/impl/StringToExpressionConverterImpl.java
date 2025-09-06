package de.ollie.baselib.util.upn.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.baselib.util.upn.StringToExpressionConverter;
import de.ollie.baselib.util.upn.WordFactory;
import de.ollie.baselib.util.upn.impl.model.Expression;
import de.ollie.baselib.util.upn.impl.model.Value;
import de.ollie.baselib.util.upn.impl.model.Word;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringTokenizer;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;

@Named
@RequiredArgsConstructor
public class StringToExpressionConverterImpl implements StringToExpressionConverter {

	private final WordFactory wordFactory;

	@Override
	public Expression convert(String expression) {
		ensure(expression != null, "expression to convert cannot be null!");
		List<Word> words = new ArrayList<Word>();
		StringTokenizer tokenizer = new StringTokenizer(expression, " ");
		StringMatcher sm = StringMatcherFactory.INSTANCE.quoteMatcher();
		tokenizer.setQuoteMatcher(sm);
		while (tokenizer.hasNext()) {
			String token = tokenizer.nextToken();
			if (wordFactory.isCommand(token)) {
				words.add(wordFactory.createCommand(token));
			} else {
				words.add(Value.of(getValue(token)));
			}
		}
		return Expression.of(words);
	}

	Object getValue(String token) {
		if (token.toUpperCase().equals("FALSE") || token.toUpperCase().equals("TRUE")) {
			return Boolean.valueOf(token.toUpperCase());
		}
		try {
			return Integer.valueOf(token);
		} catch (NumberFormatException nfe) {
			// NOP
		}
		return token;
	}
}
