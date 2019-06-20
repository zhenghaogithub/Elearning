package org.darod.elearning.common.validator;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:14
 */
public class ValidatorImpl  {
    private Validator validator;

    //实现校验方法
    public ValidationResult validate(Object bean) {
        final ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            //有错误
            validationResult.setHasError(true);
            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                validationResult.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return validationResult;
    }

    public static ValidatorImpl getInstance(){
        ValidatorImpl validator = new ValidatorImpl();
        validator.validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator;
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //将hibernate validator通过工厂初始化
//        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }
}
