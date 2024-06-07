package br.com.pixelforge.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Permission implements GrantedAuthority, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Description description;


    @Override
    public String getAuthority() {
        return this.description.name();
    }

    public enum Description {
        ADMIM(1L),
        USER(2L);
        final long permissionId;
         Description(long id){
            this.permissionId = id;
        }

    }
}
