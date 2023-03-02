package de.rezeptapp.plugins.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/* File Chooser Klasse: Ist zuständig für die Auswahl von Pfaden für Bilder */
public class FileChooser extends Component {

    public String dateiAuswahl(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //set Look and Feel to Windows
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFileChooser fileChooser = new JFileChooser(); //Create a new GUI that will use the current(windows) Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //revert the Look and Feel back to the ugly Swing
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\resources\\Pictures\\Food"));
        System.out.println(fileChooser.getCurrentDirectory());
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
}
