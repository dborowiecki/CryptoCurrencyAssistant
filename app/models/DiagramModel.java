package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import services.BitcoinAverageApiHandler;
import services.DataFetcher;

import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class DiagramModel {
    public String title;
    public LinkedList<CurrencyLine> currencyLines;

    public DiagramModel(String title){
        this.title = title;
        currencyLines = new LinkedList<>();
    }

    public void createCurrencyLine(String currency){
        currencyLines.add(new CurrencyLine(currency));
    }
    public void addCurrencyLine(CurrencyLine line){
        currencyLines.add(line);
    }

    public LinkedList<CurrencyLine> getLines(){
        return currencyLines;
    }
}


