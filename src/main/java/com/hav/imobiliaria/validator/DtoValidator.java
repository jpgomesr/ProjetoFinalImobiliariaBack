package com.hav.imobiliaria.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

@Component
@AllArgsConstructor
public class DtoValidator {

    private final Validator validator;

    public  <T> void validaDTO(Class<T> classe, T valor, String nomeObjeto) throws MethodArgumentNotValidException {

        Set<ConstraintViolation<T>> violations = validator.validate(valor);
        if (!violations.isEmpty()) {
            BindingResult bindingResult = new BeanPropertyBindingResult(valor, nomeObjeto);
            for (ConstraintViolation<T> violation : violations) {
                bindingResult.rejectValue(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

    }

}
