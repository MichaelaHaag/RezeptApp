package de.rezeptapp.plugins.gui;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.adapter.GUIFunktionen.FunktionenStartseite;

import javax.swing.*;
import java.awt.*;

/* Klasse erzeugt ein Zufalliges Rezept und zeigt es an. Das Rezept kann dann ausgewählt werden oder ein
neues zufälliges Rezept kann erzeugt werden*/
public class NeueKategorie {
    JFrame frame = new JFrame();
    JPanel pnlZufallsGenerator = new JPanel(new BorderLayout());
    DataReader dataReader;

    public NeueKategorie(DataReader dataReaderImport) {
        this.dataReader = dataReaderImport;
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
            FunktionenStartseite.kategorieHinzufügen(tfeldKategorie.getText(), tfeldKategorieTag.getText(), tfeldKategorieBeschreibung.getText(), dataReader);
            Startseite startseite = new Startseite(dataReader);
            startseite.setVisible(true);
            startseite.setBounds(300,70,900,650);
            frame.dispose();
        });
        JButton buttonNeuesZufallsRezept = new JButton("Schließen");
        buttonNeuesZufallsRezept.addActionListener(ae -> frame.dispose());
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
