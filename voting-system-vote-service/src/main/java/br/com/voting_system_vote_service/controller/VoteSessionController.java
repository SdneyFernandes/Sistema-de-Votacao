package br.com.voting_system_vote_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.voting_system_vote_service.service.VoteSessionService;
import br.com.voting_system_vote_service.entity.VoteSession;

import java.util. *;




/**
 * @author fsdney
 */

@RestController
@RequestMapping("/api/votes_session")
public class VoteSessionController {
	
	@Autowired
	private VoteSessionService voteSessionService;
	
	@PostMapping("/create")
	public ResponseEntity<VoteSession> createVoteSession(@RequestBody VoteSession voteSession) {
		return ResponseEntity.ok(voteSessionService.createVoteSession(voteSession));
	}
	
	@GetMapping("/")
    public ResponseEntity<List<VoteSession>> getAllVoteSessions() {
        List<VoteSession> sessions = voteSessionService.getAllVoteSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{voteSessionId}")
    public ResponseEntity<VoteSession> getVoteSession(@PathVariable Long voteSessionId) {
        VoteSession session = voteSessionService.getVoteSession(voteSessionId);
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/{voteSessionId}")
    public ResponseEntity<String> deleteVoteSession(@PathVariable Long voteSessionId) {
        voteSessionService.deleteVoteSession(voteSessionId);
        return ResponseEntity.ok("Sessão de votação deletada com sucesso");
    }

    // Endpoint para obter resultados de votação
    @GetMapping("/{voteSessionId}/results")
    public ResponseEntity<Map<String, Long>> getVoteResults(@PathVariable Long voteSessionId) {
        Map<String, Long> results = voteSessionService.getVoteResults(voteSessionId);
        return ResponseEntity.ok(results);
    }
}
