package br.com.voting_system_vote_service.entity;

import br.com.voting_system_vote_service.enums.VoteStatus;
import br.com.voting_system_user_service.entity.*;

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
	
	@Column(name = "creator_id", nullable = false)
	private Long creatorId;
	
	@Column(nullable = false)
	private LocalDateTime endAt;
	
	@Column(nullable = false)
	private LocalDateTime startAt;
	
	@Enumerated(EnumType.STRING)
	private VoteStatus status;
	
	
	//define o status baseado na data do servidor no momento da  eleição e atualiza dinamicamente
	//evita que votações antigas apareçam ainda como ativas
	public void updateStatus() {
		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(startAt)) {
			status = VoteStatus.NOT_STARTED;
		} else if (now.isAfter(endAt)) {
			status = VoteStatus.ENDED;
		} else {
			status = VoteStatus.ACTIVE;
		}
	}
	
	
	
	
}
