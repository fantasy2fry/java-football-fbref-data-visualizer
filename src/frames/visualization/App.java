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
import javax.swing.border.LineBorder;

/**
 * This class is used to create user interface
 * and to generate plots according to the inputs given by the user
 */
public class App extends JFrame{
    private JPanel panelMain;
    private JTextField appDescription;
    private JButton buttonToStart;
    private JButton startButton;
    private JPanel panelChoose;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox5;
    private JButton stwórzWykresButton1;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JComboBox comboBox9;
    private JComboBox comboBox10;
    private JButton stwórzWykresButton;
    private JComboBox comboBox4;
    private JComboBox comboBox3;
    private JComboBox comboBox11;
    private JButton stwórzWykresButton2;
    private JComboBox comboBox12;
    private JComboBox comboBox13;

    private ImageIcon mainBackground;

    private Map<String, String> clubsUrls=new HashMap<>();

    private List<String> plotTypes;

    private String club1;
    private String club2;
    private String thePlotType;
    private String column;
    private String characteristic;
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

    /**
     * This method is used to initialise data in the app.
     * More precisely it adds values to comboBoxes and it sets default values
     */
    private void initialiseData(){
        this.plotTypes = new ArrayList<>();
        plotTypes.add("Vertical bar chart");
        plotTypes.add("Horizontal bar chart");
        plotTypes.add("Pie chart");
        for (String plotType : plotTypes) {
            comboBox4.addItem(plotType);
        }
        this.clubNames = getter.getAllClubNames();
        for (String club : clubNames) {
            comboBox1.addItem(club);
            comboBox2.addItem(club);
            comboBox3.addItem(club);
            comboBox6.addItem(club);
            comboBox8.addItem(club);
        }
        TwoPlayersPlots twoPlayersPlots = new TwoPlayersPlots();
        this.columnNames = twoPlayersPlots.columnNames;
        for (String characteristic : columnNames){
            if(characteristic != "n"){
                comboBox11.addItem(characteristic);
            }
        }
        // Ustalenie domyślnych danych
        club1 = clubNames.get(0);
        club2 = clubNames.get(0);
        thePlotType = plotTypes.get(0);
        characteristic = columnNames.get(4);
    }

    /**
     * Method that transforms column name (String) into id (int) that is used in other methods
     * @param column is the characteristic that user wants to analyse on the plot
     * @return int id used in other methods, it represents a specific characteristic
     */
    public static int columnToId(String column) {
        // Funkcja ma na celu zamianę nazwy kolumny na id potrzebne do stworzenia wykresu
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\d+").matcher(column);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            return -1;
        }
    }

    /**
     * Method used to transform name of the characteristic to name of the column from the data table
     * @param characteristic is the name of characteristic chosen by the user
     * @return String of the original column name
     */
    public String characteristicToColumn(String characteristic){
        TwoPlayersPlots twoPlayersPlots = new TwoPlayersPlots();
        List<String> characteristics = twoPlayersPlots.columnNames;
        //remove 4 first elements
        characteristics = characteristics.subList(4, characteristics.size());
        List<String> columns = getter.getImportantColumnNamesFromTable(getter.getPlayersStats("Real Madrid - Season 23/24"));
        int characteristicIndex = characteristics.indexOf(characteristic);
        if (characteristicIndex != -1 && characteristicIndex < columns.size()) {
            return columns.get(characteristicIndex);
        } else {
            return null;
        }
    }

    /**
     * Method used to plot a chart according to the plot type selected by the user
     * @param club is a club chosen (or default) by the user
     * @param plotType is a type of plot chosen by the user
     * @param column is the characteristic that user chose to analyse
     * @return Figure that displays value of a selected characteristic for a chosen club in the
     * form of the selcted plot type
     */
    private Figure plotTeam(String club, String plotType, String column){
        Table teamData = getter.getPlayersStats(club);
        int id = columnToId(column);
        if(plotType == "Pie chart"){
            return data.visualization.TwoPlayersPlots.kolowy(teamData, id);
        } else if (plotType == "Vertical bar chart") {
            return data.visualization.TwoPlayersPlots.slupkowyPionowy(teamData, id);
        } else if (plotType == "Horizontal bar chart"){
            return data.visualization.TwoPlayersPlots.slupkowyPoziomy(teamData, id);
        }
        return null;
    }

    /**
     * Method used to initialise players data in the comboboxes
     * according to the clubs user chose beforehand
     * @param id is 1 or 2 depending whether the method was used
     *           to initialise players names for club1 or club2
     */
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


    /**
     * Constructor used to implement the app and user interface
     */
    public App(){
        // Inicjalizacja danych
        this.getter = new PlayersStatsGetter();
        initialiseData();
        panelMain = new mainPanel();
        panelMain.add(panelChoose);

        // Tworzenie i konfiguracja przycisku START
        startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.PLAIN, 80));
        startButton.setPreferredSize(new Dimension(350, 140));
        startButton.setFocusPainted(false);
        startButton.setForeground(Color.BLACK);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));
        startButton.setToolTipText("Click to start");

        GridBagConstraints gbcStartButton = new GridBagConstraints();
        gbcStartButton.gridx = 0;
        gbcStartButton.gridy = 1;
        gbcStartButton.weightx = 1;
        gbcStartButton.weighty = 1;
        gbcStartButton.fill = GridBagConstraints.NONE;
        gbcStartButton.anchor = GridBagConstraints.CENTER;
        panelMain.add(startButton, gbcStartButton);

        panelChoose.setVisible(false);

        // ustawienia aplikacji
        setTitle("Football analysis application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Pobranie rozmiarów ekranu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height-50);
        setContentPane(panelMain);
        setLocationRelativeTo(null);
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



        comboBox6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedClub = (String) comboBox6.getSelectedItem();
                if (selectedClub != null) {
                    club1 = selectedClub;
                    initialiseTeams(1);
                    player1 = playerNames1.get(0);
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
                    player2 = playerNames2.get(0);
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

        comboBox11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCharacteristic = (String) comboBox11.getSelectedItem();
                if (selectedCharacteristic != null) {
                    characteristic = selectedCharacteristic;
                }
            }
        });



        stwórzWykresButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                column = characteristicToColumn(characteristic);
                Plot.show(plotTeam(club1, thePlotType, column));
            }
        });
        stwórzWykresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Plot.show(data.visualization.TwoPlayersPlots.twoPlayersPlot(getter.getPlayersStats(club1), getter.getPlayersStats(club2), player1, player2));
            }
        });
        stwórzWykresButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table team1=getter.getPlayersStats(club1);
                Table team2=getter.getPlayersStats(club2);
                Plot.show(data.visualization.TwoPlayersPlots.twoTeamsPlot(team1,team2));
            }
        });
    }


}
