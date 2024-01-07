package frames.visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class App extends JFrame{
    private JPanel panelMain;
    private JTextField appDescription;
    private JButton buttonToStart;
    private JButton startButton;
    private JPanel panelChoose;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JButton stwórzWykresButton2;
    private JComboBox comboBox5;
    private JButton stwórzWykresButton1;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JComboBox comboBox9;
    private JComboBox comboBox10;
    private JButton stwórzWykresButton;

    private ImageIcon mainBackground;

    private Map<String, String> clubsUrls=new HashMap<>();

    private List<String> plotTypes;

    private String club1;
    private String club2;
    private String thePlotType;
    private String player1;
    private String player2;

    private Map<String, List<String>> footballTeams;

    public List<String> getPlayersForTeam(String team) {
        return footballTeams.getOrDefault(team, new ArrayList<>());
    }

    private void initialiseExample(){
        clubsUrls.put("Arsenal","https://fbref.com/en/squads/18bb7c10/2023-2024/all_comps/Arsenal-Stats-All-Competitions#all_stats_standard");
        clubsUrls.put("Barcelona", "https://fbref.com/en/squads/206d90db/2023-2024/all_comps/Barcelona-Stats-All-Competitions");
        for (String club : clubsUrls.keySet()) {
            comboBox1.addItem(club);
            comboBox2.addItem(club);
            comboBox3.addItem(club);
            comboBox6.addItem(club);
            comboBox8.addItem(club);
        }
        this.plotTypes = new ArrayList<>();
        plotTypes.add("Słupkowy pionowy");
        plotTypes.add("Słupkowy poziomy");
        plotTypes.add("Kołowy");
        plotTypes.add("Słupkowy podwójny");
        for (String plotType : plotTypes) {
            comboBox4.addItem(plotType);
            comboBox5.addItem(plotType);
            comboBox10.addItem(plotType);
        }
    }

    private void initialiseTeams(){
        footballTeams = new HashMap<>();
        List<String> arsenalPlayers = new ArrayList<>();
        arsenalPlayers.add("William Saliba");
        arsenalPlayers.add("Ben White");
        arsenalPlayers.add("Gabriel");
        arsenalPlayers.add("Jurrien Timber");
        arsenalPlayers.add("Jakub Kiwior");
        footballTeams.put("Arsenal", arsenalPlayers);
        if(Objects.equals(club1, "Arsenal")){
            for (String player : arsenalPlayers) {
                comboBox7.addItem(player);;
            }
        }
        if(Objects.equals(club2, "Arsenal")){
            for (String player : arsenalPlayers) {
                comboBox9.addItem(player);;
            }
        }
        List<String> barcelonaPlayers = new ArrayList<>();
        barcelonaPlayers.add("Robert Lewandowski");
        barcelonaPlayers.add("Frenkie De Jong");
        barcelonaPlayers.add("Ilkay Gundogan");
        barcelonaPlayers.add("Angel Alarcon");
        barcelonaPlayers.add("Marc Casado");
        footballTeams.put("Barcelona", barcelonaPlayers);
        if(Objects.equals(club1, "Barcelona")){
            for (String player : barcelonaPlayers) {
                comboBox7.addItem(player);;
            }
        }
        if(Objects.equals(club2, "Barcelona")){
            for (String player : barcelonaPlayers) {
                comboBox9.addItem(player);;
            }
        }
    }


    public App(){
        initialiseExample();

        panelMain = new mainPanel();
        panelMain.add(panelChoose);

        // Tworzenie i konfiguracja przycisku START
        GridBagConstraints gbc = new GridBagConstraints();
        startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(320, 130));
        startButton.setFont(new Font("Arial", Font.BOLD, 80));
        startButton.setFocusPainted(false);
        startButton.setForeground(Color.BLACK); // Kolor tekstu przycisku na czarny
        startButton.setOpaque(false); // Ustawienie przycisku jako nieprzezroczystego
        startButton.setContentAreaFilled(false); // Wyłączenie wypełnienia tła
        startButton.setBorder(new LineBorder(Color.BLACK, 7)); // Czarne obramowanie
        startButton.setToolTipText("Kliknij, aby rozpocząć");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelMain.add(startButton, gbc);

        panelChoose.setVisible(false);


        // ustawienia aplikacji
        setTitle("Aplikacja do analiz piłkarskich");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Pobranie rozmiarów ekranu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height-50);
        setContentPane(panelMain);
        setLocationRelativeTo(null);  //wyśrodkowanie aplikacji
        setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(panelChoose);
                panelChoose.setVisible(true);
                revalidate();
                repaint();
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClub = (String) comboBox1.getSelectedItem();
                if (selectedClub != null) {
                    club1 = selectedClub;
                }
            }
        });

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClub = (String) comboBox2.getSelectedItem();
                if (selectedClub != null) {
                    club2 = selectedClub;
                }
            }
        });

        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClub = (String) comboBox3.getSelectedItem();
                if (selectedClub != null) {
                    club1 = selectedClub;
                }
            }
        });

        comboBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlotType = (String) comboBox4.getSelectedItem();
                if (selectedPlotType != null) {
                    thePlotType = selectedPlotType;
                }
            }
        });

        comboBox5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlotType = (String) comboBox4.getSelectedItem();
                if (selectedPlotType != null) {
                    thePlotType = selectedPlotType;
                }
            }
        });

        comboBox6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClub = (String) comboBox6.getSelectedItem();
                if (selectedClub != null) {
                    club1 = selectedClub;
                    initialiseTeams();
                }
            }
        });

        comboBox7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlayer = (String) comboBox7.getSelectedItem();
                if (selectedPlayer != null) {
                    player1 = selectedPlayer;
                }
            }
        });

        comboBox8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClub = (String) comboBox8.getSelectedItem();
                if (selectedClub != null) {
                    club2 = selectedClub;
                    initialiseTeams();
                }
            }
        });

        comboBox9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlayer = (String) comboBox9.getSelectedItem();
                if (selectedPlayer != null) {
                    player2 = selectedPlayer;
                }
            }
        });

        comboBox10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlotType = (String) comboBox4.getSelectedItem();
                if (selectedPlotType != null) {
                    thePlotType = selectedPlotType;
                }
            }
        });
    }


}
