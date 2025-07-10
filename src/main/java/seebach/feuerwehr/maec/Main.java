package seebach.feuerwehr.maec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import seebach.feuerwehr.maec.obj.Alarm;
import seebach.feuerwehr.maec.obj.Durchsage;
import seebach.feuerwehr.maec.obj.GUI;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        new GUI();
        SpringApplication.run(Main.class, args);
        //      Standard Information und Test des System
        Durchsage durchsage = new Durchsage();
        durchsage.senden("Information, das Melde System ist gestartet und bereit!");

//        //Test
        Alarm alarm = new Alarm();
        alarm.setfw_name("Feuerwehr Seebach");
        alarm.setort("Schwarzwaldhochstraße");
        alarm.setfach_bereich("THW");
        alarm.setmeldung("Person unter LKW         RTW bereits auf dem Weg");
        alarm.setforces("Seebach 42, Seebach 41, Seebach 19");
        alarm.run();


    }
}