package seebach.feuerwehr.maec.obj;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;
import seebach.feuerwehr.maec.service.SettingService;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;


@Component
public class Alarm {

    private final SettingService settingService;

    @Autowired
    public Alarm(SettingService settingService) {
        this.settingService = settingService;
    }

    public boolean run(String fw_name, String fach_bereich, String meldung, String forces, String address, String id) throws Exception {

        Divera divera = new Divera();
        divera.run(settingService.getValue("divera-api-key"),settingService.getValue("divera-api-ric"), fw_name, fach_bereich, meldung, address, id);

        long sleeper = 15000;
        sleeper = Long.parseLong(settingService.getValue("divera-sleeper"));
        Thread.sleep(sleeper);

        System.setProperty("mary.base", "./lib");
        MaryInterface maryTTS = new LocalMaryInterface();
        maryTTS.setVoice("bits1-hsmm");
        String textdb = settingService.getValue("alarm-text");
        String text = textdb.replace("%feuerwehr%", fw_name)
                .replace("%fachbereich%", fach_bereich.replaceAll("", " ").trim().replace(".", "Punkt"))
                .replace("%meldung%", meldung)
                .replace("%fahrzeuge%", forces)
                .replace("%ort%", address.replace(">" , " "));

        AudioInputStream ttsAudio = maryTTS.generateAudio(text);
        AudioInputStream ttsAudio2 = maryTTS.generateAudio(text);

        InputStream fs = getClass().getClassLoader().getResourceAsStream("audio/gong.wav");

        playAudioFileBlocking(fs);
        playAudioStreamBlocking(ttsAudio);

        Thread.sleep(sleeper);
        InputStream se = getClass().getClassLoader().getResourceAsStream("audio/gong.wav");

        playAudioFileBlocking(se);
        playAudioStreamBlocking(ttsAudio2);

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
