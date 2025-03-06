package ro.tuc.ds2020;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "${rabbitmq.queue-name}")
    //public void receiveMessage(String message) {
    //    System.out.println("Received Message: " + message);
    // }
    public void receiveMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(message);
            System.out.println("Parsed Message: " + jsonNode.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
