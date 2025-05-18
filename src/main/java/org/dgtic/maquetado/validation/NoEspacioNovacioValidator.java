package org.dgtic.maquetado.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoEspacioNovacioValidator implements ConstraintValidator<NoEspacioNoVacio,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null
                || s.regionMatches(0," ",0,1)
                || s.isBlank()){
            return false;
        }
        return true;
    }

}
