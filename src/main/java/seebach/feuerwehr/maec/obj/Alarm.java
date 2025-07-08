package seebach.feuerwehr.maec.obj;
import marytts.LocalMaryInterface;
import marytts.MaryInterface;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Alarm {
    String fw_name;
    String meldung;
    String ort;
    String forces;

    public void setmeldung(String args){
        this.meldung = args;
    }
    public void setort(String args){
        this.ort = args;
    }
    public void setforces(String args){
        this.forces = args;
    }

    public boolean run(){

        MaryInterface maryTTS = new LocalMaryInterface();
        return true;
    }

}
