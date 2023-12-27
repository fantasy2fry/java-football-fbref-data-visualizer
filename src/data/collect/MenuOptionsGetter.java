package data.collect;

import tech.tablesaw.api.Table;

public class MenuOptionsGetter {
    private static final String[] leagueOptions = {"Premier League", "La Liga", "Bundesliga", "Serie A", "Ligue 1"};

    public static void main(String[] args) {
        Table table=Table.create("Menu Options");
        System.out.println(table);
    }
}
