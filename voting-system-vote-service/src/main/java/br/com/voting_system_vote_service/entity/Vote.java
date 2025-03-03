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
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "voteSession_id", nullable = false)
	private VoteSession voteSession;
	
	@Column(nullable = false)
	private String chosenOption;
	
	@Column(nullable = false) 
	private LocalDateTime createdAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
}
