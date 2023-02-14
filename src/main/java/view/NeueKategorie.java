package view;

import controller.FunktionenStartseite;
import model.Rezept;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Klasse erzeugt ein Zufalliges Rezept und zeigt es an. Das Rezept kann dann ausgewählt werden oder ein
neues zufälliges Rezept kann erzeugt werden*/
public class NeueKategorie {
    JFrame frame = new JFrame();
    JPanel pnlZufallsGenerator = new JPanel(new BorderLayout());

    public NeueKategorie() {

        JLabel labelNeueKategorie = new JLabel("Neue Kategorie");
        labelNeueKategorie.setFont(new Font("Calibri", Font.PLAIN, 30));

        JLabel labelKategorie = new JLabel("Kategorie Name: ");
        JTextField tfeldKategorie = new JTextField();

        JLabel labelKategorieTag = new JLabel("Kurzform der Kategorie: ");
        JTextField tfeldKategorieTag = new JTextField();

        JLabel labelKategorieBeschreibung = new JLabel("Kategorie Beschreibung: ");
        JTextField tfeldKategorieBeschreibung = new JTextField();

        JButton buttonRezeptÖffnen = new JButton("Speichern");
        buttonRezeptÖffnen.addActionListener(ae -> {
            FunktionenStartseite.kategorieHinzufügen(tfeldKategorie.getText(), tfeldKategorieTag.getText(), tfeldKategorieBeschreibung.getText());
            FunktionenStartseite.startseiteStarten();
            frame.dispose();
        });
        JButton buttonNeuesZufallsRezept = new JButton("Schließen");
        buttonNeuesZufallsRezept.addActionListener(ae -> { frame.dispose(); });
        JPanel pnlOben = new JPanel(new FlowLayout());
        pnlOben.add(labelNeueKategorie);
        pnlZufallsGenerator.add(pnlOben, BorderLayout.NORTH);
        JPanel pnlMitte = new JPanel(new GridLayout(3,2));
        pnlMitte.add(labelKategorie);
        pnlMitte.add(tfeldKategorie);
        pnlMitte.add(labelKategorieTag);
        pnlMitte.add(tfeldKategorieTag);
        pnlMitte.add(labelKategorieBeschreibung);
        pnlMitte.add(tfeldKategorieBeschreibung);
        pnlZufallsGenerator.add(pnlMitte, BorderLayout.CENTER);
        JPanel pnlUnten = new JPanel(new FlowLayout());
        pnlUnten.add(buttonRezeptÖffnen);
        pnlUnten.add(buttonNeuesZufallsRezept);
        pnlZufallsGenerator.add(pnlUnten, BorderLayout.SOUTH);
        frame.add( pnlZufallsGenerator );
        frame.setVisible(true);
        frame.setBounds(500,250,500,170);
    }
}
