package br.com.voting_system_notification_service.service;

import br.com.notification_service.dto.VoteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author fsdney
 */

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final SimpMessagingTemplate messagingTemplate;

    public void notifyVote(VoteEvent event) {
        sendEmail(event);
        sendWebSocket(event);
    }

    private void sendEmail(VoteEvent event) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(event.getEmail());
        mail.setSubject("Confirmação de Voto");
        mail.setText("Olá, seu voto na opção '" + event.getChosenOption() + "' foi registrado com sucesso!");
        mailSender.send(mail);
    }

    private void sendWebSocket(VoteEvent event) {
        messagingTemplate.convertAndSendToUser(
            event.getUserId().toString(),
            "/queue/notifications",
            "✅ Seu voto foi registrado com sucesso na sessão #" + event.getVoteSessionId()
        );
    }
}
