package br.com.pixelforge.repositories;

import br.com.pixelforge.domain.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
    Optional<User> findByUsername(String username);
}
