package seebach.feuerwehr.maec.obj;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;


public class Alarm {
    String fw_name;
    String fach_bereich;
    String meldung;
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
    public void setforces(String args){
        this.forces = args;
    }

    public boolean run() throws Exception {
        System.setProperty("mary.base", "./lib");
        MaryInterface maryTTS = new LocalMaryInterface();
        maryTTS.setVoice("bits1-hsmm");

        InputStream is = getClass().getClassLoader().getResourceAsStream("audio/gong.wav");
        playAudioFileBlocking(is);

        String text = "Einsatz für die " + fw_name + "!" + fach_bereich + " " +  meldung + ". Es fahren: " + forces;
        AudioInputStream ttsAudio = maryTTS.generateAudio(text);

        playAudioStreamBlocking(ttsAudio);
        return true;
    }

    private void playAudioFileBlocking(InputStream audioFile) throws Exception {
        if (audioFile == null) {
            throw new FileNotFoundException("Audio resource not found!");
        }

        File tempFile = File.createTempFile("audio-", ".wav");
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = audioFile.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
        }

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(tempFile);
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
