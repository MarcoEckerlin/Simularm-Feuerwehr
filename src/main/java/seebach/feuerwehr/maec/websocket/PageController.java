package seebach.feuerwehr.maec.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seebach.feuerwehr.maec.obj.Durchsage;

@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @PostMapping("/alarm/start")
    public String startAlarm(@RequestParam("message") String message) throws Exception {
        System.out.println("Empfangen: " + message);
        Durchsage durchsage = new Durchsage();
        durchsage.senden(message);
        return "redirect:/";
    }
}
