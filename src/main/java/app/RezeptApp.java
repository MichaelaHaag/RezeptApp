package app;

import controller.MainController;
import view.Homepage;

/* Main Klasse: Startet die Home Page und den Controller */
public class RezeptApp {
    public static MainController controller;

    public static void main(String[] args) {

        controller = new MainController();
        controller.init();

        Homepage homepage = new Homepage();
        homepage.setVisible(true);
        homepage.setBounds(300,70,900,650);
    }
}
