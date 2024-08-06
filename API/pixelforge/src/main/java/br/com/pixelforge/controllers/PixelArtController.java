package br.com.pixelforge.controllers;

import br.com.pixelforge.domain.DTOs.PixelArtDto;
import br.com.pixelforge.services.PixelArtServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pixel arts", description = "Endpoint for maneging Pixel Arts")
@RestController
@RequestMapping("/api/pixel-art/v1")
public class PixelArtController {

    private final PixelArtServices services;

    @Autowired
    public PixelArtController(PixelArtServices services) {
        this.services = services;
    }


    //non-authenticated


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
    public ResponseEntity<PixelArtDto> savePixelArt(
            @RequestBody PixelArtDto dto
            )
    {
        PixelArtDto pixelArte = this
                .services.createPixelArt(dto);

        return ResponseEntity.ok(pixelArte);
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

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PixelArtDto>>>
    findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "6") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sort = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, "name"));
        return ResponseEntity.ok(services.findAllPixelArts(pageable));
    }
}
