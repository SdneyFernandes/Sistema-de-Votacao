package br.com.voting_system_user_service.entity;


import br.com.voting_system_user_service.enums.Role;
import jakarta.persistence. *;
import lombok. *;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author fsdney
 */
@Entity
@Table(name = "table_user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private  String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.USER;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
}
