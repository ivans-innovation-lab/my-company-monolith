package com.idugalic.queryside.project.web;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idugalic.queryside.project.domain.Project;
import com.idugalic.queryside.project.repository.ProjectRepository;

/**
 * A JPA repository for {@link Project}.
 * 
 * @author idugalic
 *
 */
@RepositoryRestResource(collectionResourceRel = "projects", path = "projects")
interface MyProjectRepository extends ProjectRepository {
}
