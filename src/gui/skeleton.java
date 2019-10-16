package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import query.interfaces.*;


public class skeleton extends JFrame {
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
         // Remove after initial test
         JButton test = new JButton("omegaLUL");
         mainframe.add(test);
         mainframe.add(generateGraphicPanel());
         //mainframe.pack();
         mainframe.setVisible(true); */

        /** Layout formatting test */
        JFrame mainframe = new JFrame("DBMS Queries");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(1000, 1000);
        mainframe.setLayout(new BorderLayout());
        mainframe.add(generateGraphicPanel());
        mainframe.setVisible(true);
    }

    public static JPanel generateGraphicPanel() {
        JPanel mainPanel = new JPanel();
        JTabbedPane mainTab = new JTabbedPane();
        JPanel typecastingPanel = generateTypecasting();
        JPanel coverRolesPanel = generateCoverRoles();
        JPanel bestAndWorstPanel = generateBestAndWorst();
        JPanel baconPanel = generateBaconNumber();
        JPanel constellationPanel = generateConstellation();
        mainTab.add("Typecasting", typecastingPanel); // typecasting panel here
        mainTab.add("Cover Roles", coverRolesPanel); // cover roles panel here
        mainTab.add("Best of Days, Worst of Days", bestAndWorstPanel);
        mainTab.add("Bacon Number", baconPanel);
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
                // calling function to generate typecast
                String functionCall = "0"/*calcMostCommonGenre(user)*/;
                // use .setText() function to get actor output to text field
                output.setText(functionCall);
                System.out.println("Typecasting called");
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
        JTextField output = new JTextField(30);
        c.gridx = 0;
        c.gridy = 4;
        retval.add(output, c);
        // use .getText() function to get input from this text field
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user = actor1.getText();
                // calling function to generate CoverRoles
                List<String> functionCallList = Arrays.asList("0", "1", "2")/*calcActorsWhichPlayedCharacter(user)*/;
                String functionCall = "";
                for (int i = 0; i < functionCallList.size(); i++){
                    functionCall += functionCallList.get(i) + "\n";
                }
                // use .setText() function to get actor output to text field
                output.setText(functionCall);
                System.out.println("CoverRoles called");
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
        JLabel outputText = new JLabel("These actors have played your character ...");
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
                String functionCall = "0"/*calcWorstOfBests(user)*/;
                // use .setText() function to get actor output to text field
                output.setText(functionCall);
                System.out.println("BestAndWorst called");
            }
        });
        // return BestAndWorst JPanel
        return retval;
    }

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
        JLabel actor2 = new JLabel("Enter second actor name below!");
        c.gridy = 2;
        retval.add(actor2, c);
        JTextField actor1 = new JTextField(30);
        c.gridy = 1;
        // use .getText() function to get input from this text field
        retval.add(actor1, c);
        // Outputs are bacon number, list of connecting movies, list of connecting actors
        JLabel baconNumber = new JLabel("The bacon number is ...");
        c.gridy = 6;
        retval.add(baconNumber, c);
        JLabel connectingMovies = new JLabel("The connecting movies are ...");
        c.gridy = 8;
        retval.add(connectingMovies, c);
        JLabel connectingActors = new JLabel("The connecting actors are ...");
        c.gridy = 10;
        retval.add(connectingActors, c);
        JTextField baconOut = new JTextField(30);
        c.gridy = 7;
        retval.add(baconOut, c);
        JTextField movieOut = new JTextField(30);
        c.gridy = 9;
        retval.add(movieOut, c);
        JTextField actorOut = new JTextField(30);
        c.gridy = 11;
        retval.add(actorOut, c);
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user1 = actor1.getText();
                String user2 = actor2.getText();
                // calling function to generate BaconNumber
                List<String> functionCallList = Arrays.asList("0", "1", "2")/*calcDegreeOfSeparation(user1, user2)*/;
                String functionCall = "";
                // use .setText() function to get actor output to text field
                baconOut.setText(functionCallList.get(0));
                movieOut.setText(functionCallList.get(1));
                actorOut.setText(functionCallList.get(2));
                System.out.println("BaconNumber called");
            }
        });
        // return Typecast JPanel
        return retval;
    }

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
        JTextField actor2 = new JTextField(30);
        // use .getText() function to get input from this text field
        c.gridy = 4;
        retval.add(actor2, c);
        callQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String user1 = actor1.getText();
                String user2 = actor2.getText();
                //System.out.println("Constellation called");
            }
        });
        // calling function to generate Constellation
        String functionCall = "0";
        // Outputs are list of co-stars with input appearances
        JLabel outText = new JLabel("These co-stars have the required number of appearances ...");
        c.gridy = 6;
        retval.add(outText, c);
        JTextField costarOut = new JTextField(30);
        c.gridy = 7;
        retval.add(costarOut, c);
        // use .setText() function to get actor output to text field
        costarOut.setText(functionCall);
        // return Typecast JPanel
        return retval;
    }
}

