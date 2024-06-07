package br.com.pixelforge.domain.DTOs;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserCredentialsDto implements Serializable {
    @Serial
    private static final  long serialVersionUID = 1L;

    private String username;
    private String password;
}
