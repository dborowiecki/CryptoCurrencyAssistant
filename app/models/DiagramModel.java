package models;

import java.util.LinkedList;

public class DiagramModel {
    public String title;
    public LinkedList<CurrencyLine> currencyLines;
    public String dateStart;
    public String dateEnd;

    public DiagramModel(String title){
        this.title = title;
        currencyLines = new LinkedList<>();
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

    public LinkedList<CurrencyLine> getLines(){
        return currencyLines;
    }
}


