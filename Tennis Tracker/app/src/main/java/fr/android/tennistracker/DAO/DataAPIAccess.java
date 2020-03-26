package fr.android.tennistracker.DAO;


import com.google.gson.Gson;
import fr.android.tennistracker.Model.Match;
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

    public DataAPIAccess() {
    }

    public String getAllMatches() throws IOException {
        endpointAPI = new URL("http://10.0.2.2:8080/Matches"); //create URL
        HttpURLConnection myConnection = (HttpURLConnection) endpointAPI.openConnection();
        myConnection.setRequestMethod("GET");
        myConnection.setRequestProperty("Content-type", "application/json");
        Scanner sc = new Scanner(endpointAPI.openStream());
        while (sc.hasNext()) {
            jsonResponse += sc.nextLine();
        }

        return jsonResponse;
    }

    public void matchList() throws JSONException {
        Gson g = new Gson();
        List<Match> matchList = new ArrayList<>();
        JSONArray array = new JSONArray(jsonResponse);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            Match match = g.fromJson(object.toString(), Match.class);
            matchList.add(match);
        }
    }
    
    public void getStatisticsByMatch(){
        
    }


}
