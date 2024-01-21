
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
/**
 * This class is used to get player statistics from various football clubs.
 * It scrapes data from the website fbref.com and stores it in a Table object.
 */
public class PlayersStatsGetter {
    private Map<String, String> clubsUrls=new TreeMap<>();
    /**
     * Main method used for testing purposes.
     * @param args
     */
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
    /**
     * Constructor for the PlayersStatsGetter class.
     * It initializes the clubsUrls map with the names of the clubs and their respective urls.
     */
    public PlayersStatsGetter(){
        clubsUrls.put("Arsenal - Season 23/24","https://fbref.com/en/squads/18bb7c10/2023-2024/all_comps/Arsenal-Stats-All-Competitions#all_stats_standard");
        clubsUrls.put("Arsenal - Season 22/23","https://fbref.com/en/squads/18bb7c10/2022-2023/all_comps/Arsenal-Stats-All-Competitions");
        clubsUrls.put("Arsenal - Season 21/22","https://fbref.com/en/squads/18bb7c10/2021-2022/all_comps/Arsenal-Stats-All-Competitions");
        clubsUrls.put("Barcelona - Season 23/24", "https://fbref.com/en/squads/206d90db/2023-2024/all_comps/Barcelona-Stats-All-Competitions");
        clubsUrls.put("Barcelona - Season 22/23", "https://fbref.com/en/squads/206d90db/2022-2023/all_comps/Barcelona-Stats-All-Competitions");
        clubsUrls.put("Barcelona - Season 21/22", "https://fbref.com/en/squads/206d90db/2021-2022/all_comps/Barcelona-Stats-All-Competitions");
        clubsUrls.put("Liverpool - Season 23/24","https://fbref.com/en/squads/822bd0ba/2023-2024/all_comps/Liverpool-Stats-All-Competitions");
        clubsUrls.put("Liverpool - Season 22/23","https://fbref.com/en/squads/822bd0ba/2022-2023/all_comps/Liverpool-Stats-All-Competitions");
        clubsUrls.put("Liverpool - Season 21/22","https://fbref.com/en/squads/822bd0ba/2021-2022/all_comps/Liverpool-Stats-All-Competitions");
        clubsUrls.put("Manchester City - Season 23/24","https://fbref.com/en/squads/b8fd03ef/2023-2024/all_comps/Manchester-City-Stats-All-Competitions");
        clubsUrls.put("Manchester City - Season 22/23","https://fbref.com/en/squads/b8fd03ef/2022-2023/all_comps/Manchester-City-Stats-All-Competitions");
        clubsUrls.put("Manchester City - Season 21/22","https://fbref.com/en/squads/b8fd03ef/2021-2022/all_comps/Manchester-City-Stats-All-Competitions");
        clubsUrls.put("Aston Villa - Season 23/24", "https://fbref.com/en/squads/8602292d/2023-2024/all_comps/Aston-Villa-Stats-All-Competitions");
        clubsUrls.put("Aston Villa - Season 22/23", "https://fbref.com/en/squads/8602292d/2022-2023/all_comps/Aston-Villa-Stats-All-Competitions");
        clubsUrls.put("Aston Villa - Season 21/22", "https://fbref.com/en/squads/8602292d/2021-2022/all_comps/Aston-Villa-Stats-All-Competitions");
        clubsUrls.put("Paris Saint-Germain - Season 23/24","https://fbref.com/en/squads/e2d8892c/2023-2024/all_comps/Paris-Saint-Germain-Stats-All-Competitions");
        clubsUrls.put("Paris Saint-Germain - Season 22/23","https://fbref.com/en/squads/e2d8892c/2022-2023/all_comps/Paris-Saint-Germain-Stats-All-Competitions");
        clubsUrls.put("Paris Saint-Germain - Season 21/22","https://fbref.com/en/squads/e2d8892c/2021-2022/all_comps/Paris-Saint-Germain-Stats-All-Competitions");
        clubsUrls.put("Bayern Munich - Season 23/24", "https://fbref.com/en/squads/054efa67/2023-2024/all_comps/Bayern-Munich-Stats-All-Competitions");
        clubsUrls.put("Bayern Munich - Season 22/23", "https://fbref.com/en/squads/054efa67/2022-2023/all_comps/Bayern-Munich-Stats-All-Competitions");
        clubsUrls.put("Bayern Munich - Season 21/22", "https://fbref.com/en/squads/054efa67/2021-2022/all_comps/Bayern-Munich-Stats-All-Competitions");
        clubsUrls.put("Bayer Leverkusen - Season 23/24","https://fbref.com/en/squads/c7a9f859/2023-2024/all_comps/Bayer-Leverkusen-Stats-All-Competitions");
        clubsUrls.put("Bayer Leverkusen - Season 22/23","https://fbref.com/en/squads/c7a9f859/2022-2023/all_comps/Bayer-Leverkusen-Stats-All-Competitions");
        clubsUrls.put("Bayer Leverkusen - Season 21/22","https://fbref.com/en/squads/c7a9f859/2021-2022/all_comps/Bayer-Leverkusen-Stats-All-Competitions");
        clubsUrls.put("Girona - Season 23/24","https://fbref.com/en/squads/9024a00a/2023-2024/all_comps/Girona-Stats-All-Competitions");
        clubsUrls.put("Girona - Season 22/23","https://fbref.com/en/squads/9024a00a/2022-2023/all_comps/Girona-Stats-All-Competitions");
        clubsUrls.put("Girona - Season 21/22","https://fbref.com/en/squads/9024a00a/2021-2022/all_comps/Girona-Stats-All-Competitions");
        clubsUrls.put("Real Madrid - Season 23/24","https://fbref.com/en/squads/53a2f082/2023-2024/all_comps/Real-Madrid-Stats-All-Competitions");
        clubsUrls.put("Real Madrid - Season 22/23","https://fbref.com/en/squads/53a2f082/2022-2023/all_comps/Real-Madrid-Stats-All-Competitions");
        clubsUrls.put("Real Madrid - Season 21/22","https://fbref.com/en/squads/53a2f082/2021-2022/all_comps/Real-Madrid-Stats-All-Competitions");
        clubsUrls.put("Manchester United - Season 23/24","https://fbref.com/en/squads/19538871/2023-2024/all_comps/Manchester-United-Stats-All-Competitions");
        clubsUrls.put("Manchester United - Season 22/23","https://fbref.com/en/squads/19538871/2022-2023/all_comps/Manchester-United-Stats-All-Competitions");
        clubsUrls.put("Manchester United - Season 21/22","https://fbref.com/en/squads/19538871/2021-2022/all_comps/Manchester-United-Stats-All-Competitions");
        clubsUrls.put("Nice - Season 23/24","https://fbref.com/en/squads/132ebc33/2023-2024/all_comps/Nice-Stats-All-Competitions");
        clubsUrls.put("Nice - Season 22/23","https://fbref.com/en/squads/132ebc33/2022-2023/all_comps/Nice-Stats-All-Competitions");
        clubsUrls.put("Nice - Season 21/22","https://fbref.com/en/squads/132ebc33/2021-2022/all_comps/Nice-Stats-All-Competitions");
        clubsUrls.put("Tottenham Hotspur - Season 23/24","https://fbref.com/en/squads/361ca564/2023-2024/all_comps/Tottenham-Hotspur-Stats-All-Competitions");
        clubsUrls.put("Tottenham Hotspur - Season 22/23","https://fbref.com/en/squads/361ca564/2022-2023/all_comps/Tottenham-Hotspur-Stats-All-Competitions");
        clubsUrls.put("Tottenham Hotspur - Season 21/22","https://fbref.com/en/squads/361ca564/2021-2022/all_comps/Tottenham-Hotspur-Stats-All-Competitions");
        clubsUrls.put("Chelsea - Season 23/24","https://fbref.com/en/squads/cff3d9bb/2023-2024/all_comps/Chelsea-Stats-All-Competitions");
        clubsUrls.put("Chelsea - Season 22/23","https://fbref.com/en/squads/cff3d9bb/2022-2023/all_comps/Chelsea-Stats-All-Competitions");
        clubsUrls.put("Chelsea - Season 21/22","https://fbref.com/en/squads/cff3d9bb/2021-2022/all_comps/Chelsea-Stats-All-Competitions");
        clubsUrls.put("Internazionale - Season 23/24","https://fbref.com/en/squads/d609edc0/2023-2024/all_comps/Internazionale-Stats-All-Competitions");
        clubsUrls.put("Internazionale - Season 22/23","https://fbref.com/en/squads/d609edc0/2022-2023/all_comps/Internazionale-Stats-All-Competitions");
        clubsUrls.put("Internazionale - Season 21/22","https://fbref.com/en/squads/d609edc0/2021-2022/all_comps/Internazionale-Stats-All-Competitions");
        clubsUrls.put("Juventus - Season 23/24","https://fbref.com/en/squads/e0652b02/2023-2024/all_comps/Juventus-Stats-All-Competitions");
        clubsUrls.put("Juventus - Season 22/23","https://fbref.com/en/squads/e0652b02/2022-2023/all_comps/Juventus-Stats-All-Competitions");
        clubsUrls.put("Juventus - Season 21/22","https://fbref.com/en/squads/e0652b02/2021-2022/all_comps/Juventus-Stats-All-Competitions");
        clubsUrls.put("Brighton - Season 23/24","https://fbref.com/en/squads/d07537b9/2023-2024/all_comps/Brighton-and-Hove-Albion-Stats-All-Competitions");
        clubsUrls.put("Brighton - Season 22/23","https://fbref.com/en/squads/d07537b9/2022-2023/all_comps/Brighton-and-Hove-Albion-Stats-All-Competitions");
        clubsUrls.put("Brighton - Season 21/22","https://fbref.com/en/squads/d07537b9/2021-2022/all_comps/Brighton-and-Hove-Albion-Stats-All-Competitions");
        clubsUrls.put("Napoli - Season 23/24","https://fbref.com/en/squads/d48ad4ff/2023-2024/all_comps/Napoli-Stats-All-Competitions");
        clubsUrls.put("Napoli - Season 22/23","https://fbref.com/en/squads/d48ad4ff/2022-2023/all_comps/Napoli-Stats-All-Competitions");
        clubsUrls.put("Napoli - Season 21/22","https://fbref.com/en/squads/d48ad4ff/2021-2022/all_comps/Napoli-Stats-All-Competitions");
        clubsUrls.put("AC Milan - Season 23/24","https://fbref.com/en/squads/dc56fe14/2023-2024/all_comps/Milan-Stats-All-Competitions");
        clubsUrls.put("AC Milan - Season 22/23","https://fbref.com/en/squads/dc56fe14/2022-2023/all_comps/Milan-Stats-All-Competitions");
        clubsUrls.put("AC Milan - Season 21/22","https://fbref.com/en/squads/dc56fe14/2021-2022/all_comps/Milan-Stats-All-Competitions");
        clubsUrls.put("Atletico Madrid - Season 23/24","https://fbref.com/en/squads/db3b9613/2023-2024/all_comps/Atletico-Madrid-Stats-All-Competitions");
        clubsUrls.put("Atletico Madrid - Season 22/23","https://fbref.com/en/squads/db3b9613/2022-2023/all_comps/Atletico-Madrid-Stats-All-Competitions");
        clubsUrls.put("Atletico Madrid - Season 21/22","https://fbref.com/en/squads/db3b9613/2021-2022/all_comps/Atletico-Madrid-Stats-All-Competitions");
        clubsUrls.put("Athletic Club - Season 23/24","https://fbref.com/en/squads/2b390eca/2023-2024/all_comps/Athletic-Club-Stats-All-Competitions");
        clubsUrls.put("Athletic Club - Season 22/23","https://fbref.com/en/squads/2b390eca/2022-2023/all_comps/Athletic-Club-Stats-All-Competitions");
        clubsUrls.put("Athletic Club - Season 21/22","https://fbref.com/en/squads/2b390eca/2021-2022/all_comps/Athletic-Club-Stats-All-Competitions");
        clubsUrls.put("Newcastle United - Season 23/24","https://fbref.com/en/squads/b2b47a98/2023-2024/all_comps/Newcastle-United-Stats-All-Competitions");
        clubsUrls.put("Newcastle United - Season 22/23","https://fbref.com/en/squads/b2b47a98/2022-2023/all_comps/Newcastle-United-Stats-All-Competitions");
        clubsUrls.put("Newcastle United - Season 21/22","https://fbref.com/en/squads/b2b47a98/2021-2022/all_comps/Newcastle-United-Stats-All-Competitions");
        clubsUrls.put("West Ham United - Season 23/24","https://fbref.com/en/squads/7c21e445/2023-2024/all_comps/West-Ham-United-Stats-All-Competitions");
        clubsUrls.put("West Ham United - Season 22/23","https://fbref.com/en/squads/7c21e445/2022-2023/all_comps/West-Ham-United-Stats-All-Competitions");
        clubsUrls.put("West Ham United - Season 21/22","https://fbref.com/en/squads/7c21e445/2021-2022/all_comps/West-Ham-United-Stats-All-Competitions");
        clubsUrls.put("Roma - Season 23/24","https://fbref.com/en/squads/cf74a709/2023-2024/all_comps/Roma-Stats-All-Competitions");
        clubsUrls.put("Roma - Season 22/23","https://fbref.com/en/squads/cf74a709/2022-2023/all_comps/Roma-Stats-All-Competitions");
        clubsUrls.put("Roma - Season 21/22","https://fbref.com/en/squads/cf74a709/2021-2022/all_comps/Roma-Stats-All-Competitions");
        clubsUrls.put("Monaco - Season 23/24","https://fbref.com/en/squads/fd6114db/2023-2024/all_comps/Monaco-Stats-All-Competitions");
        clubsUrls.put("Monaco - Season 22/23","https://fbref.com/en/squads/fd6114db/2022-2023/all_comps/Monaco-Stats-All-Competitions");
        clubsUrls.put("Monaco - Season 21/22","https://fbref.com/en/squads/fd6114db/2021-2022/all_comps/Monaco-Stats-All-Competitions");
        clubsUrls.put("Fiorentina - Season 23/24","https://fbref.com/en/squads/421387cf/2023-2024/all_comps/Fiorentina-Stats-All-Competitions");
        clubsUrls.put("Fiorentina - Season 22/23","https://fbref.com/en/squads/421387cf/2022-2023/all_comps/Fiorentina-Stats-All-Competitions");
        clubsUrls.put("Fiorentina - Season 21/22","https://fbref.com/en/squads/421387cf/2021-2022/all_comps/Fiorentina-Stats-All-Competitions");
        clubsUrls.put("Lazio - Season 23/24","https://fbref.com/en/squads/7213da33/2023-2024/all_comps/Lazio-Stats-All-Competitions");
        clubsUrls.put("Lazio - Season 22/23","https://fbref.com/en/squads/7213da33/2022-2023/all_comps/Lazio-Stats-All-Competitions");
        clubsUrls.put("Lazio - Season 21/22","https://fbref.com/en/squads/7213da33/2021-2022/all_comps/Lazio-Stats-All-Competitions");
        clubsUrls.put("Real Sociedad - Season 23/24","https://fbref.com/en/squads/e31d1cd9/2023-2024/all_comps/Real-Sociedad-Stats-All-Competitions");
        clubsUrls.put("Real Sociedad - Season 22/23","https://fbref.com/en/squads/e31d1cd9/2022-2023/all_comps/Real-Sociedad-Stats-All-Competitions");
        clubsUrls.put("Real Sociedad - Season 21/22","https://fbref.com/en/squads/e31d1cd9/2021-2022/all_comps/Real-Sociedad-Stats-All-Competitions");
        clubsUrls.put("Real Betis - Season 23/24","https://fbref.com/en/squads/fc536746/2023-2024/all_comps/Real-Betis-Stats-All-Competitions");
        clubsUrls.put("Real Betis - Season 22/23","https://fbref.com/en/squads/fc536746/2022-2023/all_comps/Real-Betis-Stats-All-Competitions");
        clubsUrls.put("Real Betis - Season 21/22","https://fbref.com/en/squads/fc536746/2021-2022/all_comps/Real-Betis-Stats-All-Competitions");
        clubsUrls.put("Sevilla - Season 23/24","https://fbref.com/en/squads/ad2be733/2023-2024/all_comps/Sevilla-Stats-All-Competitions");
        clubsUrls.put("Sevilla - Season 22/23","https://fbref.com/en/squads/ad2be733/2022-2023/all_comps/Sevilla-Stats-All-Competitions");
        clubsUrls.put("Sevilla - Season 21/22","https://fbref.com/en/squads/ad2be733/2021-2022/all_comps/Sevilla-Stats-All-Competitions");
        clubsUrls.put("Villarreal - Season 23/24","https://fbref.com/en/squads/2a8183b3/2023-2024/all_comps/Villarreal-Stats-All-Competitions");
        clubsUrls.put("Villarreal - Season 22/23","https://fbref.com/en/squads/2a8183b3/2022-2023/all_comps/Villarreal-Stats-All-Competitions");
        clubsUrls.put("Villarreal - Season 21/22","https://fbref.com/en/squads/2a8183b3/2021-2022/all_comps/Villarreal-Stats-All-Competitions");
        clubsUrls.put("RB Lipsk - Season 23/24","https://fbref.com/en/squads/acbb6a5b/2023-2024/all_comps/RB-Leipzig-Stats-All-Competitions");
        clubsUrls.put("RB Lipsk - Season 22/23","https://fbref.com/en/squads/acbb6a5b/2022-2023/all_comps/RB-Leipzig-Stats-All-Competitions");
        clubsUrls.put("RB Lipsk - Season 21/22","https://fbref.com/en/squads/acbb6a5b/2021-2022/all_comps/RB-Leipzig-Stats-All-Competitions");
        clubsUrls.put("Eintracht Frankfurt - Season 23/24","https://fbref.com/en/squads/f0ac8ee6/2023-2024/all_comps/Eintracht-Frankfurt-Stats-All-Competitions");
        clubsUrls.put("Eintracht Frankfurt - Season 22/23","https://fbref.com/en/squads/f0ac8ee6/2022-2023/all_comps/Eintracht-Frankfurt-Stats-All-Competitions");
        clubsUrls.put("Eintracht Frankfurt - Season 21/22","https://fbref.com/en/squads/f0ac8ee6/2021-2022/all_comps/Eintracht-Frankfurt-Stats-All-Competitions");
        clubsUrls.put("Borussia Dortmund - Season 23/24","https://fbref.com/en/squads/add600ae/2023-2024/all_comps/Dortmund-Stats-All-Competitions");
        clubsUrls.put("Borussia Dortmund - Season 22/23","https://fbref.com/en/squads/add600ae/2022-2023/all_comps/Dortmund-Stats-All-Competitions");
        clubsUrls.put("Borussia Dortmund - Season 21/22","https://fbref.com/en/squads/add600ae/2021-2022/all_comps/Dortmund-Stats-All-Competitions");
    }

    /**
     * This method returns a Table object containing the players statistics for a given club.
     * @param clubName The name of the club for which the statistics are returned.
     * @return Table object containing the players statistics for a given club.
     */
    public Table getPlayersStats(String clubName){
        String websiteUrl=clubsUrls.get(clubName);
        List<List<String>> tableData = playersStats(websiteUrl);
        Table table=createTableFromListByRows(tableData, clubName);
        return table;
    }

    /**
     * This method returns a List of Lists of Strings containing the players statistics for a given club.
     * This is part of changing the data into something more useful.
     * @param table is object containing information about players scraped from fbref.com
     * @return List of Lists of Strings containing players statistics for a given club.
     */
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

    /**
     * This function takes url of websites chooses tables from html of website
     * and then chooses first table and transforms it to List of Lists of Strings.
     * @param websiteUrl is url of website from which we want to take data.
     * @return List of Lists of Strings containing players statistics for a given club prepaired to create Table object.
     */
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

    /**
     * This function takes url of websites chooses tables from html of website
     * and then takes chosen table and transforms it to List of Lists of Strings. Currently not used in the project.
     * @param websiteUrl is url of website from which we want to take data.
     * @param whichTable is number of table we want to take from website.
     * @return List of Lists of Strings containing players statistics for a given club prepaired to create Table object.
     */
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

    /**
     * This function takes List of Lists of Strings and creates Table object from it by columns.
     * It propably omits first row of data.
     * Currently not used in the project.
     * @param data is List of Lists of Strings containing players statistics for a given club
     *             prepaired to create Table object.
     * @param tableName is name of table we want to create.
     * @return Table object containing players statistics for a given club ready to use, transform, etc.
     */
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

    /**
     * This function takes List of Lists of Strings and creates Table object from it by rows.
     * It propably omits first column of data.
     * @param data is List of Lists of Strings containing players statistics for a given club
     * @param tableName is name of table we want to create.
     * @return Table object containing players statistics for a given club ready to use, transform, etc.
     */
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

    /**
     * This function takes Table object and returns List of Strings containing names of players from this table.
     * (In practice it takes all strings from StringColumn with index 0 from the table)
     * @param table is Table object containing players statistics for a given club.
     * @return  List of Strings containing names of players from this table.
     */
    public List<String> getPlayersFromTable(Table table){
        List<String> players=new ArrayList<>();
        StringColumn stringColumn=table.stringColumn(0);
        for (String player:stringColumn) {
            players.add(player);
        }
        return players;
    }

    /**
     * This function returns List of Strings containing names of all clubs for which we have data.
     * In practice, it takes all keys from clubsUrls TreeMap and returns them as List of Strings.
     * @return List of Strings containing names of all clubs for which we have data.
     */
    public List<String> getAllClubNames(){
        List<String> clubNames=new ArrayList<>();
        for (String clubName:clubsUrls.keySet()) {
            clubNames.add(clubName);
        }
        return clubNames;
    }

    /**
     * This function returns Names of columns which are important for us, which means that they contain
     * statistics about players, which we want to use in our project and visualizations.
     * @param table is Table object containing players statistics for a given club.
     * @return List of Strings containing names of columns which are important for us.
     */
    public static List<String> getImportantColumnNamesFromTable(Table table){
        // take 5th column na to 23rd column
        List<String> columnNames=table.columnNames();
        columnNames=columnNames.subList(4,23);
        return columnNames;
    }

    /**
     * This function returns Ids of columns which are important for us, which means that they contain
     * statistics about players, which we want to use in our project and visualizations.
     * @param t is Table object containing players statistics for a given club.
     * @return List of Integers containing Ids of columns which are important for us.
     */
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

