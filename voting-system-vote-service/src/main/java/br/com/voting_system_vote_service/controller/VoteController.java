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
	
	@PostMapping("/{electionId}/vote")
	public ResponseEntity<String> registerVote(
			@RequestParam Long userId,
			@PathVariable Long electionId,
			@RequestParam Long candidateId) {
		
		String response = voteService.registerVote(userId, electionId, candidateId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{electionId}/count")
	public ResponseEntity<Long> countVotes(
			@PathVariable Long electionId,
			@RequestParam Long candidateId) {
			
		long count = voteService.countVotes(electionId, candidateId);
		return ResponseEntity.ok(count);
		
	}
	
	@GetMapping("/{electionId}")
	public ResponseEntity<List<Vote>> getVotesByElection(@PathVariable Long electionId) {
		List<Vote> votes = voteService.getVotesByElection(electionId);
		return ResponseEntity.ok(votes);
	}
}












