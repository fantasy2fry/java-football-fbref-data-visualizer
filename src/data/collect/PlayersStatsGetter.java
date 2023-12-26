package data.collect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayersStatsGetter {

    public static void main(String[] args) {
        String websiteUrl = "https://fbref.com/en/squads/18bb7c10/2023-2024/all_comps/Arsenal-Stats-All-Competitions#all_stats_standard"; // Replace with the website URL you want to scrape

        System.out.println(playersStats(websiteUrl));
        System.out.println(playersStats("https://fbref.com/en/comps/9/stats/Premier-League-Stats", 0));
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
}

