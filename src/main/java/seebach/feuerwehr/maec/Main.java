package seebach.feuerwehr.maec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import seebach.feuerwehr.maec.obj.Durchsage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@SpringBootApplication
@EnableScheduling
public class Main {

    static String port;

    public static void main(String[] args) throws Exception {
        String portValue = null;

        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                String value = arg.split("=", 2)[1];
                if (!value.isBlank()) {
                    portValue = value;
                    break;
                }
            }
        }

        if (portValue == null) {
            portValue = "80";
        }

        System.setProperty("server.port", portValue);
        SpringApplication.run(Main.class, args);

        System.out.println("=================================================================================");
        System.out.println("\n");
        System.out.println("Öffne die AlarmWebseite mit: http://localhost:" + portValue + "/");
        System.out.println("\n");
        System.out.println("=================================================================================");
        System.out.println("by Eckerlin Developments | made for Feuerwehr Seebach, Baden-Würtenberg, Germany");
    }
    private static boolean isRestartThread() {
        // check ob im DevTools Restart-Thread
        return Thread.currentThread().getName().contains("restartedMain");
    }
}