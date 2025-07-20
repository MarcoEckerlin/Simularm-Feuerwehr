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

    private final Alarm alarm;
    private final CoreService coreService;

    @Autowired
    public Scheduler(Alarm alarm, CoreService coreService) {
        this.alarm = alarm;
        this.coreService = coreService;
    }

    @Scheduled(fixedRate = 1000)
    public void checkAlarms() {
        List<Core> activeAlarms = coreService.getTime();
        if (!activeAlarms.isEmpty()) {
            activeAlarms.forEach(coreAlarm -> {
                System.out.println("✅ Bearbeite Alarm: " + coreAlarm.getId());
                try {
                    alarm.run(coreAlarm.getFwName(), coreAlarm.getFachBereich(), coreAlarm.getMeldung(), coreAlarm.getForces(), coreAlarm.getOrt(), String.valueOf(coreAlarm.getId()));
                    System.out.println("✅ Alarm wurde bearbeitet.");
                } catch (Exception e) {
                    System.out.println("❌ Fehler beim Bearbeiten des Alarms:");
                    e.printStackTrace();
                }
                coreAlarm.setActive(true);
                coreService.save(coreAlarm);
            });
        }
    }
}

