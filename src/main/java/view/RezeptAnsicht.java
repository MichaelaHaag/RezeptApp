package view;

import model.Zutat;
import model.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Diese KLasse erzeugt einen Frame, indem ein Rezept detailiert Angezeigt wird*/
public class RezeptAnsicht {
    JFrame frame = new JFrame();
    JPanel pnlRezeptAnsicht = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");

    /*Erstellung des Headers mit dem Logo und dem Footer mit den Buttons, um ein neues Rezept hinzuzufügen, ein
    Zufallrezept auszuwählen oder auf die Homepage zu gelangen */
    public RezeptAnsicht(UUID id, JFrame frameListenÜbersicht) {
        System.out.println("Die Listpage wird gestartet");
        JPanel pnlKopfzeile = new JPanel();
        pnlKopfzeile.setBackground(Color.white);
        JLabel labelLogo = new JLabel(logo);
        pnlKopfzeile.add(labelLogo);
        pnlRezeptAnsicht.add(pnlKopfzeile, BorderLayout.NORTH);
        JPanel pnlFusszeile = new JPanel(new GridLayout());
        Color farbeGrün = new Color(0x00AAAA);
        pnlFusszeile.setBackground(farbeGrün);
        JButton zufallsgenerator = new JButton("Zufallsgenerator");
        zufallsgenerator.addActionListener(ae -> {
            new ZufallsGenerator();
            frame.dispose();
            frameListenÜbersicht.dispose();
        });
        JButton buttonStartseite = new JButton("Startseite");
        buttonStartseite.addActionListener(ae -> {
            frame.dispose();
            frameListenÜbersicht.dispose();
        });
        JButton buttonNeuesRezept = new JButton("Neues Rezept");
        buttonNeuesRezept.addActionListener(ae -> {
            new NeuesRezept();
            frame.dispose();
            frameListenÜbersicht.dispose();
        });
        zufallsgenerator.setBackground(farbeGrün);
        buttonStartseite.setBackground(farbeGrün);
        buttonNeuesRezept.setBackground(farbeGrün);
        pnlFusszeile.add(zufallsgenerator);
        pnlFusszeile.add(buttonStartseite);
        pnlFusszeile.add(buttonNeuesRezept);
        pnlRezeptAnsicht.add(pnlFusszeile, BorderLayout.SOUTH);
        initUI(id);
        frame.add( pnlRezeptAnsicht );
        frame.setVisible(true);
        frame.setBounds(300,70,900,650);
    }
    //Methode, die die Rezept Details auf den Frame einfügt
    private void initUI(UUID id) {
        Recipe ausgewähltesRezept = controller.entityManager.find(Recipe.class, id);
        JPanel pnlRezeptAnsicht2 = new JPanel(new GridLayout(5,1));
        JPanel pnlKopfzeile = new JPanel(new BorderLayout());
        JPanel pnlTitel = new JPanel(new GridLayout(2,1));
        JLabel lblTitel = new JLabel(ausgewähltesRezept.getTitle());
        lblTitel.setFont(new Font("Calibri", Font.PLAIN, 30));
        JLabel lblZutaten = new JLabel("Zustaten:");
        lblZutaten.setFont(new Font("Calibri", Font.PLAIN, 20));
        JLabel lblSchwierigkeit = new JLabel("Schwierigkeitsgrad:             "+ ausgewähltesRezept.getDifficulty());
        lblSchwierigkeit.setFont(new Font("Calibri", Font.PLAIN, 15));
        pnlTitel.add(lblTitel);
        pnlTitel.add(lblSchwierigkeit);
        pnlKopfzeile.add(pnlTitel, BorderLayout.WEST);
        if(ausgewähltesRezept.getPicture() != null){
            System.out.print(ausgewähltesRezept.getPicture().getPfad());
            ImageIcon bild = new ImageIcon(ausgewähltesRezept.getPicture().getPfad());
            Image image = bild.getImage();
            Image transfImgage = image.getScaledInstance(150, 150,  Image.SCALE_FAST);
            bild = new ImageIcon(transfImgage);
            JLabel lblBild = new JLabel(bild);
            pnlKopfzeile.add(lblBild, BorderLayout.CENTER);
        }
        pnlRezeptAnsicht2.add(pnlKopfzeile);
        pnlRezeptAnsicht2.add(lblZutaten);
        ArrayList<Zutat> zutaten = ausgewähltesRezept.getIngredients();

        JPanel pnlZutaten = new JPanel(new GridLayout(zutaten.size(),1));
        for (Zutat ingredient : zutaten) {
            JLabel lblMenge = new JLabel(String.valueOf(ingredient.getMenge()));
            pnlZutaten.add(lblMenge);

            JLabel lblEinheit = new JLabel(String.valueOf(ingredient.getEinheit().getName()));
            pnlZutaten.add(lblEinheit);
            JLabel lblZutat = new JLabel(String.valueOf(ingredient.getName()));
            pnlZutaten.add(lblZutat);
        }
        JLabel lblBeschreibung = new JLabel("Beschreibung");
        lblBeschreibung.setFont(new Font("Calibri", Font.PLAIN, 20));
        JTextField textfeldBeschreibung = new JTextField(ausgewähltesRezept.getDescription());

        pnlRezeptAnsicht2.add(pnlZutaten);
        pnlRezeptAnsicht2.add(lblBeschreibung);
        pnlRezeptAnsicht2.add(textfeldBeschreibung);

        pnlRezeptAnsicht.add(pnlRezeptAnsicht2,BorderLayout.CENTER);
    }
}
