package view;

import model.Kategorie;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

import static app.RezeptApp.controller;

/*Klasse für Listen Ansicht. In dieser KLasse wird ein FRame erzeugt, der Rezepte zu einer Kategorie UUID
ausgibt. Ist die UUID 0 so handelt es sich um alle Rezepte*/
public class ListenÜbersicht {
    JFrame frame = new JFrame();
    JPanel pnlListenÜbersicht = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");

    /*Erstellung des Headers mit dem Logo und dem Footer mit den Buttons, um ein neues Rezept hinzuzufügen, ein Zufallrezept auszuwählen
    oder auf die Homepage zu gelangen */
    public ListenÜbersicht(UUID id){
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
            new ZufallsGenerator();
            frame.dispose();
        });
        JButton buttonStartseite = new JButton("Startseite");
        buttonStartseite.addActionListener(ae -> frame.dispose());
        JButton buttonNeuesRezept = new JButton("Neues Rezept");
        buttonNeuesRezept.addActionListener(ae -> {
            new NeuesRezept();
            frame.dispose();
        });
        buttonZufallsgenerator.setBackground(farbeGrün);
        buttonStartseite.setBackground(farbeGrün);
        buttonNeuesRezept.setBackground(farbeGrün);
        pnlFusszeile.add(buttonZufallsgenerator);
        pnlFusszeile.add(buttonStartseite);
        pnlFusszeile.add(buttonNeuesRezept);
        pnlListenÜbersicht.add(pnlFusszeile, BorderLayout.SOUTH);
        initUI(id);
        frame.add( pnlListenÜbersicht );
        frame.setVisible(true);
        frame.setBounds(300,70,900,650);
    }

    //Diese Methode erzeugt eine Liste, mit allen Rezepten zu einer UUID
    private void initUI(UUID id) {
        String name = "00000000-0000-0000-0000-000000000000";
        UUID idAlleRezepte = UUID.fromString(name);
        if(id.equals(idAlleRezepte)){
            try {
                String[][] alleRezepte = controller.findAllRecipies();
                Recipe rezept = new Recipe();
                String[] spaltenNamen = rezept.getCSVHeader();

                // Initiaisierung der Tabele
                JTable tabele = new JTable(alleRezepte, spaltenNamen);
                tabele.setRowHeight(30);
                tabele.addMouseListener(new java.awt.event.MouseAdapter() {
                                           public void mouseClicked(java.awt.event.MouseEvent e) {
                                               int spalte = tabele.rowAtPoint(e.getPoint());
                                               int zeile = 0;
                                               UUID id = UUID.fromString(tabele.getValueAt(spalte, zeile).toString());
                                               new RezeptAnsicht(id, frame);
                                           }
                                       }
                );
                pnlListenÜbersicht.add(new JScrollPane(tabele), BorderLayout.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            Kategorie eingegbeneKategorie = controller.entityManager.find(Kategorie.class, id);
            try {
                String[][] recipes = controller.findRecipeOfCategory(eingegbeneKategorie);
                Recipe rezept = new Recipe();
                String[] spaltenNamen = rezept.getCSVHeader();

                // Initializing the JTable
                JTable tabele = new JTable(recipes, spaltenNamen);
                tabele.setRowHeight(30);
                tabele.addMouseListener(new java.awt.event.MouseAdapter() {
                                           public void mouseClicked(java.awt.event.MouseEvent e) {
                                               int spalte = tabele.rowAtPoint(e.getPoint());
                                               int zeile = 0;
                                               UUID id = UUID.fromString(tabele.getValueAt(spalte, zeile).toString());
                                               new RezeptAnsicht(id, frame);
                                           }
                                       }
                );
                pnlListenÜbersicht.add(new JScrollPane(tabele), BorderLayout.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
