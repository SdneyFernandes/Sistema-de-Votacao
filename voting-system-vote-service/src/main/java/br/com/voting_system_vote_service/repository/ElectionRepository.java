package br.com.voting_system_vote_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.voting_system_vote_service.entity.Election;


/**
 * @author fsdney
 */

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

}
