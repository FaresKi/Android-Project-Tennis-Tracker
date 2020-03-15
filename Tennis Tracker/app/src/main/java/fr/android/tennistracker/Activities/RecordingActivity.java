package fr.android.tennistracker.Activities;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.android.tennistracker.R;

public class RecordingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        String firstName = getIntent().getStringExtra("firstPlayerName");
        TextView tabFirstName = findViewById(R.id.tabFirstName);
        tabFirstName.setText(firstName);
        String secondName = getIntent().getStringExtra("secondPlayerName");
        TextView tabSecondName  = findViewById(R.id.tabSecondName);
        tabSecondName.setText(secondName);
    }
}
