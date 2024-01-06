
package data.collect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.DataFrameReader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class PlayersStatsGetter {
    private Map<String, String> clubsUrls=new HashMap<>();

    public static void main(String[] args) {
        PlayersStatsGetter playersStatsGetter=new PlayersStatsGetter();
        Table table=playersStatsGetter.getPlayersStats("Napoli");
        System.out.println(table);
        table=playersStatsGetter.getPlayersStats("Real Madrid");
        System.out.println(table);
        System.out.println(playersStatsGetter.getPlayersFromTable(table));
        System.out.println(playersStatsGetter.getAllClubNames());
        System.out.println(playersStatsGetter.getImportantColumnNamesFromTable(table));

//        String websiteUrl = "https://fbref.com/en/squads/18bb7c10/2023-2024/all_comps/Arsenal-Stats-All-Competitions#all_stats_standard"; // Replace with the website URL you want to scrape
//        Table table;
//        List<List<String>> tableData = playersStats(websiteUrl);
//        System.out.println(tableData);
//        table=createTableFromListByColumns(tableData, "Players Stats");
//        System.out.println(table);
//        System.out.println("-------Transpose-------");
//        table=table.transpose();
//        System.out.println(table);
//        table=createTableFromListByRows(tableData, "Players Stats");
//        System.out.println(table);
//        System.out.println("-------Transpose-------");
//        table=table.transpose();
//        System.out.println(table);
        //System.out.println(playersStats("https://fbref.com/en/comps/9/stats/Premier-League-Stats", 0));
    }
    public PlayersStatsGetter(){
        clubsUrls.put("Arsenal","https://fbref.com/en/squads/18bb7c10/2023-2024/all_comps/Arsenal-Stats-All-Competitions#all_stats_standard");
        clubsUrls.put("Barcelona", "https://fbref.com/en/squads/206d90db/2023-2024/all_comps/Barcelona-Stats-All-Competitions");
        clubsUrls.put("Liverpool","https://fbref.com/en/squads/822bd0ba/2023-2024/all_comps/Liverpool-Stats-All-Competitions");
        clubsUrls.put("Manchester City","https://fbref.com/en/squads/b8fd03ef/2023-2024/all_comps/Manchester-City-Stats-All-Competitions");
        clubsUrls.put("Aston Villa", "https://fbref.com/en/squads/8602292d/2023-2024/all_comps/Aston-Villa-Stats-All-Competitions");
        clubsUrls.put("Paris Saint-Germain","https://fbref.com/en/squads/e2d8892c/2023-2024/all_comps/Paris-Saint-Germain-Stats-All-Competitions");
        clubsUrls.put("Bayern Munich", "https://fbref.com/en/squads/054efa67/2023-2024/all_comps/Bayern-Munich-Stats-All-Competitions");
        clubsUrls.put("Bayer Leverkusen","https://fbref.com/en/squads/c7a9f859/2023-2024/all_comps/Bayer-Leverkusen-Stats-All-Competitions");
        clubsUrls.put("Girona","https://fbref.com/en/squads/9024a00a/2023-2024/all_comps/Girona-Stats-All-Competitions");
        clubsUrls.put("Real Madrid","https://fbref.com/en/squads/53a2f082/2023-2024/all_comps/Real-Madrid-Stats-All-Competitions");
        clubsUrls.put("Manchester United","https://fbref.com/en/squads/19538871/2023-2024/all_comps/Manchester-United-Stats-All-Competitions");
        clubsUrls.put("Nice","https://fbref.com/en/squads/132ebc33/2023-2024/all_comps/Nice-Stats-All-Competitions");
        clubsUrls.put("Tottenham Hotspur","https://fbref.com/en/squads/361ca564/2023-2024/all_comps/Tottenham-Hotspur-Stats-All-Competitions");
        clubsUrls.put("Chelsea","https://fbref.com/en/squads/cff3d9bb/2023-2024/all_comps/Chelsea-Stats-All-Competitions");
        clubsUrls.put("Internazionale","https://fbref.com/en/squads/d609edc0/2023-2024/all_comps/Internazionale-Stats-All-Competitions");
        clubsUrls.put("Juventus","https://fbref.com/en/squads/e0652b02/2023-2024/all_comps/Juventus-Stats-All-Competitions");
        clubsUrls.put("Brighton","https://fbref.com/en/squads/d07537b9/2023-2024/all_comps/Brighton-and-Hove-Albion-Stats-All-Competitions");
        clubsUrls.put("Napoli","https://fbref.com/en/squads/d48ad4ff/2023-2024/all_comps/Napoli-Stats-All-Competitions");
    }

    public Table getPlayersStats(String clubName){
        String websiteUrl=clubsUrls.get(clubName);
        List<List<String>> tableData = playersStats(websiteUrl);
        Table table=createTableFromListByRows(tableData, clubName);
        return table;
    }

    private static List<List<String>> extractTableData(Element table) {
        List<List<String>> tableData = new ArrayList<>();

        Elements rows = table.select("tr");

        for (Element row : rows) {
            Elements cols = row.select("td, th");
            List<String> rowData = new ArrayList<>();
            for (Element col : cols) {
                rowData.add(col.text());
            }
            tableData.add(rowData);
        }

        return tableData;
    }

    private static List<List<String>> playersStats(String websiteUrl){
        try {
            Document doc = Jsoup.connect(websiteUrl).get();
            Elements tables = doc.select("table");
            Element table=tables.get(0);
            List<List<String>> tableData = extractTableData(table);
            return tableData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<List<String>> playersStats(String websiteUrl, int whichTable){
        try {
            Document doc = Jsoup.connect(websiteUrl).get();
            System.out.println(doc);
            Elements tables = doc.select("table");
            System.out.println(tables.size());
            Element table=tables.get(whichTable);

            List<List<String>> tableData = extractTableData(table);
            return tableData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Table createTableFromListByColumns(List<List<String>> data, String tableName) {
        int columns = data.get(1).size();
        List<StringColumn> stringColumns = new ArrayList<>();
        Table table;
        // Create StringColumns for the table
        for(int i=1;i<data.size();i++){
            StringColumn stringColumn = StringColumn.create(data.get(i).get(0), data.get(i).subList(1, columns));
            stringColumns.add(stringColumn);
        }
        table = Table.create(tableName);
        // Add the StringColumns to the table
        for (StringColumn stringColumn : stringColumns) {
            table.addColumns(stringColumn);
        }
        return table;
    }
    private static Table createTableFromListByRows(List<List<String>> data, String tableName) {
        int columns =data.get(1).size();
        int rows= data.size();
        Table table=Table.create(tableName);
        for (int i=0;i<columns;i++){
            List<String> list=new ArrayList<>();
            String columnName=((Integer)i).toString()+ " "+data.get(1).get(i);
            for (int j=2;j<rows;j++){
                list.add(data.get(j).get(i));
            }
            StringColumn stringColumn = StringColumn.create(columnName, list);
            table.addColumns(stringColumn);
        }
        return table;
    }

    public List<String> getPlayersFromTable(Table table){
        List<String> players=new ArrayList<>();
        StringColumn stringColumn=table.stringColumn(0);
        for (String player:stringColumn) {
            players.add(player);
        }
        return players;
    }
    public List<String> getAllClubNames(){
        List<String> clubNames=new ArrayList<>();
        for (String clubName:clubsUrls.keySet()) {
            clubNames.add(clubName);
        }
        return clubNames;
    }
    public List<String> getImportantColumnNamesFromTable(Table table){
        // take 5th column na to 23rd column
        List<String> columnNames=table.columnNames();
        columnNames=columnNames.subList(4,23);
        return columnNames;
    }
}

