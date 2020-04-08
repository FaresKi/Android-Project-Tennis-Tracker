package fr.android.tennistracker.Activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import fr.android.tennistracker.DAO.DataAPIAccess;
import fr.android.tennistracker.DAO.MyDBHandler;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.Model.Set;
import fr.android.tennistracker.Model.Statistics;
import fr.android.tennistracker.R;

import java.io.IOException;

public class StatisticsActivity extends AppCompatActivity {
    private ToggleButton matchToggleButton, setOneToggleButton, setTwoToggleButton, setThreeToggleButton;
    private Player playerOne, playerTwo;
    private Set setOne, setTwo, setThree;
    private Match match;

    private TextView tabFirstName, firstPlayerSet_1, firstPlayerSet_2,
            firstPlayerSet_3, FPWonRallies, FPFirstServer, FPAces, FPDoubleFaults, FPPointsWonOnFirstService,
            FPWinners, FPUnforcedErrors, FPForcedErrors, firstPlayerStats, firstPlayerServiceStats, firstPlayerRallyStats;

    private TextView tabSecondName, secondPlayerSet_1, secondPlayerSet_2,
            secondPlayerSet_3, SPWonRallies, SPFirstServer, SPAces, SPDoubleFaults, SPPointsWonOnFirstService,
            SPWinners, SPUnforcedErrors, SPForcedErrors, secondPlayerStats, secondPlayerServiceStats, secondPlayerRallyStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        playerOne = getIntent().getParcelableExtra("playerOne");
        playerTwo = getIntent().getParcelableExtra("playerTwo");

        setTitle(playerOne.getName() + " vs " + playerTwo.getName());

        matchToggleButton = findViewById(R.id.matchToggleButton);
        setOneToggleButton = findViewById(R.id.setOneToggleButton);
        setTwoToggleButton = findViewById(R.id.setTwoToggleButton);
        setThreeToggleButton = findViewById(R.id.setThreeToggleButton);

        String origin = getIntent().getStringExtra("origin");

        if (origin != null && origin.equals("imageButton")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            matchToggleButton.setEnabled(false);
        } else if (origin.equals("done")) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            matchToggleButton.setEnabled(true);
        }


        setOne = getIntent().getParcelableExtra("setOne");
        setTwo = getIntent().getParcelableExtra("setTwo");
        setThree = getIntent().getParcelableExtra("setThree");
        
        if( origin!=null && origin.equals("done")){
            int playerOneSetTwo = getIntent().getIntExtra("playerOneSetTwo",-1);
            int playerTwoSetTwo = getIntent().getIntExtra("playerTwoSetTwo",-1);

            setTwo.getPlayersStats().get(0).setStatsId(playerOneSetTwo);
            setTwo.getPlayersStats().get(1).setStatsId(playerTwoSetTwo);
        }
       
        
       
        

        if (setOne == null) {
            setOneToggleButton.setEnabled(false);
        }

        if (setTwo == null) {
            setTwoToggleButton.setEnabled(false);
        }

        if (setThree != null) {
            setThreeToggleButton.setEnabled(true);
            if(origin!=null && origin.equals("done")){
                int playerOneSetThree = getIntent().getIntExtra("playerOneSetThree",-1);
                int playerTwoSetThree = getIntent().getIntExtra("playerTwoSetThree",-1);

                setThree.getPlayersStats().get(0).setStatsId(playerOneSetThree);
                setThree.getPlayersStats().get(1).setStatsId(playerTwoSetThree);
            }
        }

        match = getIntent().getParcelableExtra("match");

        matchToggleButton.setChecked(true);

        boolean matchFinished = getIntent().getExtras().getBoolean("matchIsDone");
        if (matchFinished) {
            new APIAccessAsyncTask().execute("");
        }


        setStatsLabels();
        setScoreTable();
        initializeValues();
    }

    public void initializeValues() {
        FPWonRallies = findViewById(R.id.FPWonRallies);
        FPFirstServer = findViewById(R.id.FPFirstServer);
        FPAces = findViewById(R.id.FPAces);
        FPDoubleFaults = findViewById(R.id.FPDoubleFaults);
        FPPointsWonOnFirstService = findViewById(R.id.FPPointsWonOnFirstService);
        FPWinners = findViewById(R.id.FPWinners);
        FPUnforcedErrors = findViewById(R.id.FPUnforcedErrors);
        FPForcedErrors = findViewById(R.id.FPForcedErrors);

        SPWonRallies = findViewById(R.id.SPWonRallies);
        SPFirstServer = findViewById(R.id.SPFirstServer);
        SPAces = findViewById(R.id.SPAces);
        SPDoubleFaults = findViewById(R.id.SPDoubleFaults);
        SPPointsWonOnFirstService = findViewById(R.id.SPPointsWonOnFirstService);
        SPWinners = findViewById(R.id.SPWinners);
        SPUnforcedErrors = findViewById(R.id.SPUnforcedErrors);
        SPForcedErrors = findViewById(R.id.SPForcedErrors);
    }

    public void isChecked(View view) {
        switch (view.getId()) {
            case R.id.matchToggleButton:
                setOneToggleButton.setChecked(false);
                setTwoToggleButton.setChecked(false);
                setThreeToggleButton.setChecked(false);
                clearText();
                setMatchStatistics();
                break;
            case R.id.setOneToggleButton:
                matchToggleButton.setChecked(false);
                setTwoToggleButton.setChecked(false);
                setThreeToggleButton.setChecked(false);
                clearText();
                setSetOneStatistics();
                break;
            case R.id.setTwoToggleButton:
                matchToggleButton.setChecked(false);
                setOneToggleButton.setChecked(false);
                setThreeToggleButton.setChecked(false);
                clearText();
                setSetTwoStatistics();
                break;
            case R.id.setThreeToggleButton:
                matchToggleButton.setChecked(false);
                setOneToggleButton.setChecked(false);
                setTwoToggleButton.setChecked(false);
                clearText();
                setSetThreeStatistics();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setMatchStatistics() {
        if (setThree == null) {
            FPWonRallies.setText(String.valueOf(setOne.getPlayersStats().get(0).getUnforcedErrors() + setTwo.getPlayersStats().get(0).getUnforcedErrors()));
            FPFirstServer.setText(String.valueOf(setOne.getPlayersStats().get(0).getFirstServes() + setTwo.getPlayersStats().get(0).getFirstServes()));
            FPAces.setText(String.valueOf(setOne.getPlayersStats().get(0).getAces() + setTwo.getPlayersStats().get(0).getAces()));
            FPDoubleFaults.setText(String.valueOf(setOne.getPlayersStats().get(0).getDoubleFaults() + setTwo.getPlayersStats().get(0).getDoubleFaults()));
            FPPointsWonOnFirstService.setText(String.valueOf(setOne.getPlayersStats().get(0).getPointsWonOnFirstServe() + setTwo.getPlayersStats().get(0).getPointsWonOnFirstServe()));
            FPWinners.setText(FPWonRallies.getText());
            FPUnforcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(0).getUnforcedErrors() + setTwo.getPlayersStats().get(0).getUnforcedErrors()));
            FPForcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(0).getForcedErrors() + setTwo.getPlayersStats().get(0).getForcedErrors()));

            SPWonRallies.setText(String.valueOf(setOne.getPlayersStats().get(1).getUnforcedErrors() + setTwo.getPlayersStats().get(1).getUnforcedErrors()));
            SPFirstServer.setText(String.valueOf(setOne.getPlayersStats().get(1).getFirstServes() + setTwo.getPlayersStats().get(1).getFirstServes()));
            SPAces.setText(String.valueOf(setOne.getPlayersStats().get(1).getAces() + setTwo.getPlayersStats().get(1).getAces()));
            SPDoubleFaults.setText(String.valueOf(setOne.getPlayersStats().get(1).getDoubleFaults() + setTwo.getPlayersStats().get(1).getDoubleFaults()));
            SPPointsWonOnFirstService.setText(String.valueOf(setOne.getPlayersStats().get(1).getPointsWonOnFirstServe() + setTwo.getPlayersStats().get(1).getPointsWonOnFirstServe()));
            SPWinners.setText(SPWonRallies.getText());
            SPUnforcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(1).getUnforcedErrors() + setTwo.getPlayersStats().get(1).getUnforcedErrors()));
            SPForcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(1).getForcedErrors() + setTwo.getPlayersStats().get(1).getForcedErrors()));
        } else {
            FPWonRallies.setText(String.valueOf(setOne.getPlayersStats().get(0).getUnforcedErrors() + setTwo.getPlayersStats().get(0).getUnforcedErrors() + setThree.getPlayersStats().get(0).getUnforcedErrors()));
            FPFirstServer.setText(String.valueOf(setOne.getPlayersStats().get(0).getFirstServes() + setTwo.getPlayersStats().get(0).getFirstServes() + setThree.getPlayersStats().get(0).getFirstServes()));
            FPAces.setText(String.valueOf(setOne.getPlayersStats().get(0).getAces() + setTwo.getPlayersStats().get(0).getAces() + setThree.getPlayersStats().get(0).getAces()));
            FPDoubleFaults.setText(String.valueOf(setOne.getPlayersStats().get(0).getDoubleFaults() + setTwo.getPlayersStats().get(0).getDoubleFaults() + setThree.getPlayersStats().get(0).getDoubleFaults()));
            FPPointsWonOnFirstService.setText(String.valueOf(setOne.getPlayersStats().get(0).getPointsWonOnFirstServe() + setTwo.getPlayersStats().get(0).getPointsWonOnFirstServe() + setThree.getPlayersStats().get(0).getPointsWonOnFirstServe()));
            FPWinners.setText(FPWonRallies.getText());
            FPUnforcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(0).getUnforcedErrors() + setTwo.getPlayersStats().get(0).getUnforcedErrors() + setThree.getPlayersStats().get(0).getUnforcedErrors()));
            FPForcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(0).getForcedErrors() + setTwo.getPlayersStats().get(0).getForcedErrors() + setThree.getPlayersStats().get(0).getForcedErrors()));

            SPWonRallies.setText(String.valueOf(setOne.getPlayersStats().get(1).getUnforcedErrors() + setTwo.getPlayersStats().get(1).getUnforcedErrors() + setThree.getPlayersStats().get(1).getUnforcedErrors()));
            SPFirstServer.setText(String.valueOf(setOne.getPlayersStats().get(1).getFirstServes() + setTwo.getPlayersStats().get(1).getFirstServes() + setThree.getPlayersStats().get(1).getFirstServes()));
            SPAces.setText(String.valueOf(setOne.getPlayersStats().get(1).getAces() + setTwo.getPlayersStats().get(1).getAces() + setThree.getPlayersStats().get(1).getAces()));
            SPDoubleFaults.setText(String.valueOf(setOne.getPlayersStats().get(1).getDoubleFaults() + setTwo.getPlayersStats().get(1).getDoubleFaults() + setThree.getPlayersStats().get(1).getDoubleFaults()));
            SPPointsWonOnFirstService.setText(String.valueOf(setOne.getPlayersStats().get(1).getPointsWonOnFirstServe() + setTwo.getPlayersStats().get(1).getPointsWonOnFirstServe() + setThree.getPlayersStats().get(1).getPointsWonOnFirstServe()));
            SPWinners.setText(SPWonRallies.getText());
            SPUnforcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(1).getUnforcedErrors() + setTwo.getPlayersStats().get(1).getUnforcedErrors() + setThree.getPlayersStats().get(1).getUnforcedErrors()));
            SPForcedErrors.setText(String.valueOf(setOne.getPlayersStats().get(1).getForcedErrors() + setTwo.getPlayersStats().get(1).getForcedErrors() + setThree.getPlayersStats().get(1).getForcedErrors()));

        }


    }

    @SuppressLint("SetTextI18n")
    public void setSetOneStatistics() {
        if (setOne != null) {
            FPWonRallies.setText(Integer.toString(setOne.getPlayersStats().get(0).getUnforcedErrors()));
            FPFirstServer.setText(Integer.toString(setOne.getPlayersStats().get(0).getFirstServes()));
            FPAces.setText(Integer.toString(setOne.getPlayersStats().get(0).getAces()));
            FPDoubleFaults.setText(Integer.toString(setOne.getPlayersStats().get(0).getDoubleFaults()));
            FPPointsWonOnFirstService.setText(Integer.toString(setOne.getPlayersStats().get(0).getPointsWonOnFirstServe()));
            FPWinners.setText(FPWonRallies.getText());
            FPUnforcedErrors.setText(Integer.toString(setOne.getPlayersStats().get(0).getUnforcedErrors()));
            FPForcedErrors.setText(Integer.toString(setOne.getPlayersStats().get(0).getForcedErrors()));

            SPWonRallies.setText(Integer.toString(setOne.getPlayersStats().get(1).getUnforcedErrors()));
            SPFirstServer.setText(Integer.toString(setOne.getPlayersStats().get(1).getFirstServes()));
            SPAces.setText(Integer.toString(setOne.getPlayersStats().get(1).getAces()));
            SPDoubleFaults.setText(Integer.toString(setOne.getPlayersStats().get(1).getDoubleFaults()));
            SPPointsWonOnFirstService.setText(Integer.toString(setOne.getPlayersStats().get(1).getPointsWonOnFirstServe()));
            SPWinners.setText(SPWonRallies.getText());
            SPUnforcedErrors.setText(Integer.toString(setOne.getPlayersStats().get(1).getUnforcedErrors()));
            SPForcedErrors.setText(Integer.toString(setOne.getPlayersStats().get(1).getForcedErrors()));
        }

    }

    @SuppressLint("SetTextI18n")
    public void setSetTwoStatistics() {
        if (setTwo != null) {

            FPWonRallies.setText(Integer.toString(setTwo.getPlayersStats().get(0).getUnforcedErrors()));
            FPFirstServer.setText(Integer.toString(setTwo.getPlayersStats().get(0).getFirstServes()));
            FPAces.setText(Integer.toString(setTwo.getPlayersStats().get(0).getAces()));
            FPDoubleFaults.setText(Integer.toString(setTwo.getPlayersStats().get(0).getDoubleFaults()));
            FPPointsWonOnFirstService.setText(Integer.toString(setTwo.getPlayersStats().get(0).getPointsWonOnFirstServe()));
            FPWinners.setText(FPWonRallies.getText());
            FPUnforcedErrors.setText(Integer.toString(setTwo.getPlayersStats().get(0).getUnforcedErrors()));
            FPForcedErrors.setText(Integer.toString(setTwo.getPlayersStats().get(0).getForcedErrors()));

            SPWonRallies.setText(Integer.toString(setTwo.getPlayersStats().get(1).getUnforcedErrors()));
            SPFirstServer.setText(Integer.toString(setTwo.getPlayersStats().get(1).getFirstServes()));
            SPAces.setText(Integer.toString(setTwo.getPlayersStats().get(1).getAces()));
            SPDoubleFaults.setText(Integer.toString(setTwo.getPlayersStats().get(1).getDoubleFaults()));
            SPPointsWonOnFirstService.setText(Integer.toString(setTwo.getPlayersStats().get(1).getPointsWonOnFirstServe()));
            SPWinners.setText(SPWonRallies.getText());
            SPUnforcedErrors.setText(Integer.toString(setTwo.getPlayersStats().get(1).getUnforcedErrors()));
            SPForcedErrors.setText(Integer.toString(setTwo.getPlayersStats().get(1).getForcedErrors()));
        }
    }

    @SuppressLint("SetTextI18n")
    public void setSetThreeStatistics() {
        if (setThree != null) {
            FPWonRallies.setText(Integer.toString(setThree.getPlayersStats().get(0).getUnforcedErrors()));
            FPFirstServer.setText(Integer.toString(setThree.getPlayersStats().get(0).getFirstServes()));
            FPAces.setText(Integer.toString(setThree.getPlayersStats().get(0).getAces()));
            FPDoubleFaults.setText(Integer.toString(setThree.getPlayersStats().get(0).getDoubleFaults()));
            FPPointsWonOnFirstService.setText(Integer.toString(setThree.getPlayersStats().get(0).getPointsWonOnFirstServe()));
            FPWinners.setText(FPWonRallies.getText());
            FPUnforcedErrors.setText(Integer.toString(setThree.getPlayersStats().get(0).getUnforcedErrors()));
            FPForcedErrors.setText(Integer.toString(setThree.getPlayersStats().get(0).getForcedErrors()));

            SPWonRallies.setText(Integer.toString(setThree.getPlayersStats().get(1).getUnforcedErrors()));
            SPFirstServer.setText(Integer.toString(setThree.getPlayersStats().get(1).getFirstServes()));
            SPAces.setText(Integer.toString(setThree.getPlayersStats().get(1).getAces()));
            SPDoubleFaults.setText(Integer.toString(setThree.getPlayersStats().get(1).getDoubleFaults()));
            SPPointsWonOnFirstService.setText(Integer.toString(setThree.getPlayersStats().get(1).getPointsWonOnFirstServe()));
            SPWinners.setText(SPWonRallies.getText());
            SPUnforcedErrors.setText(Integer.toString(setThree.getPlayersStats().get(1).getUnforcedErrors()));
            SPForcedErrors.setText(Integer.toString(setThree.getPlayersStats().get(1).getForcedErrors()));
        }
    }

    public void setScoreTable() {
        tabFirstName = findViewById(R.id.tabFirstName);
        tabSecondName = findViewById(R.id.tabSecondName);

        tabFirstName.setText(playerOne.getName());
        tabSecondName.setText(playerTwo.getName());

        firstPlayerSet_1 = findViewById(R.id.firstPlayerSet_1);
        secondPlayerSet_1 = findViewById(R.id.secondPlayerSet_1);

        if (setOne != null) {
            firstPlayerSet_1.setText(setOne.getSetScoreFirstPlayer());
            secondPlayerSet_1.setText(setOne.getSetScoreSecondPlayer());
        }

        firstPlayerSet_2 = findViewById(R.id.firstPlayerSet_2);
        secondPlayerSet_2 = findViewById(R.id.secondPlayerSet_2);

        if (setTwo != null) {
            firstPlayerSet_2.setText(setTwo.getSetScoreFirstPlayer());
            secondPlayerSet_2.setText(setTwo.getSetScoreSecondPlayer());
        }

        firstPlayerSet_3 = findViewById(R.id.firstPlayerSet_3);
        secondPlayerSet_3 = findViewById(R.id.secondPlayerSet_3);

        if (setThree != null) {
            firstPlayerSet_3.setText(setThree.getSetScoreFirstPlayer());
            secondPlayerSet_3.setText(setThree.getSetScoreSecondPlayer());
        }


    }

    public void setStatsLabels() {

        firstPlayerStats = findViewById(R.id.firstPlayerStats);
        secondPlayerStats = findViewById(R.id.secondPlayerStats);

        firstPlayerStats.setText(playerOne.getName());
        secondPlayerStats.setText(playerTwo.getName());

        firstPlayerServiceStats = findViewById(R.id.firstPlayerServiceStats);
        secondPlayerServiceStats = findViewById(R.id.secondPlayerServiceStats);

        firstPlayerServiceStats.setText(playerOne.getName());
        secondPlayerServiceStats.setText(playerTwo.getName());

        firstPlayerRallyStats = findViewById(R.id.firstPlayerRallyStats);
        secondPlayerRallyStats = findViewById(R.id.secondPlayerRallyStats);

        firstPlayerRallyStats.setText(playerOne.getName());
        secondPlayerRallyStats.setText(playerTwo.getName());

    }

    public void clearText() {
        FPWonRallies.setText("");
        FPFirstServer.setText("");
        FPAces.setText("");
        FPDoubleFaults.setText("");
        FPPointsWonOnFirstService.setText("");
        FPWinners.setText(FPWonRallies.getText());
        FPUnforcedErrors.setText("");
        FPForcedErrors.setText("");

        SPWonRallies.setText("");
        SPFirstServer.setText("");
        SPAces.setText("");
        SPDoubleFaults.setText("");
        SPPointsWonOnFirstService.setText("");
        SPWinners.setText(SPWonRallies.getText());
        SPUnforcedErrors.setText("");
        SPForcedErrors.setText("");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_statistics, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String origin = getIntent().getStringExtra("origin");
        switch (item.getItemId()) {
            case android.R.id.home:
                if ((origin != null && origin.equals("imageButton"))  || (origin.equals("HistoryActivity") )){
                    finish();
                    return true;
                } else if ((origin.equals("done") )) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }

        }
        return true;
    }


    private class APIAccessAsyncTask extends AsyncTask<String, Void, String> {

        String allMatches;
        DataAPIAccess dataAPIAccess = new DataAPIAccess();
        MyDBHandler myDBHandler = new MyDBHandler(getApplicationContext());

        @Override
        protected String doInBackground(String... strings) {
            try {
                /*allMatches = dataAPIAccess.getAllMatches();
                dataAPIAccess.matchList();*/

                dataAPIAccess.sendNewGame(match);


                myDBHandler.onCreate(myDBHandler.getWritableDatabase());

                dataAPIAccess.sendNewPlayer(playerOne);
                myDBHandler.addPlayer(playerOne);
                dataAPIAccess.sendNewPlayer(playerTwo);
                myDBHandler.addPlayer(playerTwo);
                dataAPIAccess.sendNewGame(match);


                myDBHandler.addGame(match);


                Statistics playerOneSetOneStats = setOne.getPlayersStats().get(0);
                Statistics playerTwoSetOneStats = setOne.getPlayersStats().get(1);

                Statistics playerOneSetTwoStats = setTwo.getPlayersStats().get(0);
                Statistics playerTwoSetTwoStats = setTwo.getPlayersStats().get(1);

                playerOneSetOneStats.setSetNumber(1);
                playerOneSetOneStats.setPlayerId(playerOne.getPlayerId());

                playerTwoSetOneStats.setSetNumber(1);
                playerTwoSetOneStats.setPlayerId(playerTwo.getPlayerId());

                playerOneSetTwoStats.setSetNumber(2);
                playerOneSetTwoStats.setPlayerId(playerOne.getPlayerId());

                playerTwoSetTwoStats.setSetNumber(2);
                playerTwoSetTwoStats.setPlayerId(playerTwo.getPlayerId());


                playerOneSetOneStats.setMatchId(match.getMatchId());
                playerTwoSetOneStats.setMatchId(match.getMatchId());

                playerOneSetTwoStats.setMatchId(match.getMatchId());
                playerTwoSetTwoStats.setMatchId(match.getMatchId());


                dataAPIAccess.sendNewStats(playerOneSetOneStats);
                myDBHandler.addStats(playerOneSetOneStats);

                dataAPIAccess.sendNewStats(playerTwoSetOneStats);
                myDBHandler.addStats(playerTwoSetOneStats);

                dataAPIAccess.sendNewStats(playerOneSetTwoStats);
                myDBHandler.addStats(playerOneSetTwoStats);

                dataAPIAccess.sendNewStats(playerTwoSetTwoStats);
                myDBHandler.addStats(playerTwoSetTwoStats);


                if (setThree != null) {
                    Statistics playerOneSetThreeStats = setThree.getPlayersStats().get(0);
                    Statistics playerTwoSetThreeStats = setThree.getPlayersStats().get(1);

                    playerOneSetThreeStats.setSetNumber(3);
                    playerTwoSetThreeStats.setPlayerId(playerOne.getPlayerId());

                    playerOneSetThreeStats.setSetNumber(3);
                    playerTwoSetThreeStats.setPlayerId(playerTwo.getPlayerId());

                    playerOneSetThreeStats.setMatchId(match.getMatchId());
                    playerTwoSetThreeStats.setMatchId(match.getMatchId());

                    dataAPIAccess.sendNewStats(playerOneSetThreeStats);
                    myDBHandler.addStats(playerOneSetThreeStats);

                    dataAPIAccess.sendNewStats(playerTwoSetThreeStats);
                    myDBHandler.addStats(playerTwoSetThreeStats);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return allMatches;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
}