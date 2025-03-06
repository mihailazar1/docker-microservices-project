package ro.tuc.ds2020;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queue name from application.properties
    private final String queueName = "smart-metering";

    @Bean
    public Queue queue() {
        return new Queue(queueName, true, false, false); // Ensure durable queue
    }
}
