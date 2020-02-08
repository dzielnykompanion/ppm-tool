package io.agileinteligence.ppmtool.validator;


import io.agileinteligence.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        if(user.getPassword().length() < 4) {
            errors.rejectValue("password", "Length", "Password must nbe at least 4 characters");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())){
             errors.rejectValue("confirmPassword", "Match", "Password must match");
         }

        // confirmPassword

    }
}
