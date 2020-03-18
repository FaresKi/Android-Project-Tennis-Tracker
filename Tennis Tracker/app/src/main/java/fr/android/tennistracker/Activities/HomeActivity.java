package fr.android.tennistracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = findViewById(R.id.buttonNewMatch);
        button.setOnClickListener(view -> onClick(view));
        getSupportActionBar().setTitle("Tennis•Tracker");
    }


    public void onClick(View v) {
        Intent intent = new Intent(this, NewMatchActivity.class);
        this.startActivity(intent);
    }
}
