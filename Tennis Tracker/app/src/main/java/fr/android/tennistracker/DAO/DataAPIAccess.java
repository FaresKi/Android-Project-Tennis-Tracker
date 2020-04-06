package fr.android.tennistracker.DAO;


import com.google.gson.Gson;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.Model.Statistics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
        endpointAPI = new URL("http:10.0.2.2:8080/Matches"); //create URL
        myConnection = (HttpURLConnection) endpointAPI.openConnection();
        myConnection.setRequestMethod("GET");
        myConnection.setRequestProperty("Content-type", "application/json");
        Scanner sc = new Scanner(endpointAPI.openStream());
        while (sc.hasNext()) {
            jsonResponse += sc.nextLine();
        }

        return jsonResponse;
    }
    

    public void sendNewGame(Match match) throws IOException {

        endpointAPI = new URL("http://10.0.2.2:8080/Games"); //create URL
        myConnection = (HttpURLConnection) endpointAPI.openConnection();
        myConnection.setRequestMethod("POST");
        myConnection.setRequestProperty("Content-type", "application/json");
        myConnection.setRequestProperty("Accept", "application/json");
        myConnection.setDoOutput(true);

        //g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsonInString = match.toString();

        System.out.println("JSON String : " + jsonInString);

        OutputStream outputStream = myConnection.getOutputStream();
        byte[] input = jsonInString.getBytes(StandardCharsets.UTF_8);
        outputStream.write(input);
        outputStream.flush();
        outputStream.close();

        int responseCode = myConnection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
    }

    public void sendNewPlayer(Player player) throws IOException {
        endpointAPI = new URL("http://10.0.2.2:8080/Players"); //create URL
        myConnection = (HttpURLConnection) endpointAPI.openConnection();
        myConnection.setRequestMethod("POST");
        myConnection.setRequestProperty("Content-type", "application/json");
        myConnection.setRequestProperty("Accept", "application/json");
        myConnection.setDoOutput(true);

        String jsonString = g.toJson(player);
        System.out.println("JSON Player String : " + jsonString);

        OutputStream outputStream = myConnection.getOutputStream();
        byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
        outputStream.write(input);
        outputStream.flush();
        outputStream.close();

        int responseCode = myConnection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
    }
    
    public void sendNewStats(Statistics statistics) throws IOException {
        endpointAPI = new URL("http://10.0.2.2:8080/Stats"); //create URL
        myConnection = (HttpURLConnection) endpointAPI.openConnection();
        myConnection.setRequestMethod("POST");
        myConnection.setRequestProperty("Content-type", "application/json");
        myConnection.setRequestProperty("Accept", "application/json");
        myConnection.setDoOutput(true);
        
        String jsonString = g.toJson(statistics);
        OutputStream outputStream = myConnection.getOutputStream();
        byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
        outputStream.write(input);
        outputStream.flush();
        outputStream.close();

        int responseCode = myConnection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
        
        
    }


}
