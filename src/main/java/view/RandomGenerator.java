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
public class RandomGenerator  {
    JFrame frame = new JFrame();
    JPanel pnlRandomizer = new JPanel(new BorderLayout());

    public RandomGenerator() {
        System.out.println("Der Randomizer wird gestartet");
        UUID id = randomUUID();
        Recipe inputRecipe = controller.entityManager.find(Recipe.class, id);
        JLabel vorschlag = new JLabel(inputRecipe.getTitle());
        vorschlag.setFont(new Font("Calibri", Font.PLAIN, 30));
        JLabel vorschlagKategorie = new JLabel("Kategorie: ");
        JLabel vorschlagKategorieTF = new JLabel(inputRecipe.getCategories().toString());
        JButton rezeptNehmen = new JButton("Rezept öffnen");
        rezeptNehmen.addActionListener(ae -> {
            new DetailRecipe(id, null);
            frame.dispose();
        });
        JButton neuesRezept = new JButton("Neues Zufallsrezept");
        neuesRezept.addActionListener(ae -> {
            new RandomGenerator();
            frame.dispose();
        });
        JPanel top = new JPanel(new FlowLayout());
        top.add(vorschlag);
        pnlRandomizer.add(top, BorderLayout.NORTH);
        JPanel centre = new JPanel(new FlowLayout());
        centre.add(vorschlagKategorie);
        centre.add(vorschlagKategorieTF);
        pnlRandomizer.add(centre, BorderLayout.CENTER);
        JPanel footer = new JPanel(new FlowLayout());
        footer.add(rezeptNehmen);
        footer.add(neuesRezept);
        pnlRandomizer.add(footer, BorderLayout.SOUTH);
        frame.add( pnlRandomizer );
        frame.setVisible(true);
        frame.setBounds(500,250,500,170);
    }

    //Erzeugt aus den vorhandenen UUIDs eine zufällige UUID
    public UUID randomUUID (){
        List<Recipe> recipe = controller.entityManager.findAll(Recipe.class);
        Random randomGenerator=new Random();
        int zufall = randomGenerator.nextInt(recipe.size());
        Recipe recipe1= recipe.get(zufall);
        return recipe1.getUUID();
    }
}
