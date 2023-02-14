package controller;

import view.NeuesRezept;
import view.ZufallsGenerator;

import javax.swing.*;

public class FunktionenZufallsGenerator {

    public static void neuerZufallsGenerator(JFrame frame){
        new ZufallsGenerator();
        frame.dispose();
    }
}
