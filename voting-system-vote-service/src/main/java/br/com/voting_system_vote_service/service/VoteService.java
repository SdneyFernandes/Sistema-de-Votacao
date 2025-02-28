package br.com.voting_system_vote_service.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.voting_system_vote_service.repository.*;
import br.com.voting_system_user_service.client.*;
import br.com.voting_system_vote_service.client.UserCliente;
import br.com.voting_system_vote_service.client.UserResponse;
import br.com.voting_system_vote_service.entity.*;

import java.util.List;


/**
 * @author fsdney
 */

@Service
public class VoteService {
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private UserCliente userCliente;
	
	@Autowired
	private ElectionRepository electionRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	public String registerVote(Long userId, Long electionId, Long candidateId) {
		
		UserResponse user = userCliente.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("Usuario não encontrado!");
        }
        
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new RuntimeException("Candidato não encontrado!"));
		
		Election election = electionRepository.findById(electionId)
				.orElseThrow(() -> new RuntimeException("Eleição não encontrada"));
		
		if (LocalDateTime.now().isBefore(election.getStartDate())) {
			throw new RuntimeException("A votação ainda não começou!");
		}
		
		if (LocalDateTime.now().isAfter(election.getEndDate())) {
			throw new RuntimeException("A votação ja foi encerrada!");
		}
		
		if (voteRepository.existsByElectionUserId(electionId, userId)) {
			throw new RuntimeException("Usuario ja votou nesta eleição!");
		}
		
		
		Vote vote = new Vote();
	    vote.setUserId(userId); 
	    vote.setElection(election);
	    vote.setCandidate(candidate);
	    vote.setTimesStamp(LocalDateTime.now());
		
		voteRepository.save(vote);
		return "Voto registrado com sucesso!";
		
	}
	
	public long countVotes(Long electionId, Long candidateId) {
		return voteRepository.countByElectionIdAndCandidateId(electionId, candidateId);
	}
	
	public List<Vote> getVotesByElection(Long electionId) {
		return voteRepository.findByElectionId(electionId);
	}
	
	
	

}
