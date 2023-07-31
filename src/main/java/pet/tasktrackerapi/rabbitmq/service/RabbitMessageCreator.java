package pet.tasktrackerapi.rabbitmq.service;

import org.springframework.stereotype.Service;
import pet.tasktrackerapi.rabbitmq.dto.EmailDto;

@Service
public class RabbitMessageCreator {
    public EmailDto createWelcomeMessage(String receiverEmail) {
        EmailDto emailDto = new EmailDto();

        emailDto.setReceiverEmail(receiverEmail);
        emailDto.setSubject("Task Tracker Registration");
        emailDto.setBody("Welcome to Task Tracker! We are glad that you have joined us. \n" +
                "You can create your own tasks and track them. \n" +
                "You can edit your tasks and mark them as completed. \n \n" +
                "Have a nice day! \n" +
                "Task Tracker Team");

        return emailDto;
    }
}