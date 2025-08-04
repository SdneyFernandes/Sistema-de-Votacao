package br.com.voting_system_notification_service.dto;

import lombok.Data;

/**
 * @author fsdney
 */


@Data
public class VoteEvent {
    private Long userId;
    private Long voteSessionId;
    private String chosenOption;
    private String email;
}

