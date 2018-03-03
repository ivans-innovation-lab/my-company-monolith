package com.idugalic.commandside.team.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.idugalic.commandside.team.command.ActivateTeamCommand;
import com.idugalic.commandside.team.command.AddMemberToTeamCommand;
import com.idugalic.commandside.team.command.AssignProjectToTeamCommand;
import com.idugalic.commandside.team.command.CreateTeamCommand;
import com.idugalic.commandside.team.command.PassivateTeamCommand;
import com.idugalic.commandside.team.command.RemoveMemberFromTeamCommand;
import com.idugalic.common.model.AuditEntry;
import com.idugalic.common.team.model.Member;

/**
 * A web controller for managing {@link TeamAggregate} - create/update only.
 * 
 * @author idugalic
 *
 */
@RestController
@RequestMapping(value = "/api/teamcommands")
public class TeamController {
    
    private CommandGateway commandGateway;
    
    @Autowired
    public TeamController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	private String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }

    private AuditEntry createAudit() {
        return new AuditEntry(getCurrentUser());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody CreateTeamRequest request) {
        CreateTeamCommand command = new CreateTeamCommand(createAudit(), request.getName(), request.getDescription(), request.getStatus());
        commandGateway.sendAndWait(command);
    }

    @RequestMapping(value = "/{id}/assigncommand/{projectId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@PathVariable String id, @PathVariable String projectId) {
        AssignProjectToTeamCommand command = new AssignProjectToTeamCommand(createAudit(), id, projectId,"Reason");
        commandGateway.sendAndWait(command);
    }
    
    @RequestMapping(value = "/{id}/activatecommand", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void activate(@PathVariable String id) {
        ActivateTeamCommand command = new ActivateTeamCommand(createAudit(), id);
        commandGateway.sendAndWait(command);
    }
    
    @RequestMapping(value = "/{id}/passivatecommand", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void passivate(@PathVariable String id) {
        PassivateTeamCommand command = new PassivateTeamCommand(createAudit(), id);
        commandGateway.sendAndWait(command);
    }
    
    @RequestMapping(value = "/{id}/addmembercommand", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void addMember(@PathVariable String id, @RequestBody AddMemberToTeamRequest request) {
    	Member member = new Member(request.getUserId(), request.getStartDate(), request.getEndDate(), request.getWeeklyHours());
        AddMemberToTeamCommand command = new AddMemberToTeamCommand(createAudit(), id, member);
        commandGateway.sendAndWait(command);
    }
    
    @RequestMapping(value = "/{id}/removemembercommand/{memberId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void removeMember(@PathVariable String id, @PathVariable String memberId) {
    	    Member member = new Member(memberId, null, null, null);
        RemoveMemberFromTeamCommand command = new RemoveMemberFromTeamCommand(createAudit(), id, member);
        commandGateway.sendAndWait(command);
    }
   
}
