package fr.android.tennistracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.DAO.MyDBHandler;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.Model.Set;
import fr.android.tennistracker.Model.Statistics;
import fr.android.tennistracker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private Set setOne, setTwo, setThree;
    private Statistics playerOneSetOne = null, playerTwoSetOne = null, playerOneSetTwo = null, playerTwoSetTwo = null, playerOneSetThree = null, playerTwoSetThree = null;
    private Player playerOne = null;
    private Player playerTwo = null;
    private Intent intent;
    private Match match = null;
    private MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDBHandler = new MyDBHandler(getApplicationContext());
        ListView matchList = findViewById(R.id.matchList);
        List<String> stringList = new ArrayList<>();
        List<Match> matches = myDBHandler.getMatchList();
        for (Match match : matches) {
            stringList.add(match.getFirstPlayer().getName() + " vs " + match.getSecondPlayer().getName());
        }
        ArrayAdapter stringListAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stringList);
        matchList.setAdapter(stringListAdapter);
        stringListAdapter.notifyDataSetChanged();

        matchList.setOnItemClickListener(this::onItemClick);

    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        match = myDBHandler.getMatchById(position+1);
        playerOne = match.getFirstPlayer();
        playerTwo = match.getSecondPlayer();
        playerOne = myDBHandler.getPlayerById(match.getFirstPlayer().getPlayerId());
        playerTwo = myDBHandler.getPlayerById(match.getSecondPlayer().getPlayerId());

        List<Statistics> playerOneStats = myDBHandler.getStatistics(match.getMatchId(), playerOne.getPlayerId());
        for (Statistics statistics : playerOneStats) {
            if (statistics.getSetNumber() == 1) {
                playerOneSetOne = statistics;
            } else if (statistics.getSetNumber() == 2) {
                playerOneSetTwo = statistics;
            } else {
                playerOneSetThree = statistics;
            }
        }
        List<Statistics> playerTwoStats = myDBHandler.getStatistics(match.getMatchId(), playerTwo.getPlayerId());
        for (Statistics statistics : playerTwoStats) {
            if (statistics.getSetNumber() == 1) {
                playerTwoSetOne = statistics;
            } else if (statistics.getSetNumber() == 2) {
                playerTwoSetTwo = statistics;
            } else {
                playerTwoSetThree = statistics;
            }
        }

        setOne = new Set(1);
        setOne.setPlayersStats(Arrays.asList(playerOneSetOne, playerTwoSetOne));
        setOne.setSetScoreFirstPlayer(String.valueOf(playerOneSetOne.getSetScore()));
        setOne.setSetScoreSecondPlayer(String.valueOf(playerTwoSetOne.getSetScore()));

        setTwo = new Set(2);
        setTwo.setPlayersStats(Arrays.asList(playerOneSetTwo, playerTwoSetTwo));
        setTwo.setSetScoreFirstPlayer(String.valueOf(playerOneSetTwo.getSetScore()));
        setTwo.setSetScoreSecondPlayer(String.valueOf(playerTwoSetTwo.getSetScore()));

        if (playerOneSetThree != null) {
            setThree = new Set(3);
            setThree.setPlayersStats(Arrays.asList(playerOneSetThree, playerTwoSetThree));
            setThree.setSetScoreFirstPlayer(String.valueOf(playerOneSetThree.getSetScore()));
            setThree.setSetScoreSecondPlayer(String.valueOf(playerTwoSetThree.getSetScore()));
        }

        intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra("playerOne", playerOne);
        intent.putExtra("playerTwo", playerTwo);
        intent.putExtra("setOne", setOne);
        intent.putExtra("setTwo", setTwo);
        intent.putExtra("setThree", setThree);
        intent.putExtra("match", match);
        intent.putExtra("matchIsDone",true);
        intent.putExtra("origin", "HistoryActivity");

        this.startActivity(intent);

    }
}
