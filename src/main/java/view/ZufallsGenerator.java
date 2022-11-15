package view;

import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Klasse erzeugt ein Zufalliges Rezept und zeigt es an. Das Rezept kann dann ausgewählt werden oder ein
neues zufälliges Rezept kann erzeugt werden*/
public class ZufallsGenerator {
    JFrame frame = new JFrame();
    JPanel pnlZufallsGenerator = new JPanel(new BorderLayout());

    public ZufallsGenerator() {
        System.out.println("Der Randomizer wird gestartet");
        UUID id = zufälligeUUID();
        Recipe zufälligesRezept = controller.entityManager.find(Recipe.class, id);
        JLabel labelVorschlag = new JLabel(zufälligesRezept.getTitle());
        labelVorschlag.setFont(new Font("Calibri", Font.PLAIN, 30));
        JLabel labelKategorie = new JLabel("Kategorie: ");
        JLabel labelZufallsrezeptKategorie = new JLabel(zufälligesRezept.getCategories().toString());
        JButton buttonRezeptÖffnen = new JButton("Rezept öffnen");
        buttonRezeptÖffnen.addActionListener(ae -> {
            new RezeptAnsicht(id, null);
            frame.dispose();
        });
        JButton buttonNeuesZufallsRezept = new JButton("Neues Zufallsrezept");
        buttonNeuesZufallsRezept.addActionListener(ae -> {
            new ZufallsGenerator();
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

    //Erzeugt aus den vorhandenen UUIDs eine zufällige UUID
    public UUID zufälligeUUID (){
        List<Recipe> listeAlleRezepte = controller.entityManager.findAll(Recipe.class);
        Random randomGenerator=new Random();
        int zufallszahl = randomGenerator.nextInt(listeAlleRezepte.size());
        Recipe zufallsRezept = listeAlleRezepte.get(zufallszahl);
        return zufallsRezept.getUUID();
    }
}
