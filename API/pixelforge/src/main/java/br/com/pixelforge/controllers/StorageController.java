package br.com.pixelforge.controllers;

import br.com.pixelforge.domain.DTOs.UploadArtFileResponseDto;
import br.com.pixelforge.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename, HttpServletRequest request) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Reading a file on disk");

        Resource resource = service.loadFileAsResource(filename, userName);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Could not determine file type!");
        }

        if (contentType.isBlank()) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
