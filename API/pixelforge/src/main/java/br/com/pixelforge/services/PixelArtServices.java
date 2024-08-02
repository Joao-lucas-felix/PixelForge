package br.com.pixelforge.services;

import br.com.pixelforge.controllers.StorageController;
import br.com.pixelforge.domain.DTOs.PixelArtDto;
import br.com.pixelforge.domain.PixelArt;
import br.com.pixelforge.domain.User;
import br.com.pixelforge.exceptions.FileStorageException;
import br.com.pixelforge.repositories.PixelArtRepository;
import br.com.pixelforge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PixelArtServices {
    private final PixelArtRepository pixelArtRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileServices;
    private final Logger logger = Logger.getLogger(PixelArtServices.class.getName());

    @Autowired
    public PixelArtServices(PixelArtRepository pixelArtRepository,
                            UserRepository userRepository,
                            FileStorageService fileServices) {
        this.pixelArtRepository = pixelArtRepository;
        this.userRepository = userRepository;
        this.fileServices = fileServices;
    }



    public boolean validateUser(){
        return true;
    }
    public boolean artFileExists(String originalFileName, String username){
        return  fileServices.artFileExists(originalFileName, username);
    }


    public PixelArtDto createPixelArt(PixelArtDto dto) {
        User user = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow( () -> new RuntimeException("Erro while tring to creates a pixel art."));


        PixelArt pixelArtToBePersisted = new PixelArt(dto, user);


        if (!this.artFileExists(dto.getOriginalFileName(), user.getUsername()))
            throw new FileStorageException("File Does Not Exists");

        pixelArtToBePersisted.setFilePath(dto.getOriginalFileName());
        PixelArt saved = pixelArtRepository.save(pixelArtToBePersisted);
        logger.info("Creating a Pixel Art with: name: "+saved.getName() +
                "description: "+ saved.getDescription() +"Is Free Use: " + saved.getIsFreeUse()
                + "This Pixel Art Is created By: " + saved.getUser().getUsername() );

        PixelArtDto pixelArtDto = new PixelArtDto();
        pixelArtDto.setName(saved.getName());
        pixelArtDto.setDescription(saved.getDescription());
        pixelArtDto.setIsFreeUse(saved.getIsFreeUse());
        pixelArtDto.setUserName(saved.getUser().getUsername());
        pixelArtDto.setOriginalFileName(saved.getFilePath());
        pixelArtDto.
                add(linkTo(methodOn(StorageController.class)
                        .downloadFile(pixelArtDto.getOriginalFileName(), null))
                        .withRel("Link to load this file"));
        return pixelArtDto;
    }
}
