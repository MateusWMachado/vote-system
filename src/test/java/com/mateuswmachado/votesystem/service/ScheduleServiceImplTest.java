package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.ScheduleDTO;
import com.mateuswmachado.votesystem.dto.ScheduleResultDTO;
import com.mateuswmachado.votesystem.dto.VoteDTO;
import com.mateuswmachado.votesystem.exceptions.AssociateAlreadyVotedException;
import com.mateuswmachado.votesystem.exceptions.AssociatedNotFoundException;
import com.mateuswmachado.votesystem.exceptions.ScheduleException;
import com.mateuswmachado.votesystem.exceptions.ScheduleNotFoundException;
import com.mateuswmachado.votesystem.model.Schedule;
import com.mateuswmachado.votesystem.model.VoteSession;
import com.mateuswmachado.votesystem.repository.ScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/** Schedule service test class
 *
 * @author Mateus W. Machado
 * @since 03/02/2022
 * @version 1.0.0
 */

@SpringBootTest
class ScheduleServiceImplTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private VoteSessionService voteSessionService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper mapper;

    @Mock
    private ScheduleService mockScheduleService = new ScheduleServiceImpl();

    @DisplayName("Test to create schedule correctly")
    @Test
    void createSchedule() {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .subject("Test JUnit")
                .votes(List.of())
                .build();
        ResponseEntity<ScheduleDTO> schedule = scheduleService.createSchedule(savedSchedule, UriComponentsBuilder.newInstance());
        assertEquals(schedule.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(schedule.getBody());
    }

    @DisplayName("Test to verify if schedule exists")
    @Test
    void verifyIfScheduleExists() throws ScheduleNotFoundException {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .id(1L)
                .subject("Test JUnit")
                .votes(List.of())
                .build();

        Mockito.when(mockScheduleService.verifyIfScheduleExists(1L)).thenReturn(savedSchedule);
        ScheduleDTO scheduleDTO = mockScheduleService.verifyIfScheduleExists(1L);

        assertNotNull(scheduleDTO);
        assertEquals(scheduleDTO.getId(), 1L);
        assertEquals(scheduleDTO.getSubject(), "Test JUnit");
    }

    @DisplayName("Test to vote for a schedule correctly")
    @Test
    void voteSchedule() throws ScheduleNotFoundException {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .subject("Test JUnit Vote Schedule")
                .votes(List.of())
                .build();

        ResponseEntity<ScheduleDTO> schedule = scheduleService.createSchedule(savedSchedule, UriComponentsBuilder.newInstance());

        VoteSession voteSession = VoteSession.builder()
                .duration(3)
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .build();

        voteSessionService.openVotingSession(voteSession);

        VoteDTO voteDTO = VoteDTO.builder()
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .vote("YES")
                .cpf("287.538.880-06")
                .build();
        VoteDTO voteDTO1 = scheduleService.voteSchedule(voteDTO);

        assertNotNull(voteDTO1);
        assertEquals(voteDTO1.getVote(), "YES");
        assertEquals(voteDTO1.getCpf(), voteDTO.getCpf());
        assertEquals(voteDTO1.getIdSchedule(), voteDTO.getIdSchedule());
    }

    @DisplayName("Test to GET the results of a schedule")
    @Test
    void scheduleResult() throws ScheduleNotFoundException, InterruptedException {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .subject("Test JUnit Vote Schedule")
                .votes(List.of())
                .build();

        ResponseEntity<ScheduleDTO> schedule = scheduleService.createSchedule(savedSchedule, UriComponentsBuilder.newInstance());

        VoteSession voteSession = VoteSession.builder()
                .duration(1)
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .build();

        voteSessionService.openVotingSession(voteSession);

        VoteDTO voteDTO = VoteDTO.builder()
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .vote("YES")
                .cpf("287.538.880-06")
                .build();

        scheduleService.voteSchedule(voteDTO);

        Thread.sleep(60500);

        ScheduleResultDTO scheduleResultDTO = scheduleService.scheduleResult(Objects.requireNonNull(schedule.getBody()).getId());

        assertNotNull(scheduleResultDTO);
        assertEquals(scheduleResultDTO.getSubject(), savedSchedule.getSubject());
    }

    @DisplayName("Test to throw an exception if a associate tries to vote on a schedule that doesn't exist")
    @Test
    void throwExceptionIfScheduleHasNoVotingSession() {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .subject("Test JUnit Vote Schedule")
                .votes(List.of())
                .build();

        ResponseEntity<ScheduleDTO> schedule = scheduleService.createSchedule(savedSchedule, UriComponentsBuilder.newInstance());

        VoteDTO voteDTO = VoteDTO.builder()
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .vote("YES")
                .cpf("287.538.880-06")
                .build();

        assertThrows(ScheduleException.class, () -> scheduleService.voteSchedule(voteDTO), "Test");
    }

    @DisplayName("Test to throw an exception if a associate has already voted")
    @Test
    void throwExceptionIfAssociateAlreadyVoted() throws ScheduleNotFoundException, InterruptedException {
        ScheduleDTO savedSchedule = ScheduleDTO.builder()
                .subject("Test JUnit Associate Already Voted")
                .votes(List.of())
                .build();

        ResponseEntity<ScheduleDTO> schedule = scheduleService.createSchedule(savedSchedule, UriComponentsBuilder.newInstance());

        VoteSession voteSession = VoteSession.builder()
                .duration(1)
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .build();

        voteSessionService.openVotingSession(voteSession);

        VoteDTO voteDTO = VoteDTO.builder()
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .vote("YES")
                .cpf("287.538.880-06")
                .build();

        VoteDTO voteDTO2 = VoteDTO.builder()
                .idSchedule(Objects.requireNonNull(schedule.getBody()).getId())
                .vote("YES")
                .cpf("287.538.880-06")
                .build();

        scheduleService.voteSchedule(voteDTO);

        assertThrows(AssociateAlreadyVotedException.class, () -> scheduleService.voteSchedule(voteDTO2), "Test");
    }
}