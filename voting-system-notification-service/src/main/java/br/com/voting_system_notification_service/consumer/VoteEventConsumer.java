package br.com.voting_system_notification_service.consumer;

import br.com.notification_service.dto.VoteEvent;
import br.com.notification_service.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * @author fsdney
 */

@Component
@RequiredArgsConstructor
public class VoteEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "vote-casted", groupId = "notification-group")
    public void listenVote(String payload) throws Exception {
        VoteEvent event = new ObjectMapper().readValue(payload, VoteEvent.class);
        notificationService.notifyVote(event);
    }
}

