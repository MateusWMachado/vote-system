package com.mateuswmachado.votesystem.controller;

import com.mateuswmachado.votesystem.dto.ScheduleDTO;
import com.mateuswmachado.votesystem.dto.ScheduleResultDTO;
import com.mateuswmachado.votesystem.exceptions.ScheduleNotFoundException;
import com.mateuswmachado.votesystem.model.Schedule;
import com.mateuswmachado.votesystem.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/** Class that represents a controller to handle information about schedules
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Slf4j
@RestController
@RequestMapping(value = "api/v1/schedule")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleController {

    private ScheduleService scheduleService;

    @PostMapping
    @Transactional
    @ApiOperation("Create a schedule")
    public ResponseEntity<ScheduleDTO> create(@RequestBody @Valid ScheduleDTO scheduleDTO, UriComponentsBuilder uriBuilder) {
        return scheduleService.createSchedule(scheduleDTO, uriBuilder);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a result from a schedule")
    public ResponseEntity<ScheduleResultDTO> getResult(@PathVariable Long id) throws ScheduleNotFoundException {
        log.info("ScheduleController.getResult - get schedule by id - schedule id: [{}]", id);
        return new ResponseEntity<>(scheduleService.scheduleResult(id), HttpStatus.OK);
    }

}
