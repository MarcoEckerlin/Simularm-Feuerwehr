package seebach.feuerwehr.maec.obj;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class GUI {

    private static final String ADMIN_LINK = "http://localhost:80/";

    public GUI(){
        JFrame frame = new JFrame("Simularm - Feuerwehr");
        frame.setSize(350, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

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

        panel.add(openPanel);
        panel.add(kill);

        frame.add(panel);
        frame.setLocationRelativeTo(null); // zentrieren
        frame.setVisible(true);

    }
}
