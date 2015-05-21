package hello;

import hello.api.LocalizationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
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
//        scheduledExecutorService.scheduleWithFixedDelay(() -> executorSubscribableChannel.send(new Message<List<LocalizationMessage>>() {
//            @Override
//            public List<LocalizationMessage> getPayload() {
//                return merger.takeSnapshot();
//            }
//
//            @Override
//            public MessageHeaders getHeaders() {
//                return new MessageHeaders(null);
//            }
//        }), 1000, 1000, TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("sending message");
            try {
                messageChannel.send(new Message<HelloMessage>() {
                    @Override
                    public HelloMessage getPayload() {
                        return merger.get();
                    }

                    @Override
                    public MessageHeaders getHeaders() {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("", "");
                        return new MessageHeaders(map);
                    }
                });
            } catch (RuntimeException e) {
                System.out.println(e);
            }
        }, 5000, 5000, TimeUnit.MILLISECONDS);

    }
}
