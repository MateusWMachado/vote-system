package com.mateuswmachado.votesystem.kafka.producer;

import com.mateuswmachado.votesystem.dto.ScheduleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/** Class responsible for sending Schedule messages
 *
 * @author Mateus W. Machado
 * @since 02/02/2022
 * @version 1.0.0
 */

@Service
@Slf4j
public class ResultProducer {

    private final String schedule;
    private final KafkaTemplate<String, ScheduleDTO> kafkaTemplate;

    public ResultProducer(@Value("${topic.name}") String schedule, KafkaTemplate<String, ScheduleDTO> kafkaTemplate) {
        this.schedule = schedule;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ScheduleDTO scheduleDTO) {
        kafkaTemplate.send(schedule, scheduleDTO).addCallback(
                success -> log.info("Message send " + success.getProducerRecord().value()),
                failure -> log.info("Message failure " + failure.getMessage())
        );
    }

}
