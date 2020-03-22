package fr.android.tennistracker.DAO;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataAPIAccess {
    private URL endpointAPI;

    public DataAPIAccess() {
    }
    
    public String getAllMatches() throws IOException {
        endpointAPI = new URL("http://localhost:8080/Matches"); //create URL
        HttpURLConnection myConnection = (HttpURLConnection) endpointAPI.openConnection();
        if(myConnection.getResponseCode()==200){
            InputStream responseBody = myConnection.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody,"UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            return jsonReader.toString();
        }
        return "fail";
        
    }
    
}
