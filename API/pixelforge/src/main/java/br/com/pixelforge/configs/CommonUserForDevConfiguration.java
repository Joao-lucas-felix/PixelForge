package br.com.pixelforge.configs;

import br.com.pixelforge.domain.Permission;
import br.com.pixelforge.domain.User;
import br.com.pixelforge.repositories.PermissionRepository;
import br.com.pixelforge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

//Essa classe é responsavel por tentar recuperar um usuario admim do banco de dados,
//Caso não exista ela cria esse usuario e o persiste
@Configuration
public class CommonUserForDevConfiguration implements CommandLineRunner{
    private final Logger logger = Logger.getLogger(CommonUserForDevConfiguration.class.getName());
    @Autowired
    private UserRepository repository;
    @Autowired
    private PermissionRepository repositoryPermission;

    PasswordEncoder getPasswordEncoder(){
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

        return passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        var roleUser = repositoryPermission.findByDescription(Permission.Description.USER.name());
        var userAdmin = repository.findByUsername("commonUser");
        userAdmin.ifPresentOrElse(
                user -> {
                    logger.info("Common user exists");
                },
                () -> {
                    var user = new User();
                    user.setUsername("commonUser");
                    user.setFullName("commonUser");
                    user.setPassword(getPasswordEncoder().encode("commonUser"));
                    user.setAccountNonExpired(true);
                    user.setAccountNonLocked(true);
                    user.setCredentialsNonExpired(true);
                    user.setEnabled(true);
                    user.setPermissions(List.of(roleUser));
                    repository.save(user);
                }
        );

    }
}
