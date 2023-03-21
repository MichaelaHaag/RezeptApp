package de.rezeptapp.plugins.gui;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.adapter.Datenpersistenz.CSVRezept;
import de.rezeptapp.adapter.GUIFunktionen.FunktionenListenÜbersicht;
import de.rezeptapp.domain.Rezept.Rezept;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/*Klasse für Listen Ansicht. In dieser KLasse wird ein FRame erzeugt, der Rezepte zu einer Kategorie UUID
ausgibt. Ist die UUID 0 so handelt es sich um alle Rezepte*/
public class ListenÜbersicht {
    JFrame frame = new JFrame();
    JPanel pnlListenÜbersicht = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");
    DataReader dataReader;

    /*Erstellung des Headers mit dem Logo und dem Footer mit den Buttons, um ein neues Rezept hinzuzufügen, ein Zufallrezept auszuwählen
    oder auf die Homepage zu gelangen */
    public ListenÜbersicht(UUID id, DataReader dataReaderImport){
        this.dataReader = dataReaderImport;
        System.out.println("Die Listpage wird gestartet");
        JPanel pnlKopfzeile = new JPanel();
        pnlKopfzeile.setBackground(Color.white);
        JLabel labelLogo = new JLabel(logo);
        pnlKopfzeile.add(labelLogo);
        pnlListenÜbersicht.add(pnlKopfzeile, BorderLayout.NORTH);
        JPanel pnlFusszeile = new JPanel(new GridLayout());
        Color farbeGrün = new Color(0x00AAAA);
        pnlFusszeile.setBackground(farbeGrün);
        JButton buttonZufallsgenerator = new JButton("Zufallsgenerator");
        buttonZufallsgenerator.addActionListener(ae -> {
            new ZufallsGenerator(dataReader);
            frame.dispose();
        });
        JButton buttonStartseite = new JButton("Startseite");
        buttonStartseite.addActionListener(ae -> frame.dispose());
        JButton buttonNeuesRezept = new JButton("Neues Rezept");
        buttonNeuesRezept.addActionListener(ae -> {
            new NeuesRezept(dataReader);
            frame.dispose();
        });
        buttonZufallsgenerator.setBackground(farbeGrün);
        buttonStartseite.setBackground(farbeGrün);
        buttonNeuesRezept.setBackground(farbeGrün);
        pnlFusszeile.add(buttonZufallsgenerator);
        pnlFusszeile.add(buttonStartseite);
        pnlFusszeile.add(buttonNeuesRezept);
        pnlListenÜbersicht.add(pnlFusszeile, BorderLayout.SOUTH);
        initBenutzeroberfläche(id);
        frame.add( pnlListenÜbersicht );
        frame.setVisible(true);
        frame.setBounds(300,70,900,650);
    }

    //Diese Methode erzeugt eine Liste, mit allen Rezepten zu einer UUID
    private void initBenutzeroberfläche(UUID id) {
        String[][] alleRezepte = FunktionenListenÜbersicht.alleRezepte(id, dataReader);
        Rezept rezept = new Rezept();
        CSVRezept csvRezept = new CSVRezept(rezept);
        String[] spaltenNamen = csvRezept.bekommeCSVKopf();
        // Initiaisierung der Tabele
        JTable tabelle = new JTable(alleRezepte, spaltenNamen);
        tabelle.setRowHeight(30);
        tabelle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int spalte = tabelle.rowAtPoint(e.getPoint());
                int zeile = 0;
                UUID id = UUID.fromString(tabelle.getValueAt(spalte, zeile).toString());
                new RezeptAnsicht(id, frame, dataReader);
            }
        }
        );
        pnlListenÜbersicht.add(new JScrollPane(tabelle), BorderLayout.CENTER);
    }
}
