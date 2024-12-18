package br.com.pixelforge.controllers;

import br.com.pixelforge.domain.DTOs.FileInfoDto;
import br.com.pixelforge.domain.DTOs.PixelArtDto;
import br.com.pixelforge.domain.DTOs.UploadArtFileResponseDto;
import br.com.pixelforge.services.FileStorageService;
import br.com.pixelforge.services.PixelArtServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/pixel-art/v1")
public class PixelArtController {
    @Autowired
    private FileStorageService service;
    @Autowired
    private PixelArtServices pixelArtServices;


    @PostMapping
    public ResponseEntity<UploadArtFileResponseDto> createPixelArt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("isFreeUse") Boolean isFreeUse
    ){
        log.info("Create an Pixel Art");

        //Getting the user with the security context
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("Storing the file in disk.");

        String fileName = service.storageFile(file,userName );
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/file/v1/download/"+fileName).toUriString();

        var fileInfoDto = new FileInfoDto(fileName,
                fileDownloadUri, file.getContentType(), file.getSize());

        log.info("Storing the pixel art info in the DataBase");
        log.info("Name {}", name);
        log.info("Description {}", description);
        log.info("Is Free Use {}", isFreeUse);

        var createDto = new PixelArtDto(null,  name, description,
                file.getOriginalFilename(), isFreeUse, userName);
        var pixelArtResponseDto = pixelArtServices.createPixelArt(createDto);

        return ResponseEntity.ok(new UploadArtFileResponseDto(
                 pixelArtResponseDto, fileInfoDto
        ));

    }

    @PutMapping("/{id}/file")
    public ResponseEntity<UploadArtFileResponseDto> updatePixelArt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("isFreeUse") Boolean isFreeUse,
            @PathVariable("id") Long id
    ){
        log.info("Update an Pixel art with File, ID: {}",id);

        //Getting the user with the security context
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var oldFileName = pixelArtServices.getOldFileName(id);
        service.deleteAnFile(oldFileName, userName);
        log.info("Storing the new file in disk.");

        String fileName = service.storageFile(file,userName );
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/file/v1/download/"+fileName).toUriString();

        var fileInfoDto = new FileInfoDto(fileName,
                fileDownloadUri, file.getContentType(), file.getSize());

        log.info("Storing the pixel art info in the DataBase");
        log.info("Name {}", name);
        log.info("Description {}", description);
        log.info("Is Free Use {}", isFreeUse);

        var createDto = new PixelArtDto(id,  name, description,
                file.getOriginalFilename(), isFreeUse, userName);
        var pixelArtResponseDto = pixelArtServices.updateWithFile(createDto);

        return ResponseEntity.ok(new UploadArtFileResponseDto(
                pixelArtResponseDto, fileInfoDto
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PixelArtDto> updatePixelArt(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("isFreeUse") Boolean isFreeUse,
            @PathVariable("id") Long id
    ){
        log.info("Update an pixel art without file! ID: {}",id);
        PixelArtDto pixelArtDto = pixelArtServices.updateWithoutFile(id, name, description, isFreeUse);
        return ResponseEntity.ok(pixelArtDto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PixelArtDto>>>
    findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "6") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sort = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, "name"));
        return ResponseEntity.ok(pixelArtServices.findAllPixelArts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PixelArtDto>
    findById(@PathVariable Long id) {
        return ResponseEntity.ok(pixelArtServices.findById(id));
    }


    @GetMapping("/searchByName/{name}")
    public ResponseEntity<PagedModel<EntityModel<PixelArtDto>>>
    findByName(@PathVariable String name,
               @RequestParam(value = "page", defaultValue = "0") Integer page,
               @RequestParam(value = "size", defaultValue = "6") Integer size,
               @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sort = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, "name"));
        return ResponseEntity.ok(pixelArtServices.findByName(name, pageable));
    }




    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename, HttpServletRequest request) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Reading a file on disk");

        Resource resource = service.loadFileAsResource(filename, userName);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            log.info("Could not determine file type!");
        }

        if (contentType.isBlank()) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@pixelArtServices.canChangeEntity(#id, authentication.name, authentication.authorities)")
    public ResponseEntity<String> deletePixelArt(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(pixelArtServices.delete(id));
    }
}
