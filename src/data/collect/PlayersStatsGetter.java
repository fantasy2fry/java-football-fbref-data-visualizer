
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
    private Map<String, String> clubsUrls=new TreeMap<>();

    public static void main(String[] args) {
        PlayersStatsGetter playersStatsGetter=new PlayersStatsGetter();
        Table table=playersStatsGetter.getPlayersStats("Napoli");
        System.out.println(table);
        table=playersStatsGetter.getPlayersStats("Real Madrid");
        System.out.println(table);
        System.out.println(playersStatsGetter.getPlayersFromTable(table));
        System.out.println(playersStatsGetter.getAllClubNames());
        System.out.println(playersStatsGetter.getImportantColumnNamesFromTable(table));
        System.out.println(playersStatsGetter.getImportantColumnIdsFromTable(table));

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
        clubsUrls.put("Arsenal - Sezon 23/24","https://fbref.com/en/squads/18bb7c10/2023-2024/all_comps/Arsenal-Stats-All-Competitions#all_stats_standard");
        clubsUrls.put("Arsenal - Sezon 22/23","https://fbref.com/en/squads/18bb7c10/2022-2023/all_comps/Arsenal-Stats-All-Competitions");
        clubsUrls.put("Arsenal - Sezon 21/22","https://fbref.com/en/squads/18bb7c10/2021-2022/all_comps/Arsenal-Stats-All-Competitions");
        clubsUrls.put("Barcelona - Sezon 23/24", "https://fbref.com/en/squads/206d90db/2023-2024/all_comps/Barcelona-Stats-All-Competitions");
        clubsUrls.put("Barcelona - Sezon 22/23", "https://fbref.com/en/squads/206d90db/2022-2023/all_comps/Barcelona-Stats-All-Competitions");
        clubsUrls.put("Barcelona - Sezon 21/22", "https://fbref.com/en/squads/206d90db/2021-2022/all_comps/Barcelona-Stats-All-Competitions");
        clubsUrls.put("Liverpool - Sezon 23/24","https://fbref.com/en/squads/822bd0ba/2023-2024/all_comps/Liverpool-Stats-All-Competitions");
        clubsUrls.put("Liverpool - Sezon 22/23","https://fbref.com/en/squads/822bd0ba/2022-2023/all_comps/Liverpool-Stats-All-Competitions");
        clubsUrls.put("Liverpool - Sezon 21/22","https://fbref.com/en/squads/822bd0ba/2021-2022/all_comps/Liverpool-Stats-All-Competitions");
        clubsUrls.put("Manchester City - Sezon 23/24","https://fbref.com/en/squads/b8fd03ef/2023-2024/all_comps/Manchester-City-Stats-All-Competitions");
        clubsUrls.put("Manchester City - Sezon 22/23","https://fbref.com/en/squads/b8fd03ef/2022-2023/all_comps/Manchester-City-Stats-All-Competitions");
        clubsUrls.put("Manchester City - Sezon 21/22","https://fbref.com/en/squads/b8fd03ef/2021-2022/all_comps/Manchester-City-Stats-All-Competitions");
        clubsUrls.put("Aston Villa - Sezon 23/24", "https://fbref.com/en/squads/8602292d/2023-2024/all_comps/Aston-Villa-Stats-All-Competitions");
        clubsUrls.put("Aston Villa - Sezon 22/23", "https://fbref.com/en/squads/8602292d/2022-2023/all_comps/Aston-Villa-Stats-All-Competitions");
        clubsUrls.put("Aston Villa - Sezon 21/22", "https://fbref.com/en/squads/8602292d/2021-2022/all_comps/Aston-Villa-Stats-All-Competitions");
        clubsUrls.put("Paris Saint-Germain - Sezon 23/24","https://fbref.com/en/squads/e2d8892c/2023-2024/all_comps/Paris-Saint-Germain-Stats-All-Competitions");
        clubsUrls.put("Paris Saint-Germain - Sezon 22/23","https://fbref.com/en/squads/e2d8892c/2022-2023/all_comps/Paris-Saint-Germain-Stats-All-Competitions");
        clubsUrls.put("Paris Saint-Germain - Sezon 21/22","https://fbref.com/en/squads/e2d8892c/2021-2022/all_comps/Paris-Saint-Germain-Stats-All-Competitions");
        clubsUrls.put("Bayern Munich - Sezon 23/24", "https://fbref.com/en/squads/054efa67/2023-2024/all_comps/Bayern-Munich-Stats-All-Competitions");
        clubsUrls.put("Bayern Munich - Sezon 22/23", "https://fbref.com/en/squads/054efa67/2022-2023/all_comps/Bayern-Munich-Stats-All-Competitions");
        clubsUrls.put("Bayern Munich - Sezon 21/22", "https://fbref.com/en/squads/054efa67/2021-2022/all_comps/Bayern-Munich-Stats-All-Competitions");
        clubsUrls.put("Bayer Leverkusen - Sezon 23/24","https://fbref.com/en/squads/c7a9f859/2023-2024/all_comps/Bayer-Leverkusen-Stats-All-Competitions");
        clubsUrls.put("Bayer Leverkusen - Sezon 22/23","https://fbref.com/en/squads/c7a9f859/2022-2023/all_comps/Bayer-Leverkusen-Stats-All-Competitions");
        clubsUrls.put("Bayer Leverkusen - Sezon 21/22","https://fbref.com/en/squads/c7a9f859/2021-2022/all_comps/Bayer-Leverkusen-Stats-All-Competitions");
        clubsUrls.put("Girona - Sezon 23/24","https://fbref.com/en/squads/9024a00a/2023-2024/all_comps/Girona-Stats-All-Competitions");
        clubsUrls.put("Girona - Sezon 22/23","https://fbref.com/en/squads/9024a00a/2022-2023/all_comps/Girona-Stats-All-Competitions");
        clubsUrls.put("Girona - Sezon 21/22","https://fbref.com/en/squads/9024a00a/2021-2022/all_comps/Girona-Stats-All-Competitions");
        clubsUrls.put("Real Madrid - Sezon 23/24","https://fbref.com/en/squads/53a2f082/2023-2024/all_comps/Real-Madrid-Stats-All-Competitions");
        clubsUrls.put("Real Madrid - Sezon 22/23","https://fbref.com/en/squads/53a2f082/2022-2023/all_comps/Real-Madrid-Stats-All-Competitions");
        clubsUrls.put("Real Madrid - Sezon 21/22","https://fbref.com/en/squads/53a2f082/2021-2022/all_comps/Real-Madrid-Stats-All-Competitions");
        clubsUrls.put("Manchester United - Sezon 23/24","https://fbref.com/en/squads/19538871/2023-2024/all_comps/Manchester-United-Stats-All-Competitions");
        clubsUrls.put("Manchester United - Sezon 22/23","https://fbref.com/en/squads/19538871/2022-2023/all_comps/Manchester-United-Stats-All-Competitions");
        clubsUrls.put("Manchester United - Sezon 21/22","https://fbref.com/en/squads/19538871/2021-2022/all_comps/Manchester-United-Stats-All-Competitions");
        clubsUrls.put("Nice - Sezon 23/24","https://fbref.com/en/squads/132ebc33/2023-2024/all_comps/Nice-Stats-All-Competitions");
        clubsUrls.put("Nice - Sezon 22/23","https://fbref.com/en/squads/132ebc33/2022-2023/all_comps/Nice-Stats-All-Competitions");
        clubsUrls.put("Nice - Sezon 21/22","https://fbref.com/en/squads/132ebc33/2021-2022/all_comps/Nice-Stats-All-Competitions");
        clubsUrls.put("Tottenham Hotspur - Sezon 23/24","https://fbref.com/en/squads/361ca564/2023-2024/all_comps/Tottenham-Hotspur-Stats-All-Competitions");
        clubsUrls.put("Tottenham Hotspur - Sezon 22/23","https://fbref.com/en/squads/361ca564/2022-2023/all_comps/Tottenham-Hotspur-Stats-All-Competitions");
        clubsUrls.put("Tottenham Hotspur - Sezon 21/22","https://fbref.com/en/squads/361ca564/2021-2022/all_comps/Tottenham-Hotspur-Stats-All-Competitions");
        clubsUrls.put("Chelsea - Sezon 23/24","https://fbref.com/en/squads/cff3d9bb/2023-2024/all_comps/Chelsea-Stats-All-Competitions");
        clubsUrls.put("Chelsea - Sezon 22/23","https://fbref.com/en/squads/cff3d9bb/2022-2023/all_comps/Chelsea-Stats-All-Competitions");
        clubsUrls.put("Chelsea - Sezon 21/22","https://fbref.com/en/squads/cff3d9bb/2021-2022/all_comps/Chelsea-Stats-All-Competitions");
        clubsUrls.put("Internazionale - Sezon 23/24","https://fbref.com/en/squads/d609edc0/2023-2024/all_comps/Internazionale-Stats-All-Competitions");
        clubsUrls.put("Internazionale - Sezon 22/23","https://fbref.com/en/squads/d609edc0/2022-2023/all_comps/Internazionale-Stats-All-Competitions");
        clubsUrls.put("Internazionale - Sezon 21/22","https://fbref.com/en/squads/d609edc0/2021-2022/all_comps/Internazionale-Stats-All-Competitions");
        clubsUrls.put("Juventus - Sezon 23/24","https://fbref.com/en/squads/e0652b02/2023-2024/all_comps/Juventus-Stats-All-Competitions");
        clubsUrls.put("Juventus - Sezon 22/23","https://fbref.com/en/squads/e0652b02/2022-2023/all_comps/Juventus-Stats-All-Competitions");
        clubsUrls.put("Juventus - Sezon 21/22","https://fbref.com/en/squads/e0652b02/2021-2022/all_comps/Juventus-Stats-All-Competitions");
        clubsUrls.put("Brighton - Sezon 23/24","https://fbref.com/en/squads/d07537b9/2023-2024/all_comps/Brighton-and-Hove-Albion-Stats-All-Competitions");
        clubsUrls.put("Brighton - Sezon 22/23","https://fbref.com/en/squads/d07537b9/2022-2023/all_comps/Brighton-and-Hove-Albion-Stats-All-Competitions");
        clubsUrls.put("Brighton - Sezon 21/22","https://fbref.com/en/squads/d07537b9/2021-2022/all_comps/Brighton-and-Hove-Albion-Stats-All-Competitions");
        clubsUrls.put("Napoli - Sezon 23/24","https://fbref.com/en/squads/d48ad4ff/2023-2024/all_comps/Napoli-Stats-All-Competitions");
        clubsUrls.put("Napoli - Sezon 22/23","https://fbref.com/en/squads/d48ad4ff/2022-2023/all_comps/Napoli-Stats-All-Competitions");
        clubsUrls.put("Napoli - Sezon 21/22","https://fbref.com/en/squads/d48ad4ff/2021-2022/all_comps/Napoli-Stats-All-Competitions");
        clubsUrls.put("AC Milan - Sezon 23/24","https://fbref.com/en/squads/dc56fe14/2023-2024/all_comps/Milan-Stats-All-Competitions");
        clubsUrls.put("AC Milan - Sezon 22/23","https://fbref.com/en/squads/dc56fe14/2022-2023/all_comps/Milan-Stats-All-Competitions");
        clubsUrls.put("AC Milan - Sezon 21/22","https://fbref.com/en/squads/dc56fe14/2021-2022/all_comps/Milan-Stats-All-Competitions");
        clubsUrls.put("Atletico Madrid - Sezon 23/24","https://fbref.com/en/squads/db3b9613/2023-2024/all_comps/Atletico-Madrid-Stats-All-Competitions");
        clubsUrls.put("Atletico Madrid - Sezon 22/23","https://fbref.com/en/squads/db3b9613/2022-2023/all_comps/Atletico-Madrid-Stats-All-Competitions");
        clubsUrls.put("Atletico Madrid - Sezon 21/22","https://fbref.com/en/squads/db3b9613/2021-2022/all_comps/Atletico-Madrid-Stats-All-Competitions");
        clubsUrls.put("Athletic Club - Sezon 23/24","https://fbref.com/en/squads/2b390eca/2023-2024/all_comps/Athletic-Club-Stats-All-Competitions");
        clubsUrls.put("Athletic Club - Sezon 22/23","https://fbref.com/en/squads/2b390eca/2022-2023/all_comps/Athletic-Club-Stats-All-Competitions");
        clubsUrls.put("Athletic Club - Sezon 21/22","https://fbref.com/en/squads/2b390eca/2021-2022/all_comps/Athletic-Club-Stats-All-Competitions");
        clubsUrls.put("Newcastle United - Sezon 23/24","https://fbref.com/en/squads/b2b47a98/2023-2024/all_comps/Newcastle-United-Stats-All-Competitions");
        clubsUrls.put("Newcastle United - Sezon 22/23","https://fbref.com/en/squads/b2b47a98/2022-2023/all_comps/Newcastle-United-Stats-All-Competitions");
        clubsUrls.put("Newcastle United - Sezon 21/22","https://fbref.com/en/squads/b2b47a98/2021-2022/all_comps/Newcastle-United-Stats-All-Competitions");
        clubsUrls.put("West Ham United - Sezon 23/24","https://fbref.com/en/squads/7c21e445/2023-2024/all_comps/West-Ham-United-Stats-All-Competitions");
        clubsUrls.put("West Ham United - Sezon 22/23","https://fbref.com/en/squads/7c21e445/2022-2023/all_comps/West-Ham-United-Stats-All-Competitions");
        clubsUrls.put("West Ham United - Sezon 21/22","https://fbref.com/en/squads/7c21e445/2021-2022/all_comps/West-Ham-United-Stats-All-Competitions");
        clubsUrls.put("Roma - Sezon 23/24","https://fbref.com/en/squads/cf74a709/2023-2024/all_comps/Roma-Stats-All-Competitions");
        clubsUrls.put("Roma - Sezon 22/23","https://fbref.com/en/squads/cf74a709/2022-2023/all_comps/Roma-Stats-All-Competitions");
        clubsUrls.put("Roma - Sezon 21/22","https://fbref.com/en/squads/cf74a709/2021-2022/all_comps/Roma-Stats-All-Competitions");
        clubsUrls.put("Monaco - Sezon 23/24","https://fbref.com/en/squads/fd6114db/2023-2024/all_comps/Monaco-Stats-All-Competitions");
        clubsUrls.put("Monaco - Sezon 22/23","https://fbref.com/en/squads/fd6114db/2022-2023/all_comps/Monaco-Stats-All-Competitions");
        clubsUrls.put("Monaco - Sezon 21/22","https://fbref.com/en/squads/fd6114db/2021-2022/all_comps/Monaco-Stats-All-Competitions");
        clubsUrls.put("Fiorentina - Sezon 23/24","https://fbref.com/en/squads/421387cf/2023-2024/all_comps/Fiorentina-Stats-All-Competitions");
        clubsUrls.put("Fiorentina - Sezon 22/23","https://fbref.com/en/squads/421387cf/2022-2023/all_comps/Fiorentina-Stats-All-Competitions");
        clubsUrls.put("Fiorentina - Sezon 21/22","https://fbref.com/en/squads/421387cf/2021-2022/all_comps/Fiorentina-Stats-All-Competitions");
        clubsUrls.put("Lazio - Sezon 23/24","https://fbref.com/en/squads/7213da33/2023-2024/all_comps/Lazio-Stats-All-Competitions");
        clubsUrls.put("Lazio - Sezon 22/23","https://fbref.com/en/squads/7213da33/2022-2023/all_comps/Lazio-Stats-All-Competitions");
        clubsUrls.put("Lazio - Sezon 21/22","https://fbref.com/en/squads/7213da33/2021-2022/all_comps/Lazio-Stats-All-Competitions");
        clubsUrls.put("Real Sociedad - Sezon 23/24","https://fbref.com/en/squads/e31d1cd9/2023-2024/all_comps/Real-Sociedad-Stats-All-Competitions");
        clubsUrls.put("Real Sociedad - Sezon 22/23","https://fbref.com/en/squads/e31d1cd9/2022-2023/all_comps/Real-Sociedad-Stats-All-Competitions");
        clubsUrls.put("Real Sociedad - Sezon 21/22","https://fbref.com/en/squads/e31d1cd9/2021-2022/all_comps/Real-Sociedad-Stats-All-Competitions");
        clubsUrls.put("Real Betis - Sezon 23/24","https://fbref.com/en/squads/fc536746/2023-2024/all_comps/Real-Betis-Stats-All-Competitions");
        clubsUrls.put("Real Betis - Sezon 22/23","https://fbref.com/en/squads/fc536746/2022-2023/all_comps/Real-Betis-Stats-All-Competitions");
        clubsUrls.put("Real Betis - Sezon 21/22","https://fbref.com/en/squads/fc536746/2021-2022/all_comps/Real-Betis-Stats-All-Competitions");
        clubsUrls.put("Sevilla - Sezon 23/24","https://fbref.com/en/squads/ad2be733/2023-2024/all_comps/Sevilla-Stats-All-Competitions");
        clubsUrls.put("Sevilla - Sezon 22/23","https://fbref.com/en/squads/ad2be733/2022-2023/all_comps/Sevilla-Stats-All-Competitions");
        clubsUrls.put("Sevilla - Sezon 21/22","https://fbref.com/en/squads/ad2be733/2021-2022/all_comps/Sevilla-Stats-All-Competitions");
        clubsUrls.put("Villarreal - Sezon 23/24","https://fbref.com/en/squads/2a8183b3/2023-2024/all_comps/Villarreal-Stats-All-Competitions");
        clubsUrls.put("Villarreal - Sezon 22/23","https://fbref.com/en/squads/2a8183b3/2022-2023/all_comps/Villarreal-Stats-All-Competitions");
        clubsUrls.put("Villarreal - Sezon 21/22","https://fbref.com/en/squads/2a8183b3/2021-2022/all_comps/Villarreal-Stats-All-Competitions");
        clubsUrls.put("RB Lipsk - Sezon 23/24","https://fbref.com/en/squads/acbb6a5b/2023-2024/all_comps/RB-Leipzig-Stats-All-Competitions");
        clubsUrls.put("RB Lipsk - Sezon 22/23","https://fbref.com/en/squads/acbb6a5b/2022-2023/all_comps/RB-Leipzig-Stats-All-Competitions");
        clubsUrls.put("RB Lipsk - Sezon 21/22","https://fbref.com/en/squads/acbb6a5b/2021-2022/all_comps/RB-Leipzig-Stats-All-Competitions");
        clubsUrls.put("Eintracht Frankfurt - Sezon 23/24","https://fbref.com/en/squads/f0ac8ee6/2023-2024/all_comps/Eintracht-Frankfurt-Stats-All-Competitions");
        clubsUrls.put("Eintracht Frankfurt - Sezon 22/23","https://fbref.com/en/squads/f0ac8ee6/2022-2023/all_comps/Eintracht-Frankfurt-Stats-All-Competitions");
        clubsUrls.put("Eintracht Frankfurt - Sezon 21/22","https://fbref.com/en/squads/f0ac8ee6/2021-2022/all_comps/Eintracht-Frankfurt-Stats-All-Competitions");
        clubsUrls.put("Borussia Dortmund - Sezon 23/24","https://fbref.com/en/squads/add600ae/2023-2024/all_comps/Dortmund-Stats-All-Competitions");
        clubsUrls.put("Borussia Dortmund - Sezon 22/23","https://fbref.com/en/squads/add600ae/2022-2023/all_comps/Dortmund-Stats-All-Competitions");
        clubsUrls.put("Borussia Dortmund - Sezon 21/22","https://fbref.com/en/squads/add600ae/2021-2022/all_comps/Dortmund-Stats-All-Competitions");
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
    public static List<String> getImportantColumnNamesFromTable(Table table){
        // take 5th column na to 23rd column
        List<String> columnNames=table.columnNames();
        columnNames=columnNames.subList(4,23);
        return columnNames;
    }
    public static List<Integer> getImportantColumnIdsFromTable(Table t){
        List<String> columnNames=getImportantColumnNamesFromTable(t);
        // foreach String from List Take characters until space
        List<Integer> columnIds=new ArrayList<>();
        for (String columnName:columnNames) {
            String id=columnName.substring(0, columnName.indexOf(" "));
            columnIds.add(Integer.parseInt(id));
        }
        //remove 6
        columnIds.remove(2);
        return columnIds;
    }
}

