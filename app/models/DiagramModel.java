package models;

import services.DateFormater;

import java.awt.*;
import java.util.*;
import java.util.List;

import services.Diagram.*;

public class DiagramModel {
    public String title;
    public LinkedList<CurrencyLine> currencyLines;
    public LinkedList<TrendLine> trendLines;
    public Map<String, List<TrendLine>> currencyTrends;

    public LinkedList<CurrencyLine> getLines(){
        return currencyLines;
    }
    public DiagramModel(String title){
        this.title = title;
        currencyLines = new LinkedList<>();
        trendLines = new LinkedList<>();
        currencyTrends = new HashMap<>();
    }
    public void addCurrencyInfo(String currency, Date dateFrom, Date dateTo, Color color){
        CurrencyLine newLine;
        newLine = CurrencyLine.createNewLine(currency, dateFrom, dateTo);
        addCurrencyLine(newLine);
        newLine.setColor(color.getRed(),color.getGreen(),color.getBlue());
    }
    public void createCurrencyLine(String currency){
        currencyLines.add(CurrencyLine.createNewLine(currency));
    }

    public void changeLineColor(CurrencyLine l,int r, int g, int b){
        l.setColor(r,g,b);
    }

    public void createTrimmedLine(String currency, Date dateFrom, Date dateTo){
        currencyLines.add(CurrencyLine.createNewLine(currency, dateFrom, dateTo));
    }

    public void addCurrencyLine(CurrencyLine line){
        currencyLines.add(line);

    }

    private void addTrendLine(CurrencyLine currencyLine, Date dateFrom, Date dateTo){
        try {
            TrendLine newTrendLine = TrendLine.createPeriodTrendLine(currencyLine, dateFrom, dateTo);
            trendLines.add(newTrendLine);
            currencyTrends.get(currencyLine.currencyName).add(newTrendLine);
        } catch(Exception e){
            return;
        }

    }

    public void createWeeklyTrendLines(CurrencyLine line){
        currencyTrends.put(line.currencyName, new LinkedList<>());
        String startDate = line.diagramPoints.get(0).x;
        String endDate = line.diagramPoints.get(line.diagramPoints.size()-1).x;
        List<Date> periods = DateFormater.divideDateByPeriod(startDate, endDate, 8);
            for (int i = 0; i < periods.size() - 1;i++) {
                try {
                    addTrendLine(line, periods.get(i), periods.get(i + 1));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(startDate+ "  "+endDate);
    }
}


