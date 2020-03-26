package fr.android.tennistracker.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.DAO.DataAPIAccess;
import fr.android.tennistracker.R;
import org.json.JSONException;

import java.io.IOException;

public class HistoryActivity extends AppCompatActivity {

    private TextView dataAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dataAPI = findViewById(R.id.dataAPI);
        new APIAccessAsyncTask().execute("");
        
        
    }
    
    
    private class APIAccessAsyncTask extends AsyncTask <String, Void, String>{

        String allMatches;
        DataAPIAccess dataAPIAccess = new DataAPIAccess();
        @Override
        protected String doInBackground(String... strings) {
            try {
                allMatches = dataAPIAccess.getAllMatches();
                dataAPIAccess.matchList();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            
            return allMatches;
        }
        
        @Override
        protected void onPostExecute(String s) {
            dataAPI.setText(s);
        }
    }
    
}
