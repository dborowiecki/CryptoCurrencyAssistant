package services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Connection {
    private HttpURLConnection connection = null;

    public Connection(String urlAdress){
        try {
            URL url = new URL(urlAdress);
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

    public int getResponseCode() throws IOException{
        return connection.getResponseCode();
    }

}
