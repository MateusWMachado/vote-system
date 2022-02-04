package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.ScheduleDTO;
import com.mateuswmachado.votesystem.dto.VoteSessionDTO;
import com.mateuswmachado.votesystem.exceptions.ScheduleNotFoundException;
import com.mateuswmachado.votesystem.model.VoteSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/** Vote Session service test class
 *
 * @author Mateus W. Machado
 * @since 03/02/2022
 * @version 1.0.0
 */

@SpringBootTest
class VoteSessionServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private VoteSessionService voteSessionService;

    @DisplayName("Test to open a voting session correctly")
    @Test
    void openVotingSession() throws ScheduleNotFoundException {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .subject("Test Open Voting Session")
                .votes(List.of())
                .build();

        ResponseEntity<ScheduleDTO> schedule = scheduleService.createSchedule(savedSchedule, UriComponentsBuilder.newInstance());

        VoteSession voteSession = VoteSession.builder()
                .duration(1)
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .build();

        VoteSessionDTO voteSessionDTO = voteSessionService.openVotingSession(voteSession);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        assertNotNull(voteSessionDTO);
        assertEquals(voteSessionDTO.getStartTime().format(formatter), LocalDateTime.now().format(formatter));
        assertEquals(voteSessionDTO.getEndTime().format(formatter), LocalDateTime.now().plusMinutes(voteSessionDTO.getDuration()).format(formatter));
    }

    @DisplayName("Test to see if the end time will be set to 1 minute if duration is null")
    @Test
    void setTimeSessionIfDurationIsNull() throws ScheduleNotFoundException {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .subject("Test if time session is null")
                .votes(List.of())
                .build();

        ResponseEntity<ScheduleDTO> schedule = scheduleService.createSchedule(savedSchedule, UriComponentsBuilder.newInstance());

        VoteSession voteSession = VoteSession.builder()
                .duration(null)
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .build();

        VoteSessionDTO voteSessionDTO = voteSessionService.openVotingSession(voteSession);

        assertNotNull(voteSessionDTO);
        assertEquals(voteSessionDTO.getDuration(), 1);
    }


}