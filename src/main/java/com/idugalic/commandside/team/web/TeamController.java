package com.idugalic.commandside.team.web;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.idugalic.commandside.team.command.AssignProjectToTeamCommand;
import com.idugalic.commandside.team.command.CreateTeamCommand;
import com.idugalic.common.model.AuditEntry;

/**
 * A web controller for managing {@link TeamAggregate} - create/update only.
 * 
 * @author idugalic
 *
 */
@RestController
@RequestMapping(value = "/teamcommands")
public class TeamController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);
    
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
    public void create(@RequestBody CreateTeamRequest request, HttpServletResponse response, Principal principal) {
        LOG.info("################ "+ CreateTeamRequest.class.getSimpleName() + " request received");
        CreateTeamCommand command = new CreateTeamCommand(createAudit(), request.getName(), request.getDescription(), request.getStatus());
        commandGateway.sendAndWait(command);
        LOG.info("################ "+ CreateTeamCommand.class.getSimpleName() + " sent to command gateway: Team [{}] ", command.getId());
    }

    @RequestMapping(value = "/{id}/assigncommand/{projectId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void update(@PathVariable String id, @PathVariable String projectId, @RequestBody AssignProjectToTeamRequest request, HttpServletResponse response, Principal principal) {
        LOG.info("################ "+ AssignProjectToTeamRequest.class.getSimpleName() + " request received");
        AssignProjectToTeamCommand command = new AssignProjectToTeamCommand(createAudit(), request.getId(), request.getProjectId(),"Reeason");
        commandGateway.sendAndWait(command);
        LOG.info("################ "+ AssignProjectToTeamCommand.class.getSimpleName() + " sent to command gateway: Team [{}] ", command.getId());
    }

}
