package br.com.chris.cartuchos.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.chris.cartuchos.model.NegocioException;

@ControllerAdvice
public class NegocioExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> erroDeNegocio(Exception ex, WebRequest request) {
		NegocioException ne = (NegocioException) ex;
        return new ResponseEntity<>(ne.getErroMap(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
