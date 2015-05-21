package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.api.LocalizationMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalizationsMerger {

    Map<String, LocalizationMessage> localizationsPerCity = new ConcurrentHashMap<>();
    private HelloMessage localizations;
    private ObjectMapper objectMapper = new ObjectMapper();

    public LocalizationsMerger() {
        localizationsPerCity.put("Warszawa", new LocalizationMessage("Warszawa", "Mazowieckie", "52.259", "21.020", 5));
    }

    public void merge(List<LocalizationMessage> localizations) {
        localizations.forEach(localization -> localizationsPerCity.put(localization.getName(), localization));
    }

    public byte[] takeSnapshotAsBytes() {
        try {
            return objectMapper.writeValueAsBytes(new ArrayList(localizationsPerCity.values()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void test(HelloMessage localizations) {
        this.localizations = localizations;
    }

    public HelloMessage get() {
        return localizations;
    }
}
