package br.com.pixelforge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidTokenAuthenticationException
        extends AuthenticationException
        implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidTokenAuthenticationException(String message) {
        super(message);
    }
}
