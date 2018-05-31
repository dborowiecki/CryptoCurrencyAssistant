package services;

import com.google.gson.JsonArray;

import java.io.IOException;
import java.net.URL;

public class BitcoinAverageApiHandler {
    private String baseUrl = "https://apiv2.bitcoinaverage.com/indices/global/history/{symbol}?period=alltime&format=json";
    private String symbol_set = "global";
    private String currency = "btcusd"; // Cryptocurrency BTCUSD/ETHUSD/LTCUSD
    private String period = "alltime"; // Alltime/monthly/daily

    /*public String getRawHistoricData(String currency) throws WrongCurrencyException {
        *//*String recivedData = "";
        try {
            DataFetcher fetcher = new DataFetcher(baseUrl.replace("{symbol}", currency));
             recivedData = HtmlHandler.reciveGetRequestData(url);
        } catch (IOException e) {
            System.err.print("Wrong input while reciving data from API\n");
            e.printStackTrace();
        } finally {
            if(recivedData== "") throw  new WrongCurrencyException(currency);
        }
        return recivedData;*//*
    }*/

    public JsonArray getJsonData(String currency) throws WrongCurrencyException{
        try {
            DataFetcher fetcher = new DataFetcher(baseUrl.replace("{symbol}", currency));
            return fetcher.fetchJsonData();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    class WrongCurrencyException extends Exception
    {
        // Parameterless Constructor
        public WrongCurrencyException(String currency) {
            super(currency +" is incorrect");
        }
    }
}

