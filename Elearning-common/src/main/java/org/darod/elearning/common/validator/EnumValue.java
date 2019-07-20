package org.darod.elearning.common.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/9 0009 22:49
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {
    String message() default "{字段枚举值不合法}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

//    String enumMethod();

    class Validator implements ConstraintValidator<EnumValue, String> {

            private Class<? extends Enum<?>> enumClass;
//        private String enumMethod;

            @Override
            public void initialize(EnumValue enumValue) {
//            enumMethod = enumValue.enumMethod();
                enumClass = enumValue.enumClass();
            }

            @Override
            public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
                if (value == null) {
                    return Boolean.TRUE;
                }
                if (enumClass == null) {
                    return Boolean.TRUE;
                }

                Class<?> valueClass = value.getClass();
                try {
                    Object[] objs = enumClass.getEnumConstants();
                    Method method = enumClass.getMethod("name");
                    for (Object obj : objs) {
                        Object code = method.invoke(obj, null);
                        if (value.equalsIgnoreCase(code.toString())) {
                            return true;
                        }
                    }
                    return false;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }


//            try {
//                Method method = enumClass.getMethod(enumMethod, valueClass);
//                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
//                    throw new RuntimeException(Strings.formatIfArgs("%s method return is not boolean type in the %s class", enumMethod, enumClass));
//                }
//
//                if(!Modifier.isStatic(method.getModifiers())) {
//                    throw new RuntimeException(Strings.formatIfArgs("%s method is not static method in the %s class", enumMethod, enumClass));
//                }
//
//                Boolean result = (Boolean)method.invoke(null, value);
//                return result == null ? false : result;
//            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (NoSuchMethodException | SecurityException e) {
//                throw new RuntimeException(Strings.formatIfArgs("This %s(%s) method does not exist in the %s", enumMethod, valueClass, enumClass), e);
//            }
                return false;
            }

    }
}
