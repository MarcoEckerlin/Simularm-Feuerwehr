package seebach.feuerwehr.maec;

import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import seebach.feuerwehr.maec.obj.Alarm;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String ADMIN_LINK = "http://localhost:8080/admin";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "supersecure123";


    public static void main(String[] args) throws LineUnavailableException, IOException, InterruptedException, MaryConfigurationException, SynthesisException {

        Alarm alarm = new Alarm();
        alarm.setort("Ruhesteinstr. 14");
        alarm.setmeldung("B3.1 Gebäudevollbrand");
        alarm.setforces("Seebach 42, Seebach 41, Seebach 19");
        alarm.run();



        JFrame frame = new JFrame("Simularm - Feuerwehr");
        frame.setSize(480, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel("Admin Zugang", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel credentials = new JLabel("User: " + ADMIN_USERNAME + " | Passwort: " + ADMIN_PASSWORD, SwingConstants.CENTER);

        JButton openPanel = new JButton("Alarmpanel öffnen");
        JButton kill = new JButton("System Heruterfahren");

        openPanel.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(URI.create(ADMIN_LINK));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Fehler beim Öffnen des Browsers: " + ex.getMessage());
            }
        });

        kill.addActionListener(e -> {
            System.exit(0);
        });

        panel.add(title);
        panel.add(credentials);
        panel.add(openPanel);
        panel.add(kill);

        frame.add(panel);
        frame.setLocationRelativeTo(null); // zentrieren
        frame.setVisible(true);
    }
}