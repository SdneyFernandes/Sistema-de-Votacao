package br.com.voting_system_user_service.dto;

import br.com.voting_system_user_service.entity.*;
import br.com.voting_system_user_service.enums.*;


import lombok. *;



/**
 * @author fsdney
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;  

    public UserDTO(User user) {
        this.id = user.getId();         
        this.username = user.getUsername(); 
        this.email = user.getEmail();   
        this.role = user.getRole();     
    }
}
