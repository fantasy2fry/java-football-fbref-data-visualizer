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

        try {
            Document doc = Jsoup.connect(websiteUrl).get();
            Elements tables = doc.select("table");

            for (Element table : tables) {
                List<List<String>> tableData = extractTableData(table);
                // Use the extracted tableData as needed
                System.out.println("Extracted Table Data:\n" + tableData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}

