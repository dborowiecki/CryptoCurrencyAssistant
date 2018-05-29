package services;

import java.util.HashMap;

public class DataFetcher {
    private Connection dataURL;
    private HashMap<String, String> pathComponents;

    public DataFetcher(String baseURL){
        dataURL = new Connection(baseURL);
    }
}
