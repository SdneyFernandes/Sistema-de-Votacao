package br.com.voting_system_vote_service.entity;

import jakarta.persistence. *;
import lombok. *;

/**
 * @author fsdney
 */

@Entity
@Table(name = "table_candidate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "election_id", nullable = false)
	private Election election;
	
	private int votes; //contador de votos recebidos
}
