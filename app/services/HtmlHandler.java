package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlHandler {
    public static String reciveGetRequestData(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        StringBuilder data;
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        BufferedReader in = new BufferedReader(reader);
        String inputLine;
        data = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            data.append(inputLine);
        }
        in.close();
        String requestedData = data.toString();
        //print result
        connection.disconnect();
        return requestedData;
    }
}
