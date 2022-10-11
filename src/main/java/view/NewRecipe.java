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
public class NewRecipe{
    JFrame frame = new JFrame();
    JPanel pnlNewRecipe = new JPanel(new BorderLayout());
    JButton button = new JButton();

    public NewRecipe()  {
        JPanel pnlCentre = new JPanel(new BorderLayout());
        JPanel pnlTop = new JPanel(new GridLayout(2,2));
        System.out.println("Das Panel für ein neues Rezept wird gestartet");
        JLabel title = new JLabel("Titel: ");
        JTextField titleTF = new JTextField();
        pnlTop.add(title);
        pnlTop.add(titleTF);

        JLabel tags = new JLabel("Tags: ");
        List<Category> kategorien = controller.entityManager.findAll(Category.class);
        JPanel checkboxenJP = new JPanel(new FlowLayout());
        Checkbox[] checkboxen = new Checkbox[kategorien.size()];
        for(int i=0; i<kategorien.size(); i++){
            Checkbox checkBoxKategorie = new Checkbox(kategorien.get(i).getTag());
            checkboxen[i]= checkBoxKategorie;
            checkboxenJP.add(checkBoxKategorie);
        }

        pnlTop.add(tags);
        checkboxenJP.setPreferredSize(new Dimension(600,120));
        pnlTop.add(checkboxenJP);

        pnlCentre.add(pnlTop, BorderLayout.NORTH);

        List<Unit> units = controller.entityManager.findAll(Unit.class);
        Unit[] unitArray = units.toArray(new Unit[0]);
        String [] unitsString = new String[unitArray.length];
        for(int i=0; i<unitArray.length; i++){
            unitsString[i] = unitArray[i].getName();
        }
        JComboBox<String> unit = new JComboBox<>(unitsString);

        JLabel zutaten = new JLabel("Zutaten: ");

        JPanel zutatenALL = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Menge");
        model.addColumn("Einheit");
        model.addColumn("Zutat");
        model.addColumn("Löschen");

        model.addRow(new Object[]{"","l", "", "-"});

        TableColumn unitColumn = table.getColumnModel().getColumn(1);
        unitColumn.setCellEditor(new DefaultCellEditor(unit));
        table.getColumn("Löschen").setCellRenderer(new ButtonRenderer());
        table.getColumn("Löschen").setCellEditor(new ButtonEditor(new JCheckBox()));
        button.addActionListener(ae -> {
            System.out.print("Button geklickt");
            if(table.getSelectedRow() != -1) {
                // remove selected row from the model
                model.removeRow(table.getSelectedRow());
                JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
            }
        });
        table.setMaximumSize(new Dimension(100,50));
        zutatenALL.add(new JScrollPane(table), BorderLayout.CENTER);
        JButton newIngre = new JButton("Zutat hinzufügen");
        newIngre.addActionListener(ae -> {
            DefaultTableModel model1 = (DefaultTableModel) table.getModel();
            model1.addRow(new Object[]{"", "l", "", "-"});
        });

        zutatenALL.add(newIngre, BorderLayout.SOUTH);
        zutatenALL.add(zutaten, BorderLayout.NORTH);
        zutatenALL.setPreferredSize(new Dimension(290,200));
        pnlCentre.add(zutatenALL, BorderLayout.EAST);

        JPanel pnlWest = new JPanel(new BorderLayout());
        JLabel decription = new JLabel("Beschreibung: ");
        JTextArea decriptionTA = new JTextArea("Hier sollte die Beschreibung abc stehen");
        decriptionTA.setLineWrap(true);
        decriptionTA.setWrapStyleWord(true);
        pnlWest.add(decription, BorderLayout.NORTH);
        pnlWest.add(new JScrollPane(decriptionTA), BorderLayout.CENTER);
        pnlWest.setPreferredSize(new Dimension(290,200));
        pnlCentre.add(pnlWest, BorderLayout.WEST);

        JPanel pnlBottom = new JPanel(new GridLayout(2,2));
        JPanel rediobuttonJP = new JPanel(new FlowLayout());
        JLabel difficulty = new JLabel("Schwierigkeitsgrad: ");
        String[] difficulties = Difficulty.getAllDifficulties();
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] radiobuttons = new JRadioButton[difficulties.length];
        for(int i=0; i<difficulties.length; i++){
            JRadioButton radioDiffi = new JRadioButton(difficulties[i]);
            radiobuttons[i]= radioDiffi;
            group.add(radiobuttons[i]);
            rediobuttonJP.add(radiobuttons[i]);
        }
        pnlBottom.add(difficulty);
        pnlBottom.add(rediobuttonJP);

        JLabel file = new JLabel("Füge ein Dokument oder Bild hinzu ");
        JButton choose = new JButton("Choose File");
        final String[] picturePath = new String[1];
        choose.addActionListener(ae -> {
            FileChooser chooser = new FileChooser();
            picturePath[0] = chooser.chooseFile();
        });
        pnlBottom.add(file);
        pnlBottom.add(choose);
        pnlCentre.add(pnlBottom, BorderLayout.SOUTH);

        JPanel footer = new JPanel(new FlowLayout());
        JButton cancel = new JButton("Abbrechen");
        cancel.addActionListener(ae -> frame.dispose());
        JButton save = new JButton("Speichern");
        save.addActionListener(ae -> {
            boolean tableEdited = false;
            if(table.getCellEditor() != null){
                table.getCellEditor().stopCellEditing();
                tableEdited = true;
            }
            UUID recipeID = UUID.randomUUID();
            String title1 = titleTF.getText();
            String description = decriptionTA.getText();
            ArrayList<Category> categories = new ArrayList<>();
            ArrayList<Ingredient> ingredientList = new ArrayList<>();
            Difficulty difficulty1 = null;

            List<Category> allCategries = controller.entityManager.findAll(Category.class);
            for (int i = 0; i < allCategries.size(); i++) {
                if(checkboxen[i].getState()){
                    System.out.println( checkboxen[i].getLabel());
                    for (Category c : allCategries){
                        if (c.getTag().equals(checkboxen[i].getLabel())){
                            categories.add(c);
                        }
                    }
                }
            }

            for (JRadioButton radiobutton : radiobuttons){
                if (radiobutton.isSelected()){
                    for(Difficulty difficultyEnum : Difficulty.values()){
                        if(difficultyEnum.getName().equals(radiobutton.getText())){
                            difficulty1 = difficultyEnum;
                        }
                    }
                    System.out.println(radiobutton.getText());
                }
            }

            if(!title1.equals("") && !description.equals("") && tableEdited && !categories.isEmpty() && difficulty1 != null){

                for (int i = 0; i < table.getRowCount(); i++) {
                    String amountText = (String) table.getModel().getValueAt(i, 0);
                    long amount = Long.parseLong(amountText);
                    String unitText = (String) table.getModel().getValueAt(i, 1);
                    String name = (String) table.getModel().getValueAt(i, 2);

                    Unit selectedUnit = null;
                    List<Unit> allUnits = controller.entityManager.findAll(Unit.class);
                    for (Unit unit1 : allUnits) {
                        if (unit1.getName().equals(unitText)) {
                            selectedUnit = unit1;
                        }
                    }

                    Ingredient ingredient = new Ingredient(recipeID, amount, selectedUnit, name);
                    ingredientList.add(ingredient);

                    try {
                        controller.entityManager.persist(ingredient);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String path = picturePath[0];
                if (path != null){
                    String[] parts = path.split("(?=src)");
                    String pathString = parts[1].replace("\\", "/");
                    Picture pictureElement = new Picture(recipeID,pathString);

                    //store in Picture and Recipe in EntityManger
                    try {
                        controller.entityManager.persist( pictureElement );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Recipe recipeElement = new Recipe(recipeID, title1, categories, ingredientList, description, difficulty1, pictureElement );
                    try {
                        controller.entityManager.persist( recipeElement );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //store Picture in CVS Files
                    List<Picture> allPictures = controller.entityManager.findAll(Picture.class);
                    controller.saveCSVData(controller.csvFilePath + "Picture.csv", allPictures);

                }else{
                    Recipe recipeElement = new Recipe(recipeID, title1, categories, ingredientList, description, difficulty1);
                    try {
                        controller.entityManager.persist( recipeElement );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //store Ingredients and Recipe in CVS Files
                List<Ingredient> allIngredients = controller.entityManager.findAll(Ingredient.class);
                controller.saveCSVData(controller.csvFilePath + "Ingredient.csv", allIngredients);
                List<Recipe> allRecipies = controller.entityManager.findAll(Recipe.class);
                controller.saveCSVData(controller.csvFilePath + "Recipe.csv", allRecipies);

                frame.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder aus!");
            }
        });

        footer.add(cancel);
        footer.add(save);
        pnlNewRecipe.add(pnlCentre, BorderLayout.CENTER);
        pnlNewRecipe.add(footer, BorderLayout.SOUTH);

        frame.add( pnlNewRecipe );
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
