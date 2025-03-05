package br.com.voting_system_user_service.service;

import br.com.voting_system_user_service.repository.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import br.com.voting_system_user_service.dto.UserDTO;
import br.com.voting_system_user_service.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author fsdney
 */



@Service
public class UserService {
	
private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
    private MeterRegistry meterRegistry;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	public List<UserDTO> getAllUsers() {
		 long startTime = System.currentTimeMillis();
	        meterRegistry.counter("listar.usuarios.chamadas").increment();

	        logger.info("Listando usuarios");
		
	        long duration = System.currentTimeMillis() - startTime;
	        meterRegistry.timer("listar.usuarios.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

		return userRepository.findAll().stream()
		       .map(UserDTO::new)
		       .collect(Collectors.toList());
	}
	
	public UserDTO getUserById(Long id) {
		long startTime = System.currentTimeMillis();
        meterRegistry.counter("buscar.usuario.porId.chamadas").increment();

        logger.info("Buscando usuario com id {}", id);
        
		User user = userRepository.findById(id)
		   .orElseThrow(() -> {
			   logger.warn("Usuario com id {} não encontrado", id);
			   return new RuntimeException("Usuario não encontrado");
		   });
		 
		long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("buscar.usuario.porId.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

        logger.info("Usuario encontrado com sucesso");
		  
	 return new UserDTO(user);
	}
	
	public UserDTO updateUser(Long id, User updateUser) {
		
		long startTime = System.currentTimeMillis();
        meterRegistry.counter("atualizar.dados.usuario.chamadas").increment();

        logger.info("Alterando dados do usuario com id {}", id);
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> {
				logger.warn("Usuario com id {} não encontrado", id);
				return new RuntimeException("Usuario não encontrado");
	});
		
		
		user.setUsername(updateUser.getUsername());
		user.setEmail(updateUser.getEmail());
		userRepository.save(user);
		
		long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("atualizar.dados.usuario.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

        logger.info("Dados do Usuario actualizados com sucesso");
		  
		
		return new UserDTO(user);
	}
	
	public void deleteUser(Long id) {
		
		long startTime = System.currentTimeMillis();
        meterRegistry.counter("deletar.usuario.chamadas").increment();

        logger.info("Deletando usuario com id {}", id);
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> {
					logger.warn("Usuario com id {} não encontrado", id);
				return new RuntimeException("Usuario não encontrado");
				});
		
		long duration = System.currentTimeMillis() - startTime;
        meterRegistry.timer("deletar.usuario.chamadas.tempo").record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

        logger.info("Usuario deletado com sucesso");
		
		userRepository.delete(user);
	}
	
	

}
