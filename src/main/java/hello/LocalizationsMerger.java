package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import hello.api.LocalizationMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class LocalizationsMerger {

    private LoadingCache<String, LocalizationMessage> localizationsCache;
    private ObjectMapper objectMapper = new ObjectMapper();

    public LocalizationsMerger() {
        localizationsCache = CacheBuilder.newBuilder()
            .expireAfterAccess(120, TimeUnit.SECONDS)
            .removalListener(notification -> {
                System.out.println("City expiration: " + notification.getKey());
            })
            .build(
                    new CacheLoader<String, LocalizationMessage>() {
                        public LocalizationMessage load(String city) {
                            return null;
                        }
                    });
        localizationsCache.put("Warszawa", new LocalizationMessage("Warszawa", "Mazowieckie", "52.259", "21.020", 1));
    }

    public void merge(List<LocalizationMessage> localizations) {
        localizations.forEach(localization -> localizationsCache.put(localization.getName(), localization));
    }

    public byte[] takeSnapshotAsBytes() {
        try {
            return objectMapper.writeValueAsBytes(new ArrayList(localizationsCache.asMap().values()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
