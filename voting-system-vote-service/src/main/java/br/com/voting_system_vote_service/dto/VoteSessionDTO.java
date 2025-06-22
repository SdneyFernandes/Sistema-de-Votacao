package br.com.voting_system_vote_service.dto;


import br.com.voting_system_vote_service.enums.*;
import br.com.voting_system_vote_service.entity.*;
import java.time.LocalDateTime;
import java.util.List;


import lombok. *;


/**
 * @author fsdney
 */



@Getter
@Setter
public class VoteSessionDTO {

    private Long id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private VoteStatus status;
    private Long creatorId;
    private String title;
    private List<String> options;
    
    public VoteSessionDTO() {}

    public VoteSessionDTO(VoteSession voteSession) {
        this.id = voteSession.getId();
        this.title = voteSession.getTitle();
        this.options = voteSession.getOptions();
        this.startAt = voteSession.getStartAt();
        this.endAt = voteSession.getEndAt();
        this.status = voteSession.getStatus();
        this.creatorId = voteSession.getCreatorId();
    }

}

