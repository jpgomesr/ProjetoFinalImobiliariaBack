package com.hav.imobiliaria.controller.common;

import com.hav.imobiliaria.controller.dto.exception.ErroCampo;
import com.hav.imobiliaria.controller.dto.exception.ErroResposta;
import com.hav.imobiliaria.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldError = e.getFieldErrors();
        List<ErroCampo> listaDeErros =
                fieldError.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).toList();

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação", listaDeErros);
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage(),
                List.of(new ErroCampo(e.getMessage(), e.getCampo())));
    }
    @ExceptionHandler(ProprietarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleProprietarioNaoEncontradoException(ProprietarioNaoEncontradoException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(),List.of());
    }

    @ExceptionHandler(ImovelNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleImovelNaoEncontradoExcpetion(ProprietarioNaoEncontradoException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleNoSuchElementException(NoSuchElementException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException e){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(ChatJaCadastradoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleChatJaCadastradoException(ChatJaCadastradoException e) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(ChatNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleChatNaoEncontradoException(ChatNaoEncontradoException e) {
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

//    @ExceptionHandler(EmailJaCadastradoException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErroResposta handelEmailJaCadastradoException(EmailJaCadastradoException e){
//        return new ErroResposta(HttpStatus.CONTINUE.value(), e.getMessage(), List.of());
//    }

//    @ExceptionHandler(CPFJaCadastradoException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErroResposta handleCPFJaCadastradoException(CPFJaCadastradoException e){
//        return new ErroResposta(HttpStatus.CONTINUE.value(), e.getMessage(), List.of());
//    }

//    @ExceptionHandler(TelefoneJaCadastradoException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErroResposta handleTelefoneJaCadastradoException(TelefoneJaCadastradoException e){
//        return new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage(), List.of());
//    }

    @ExceptionHandler(UsuarioJaCadastradoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleUsuarioJaCadastradoException(UsuarioJaCadastradoException e){
        return new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage(),List.of());
    }



}
