package de.rezeptapp.adapter.GUIFunktionen;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer
{
    public ButtonRenderer() {
        setOpaque(true);
    }
    public Component getTableCellRendererComponent(JTable tabele, Object wert,
                                                   boolean istAusgewählt, boolean hatFokus, int zeile, int spalte) {
        setText((wert == null) ? "-" : wert.toString());
        return this;
    }
}

