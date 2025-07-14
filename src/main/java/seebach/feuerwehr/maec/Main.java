package seebach.feuerwehr.maec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import seebach.feuerwehr.maec.obj.Alarm;
import seebach.feuerwehr.maec.obj.Durchsage;
import seebach.feuerwehr.maec.obj.GUI;

import javax.swing.*;
import java.util.Arrays;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        if (Arrays.asList(args).contains("--gui")) {
//            SwingUtilities.invokeLater(() -> new GUI());
            if (System.getProperty("spring.devtools.restart.enabled") == null ||
                    Boolean.getBoolean("spring.devtools.restart.enabled")) {
                if (!isRestartThread()) {
                    SwingUtilities.invokeLater(() -> {
                        new GUI();
                    });
                }
            }
        }

        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                String portValue = arg.split("=")[1];
                System.setProperty("server.port", portValue);
            }
        }

        SpringApplication.run(Main.class, args);

        // Standard Information und Test des System
        Durchsage durchsage = new Durchsage();
        durchsage.senden("Information, das Melde System ist gestartet und bereit!");

//        //Test
//        Alarm alarm = new Alarm();
//        alarm.setfw_name("Feuerwehr Seebach");
//        alarm.setort("Schwarzwaldhochstraße");
//        alarm.setfach_bereich("THW");
//        alarm.setmeldung("Person unter LKW         RTW bereits auf dem Weg");
//        alarm.setforces("Seebach 42, Seebach 41, Seebach 19");
//        alarm.run();

    }
    private static boolean isRestartThread() {
        // check ob im DevTools Restart-Thread
        return Thread.currentThread().getName().contains("restartedMain");
    }
}