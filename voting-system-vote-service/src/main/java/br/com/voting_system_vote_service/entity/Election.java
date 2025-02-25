package br.com.voting_system_vote_service.entity;

import jakarta.persistence. *;
import lombok. *;

import java.time.LocalDateTime;

import java.util.List;


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
public class Election {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String title;
	
	@Column(nullable = false)
	private LocalDateTime startDate;
	
	@Column(nullable = false)
	private LocalDateTime endDate;
	
	@OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Candidate> candidates;

}
