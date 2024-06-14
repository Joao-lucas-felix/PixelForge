package br.com.pixelforge.controllers;

import br.com.pixelforge.domain.DTOs.UploadArtFileResponseDto;
import br.com.pixelforge.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/storage-pixel-art/v1")
public class StorageController {
    private final Logger logger = Logger.getLogger(PixelArtController.class.getName());
    @Autowired
    private FileStorageService service;
    @PostMapping(value = "/upload")
    public ResponseEntity<UploadArtFileResponseDto> upload(
            @RequestParam("file") MultipartFile file
    ){
        //Getting the user with the security context
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        logger.info("Storing a file in disk.");

        String fileName = service.storageFile(file,userName );
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/file/v1/download/"+fileName).toUriString();

        return ResponseEntity.ok(new UploadArtFileResponseDto(
                fileName, fileDownloadUri, file.getContentType(), file.getSize()
        ));
    }

}
