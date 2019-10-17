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
import query.WorstOfBestQuery;
import query.interfaces.*;


public class skeleton {
    private static SqlExecutor executor = new SqlExecutor(new Dbms());
    /**
     * Layout structure:
     * 5 tabs for each corresponding query
     * Each button has a listener that performs the getText() function and calls corresponding function
     */

    /**
     * Completed progress: Basic GUI structure
     * Needs work: listeners for button, functions to pass GUI data to actual query functions, GUI formatting
     * */
    public static void main(String[] args) {
        // System.out.println("Does this work?????"); It does indeed
        /**
         JFrame mainframe = new JFrame();
         mainframe.setSize(1000, 1000);
         mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        */
    }

    public static void generateGUI() {
        JFrame mainframe = new JFrame("DBMS Queries");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(1920, 1080);
        mainframe.setLayout(new BorderLayout());
        mainframe.add(generateGraphicPanel());
        mainframe.pack();
        mainframe.setVisible(true);
    }

    public static JPanel generateGraphicPanel() {
        JPanel mainPanel = new JPanel();
        JTabbedPane mainTab = new JTabbedPane();
        JPanel typecastingPanel = generateTypecasting();
        JPanel coverRolesPanel = generateCoverRoles();
        JPanel bestAndWorstPanel = generateBestAndWorst();
        //JPanel baconPanel = generateBaconNumber();
        JPanel constellationPanel = generateConstellation();
        mainTab.add("Typecasting", typecastingPanel); // typecasting panel here
        mainTab.add("Cover Roles", coverRolesPanel); // cover roles panel here
        mainTab.add("Best of Days, Worst of Days", bestAndWorstPanel);
        //mainTab.add("Bacon Number", baconPanel);
        mainTab.add("Constellation of Co-Stars", constellationPanel);
        mainPanel.add(mainTab);
        return mainPanel;
    }

    /** Needs formatting fixed */
    public static JPanel generateTypecasting() {
        JPanel retval = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        // Adding button to query
        JButton callQuery = new JButton("Query");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        retval.add(callQuery, c);
        // Adding actor input text label
        JLabel desiredInput = new JLabel("Enter actor name below!");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        retval.add(desiredInput,c);
        // Adding actor input text field
        JTextField actor1 = new JTextField(30);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        retval.add(actor1, c);
        // Adding actor output text label
        JLabel outputText = new JLabel("The actor has been typecast as ...");
        c.gridx = 0;
        c.gridy = 3;
        retval.add(outputText, c);
        // Adding actor output text field
        JTextField output = new JTextField(30);
        c.gridx = 0;
        c.gridy = 4;
        retval.add(output, c);
        // use .getText() function to get actor input from text field
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = actor1.getText();
                // create typecasting executor here
                String typecastOutput; /** insert typecasting function here */
                // calling function to generate typecast
                String functionCall = "0"/*calcMostCommonGenre(user)*/;
                // use .setText() function to get actor output to text field
                output.setText(functionCall);
            }
        });
        // return Typecast JPanel
        return retval;
    }

    public static JPanel generateCoverRoles() {
        JPanel retval = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        // Adding button to query
        JButton callQuery = new JButton("Query");
        c.gridy = 2;
        c.gridx = 0;
        retval.add(callQuery, c);
        // Adding text fields
        JLabel desiredInput = new JLabel("Enter movie/show character name!");
        c.gridx = 0;
        c.gridy = 0;
        retval.add(desiredInput, c);
        JTextField actor1 = new JTextField(30);
        c.gridy = 1;
        c.gridx = 0;
        retval.add(actor1, c);
        JLabel outputText = new JLabel("These actors have played your character ...");
        c.gridx = 0;
        c.gridy = 3;
        retval.add(outputText, c);
        JTextArea output = new JTextArea();
        c.gridx = 0;
        c.gridy = 4;
        retval.add(output, c);
        // use .getText() function to get input from this text field
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = actor1.getText();
                CoverRolesQuery q = new CoverRolesQuery(executor);
                List<String> actorList = q.calcActorsWhichPlayedCharacter(user);
                // calling function to generate CoverRoles
                //List<String> functionCallList = Arrays.asList("0", "1", "2")/*calcActorsWhichPlayedCharacter(user)*/;
                String functionCall = "";
                for (int i = 0; i < actorList.size(); i++){
                    functionCall += actorList.get(i) + "\n";
                }
                // use .setText() function to get actor output to text field
                output.setText(functionCall);
            }
        });
        // return CoverRoles JPanel
        return retval;
    }

    public static JPanel generateBestAndWorst() {
        JPanel retval = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        // Adding button to query
        JButton callQuery = new JButton("Query");
        c.gridx = 0;
        c.gridy = 2;
        retval.add(callQuery, c);
        // Adding text fields
        JLabel desiredInput = new JLabel("Enter actor name below!");
        c.gridy = 0;
        retval.add(desiredInput, c);
        JTextField actor1 = new JTextField(30);
        c.gridy = 1;
        // use .getText() function to get input from this text field
        retval.add(actor1, c);
        JLabel outputText = new JLabel("Worst film of the director of the actor's best film ...");
        c.gridx = 0;
        c.gridy = 3;
        retval.add(outputText, c);
        JTextField output = new JTextField(30);
        c.gridx = 0;
        c.gridy = 4;
        retval.add(output, c);
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = actor1.getText();
                // calling function to generate BestAndWorst
                WorstOfBestQuery q = new WorstOfBestQuery(executor);
                String worstMovie = q.calcWorstOfBests(user);
                String functionCall = "0"/*calcWorstOfBests(user)*/;
                // use .setText() function to get actor output to text field
                output.setText(worstMovie);
            }
        });
        // return BestAndWorst JPanel
        return retval;
    }

    /** NOT IMPLEMENTING THE FUNCTION
    public static JPanel generateBaconNumber() {
        JPanel retval = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        // Adding button to query
        JButton callQuery = new JButton("Query");
        c.gridy = 5;
        retval.add(callQuery, c);
        // Adding text fields
        JLabel desiredInput = new JLabel("Enter actor name below!");
        c.gridy = 0;
        retval.add(desiredInput, c);
        JLabel desiredInput2 = new JLabel("Enter second actor name below!");
        c.gridy = 2;
        retval.add(desiredInput2, c);
        JTextField actor1 = new JTextField(30);
        c.gridy = 1;
        // use .getText() function to get input from this text field
        retval.add(actor1, c);
        JTextField actor2 = new JTextField(30);
        c.gridy = 4;
        retval.add(actor2, c);
        // Outputs are bacon number, list of connecting movies, list of connecting actors
        JLabel baconNumber = new JLabel("The bacon number is ...");
        c.gridy = 6;
        retval.add(baconNumber, c);
        JLabel connectingMovies = new JLabel("The connecting movies are ...");
        c.gridy = 8;
        retval.add(connectingMovies, c);
        // random comment
        JLabel connectingActors = new JLabel("The connecting actors are ...");
        c.gridy = 10;
        retval.add(connectingActors, c);
        JTextField baconOut = new JTextField(30);
        c.gridy = 7;
        retval.add(baconOut, c);
        JTextArea movieOut = new JTextArea();
        c.gridy = 9;
        retval.add(movieOut, c);
        JTextArea actorOut = new JTextArea();
        c.gridy = 11;
        retval.add(actorOut, c);
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user1 = actor1.getText();
                String user2 = actor2.getText();
                // calling function to generate BaconNumber
                List<String> functionCallList1 = Arrays.asList("0", "1", "2"), functionCallList2 = Arrays.asList("4", "5", "6")/*calcDegreeOfSeparation(user1, user2);
                String functionCall1 = "";
                String functionCall2 = "";
                // use .setText() function to get actor output to text field
                for (int i = 0; i < functionCallList1.size(); i++) {
                    functionCall1 += functionCallList1.get(i) + "\n";
                }
                for (int i = 0; i < functionCallList2.size(); i++){
                    functionCall2 += functionCallList2.get(i) + "\n";
                }
                baconOut.setText(Integer.toString(functionCallList1.size()));
                movieOut.setText(functionCall1);
                actorOut.setText(functionCall2);
            }
        });
        // return BaconNumber JPanel
        return retval;
    } */


    public static JPanel generateConstellation() {
        JPanel retval = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        // Adding button to query
        JButton callQuery = new JButton("Query");
        c.gridy = 5;
        retval.add(callQuery, c);
        // Adding text fields
        JLabel desiredInput = new JLabel("Enter actor name below!");
        c.gridy = 0;
        retval.add(desiredInput, c);
        JLabel desiredInput2 = new JLabel("Enter number of co-star appearances!");
        c.gridy = 2;
        retval.add(desiredInput2, c);
        JTextField actor1 = new JTextField(30);
        c.gridy = 1;
        retval.add(actor1,c);
        JTextField appearances = new JTextField(30);
        // use .getText() function to get input from this text field
        c.gridy = 4;
        retval.add(appearances, c);
        // Outputs are list of co-stars with input appearances
        JLabel outText = new JLabel("These co-stars have the required number of appearances ...");
        c.gridy = 6;
        retval.add(outText, c);
        JTextArea costarOut = new JTextArea();
        c.gridy = 7;
        retval.add(costarOut, c);
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user1 = actor1.getText();
                String user2 = appearances.getText();
                int user3 = 0;
                try {
                    user3 = Integer.parseInt(user2);
                } catch (NumberFormatException e) {
                    appearances.setText("Input must be integer");
                }
                // calling function to generate Constellation
                ICostarConstellationQuery q; /** insert function code herer */
                List<String> functionCallList = Arrays.asList("0", "1", "2")/*calcCostarAppearances(user1, user3)*/;
                String functionCall = "";
                for (int i = 0; i < functionCallList.size(); i++){
                    functionCall += functionCallList.get(i) + "\n";
                }
                // use .setText() function to get actor output to text field
                costarOut.setText(functionCall);
            }
        });
        // return Constellation JPanel
        return retval;
    }
}
