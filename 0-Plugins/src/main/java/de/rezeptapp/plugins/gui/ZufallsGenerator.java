package de.rezeptapp.plugins.gui;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.adapter.GUIFunktionen.FunktionenZufallsGenerator;
import de.rezeptapp.domain.Rezept.Rezept;
import de.rezeptapp.domain.Rezept.RezeptRepository;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/* Klasse erzeugt ein Zufalliges Rezept und zeigt es an. Das Rezept kann dann ausgewählt werden oder ein
neues zufälliges Rezept kann erzeugt werden*/
public class ZufallsGenerator {
    final static RezeptRepository rezeptRepository = new RezeptRepository();
    JFrame frame = new JFrame();
    JPanel pnlZufallsGenerator = new JPanel(new BorderLayout());

    public ZufallsGenerator(DataReader dataReader) {
        System.out.println("Der Randomizer wird gestartet");
        UUID zufälligeRezeptID = FunktionenZufallsGenerator.zufälligeRezeptUUID(dataReader);
        Rezept zufälligesRezept = rezeptRepository.findeRezept(zufälligeRezeptID, dataReader.entityManager);
        JLabel labelVorschlag = new JLabel(zufälligesRezept.bekommeTitel());
        labelVorschlag.setFont(new Font("Calibri", Font.PLAIN, 30));
        JLabel labelKategorie = new JLabel("Kategorie: ");
        JLabel labelZufallsrezeptKategorie = new JLabel(zufälligesRezept.bekommeKategorien().toString());
        JButton buttonRezeptÖffnen = new JButton("Rezept öffnen");
        buttonRezeptÖffnen.addActionListener(ae -> {
            new RezeptAnsicht(zufälligeRezeptID, null, dataReader);
            frame.dispose();
        });
        JButton buttonNeuesZufallsRezept = new JButton("Neues Zufallsrezept");
        buttonNeuesZufallsRezept.addActionListener(ae -> {
            new ZufallsGenerator(dataReader);
            frame.dispose();
        });
        JPanel pnlOben = new JPanel(new FlowLayout());
        pnlOben.add(labelVorschlag);
        pnlZufallsGenerator.add(pnlOben, BorderLayout.NORTH);
        JPanel pnlMitte = new JPanel(new FlowLayout());
        pnlMitte.add(labelKategorie);
        pnlMitte.add(labelZufallsrezeptKategorie);
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
