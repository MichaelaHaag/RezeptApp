package view;

import controller.FunktionenRezeptBearbeiten;
import model.Zutat;
import model.Rezept;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Diese KLasse erzeugt einen Frame, indem ein Rezept detailiert Angezeigt wird*/
public class RezeptAnsicht implements ActionListener {
    JFrame frame = new JFrame();
    JPanel pnlRezeptAnsicht = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");
    Color farbeGrün = new Color(0x00AAAA);

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
        initBenutzeroberfläche(id);
        frame.add( pnlRezeptAnsicht );
        frame.setVisible(true);
        frame.setBounds(300,70,900,650);
    }
    //Methode, die die Rezept Details auf den Frame einfügt
    private void initBenutzeroberfläche(UUID id) {
        Rezept ausgewähltesRezept = controller.entityManager.finde(Rezept.class, id);
        JPanel pnlRezeptAnsicht2 = new JPanel(new GridLayout(5,1));
        JPanel pnlKopfzeile = new JPanel(new BorderLayout());
        JPanel pnlTitel = new JPanel(new GridLayout(2,1));
        JLabel lblTitel = new JLabel(ausgewähltesRezept.bekommeTitel());
        lblTitel.setFont(new Font("Calibri", Font.PLAIN, 30));
        JLabel lblZutaten = new JLabel("Zustaten:");
        lblZutaten.setFont(new Font("Calibri", Font.PLAIN, 20));
        JLabel lblSchwierigkeit = new JLabel("Schwierigkeitsgrad:             "+ ausgewähltesRezept.bekommeSchwierigkeitsgrad());
        lblSchwierigkeit.setFont(new Font("Calibri", Font.PLAIN, 15));
        pnlTitel.add(lblTitel);
        pnlTitel.add(lblSchwierigkeit);
        pnlKopfzeile.add(pnlTitel, BorderLayout.WEST);
        if(ausgewähltesRezept.bekommeBild() != null){
            System.out.print(ausgewähltesRezept.bekommeBild().bekommePfad());
            ImageIcon bild = new ImageIcon(ausgewähltesRezept.bekommeBild().bekommePfad());
            Image image = bild.getImage();
            Image transfImgage = image.getScaledInstance(150, 150,  Image.SCALE_FAST);
            bild = new ImageIcon(transfImgage);
            JLabel lblBild = new JLabel(bild);
            pnlKopfzeile.add(lblBild, BorderLayout.CENTER);
        }
        JButton buttonBearbeiten = new JButton("Bearbeiten");
        buttonBearbeiten.addActionListener(ae -> {
            frame.dispose();
            FunktionenRezeptBearbeiten.rezeptBearbeiten(ausgewähltesRezept);
        });
        buttonBearbeiten.setPreferredSize(new Dimension(100, 80));
        buttonBearbeiten.setBackground(farbeGrün);
        pnlKopfzeile.add(buttonBearbeiten, BorderLayout.EAST);
        pnlRezeptAnsicht2.add(pnlKopfzeile);
        pnlRezeptAnsicht2.add(lblZutaten);
        ArrayList<Zutat> zutaten = ausgewähltesRezept.bekommeZutaten();

        JPanel pnlZutaten = new JPanel(new GridLayout(zutaten.size(),1));
        for (Zutat zutat : zutaten) {
            JLabel lblMenge = new JLabel(String.valueOf(zutat.bekommeMenge().dieMenge()));
            pnlZutaten.add(lblMenge);
            JLabel lblEinheit = new JLabel(String.valueOf(zutat.bekommeMenge().dieEinheit().bekommeName()));
            pnlZutaten.add(lblEinheit);
            JLabel lblZutat = new JLabel(String.valueOf(zutat.bekommeName()));
            pnlZutaten.add(lblZutat);
        }
        JLabel lblBeschreibung = new JLabel("Beschreibung");
        lblBeschreibung.setFont(new Font("Calibri", Font.PLAIN, 20));
        JTextField textfeldBeschreibung = new JTextField(ausgewähltesRezept.bekommeBeschreibung());

        pnlRezeptAnsicht2.add(pnlZutaten);
        pnlRezeptAnsicht2.add(lblBeschreibung);
        pnlRezeptAnsicht2.add(textfeldBeschreibung);

        pnlRezeptAnsicht.add(pnlRezeptAnsicht2,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
