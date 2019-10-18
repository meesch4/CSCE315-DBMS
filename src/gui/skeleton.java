package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;
import dbms.Dbms;
import dbms.SqlExecutor;
import query.CoverRolesQuery;
import query.Query;
import query.WorstOfBestQuery;
import query.interfaces.*;

/**
 * To Implement: typecasting Query function, Constellation of Co-Stars function, GUI Redesign
 **/

public class skeleton {
    private static SqlExecutor executor = new SqlExecutor(new Dbms());
    /**
     * Layout structure:
     * 5 tabs for each corresponding query
     * Each button has a listener that performs the getText() function and calls corresponding function
     */

    // Main GUI building function
    public static void generateGUI() {
        JFrame mainframe = new JFrame("DBMS Queries");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(1920, 1080);
        mainframe.setLayout(new BorderLayout());
        mainframe.add(generateGraphicPanel());
        mainframe.setVisible(true);
    }

    private static JPanel generateGraphicPanel() {
        JPanel mainPanel = new JPanel();
        JTabbedPane mainTab = new JTabbedPane();
        JPanel typecastingPanel = generateTypecasting();
        JPanel coverRolesPanel = generateCoverRoles();
        JPanel bestAndWorstPanel = generateBestAndWorst();
        JPanel constellationPanel = generateConstellation();
        mainTab.add("Typecasting", typecastingPanel); // typecasting panel here
        mainTab.add("Cover Roles", coverRolesPanel); // cover roles panel here
        mainTab.add("Best of Days, Worst of Days", bestAndWorstPanel);
        mainTab.add("Constellation of Co-Stars", constellationPanel);
        mainPanel.add(mainTab);
        return mainPanel;
    }

    // Query panel building functions
    private static JPanel generateTypecasting() {
        JPanel typecastingPanel = new JPanel(new GridBagLayout());
        // Grid Bag formatting
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        // Panel components
        JLabel actorInputText = new JLabel("Enter actor name below!");
        c.gridy = 0;
        typecastingPanel.add(actorInputText,c);
        JTextField typecastingActor = new JTextField(30);
        c.gridy = 1;
        typecastingPanel.add(typecastingActor, c);
        JButton typecastingQuery = new JButton("Query");
        c.gridy = 2;
        typecastingPanel.add(typecastingQuery, c);
        JLabel outputText = new JLabel("The actor has mainly plays roles in this genre ...");
        c.gridy = 3;
        typecastingPanel.add(outputText, c);
        JTextField typecastedRole = new JTextField(30);
        c.gridy = 4;
        typecastingPanel.add(typecastedRole, c);
        typecastingQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Query q = new Query(executor);
                String toTypecast = typecastingActor.getText();
                String typecastOutput = q.calcMostCommonGenre(toTypecast);
                typecastedRole.setText(typecastOutput);
            }
        });
        return typecastingPanel;
    }

    private static JPanel generateCoverRoles() {
        JPanel coverRolesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Gridbag formatting
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        // Panel contents
        JButton coverRolesButton = new JButton("Query");
        c.gridy = 2;
        coverRolesPanel.add(coverRolesButton, c);
        JLabel rolePrompt = new JLabel("Enter movie/show character name!");
        c.gridy = 0;
        coverRolesPanel.add(rolePrompt, c);
        JTextField role = new JTextField(30);
        c.gridy = 1;
        coverRolesPanel.add(role, c);
        JLabel actorOutputText = new JLabel("These actors have played your character ...");
        c.gridy = 3;
        coverRolesPanel.add(actorOutputText, c);
        JTextArea coverRolesOutput = new JTextArea();
        c.gridy = 4;
        coverRolesPanel.add(coverRolesOutput, c);
        coverRolesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String roleToQuery = role.getText();
                CoverRolesQuery q = new CoverRolesQuery(executor);
                List<String> actorList = q.calcActorsWhichPlayedCharacter(roleToQuery);
                String actors = "";
                for (int i = 0; i < actorList.size(); i++){
                    actors += actorList.get(i) + "\n";
                }
                coverRolesOutput.setText(actors);
            }
        });
        return coverRolesPanel;
    }

    private static JPanel generateBestAndWorst() {
        JPanel bestWorstPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        /// Gridbag formatting
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        JLabel actorPrompt = new JLabel("Enter actor name below!");
        c.gridy = 0;
        bestWorstPanel.add(actorPrompt, c);
        JTextField actor = new JTextField(30);
        c.gridy = 1;
        bestWorstPanel.add(actor, c);
        JButton bestWorstQueryButton = new JButton("Query");
        c.gridy = 2;
        bestWorstPanel.add(bestWorstQueryButton, c);
        JLabel outputText = new JLabel("Worst film of the director of the actor's best film ...");
        c.gridy = 3;
        bestWorstPanel.add(outputText, c);
        JTextField worstOutput = new JTextField(30);
        c.gridy = 4;
        bestWorstPanel.add(worstOutput, c);
        bestWorstQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = actor.getText();
                WorstOfBestQuery q = new WorstOfBestQuery(executor);
                String worstMovie = q.calcWorstOfBests(user);
                worstOutput.setText(worstMovie);
            }
        });
        return bestWorstPanel;
    }

    private static JPanel generateConstellation() {
        JPanel constellationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Gridbag formatting
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        JLabel actorPrompt = new JLabel("Enter actor name below!");
        c.gridy = 0;
        constellationPanel.add(actorPrompt, c);
        JTextField actor = new JTextField(30);
        c.gridy = 1;
        constellationPanel.add(actor,c);
        JLabel appearancePrompt = new JLabel("Enter number of co-star appearances!");
        c.gridy = 2;
        constellationPanel.add(appearancePrompt, c);
        JTextField appearances = new JTextField(30);
        c.gridy = 4;
        constellationPanel.add(appearances, c);
        JButton constellationQueryButton = new JButton("Query");
        c.gridy = 5;
        constellationPanel.add(constellationQueryButton, c);
        JLabel actorOutputPrompt = new JLabel("These co-stars have the required number of appearances ...");
        c.gridy = 6;
        constellationPanel.add(actorOutputPrompt, c);
        JTextArea actorListOutput = new JTextArea();
        c.gridy = 7;
        constellationPanel.add(actorListOutput, c);
        constellationQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Query q = new Query(executor);
                String user1 = actor.getText();
                String user2 = appearances.getText();
                int user3 = Integer.parseInt(user2);
                List<String> functionCallList = q.calcCostarAppearances(user1, user3);
                String functionCall = "";
                for (int i = 0; i < functionCallList.size(); i++){
                    functionCall += functionCallList.get(i) + "\n";
                }
                actorListOutput.setText(functionCall); }
        });
        return constellationPanel;
    }
}
