package data.visualization;

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
import java.util.List;

import static java.lang.Math.min;

public class TwoPlayersPlots {
    public static void main(String[] args) {
        data.collect.PlayersStatsGetter getter = new data.collect.PlayersStatsGetter();
        Table table = getter.getPlayersStats("Barcelona");
        Table table2 = getter.getPlayersStats("Arsenal");
        System.out.println(table);
        Plot.show(slupkowyPodwojny(table,table2,7,"Barcelona", "Arsenal"));



    }
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
                int asc = (int) str.charAt(0);
                if (asc > 47 && asc < 58) {
                    listad.add(Double.valueOf(str));
                }
            }
        }
        DoubleColumn dopel = DoubleColumn.create(name, listad);
        return dopel;

    }
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
    public static Figure slupkowyPionowy(Table table, int id)
    {
        Table doRoboty = ready2Plot(table,id);
        String[] namy=doRoboty.columnNames().toArray(new String[0]);
        String nazwadan=namy[1];
        return VerticalBarPlot.create(
                nazwadan+"według zawodników",
                doRoboty,
                "0 Player",
                Layout.BarMode.GROUP,
                nazwadan);

    }
    public static Figure slupkowyPoziomy(Table table, int id)
    {
        Table doRoboty = ready2Plot(table,id);
        String[] namy=doRoboty.columnNames().toArray(new String[0]);
        String nazwadan=namy[1];
        return HorizontalBarPlot.create(
                nazwadan+"według zawodników",
                doRoboty,
                "0 Player",
                Layout.BarMode.STACK,
                nazwadan

        );
    }
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
        PieTrace trace =
                PieTrace.builder(top5.categoricalColumn(0), top5.numberColumn(nazwadan)).build();
        Layout layout = Layout.builder().title(nazwadan+" by player").build();
        return new Figure(layout,trace);
    }
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
        Layout layout = Layout.builder().title(nazwadan+" by player").build();

        return new Figure(layout,(Trace)trace);
    }
    public static StringColumn kolumienka(String nazwa, String parametr, int licz)
    {
        List<String> chlop=new ArrayList<>();
        for (int i = 0; i <licz ; i++)
        {
            chlop.add(parametr);
        }
        return StringColumn.create(nazwa,chlop);
    }


}
