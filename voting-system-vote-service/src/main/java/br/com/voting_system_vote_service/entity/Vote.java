package br.com.voting_system_vote_service.entity;

import jakarta.persistence. *;
import lombok. *;

import java.time.LocalDateTime;

/**
 * @author fsdney
 */

@Entity
@Table(name = "table_vote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "candidate_id", nullable = false)
	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name = "election_id", nullable = false)
	private Election election;
	
	@Column(nullable = false) 
	private LocalDateTime timesStamp;
}
