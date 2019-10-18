package gui;

import dbms.Dbms;
import dbms.SqlExecutor;
import query.CoverRolesQuery;
import query.Query;
import query.WorstOfBestQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class MainWindow extends JFrame {
    private SqlExecutor executor;

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2662352713062784659L;

    /**
     * Creates new form MainWindow
     */
    public MainWindow(SqlExecutor executor) {
        this.executor = executor;

        initComponents();
    }

    private void initComponents() {

        mainPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        tab1 = new JPanel();
        fp1 = new JPanel();
        l1 = new JLabel();
        actor3 = new JTextField();
        l2 = new JLabel();
        actor4 = new JTextField();
        baconNumber = new JButton();
        rp1 = new JPanel();
        l3 = new JLabel();
        sc1 = new JScrollPane();
        baconOut = new JTextArea();
        l4 = new JLabel();
        sc2 = new JScrollPane();
        movieOut = new JTextArea();
        l5 = new JLabel();
        sc3 = new JScrollPane();
        actorOut = new JTextArea();
        tab2 = new JPanel();
        fp2 = new JPanel();
        l6 = new JLabel();
        actor = new JTextField();
        l7 = new JLabel();
        appearances = new JTextField();
        constellationCoStars = new JButton();
        rp2 = new JPanel();
        l8 = new JLabel();
        sc4 = new JScrollPane();
        actorListOutput = new JTextArea();
        tab3 = new JPanel();
        fp3 = new JPanel();
        l9 = new JLabel();
        typecastingActor = new JTextField();
        typeCasting = new JButton();
        rp3 = new JPanel();
        l10 = new JLabel();
        sc5 = new JScrollPane();
        coverRolesOutput = new JTextArea();
        tab4 = new JPanel();
        fp4 = new JPanel();
        l11 = new JLabel();
        role = new JTextField();
        coverRoles = new JButton();
        rp4 = new JPanel();
        l12 = new JLabel();
        sc6 = new JScrollPane();
        typecastedRole = new JTextArea();
        tab5 = new JPanel();
        fp5 = new JPanel();
        l13 = new JLabel();
        actor2 = new JTextField();
        bestOfWorst = new JButton();
        rp5 = new JPanel();
        l14 = new JLabel();
        sc7 = new JScrollPane();
        worstOutput = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        mainPanel.setLayout(null);

        tab2.setLayout(null);

        fp2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp2.setLayout(new GridLayout(5, 1, 0, 5));

        l6.setFont(new Font("Arial", 1, 15));
        l6.setText("Actor Name");
        fp2.add(l6);
        fp2.add(actor);

        l7.setFont(new Font("Arial", 1, 15));
        l7.setText("# Costar Apperances");
        fp2.add(l7);
        fp2.add(appearances);

        constellationCoStars.setFont(new Font("Arial", 1, 15));
        constellationCoStars.setText("Send inputs to Function");
        constellationCoStars.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                constellationCoStarsActionPerformed(evt);
            }
        });
        fp2.add(constellationCoStars);

        tab2.add(fp2);
        fp2.setBounds(0, 0, 420, 200);

        rp2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        rp2.setLayout(null);

        l8.setFont(new Font("Arial", 1, 15));
        l8.setText("List of costars");
        rp2.add(l8);
        l8.setBounds(1, 1, 518, 40);

        actorListOutput.setColumns(20);
        actorListOutput.setRows(5);
        sc4.setViewportView(actorListOutput);

        rp2.add(sc4);
        sc4.setBounds(0, 50, 510, 300);

        tab2.add(rp2);
        rp2.setBounds(420, 0, 520, 610);

        tabbedPane.addTab("Constellation Of Co-Stars", tab2);

        tab3.setLayout(null);

        fp3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp3.setLayout(new GridLayout(3, 1, 0, 5));

        l9.setFont(new Font("Arial", 1, 15));
        l9.setText("Actor Name");
        fp3.add(l9);
        fp3.add(typecastingActor);

        typeCasting.setFont(new Font("Arial", 1, 15));
        typeCasting.setText("Send input to function");
        typeCasting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                typeCastingActionPerformed(evt);
            }
        });
        fp3.add(typeCasting);

        tab3.add(fp3);
        fp3.setBounds(0, 0, 340, 130);

        rp3.setLayout(null);

        l10.setFont(new Font("Arial", 1, 15));
        l10.setText("Most Common Genre");
        rp3.add(l10);
        l10.setBounds(0, 0, 600, 40);

        typecastedRole.setColumns(20);
        typecastedRole.setRows(5);
        sc5.setViewportView(typecastedRole);

        rp4.add(sc6);
        sc5.setBounds(0, 40, 600, 370);

        tab3.add(rp3);
        rp3.setBounds(340, 0, 600, 610);

        tabbedPane.addTab("Typecasting", tab3);

        tab4.setLayout(null);

        fp4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp4.setLayout(new GridLayout(3, 1, 0, 5));

        l11.setFont(new Font("Arial", 1, 15));
        l11.setText("Role/Character");
        fp4.add(l11);
        fp4.add(role);

        coverRoles.setFont(new Font("Arial", 1, 15));
        coverRoles.setText("Send input to function");
        coverRoles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                coverRolesActionPerformed(evt);
            }
        });
        fp4.add(coverRoles);

        tab4.add(fp4);
        fp4.setBounds(0, 0, 340, 130);

        rp4.setLayout(null);

        l12.setFont(new Font("Arial", 1, 15));
        l12.setText("Most Common Actors");
        rp4.add(l12);
        l12.setBounds(0, 0, 600, 40);

        coverRolesOutput.setColumns(20);
        coverRolesOutput.setRows(5);
        sc6.setViewportView(coverRolesOutput);

        rp3.add(sc5);
        sc6.setBounds(0, 40, 600, 370);

        tab4.add(rp4);
        rp4.setBounds(340, 0, 600, 610);

        tabbedPane.addTab("Cover Roles", tab4);

        tab5.setLayout(null);

        fp5.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp5.setLayout(new GridLayout(3, 1, 0, 5));

        l13.setFont(new Font("Arial", 1, 15));
        l13.setText("Actor Name");
        fp5.add(l13);
        fp5.add(actor2);

        bestOfWorst.setFont(new Font("Arial", 1, 15));
        bestOfWorst.setText("Send input to function");
        bestOfWorst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bestOfWorstActionPerformed(evt);
            }
        });
        fp5.add(bestOfWorst);

        tab5.add(fp5);
        fp5.setBounds(0, 0, 340, 130);

        rp5.setLayout(null);

        l14.setFont(new Font("Arial", 1, 15));
        l14.setText("Worst Movie of Director of Best Film of The Actor");
        rp5.add(l14);
        l14.setBounds(0, 0, 600, 40);

        worstOutput.setColumns(20);
        worstOutput.setRows(5);
        sc7.setViewportView(worstOutput);

        rp5.add(sc7);
        sc7.setBounds(0, 40, 600, 370);

        tab5.add(rp5);
        rp5.setBounds(340, 0, 600, 610);

        tabbedPane.addTab("Best And Worst", tab5);

        mainPanel.add(tabbedPane);
        tabbedPane.setBounds(0, 0, 950, 650);

        getContentPane().add(mainPanel);
        mainPanel.setBounds(0, 0, 950, 650);

        pack();
    }

    private void tf1ActionPerformed(ActionEvent evt) {
        System.out.println("Text Field");

    }

    private void constellationCoStarsActionPerformed(ActionEvent evt) {
        Query q = new Query(executor);
        String user1 = actor.getText();
        String user2 = appearances.getText();
        int user3 = Integer.parseInt(user2);
        List<String> functionCallList = q.calcCostarAppearances(user1, user3);
        String functionCall = "";
        for (int i = 0; i < functionCallList.size(); i++){
            functionCall += functionCallList.get(i) + "\n";
        }
        actorListOutput.setText(functionCall);

    }

    private void typeCastingActionPerformed(ActionEvent evt) {
        Query q = new Query(executor);
        String toTypecast = typecastingActor.getText();
        String typecastOutput = q.calcMostCommonGenre(toTypecast);
        typecastedRole.setText(typecastOutput);

    }

    private void coverRolesActionPerformed(ActionEvent evt) {
        String roleToQuery = role.getText();
        CoverRolesQuery q = new CoverRolesQuery(executor);
        List<String> actorList = q.calcActorsWhichPlayedCharacter(roleToQuery);
        String actors = "";
        for (int i = 0; i < actorList.size(); i++){
            actors += actorList.get(i) + "\n";
        }
        coverRolesOutput.setText(actors);
    }

    private void bestOfWorstActionPerformed(ActionEvent evt) {
        String user = actor2.getText();
        WorstOfBestQuery q = new WorstOfBestQuery(executor);
        String worstMovie = q.calcWorstOfBests(user);
        worstOutput.setText(worstMovie);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton baconNumber;
    private JButton constellationCoStars;
    private JButton typeCasting;
    private JButton coverRoles;
    private JButton bestOfWorst;
    private JPanel fp1;
    private JPanel fp2;
    private JPanel fp3;
    private JPanel fp4;
    private JPanel fp5;
    private JLabel l1;
    private JLabel l10;
    private JLabel l11;
    private JLabel l12;
    private JLabel l13;
    private JLabel l14;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private JLabel l6;
    private JLabel l7;
    private JLabel l8;
    private JLabel l9;
    private JTextField actor;
    private JPanel mainPanel;
    private JPanel rp1;
    private JPanel rp2;
    private JPanel rp3;
    private JPanel rp4;
    private JPanel rp5;
    private JScrollPane sc1;
    private JScrollPane sc2;
    private JScrollPane sc3;
    private JScrollPane sc4;
    private JScrollPane sc5;
    private JScrollPane sc6;
    private JScrollPane sc7;
    private JPanel tab1;
    private JPanel tab2;
    private JPanel tab3;
    private JPanel tab4;
    private JPanel tab5;
    private JTabbedPane tabbedPane;
    private JTextField actor3;
    private JTextField role;
    private JTextField actor2;
    private JTextField actor4;
    private JTextField appearances;
    private JTextField typecastingActor;
    private JTextArea baconOut;
    private JTextArea movieOut;
    private JTextArea actorOut;
    private JTextArea actorListOutput;
    private JTextArea coverRolesOutput;
    private JTextArea typecastedRole;
    private JTextArea worstOutput;
    // End of variables declaration//GEN-END:variables
}