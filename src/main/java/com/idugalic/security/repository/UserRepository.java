package com.idugalic.security.repository;


import com.idugalic.queryside.blog.domain.BlogPost;
import com.idugalic.security.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
