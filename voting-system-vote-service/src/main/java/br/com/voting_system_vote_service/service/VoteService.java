package br.com.voting_system_vote_service.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.voting_system_vote_service.repository.*;
import br.com.voting_system_vote_service.entity.*;
import br.com.voting_system_vote_service.client.UserServiceClient;



/**
 * @author fsdney
 */

@Service
public class VoteService {
	
    private final VoteRepository voteRepository;
    private final VoteSessionRepository voteSessionRepository;
    private final UserServiceClient userServiceClient;

    public VoteService(VoteRepository voteRepository, 
                       VoteSessionRepository voteSessionRepository, 
                       UserServiceClient userServiceClient) {
        this.voteRepository = voteRepository;
        this.voteSessionRepository = voteSessionRepository;
        this.userServiceClient = userServiceClient;
    }
	
	
	public String castVote(Long voteSessionId, Long userId, String option) {
		
		 try {
	            userServiceClient.getUserById(userId);
	        } catch (Exception e) {
	            throw new RuntimeException("Usuário não encontrado. O voto não pode ser registrado.");
	        }
		
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
		
		if(!session.getOptions().contains(option)) {
			throw new RuntimeException("Opção de voto invalida");
		}
		
		Vote vote = new Vote();
	    vote.setVoteSession(session); 
	    vote.setUserId(userId);
	    vote.setChosenOption(option);		
		voteRepository.save(vote);
		return "Voto registrado com sucesso!";
		
	}
	

	
	
	
	

}
