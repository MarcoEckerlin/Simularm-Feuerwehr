package seebach.feuerwehr.maec.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seebach.feuerwehr.maec.obj.Durchsage;
import seebach.feuerwehr.maec.service.Core;
import seebach.feuerwehr.maec.service.CoreService;
import seebach.feuerwehr.maec.obj.Setting;
import seebach.feuerwehr.maec.service.SettingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PageController {
    private final CoreService coreService;
    private final SettingService settingService;

    @Autowired
    public PageController(CoreService coreService, SettingService settingService) {
        this.coreService = coreService;
        this.settingService = settingService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Core> alarms = coreService.getAll();
        Map<String, String> settingsMap = settingService.getAllSettings()
                .stream()
                .collect(Collectors.toMap(Setting::getSetting, Setting::getValue));
        model.addAttribute("alarms", alarms);
        model.addAttribute("settings", settingsMap);
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

    @PostMapping("/alarm/edit")
    public String editAlarm(@RequestParam String fwName,
                           @RequestParam String fachBereich,
                           @RequestParam String meldung,
                           @RequestParam String ort,
                           @RequestParam String forces,
                           @RequestParam String time,
                            @RequestParam String id) {
        LocalDateTime alarmTime = LocalDateTime.parse(time);
        Core core = new Core(fwName, fachBereich, meldung, ort, forces, alarmTime);
        long alarmId = Long.parseLong(id);
        Optional<Core> alarm = coreService.getId(alarmId);
        if (alarm.isPresent()){
            coreService.write(alarmId, core);
        }
        return "redirect:/";

    }

    @PostMapping("/alarm/delete")
    public String deleteAlarm(@RequestParam String id) {
        long alarmId = Long.parseLong(id);
        Optional<Core> alarm = coreService.getId(alarmId);
        if (alarm.isPresent()){
            coreService.delete(alarmId);
        }
        return "redirect:/";

    }

    @PostMapping("/alarm/setting")
    public String saveSettings(@RequestParam Map<String, String> params) {
        params.forEach(settingService::saveSetting);
        return "redirect:/";
    }


}
