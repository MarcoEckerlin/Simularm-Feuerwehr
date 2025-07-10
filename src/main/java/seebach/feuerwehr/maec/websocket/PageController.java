package seebach.feuerwehr.maec.websocket;

import org.springframework.web.bind.annotation.GetMapping;

public class PageController {
    @GetMapping("/")
    public String index() {
        return "index"; // src/main/resources/templates/index.html
    }
    @GetMapping("/settings")
    public String settings() {
        return "settings"; // src/main/resources/templates/settings.html
    }
}
