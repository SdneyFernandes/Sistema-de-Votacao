package br.com.voting_system_user_service.service;

import br.com.voting_system_user_service.repository.UserRepository;
import br.com.voting_system_user_service.dto.UserDTO;
import br.com.voting_system_user_service.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author fsdney
 */



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream()
		       .map(UserDTO::new)
		       .collect(Collectors.toList());
	}
	
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
		   .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
	 return new UserDTO(user);
	}
	
	public UserDTO updateUser(Long id, User updateUser) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
		user.setUsername(updateUser.getUsername());
		user.setEmail(updateUser.getEmail());
		userRepository.save(user);
		return new UserDTO(user);
	}
	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
		userRepository.delete(user);
	}
	
	

}
