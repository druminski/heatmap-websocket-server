package hello;

import hello.api.LocalizationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LocalizationsReceiver {

    @Autowired
    LocalizationsMerger localizationsMerger;

    @MessageMapping("/hello")
//    public void greeting(List<LocalizationMessage> localizations) throws Exception {
    public void greeting(HelloMessage localizations) throws Exception {
        System.out.println("received message");
        //localizationsMerger.merge(localizations);
        localizationsMerger.test(localizations);
    }

}
