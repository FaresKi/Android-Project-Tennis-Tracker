package fr.android.tennistracker.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.R;

public class NewMatchActivity extends AppCompatActivity {
    Intent intent;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.startButton){
            intent = new Intent(this,RecordingActivity.class);
            EditText firstPlayer = findViewById(R.id.firstPlayerName);
            EditText secondPlayer = findViewById(R.id.secondPlayerName);

            String firstPlayerName = String.valueOf(firstPlayer.getText());
            String secondPlayerName = String.valueOf(secondPlayer.getText());
            if(!firstPlayerName.isEmpty() && !secondPlayerName.isEmpty()){
                intent.putExtra("firstPlayerName",firstPlayerName)
                        .putExtra("secondPlayerName",secondPlayerName);

                this.startActivity(intent);
            }
        }
        return true;
    }
}
