package fr.android.tennistracker.Activities;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.R;

public class NewMatchActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter arrayAdapter;

    private String[] players = {"Joueur 1", "Joueur 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.joueurs);
        arrayAdapter = new ArrayAdapter(this, R.layout.list_players,R.id.textView2,players);
        listView.setAdapter(arrayAdapter);
        
        

    }
}
