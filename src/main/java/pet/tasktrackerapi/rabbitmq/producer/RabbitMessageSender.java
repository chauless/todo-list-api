package pet.tasktrackerapi.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pet.tasktrackerapi.rabbitmq.dto.EmailDto;
import pet.tasktrackerapi.rabbitmq.service.RabbitMessageCreator;

@Component
@RequiredArgsConstructor
public class RabbitMessageSender {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMessageCreator rabbitMessageCreator;
    private final ObjectMapper objectMapper;

    public void sendWelcomeEmail(String receiverEmail) throws JsonProcessingException {
        String queueName = QueueName.EMAIL_SENDER_TASKS.toString();
        EmailDto emailDto = rabbitMessageCreator.createWelcomeMessage(receiverEmail);
        rabbitTemplate.convertAndSend(queueName, objectMapper.writeValueAsString(emailDto));
    }
}
