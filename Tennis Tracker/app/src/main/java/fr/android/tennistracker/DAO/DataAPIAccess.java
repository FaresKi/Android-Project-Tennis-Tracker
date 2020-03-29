package fr.android.tennistracker.DAO;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.Model.Statistics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataAPIAccess {
    private URL endpointAPI;
    private String jsonResponse = "";
    private Gson g = new Gson();
    private HttpURLConnection myConnection;
    public DataAPIAccess() {
    }

    public String getAllMatches() throws IOException {
        endpointAPI = new URL("http://10.0.2.2:8080/Matches"); //create URL
        myConnection = (HttpURLConnection) endpointAPI.openConnection();
        myConnection.setRequestMethod("GET");
        myConnection.setRequestProperty("Content-type", "application/json");
        Scanner sc = new Scanner(endpointAPI.openStream());
        while (sc.hasNext()) {
            jsonResponse += sc.nextLine();
        }

        return jsonResponse;
    }

    public void matchList() throws JSONException {
        
        List<Match> matchList = new ArrayList<>();
        JSONArray array = new JSONArray(jsonResponse);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            Match match = g.fromJson(object.toString(), Match.class);
            matchList.add(match);
        }
    }
    
    public void sendNewMatch(Match match) throws IOException {
        /*
        myConnection = (HttpURLConnection) endpointAPI.openConnection();
        myConnection.setRequestMethod("POST");
        myConnection.setRequestProperty("Content-type", "application/json");
         */
        
        String jsonInString = g.toJson(match);

        System.out.println("JSON String : " + jsonInString);
        
        

    }


}
