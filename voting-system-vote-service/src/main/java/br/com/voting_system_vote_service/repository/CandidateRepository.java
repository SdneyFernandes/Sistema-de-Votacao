package br.com.voting_system_vote_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.voting_system_vote_service.entity.Candidate;

/**
 * @author fsdney
 */

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
