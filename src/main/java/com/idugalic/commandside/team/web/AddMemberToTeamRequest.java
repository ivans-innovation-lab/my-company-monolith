package com.idugalic.commandside.team.web;

import java.time.LocalDate;

public class AddMemberToTeamRequest {

	private String teamId;
	private String userId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long weeklyHours;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Long getWeeklyHours() {
		return weeklyHours;
	}
	public void setWeeklyHours(Long weeklyHours) {
		this.weeklyHours = weeklyHours;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	
}
