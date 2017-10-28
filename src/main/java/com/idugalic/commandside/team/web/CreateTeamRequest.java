package com.idugalic.commandside.team.web;

import com.idugalic.commandside.project.command.CreateProjectCommand;
import com.idugalic.common.team.model.TeamStatus;

/**
 * A web request data transfer object for {@link CreateProjectCommand}
 * 
 * @author idugalic
 *
 */
public class CreateTeamRequest {

	private String name;
	private String description;
	private TeamStatus status;

    public CreateTeamRequest() {
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public void setStatus(TeamStatus status) {
		this.status = status;
	}

  
}
