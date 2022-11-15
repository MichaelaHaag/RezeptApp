package view;

import model.*;
import util.FileChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import static app.RezeptApp.controller;

/* Die KLasse erzeugt einen Frame, indem man ein neues Rezept hinzufügen kann*/
public class NeuesRezept {
    JFrame frame = new JFrame();
    JPanel pnlNeuesRezept = new JPanel(new BorderLayout());
    JButton button = new JButton();

    public NeuesRezept()  {
        JPanel pnlMitte = new JPanel(new BorderLayout());
        JPanel pnlOben = new JPanel(new GridLayout(2,2));
        System.out.println("Das Panel für ein neues Rezept wird gestartet");
        JLabel labelTitle = new JLabel("Titel: ");
        JTextField textfeldTitle = new JTextField();
        pnlOben.add(labelTitle);
        pnlOben.add(textfeldTitle);

        JLabel labelTags = new JLabel("Tags: ");
        List<Kategorie> kategorien = controller.entityManager.findAll(Kategorie.class);
        JPanel pnlCheckboxen = new JPanel(new FlowLayout());
        Checkbox[] checkboxen = new Checkbox[kategorien.size()];
        for(int i=0; i<kategorien.size(); i++){
            Checkbox checkboxKategorie = new Checkbox(kategorien.get(i).getTag());
            checkboxen[i]= checkboxKategorie;
            pnlCheckboxen.add(checkboxKategorie);
        }

        pnlOben.add(labelTags);
        pnlCheckboxen.setPreferredSize(new Dimension(600,120));
        pnlOben.add(pnlCheckboxen);

        pnlMitte.add(pnlOben, BorderLayout.NORTH);

        List<Einheit> einheiten = controller.entityManager.findAll(Einheit.class);
        Einheit[] arrayEinheiten = einheiten.toArray(new Einheit[0]);
        String [] stringEinheiten = new String[arrayEinheiten.length];
        for(int i=0; i<arrayEinheiten.length; i++){
            stringEinheiten[i] = arrayEinheiten[i].getName();
        }
        JComboBox<String> einheit = new JComboBox<>(stringEinheiten);

        JLabel labelZutaten = new JLabel("Zutaten: ");

        JPanel pnlZutaten = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        JTable tabele = new JTable(model);

        model.addColumn("Menge");
        model.addColumn("Einheit");
        model.addColumn("Zutat");
        model.addColumn("Löschen");

        model.addRow(new Object[]{"","l", "", "-"});

        TableColumn unitColumn = tabele.getColumnModel().getColumn(1);
        unitColumn.setCellEditor(new DefaultCellEditor(einheit));
        tabele.getColumn("Löschen").setCellRenderer(new ButtonRenderer());
        tabele.getColumn("Löschen").setCellEditor(new ButtonEditor(new JCheckBox()));
        button.addActionListener(ae -> {
            System.out.print("Button geklickt");
            if(tabele.getSelectedRow() != -1) {
                // entferne die ausgeählte Zeile aus dem Model
                model.removeRow(tabele.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Die ausgewühlte Zeile würde erfolgreich gelöscht!");
            }
        });
        tabele.setMaximumSize(new Dimension(100,50));
        pnlZutaten.add(new JScrollPane(tabele), BorderLayout.CENTER);
        JButton buttonNeueZutat = new JButton("Zutat hinzufügen");
        buttonNeueZutat.addActionListener(ae -> {
            DefaultTableModel modelAusgangswerte = (DefaultTableModel) tabele.getModel();
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
        String[] schwierigkeiten = Schwierigkeit.getAlleSchwierigkeiten();
        ButtonGroup gruppe = new ButtonGroup();
        JRadioButton[] radiobuttons = new JRadioButton[schwierigkeiten.length];
        for(int i=0; i<schwierigkeiten.length; i++){
            JRadioButton radioDiffi = new JRadioButton(schwierigkeiten[i]);
            radiobuttons[i]= radioDiffi;
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
            bildPfad[0] = chooser.chooseFile();
        });
        pnlUnten.add(labelDokument);
        pnlUnten.add(buttonDokument);
        pnlMitte.add(pnlUnten, BorderLayout.SOUTH);

        JPanel pnlFusszeile = new JPanel(new FlowLayout());
        JButton buttonAbbrechen = new JButton("Abbrechen");
        buttonAbbrechen.addActionListener(ae -> frame.dispose());
        JButton buttonSpeichern = new JButton("Speichern");
        buttonSpeichern.addActionListener(ae -> {
            boolean tabeleBearbeitet= false;
            if(tabele.getCellEditor() != null){
                tabele.getCellEditor().stopCellEditing();
                tabeleBearbeitet = true;
            }
            UUID rezeptID = UUID.randomUUID();
            String titel = textfeldTitle.getText();
            String beschreibung = textAreaBeschreibung.getText();
            ArrayList<Kategorie> rezeptKategorien = new ArrayList<>();
            ArrayList<Zutat> rezeptEinheiten = new ArrayList<>();
            Schwierigkeit schwierigkeit = null;

            List<Kategorie> alleKategorien = controller.entityManager.findAll(Kategorie.class);
            for (int i = 0; i < alleKategorien.size(); i++) {
                if(checkboxen[i].getState()){
                    System.out.println( checkboxen[i].getLabel());
                    for (Kategorie c : alleKategorien){
                        if (c.getTag().equals(checkboxen[i].getLabel())){
                            rezeptKategorien.add(c);
                        }
                    }
                }
            }

            for (JRadioButton radiobutton : radiobuttons){
                if (radiobutton.isSelected()){
                    for(Schwierigkeit enumSchwierigkeit : Schwierigkeit.values()){
                        if(enumSchwierigkeit.getName().equals(radiobutton.getText())){
                            schwierigkeit = enumSchwierigkeit;
                        }
                    }
                    System.out.println(radiobutton.getText());
                }
            }

            if(!titel.equals("") && !beschreibung.equals("") && tabeleBearbeitet && !rezeptKategorien.isEmpty() && schwierigkeit != null){

                for (int i = 0; i < tabele.getRowCount(); i++) {
                    String mengeText = (String) tabele.getModel().getValueAt(i, 0);
                    long menge = Long.parseLong(mengeText);
                    String einheitText = (String) tabele.getModel().getValueAt(i, 1);
                    String name = (String) tabele.getModel().getValueAt(i, 2);

                    Einheit ausgewählteEinheit = null;
                    List<Einheit> alleEinheiten = controller.entityManager.findAll(Einheit.class);
                    for (Einheit einheit1 : alleEinheiten) {
                        if (einheit1.getName().equals(einheitText)) {
                            ausgewählteEinheit = einheit1;
                        }
                    }

                    Zutat zutat = new Zutat(rezeptID, menge, ausgewählteEinheit, name);
                    rezeptEinheiten.add(zutat);

                    try {
                        controller.entityManager.persist(zutat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String pfad = bildPfad[0];
                if (pfad != null){
                    String[] aufgeteilterPfad = pfad.split("(?=src)");
                    String stringPfad = aufgeteilterPfad[1].replace("\\", "/");
                    Bilder bildElement = new Bilder(rezeptID,stringPfad);

                    //Bild und Rezept im EntityManager speichern
                    try {
                        controller.entityManager.persist( bildElement );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Rezept recipeElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptEinheiten, beschreibung, schwierigkeit, bildElement );
                    try {
                        controller.entityManager.persist( recipeElement );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Bild in der CVS Datei speichern
                    List<Bilder> alleBilder = controller.entityManager.findAll(Bilder.class);
                    controller.speichereCSVDaten(controller.csvBilderPfad + "Bild.csv", alleBilder);

                }else{
                    Rezept rezeptElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptEinheiten, beschreibung, schwierigkeit);
                    try {
                        controller.entityManager.persist( rezeptElement );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //Zutaten und Rezept in CSV Speichern
                List<Zutat> alleZutaten = controller.entityManager.findAll(Zutat.class);
                controller.speichereCSVDaten(controller.csvBilderPfad + "Zutaten.csv", alleZutaten);
                List<Rezept> alleRezepte = controller.entityManager.findAll(Rezept.class);
                controller.speichereCSVDaten(controller.csvBilderPfad + "Rezept.csv", alleRezepte);

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

    static class ButtonRenderer extends JButton implements TableCellRenderer
    {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "-" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor
    {
        private String label;

        public ButtonEditor(JCheckBox checkBox)
        {
            super(checkBox);
        }
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column)
        {
            label = (value == null) ? "-" : value.toString();
            button.setText(label);
            return button;
        }
        public Object getCellEditorValue()
        {
            return label;
        }
    }
}
