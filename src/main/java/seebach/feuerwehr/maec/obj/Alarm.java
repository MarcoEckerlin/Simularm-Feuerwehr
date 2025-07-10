package seebach.feuerwehr.maec.obj;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.concurrent.CountDownLatch;


public class Alarm {
    String fw_name = "Feuerwehr Seebach";
    String fach_bereich;
    String meldung;
    String ort;
    String forces;

    public void setfw_name(String args){
        this.fw_name = args;
    }
    public void setmeldung(String args){
        this.meldung = args;
    }
    public void setfach_bereich(String args){
        this.fach_bereich = (args.replaceAll("", " ").trim()).replace(".", "Punkt");
    }
    public void setort(String args){
        this.ort = args;
    }
    public void setforces(String args){
        this.forces = args;
    }

    public boolean run() throws Exception {
        System.setProperty("mary.base", "./lib");
        MaryInterface maryTTS = new LocalMaryInterface();
        maryTTS.setVoice("bits1-hsmm");

        File gongFile = new File("src/main/resources/audio/gong.wav");
        playAudioFileBlocking(gongFile);

        String text = "Einsatz für die " + fw_name + "!" + fach_bereich + " " +  meldung + ". Es fahren: " + forces;
        AudioInputStream ttsAudio = maryTTS.generateAudio(text);

        playAudioStreamBlocking(ttsAudio);

        Thread.sleep(15*1000);

        playAudioFileBlocking(gongFile);

        AudioInputStream ttsAudio2 = maryTTS.generateAudio(text);
        playAudioStreamBlocking(ttsAudio2);
        return true;
    }

    private void playAudioFileBlocking(File audioFile) throws Exception {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        playAudioStreamBlocking(audioStream);
    }

    private void playAudioStreamBlocking(AudioInputStream audioStream) throws Exception {
        Clip clip = AudioSystem.getClip();
        CountDownLatch latch = new CountDownLatch(1);

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                latch.countDown();
            }
        });

        clip.open(audioStream);
        clip.start();
        latch.await();
        clip.close();
    }


}
