package seebach.feuerwehr.maec.obj;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.modules.synthesis.Voice;
import marytts.language.de.GermanConfig;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

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

    public boolean run() throws MaryConfigurationException, SynthesisException, LineUnavailableException, IOException, InterruptedException {
        System.setProperty("mary.base", "lib"); // optional, wenn du sicherstellen willst, dass Mary dort sucht


        MaryInterface maryTTS = new LocalMaryInterface();
        maryTTS.setLocale(Locale.GERMAN);
//        maryTTS.setVoice("bits1-hsmm");
        maryTTS.setVoice("voice-dfki-pavoque-neutral");

// Text-to-Speech ausführen
        String text = "Achtung! Einsatz für die Feuerwehr Seebach.";
        AudioInputStream audio = maryTTS.generateAudio(text);


        // Abspielen
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();

        // Warten bis fertig
        while (!clip.isRunning()) Thread.sleep(10);
        while (clip.isRunning()) Thread.sleep(10);
        clip.close();

        return true;
    }

}
