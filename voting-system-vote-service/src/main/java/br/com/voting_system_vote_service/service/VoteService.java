package br.com.voting_system_vote_service.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.voting_system_vote_service.repository.*;
import br.com.voting_system_vote_service.entity.*;



/**
 * @author fsdney
 */

@Service
public class VoteService {
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private VoteSessionRepository voteSessionRepository;
	
	
	public String castVote(Long voteSessionId, Long userId, String option) {
		VoteSession session = voteSessionRepository.findById(voteSessionId)
					.orElseThrow(() -> new RuntimeException("Votação não encontrada"));
		
		LocalDateTime now = LocalDateTime.now();
		if(now.isBefore(session.getStartAt())) {
			throw new RuntimeException("A votação ainda não esta aberta");
		}
		if(now.isAfter(session.getEndAt())) {
			throw new RuntimeException("A votação ja foi encerrada");
		}
		
		if(voteRepository.existsByVoteSessionAndUserId(session, userId)) {
			throw new RuntimeException("Usuario já votou nesta votação");
		}
		
		Vote vote = new Vote();
	    vote.setVoteSession(session); 
	    vote.setUserId(userId);
	    vote.setChosenOption(option);		
		voteRepository.save(vote);
		return "Voto registrado com sucesso!";
		
	}
	

	
	
	
	

}
