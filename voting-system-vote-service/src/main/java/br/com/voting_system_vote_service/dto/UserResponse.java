package br.com.voting_system_vote_service.dto;

import lombok. *;
import br.com.voting_system_user_service.enums.Role;

/**
 * @author fsdney
 */

@Getter
@Setter
public class UserResponse {
	private Long id;
	private String username;
	private String email;
	private Role role;
}
