package fr.android.tennistracker.Activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.DAO.MyDBHandler;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView matchList = findViewById(R.id.matchList);

        List<String> stringList = new ArrayList<>();

        MyDBHandler myDBHandler = new MyDBHandler(getApplicationContext());

        List<Match> matches = myDBHandler.getMatchList();

        for (Match match : matches) {
            stringList.add(match.getFirstPlayer().getName() + " vs " + match.getSecondPlayer().getName());
        }

        ArrayAdapter stringListAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stringList);
        matchList.setAdapter(stringListAdapter);
        stringListAdapter.notifyDataSetChanged();


    }


}
