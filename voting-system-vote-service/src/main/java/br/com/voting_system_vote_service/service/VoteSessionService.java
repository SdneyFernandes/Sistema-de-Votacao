package br.com.voting_system_vote_service.service;

import br.com.voting_system_vote_service.repository.*;
import br.com.voting_system_vote_service.entity.Vote;
import br.com.voting_system_vote_service.entity.VoteSession;

import java.util.*;
import java.util.stream.Collectors;



import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;




/**
 * @author fsdney
 */

@Service
public class VoteSessionService {

	
	@Autowired
	private VoteSessionRepository voteSessionRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	public VoteSession createVoteSession(VoteSession voteSession) {
		if (voteSession.getStartAt().isAfter(voteSession.getEndAt())) {
			throw new RuntimeException("A data de inicio não pode ser depois da data de termino");
		}
		return voteSessionRepository.save(voteSession);
	}
	
	 public List<VoteSession> getAllVoteSessions() {
	        return voteSessionRepository.findAll();
	    }

	    public VoteSession getVoteSession(Long voteSessionId) {
	        return voteSessionRepository.findById(voteSessionId)
	                .orElseThrow(() -> new RuntimeException("Sessão de votação não encontrada"));
	    }

	    public void deleteVoteSession(Long voteSessionId) {
	        if (!voteSessionRepository.existsById(voteSessionId)) {
	            throw new RuntimeException("Sessão de votação não encontrada");
	        }
	        voteSessionRepository.deleteById(voteSessionId);
	    }
	    
	    public Map<String, Long> getVoteResults(Long voteSessionId) {
			VoteSession session = voteSessionRepository.findById(voteSessionId)
					.orElseThrow(() -> new RuntimeException("Votação não encontrada"));
			List<Vote> votes = voteRepository.findByVoteSession(session);
			
			return votes.stream()
					.collect(Collectors.groupingBy(Vote::getChosenOption, Collectors.counting()));
		}
		
}
