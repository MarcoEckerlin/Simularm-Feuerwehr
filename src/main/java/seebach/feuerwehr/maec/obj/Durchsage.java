package seebach.feuerwehr.maec.obj;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import org.springframework.stereotype.Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

@Controller
public class Durchsage {

    public void senden(String message) throws Exception {
        System.setProperty("mary.base", "./lib");
        MaryInterface maryTTS = new LocalMaryInterface();
        maryTTS.setVoice("bits1-hsmm");

        InputStream is = getClass().getClassLoader().getResourceAsStream("audio/info.wav");
        playAudioFileBlocking(is);

        String text = message;
        AudioInputStream ttsAudio = maryTTS.generateAudio(text);

        playAudioStreamBlocking(ttsAudio);
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
