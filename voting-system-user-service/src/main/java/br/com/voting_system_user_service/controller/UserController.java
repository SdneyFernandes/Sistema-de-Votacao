package br.com.voting_system_user_service.controller;

import br.com.voting_system_user_service.dto.UserDTO;
import br.com.voting_system_user_service.entity.User;
import br.com.voting_system_user_service.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


/**
 * @author fsdney
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
	
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		logger.info("Recebida requisição para criar listar todos usuarios");
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		logger.info("Recebida requisição para buscar Usuario por Id");
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User updateUser) {
		logger.info("Recebida requisição para actualizar dados de usuario");
		return ResponseEntity.ok(userService.updateUser(id, updateUser));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		logger.info("Recebida requisição para deletar usuario");
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	 

	

}
