package br.com.pixelforge.domain;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ValidationFilePathInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Boolean exists;
    private String filePath;
}
