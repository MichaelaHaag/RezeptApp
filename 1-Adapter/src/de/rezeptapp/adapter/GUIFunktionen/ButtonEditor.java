package de.rezeptapp.adapter.GUIFunktionen;

import javax.swing.*;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor
{
    private String label;

    public ButtonEditor(JCheckBox checkBox)
    {
        super(checkBox);
    }
    public Component getTableCellEditorComponent(JTable tabele, Object wert,
                                                 boolean istAusgewählt, int zeile, int spalte, JButton button)
    {
        label = (wert == null) ? "-" : wert.toString();
        button.setText(label);
        return button;
    }
    public Object getCellEditorValue()
    {
        return label;
    }
}