package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EndpointReceiver {

    @RequestMapping("/localizations")
    public void receive(HelloMessage helloMessage) {

    }
}
