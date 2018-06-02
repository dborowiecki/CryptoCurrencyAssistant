package models;

import akka.http.impl.engine.parsing.NotEnoughDataException;
import services.DateFormater;

import java.util.Date;

public class TrendLine {
    public CurrencyLine currencyLine;
    public String startDate;
    public String endDate;
    public DiagramPoint  lineStart;
    public DiagramPoint  lineEnd;
    public DiagramPoint[] linePoints = new DiagramPoint[2];
    public String trending;
    public String color = "rgba(255, 0, 0, 1)";

    public TrendLine(CurrencyLine line, String dateStart, String dateEnd) throws TrendLineException{
        this.currencyLine = line;
        this.startDate = dateStart;
        this.endDate = dateEnd;

        coutTrending();
    }

    public void coutTrending() throws TrendLineException{
        DiagramPoint endingPoint;
        DiagramPoint startingPoint;
        try {
            endingPoint   = currencyLine.getPointByDate(DateFormater.stringToDate(endDate, "yyyy-MM-dd"));
            startingPoint = currencyLine.getPointByDate(DateFormater.stringToDate(startDate, "yyyy-MM-dd"));
        } catch (Exception e){
            System.err.println("Data incorrectly passed");
            e.printStackTrace();
            return;
        }
        int gain;

        gain = endingPoint.y.intValue() - startingPoint.y.intValue();
        color = gain > 0 ?  "rgba(0, 255, 0, 1)" : "rgba(255, 0, 0, 1)";
        lineStart = startingPoint;
        lineEnd = endingPoint;

        //Znaleźć największy dół
        //Znaleźć największe szczyty
        if(gain>0) findHighest();
        else findLowest();
        //Połączyć

        //Wyznaczyć wyznaczyć prostą linię przechodzącą między punktami od start do stop
    }


    private void findHighest() throws TrendLineException{
        int start = currencyLine.diagramPoints.indexOf(lineStart);
        int end = currencyLine.diagramPoints.indexOf(lineEnd);
        if(end-start<2) throw new TrendLineException();
        DiagramPoint first = currencyLine.diagramPoints.get(start);
        DiagramPoint second = currencyLine.diagramPoints.get(start+1);
        linePoints[0] = first.y.doubleValue() >= second.y.doubleValue() ? first : second;
        linePoints[1] = first.y.doubleValue() <= second.y.doubleValue() ? first : second;
        for(int i = start+2; i<=end; i++){
            DiagramPoint next = currencyLine.diagramPoints.get(i);
            if(linePoints[0].y.doubleValue()<next.y.doubleValue()){
                linePoints[1] = linePoints[0];
                linePoints[0] = next;
            }
            else {
                if(linePoints[1].y.doubleValue()<next.y.doubleValue()){
                    linePoints[1] = next;
                }
            }
        }
    }

    private void findLowest() throws TrendLineException{
        int start = currencyLine.diagramPoints.indexOf(lineStart);
        int end = currencyLine.diagramPoints.indexOf(lineEnd);
        if(end-start<2) throw new TrendLineException();
        DiagramPoint first = currencyLine.diagramPoints.get(start);
        DiagramPoint second = currencyLine.diagramPoints.get(start+1);
        linePoints[0] = first.y.doubleValue() <= second.y.doubleValue() ? first : second;
        linePoints[1] = first.y.doubleValue() >= second.y.doubleValue() ? first : second;
        for(int i = start+2; i<=end; i++){
            DiagramPoint next = currencyLine.diagramPoints.get(i);
            if(linePoints[0].y.doubleValue()>next.y.doubleValue()){
                linePoints[1] = linePoints[0];
                linePoints[0] = next;
            }
            else {
                if(linePoints[1].y.doubleValue()>next.y.doubleValue()){
                    linePoints[1] = next;
                }
            }
        }
    }

    public void printPointsAndPeaks(){
        int start = currencyLine.diagramPoints.indexOf(lineStart);
        int end = currencyLine.diagramPoints.indexOf(lineEnd);
        for(int i = start; i<=end; i++){
            DiagramPoint next = currencyLine.diagramPoints.get(i);
            System.out.print("[" + next.x +", "+next.y+"], ");
        }

        System.out.println(
                "ZNALEZIONE:"
                + "["+linePoints[0].x+", "+linePoints[0].y+"], "
                + "["+linePoints[1].x+", "+linePoints[1].y+"], "
        );
    }

    class TrendLineException extends Exception{
        public TrendLineException() {
            super("Data for creating trend line is insufficient");
        }
    }
}
