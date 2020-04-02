package fr.android.tennistracker.Activities;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.DAO.MyDBHandler;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.R;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        TableLayout matchList = findViewById(R.id.matchList);

        TableRow matchRow = new TableRow(this);
        matchRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));

        MyDBHandler myDBHandler = new MyDBHandler(getApplicationContext());
        
        List<Match> matches = myDBHandler.getMatchList();
        for(Match match : matches){
            TextView matchLabel = new TextView(this);
            matchLabel.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
            matchLabel.setText(match.getFirstPlayer().getName() + " vs " + match.getSecondPlayer().getName());
            matchRow.addView(matchLabel);
            matchList.addView(matchRow);
        }
        
    }
    

}
