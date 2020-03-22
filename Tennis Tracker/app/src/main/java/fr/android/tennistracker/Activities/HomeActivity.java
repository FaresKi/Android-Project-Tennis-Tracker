package fr.android.tennistracker.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.DAO.DataAPIAccess;
import fr.android.tennistracker.R;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private  Intent intent;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = findViewById(R.id.buttonNewMatch);
        button.setOnClickListener(view -> onClick(view));
        getSupportActionBar().setTitle("Tennis•Tracker");
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonNewMatch:
                 intent = new Intent(this, NewMatchActivity.class);
                this.startActivity(intent);
                break;
            case R.id.buttonPreviousMatches:
                intent = new Intent(this, HistoryActivity.class);
                AtomicReference<String> allMatches = new AtomicReference<>("fail");
                AsyncTask.execute(() -> {
                    try {
                        allMatches.set(new DataAPIAccess().getAllMatches());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                intent.putExtra("allMatches", allMatches);
                this.startActivity(intent);
                break;
            case R.id.buttonNewPicture:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
                break;
        }
        
    }
    

}
