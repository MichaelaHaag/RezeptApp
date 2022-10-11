package view;

import model.Ingredient;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Diese KLasse erzeugt einen Frame, indem ein Rezept detailiert Angezeigt wird*/
public class DetailRecipe {
    JFrame frame = new JFrame();
    JPanel pnlListpage = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");

    /*Erstellung des Headers mit dem Logo und dem Footer mit den Buttons, um ein neues Rezept hinzuzufügen, ein
    Zufallrezept auszuwählen oder auf die Homepage zu gelangen */
    public DetailRecipe(UUID id, JFrame frameListpage) {
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
            frameListpage.dispose();
        });
        JButton homeButton = new JButton("Startseite");
        homeButton.addActionListener(ae -> {
            frame.dispose();
            frameListpage.dispose();
        });
        JButton newRecipeButton = new JButton("Neues Rezept");
        newRecipeButton.addActionListener(ae -> {
            new NewRecipe();
            frame.dispose();
            frameListpage.dispose();
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
    //Methode, die die Rezept Details auf den Frame einfügt
    private void initUI(UUID id) {
        Recipe inputRecipe = controller.entityManager.find(Recipe.class, id);
        JPanel detailpagePanel = new JPanel(new GridLayout(5,1));
        JPanel header = new JPanel(new BorderLayout());
        JPanel titlePnl = new JPanel(new GridLayout(2,1));
        JLabel title = new JLabel(inputRecipe.getTitle());
        title.setFont(new Font("Calibri", Font.PLAIN, 30));
        JLabel zutatenl = new JLabel("Zustaten:");
        zutatenl.setFont(new Font("Calibri", Font.PLAIN, 20));
        JLabel difficulty = new JLabel("Schwierigkeitsgrad:             "+ inputRecipe.getDifficulty());
        difficulty.setFont(new Font("Calibri", Font.PLAIN, 15));
        titlePnl.add(title);
        titlePnl.add(difficulty);
        header.add(titlePnl, BorderLayout.WEST);
        if(inputRecipe.getPicture() != null){
            System.out.print(inputRecipe.getPicture().getPath());
            //ImageIcon picture = new ImageIcon(inputRecipe.getPicture().getPath());
            ImageIcon picture = new ImageIcon(inputRecipe.getPicture().getPath());
            Image image = picture.getImage(); // transform it
            Image newimg = image.getScaledInstance(150, 150,  Image.SCALE_FAST);
            picture = new ImageIcon(newimg);
            JLabel labelPicture = new JLabel(picture);
            header.add(labelPicture, BorderLayout.CENTER);
        }
        detailpagePanel.add(header);
        detailpagePanel.add(zutatenl);
        ArrayList<Ingredient> zutaten = inputRecipe.getIngredients();

        JPanel zutatenP = new JPanel(new GridLayout(zutaten.size(),1));
        for (Ingredient ingredient : zutaten) {
            JLabel menge = new JLabel(String.valueOf(ingredient.getAmount()));
            zutatenP.add(menge);

            JLabel einheit = new JLabel(String.valueOf(ingredient.getUnit().getName()));
            zutatenP.add(einheit);
            JLabel zutat = new JLabel(String.valueOf(ingredient.getName()));
            zutatenP.add(zutat);
        }
        JLabel beschreibung = new JLabel("Beschreibung");
        beschreibung.setFont(new Font("Calibri", Font.PLAIN, 20));
        JTextField beschreibungTF = new JTextField(inputRecipe.getDescription());

        detailpagePanel.add(zutatenP);
        detailpagePanel.add(beschreibung);
        detailpagePanel.add(beschreibungTF);

        pnlListpage.add(detailpagePanel,BorderLayout.CENTER);
    }
}
