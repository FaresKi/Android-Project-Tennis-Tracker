package fr.android.tennistracker.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.android.tennistracker.Model.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

public class DataAPIAccess {
    private URL endpointAPI;

    private String jsonResponse; 
    public DataAPIAccess(){
    }
    
    public String getAllMatches() throws IOException {
        String output;
        endpointAPI = new URL("http://10.0.2.2:8080/Matches"); //create URL
        HttpURLConnection myConnection = (HttpURLConnection) endpointAPI.openConnection();
        if(myConnection.getResponseCode()==200){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
            while((output=bufferedReader.readLine())!=null){
                jsonResponse+=output;
            }
            myConnection.disconnect();
            return jsonResponse;
        }
        return "fail";
        
    }
    
    public void matchList() {
        Gson gson = new Gson();
        
       Match matches = gson.fromJson(jsonResponse,Match.class);
        
        
    }
    
}
