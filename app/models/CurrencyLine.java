package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import services.BitcoinAverageApiHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class CurrencyLine{
    public LinkedList<DiagramPoint> diagramPoints;
    JsonArray data;

    public CurrencyLine(String currency){
        try {
            data = new BitcoinAverageApiHandler().getJsonData(currency);
            createLineFromJson(data, "time","average");
        } catch (Exception e){
            System.err.print("Reciving data failed");
            e.printStackTrace();
        }
    }

    public LinkedList<DiagramPoint> getDiagramPoints(){
       return diagramPoints;
    }

    public LinkedList<DiagramPoint> createLineFromJson(JsonArray json, String memberNameX, String memberNameY){
        LinkedList<DiagramPoint> points = new LinkedList<>();

        for (JsonElement jsonElement : json) {
            String xValue = formatDate(jsonElement.getAsJsonObject().get(memberNameX).getAsString());
            Number yValue = jsonElement.getAsJsonObject().get(memberNameY).getAsNumber();
            DiagramPoint nextPoint = new DiagramPoint(xValue, yValue);

            points.add(0,nextPoint);
        }
        diagramPoints = points;
        return points;
    }

    private String formatDate(String unformatedData){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(unformatedData);
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e){
            System.err.print("Incorrect data formating");
            e.printStackTrace();
        }
        return null;
    }
}