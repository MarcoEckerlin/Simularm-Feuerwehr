package seebach.feuerwehr.maec.websocket;

import seebach.feuerwehr.maec.service.Core;

public class CoreDTO {
    public Long id;
    public String fwName;
    public String fachBereich;
    public String meldung;
    public String ort;
    public String forces;
    public String timestamp;

    public CoreDTO(Core core) {
        this.id = core.getId();
        this.fwName = core.getFwName();
        this.fachBereich = core.getFachBereich();
        this.meldung = core.getMeldung();
        this.ort = core.getOrt();
        this.forces = core.getForces();
        this.timestamp = core.getTimestamp().toString();
    }
}

