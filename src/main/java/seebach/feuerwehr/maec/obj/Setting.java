package seebach.feuerwehr.maec.obj;

import jakarta.persistence.*;

@Entity
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String setting;

    @Column(name = "setting_value")
    private String value;

    public Long getId() {
        return id;
    }

    public String getSetting() {
        return setting;
    }

    public String getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
