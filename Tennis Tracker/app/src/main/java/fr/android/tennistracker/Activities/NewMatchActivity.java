package fr.android.tennistracker.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import fr.android.tennistracker.DAO.MyDBHandler;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.R;

public class NewMatchActivity extends AppCompatActivity {
    Intent intent;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        getSupportActionBar().setTitle("New Match");

        if (savedInstanceState != null) {
            EditText firstPlayer = findViewById(R.id.firstPlayerName);
            EditText secondPlayer = findViewById(R.id.secondPlayerName);

            String firstPlayerName = savedInstanceState.getString("firstPlayerName");
            String secondPlayerName = savedInstanceState.getString("secondPlayerName");

            firstPlayer.setText(firstPlayerName);
            secondPlayer.setText(secondPlayerName);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.startButton :
                dbHandler = new MyDBHandler(this,null,null,1);
                intent = new Intent(this, RecordingActivity.class);

                Spinner matchFormat = findViewById(R.id.matchFormat);
                int matchFormatPos = matchFormat.getSelectedItemPosition();

                Spinner lastSetFormat = findViewById(R.id.lastSetFormat);
                int lastSetFormatPos = lastSetFormat.getSelectedItemPosition();

                EditText firstPlayer = findViewById(R.id.firstPlayerName);
                EditText secondPlayer = findViewById(R.id.secondPlayerName);

                String firstPlayerName = String.valueOf(firstPlayer.getText());
                String secondPlayerName = String.valueOf(secondPlayer.getText());
                Player playerOne, playerTwo;
                playerOne=new Player(firstPlayerName);
                playerTwo = new Player(secondPlayerName);
                dbHandler.addHandler(playerOne);
                dbHandler.addHandler(playerTwo);
                if (!firstPlayerName.isEmpty() && !secondPlayerName.isEmpty()) {
                    intent.putExtra("firstPlayerName", firstPlayerName)
                            .putExtra("secondPlayerName", secondPlayerName)
                            .putExtra("matchFormatPos", matchFormatPos)
                            .putExtra("lastSetFormatPos", lastSetFormatPos);

                    this.startActivity(intent);
                }
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        EditText firstPlayer = findViewById(R.id.firstPlayerName);
        EditText secondPlayer = findViewById(R.id.secondPlayerName);
        String firstPlayerName = String.valueOf(firstPlayer.getText());
        String secondPlayerName = String.valueOf(secondPlayer.getText());
        savedInstanceState.putString("firstPlayerName", firstPlayerName);
        savedInstanceState.putString("secondPlayerName", secondPlayerName);
    }


}
