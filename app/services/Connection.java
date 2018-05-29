package services;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class Connection {
    private HttpURLConnection connection = null;

    public Connection(String urlAdress){
        try {
            URL url = new URL("https://apiv2.bitcoinaverage.com/indices/{symbol_set}/history/{symbol}?period={period}&format={format}");
            this.connection = (HttpURLConnection) url.openConnection();
           // con.setRequestMethod("GET");
        }catch (IOException i){
            i.printStackTrace();
        }
    }

    public void setRequestMethod(String method){
        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e){
            e.printStackTrace();
        }
    }

}
