package data.visualization;

import data.collect.PlayersStatsGetter;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.numbers.NumberColumnFormatter;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.VerticalBarPlot;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Marker;
import tech.tablesaw.plotly.traces.BarTrace;
import tech.tablesaw.plotly.traces.PieTrace;
import tech.tablesaw.plotly.traces.Trace;
import tech.tablesaw.selection.Selection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.min;
/**
 * class which purpose is to provide different types of plots that help in persuading someone into believing some nonsense
 * the class operates on tables from tablesaw library
 */
public class TwoPlayersPlots {
    /**
     * a list of nice names for columns
     */
    public static final ArrayList<String> columnNames = new ArrayList<String>(List.of("n","n", "n", "n","Matches Played","Matches Played From Start", "n", "Full Match Equivalent", "Goals", "Assists", "Goals + Assists", "Non-Penalty Goals", "Penalty Kicks Made", "Penalty Kicks Attempted", "Yellow Cards", "Red Cards", "Expected Goals", "Non-Penalty Expected Goals", "Expected Assisted Goals", "Non-Penalty Expected Goals + Assisted Goals", "Progressive Carries", "Progressive Passes", "Progressive Passes Received"));
    /**
     * Main method used for testing purposes.
     * @param args
     */

    public static void main(String[] args) {
        data.collect.PlayersStatsGetter getter = new data.collect.PlayersStatsGetter();
        Table table = getter.getPlayersStats("Barcelona - Sezon 21/22");
        Table table2 = getter.getPlayersStats("Bayern Munich - Sezon 21/22");
        Plot.show(twoTeamsPlot(table,table2));




        //System.out.println(ready2Plot(table, 7));
        //table=table.where(table.stringColumn(0).isEqualTo("Pedri"));
        //Plot.show(slupkowyPodwojny(table,table2,7,"Barcelona", "Arsenal"));
        //System.out.println(table);
        //table=table.append(table2);
        //System.out.println(table);
       // System.out.println(PlayersStatsGetter.getImportantColumnIdsFromTable(table));
        //System.out.println(twoPlayersPlot(table,table2,"Pedri","Bukayo Saka"));
        //Plot.show(twoPlayersPlot(table,table2,"Pedri","Bukayo Saka"));


    }
    /**
     * as columns of frames imported by other classes come as columns of strings we need them converted to a numeric format to later be plotted
     * @param stringColumn a StringColumn to be converted to a DoubleColumn
     * @return DoubleColumn
     */
    public static DoubleColumn str2dbl(StringColumn stringColumn) {
        String name = stringColumn.name();
        List<String> listas = stringColumn.asList();
        List<Double> listad = new ArrayList<>();
        for (String str : listas)
        {
            if(str=="")
            {
                listad.add(0.0);
            }
            else
            {
                String str2 = str.replace(",", ".");
                int asc = (int) str2.charAt(0);
                if (asc > 47 && asc < 58) {
                    listad.add(Double.valueOf(str2));
                }
            }
        }
        DoubleColumn dopel = DoubleColumn.create(name, listad);
        return dopel;

    }
    /**
     * Function that allows us to choose a column that will later be plotted, as well as formatting the table accordingly to plotting needs
     * @param table the table on which we operate
     * @param id id of column to be plotted
     * @return a table that can be successfully used in plotting functions below
     */
    public static Table ready2Plot(Table table, int id)
    {
        Column<?> pilk = table.column(0);
        Table ret = Table.create(pilk);
        Column<?> dana = table.column(id);
        String name=dana.name();
        DoubleColumn danad = str2dbl((StringColumn) dana);
        ret.addColumns(danad);
        ret=ret.where((ret.numberColumn(name).isNotEqualTo(0)));
        return ret;
    }
    /**
     * Function returning vertical bar plot of a given statistic from a given table
     * @param table data
     * @param id index of statistic to be plotted
     * @return object of type Figure with desired plot
     */
    public static Figure slupkowyPionowy(Table table, int id)
    {
        table=callMeMoron(table);
        Table doRoboty = ready2Plot(table,id);
        System.out.println(doRoboty);
        String[] namy=doRoboty.columnNames().toArray(new String[0]);
        String nazwadan=namy[1];
        return VerticalBarPlot.create(
                columnNames.get(id)+" by player",
                doRoboty,
                "0 Player",
                Layout.BarMode.GROUP,
                nazwadan);

    }
    /**
     * Function returning horizontal bar plot of a given statistic from a given table
     * @param table data
     * @param id index of statistic to be plotted
     * @return object of type Figure with desired plot
     */
    public static Figure slupkowyPoziomy(Table table, int id)
    {
        table=callMeMoron(table);
        Table doRoboty = ready2Plot(table,id);
        String[] namy=doRoboty.columnNames().toArray(new String[0]);
        String nazwadan=namy[1];
        return HorizontalBarPlot.create(
                columnNames.get(id)+" by player",
                doRoboty,
                "0 Player",
                Layout.BarMode.STACK,
                nazwadan

        );
    }
    /**
     * Function returning pie chart of a given statistic from a given table, it groups all the players but top5 together
     * @param table data
     * @param id index of statistic to be plotted
     * @return object of type Figure with desired plot
     */
    public static Figure kolowy(Table table, int id)
    {
        Table doRoboty = ready2Plot(table,id);
        String[] namy=doRoboty.columnNames().toArray(new String[0]);
        String nazwadan=namy[1];
        Table top5 = doRoboty.sortDescendingOn(nazwadan).where(Selection.withRange(0,5));
        DoubleColumn cos=(DoubleColumn) (doRoboty.sortDescendingOn(nazwadan).where(Selection.withRange(6,doRoboty.rowCount())).column(1));
        double sumaPoz=cos.sum();
        StringColumn pla=StringColumn.create("0 Players", "other");
        DoubleColumn razem=DoubleColumn.create(nazwadan,sumaPoz);
        Table rzad = Table.create(pla,razem);
        top5.addRow(0, rzad);
        System.out.println(top5);
        table=callMeMoron(table);

        PieTrace trace =
                PieTrace.builder(top5.categoricalColumn(0), top5.numberColumn(nazwadan)).build();
        Layout layout = Layout.builder().title(columnNames.get(id)+" by player").build();
        return new Figure(layout,trace);
    }

    /**
     * it is junk it isn't used
     * @param table1
     * @param table2
     * @param id
     * @param klub1
     * @param klub2
     * @return
     */
    public static Figure slupkowyPodwojny(Table table1, Table table2, int id, String klub1, String klub2) {
        Table dr1 = ready2Plot(table1, id);
        Table dr2 = ready2Plot(table2, id);
        String[] namy = dr1.columnNames().toArray(new String[0]);
        String nazwadan = namy[1];
        int rzedy = min(dr1.rowCount(), dr2.rowCount());
        dr1 = dr1.sortDescendingOn(nazwadan).where(Selection.withRange(0, rzedy));
        dr2 = dr2.sortDescendingOn(nazwadan).where(Selection.withRange(0, rzedy));
        StringColumn kol1 = kolumienka("club name", klub1, rzedy);
        StringColumn kol2 = kolumienka("club name", klub2, rzedy);
        dr1.addColumns(kol1);
        dr2.addColumns(kol2);
        for (int i = 0; i < rzedy; i++) {
            dr1.addRow(i, dr2);
        }
        System.out.println(dr1);
        BarTrace[] zebyNieBylo = new BarTrace[dr1.rowCount()];
        String[] playerNames = (String[]) dr1.column(0).asList().toArray();
        Double[] stat = (Double[]) dr1.column(1).asList().toArray();
        String[] club = (String[]) dr1.column(2).asList().toArray();
        BarTrace trace = BarTrace.builder(dr1.categoricalColumn(0),dr2.numberColumn(1)).build();
        Layout layout = Layout.builder().title(columnNames.get(id)+" by player").build();

        return new Figure(layout,(Trace)trace);
    }

    /**
     * and so is it
     * @param nazwa
     * @param parametr
     * @param licz
     * @return
     */
    public static StringColumn kolumienka(String nazwa, String parametr, int licz)
    {
        List<String> chlop=new ArrayList<>();
        for (int i = 0; i <licz ; i++)
        {
            chlop.add(parametr);
        }
        return StringColumn.create(nazwa,chlop);
    }
    /**
     * another version of a function above named the same, it allows for choosing multiple statistics
     * @param t table on which shenanigans are conducted
     * @param ids a List of ids of parameters to be selected into output table
     * @return output table
     */
    public static Table ready2Plot(Table t,List<Integer> ids){
        Column<?> names=t.column(0);
        Table ret=Table.create(names);
        for(int id:ids){
            StringColumn col= (StringColumn) t.column(id);
            //take name of column
            String name=col.name();
            // take Strings to from column to List
            List<String> list=col.asList();
            // convert this list to list of doubles
            List<Double> listDoubles=new ArrayList<>();
            for(String str:list){
                if(str==""){
                    listDoubles.add(0.0);
                }
                else{
                    int asc=(int)str.charAt(0);
                    if(asc>47 && asc<58){
                        listDoubles.add(Double.valueOf(str));
                    }
                }
            }
            // create new column with doubles
            DoubleColumn dcol=DoubleColumn.create(name,listDoubles);
            ret.addColumns(dcol);
        }
        return ret;
    }

    /**
     * function used to rename columns
     * @param table object on which we operate
     * @return table with different column names
     */
    public static Table callMeMoron(Table table)
    {
        for(int i=4; i<23; i++)
        {
            if(i!=6)
            {
                table.column(i).setName(columnNames.get(i));
            }
        }
        return table;
    }

    /**
     * Function for transposing tables
     * @param t input table
     * @return output table t^t
     */
    public static Table twoPlayersPlotTranspose(Table t){
        //take column names
        List<String> names=t.columnNames();
        names.remove(0);
        // take first column to List<String>
        List<String> players=t.stringColumn(0).asList();
        // remove first column from t
        t=t.removeColumns(0);
        // transpose table
        t=t.transpose();
        // change column names to players names
        t.column(0).setName(players.get(0));
        t.column(1).setName(players.get(1));
        // create String Column with names
        StringColumn namesColumn=StringColumn.create("Attr",names);
        // add this column to table
        t.addColumns(namesColumn);
        return t;
    }

    /**
     * Function creating bar plot for some statistics in given tables for given players
     * @param table1 table of team of player1
     * @param table2 table of team of player2
     * @param player1 a String name, of player1
     * @param player2 a String name, of player1
     * @return he who in the moment of the greatest despair achieves the bravery to run this function meets with the indescribable beauty of its output
     */

    public static Figure twoPlayersPlot(Table table1, Table table2, String player1, String player2){
        // from first table take row with player1 in first column
        table1=table1.where(table1.stringColumn(0).isEqualTo(player1));
        // from second table take row with player2 in first column
        table2=table2.where(table2.stringColumn(0).isEqualTo(player2));
        // join two tables
        Table table=table1.append(table2);
        //PlayersStatsGetter.getImportantColumnIdsFromTable(table)
        table=callMeMoron(table);
        table=ready2Plot(table, new ArrayList<Integer>(List.of(4,5,7,8,9,10,11,12,13,16,17,18,19)));
        table=twoPlayersPlotTranspose(table);
        //create String Array with two first columnNames()
        String n1=table1.name();
        String n2=table2.name();
        table.column(0).setName(player1+" - "+n1);
        table.column(1).setName(player2+" - "+n2);
        String[] columnNames=table.columnNames().toArray(new String[0]);
        String[] columnNames2=new String[2];
        columnNames2[0]=columnNames[0];
        columnNames2[1]=columnNames[1];
        return VerticalBarPlot.create(
                "Stats for "+player1+" and "+player2,
                table,
                "Attr",
                Layout.BarMode.GROUP,
                columnNames2
        );
    }

    /**
     * another plotting mess, it sums some statistics for 2 teams and then for both of them it plots
     * @param team1 table of team1
     * @param team2 table of team2
     * @return I only believe in statistics I have doctored myself.
     */
    public static Figure twoTeamsPlot(Table team1, Table team2)
    {
        team1=callMeMoron(team1);
        team2=callMeMoron(team2);
        String s1=team1.name();
        String s2=team2.name();
        team1=ready2Plot(team1, new ArrayList<Integer>(List.of(8,9,10,11,12,13,14,15,16,17,18,19)));
        team2=ready2Plot(team2, new ArrayList<Integer>(List.of(8,9,10,11,12,13,14,15,16,17,18,19)));
        team1=team1.removeColumns(0);
        team2=team2.removeColumns(0);
        Column parameterNames = StringColumn.create("Parameter Name");
        DoubleColumn team1Values = DoubleColumn.create(s1);
        DoubleColumn team2Values = DoubleColumn.create(s2);
        DoubleColumn robota;
        Double suma;
        for(int i=0; i<12; i++)
        {
            parameterNames.append(columnNames.get(i+8));
            robota = (DoubleColumn) team1.column(i);
            suma= robota.sum();
            team1Values.append(suma);
            robota =(DoubleColumn) team2.column(i);
            suma= robota.sum();
            team2Values.append(suma);


        }
        Table wyn=Table.create(parameterNames,team1Values, team2Values);
        wyn.setName(s1+" and "+s2+" comparison");


        String[] nColNames = new String[2];
        nColNames[0]=s1;
        nColNames[1]=s2;

        return VerticalBarPlot.create(
                wyn.name(),
                wyn,
                "Parameter Name",
                Layout.BarMode.GROUP,
                nColNames
        );

    }
/*
return VerticalBarPlot.create(
                "Stats for "+player1+" and "+player2,
                table,
                "0 Player",
                Layout.BarMode.GROUP,
                table.columnNames().toArray(new String[0])
        );
 */
}
