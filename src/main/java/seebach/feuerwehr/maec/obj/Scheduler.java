package seebach.feuerwehr.maec.obj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import seebach.feuerwehr.maec.obj.Alarm;
import seebach.feuerwehr.maec.service.Core;
import seebach.feuerwehr.maec.service.CoreService;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private CoreService coreService;


    @Scheduled(fixedRate = 1000)  // alle 5 Sekunden
    public void checkAlarms() {
        List<Core> activeAlarms = coreService.getTime();
        if (!activeAlarms.isEmpty()) {
            activeAlarms.forEach(alarm -> {
                System.out.println("✅ Bearbeite Alarm: " + alarm.getId());

                Alarm tts = new Alarm();
                tts.setfach_bereich(alarm.getFachBereich());
                tts.setforces(alarm.getForces());
                tts.setfw_name(alarm.getFwName());
                tts.setmeldung(alarm.getMeldung());

                try {
                    tts.run();
                    System.out.println("✅ Erfolgreich bei TTS: " + alarm.getId());
                } catch (Exception e) {
                    System.out.println("❌ Fehler bei TTS: " + alarm.getId());
                    e.printStackTrace();
                }
                alarm.setActive(true);
                coreService.save(alarm);
            });
        }
    }
}
