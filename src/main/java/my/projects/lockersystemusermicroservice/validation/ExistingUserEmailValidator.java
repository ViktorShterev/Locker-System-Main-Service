package my.projects.lockersystemusermicroservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import my.projects.lockersystemusermicroservice.repository.UserRepository;

public class ExistingUserEmailValidator implements ConstraintValidator<ExistingUserEmail, String> {

    private final UserRepository userRepository;

    public ExistingUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.userRepository.findByEmail(value)
                .isPresent();
    }
}

