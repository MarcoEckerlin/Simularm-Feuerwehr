package seebach.feuerwehr.maec;

import seebach.feuerwehr.maec.obj.Alarm;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Alarm alarm = new Alarm();
        alarm.setort("Ruhesteinstr. 14");
        alarm.setmeldung("B3.1 Gebäudevollbrand");
        alarm.setforces("Seebach 42, Seebach 41, Seebach 19");
        alarm.run();
    }
}