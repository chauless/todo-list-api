package pet.tasktrackerapi.rabbitmq.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailDto {

    @Email
    private String receiverEmail;

    private String subject;

    private String body;
}