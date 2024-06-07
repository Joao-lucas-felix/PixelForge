package br.com.pixelforge.domain.DTOs;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TokenDto implements Serializable {
    @Serial
    private static final  long serialVersionUID = 1L;

    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
}
