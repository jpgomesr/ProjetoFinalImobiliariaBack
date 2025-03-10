package com.hav.imobiliaria.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
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
                String campo = violation.getPropertyPath().toString();
                String mensagem = violation.getMessage();
                bindingResult.addError(new FieldError(nomeObjeto, campo, mensagem));
            }

            throw new MethodArgumentNotValidException(getMethodParameter(classe), bindingResult);
        }
    }
    private <T> MethodParameter getMethodParameter(Class<T> classe) {
        try {
            // Supondo que o primeiro método do DTO seja um método de validação
            Method method = this.getClass().getDeclaredMethod("validaDTO", Class.class, Object.class, String.class);
            return new MethodParameter(method, 1); // Índice 1 = parâmetro "valor"
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Erro ao criar MethodParameter", e);
        }
    }

}
