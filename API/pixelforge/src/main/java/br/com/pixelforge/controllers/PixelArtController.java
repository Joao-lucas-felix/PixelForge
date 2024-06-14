package br.com.pixelforge.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Tag(name = "Pixel arts", description = "Endpoint for maneging Pixel Arts")
@RestController
@RequestMapping("/api/pixel-art/v1")
public class PixelArtController {

    private final Logger logger = Logger.getLogger(PixelArtController.class.getName());
    //non-authenticated
    @GetMapping
    public ResponseEntity<String> getNewPixelArts(){
        return ResponseEntity.ok("Get Works!");
    }

    //non-authenticated
    @GetMapping("/searchByName")
    public ResponseEntity<String> getArtByName(
            @PathParam("name")String name
    )
    {
        return ResponseEntity.ok("Get by name Wokrs! name in path "+name);
    }

    //authenticated
    @PostMapping
    public ResponseEntity<String> savePixelArt(
            // body with the data
    )
    {
        return ResponseEntity.ok("Post works!");
    }


    //authenticated and be the owner
    @PutMapping
    public ResponseEntity<String> updatePixelArt(
        //Request Body with the data and an art file (the file for update is optional).
    ){
        return ResponseEntity.ok("Put works!");
    }


    //authenticated and be the owner or admim
    @DeleteMapping
    public ResponseEntity<String> deletePixelArt(
            // Unique Indentifier of a pixel art
    ){
        return ResponseEntity.ok("Delete Works");
    }

}
