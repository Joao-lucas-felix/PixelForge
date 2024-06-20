package br.com.pixelforge.security.jwt;


import br.com.pixelforge.domain.DTOs.TokenDto;
import br.com.pixelforge.exceptions.InvalidTokenAuthenticationException;
import br.com.pixelforge.repositories.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtTokenProvider {
    @Value("${security.jwt.private-key:secret}")
    private RSAPrivateKey privateKey;
    @Value("${security.jwt.public-key:secret}")
    private RSAPublicKey  publicKey;
    @Value("${security.jwt.expire-length:3600000}")
    private Long validityInMiliseconds;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    public JwtEncoder getEncoder(){
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    public JwtDecoder getDecoder(){
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    public TokenDto createAccessToke(String userId, List<String> roles)
    {
        Date now = new Date();
        Date validity = new Date(now.getTime()+validityInMiliseconds);
        var accessToken = getAccessToken(userId, now, validity);
        return new
                TokenDto(userId,true, now, validity, accessToken);
    }

    private String getAccessToken(String userId, Date now, Date validity) {
        String issueUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath().build().toUriString();

        var claims = JwtClaimsSet.builder()
                .issuer("pixelforge")
                .issuedAt(Instant.ofEpochMilli(now.getTime()))
                .subject(userId)
                .expiresAt(Instant.ofEpochMilli(validity.getTime()))
                .build();

        return getEncoder()
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public Authentication getAutentication(String token){
        Jwt decode = getDecoder().decode(token);
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(
                        userRepository.findById(UUID.fromString(decode.getClaim("sub")))
                            .orElseThrow().getUsername());
        return new UsernamePasswordAuthenticationToken
                (userDetails, "", userDetails.getAuthorities());
    }
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token){
        var expiresAt = getDecoder().decode(token).getClaim("exp");
        try {
            if ( Date.from((Instant) expiresAt).before(new Date()) ) {
                return false;
            }
        } catch (Exception e) {
            throw new InvalidTokenAuthenticationException
                    ("Invalid or Expired Token!");
        }
        return true;
    }


}
