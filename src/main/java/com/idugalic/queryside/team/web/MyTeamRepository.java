package com.idugalic.queryside.team.web;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.idugalic.queryside.team.domain.Team;
import com.idugalic.queryside.team.repository.TeamRepository;

/**
 * A JPA repository for {@link Team}.
 * 
 * @author idugalic
 *
 */
@RepositoryRestResource(collectionResourceRel = "team", path = "team")
interface MyTeamRepository extends TeamRepository {
}
