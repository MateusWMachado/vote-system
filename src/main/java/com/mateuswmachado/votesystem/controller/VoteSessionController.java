package com.mateuswmachado.votesystem.controller;

import com.mateuswmachado.votesystem.dto.VoteSessionDTO;
import com.mateuswmachado.votesystem.exceptions.ScheduleNotFoundException;
import com.mateuswmachado.votesystem.model.VoteSession;
import com.mateuswmachado.votesystem.service.VoteSessionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/** Class that represents a controller to handle information about voting sessions
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Slf4j
@RestController
@RequestMapping("api/v1/vote-session")
public class VoteSessionController {

    @Autowired
    private VoteSessionService voteSessionService;

    @PostMapping
    @Transactional
    @ApiOperation("Create a vote session")
    public ResponseEntity<VoteSessionDTO> createVotingSession(@RequestBody @Valid VoteSession voteSession) throws ScheduleNotFoundException {
        log.info("VoteSessionController.createVotingSession - creating a vote session. parameters: [{}]", voteSession);
        return new ResponseEntity<>(voteSessionService.openVotingSession(voteSession), HttpStatus.CREATED);
    }

}
