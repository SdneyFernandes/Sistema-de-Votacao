package br.com.voting_system_vote_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.voting_system_vote_service.entity.Vote;

import java.util.List;


/**
 * @author fsdney
 */


@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    // Verifica se o usuário já votou na eleição
    boolean existsByElectionIdAndUserId(Long electionId, Long userId);
    
    // Conta os votos de um determinado candidato em uma determinada eleição
    long countByElectionIdAndCandidateId(Long electionId, Long candidateId);
    
    // Retorna todos os votos de uma eleição
    List<Vote> findByElectionId(Long electionId); 
}
