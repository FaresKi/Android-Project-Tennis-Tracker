package fr.android.tennistracker.Activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import fr.android.tennistracker.DAO.DataAPIAccess;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.Model.Set;
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

        setOne = getIntent().getParcelableExtra("setOne");
        setTwo = getIntent().getParcelableExtra("setTwo");
        setThree = getIntent().getParcelableExtra("setThree");
        
        match = getIntent().getParcelableExtra("match");
        
        new APIAccessAsyncTask().execute("");
        
        setStatsLabels();
        setScoreTable();
        initializeValues();
    }
    
    public void initializeValues(){
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

    public void setMatchStatistics() {
        

    }

    @SuppressLint("SetTextI18n")
    public void setSetOneStatistics() {
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

    public void setSetTwoStatistics() {
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

    public void setSetThreeStatistics() {

    }

    public void setScoreTable() {
        tabFirstName = findViewById(R.id.tabFirstName);
        tabSecondName = findViewById(R.id.tabSecondName);

        tabFirstName.setText(playerOne.getName());
        tabSecondName.setText(playerTwo.getName());
        
        firstPlayerSet_1 = findViewById(R.id.firstPlayerSet_1);
        secondPlayerSet_1 = findViewById(R.id.secondPlayerSet_1);

        firstPlayerSet_1.setText(setOne.getSetScoreFirstPlayer());
        secondPlayerSet_1.setText(setOne.getSetScoreSecondPlayer());

        firstPlayerSet_2 = findViewById(R.id.firstPlayerSet_2);
        secondPlayerSet_2 = findViewById(R.id.secondPlayerSet_2);

        firstPlayerSet_2.setText(setTwo.getSetScoreFirstPlayer());
        secondPlayerSet_2.setText(setTwo.getSetScoreSecondPlayer());


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
    
    public void clearText(){
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

    private class APIAccessAsyncTask extends AsyncTask<String, Void, String> {

        String allMatches;
        DataAPIAccess dataAPIAccess = new DataAPIAccess();
        @Override
        protected String doInBackground(String... strings) {
            try {
                /*allMatches = dataAPIAccess.getAllMatches();
                dataAPIAccess.matchList();*/
                dataAPIAccess.sendNewMatch(match);
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