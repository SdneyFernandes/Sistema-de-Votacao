package br.com.voting_system_user_service.dto;

import br.com.voting_system_user_service.entity.*;


import lombok. *;



/**
 * @author fsdney
 */


@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	
	private Long id;
	private String username;
	private String email;
	
	// Construtor que aceita um objeto User
    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

}
