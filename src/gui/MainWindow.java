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
    private static SqlExecutor executor = new SqlExecutor(new Dbms());

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 2662352713062784659L;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }

    private void initComponents() {

        mainPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        tab1 = new JPanel();
        fp1 = new JPanel();
        l1 = new JLabel();
        tf1 = new JTextField();
        l2 = new JLabel();
        tf2 = new JTextField();
        btn1 = new JButton();
        rp1 = new JPanel();
        l3 = new JLabel();
        sc1 = new JScrollPane();
        txa1 = new JTextArea();
        l4 = new JLabel();
        sc2 = new JScrollPane();
        txa2 = new JTextArea();
        l5 = new JLabel();
        sc3 = new JScrollPane();
        txa3 = new JTextArea();
        tab2 = new JPanel();
        fp2 = new JPanel();
        actor = new JLabel();
        lf3 = new JTextField();
        appearances = new JLabel();
        tf4 = new JTextField();
        btn2 = new JButton();
        rp2 = new JPanel();
        actorListOutput = new JLabel();
        sc4 = new JScrollPane();
        txa4 = new JTextArea();
        tab3 = new JPanel();
        fp3 = new JPanel();
        typecastingActor = new JLabel();
        tf9 = new JTextField();
        btn3 = new JButton();
        rp3 = new JPanel();
        typecastedRole = new JLabel();
        sc5 = new JScrollPane();
        txa5 = new JTextArea();
        tab4 = new JPanel();
        fp4 = new JPanel();
        role = new JLabel();
        tf11 = new JTextField();
        btn4 = new JButton();
        rp4 = new JPanel();
        coverRolesOutput = new JLabel();
        sc6 = new JScrollPane();
        txa6 = new JTextArea();
        tab5 = new JPanel();
        fp5 = new JPanel();
        actor2 = new JLabel();
        tf13 = new JTextField();
        btn5 = new JButton();
        rp5 = new JPanel();
        worstOutput = new JLabel();
        sc7 = new JScrollPane();
        txa7 = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setResizable(false);
        getContentPane().setLayout(null);

        mainPanel.setLayout(null);

        tab1.setLayout(null);

        fp1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp1.setLayout(new GridLayout(5, 1, 0, 5));

        l1.setFont(new Font("Arial", 1, 15));
        l1.setText("Actor 1 Name");
        fp1.add(l1);

        tf1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tf1ActionPerformed(evt);
            }
        });
        fp1.add(tf1);

        l2.setFont(new Font("Arial", 1, 15));
        l2.setText("Actor 2 Name");
        fp1.add(l2);
        fp1.add(tf2);

        btn1.setFont(new Font("Arial", 1, 15));
        btn1.setText("Send Values to fucntion");
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                baconNumberActionPerformed(evt);
            }
        });
        fp1.add(btn1);

        tab1.add(fp1);
        fp1.setBounds(0, 0, 400, 240);

        rp1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        rp1.setLayout(new GridLayout(6, 1, 0, 5));

        l3.setFont(new Font("Arial", 1, 15));
        l3.setText("Bacon Number");
        rp1.add(l3);

        txa1.setColumns(20);
        txa1.setRows(5);
        sc1.setViewportView(txa1);

        rp1.add(sc1);

        l4.setFont(new Font("Arial", 1, 15));
        l4.setText("List of connecting moviews");
        rp1.add(l4);

        txa2.setColumns(20);
        txa2.setRows(5);
        sc2.setViewportView(txa2);

        rp1.add(sc2);

        l5.setFont(new Font("Arial", 1, 15));
        l5.setText("List of connecting actors");
        rp1.add(l5);

        txa3.setColumns(20);
        txa3.setRows(5);
        sc3.setViewportView(txa3);

        rp1.add(sc3);

        tab1.add(rp1);
        rp1.setBounds(400, 0, 540, 610);

        tabbedPane.addTab("Bacon Number", tab1);

        tab2.setLayout(null);

        fp2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp2.setLayout(new GridLayout(5, 1, 0, 5));

        actor.setFont(new Font("Arial", 1, 15));
        actor.setText("Actor Name");
        fp2.add(actor);
        fp2.add(lf3);

        appearances.setFont(new Font("Arial", 1, 15));
        appearances.setText("# Costar Apperances");
        fp2.add(appearances);
        fp2.add(tf4);

        btn2.setFont(new Font("Arial", 1, 15));
        btn2.setText("Send inputs to Function");
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                constellationCoStarsActionPerformed(evt);
            }
        });
        fp2.add(btn2);

        tab2.add(fp2);
        fp2.setBounds(0, 0, 420, 200);

        rp2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        rp2.setLayout(null);

        actorListOutput.setFont(new Font("Arial", 1, 15));
        actorListOutput.setText("List of costars");
        rp2.add(actorListOutput);
        actorListOutput.setBounds(1, 1, 518, 40);

        txa4.setColumns(20);
        txa4.setRows(5);
        sc4.setViewportView(txa4);

        rp2.add(sc4);
        sc4.setBounds(0, 50, 510, 300);

        tab2.add(rp2);
        rp2.setBounds(420, 0, 520, 610);

        tabbedPane.addTab("Constellation Of Co-Stars", tab2);

        tab3.setLayout(null);

        fp3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp3.setLayout(new GridLayout(3, 1, 0, 5));

        typecastingActor.setFont(new Font("Arial", 1, 15));
        typecastingActor.setText("Actor Name");
        fp3.add(typecastingActor);
        fp3.add(tf9);

        btn3.setFont(new Font("Arial", 1, 15));
        btn3.setText("Send input to function");
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                typeCastingActionPerformed(evt);
            }
        });
        fp3.add(btn3);

        tab3.add(fp3);
        fp3.setBounds(0, 0, 340, 130);

        rp3.setLayout(null);

        typecastedRole.setFont(new Font("Arial", 1, 15));
        typecastedRole.setText("Most Common Role");
        rp3.add(typecastedRole);
        typecastedRole.setBounds(0, 0, 600, 40);

        txa5.setColumns(20);
        txa5.setRows(5);
        sc5.setViewportView(txa5);

        rp3.add(sc5);
        sc5.setBounds(0, 40, 600, 370);

        tab3.add(rp3);
        rp3.setBounds(340, 0, 600, 610);

        tabbedPane.addTab("Typecasting", tab3);

        tab4.setLayout(null);

        fp4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp4.setLayout(new GridLayout(3, 1, 0, 5));

        role.setFont(new Font("Arial", 1, 15));
        role.setText("Actor Name");
        fp4.add(role);
        fp4.add(tf11);

        btn4.setFont(new Font("Arial", 1, 15));
        btn4.setText("Send input to function");
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                coverRolesActionPerformed(evt);
            }
        });
        fp4.add(btn4);

        tab4.add(fp4);
        fp4.setBounds(0, 0, 340, 130);

        rp4.setLayout(null);

        coverRolesOutput.setFont(new Font("Arial", 1, 15));
        coverRolesOutput.setText("Most Common Genre");
        rp4.add(coverRolesOutput);
        coverRolesOutput.setBounds(0, 0, 600, 40);

        txa6.setColumns(20);
        txa6.setRows(5);
        sc6.setViewportView(txa6);

        rp4.add(sc6);
        sc6.setBounds(0, 40, 600, 370);

        tab4.add(rp4);
        rp4.setBounds(340, 0, 600, 610);

        tabbedPane.addTab("Cover Roles", tab4);

        tab5.setLayout(null);

        fp5.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        fp5.setLayout(new GridLayout(3, 1, 0, 5));

        actor2.setFont(new Font("Arial", 1, 15));
        actor2.setText("Actor Name");
        fp5.add(actor2);
        fp5.add(tf13);

        btn5.setFont(new Font("Arial", 1, 15));
        btn5.setText("Send input to function");
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bestWorst5ActionPerformed(evt);
            }
        });
        fp5.add(btn5);

        tab5.add(fp5);
        fp5.setBounds(0, 0, 340, 130);

        rp5.setLayout(null);

        worstOutput.setFont(new Font("Arial", 1, 15));
        worstOutput.setText("Worst Movie of Director of  Best Actor");
        rp5.add(worstOutput);
        worstOutput.setBounds(0, 0, 600, 40);

        txa7.setColumns(20);
        txa7.setRows(5);
        sc7.setViewportView(txa7);

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
        System.out.println("Text field 1");

    }

    private void constellationCoStarsActionPerformed(ActionEvent evt) {
        String user1 = actor.getText();
        String user2 = appearances.getText();
        int user3 = 0;
        try {
            user3 = Integer.parseInt(user2);
        } catch (NumberFormatException e) {
            System.out.println("Numerical exception");
        }
        List<String> functionCallList = Arrays.asList("0", "1", "2")/*calcCostarAppearances(user1, user3)*/;
        String functionCall = "";
        for (int i = 0; i < functionCallList.size(); i++){
            functionCall += functionCallList.get(i) + "\n";
        }
        actorListOutput.setText(functionCall);

    }

    private void baconNumberActionPerformed(ActionEvent evt) {
        System.out.println("Bacon Number");

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

    private void bestWorst5ActionPerformed(ActionEvent evt) {
        String user = actor2.getText();
        WorstOfBestQuery q = new WorstOfBestQuery(executor);
        String worstMovie = q.calcWorstOfBests(user);
        worstOutput.setText(worstMovie);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setLocationByPlatform(true);
        mainWindow.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JPanel fp1;
    private JPanel fp2;
    private JPanel fp3;
    private JPanel fp4;
    private JPanel fp5;
    private JLabel l1;
    private JLabel typecastedRole;
    private JLabel role;
    private JLabel coverRolesOutput;
    private JLabel actor2;
    private JLabel worstOutput;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private JLabel actor;
    private JLabel appearances;
    private JLabel actorListOutput;
    private JLabel typecastingActor;
    private JTextField lf3;
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
    private JTextField tf1;
    private JTextField tf11;
    private JTextField tf13;
    private JTextField tf2;
    private JTextField tf4;
    private JTextField tf9;
    private JTextArea txa1;
    private JTextArea txa2;
    private JTextArea txa3;
    private JTextArea txa4;
    private JTextArea txa5;
    private JTextArea txa6;
    private JTextArea txa7;
    // End of variables declaration//GEN-END:variables
}