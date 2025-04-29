package my.projects.lockersystemusermicroservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ExistingUserEmailValidator.class)
public @interface ExistingUserEmail {

    String message() default "Email address does not exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

