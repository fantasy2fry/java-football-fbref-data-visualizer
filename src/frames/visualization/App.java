package frames.visualization;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class App extends JFrame{
    private JPanel panelMain;
    private JTextField appDescription;
    private JButton buttonToStart;
    private JPanel panelChoose;
    private JCheckBox checkBox1;
    private JTextField llllllTextField;
    private JCheckBox checkBox2;


    public App(){
        panelChoose.setVisible(false);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        appDescription.setBorder(emptyBorder);
        //buttonToStart.setBorderPainted(false);
        buttonToStart.setFocusPainted(false);


        setTitle("Aplikacja do analiz piłkarskich");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setSize(400,400);
        // Pobranie rozmiarów ekranu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        setContentPane(panelMain);
        setLocationRelativeTo(null);  //wyśrodkowanie aplikacji
        setVisible(true);


        buttonToStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(panelChoose);
                panelChoose.setVisible(true);
                revalidate();
                repaint();
            }
        });
    }


}
