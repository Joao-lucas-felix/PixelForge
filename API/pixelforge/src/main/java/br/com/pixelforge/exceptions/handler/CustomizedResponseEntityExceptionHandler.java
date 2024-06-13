package br.com.pixelforge.exceptions.handler;

import br.com.pixelforge.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler
extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(Exception.class) //Trata exeções geneircas.
    public final ResponseEntity<ExceptionResponse>
    handleAllException(Exception ex, WebRequest wr){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                wr.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ExceptionResponse> handleAuthenticationErrorResponseException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                "Invalid or Expired Token",
                "Problem in authentication."
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

}
