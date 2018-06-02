package models;

import java.util.LinkedList;

public class DiagramModel {
    public String title;
    public LinkedList<CurrencyLine> currencyLines;
    public LinkedList<CurrencyLine> trendLines;
    public String dateStart;
    public String dateEnd;

    public DiagramModel(String title){
        this.title = title;
        currencyLines = new LinkedList<>();
        trendLines = new LinkedList<>();
    }

    public void createCurrencyLine(String currency){
        currencyLines.add(CurrencyLine.createNewLine(currency));
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
    public void addTrendLine(CurrencyLine currencyLine, String dateFrom, String dateTo){
        try {
            TrendLine newTrendLine = new TrendLine(currencyLine, dateFrom, dateTo);
            newTrendLine.printPointsAndPeaks();
        }catch (Exception e){
            System.err.println("Couldn't add new trend line, not enough data");
        }
    }
    public LinkedList<CurrencyLine> getLines(){
        return currencyLines;
    }
}


