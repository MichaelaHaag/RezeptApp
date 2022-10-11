package view;

import model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Home Page: Wird beim Starten der Anwendung geladen. Die Home Page enthält Kacheln mit den einzelnen Kategorien */
public class Homepage extends JFrame implements ActionListener {
    JPanel pnlStartseite = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");

    /*Erstellung des Headers mit dem Logo und dem Footer mit den Buttons, um ein neues Rezept hinzuzufügen, ein
    Zufallrezept auszuwählen oder auf die Homepage zu gelangen */
    public Homepage() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        System.out.println("Die Homepage wird gestartet");
        JPanel header = new JPanel();
        header.setBackground(Color.white);
        JLabel labelLogo = new JLabel(logo);
        header.add(labelLogo);
        pnlStartseite.add(header, BorderLayout.NORTH);
        initUI();
        JPanel footer = new JPanel(new GridLayout());
        Color green = new Color(0x00AAAA);
        footer.setBackground(green);
        JButton zufallsgenerator = new JButton("Zufallsgenerator");
        zufallsgenerator.addActionListener(ae -> new RandomGenerator());
        JButton homeButton = new JButton("Startseite");
        JButton newRecipeButton = new JButton("Neues Rezept");
        newRecipeButton.addActionListener(ae -> new NewRecipe());
        zufallsgenerator.setBackground(green);
        homeButton.setBackground(green);
        newRecipeButton.setBackground(green);
        footer.add(zufallsgenerator);
        footer.add(homeButton);
        footer.add(newRecipeButton);
        pnlStartseite.add(footer, BorderLayout.SOUTH);
        this.getContentPane().add( pnlStartseite );
    }

    //Methode, um die Kachlen der einzelnen Kategorien zu erstellen
    private void initUI() {
        JPanel pnlStartseite2 = new JPanel(new FlowLayout(20, 20, 20));
        List<Category> categories = controller.entityManager.findAll(Category.class);
        JButton[] buttons = new JButton[categories.size()+1];
        buttons[0] = new JButton("Alle Rezepte");
        buttons[0].setName("00000000-0000-0000-0000-000000000000");
        buttons[0].setVisible(true);
        buttons[0].setPreferredSize(new Dimension(150, 125));
        buttons[0].setToolTipText("Alle Katgorie");
        pnlStartseite2.add(buttons[0]);
        buttons[0].addActionListener(this);
        Category[] categorieArray = categories.toArray(new Category[0]);
        String [] kategorien = new String[categorieArray.length];
        for(int i=0; i<categorieArray.length; i++){
            kategorien[i] = categorieArray[i].getName();
        }
        for (int i = 0; i < kategorien.length; i++) {
            buttons[i+1] = new JButton(kategorien[i]);
            buttons[i+1].setName(categorieArray[i].getUUID().toString());
            buttons[i+1].setVisible(true);
            buttons[i+1].setPreferredSize(new Dimension(150, 125));
            buttons[i+1].setToolTipText(kategorien[i]);
            pnlStartseite2.add(buttons[i+1]);
            buttons[i+1].addActionListener(this);
        }
        Color grey = new Color(0xFCFCFC);
        pnlStartseite.setBackground(grey);
        pnlStartseite.add(pnlStartseite2, BorderLayout.CENTER);
    }

    /*Methode für die Funktionalität der Buttons. Wird ein Button geklickt, so öffnet sich die UI der KLasse
    Listpage (eine Liste mit allen Rezepten der ausgewählten Kategorie */
    public void actionPerformed (ActionEvent ae){
        JButton o = (JButton)ae.getSource();
        String name = o.getName();
        UUID id = UUID.fromString(name);
        new Listpage(id);
    }
}
