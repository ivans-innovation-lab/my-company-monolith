package com.idugalic.commandside.team.web;
/**
 * 
 * 
 * @author idugalic
 *
 */
public class AssignProjectToTeamRequest {

	private String id;
	private String projectId;

    public AssignProjectToTeamRequest() {
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
