package br.com.pixelforge.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloApiController {

    @Tag(name = "Hello", description = "A endpoint to verifier if the api is work when get then returns \"Hello, world!\"")
    @GetMapping
    public ResponseEntity<String> helloWorld(){
        return  ResponseEntity.ok("Hello, world!");
    }

}
