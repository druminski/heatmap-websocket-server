package hello;

import hello.api.LocalizationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LocalizationsPusher {

    @Autowired
    @Qualifier("brokerChannel")
    MessageChannel messageChannel;

    @Autowired
    LocalizationsMerger merger;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void start() {
        SimpMessagingTemplate template = new SimpMessagingTemplate(messageChannel);
        template.setDefaultDestination("/topic/greetings");

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                template.send(new Message<byte[]>() {
                    @Override
                    public byte[] getPayload() {
                        return merger.takeSnapshotAsBytes();
                    }

                    @Override
                    public MessageHeaders getHeaders() {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("destination", "/topic/greetings");
                        map.put("content-type", "application/json;charset=UTF-8");

                        return new MessageHeaders(map);
                    }
                });
            } catch (RuntimeException e) {
                System.err.println(e);
            }
        }, 5000, 5000, TimeUnit.MILLISECONDS);

    }
}
