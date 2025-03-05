package br.com.voting_system_vote_service.controller;

import br.com.voting_system_vote_service.service.VoteService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




/**
 * @author fsdney
 */

@RestController
@RequestMapping("/api/votes")
public class VoteController {

	private static final Logger logger = LoggerFactory.getLogger(VoteController.class);
	
	@Autowired
	private VoteService voteService;
	
	@PostMapping("/{voteSessionId}/cast")
    public ResponseEntity<String> castVote(
            @PathVariable Long voteSessionId,
            @RequestParam(required = true) Long userId,
            @RequestParam(required = true) String option) {
		logger.info("Recebida requisição para registar voto");
        String responseMessage = voteService.castVote(voteSessionId, userId, option);
        return ResponseEntity.ok(responseMessage);
    }
	
}












