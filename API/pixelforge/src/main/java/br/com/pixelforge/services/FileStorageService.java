package br.com.pixelforge.services;

import br.com.pixelforge.configs.FileUploadConfig;
import br.com.pixelforge.domain.ValidationFilePathInfo;
import br.com.pixelforge.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path baseFileStorageLocation;

    @Autowired
    public FileStorageService(FileUploadConfig config) {
        this.baseFileStorageLocation = Paths.get(config.getUploadBaseDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.baseFileStorageLocation);
        }catch (Exception e) {
            throw new FileStorageException("Could not create the directory.");
        }
    }

    public String storageFile (MultipartFile multipartFile,String userName){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            //Here is going be the media type verification
            if(fileName.contains("..")){
                throw new FileStorageException("File name contains invalid char sequence: "+ fileName);
            }

            //creates the user dir
            Path useDirResolved = Paths.get(this.baseFileStorageLocation.toUri())
                    .resolve(userName).toAbsolutePath().normalize();
            try{
                Files.createDirectories(useDirResolved);
            }catch (Exception e){
                throw new FileStorageException("Could not create the user directory.");
            }

            //saves the art.
            Path targetLocation = useDirResolved.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        }catch (Exception e) {
            throw new FileStorageException("Could not Store the file: "+fileName);
        }

        return fileName;
    }

    public ValidationFilePathInfo artFileExists(String originalFileName, String username) {
        try{
            Path pixelArtFile = Paths.get(this.baseFileStorageLocation.toUri()).resolve(username).toAbsolutePath().normalize()
                    .resolve(originalFileName);
            return new ValidationFilePathInfo( Files.exists(pixelArtFile), Paths.get(this.baseFileStorageLocation.toUri())
                    .resolve(username)
                    .resolve(originalFileName)
                    .toString());

        }catch (Exception e){
            throw new FileStorageException("Error while trying to find the file");
        }
    }
}
