package services;


import com.google.gson.JsonArray;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class DataFetcher {
    private URL url;
    //private HashMap<String, String> pathComponents;
    JsonArray fetchedJsonData;
    String fetchedStringData;

    public JsonArray getFetchedJsonData() {
        return fetchedJsonData;
    }

    public DataFetcher(String url){
        try {
            this.url = new URL(url);
        }catch (IOException i){
            i.printStackTrace();
        }
    }

    public JsonArray fetchJsonData() throws IOException{
        String requestData = HtmlHandler.reciveGetRequestData(url);
        return JSONHandler.getJsonData(requestData);
    }
    public JsonArray fetchJsonData(Date from, Date to) throws IOException{
        String requestData = HtmlHandler.reciveGetRequestData(url);
        return JSONHandler.getJsonData(requestData);
    }

}
