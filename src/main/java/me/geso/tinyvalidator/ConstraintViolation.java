package me.geso.tinyvalidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.SneakyThrows;
import lombok.ToString;

/**
 * Constraint violation class.
 */
@ToString
public class ConstraintViolation {
	private final Object value;
	private final Annotation annotation;
	private final String name;

	private static final Pattern pattern = Pattern.compile("\\{([^}]+)\\}");

	ConstraintViolation(Object value,
			Annotation annotation,
			String name) {
		this.value = value;
		this.annotation = annotation;
		this.name = name;
	}

	/**
	 * Get the validation message.
	 */
	@SneakyThrows
	public String getMessage() {
		// ref.
		// https://svn.apache.org/repos/asf/tapestry/tapestry5/trunk/tapestry-beanvalidator/src/test/resources/ValidationMessages_en.properties
		Method method = annotation.getClass()
			.getMethod("message");
		String messageTemplate = (String)method.invoke(annotation);
		StringBuffer resultString = new StringBuffer();
		final Matcher matcher = pattern.matcher(messageTemplate);
		while (matcher.find()) {
			final MatchResult matchResult = matcher.toMatchResult();
			final String methodName = matchResult.group(1);
			final String value = annotation.getClass()
				.getMethod(methodName).invoke(annotation)
				.toString();
			matcher.appendReplacement(resultString,
				Matcher.quoteReplacement(value));
		}
		matcher.appendTail(resultString);
		return resultString.toString();
	}

	/**
	 * Get the value validated.
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Get the parameter name.
	 */
	public String getName() {
		return name;
	}

}
