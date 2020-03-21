package com.learn.seckill.ValidData;
import com.learn.seckill.Until.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    private boolean required =false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.requied();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(s);
        }else{
            if(s.isEmpty()){
                return true;
            }else{
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
