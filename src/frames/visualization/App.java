package frames.visualization;

import data.collect.PlayersStatsGetter;
import data.visualization.TwoPlayersPlots;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.Figure;

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
    private JComboBox comboBox11;
    private JComboBox comboBox12;
    private JComboBox comboBox13;

    private ImageIcon mainBackground;

    private Map<String, String> clubsUrls=new HashMap<>();

    private List<String> plotTypes;

    private String club1;
    private String club2;
    private String thePlotType;
    private String column;
    private String player1;
    private String player2;
    private Map<String, List<String>> footballTeams;
    private List<String> clubNames;
    private List<String> columnNames;
    private List<String> playerNames1;
    private List<String> playerNames2;
    private data.collect.PlayersStatsGetter getter;
    public List<String> getPlayersForTeam(String team) {
        return footballTeams.getOrDefault(team, new ArrayList<>());
    }

    private void initialiseData(){
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
        this.clubNames = getter.getAllClubNames();
        for (String club : clubNames) {
            comboBox1.addItem(club);
            comboBox2.addItem(club);
            comboBox3.addItem(club);
            comboBox6.addItem(club);
            comboBox8.addItem(club);
        }
        this.columnNames = getter.getImportantColumnNamesFromTable(getter.getPlayersStats("Real Madrid - Sezon 23/24"));
        for (String column : columnNames){
            comboBox11.addItem(column);
            comboBox12.addItem(column);
            comboBox13.addItem(column);
        }
    }

    public static int columnToId(String column) {
        // Funkcja ma na celu zamianę nazwy kolumny na id potrzebne do stworzenia wykresu
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\d+").matcher(column);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            return -1;
        }
    }

    private Figure plotTeam(String club, String plotType, String column){
        Table teamData = getter.getPlayersStats(club);
        int id = columnToId(column);
        if(plotType == "Kołowy"){
            return data.visualization.TwoPlayersPlots.kolowy(teamData, id);
        } else if (plotType == "Słupkowy pionowy") {
            return data.visualization.TwoPlayersPlots.slupkowyPionowy(teamData, id);
        } else if (plotType == "Słupkowy poziomy"){
            return data.visualization.TwoPlayersPlots.slupkowyPoziomy(teamData, id);
        }
        return null;
    }

    private void initialiseTeams(int id){
        if(id == 1){
            Table teamData = getter.getPlayersStats(club1);
            playerNames1 = getter.getPlayersFromTable(teamData);
            comboBox7.removeAllItems();
            for (String player : playerNames1){
                comboBox7.addItem(player);
            }
        } else if (id == 2) {
            Table teamData = getter.getPlayersStats(club2);
            playerNames2 = getter.getPlayersFromTable(teamData);
            comboBox9.removeAllItems();
            for (String player : playerNames2){
                comboBox9.addItem(player);
            }
        }
    }


    public App(){
        this.getter = new PlayersStatsGetter();
        initialiseData();

        panelMain = new mainPanel();
        panelMain.add(panelChoose);

        // Tworzenie i konfiguracja etykiety z tekstem
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Analiza danych piłkarskich", SwingConstants.CENTER);
        label.setFont(new Font("Century Gothic", Font.BOLD, 110));
        label.setForeground(new Color(28,28,28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        panelMain.add(label, gbc);

        // Tworzenie i konfiguracja przycisku START
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
        gbc.weighty = 0; // Zmniejsz wartość weighty dla przycisku
        gbc.insets = new Insets(0, 0, 210, 0); // Dodaj margines od dolu, aby podnieść przycisk wyżej
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
                    initialiseTeams(1);
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
                    initialiseTeams(2);
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

        comboBox11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColumn = (String) comboBox11.getSelectedItem();
                if (selectedColumn != null) {
                    column = selectedColumn;
                }
            }
        });

        comboBox12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColumn = (String) comboBox12.getSelectedItem();
                if (selectedColumn != null) {
                    column = selectedColumn;
                }
            }
        });

        comboBox13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColumn = (String) comboBox13.getSelectedItem();
                if (selectedColumn != null) {
                    column = selectedColumn;
                }
            }
        });

        stwórzWykresButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Plot.show(plotTeam(club1, thePlotType, column));
            }
        });
        stwórzWykresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Plot.show(data.visualization.TwoPlayersPlots.twoPlayersPlot(getter.getPlayersStats(club1), getter.getPlayersStats(club2), player1, player2));
            }
        });
    }


}
