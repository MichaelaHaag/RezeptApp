package view;

import controller.FunktionenRezeptBearbeiten;
import controller.FunktionenStartseite;
import model.Kategorie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Home Page: Wird beim Starten der Anwendung geladen. Die Home Page enthält Kacheln mit den einzelnen Kategorien */
public class Startseite extends JFrame implements ActionListener {
    JPanel pnlStartseite = new JPanel(new BorderLayout());
    ImageIcon logo = new ImageIcon("src/main/resources/Pictures/RecipeCollection.png");

    /*Erstellung des Headers mit dem Logo und dem Footer mit den Buttons, um ein neues Rezept hinzuzufügen, ein
    Zufallrezept auszuwählen oder auf die Homepage zu gelangen */
    public Startseite() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        System.out.println("Die Homepage wird gestartet");
        JPanel pnlKopfzeile = new JPanel();
        pnlKopfzeile.setBackground(Color.white);
        JLabel labelLogo = new JLabel(logo);
        pnlKopfzeile.add(labelLogo);
        pnlStartseite.add(pnlKopfzeile, BorderLayout.NORTH);
        initBenutzeroberfläche();
        JPanel fusszeile = new JPanel(new GridLayout());
        Color farbeGrün = new Color(0x00AAAA);
        fusszeile.setBackground(farbeGrün);
        JButton zufallsgenerator = new JButton("Zufallsgenerator");
        zufallsgenerator.addActionListener(ae -> new ZufallsGenerator());
        JButton buttonStartseite = new JButton("Startseite");
        JButton buttonNeuesRezept = new JButton("Neues Rezept");
        buttonNeuesRezept.addActionListener(ae -> new NeuesRezept());
        zufallsgenerator.setBackground(farbeGrün);
        buttonStartseite.setBackground(farbeGrün);
        buttonNeuesRezept.setBackground(farbeGrün);
        fusszeile.add(zufallsgenerator);
        fusszeile.add(buttonStartseite);
        fusszeile.add(buttonNeuesRezept);
        pnlStartseite.add(fusszeile, BorderLayout.SOUTH);
        this.getContentPane().add( pnlStartseite );
    }

    //Methode, um die Kachlen der einzelnen Kategorien zu erstellen
    private void initBenutzeroberfläche() {
        JPanel pnlStartseite2 = new JPanel(new FlowLayout(20, 20, 20));
        List<Kategorie> alleKategorien = controller.entityManager.findeAlle(Kategorie.class);
        JButton[] knöpfe = new JButton[alleKategorien.size()+2];

        knöpfe[0] = new JButton("<html>Kategorie<br>hinzufügen</html>");
        knöpfe[0].setName("00000000-0000-0000-0000-000000000000");
        knöpfe[0].setVisible(true);
        knöpfe[0].setPreferredSize(new Dimension(150, 125));
        knöpfe[0].setToolTipText("Kategorie hinzufügen");
        pnlStartseite2.add(knöpfe[0]);
        knöpfe[0].addActionListener(ae -> {
            String getMessage = JOptionPane.showInputDialog(this, "Name der Kategorie: ");
            FunktionenStartseite.kategorieHinzufügen(getMessage);
        });

        knöpfe[1] = new JButton("Alle Rezepte");
        knöpfe[1].setName("11111111-1111-1111-1111-111111111111");
        knöpfe[1].setVisible(true);
        knöpfe[1].setPreferredSize(new Dimension(150, 125));
        knöpfe[1].setToolTipText("Alle Katgorie");
        pnlStartseite2.add(knöpfe[1]);
        knöpfe[1].addActionListener(ae -> {
            FunktionenStartseite.listenÜbersichtÖffnen(ae);
        });
        Kategorie[] kategorieArray = alleKategorien.toArray(new Kategorie[0]);
        String [] kategorien = new String[kategorieArray.length];
        for(int i=0; i<kategorieArray.length; i++){
            kategorien[i] = kategorieArray[i].bekommeName();
        }
        for (int i = 0; i < kategorien.length; i++) {
            knöpfe[i+2] = new JButton(kategorien[i]);
            knöpfe[i+2].setName(kategorieArray[i].bekommeUUID().toString());
            knöpfe[i+2].setVisible(true);
            knöpfe[i+2].setPreferredSize(new Dimension(150, 125));
            knöpfe[i+2].setToolTipText(kategorien[i]);
            pnlStartseite2.add(knöpfe[i+2]);
            knöpfe[i+2].addActionListener(ae -> {
                FunktionenStartseite.listenÜbersichtÖffnen(ae);
            });
        }
        Color farbeGrau = new Color(0xFCFCFC);
        pnlStartseite.setBackground(farbeGrau);
        pnlStartseite.add(pnlStartseite2, BorderLayout.CENTER);
    }
    public void actionPerformed (ActionEvent ae){
    }
}
