package gui;
import javax.swing.*;
import java.awt.*;

public class skeleton {

    // Main page
    private JPanel homePage;
    // Panel storage
    private JTabbedPane functions;
    // Each function (tab) panel
    private JPanel bacon;
    private JPanel costar;
    private JPanel typecast;
    private JPanel roles;
    private JPanel bestAndWorst;
    // All fields for Bacon Number tab
    private JTextField baconActor1Name;
    private JTextField baconActor2Name;
    private JTextArea baconBaconNumber;
    private JButton baconButton;
    private JTextArea baconMovieList;
    private JTextArea baconConnectingActors;
    // All fields for Constellation of Co-Stars tab
    private JButton costarButton;
    private JTextField costarNumberAppearances;
    private JTextField costarActorName;
    private JTextArea costarCostarList;
    // All fields for Typecast tab
    private JButton typecastButton;
    private JTextField typecastActor;
    private JTextArea typecastRole;
    // All fields for Cover roles tab
    private JButton coverRolesButton;
    private JTextField coverRolesActor;
    private JTextArea coverRolesGenre;
    // All fields for Best and Worst tab
    private JButton bestAndWorstButton;
    private JTextField bestAndWorstActorName;
    private JTextArea bestAndWorstMovie;

    public static void main(String[] args) {
        JFrame f = new JFrame("skeleton");//creating instance of JFrame

        f.setContentPane(new skeleton().homePage);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

}
