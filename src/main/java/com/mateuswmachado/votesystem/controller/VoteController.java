package com.mateuswmachado.votesystem.controller;

import com.mateuswmachado.votesystem.dto.VoteDTO;
import com.mateuswmachado.votesystem.exceptions.ScheduleNotFoundException;
import com.mateuswmachado.votesystem.model.Vote;
import com.mateuswmachado.votesystem.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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

/** Class that represents a controller to handle voting information
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Slf4j
@RestController
@RequestMapping(value = "api/v1/vote")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VoteController {

    private ScheduleService scheduleService;

    @PostMapping
    @Transactional
    @ApiOperation("Create a vote on a schedule")
    public ResponseEntity<VoteDTO> vote(@RequestBody @Valid VoteDTO voteDTO) throws ScheduleNotFoundException {
        log.info("VoteController.vote - start vote schedule. vote info: [{}]", voteDTO);
        return new ResponseEntity<>(scheduleService.voteSchedule(voteDTO), HttpStatus.CREATED);
    }
}
