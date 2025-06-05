package com.example.demo.Service.validator;

import com.example.demo.Service.UserService;
import com.example.demo.domain.dto.RegisterDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

    private  final UserService userService;


    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }
    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        if(!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("password does not match confirm password")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;


        }

        //check email
        if(this.userService.checkEmailExists(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("email đã tồn tại")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
