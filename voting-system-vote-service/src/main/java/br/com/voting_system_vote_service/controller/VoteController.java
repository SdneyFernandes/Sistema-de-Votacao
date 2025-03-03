package br.com.voting_system_vote_service.controller;

import br.com.voting_system_vote_service.service.VoteService;
import br.com.voting_system_vote_service.entity.Vote;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author fsdney
 */

@RestController
@RequestMapping("/api/votes")
public class VoteController {

	@Autowired
	private VoteService voteService;
	
	@PostMapping("/{voteSessionId}/cast")
    public ResponseEntity<String> castVote(
            @PathVariable Long voteSessionId,
            @RequestParam Long userId,
            @RequestParam String option) {
        String responseMessage = voteService.castVote(voteSessionId, userId, option);
        return ResponseEntity.ok(responseMessage);
    }
	
}












