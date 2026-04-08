package seebach.feuerwehr.maec.service;

import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

@Service
public class AudioPlaybackService {

    public void playClasspathAudioBlocking(String resourcePath) throws Exception {
        try (InputStream raw = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (raw == null) {
                throw new FileNotFoundException("Audio resource not found: " + resourcePath);
            }
            try (BufferedInputStream buffered = new BufferedInputStream(raw);
                 AudioInputStream audioStream = AudioSystem.getAudioInputStream(buffered)) {
                playAudioStreamBlocking(audioStream);
            }
        }
    }

    public void playAudioStreamBlocking(AudioInputStream audioStream) throws Exception {
        Clip clip = AudioSystem.getClip();
        CountDownLatch latch = new CountDownLatch(1);

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                latch.countDown();
            }
        });

        try {
            clip.open(audioStream);
            clip.start();
            latch.await();
        } finally {
            clip.close();
        }
    }
}
