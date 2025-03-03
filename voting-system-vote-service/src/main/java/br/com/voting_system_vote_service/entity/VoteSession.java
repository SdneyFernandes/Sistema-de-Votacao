package br.com.voting_system_vote_service.entity;

import br.com.voting_system_vote_service.enums.VoteStatus;

import jakarta.persistence. *;
import lombok. *;
import java.util.List;

import java.time.LocalDateTime;

/**
 * @author fsdney
 */

@Entity
@Table(name = "table_voteSession")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@ElementCollection
	private List<String> options;
	
	@Column(nullable = false)
	private Long creatorId;
	
	@Column(nullable = false)
	private LocalDateTime endAt;
	
	@Column(nullable = false)
	private LocalDateTime startAt;
	
	@Enumerated(EnumType.STRING)
	private VoteStatus status;
	
	@PrePersist
	public void prePersist() {
		if (LocalDateTime.now().isBefore(startAt)) {
			status = VoteStatus.NOT_STARTED;
		} else if (LocalDateTime.now().isAfter(endAt)) {
			status = VoteStatus.ENDED;
		} else {
			status = VoteStatus.ACTIVE;
		}
	}
	
	
	
	
}
