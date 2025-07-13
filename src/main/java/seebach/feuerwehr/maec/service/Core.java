package seebach.feuerwehr.maec.service;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Core {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fwName;
    private String fachBereich;
    private String meldung;
    private String ort;
    private String forces;
    private LocalDateTime timestamp;

    // Getter, Setter, Konstruktoren
    public Core() {}

    public Core(String fwName, String fachBereich, String meldung, String ort, String forces, LocalDateTime timestamp) {
        this.fwName = fwName;
        this.fachBereich = fachBereich;
        this.meldung = meldung;
        this.ort = ort;
        this.forces = forces;
        this.timestamp = timestamp;
    }

    public void setFachBereich(String fachBereich) {
        this.fachBereich = fachBereich;
    }

    public void setForces(String forces) {
        this.forces = forces;
    }

    public void setFwName(String fwName) {
        this.fwName = fwName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMeldung(String meldung) {
        this.meldung = meldung;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFachBereich() {
        return fachBereich;
    }

    public String getForces() {
        return forces;
    }

    public Long getId() {
        return id;
    }

    public String getFwName() {
        return fwName;
    }

    public String getMeldung() {
        return meldung;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getOrt() {
        return ort;
    }
}
