package seebach.feuerwehr.maec.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seebach.feuerwehr.maec.obj.Durchsage;
import seebach.feuerwehr.maec.service.Core;
import seebach.feuerwehr.maec.service.CoreService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private CoreService coreService;

    @GetMapping("/")


    public String index(Model model) {
        List<Core> alarms = coreService.getAll();

        model.addAttribute("alarms", alarms);

        return "index";
    }

    @PostMapping("/alarm/durchsage")
    public String startAlarm(@RequestParam("message") String message) throws Exception {
        if (!(message.length() <= 0)){
            System.out.println("Durchsage: " + message);
            Durchsage durchsage = new Durchsage();
            durchsage.senden(message);
        }
        return "redirect:/";
    }

    @PostMapping("/alarm/add")
    public String addAlarm(@RequestParam String fwName,
                           @RequestParam String fachBereich,
                           @RequestParam String meldung,
                           @RequestParam String ort,
                           @RequestParam String forces,
                           @RequestParam String time) {
        // Parsen von String zu LocalDateTime
        LocalDateTime alarmTime = LocalDateTime.parse(time);
        Core core = new Core(fwName, fachBereich, meldung, ort, forces, alarmTime);
        if (!(core.toString().length() <= 0)){
            coreService.save(core);
            System.out.println(coreService.getAll());
        }
        return "redirect:/";

    }


}
