package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.ScheduleDTO;
import com.mateuswmachado.votesystem.dto.ScheduleResultDTO;
import com.mateuswmachado.votesystem.dto.VoteDTO;
import com.mateuswmachado.votesystem.exceptions.ScheduleNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/** Interface representing a Schedule service operations contract
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */


public interface ScheduleService {

    ResponseEntity<ScheduleDTO> createSchedule(ScheduleDTO scheduleDTO, UriComponentsBuilder uriBuilder);

    ScheduleResultDTO scheduleResult(Long id) throws ScheduleNotFoundException;

    ScheduleDTO verifyIfScheduleExists(Long id) throws ScheduleNotFoundException;

    VoteDTO voteSchedule(VoteDTO voteDTO) throws ScheduleNotFoundException;
}
