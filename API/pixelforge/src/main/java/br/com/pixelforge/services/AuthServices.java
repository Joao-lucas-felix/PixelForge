package br.com.pixelforge.services;

import br.com.pixelforge.configs.AdminUserForDevConfiguration;
import br.com.pixelforge.domain.DTOs.TokenDto;
import br.com.pixelforge.domain.DTOs.UserCredentialsDto;
import br.com.pixelforge.domain.User;
import br.com.pixelforge.repositories.UserRepository;
import br.com.pixelforge.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthServices {
    private final Logger logger = Logger.getLogger(AdminUserForDevConfiguration.class.getName());

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager manager;


    public ResponseEntity<TokenDto> login(UserCredentialsDto dto){
        try {
            var username = dto.getUsername();
            var password = dto.getPassword();
            manager.authenticate( new UsernamePasswordAuthenticationToken(username, password));

            User user = userRepository.findByUsername(dto.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("User or password invalid!")
            );
            TokenDto accessToken = tokenProvider.createAccessToke(user.getUsername(), user.getRoles());


            //Cdoigo para pegar a role de um user pelo username.
            userRepository.findByUsername(tokenProvider.getDecoder().decode(accessToken.getAccessToken()).getClaim("sub"))
                    .ifPresent((user1 -> {user1.getPermissions()
                            .forEach(permission -> logger.info("User Role: "+ permission.getDescription()));}));

            return ResponseEntity.ok(accessToken);

        } catch (Exception e) {
            throw new BadCredentialsException("Username or Password invalid! ");
        }
    }

}
