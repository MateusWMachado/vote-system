package com.mateuswmachado.votesystem.service;

import com.mateuswmachado.votesystem.dto.ScheduleDTO;
import com.mateuswmachado.votesystem.dto.VoteSessionDTO;
import com.mateuswmachado.votesystem.exceptions.ScheduleException;
import com.mateuswmachado.votesystem.exceptions.ScheduleNotFoundException;
import com.mateuswmachado.votesystem.model.Schedule;
import com.mateuswmachado.votesystem.model.VoteSession;
import com.mateuswmachado.votesystem.repository.VoteSessionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/** Class of service responsible for opening voting sessions
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VoteSessionService {

    private ModelMapper modelMapper;
    private final VoteSessionRepository voteSessionRepository;
    private final ScheduleService ScheduleService;

    public VoteSessionDTO openVotingSession(VoteSession voteSession) throws ScheduleNotFoundException {

        log.info("VoteSessionService.openVotingSession - starting a voting session");

        ScheduleDTO scheduleDTO = ScheduleService.verifyIfScheduleExists(voteSession.getIdSchedule());

        if (Objects.nonNull(scheduleDTO.getVoteSession())) {
            throw new ScheduleException("Schedule already passed session");
        }

        setTimeSession(voteSession);

        voteSession.setEndTime(voteSession.getStartTime().plusMinutes(voteSession.getDuration()));
        voteSession.setSchedule(modelMapper.map(scheduleDTO, Schedule.class));
        voteSessionRepository.save(voteSession);

        log.info("VoteSessionService.openVotingSession - voting session created. results: [{}]", voteSession);

        return modelMapper.map(voteSession, VoteSessionDTO.class);
    }

    public void setTimeSession(VoteSession voteSession) {
        if (Objects.isNull(voteSession.getStartTime())) {
            voteSession.setStartTime(LocalDateTime.now());
        }
        if (Objects.isNull(voteSession.getDuration())) {
            voteSession.setDuration(1);
        }
    }


}