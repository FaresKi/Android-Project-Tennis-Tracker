package fr.android.tennistracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.android.tennistracker.R;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setTitle(R.string.firstPlayerName + "vs" + R.string.secondPlayerName);
       
    }
}