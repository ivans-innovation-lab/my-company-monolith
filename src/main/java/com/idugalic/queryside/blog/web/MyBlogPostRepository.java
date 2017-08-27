package com.idugalic.queryside.blog.web;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idugalic.queryside.blog.domain.BlogPost;
import com.idugalic.queryside.blog.repository.BlogPostRepository;

/**
 * A JPA repository for {@link BlogPost}.
 * 
 * @author idugalic
 *
 */
@RepositoryRestResource(collectionResourceRel = "blogposts", path = "blogposts")
interface MyBlogPostRepository extends BlogPostRepository {
}
