package com.idugalic.security.repository;


import com.idugalic.security.domain.Role;
import com.idugalic.security.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
