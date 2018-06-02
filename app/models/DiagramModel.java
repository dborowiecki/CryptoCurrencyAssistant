package models;

import services.DateFormater;

import java.text.ParseException;
import java.util.*;

public class DiagramModel {
    public String title;
    public LinkedList<CurrencyLine> currencyLines;
    public LinkedList<TrendLine> trendLines;
    public Map<String, List<TrendLine>> currencyTrends;
    public String dateStart;
    public String dateEnd;

    public DiagramModel(String title){
        this.title = title;
        currencyLines = new LinkedList<>();
        trendLines = new LinkedList<>();
        currencyTrends = new HashMap<>();
    }

    public void createCurrencyLine(String currency){
        currencyLines.add(CurrencyLine.createNewLine(currency));
        //currencyTrends.put(currency, new LinkedList<>());
    }

    public void changeLineColor(CurrencyLine l,int r, int g, int b){
        l.setColor(r,g,b);
    }

    public void createTrimmedLine(String currency, String dateFrom, String dateTo){
        currencyLines.add(CurrencyLine.createNewLine(currency, dateFrom, dateTo));
    }

    public void addCurrencyLine(CurrencyLine line){
        currencyLines.add(line);

    }

    private void addTrendLine(CurrencyLine currencyLine, Date dateFrom, Date dateTo){
            TrendLine newTrendLine = new TrendLine(currencyLine, dateFrom, dateTo);
            System.out.println(
                             "DATE FROM: "+dateFrom
                            +"DATE TO: "+dateTo
                            +"NOWA LINIA" + newTrendLine
            );
            trendLines.add(newTrendLine);
            currencyTrends.get(currencyLine.currencyName).add(newTrendLine);
    }

    public void createWeeklyTrendLines(CurrencyLine line){
        currencyTrends.put(line.currencyName, new LinkedList<>());
        String startDate = line.diagramPoints.get(0).x;
        String endDate = line.diagramPoints.get(line.diagramPoints.size()-1).x;
        List<Date> periods = DateFormater.divideDateByPeriod(startDate, endDate, 8);
            for (int i = 0; i < periods.size() - 1;i++) {
               // String start = DateFormater.dateToString(periods.get(i), "dd/MM/yyyy");
               // String end   = DateFormater.dateToString(periods.get(i+1), "dd/MM/yyyy");
                try {
                    addTrendLine(line, periods.get(i), periods.get(i + 1));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(startDate+ "  "+endDate);
        System.out.println("REZULTAT TWORZENIA TRENDLINE: "+currencyTrends);
    }
    public LinkedList<CurrencyLine> getLines(){
        return currencyLines;
    }
}


