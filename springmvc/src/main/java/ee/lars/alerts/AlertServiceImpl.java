package ee.lars.alerts;

import ee.lars.springmvc.spittr.Spittle;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class AlertServiceImpl implements AlertService {

    private RabbitTemplate rabbit;

    @Autowired
    public AlertServiceImpl(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void sendSpittleAlert(Spittle spittle) {
        this.rabbit.convertAndSend("spittle.alert.exchange", "spittle.alerts", spittle);
    }

    public void sendHello(){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("my-header", "hello");
        Message helloMessage = new Message("Hello World!".getBytes(), messageProperties);
        this.rabbit.send("hello.exchange", "hello.routing", helloMessage);

    }
}