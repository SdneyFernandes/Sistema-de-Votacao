package br.com.voting_system_vote_service.dto;

import lombok. *;

/**
 * @author fsdney
 */

@Getter
@Setter
public class UserResponse {
	private Long id;
	private String username;
	private String email;
}
