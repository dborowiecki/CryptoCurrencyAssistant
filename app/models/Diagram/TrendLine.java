package models.Diagram;

import java.util.Date;

public class TrendLine {
    private CurrencyLine currencyLine;
    public DiagramPoint startPoint;
    public DiagramPoint endingPoint;
    private DiagramPoint[] extrema = new DiagramPoint[2];
    public String color = "rgba(255, 0, 0, 1)";

    public static TrendLine createPeriodTrendLine(CurrencyLine line, Date dateStart, Date dateEnd) throws TrendLineException{
        TrendLine newLine = new TrendLine(line, dateStart, dateEnd);

        return newLine;
    }

    private TrendLine(CurrencyLine line, Date dateStart, Date dateEnd) throws TrendLineException{
        this.currencyLine = line;

        coutTrending(dateStart, dateEnd);
        if(!valdate()) throw new TrendLineException();
    }

    private void coutTrending(Date startDate, Date endDate){
        DiagramPoint endingPoint;
        DiagramPoint startingPoint;
        DiagramPoint  lineStart;
        DiagramPoint  lineEnd;

        try {

            endingPoint   = currencyLine.getPointByDate(endDate);
            startingPoint = currencyLine.getPointByDate(startDate);

            int gain;
            gain = endingPoint.y.intValue() - startingPoint.y.intValue();
            color = gain > 0 ?  "rgba(0, 255, 0, 1)" : "rgba(255, 0, 0, 1)";
            lineStart = startingPoint;
            lineEnd = endingPoint;

            if (gain > 0) findLowest(lineStart, lineEnd);
            else findHighest(lineStart, lineEnd);

            findStartAndEndingPoint(lineStart, lineEnd);

        } catch (TrendLineException e){
            System.err.println("Couldn't add new trend line, not enough data");
        } catch (NullPointerException n){
            System.err.println("One of dates not found in diagram");
        }
    }


    private void findHighest(DiagramPoint lineStart, DiagramPoint lineEnd) throws TrendLineException{
        int start = currencyLine.diagramPoints.indexOf(lineStart);
        int end = currencyLine.diagramPoints.indexOf(lineEnd);
        if(end-start<2) throw new TrendLineException();
        DiagramPoint first = currencyLine.diagramPoints.get(start);
        DiagramPoint second = currencyLine.diagramPoints.get(start+1);
        extrema[0] = first.y.doubleValue() >= second.y.doubleValue() ? first : second;
        extrema[1] = first.y.doubleValue() <= second.y.doubleValue() ? first : second;
        for(int i = start+2; i<=end; i++){
            DiagramPoint next = currencyLine.diagramPoints.get(i);
            if(extrema[0].y.doubleValue()<next.y.doubleValue()){
                extrema[1] = extrema[0];
                extrema[0] = next;
            }
            else {
                if(extrema[1].y.doubleValue()<next.y.doubleValue()){
                    extrema[1] = next;
                }
            }
        }
    }

    private void findLowest(DiagramPoint lineStart, DiagramPoint lineEnd) throws TrendLineException{
        int start = currencyLine.diagramPoints.indexOf(lineStart);
        int end = currencyLine.diagramPoints.indexOf(lineEnd);
        if(end-start<2) throw new TrendLineException();
        DiagramPoint first = currencyLine.diagramPoints.get(start);
        DiagramPoint second = currencyLine.diagramPoints.get(start+1);
        extrema[0] = first.y.doubleValue() <= second.y.doubleValue() ? first : second;
        extrema[1] = first.y.doubleValue() >= second.y.doubleValue() ? first : second;
        for(int i = start+2; i<=end; i++){
            DiagramPoint next = currencyLine.diagramPoints.get(i);
            if(extrema[0].y.doubleValue()>next.y.doubleValue()){
                extrema[1] = extrema[0];
                extrema[0] = next;
            }
            else {
                if(extrema[1].y.doubleValue()>next.y.doubleValue()){
                    extrema[1] = next;
                }
            }
        }
    }

    private void findStartAndEndingPoint(DiagramPoint lineStart, DiagramPoint lineEnd){
        Double functionFactor = 1.0;
        Double functionAddValue = 0.0;
        int extremaDeltaX = 0;

        if(currencyLine.diagramPoints.indexOf(extrema[1])<currencyLine.diagramPoints.indexOf(extrema[1])){
            DiagramPoint temp = extrema[1];
            extrema[1] = extrema[0];
            extrema[0] = temp;
        }
        int x1 = currencyLine.diagramPoints.indexOf(extrema[0]);
        int x2 = currencyLine.diagramPoints.indexOf(extrema[1]);
        double firstExtremeValue = extrema[0].y.doubleValue();
        double secondExtremeValue= extrema[1].y.doubleValue();
        extremaDeltaX = x2-x1;
        double deltaY = (secondExtremeValue-firstExtremeValue)/extremaDeltaX;
        double distanceX1 = x1-currencyLine.diagramPoints.indexOf(lineStart)+1;
        double lineLen = currencyLine.diagramPoints.indexOf(lineEnd)-currencyLine.diagramPoints.indexOf(lineStart)+1;

        startPoint  = new DiagramPoint(lineStart.x, lineStart.y.doubleValue()-(distanceX1*deltaY));
        endingPoint = new DiagramPoint(lineEnd.x, lineEnd.y.doubleValue()+lineLen*deltaY);
    }

    private boolean valdate(){
        if(startPoint == null || endingPoint == null) return false;
        return true;
    }
    public class TrendLineException extends Exception{
        public TrendLineException() {
            super("Data for creating trend line is insufficient");
        }
    }
}
