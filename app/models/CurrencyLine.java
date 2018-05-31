package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import services.BitcoinAverageApiHandler;
import services.DateFormater;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

public class CurrencyLine{
    private JsonArray jsonData;
    private int firstElementIndex;
    private int lastElementIndex;
    private final String xAxisMemberName = "time";
    private final String yAxisMemberName = "average";
    public static final String DATE_PATTERN = "yyy-MM-dd HH:mm:ss";
    public LinkedList<DiagramPoint> diagramPoints;
    public String name;
    public String color = "rgba(255, 0, 0, 1)";
    public CurrencyLine(String currency){
        try {
            name = currency;
            jsonData = new BitcoinAverageApiHandler().getJsonData(currency);
            firstElementIndex = 0;
            lastElementIndex = jsonData.size();
        } catch (Exception e){
            System.err.print("Reciving data failed");
            e.printStackTrace();
        }
    }

    public static CurrencyLine createNewLine(String currency, String dateFrom, String dateTo){
        CurrencyLine newLine = new CurrencyLine(currency);
        newLine.trimToPeriod(dateFrom, dateTo);
        newLine.createLineFromJson();
        return newLine;
    }

    public static CurrencyLine createNewLine(String currency){
        CurrencyLine newLine = new CurrencyLine(currency);
        newLine.createLineFromJson();
        return newLine;
    }

    private void trimToPeriod(String from, String to){
        try {
            firstElementIndex = searchForDateIndex(to);
            lastElementIndex = searchForDateIndex(from);
        } catch (ParseException e){
            System.err.print("Incorrectly formated data");
            e.printStackTrace();
        }
    }

    public LinkedList<DiagramPoint> getLinePoints(){
       return diagramPoints;
    }


    private void createLineFromJson(){
        LinkedList<DiagramPoint> points = new LinkedList<>();

        for(int i=firstElementIndex; i<lastElementIndex;i++){
            JsonElement jsonElement = jsonData.get(i);
            String xValue = DateFormater.formatDate(jsonElement.getAsJsonObject().get(xAxisMemberName).getAsString());
            Number yValue = jsonElement.getAsJsonObject().get(yAxisMemberName).getAsNumber();
            DiagramPoint nextPoint = new DiagramPoint(xValue, yValue);

            points.add(0,nextPoint);
        }
        diagramPoints = points;
    }

    private int searchForDateIndex(String d) throws ParseException{
        int jsonLen = jsonData.size();
        Date userDate = DateFormater.stringToDate(d, "yyy-MM-dd");
        Date jsonDate = DateFormater.stringToDate(jsonData.get(jsonLen-1).getAsJsonObject().get("time").getAsString(), DATE_PATTERN);

        if(userDate.before(jsonDate)) return jsonLen;
        jsonDate = DateFormater.stringToDate(jsonData.get(0).getAsJsonObject().get("time").getAsString(), DATE_PATTERN);
        if(userDate.after(jsonDate)) return 0;

        int left = 0;
        int right = jsonLen - 1;
        int mid = right - left;
        while (left <= right) {
            mid = left + (right - left) / 2;
            jsonDate = DateFormater.stringToDate(jsonData.get(mid).getAsJsonObject().get("time").getAsString(), DATE_PATTERN);

            if      (userDate.after(jsonDate)){
                right = mid - 1;
            }
            else if (userDate.before(jsonDate)){
                left = mid + 1;}
            else {
                break;
            }
        }
        return mid;
    }
    public void setColor(Integer r, Integer g, Integer b){
        color = "rgba("+Integer.toString(r%256)+","+Integer.toString(g%256)+","+Integer.toString(b%256)+",1)";
    }
}