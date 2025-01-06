package br.com.pixelforge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundPixelArtException
        extends AuthenticationException
    {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundPixelArtException(String message) {
        super(message);
    }
}
