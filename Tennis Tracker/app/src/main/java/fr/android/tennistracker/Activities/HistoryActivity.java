package fr.android.tennistracker.Activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.R;

public class HistoryActivity extends AppCompatActivity {

    private TextView dataAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dataAPI = findViewById(R.id.dataAPI);
        String allMatches = getIntent().getStringExtra("allMatches");
        dataAPI.setText(allMatches);
    }
    
}
