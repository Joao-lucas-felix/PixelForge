package br.com.pixelforge.services;

import br.com.pixelforge.repositories.PixelArtRepository;
import br.com.pixelforge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PixelArtServices {
    private final PixelArtRepository pixelArtRepository;
    private final UserRepository userRepository;

    @Autowired
    public PixelArtServices(PixelArtRepository pixelArtRepository, UserRepository userRepository) {
        this.pixelArtRepository = pixelArtRepository;
        this.userRepository = userRepository;
    }



    public boolean validateUser(){
        return true;
    }
    public boolean artFileExists(){
        return true;
    }


}
