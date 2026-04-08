package seebach.feuerwehr.maec.service;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioInputStream;

@Service
public class TtsService {

    private MaryInterface mary;

    @PostConstruct
    public void init() throws Exception {
        System.setProperty("mary.base", "./lib");
        mary = new LocalMaryInterface();
        mary.setVoice("bits1-hsmm");
    }

    public AudioInputStream generate(String text) throws Exception {
        return mary.generateAudio(text);
    }
}
