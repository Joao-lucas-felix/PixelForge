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

@Configuration
public class AdminUserForDevConfiguration implements CommandLineRunner{
    private final Logger logger = Logger.getLogger(AdminUserForDevConfiguration.class.getName());
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
        var roleAdmin = repositoryPermission.findByDescription(Permission.Description.ADMIM.name());
        var userAdmin = repository.findByUsername("admin2");
        userAdmin.ifPresentOrElse(
                user -> {
                    logger.info("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setUsername("admin2");
                    user.setFullName("admin2");
                    user.setPassword(getPasswordEncoder().encode("admin"));
                    user.setAccountNonExpired(true);
                    user.setAccountNonLocked(true);
                    user.setCredentialsNonExpired(true);
                    user.setEnabled(true);
                    user.setPermissions(List.of(roleAdmin));
                    repository.save(user);
                }
        );

    }
}
