package br.com.pixelforge.repositories;

import br.com.pixelforge.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByDescription(String description);
}
