package de.rezeptapp.plugins.gui;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.adapter.GUIFunktionen.ButtonEditor;
import de.rezeptapp.adapter.GUIFunktionen.ButtonRenderer;
import de.rezeptapp.adapter.GUIFunktionen.FunktionenNeuesRezept;
import de.rezeptapp.domain.Kategorie.Kategorie;
import de.rezeptapp.domain.Kategorie.KategorieRepository;
import de.rezeptapp.domain.Rezept.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/* Die KLasse erzeugt einen Frame, indem man ein neues Rezept hinzufügen kann*/
public class NeuesRezept {
    final static KategorieRepository kategorieRepository = new KategorieRepository();
    JFrame frame = new JFrame();
    JPanel pnlNeuesRezept = new JPanel(new BorderLayout());
    JButton button = new JButton();

    public NeuesRezept(DataReader dataReader)  {
        JPanel pnlMitte = new JPanel(new BorderLayout());
        JPanel pnlOben = new JPanel(new GridLayout(2,2));
        System.out.println("Das Panel für ein neues Rezept wird gestartet");
        JLabel labelTitel = new JLabel("Titel: ");
        JTextField textfeldTitel = new JTextField();
        pnlOben.add(labelTitel);
        pnlOben.add(textfeldTitel);

        JLabel labelTags = new JLabel("Tags: ");
        List<Kategorie> kategorien = kategorieRepository.findeAlleKategorien(dataReader.entityManager);
        JPanel pnlCheckboxen = new JPanel(new FlowLayout());
        Checkbox[] checkboxen = new Checkbox[kategorien.size()];
        for(int i=0; i<kategorien.size(); i++){
            Checkbox checkboxKategorie = new Checkbox(kategorien.get(i).bekommeKurzformName());
            checkboxen[i]= checkboxKategorie;
            pnlCheckboxen.add(checkboxKategorie);
        }

        pnlOben.add(labelTags);
        pnlCheckboxen.setPreferredSize(new Dimension(600,120));
        pnlOben.add(pnlCheckboxen);

        pnlMitte.add(pnlOben, BorderLayout.NORTH);

        Einheit[] alleEinheiten = Einheit.values();
        String [] stringEinheiten = new String[alleEinheiten.length];
        for(int i=0; i<alleEinheiten.length; i++){
            stringEinheiten[i] = alleEinheiten[i].bekommeName();
        }
        JComboBox<String> comboBoxEinheit = new JComboBox<>(stringEinheiten);

        JLabel labelZutaten = new JLabel("Zutaten: ");

        JPanel pnlZutaten = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        JTable tabelle = new JTable(model);

        model.addColumn("Menge");
        model.addColumn("Einheit");
        model.addColumn("Zutat");
        model.addColumn("Löschen");

        model.addRow(new Object[]{"","l", "", "-"});

        TableColumn einheitSpalte = tabelle.getColumnModel().getColumn(1);
        einheitSpalte.setCellEditor(new DefaultCellEditor(comboBoxEinheit));
        tabelle.getColumn("Löschen").setCellRenderer(new ButtonRenderer());
        tabelle.getColumn("Löschen").setCellEditor(new ButtonEditor(new JCheckBox()));
        button.addActionListener(ae -> {
            System.out.print("Button geklickt");
            if(tabelle.getSelectedRow() != -1) {
                // entferne die ausgeählte Zeile aus dem Model
                model.removeRow(tabelle.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Die ausgewühlte Zeile würde erfolgreich gelöscht!");
            }
        });
        tabelle.setMaximumSize(new Dimension(100,50));
        pnlZutaten.add(new JScrollPane(tabelle), BorderLayout.CENTER);
        JButton buttonNeueZutat = new JButton("Zutat hinzufügen");
        buttonNeueZutat.addActionListener(ae -> {
            DefaultTableModel modelAusgangswerte = (DefaultTableModel) tabelle.getModel();
            modelAusgangswerte.addRow(new Object[]{"", "l", "", "-"});
        });

        pnlZutaten.add(buttonNeueZutat, BorderLayout.SOUTH);
        pnlZutaten.add(labelZutaten, BorderLayout.NORTH);
        pnlZutaten.setPreferredSize(new Dimension(290,200));
        pnlMitte.add(pnlZutaten, BorderLayout.EAST);

        JPanel pnlWesten = new JPanel(new BorderLayout());
        JLabel labelBeschreibung = new JLabel("Beschreibung: ");
        JTextArea textAreaBeschreibung = new JTextArea("Hier sollte die Beschreibung abc stehen");
        textAreaBeschreibung.setLineWrap(true);
        textAreaBeschreibung.setWrapStyleWord(true);
        pnlWesten.add(labelBeschreibung, BorderLayout.NORTH);
        pnlWesten.add(new JScrollPane(textAreaBeschreibung), BorderLayout.CENTER);
        pnlWesten.setPreferredSize(new Dimension(290,200));
        pnlMitte.add(pnlWesten, BorderLayout.WEST);

        JPanel pnlUnten = new JPanel(new GridLayout(2,2));
        JPanel pnlRadioButton = new JPanel(new FlowLayout());
        JLabel labelSchwierigkeitsgrad = new JLabel("Schwierigkeitsgrad: ");
        Schwierigkeit[] alleSchwierigkeitsgrade = Schwierigkeit.values();
        ButtonGroup gruppe = new ButtonGroup();
        JRadioButton[] radiobuttons = new JRadioButton[alleSchwierigkeitsgrade.length];
        for(int i=0; i<alleSchwierigkeitsgrade.length; i++){
            JRadioButton radioButtonSchwierigkeiten = new JRadioButton(alleSchwierigkeitsgrade[i].toString());
            radiobuttons[i]= radioButtonSchwierigkeiten;
            gruppe.add(radiobuttons[i]);
            pnlRadioButton.add(radiobuttons[i]);
        }
        pnlUnten.add(labelSchwierigkeitsgrad);
        pnlUnten.add(pnlRadioButton);

        JLabel labelDokument = new JLabel("Füge ein Dokument oder Bild hinzu ");
        JButton buttonDokument = new JButton("Wähle ein Dokument");
        final String[] bildPfad = new String[1];
        buttonDokument.addActionListener(ae -> {
            FileChooser chooser = new FileChooser();
            bildPfad[0] = chooser.dateiAuswahl();
        });
        pnlUnten.add(labelDokument);
        pnlUnten.add(buttonDokument);
        pnlMitte.add(pnlUnten, BorderLayout.SOUTH);

        JPanel pnlFusszeile = new JPanel(new FlowLayout());
        JButton buttonAbbrechen = new JButton("Abbrechen");
        buttonAbbrechen.addActionListener(ae -> frame.dispose());
        JButton buttonSpeichern = new JButton("Speichern");
        buttonSpeichern.addActionListener(ae -> {
            boolean tabelleBearbeitet = false;
            if(tabelle.getCellEditor() != null){
                tabelle.getCellEditor().stopCellEditing();
                tabelleBearbeitet = true;
            }

            String titel = textfeldTitel.getText();
            String beschreibung = textAreaBeschreibung.getText();
            String pfadBild = bildPfad[0];

            ArrayList<String> checkedKategorien = new ArrayList<>();
            for (Checkbox checkbox : checkboxen) {
                if (checkbox.getState()) {
                    checkedKategorien.add(checkbox.getLabel());
                }
            }

            String ausewaehlteSchwierigkeit = "";
            for (JRadioButton radiobutton : radiobuttons){
                if (radiobutton.isSelected()){
                    ausewaehlteSchwierigkeit = radiobutton.getText();
                }
            }

            if(!titel.equals("") && !beschreibung.equals("") && tabelleBearbeitet && !checkedKategorien.isEmpty() && !ausewaehlteSchwierigkeit.equals("")) {
                ArrayList<String[]> zutatenListe = new ArrayList<>();
                for (int i = 0; i < tabelle.getRowCount(); i++) {
                    String mengeText = (String) tabelle.getModel().getValueAt(i, 0);
                    String einheitText = (String) tabelle.getModel().getValueAt(i, 1);
                    String name = (String) tabelle.getModel().getValueAt(i, 2);
                    String[] zutatEingabe = {mengeText, einheitText, name};
                    zutatenListe.add(zutatEingabe);
                }

                FunktionenNeuesRezept.neuesRezeptSpeichern(titel, beschreibung, checkedKategorien,ausewaehlteSchwierigkeit, pfadBild, zutatenListe, dataReader);

                frame.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus!");
            }
        });

        pnlFusszeile.add(buttonAbbrechen);
        pnlFusszeile.add(buttonSpeichern);
        pnlNeuesRezept.add(pnlMitte, BorderLayout.CENTER);
        pnlNeuesRezept.add(pnlFusszeile, BorderLayout.SOUTH);

        frame.add( pnlNeuesRezept );
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setBounds(400,100,600,700);
    }

}
