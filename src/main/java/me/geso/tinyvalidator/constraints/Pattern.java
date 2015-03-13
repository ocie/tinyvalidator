package me.geso.tinyvalidator.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.geso.tinyvalidator.Constraint;
import me.geso.tinyvalidator.rules.PatternConstraintValidator;

/**
 * The annotated String must match the following regular expression. The regular
 * expression follows the Java regular expression conventions see Pattern.
 * Accepts String. null elements are considered valid.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = PatternConstraintValidator.class)
public @interface Pattern {
	/**
	 * Regular expression pattern.
	 */
	String regexp();

	String message() default "must match {regexp}";

	int flags() default 0;
}
