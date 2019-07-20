package org.darod.elearning.common.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/17 0017 13:41
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyNotBlank.Validator.class)
public @interface MyNotBlank {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<MyNotBlank, String> {

        @Override
        public void initialize(MyNotBlank enumValue) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) { //字段为null时合法
                return true;
            }else {
                if(value.trim().equals("")){ //字段不为null时，如果为空字符串不合法
                    return false;
                }
            }
            return true;
        }

    }
}
