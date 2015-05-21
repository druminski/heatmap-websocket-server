package hello;

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

    public void merge(List<LocalizationMessage> localizations) {
        localizations.forEach(localization -> localizationsPerCity.put(localization.getName(), localization));
    }

    public List<LocalizationMessage> takeSnapshot() {
        return new ArrayList(localizationsPerCity.values());
    }

    public void test(HelloMessage localizations) {
        this.localizations = localizations;
    }

    public HelloMessage get() {
        return localizations;
    }
}
