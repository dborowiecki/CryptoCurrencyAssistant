package services.Diagram;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import services.BitcoinAverageApiHandler;
import services.DateFormater;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

public class CurrencyLine{
    public static final String DATE_PATTERN = "yyy-MM-dd HH:mm:ss";
    public LinkedList<DiagramPoint> diagramPoints;
    public String currencyName;
    public String color = "rgba(255, 0, 0, 1)";
    private JsonArray jsonData;
    private int firstElementIndex;
    private int lastElementIndex;
    private final String xAxisMemberName = "time";
    private final String yAxisMemberName = "average";

    public CurrencyLine(String currency){
        try {
            currencyName = currency;
            jsonData = new BitcoinAverageApiHandler().getJsonData(currency);
            firstElementIndex = 0;
            lastElementIndex = jsonData.size();
        } catch (Exception e){
            System.err.print("Reciving data failed");
            e.printStackTrace();
        }
    }

    public static CurrencyLine createNewLine(String currency, Date dateFrom, Date dateTo){
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

    public void trimToPeriod(Date from, Date to){
        try {
            firstElementIndex = searchForDateIndex(to);
            lastElementIndex   = searchForDateIndex(from);
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

        for(int i=firstElementIndex; i<=lastElementIndex;i++){
            try {
                JsonElement jsonElement = jsonData.get(i);
                String xValue = DateFormater.formatJsonDate(jsonElement.getAsJsonObject().get(xAxisMemberName).getAsString());
                Number yValue = jsonElement.getAsJsonObject().get(yAxisMemberName).getAsNumber();
                DiagramPoint nextPoint = new DiagramPoint(xValue, yValue);

                points.add(0,nextPoint);
            } catch (IndexOutOfBoundsException e){
                System.out.print("Index out of bound");
            }

        }
        diagramPoints = points;
    }
    public DiagramPoint getPointByDate(Date date){
        try {
            Integer index = 0;
            String dateF = DateFormater.dateToString(date);
            while(index<diagramPoints.size()){
                DiagramPoint next = diagramPoints.get(index);
                if(dateF.equals(next.x)) return next;
                index++;
            }
        }catch(Exception e){
            System.err.print("Incorrectly formated data");
            e.printStackTrace();
        }
        return null;
    }
    private int searchForDateIndex(Date d) throws ParseException{
        int jsonLen = jsonData.size();
        Date userDate = d;
        Date jsonDate = DateFormater.stringToDate(jsonData.get(jsonLen-1).getAsJsonObject().get("time").getAsString(), DATE_PATTERN);

        Integer position = binaryPointSearch(userDate);
        if(position==null) {
            if (userDate.before(jsonDate)) return jsonLen - 1;
            jsonDate = DateFormater.stringToDate(jsonData.get(0).getAsJsonObject().get("time").getAsString(), DATE_PATTERN);
            if (userDate.after(jsonDate)) return 0;
        }
        return position;
    }
    private Integer binaryPointSearch(Date userDate) throws ParseException{
        int jsonLen = jsonData.size();
        Date jsonDate = DateFormater.stringToDate(jsonData.get(jsonLen-1).getAsJsonObject().get("time").getAsString(), DATE_PATTERN);
        if(userDate.before(jsonDate)) return null;
        jsonDate = DateFormater.stringToDate(jsonData.get(0).getAsJsonObject().get("time").getAsString(), DATE_PATTERN);
        if(userDate.after(jsonDate)) return null;

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