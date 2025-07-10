package seebach.feuerwehr.maec.obj;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import org.springframework.stereotype.Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.concurrent.CountDownLatch;

@Controller
public class Durchsage {

    public void senden(String message) throws Exception {
        System.setProperty("mary.base", "./lib");
        MaryInterface maryTTS = new LocalMaryInterface();
        maryTTS.setVoice("bits1-hsmm");


        File gongFile = new File("src/main/resources/audio/info.wav");
        playAudioFileBlocking(gongFile);

        String text = message;
        AudioInputStream ttsAudio = maryTTS.generateAudio(text);

        playAudioStreamBlocking(ttsAudio);
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
