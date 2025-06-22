package br.com.voting_system_vote_service.service;

import br.com.voting_system_vote_service.repository.*;
import br.com.voting_system_vote_service.entity.Vote;
import br.com.voting_system_vote_service.entity.VoteSession;
import br.com.voting_system_vote_service.dto. *;
import br.com.voting_system_user_service.dto. *;
import br.com.voting_system_vote_service.client. *;
import br.com.voting_system_vote_service.enums.VoteStatus;


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
    
    @Autowired
    private UserServiceClient userServiceClient;
    
    public VoteSessionDTO createVoteSession(VoteSessionDTO voteSessionDTO) {
        logger.info("Criando uma nova Sessão de Votação");
        meterRegistry.counter("votacao.criar.chamadas").increment();
        long startTime = System.currentTimeMillis();

        UserDTO creator;
        try {
            creator = userServiceClient.getUserById(voteSessionDTO.getCreatorId());
        } catch (Exception e) {
            logger.error("Erro ao buscar usuário criador: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar usuário criador", e);
        }

        if (!"ADMIN".equals(creator.getRole())) {
            logger.warn("Usuário {} não tem permissão para criar votação", creator.getId());
            throw new RuntimeException("Apenas administradores podem criar votações");
        }

        if (voteSessionDTO.getStartAt().isAfter(voteSessionDTO.getEndAt())) {
            meterRegistry.counter("votacao.chamadas.falhas").increment();
            logger.error("A data de início deve ser antes da data de término");
            throw new RuntimeException("A data de início não pode ser depois da data de término");
        }

        VoteSession voteSession = new VoteSession();
        voteSession.setTitle(voteSessionDTO.getTitle());
        voteSession.setStatus(VoteStatus.ACTIVE);
        voteSession.setStartAt(voteSessionDTO.getStartAt());
        voteSession.setEndAt(voteSessionDTO.getEndAt());
        voteSession.setCreatorId(creator.getId()); // use ID, ou mapeie manual se entidade tiver `User` embutido

        VoteSession savedSession = voteSessionRepository.save(voteSession);
        logger.info("Sessão de votação registrada com sucesso");

        return new VoteSessionDTO(savedSession);
    }    
    public List<VoteSessionDTO> getAllVoteSessions() {
        long startTime = System.currentTimeMillis();
        meterRegistry.counter("listar.votacoes.chamadas").increment();
        logger.info("Listando Sessões de votação");
        
        List<VoteSessionDTO> sessions = voteSessionRepository.findAll().stream()
            .map(VoteSessionDTO::new)
            .collect(Collectors.toList());
        
        long duration = System.currentTimeMillis() - startTime;
        return sessions;
    }
    
    public VoteSessionDTO getVoteSession(Long voteSessionId) {
        logger.info("Buscando Sessão de votação com id {}", voteSessionId);
        VoteSession session = voteSessionRepository.findById(voteSessionId)
            .orElseThrow(() -> new RuntimeException("Sessão de votação não encontrada"));
        return new VoteSessionDTO(session);
    }
    
    public void deleteVoteSession(Long voteSessionId) {
        logger.info("Eliminando Sessão de votação");
        VoteSession session = voteSessionRepository.findById(voteSessionId)
            .orElseThrow(() -> new RuntimeException("Sessão de votação não encontrada"));
        
        if (!voteRepository.findByVoteSession(session).isEmpty()) {
            logger.warn("Tentativa de exclusão falhou: sessão {} possui votos registrados", voteSessionId);
            throw new RuntimeException("Não é possível excluir uma votação que já possui votos registrados");
        }
        
        voteSessionRepository.delete(session);
        logger.info("Sessão de votação {} excluída com sucesso", voteSessionId);
    }
    
    public Map<String, Long> getVoteResults(Long voteSessionId) {
        logger.info("Buscando resultado da Sessão de votação com id {}", voteSessionId);
        VoteSession session = voteSessionRepository.findById(voteSessionId)
            .orElseThrow(() -> new RuntimeException("Votação não encontrada"));
        
        List<Vote> votes = voteRepository.findByVoteSession(session);
        return votes.stream()
            .collect(Collectors.groupingBy(Vote::getChosenOption, Collectors.counting()));
    }
}
