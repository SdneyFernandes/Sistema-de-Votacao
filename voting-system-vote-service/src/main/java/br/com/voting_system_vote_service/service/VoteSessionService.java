package br.com.voting_system_vote_service.service;

import br.com.voting_system_vote_service.repository.*;
import br.com.voting_system_vote_service.entity.Vote;
import br.com.voting_system_vote_service.entity.VoteSession;

import java.util.*;
import java.util.stream.Collectors;



import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;


import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * @author fsdney
 */

@Service
public class VoteSessionService {
	
	private final Logger logger = LoggerFactory.getLogger(VoteSessionService.class);
    
    @Autowired
    private MeterRegistry meterRegistry;
	
	@Autowired
	private VoteSessionRepository voteSessionRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	public VoteSession createVoteSession(VoteSession voteSession) {
		logger.info("Criando uma nova Sessão de Votação");
		meterRegistry.counter("votação.criar.chamadas").increment();
        long startTime = System.currentTimeMillis();
        
		if (voteSession.getStartAt().isAfter(voteSession.getEndAt())) {
			 meterRegistry.counter("votação.chamadas.falhas").increment();
             logger.error("A data de inicio da votação tem de ser antes da data de termino");
			throw new RuntimeException("A data de inicio não pode ser depois da data de termino");
		}
		
		long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("votação.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

        logger.info("Sessão de votação registrada com sucesso");
		
		return voteSessionRepository.save(voteSession);
	}
	
	 public List<VoteSession> getAllVoteSessions() {
		 long startTime = System.currentTimeMillis();
	        meterRegistry.counter("listar.votações.chamadas").increment();
	        logger.info("Listando Sessões de votação");
	        
	        long duration = System.currentTimeMillis() - startTime;
	        meterRegistry.timer("listar.votações.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

	        return voteSessionRepository.findAll();
	    }

	    public VoteSession getVoteSession(Long voteSessionId) {
	    	 logger.info("Buscando Sessão de votação com id {}", voteSessionId);
	    	long startTime = System.currentTimeMillis();
	        meterRegistry.counter("listar.votações.porId.chamadas").increment();
	        logger.info("Sessão de votação com id {} encontrada", voteSessionId);
	        return voteSessionRepository.findById(voteSessionId)	
	        		.orElseThrow(() -> {
	                    logger.warn("Sessão de votação {} não encontrada", voteSessionId);
	                    return new RuntimeException("Sessão de votação não encontrada");
	                });
	    }

	    public void deleteVoteSession(Long voteSessionId) {
	    	long startTime = System.currentTimeMillis();
	        meterRegistry.counter("deletar.votações.chamadas").increment();
	        logger.info("Eliminando Sessão de votação");
	    	
	    	VoteSession session = voteSessionRepository.findById(voteSessionId)
	    			.orElseThrow(() -> {
	    				logger.warn("Sessão {} não encontrada", voteSessionId);
	    				return new RuntimeException("Sessão de votação não encontrada");
	    			});

	        if (!voteRepository.findByVoteSession(session).isEmpty()) {
	            logger.warn("Tentativa de exclusão de votação {} falhou: possui votos registrados.", voteSessionId);
	            throw new RuntimeException("Não é possível excluir uma votação que já possui votos registrados");
	        }
	        
	        logger.info("Sessão de votação {} excluída com sucesso.", voteSessionId);
	        voteSessionRepository.delete(session);
	    }
	    
	    public Map<String, Long> getVoteResults(Long voteSessionId) {
	    	
	    	long startTime = System.currentTimeMillis();
	        meterRegistry.counter("buscar.resultado.votações.chamadas").increment();
	        logger.info("Buscando resultado da Sessão de votação com id {}", voteSessionId);
	    	
	        VoteSession session = voteSessionRepository.findById(voteSessionId)
	                .orElseThrow(() -> {
	                    logger.warn("Tentativa de consulta aos resultados da votação {} falhou: votação não encontrada.", voteSessionId);
	                    return new RuntimeException("Votação não encontrada");
	                });
			
			List<Vote> votes = voteRepository.findByVoteSession(session);
			
			long duration = System.currentTimeMillis() - startTime;
	        meterRegistry.timer("votos.resultados.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

	        logger.info("Resultados da votação {} ", voteSessionId);
			return votes.stream()
					.collect(Collectors.groupingBy(Vote::getChosenOption, Collectors.counting()));
		}
		
}
