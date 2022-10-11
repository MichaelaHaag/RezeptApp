package view;

import model.Category;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

import static app.RezeptApp.controller;

/*Klasse für Listen Ansicht. In dieser KLasse wird ein FRame erzeugt, der Rezepte zu einer Kategorie UUID
ausgibt. Ist die UUID 0 so handelt es sich um alle Rezepte*/
public class Listpage {
    JFrame frame = new JFrame();
    JPanel pnlListpage = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");

    /*Erstellung des Headers mit dem Logo und dem Footer mit den Buttons, um ein neues Rezept hinzuzufügen, ein Zufallrezept auszuwählen
    oder auf die Homepage zu gelangen */
    public Listpage(UUID id){
        System.out.println("Die Listpage wird gestartet");
        JPanel header = new JPanel();
        header.setBackground(Color.white);
        JLabel labelLogo = new JLabel(logo);
        header.add(labelLogo);
        pnlListpage.add(header, BorderLayout.NORTH);
        JPanel footer = new JPanel(new GridLayout());
        Color green = new Color(0x00AAAA);
        footer.setBackground(green);
        JButton zufallsgenerator = new JButton("Zufallsgenerator");
        zufallsgenerator.addActionListener(ae -> {
            new RandomGenerator();
            frame.dispose();
        });
        JButton homeButton = new JButton("Startseite");
        homeButton.addActionListener(ae -> frame.dispose());
        JButton newRecipeButton = new JButton("Neues Rezept");
        newRecipeButton.addActionListener(ae -> {
            new NewRecipe();
            frame.dispose();
        });
        zufallsgenerator.setBackground(green);
        homeButton.setBackground(green);
        newRecipeButton.setBackground(green);
        footer.add(zufallsgenerator);
        footer.add(homeButton);
        footer.add(newRecipeButton);
        pnlListpage.add(footer, BorderLayout.SOUTH);
        initUI(id);
        frame.add( pnlListpage );
        frame.setVisible(true);
        frame.setBounds(300,70,900,650);
    }

    //Diese Methode erzeugt eine Liste, mit allen Rezepten zu einer UUID
    private void initUI(UUID id) {
        String name = "00000000-0000-0000-0000-000000000000";
        UUID allrecipeID = UUID.fromString(name);
        if(id.equals(allrecipeID)){
            try {
                String[][] recipes = controller.findAllRecipies();
                Recipe rezept = new Recipe();
                String[] columnNames = rezept.getCSVHeader();

                // Initializing the JTable
                JTable table = new JTable(recipes, columnNames);
                table.setRowHeight(30);
                table.addMouseListener(new java.awt.event.MouseAdapter() {
                                           public void mouseClicked(java.awt.event.MouseEvent e) {
                                               int row = table.rowAtPoint(e.getPoint());
                                               int col = 0;
                                               UUID id = UUID.fromString(table.getValueAt(row, col).toString());
                                               new DetailRecipe(id, frame);
                                           }
                                       }
                );
                pnlListpage.add(new JScrollPane(table), BorderLayout.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            Category inputCategory = controller.entityManager.find(Category.class, id);
            try {
                String[][] recipes = controller.findRecipeOfCategory(inputCategory);
                Recipe rezept = new Recipe();
                String[] columnNames = rezept.getCSVHeader();

                // Initializing the JTable
                JTable table = new JTable(recipes, columnNames);
                table.setRowHeight(30);
                table.addMouseListener(new java.awt.event.MouseAdapter() {
                                           public void mouseClicked(java.awt.event.MouseEvent e) {
                                               int row = table.rowAtPoint(e.getPoint());
                                               int col = 0;
                                               UUID id = UUID.fromString(table.getValueAt(row, col).toString());
                                               new DetailRecipe(id, frame);
                                           }
                                       }
                );
                pnlListpage.add(new JScrollPane(table), BorderLayout.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
