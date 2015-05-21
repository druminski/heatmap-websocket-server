package hello;

import hello.api.LocalizationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LocalizationController {
    @Autowired
    LocalizationsMerger merger;

    @RequestMapping(value = "/localization", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String receive(@RequestBody List<LocalizationMessage> messages) {
        merger.merge(messages);
        return "ok";
    }

}
