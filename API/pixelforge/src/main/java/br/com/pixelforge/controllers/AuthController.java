package br.com.pixelforge.controllers;

import br.com.pixelforge.domain.DTOs.TokenDto;
import br.com.pixelforge.domain.DTOs.UserCredentialsDto;
import br.com.pixelforge.services.AuthServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthServices service;
    @PostMapping(value = "/login")
    @Tag(name = "Auth", description = "EndPoint for Anthenticate users.")
    public ResponseEntity<TokenDto> login
            (@RequestBody UserCredentialsDto dto)
    {
        return service.login(dto);
    }
}
