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
        Table table=playersStatsGetter.getPlayersStats("Arsenal");
        System.out.println(table);
        table=playersStatsGetter.getPlayersStats("Barcelona");
        System.out.println(table);

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
}

