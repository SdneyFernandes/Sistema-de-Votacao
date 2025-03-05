package br.com.voting_system_vote_service.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.voting_system_vote_service.repository.*;
import br.com.voting_system_vote_service.entity.*;
import br.com.voting_system_vote_service.client.UserServiceClient;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author fsdney
 */

@Service
public class VoteService {
	
	private static final Logger logger = LoggerFactory.getLogger(VoteService.class);
	
	@Autowired
    private MeterRegistry meterRegistry;
	
	@Autowired
    private VoteRepository voteRepository;
	
	@Autowired
    private VoteSessionRepository voteSessionRepository;
	
	@Autowired
    private UserServiceClient userServiceClient;
	
	
	public String castVote(Long voteSessionId, Long userId, String option) {
		
		 long startTime = System.currentTimeMillis();
	        meterRegistry.counter("votos.chamadas").increment();

	        logger.info("Usuário {} tentando votar na sessão {}", userId, voteSessionId);
		
		 try {
	            userServiceClient.getUserById(userId);
	        } catch (Exception e) {
	     
	             logger.error("Usuário {} não encontrado. O voto não pode ser registrado.", userId);
	             throw new RuntimeException("Usuário não encontrado. O voto não pode ser registrado.");
	        }
		
		VoteSession session = voteSessionRepository.findById(voteSessionId)
					.orElseThrow(() -> new RuntimeException("Votação não encontrada"));
		
		LocalDateTime now = LocalDateTime.now();
		if(now.isBefore(session.getStartAt())) {
			logger.warn("Votação {} ainda não começou.", voteSessionId);
            throw new RuntimeException("A votação ainda não está aberta");
		}
		if(now.isAfter(session.getEndAt())) {
			logger.warn("Votação {} já foi encerrada.", voteSessionId);
			throw new RuntimeException("A votação ja foi encerrada");
		}
		
		if(voteRepository.existsByVoteSessionAndUserId(session, userId)) {
			logger.warn("Usuário {} já votou na sessão {}", userId, voteSessionId);
			throw new RuntimeException("Usuario já votou nesta votação");
		}
		
		if(!session.getOptions().contains(option)) {
			logger.warn("Votação {} já foi encerrada.", voteSessionId);
			throw new RuntimeException("Opção de voto invalida");
		}
		
		Vote vote = new Vote();
	    vote.setVoteSession(session); 
	    vote.setUserId(userId);
	    vote.setChosenOption(option);		
		voteRepository.save(vote);

		long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("votos.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

        logger.info("Voto registrado com sucesso: Usuário {} na sessão {}", userId, voteSessionId);
        return "Voto registrado com sucesso!";
		
	}
	

	
	
	
	

}
