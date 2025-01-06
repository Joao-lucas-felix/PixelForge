package br.com.pixelforge.services;

import br.com.pixelforge.controllers.PixelArtController;
import br.com.pixelforge.domain.DTOs.PixelArtDto;
import br.com.pixelforge.domain.PixelArt;
import br.com.pixelforge.domain.User;
import br.com.pixelforge.exceptions.FileStorageException;
import br.com.pixelforge.exceptions.NotFoundPixelArtException;
import br.com.pixelforge.repositories.PixelArtRepository;
import br.com.pixelforge.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Slf4j
@Service
public class PixelArtServices {
    private final PixelArtRepository pixelArtRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileServices;
    private final PagedResourcesAssembler<PixelArtDto> assembler;

    public PixelArtServices(PixelArtRepository pixelArtRepository,
                            UserRepository userRepository,
                            FileStorageService fileServices,
                            PagedResourcesAssembler<PixelArtDto> assembler) {
        this.pixelArtRepository = pixelArtRepository;
        this.userRepository = userRepository;
        this.fileServices = fileServices;
        this.assembler = assembler;
    }
    //Post methods
    public PixelArtDto createPixelArt(PixelArtDto dto) {
        User user = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow( () -> new RuntimeException("Erro while tring to creates a pixel art."));


        PixelArt pixelArtToBePersisted = new PixelArt(dto, user);


        if (!this.artFileExists(dto.getOriginalFileName(), user.getUsername()))
            throw new FileStorageException("File Does Not Exists");

        pixelArtToBePersisted.setFilePath(dto.getOriginalFileName());
        PixelArt saved = pixelArtRepository.save(pixelArtToBePersisted);
        log.info("Creating a Pixel Art with: name: "+saved.getName() +
                "description: "+ saved.getDescription() +"Is Free Use: " + saved.getIsFreeUse()
                + "This Pixel Art Is created By: " + saved.getUser().getUsername() );

        PixelArtDto pixelArtDto = new PixelArtDto();
        pixelArtDto.setKey(saved.getId());
        pixelArtDto.setName(saved.getName());
        pixelArtDto.setDescription(saved.getDescription());
        pixelArtDto.setIsFreeUse(saved.getIsFreeUse());
        pixelArtDto.setUserName(saved.getUser().getUsername());
        pixelArtDto.setOriginalFileName(saved.getFilePath());
        pixelArtDto.
                add(linkTo(methodOn(PixelArtController.class)
                        .downloadFile(pixelArtDto.getOriginalFileName(), null))
                        .withRel("Link to load this file"));
        return pixelArtDto;
    }
    //Get Methods
    public PagedModel<EntityModel<PixelArtDto>> findAllPixelArts(Pageable pageable){
        log.info("Finding all pixel arts");
        Page<PixelArt> pixelArtsPage = pixelArtRepository.findAll(pageable);

        Page<PixelArtDto> dtosPage = pixelArtsPage.map(pixelArt -> {
            PixelArtDto pixelArtDto = new PixelArtDto();
            pixelArtDto.setKey(pixelArt.getId());
            pixelArtDto.setName(pixelArt.getName());
            pixelArtDto.setDescription(pixelArt.getDescription());
            pixelArtDto.setIsFreeUse(pixelArt.getIsFreeUse());
            pixelArtDto.setUserName(pixelArt.getUser().getUsername());
            pixelArtDto.setOriginalFileName(pixelArt.getFilePath());
            pixelArtDto.
                    add(linkTo(methodOn(PixelArtController.class)
                            .downloadFile(pixelArtDto.getOriginalFileName(), null))
                            .withRel("Link to load this file"));
            pixelArtDto.
                    add(linkTo(methodOn(PixelArtController.class)
                            .findById(pixelArt.getId()))
                            .withSelfRel());
            return pixelArtDto;
        });

        //dtosPage.map(dto -> dto.add()) self relation to add later

        Link link = linkTo(methodOn(PixelArtController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtosPage, link);
    }

    public PixelArtDto findById(Long id) {
        log.info("Finding a pixel art with ID: {}", id);

        Optional<PixelArt> byId = pixelArtRepository.findById(id);

        PixelArt pixelArt = byId
                .orElseThrow(() -> new NotFoundPixelArtException("Not found Pixel art with this ID !"));

        PixelArtDto pixelArtDto = new PixelArtDto();
        pixelArtDto.setKey(pixelArt.getId());
        pixelArtDto.setName(pixelArt.getName());
        pixelArtDto.setDescription(pixelArt.getDescription());
        pixelArtDto.setIsFreeUse(pixelArt.getIsFreeUse());
        pixelArtDto.setUserName(pixelArt.getUser().getUsername());
        pixelArtDto.setOriginalFileName(pixelArt.getFilePath());
        pixelArtDto.
                add(linkTo(methodOn(PixelArtController.class)
                        .downloadFile(pixelArtDto.getOriginalFileName(), null))
                        .withRel("Link to load this file"));
        pixelArtDto.
                add(linkTo(methodOn(PixelArtController.class)
                        .findById(pixelArt.getId()))
                        .withSelfRel());
        return pixelArtDto;
    }


    private boolean artFileExists(String originalFileName, String username){
        return  fileServices.artFileExists(originalFileName, username);
    }

    public PagedModel<EntityModel<PixelArtDto>> findByName(String name, Pageable pageable) {
        log.info("Finding all pixel arts");
        Page<PixelArt> pixelArtsPage = pixelArtRepository.findByName(name, pageable);

        Page<PixelArtDto> dtosPage = pixelArtsPage.map(pixelArt -> {
            PixelArtDto pixelArtDto = new PixelArtDto();
            pixelArtDto.setKey(pixelArt.getId());
            pixelArtDto.setName(pixelArt.getName());
            pixelArtDto.setDescription(pixelArt.getDescription());
            pixelArtDto.setIsFreeUse(pixelArt.getIsFreeUse());
            pixelArtDto.setUserName(pixelArt.getUser().getUsername());
            pixelArtDto.setOriginalFileName(pixelArt.getFilePath());
            pixelArtDto.
                    add(linkTo(methodOn(PixelArtController.class)
                            .downloadFile(pixelArtDto.getOriginalFileName(), null))
                            .withRel("Link to load this file"));
            pixelArtDto.
                    add(linkTo(methodOn(PixelArtController.class)
                            .findById(pixelArt.getId()))
                            .withSelfRel());
            return pixelArtDto;
        });

        //dtosPage.map(dto -> dto.add()) self relation to add later

        Link link = linkTo(methodOn(PixelArtController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtosPage, link);
    }

    public PixelArtDto updateWithoutFile(Long id, String name, String description, Boolean isFreeUse) {
       log.info("Get the pixel art with ID: {} in the database", id);
        Optional<PixelArt> byId = pixelArtRepository.findById(id);
        PixelArt pixelArt = byId.orElseThrow(
                () -> new NotFoundPixelArtException("Not found the pixel art with this id !")
        );

        log.info("Updating the art! ");
        pixelArt.setName(name);
        pixelArt.setDescription(description);
        pixelArt.setIsFreeUse(isFreeUse);

        PixelArt saved = pixelArtRepository.save(pixelArt);

        PixelArtDto response = new PixelArtDto(saved.getId(), saved.getName(),
        saved.getDescription(), saved.getFilePath(), saved.getIsFreeUse(),
                saved.getUser().getUsername());
        response.
                add(linkTo(methodOn(PixelArtController.class)
                        .downloadFile(response.getOriginalFileName(), null))
                        .withRel("Link to load this file"));
        response.
                add(linkTo(methodOn(PixelArtController.class)
                        .findById(saved.getId()))
                        .withSelfRel());
        return response;
    }

    public PixelArtDto updateWithFile(PixelArtDto createDto) {
        log.info("Get the pixel art with ID: {} in the database", createDto.getKey());
        Optional<PixelArt> byId = pixelArtRepository.findById(createDto.getKey());
        PixelArt pixelArt = byId.orElseThrow(
                () -> new NotFoundPixelArtException("Not found the pixel art with this id !")
        );

        log.info("Updating the art! ");
        pixelArt.setName(createDto.getName());
        pixelArt.setDescription(createDto.getDescription());
        pixelArt.setIsFreeUse(createDto.getIsFreeUse());
        pixelArt.setFilePath(createDto.getOriginalFileName());

        PixelArt saved = pixelArtRepository.save(pixelArt);

        PixelArtDto response = new PixelArtDto(saved.getId(), saved.getName(),
                saved.getDescription(), pixelArt.getFilePath(), saved.getIsFreeUse(),
                saved.getUser().getUsername());
        response.
                add(linkTo(methodOn(PixelArtController.class)
                        .downloadFile(response.getOriginalFileName(), null))
                        .withRel("Link to load this file"));
        response.
                add(linkTo(methodOn(PixelArtController.class)
                        .findById(pixelArt.getId()))
                        .withSelfRel());
        return response;
    }

    public String getOldFileName(Long id) {
        return pixelArtRepository.findById(id).orElseThrow(
                () -> new NotFoundPixelArtException("Not found the pixel art with this id !")
        ).getFilePath();
    }

    public String delete(Long id) {
        //Loading the data to verify and join the file path
        var pixelArt = pixelArtRepository.findById(id)
                .orElseThrow(() -> new NotFoundPixelArtException("The pixel art with id: "+ id+ " do not exits!"));
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();



        // Deleting the file
        fileServices.deleteAnFile(pixelArt.getFilePath(), userName);

        // Deleting the entity in the database
        pixelArtRepository.delete(pixelArt);

        return "The Pixel With ID: " + id + " Are Delete Successfully";
    }

    // canChangeEntity checks if the user is able to change te pixel art
    // the user is able to change if is the pixel art owner or is an ADMIM
    public boolean canChangeEntity(Long id, String username, Collection<? extends GrantedAuthority> authorities){
        var entity = pixelArtRepository.findById(id)
                .orElseThrow(()-> new NotFoundPixelArtException("The pixel art with id: "+ id+ " do not exits!"));

        return entity.getUser().getUsername().equals(username) ||
                authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIM"));
    }
}
