package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

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
        l6 = new JLabel();
        lf3 = new JTextField();
        l7 = new JLabel();
        tf4 = new JTextField();
        btn2 = new JButton();
        rp2 = new JPanel();
        l8 = new JLabel();
        sc4 = new JScrollPane();
        txa4 = new JTextArea();
        tab3 = new JPanel();
        fp3 = new JPanel();
        l9 = new JLabel();
        tf9 = new JTextField();
        btn3 = new JButton();
        rp3 = new JPanel();
        l10 = new JLabel();
        sc5 = new JScrollPane();
        txa5 = new JTextArea();
        tab4 = new JPanel();
        fp4 = new JPanel();
        l11 = new JLabel();
        tf11 = new JTextField();
        btn4 = new JButton();
        rp4 = new JPanel();
        l12 = new JLabel();
        sc6 = new JScrollPane();
        txa6 = new JTextArea();
        tab5 = new JPanel();
        fp5 = new JPanel();
        l13 = new JLabel();
        tf13 = new JTextField();
        btn5 = new JButton();
        rp5 = new JPanel();
        l14 = new JLabel();
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
                btn1ActionPerformed(evt);
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

        l6.setFont(new Font("Arial", 1, 15));
        l6.setText("Actor Name");
        fp2.add(l6);
        fp2.add(lf3);

        l7.setFont(new Font("Arial", 1, 15));
        l7.setText("# Costar Apperances");
        fp2.add(l7);
        fp2.add(tf4);

        btn2.setFont(new Font("Arial", 1, 15));
        btn2.setText("Send inputs to Function");
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });
        fp2.add(btn2);

        tab2.add(fp2);
        fp2.setBounds(0, 0, 420, 200);

        rp2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        rp2.setLayout(null);

        l8.setFont(new Font("Arial", 1, 15));
        l8.setText("List of costars");
        rp2.add(l8);
        l8.setBounds(1, 1, 518, 40);

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

        l9.setFont(new Font("Arial", 1, 15));
        l9.setText("Actor Name");
        fp3.add(l9);
        fp3.add(tf9);

        btn3.setFont(new Font("Arial", 1, 15));
        btn3.setText("Send input to function");
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });
        fp3.add(btn3);

        tab3.add(fp3);
        fp3.setBounds(0, 0, 340, 130);

        rp3.setLayout(null);

        l10.setFont(new Font("Arial", 1, 15));
        l10.setText("Most Common Role");
        rp3.add(l10);
        l10.setBounds(0, 0, 600, 40);

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

        l11.setFont(new Font("Arial", 1, 15));
        l11.setText("Actor Name");
        fp4.add(l11);
        fp4.add(tf11);

        btn4.setFont(new Font("Arial", 1, 15));
        btn4.setText("Send input to function");
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });
        fp4.add(btn4);

        tab4.add(fp4);
        fp4.setBounds(0, 0, 340, 130);

        rp4.setLayout(null);

        l12.setFont(new Font("Arial", 1, 15));
        l12.setText("Most Common Genre");
        rp4.add(l12);
        l12.setBounds(0, 0, 600, 40);

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

        l13.setFont(new Font("Arial", 1, 15));
        l13.setText("Actor Name");
        fp5.add(l13);
        fp5.add(tf13);

        btn5.setFont(new Font("Arial", 1, 15));
        btn5.setText("Send input to function");
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });
        fp5.add(btn5);

        tab5.add(fp5);
        fp5.setBounds(0, 0, 340, 130);

        rp5.setLayout(null);

        l14.setFont(new Font("Arial", 1, 15));
        l14.setText("Worst Movie of Director of  Best Actor");
        rp5.add(l14);
        l14.setBounds(0, 0, 600, 40);

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

    }

    private void btn2ActionPerformed(ActionEvent evt) {

    }

    private void btn1ActionPerformed(ActionEvent evt) {

    }

    private void btn3ActionPerformed(ActionEvent evt) {

    }

    private void btn4ActionPerformed(ActionEvent evt) {
    }

    private void btn5ActionPerformed(ActionEvent evt) {

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