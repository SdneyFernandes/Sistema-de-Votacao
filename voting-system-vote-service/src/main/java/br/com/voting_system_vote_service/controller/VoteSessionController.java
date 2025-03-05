package br.com.voting_system_vote_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(VoteSessionController.class);
	
	
	@Autowired
	private VoteSessionService voteSessionService;
	
	@PostMapping("/create")
	public ResponseEntity<VoteSession> createVoteSession(@RequestBody VoteSession voteSession) {
		logger.info("Recebida requisição para criar nova Sessão de Votação");
		return ResponseEntity.ok(voteSessionService.createVoteSession(voteSession));
	}
	
	@GetMapping("/")
    public ResponseEntity<List<VoteSession>> getAllVoteSessions() {
		logger.info("Recebida requisição para listar todas as Sessões de Votação");
        List<VoteSession> sessions = voteSessionService.getAllVoteSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{voteSessionId}")
    public ResponseEntity<VoteSession> getVoteSession(@PathVariable Long voteSessionId) {
    	logger.info("Recebida requisição para buscar Sessão de Votação");
        VoteSession session = voteSessionService.getVoteSession(voteSessionId);
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/{voteSessionId}")
    public ResponseEntity<String> deleteVoteSession(@PathVariable Long voteSessionId) {
    	logger.info("Recebida requisição para deletar Sessão de Votação");
        voteSessionService.deleteVoteSession(voteSessionId);
        return ResponseEntity.ok("Sessão de votação deletada com sucesso");
    }

    // Endpoint para obter resultados de votação
    @GetMapping("/{voteSessionId}/results")
    public ResponseEntity<Map<String, Long>> getVoteResults(@PathVariable Long voteSessionId) {
    	logger.info("Recebida requisição para buscar resultados da votação");
        Map<String, Long> results = voteSessionService.getVoteResults(voteSessionId);
        return ResponseEntity.ok(results);
    }
}
