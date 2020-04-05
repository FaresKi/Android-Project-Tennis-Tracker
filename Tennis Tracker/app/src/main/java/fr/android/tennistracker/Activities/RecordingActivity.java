package fr.android.tennistracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import fr.android.tennistracker.Fragments.*;
import fr.android.tennistracker.Model.Match;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.Model.Set;
import fr.android.tennistracker.Model.Statistics;
import fr.android.tennistracker.R;

import java.util.*;

public class RecordingActivity extends AppCompatActivity implements DoneMatchDialog.DoneMatchDialogListener, FirstServerDialog.FirstServerDialogListener,
        LeaveRecordingDialog.LeaveRecordingDialogListener, GameNotOverDialog.GameNotOverDialogListener, UpdateServerDialog.UpdateServerListener {

    private Intent intent;
    private FirstServerDialog firstServerDialog;
    private LeaveRecordingDialog leaveRecordingDialog;
    private DoneMatchDialog doneMatchDialog;
    private UpdateServerDialog updateServerDialog;

    private TextView firstPlayerSet_1, firstPlayerSet_2, firstPlayerSet_3, firstPlayerScore, tabFirstName, tabSecondName;
    private TextView secondPlayerSet_1, secondPlayerSet_2, secondPlayerSet_3, secondPlayerScore;
    private TextView server;

    private Set setOne, setTwo, setThree;

    private Match match;

    private Player playerOne;
    private Player playerTwo;
    private Player winner;


    private Statistics firstPlayerStats;
    private Statistics secondPlayerStats;

    private boolean advantage = true;
    private boolean firstServeFP = false;
    private boolean firstServeSP = false;
    private boolean tieBreak = false;
    private boolean FPHadAdvantage = false;
    private boolean SPHadAdvantage = false;

    private int nbGames;
    private int pointsTieBreak;
    private int lastSetFormat;
    private int serverTB = 0;
    private int currentSet = 1;

    private List<Integer> currentPlayer = new ArrayList<>();

    private boolean matchFinished = false;


    private GameNotOverDialog gameNotOverDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        if (savedInstanceState == null) {
            int matchFormat = getIntent().getIntExtra("matchFormatPos", 0);

            switch (matchFormat) {
                case 0:
                    advantage = true;
                    nbGames = 6;
                    pointsTieBreak = 6;
                    break;
                case 1:
                    advantage = true;
                    nbGames = 5;
                    pointsTieBreak = 5;
                    break;
                case 2:
                    advantage = true;
                    nbGames = 4;
                    pointsTieBreak = 4;
                    break;

                case 3:
                    advantage = false;
                    nbGames = 5;
                    pointsTieBreak = 4;

                    break;
                case 4:
                    advantage = false;
                    nbGames = 4;
                    pointsTieBreak = 3;

                    break;
                case 5:
                    advantage = false;
                    nbGames = 3;
                    pointsTieBreak = 2;
                    break;

            }
            lastSetFormat = getIntent().getIntExtra("lastSetFormatPos", 0);


            // Initialize first player
            String playerOneName = getIntent().getStringExtra("firstPlayerName");
            tabFirstName = findViewById(R.id.tabFirstName);
            playerOne = initializePlayer(playerOneName, tabFirstName);
            firstPlayerStats = playerOne.getPlayerStats();

            // Initialize second player
            String playerTwoName = getIntent().getStringExtra("secondPlayerName");
            tabSecondName = findViewById(R.id.tabSecondName);
            playerTwo = initializePlayer(playerTwoName, tabSecondName);
            secondPlayerStats = playerTwo.getPlayerStats();

            match = new Match(playerOne, playerTwo);

            initializeElements();
        } else {
            //recover content from savedInstance
            firstPlayerSet_1 = findViewById(R.id.firstPlayerSet_1);
            firstPlayerSet_2 = findViewById(R.id.firstPlayerSet_2);
            firstPlayerSet_3 = findViewById(R.id.firstPlayerSet_3);
            firstPlayerScore = findViewById(R.id.tabFirstPlayerScore);

            secondPlayerSet_1 = findViewById(R.id.secondPlayerSet_1);
            secondPlayerSet_2 = findViewById(R.id.secondPlayerSet_2);
            secondPlayerSet_3 = findViewById(R.id.secondPlayerSet_3);
            secondPlayerScore = findViewById(R.id.tabSecondPlayerScore);

            firstPlayerScore.setText(savedInstanceState.getString("firstPlayerScore"));
            secondPlayerScore.setText(savedInstanceState.getString("secondPlayerStore"));

            lastSetFormat = savedInstanceState.getInt("lastSetFormat");

            playerOne = savedInstanceState.getParcelable("playerOne");
            playerTwo = savedInstanceState.getParcelable("playerTwo");


            //first set

            if (savedInstanceState.getParcelable("setOne") != null) {
                setOne = savedInstanceState.getParcelable("setOne");
            }

            if (savedInstanceState.getString("firstPlayerSet_1") != null) {
                firstPlayerSet_1.setText(savedInstanceState.getString("firstPlayerSet_1"));
            }
            if (savedInstanceState.getString("secondPlayerSet_1") != null) {
                secondPlayerSet_1.setText(savedInstanceState.getString("secondPlayerSet_1"));
            }

            //second set

            if (savedInstanceState.getParcelable("setTwo") != null) {
                setTwo = savedInstanceState.getParcelable("setTwo");
            }

            if (savedInstanceState.getString("firstPlayerSet_2") != null) {
                firstPlayerSet_2.setText(savedInstanceState.getString("firstPlayerSet_2"));
            }
            if (savedInstanceState.getString("secondPlayerSet_2") != null) {
                secondPlayerSet_2.setText(savedInstanceState.getString("secondPlayerSet_2"));
            }

            //third set

            if (savedInstanceState.getParcelable("setThree") != null) {
                setThree = savedInstanceState.getParcelable("setThree");
            }

            if (savedInstanceState.getString("firstPlayerSet_3") != null) {
                firstPlayerSet_3.setText(savedInstanceState.getString("firstPlayerSet_3"));
            }
            if (savedInstanceState.getString("secondPlayerSet_3") != null) {
                secondPlayerSet_3.setText(savedInstanceState.getString("secondPlayerSet_3"));
            }


        }


    }

    public void initializeElements() {
        getSupportActionBar().setTitle(R.string.recordingActivityTitle);
        server = findViewById(R.id.serverName);
        firstServerDialog = new FirstServerDialog();
        updateServerDialog = new UpdateServerDialog();
        firstServerDialog.show(getSupportFragmentManager(), "test dialog");

        firstPlayerSet_1 = findViewById(R.id.firstPlayerSet_1);
        firstPlayerSet_2 = findViewById(R.id.firstPlayerSet_2);
        firstPlayerSet_3 = findViewById(R.id.firstPlayerSet_3);
        firstPlayerScore = findViewById(R.id.tabFirstPlayerScore);

        secondPlayerSet_1 = findViewById(R.id.secondPlayerSet_1);
        secondPlayerSet_2 = findViewById(R.id.secondPlayerSet_2);
        secondPlayerSet_3 = findViewById(R.id.secondPlayerSet_3);
        secondPlayerScore = findViewById(R.id.tabSecondPlayerScore);

        firstPlayerSet_1.setText("0");
        secondPlayerSet_1.setText("0");
    }


    public Player initializePlayer(String name, TextView tabName) {
        Player player = new Player(name);
        tabName.setText(name);
        return player;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_recording_activity, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);
        switch (item.getItemId()) {
            case R.id.cancelRecordingItem:
                leaveRecordingDialog = new LeaveRecordingDialog();
                leaveRecordingDialog.show(getSupportFragmentManager(), "leaving dialog");
                break;
            case R.id.givePointToFPItem:
                scoringPlayer(playerOne, firstPlayerScore, firstPlayerSet, secondPlayerSet);
                break;
            case R.id.givePointToSPItem:
                scoringPlayer(playerTwo, secondPlayerScore, secondPlayerSet, firstPlayerSet);
                break;
            case R.id.finishButton:
                gameNotOverDialog = new GameNotOverDialog();
                gameNotOverDialog.show(getSupportFragmentManager(), "game not over");
                break;
        }
        return true;
    }

    @Override
    public void redirectToStats() {
        intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra("playerOne", playerOne);
        intent.putExtra("playerTwo", playerTwo);
        intent.putExtra("setOne", setOne);
        intent.putExtra("setTwo", setTwo);
        intent.putExtra("setThree", setThree);
        intent.putExtra("match", match);
        intent.putExtra("matchIsDone", matchFinished);
        intent.putExtra("origin", "done");
        this.startActivity(intent);
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public void sendPlayerName(View view) {
        Button clickedButton = view.findViewById(view.getId());
        String playerName = clickedButton.getText().toString();
        firstServerDialog.dismiss();
        server.setText(playerName);
    }


    @Override
    public void setPlayersName(View view) {
        String firstName = getIntent().getStringExtra("firstPlayerName");
        String secondName = getIntent().getStringExtra("secondPlayerName");

        Button firstPlayer = view.findViewById(R.id.buttonFirstPlayerDialog);
        Button secondPlayer = view.findViewById(R.id.buttonSecondPlayerDialog);
        firstPlayer.setText(firstName);
        secondPlayer.setText(secondName);
    }

    @Override
    public void goBackToNewMatch() {
        this.finish();
    }

    @Override
    public void stayInThisActivity() {
        leaveRecordingDialog.dismiss();
    }

    @Override
    public void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public List<TextView> selectSet() {
        List<TextView> sets = new ArrayList<>();

        switch (currentSet) {
            case 1:
                setOne = new Set(currentSet);
                sets.add(firstPlayerSet_1);
                sets.add(secondPlayerSet_1);
                break;
            case 2:
                setTwo = new Set(currentSet);
                sets.add(firstPlayerSet_2);
                sets.add(secondPlayerSet_2);
                break;
            case 3:
                setThree = new Set(currentSet);
                sets.add(firstPlayerSet_3);
                sets.add(secondPlayerSet_3);
                break;
        }
        return sets;
    }

    public void onRallyClick(View view) {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);

        switch (view.getId()) {
            case R.id.buttonWinnerFP:
                scoringPlayer(playerOne, firstPlayerScore, firstPlayerSet, secondPlayerSet);
                currentPlayer.add(1);
                firstPlayerStats.setWinners(firstPlayerStats.getWinners() + 1);
                if (firstServeFP) {
                    if (firstPlayerStats.getPointsWonOnFirstServe() != 30) {
                        firstPlayerStats.setPointsWonOnFirstServe(firstPlayerStats.getPointsWonOnFirstServe() + 15);
                    } else {
                        firstPlayerStats.setPointsWonOnFirstServe(firstPlayerStats.getPointsWonOnFirstServe() + 10);
                    }
                }
                break;

            case R.id.buttonWinnerSP:
                currentPlayer.add(2);
                scoringPlayer(playerTwo, secondPlayerScore, secondPlayerSet, firstPlayerSet);
                secondPlayerStats.setWinners(secondPlayerStats.getWinners() + 1);
                if (firstServeSP) {
                    if (secondPlayerStats.getPointsWonOnFirstServe() != 30) {
                        secondPlayerStats.setPointsWonOnFirstServe(secondPlayerStats.getPointsWonOnFirstServe() + 15);
                    } else {
                        secondPlayerStats.setPointsWonOnFirstServe(secondPlayerStats.getPointsWonOnFirstServe() + 10);
                    }
                }
                break;

            case R.id.buttonUnforcedErrorFP:
                scoringPlayer(playerTwo, secondPlayerScore, secondPlayerSet, firstPlayerSet);
                firstPlayerStats.setUnforcedErrors(firstPlayerStats.getUnforcedErrors() + 1);
                currentPlayer.add(2);
                break;

            case R.id.buttonUnforcedErrorSP:
                scoringPlayer(playerOne, firstPlayerScore, firstPlayerSet, secondPlayerSet);
                secondPlayerStats.setUnforcedErrors(secondPlayerStats.getUnforcedErrors() + 1);
                currentPlayer.add(1);
                break;

            case R.id.buttonForcedErrorFP:
                scoringPlayer(playerTwo, secondPlayerScore, secondPlayerSet, firstPlayerSet);
                firstPlayerStats.setForcedErrors(firstPlayerStats.getForcedErrors() + 1);
                currentPlayer.add(2);
                break;

            case R.id.buttonForcedErrorSP:
                scoringPlayer(playerOne, firstPlayerScore, firstPlayerSet, secondPlayerSet);
                secondPlayerStats.setForcedErrors(secondPlayerStats.getForcedErrors() + 1);
                currentPlayer.add(1);
                break;

        }
    }

    public void onAceClick(View view) {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);
        if (server.getText().equals(playerOne.getName())) {
            scoringPlayer(playerOne, firstPlayerScore, firstPlayerSet, secondPlayerSet);
            firstPlayerStats.setAces(firstPlayerStats.getAces() + 1);
            currentPlayer.add(1);

        } else {
            scoringPlayer(playerTwo, secondPlayerScore, secondPlayerSet, firstPlayerSet);
            secondPlayerStats.setAces(secondPlayerStats.getAces() + 1);
            currentPlayer.add(2);
        }
    }

    public void onDoubleFaultClick(View view) {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);
        if (server.getText().equals(playerOne.getName())) {
            scoringPlayer(playerTwo, secondPlayerScore, secondPlayerSet, firstPlayerSet);
            firstPlayerStats.setDoubleFaults(firstPlayerStats.getDoubleFaults() + 1);
            currentPlayer.add(2);
        } else {
            scoringPlayer(playerOne, firstPlayerScore, firstPlayerSet, secondPlayerSet);
            secondPlayerStats.setDoubleFaults(secondPlayerStats.getDoubleFaults() + 1);
            currentPlayer.add(1);

        }
    }

    private void changeServer() {
        if (server.getText().equals(playerOne.getName())) {
            server.setText(playerTwo.getName());
        } else {
            server.setText(playerOne.getName());
        }
    }

    private void winGame(Player player, TextView playerSet) {
        playerSet.setText(String.valueOf(Integer.parseInt(playerSet.getText().toString()) + 1));
        firstPlayerScore.setText("0");
        secondPlayerScore.setText("0");
        changeServer();
        setDone(player);
    }

    private void setDone(Player player) {
        if (setIsDone()) {
            player.winSet();
            matchIsDone();
            if (!matchFinished){
                changeSet();
            }
        }
    }

    private void changeSet() {
        if (currentSet == 1) {
            setOne = new Set(1);
            firstPlayerStats.setSetNumber(setOne.getSetNumber());
            secondPlayerStats.setSetNumber(setOne.getSetNumber());
            setOne.setPlayersStats(Arrays.asList(firstPlayerStats, secondPlayerStats));
            setOne.setScoreFP((String) firstPlayerScore.getText());
            setOne.setScoreSP((String) secondPlayerScore.getText());
            setOne.setSetScoreFirstPlayer((String) firstPlayerSet_1.getText());
            setOne.setSetScoreSecondPlayer((String) secondPlayerSet_1.getText());
            playerOne.reinitialiseStats();
            playerTwo.reinitialiseStats();

            firstPlayerStats = playerOne.getPlayerStats();
            secondPlayerStats = playerTwo.getPlayerStats();
        }
        currentSet++;
        switch (currentSet) {
            case 2:
                setTwo = new Set(2);
                firstPlayerSet_2.setText("0");
                secondPlayerSet_2.setText("0");
                break;
            case 3:
                firstPlayerSet_3.setText("0");
                secondPlayerSet_3.setText("0");
                firstPlayerStats.setSetNumber(setTwo.getSetNumber());
                secondPlayerStats.setSetNumber(setTwo.getSetNumber());
                setTwo.setPlayersStats(Arrays.asList(firstPlayerStats, secondPlayerStats));
                setTwo.setSetScoreFirstPlayer((String) firstPlayerSet_2.getText());
                setTwo.setSetScoreSecondPlayer((String) secondPlayerSet_2.getText());
                setTwo.setScoreFP((String) firstPlayerScore.getText());
                setTwo.setScoreSP((String) secondPlayerScore.getText());
                break;
            default:
                playerOne.reinitialiseStats();
                playerTwo.reinitialiseStats();
                firstPlayerStats = playerOne.getPlayerStats();
                secondPlayerStats = playerTwo.getPlayerStats();
                firstPlayerStats.setSetNumber(setThree.getSetNumber());
                secondPlayerStats.setSetNumber(setThree.getSetNumber());
                setThree.setPlayersStats(Arrays.asList(firstPlayerStats, secondPlayerStats));
                setThree.setSetScoreFirstPlayer((String) firstPlayerSet_3.getText());
                setThree.setSetScoreSecondPlayer((String) secondPlayerSet_3.getText());
                setThree.setScoreFP((String) tabFirstName.getText());
                setThree.setScoreSP((String) tabSecondName.getText());
                break;
                
        }
    }

    public boolean setIsDone() {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);
        int gamesPlayerOne = Integer.parseInt((String) firstPlayerSet.getText());
        int gamesPlayerTwo = Integer.parseInt((String) secondPlayerSet.getText());

        if (advantage) {
            return ((gamesPlayerOne == nbGames && gamesPlayerTwo < nbGames - 1) || (gamesPlayerOne < nbGames - 1 && gamesPlayerTwo == nbGames) ||
                    (gamesPlayerOne == nbGames - 1 && gamesPlayerTwo == nbGames + 1) || (gamesPlayerOne == nbGames + 1 && gamesPlayerTwo == nbGames - 1) ||
                    (gamesPlayerOne == nbGames && gamesPlayerTwo == nbGames + 1) || (gamesPlayerOne == nbGames + 1 && gamesPlayerTwo == nbGames));
        } else {
            return ((gamesPlayerOne == nbGames || gamesPlayerTwo == nbGames));
        }

    }

    public void matchIsDone() {
        if (playerOne.getSet() == 2 || playerTwo.getSet() == 2) {
            if (playerOne.getSet() == 2) {
                winner = playerOne;
            } else {
                winner = playerTwo;
            }
            matchFinished = true;
            doneMatchDialog = new DoneMatchDialog();
            doneMatchDialog.show(getSupportFragmentManager(), "doneMatch");

        }
    }

    private void scoringWithoutAdvantage(Player player, TextView playerScore, TextView playerSet) {

        switch (playerScore.getText().toString()) {
            case "0":
                playerScore.setText("15");
                break;
            case "15":
                playerScore.setText("30");
                break;
            case "30":
                playerScore.setText("40");
                break;
            case "40":
                winGame(player, playerSet);
                break;
        }
    }

    private void scoringWithAdvantage(Player player, TextView playerScore, TextView playerSet) {
        switch (playerScore.getText().toString()) {
            case "0":
                playerScore.setText("15");
                break;
            case "15":
                playerScore.setText("30");
                break;
            case "30":
                playerScore.setText("40");
                break;
            case "40":
                if (firstPlayerScore.getText() == secondPlayerScore.getText() && firstPlayerScore.getText() == "40") {
                    playerScore.setText("A");
                } else if ((firstPlayerScore.getText() == "A" || secondPlayerScore.getText() == "A")) {
                    if (firstPlayerScore.getText() == "A") {
                        firstPlayerScore.setText("40");
                        FPHadAdvantage = false;
                        SPHadAdvantage = true;
                    } else {
                        FPHadAdvantage = true;
                        SPHadAdvantage = false;
                        secondPlayerScore.setText("40");
                    }
                } else {
                    winGame(player, playerSet);
                }
                break;
            case "A":
                winGame(player, playerSet);
        }
    }

    private void scoringTieBreak(Player player, TextView playerScore, TextView playerSet, boolean firstPoint, int TB) {
        int score = Integer.parseInt(playerScore.getText().toString()) + 1;
        playerScore.setText(String.valueOf(score));
        if (firstPoint) {
            changeServer();
        } else {
            serverTB++;
            if (serverTB == 2) {
                changeServer();
                serverTB = 0;
            }
        }
        if (winTieBreak(TB)) {
            playerSet.setText(String.valueOf(Integer.parseInt(playerSet.getText().toString()) + 1));
            firstPlayerScore.setText("0");
            secondPlayerScore.setText("0");
            tieBreak = false;
            serverTB = 0;
            changeServer();
            changeSet();
        }
    }

    private boolean winTieBreak(int TB) {
        int scorePlayerOne = Integer.parseInt(firstPlayerScore.getText().toString());
        int scorePlayerTwo = Integer.parseInt(secondPlayerScore.getText().toString());

        return ((scorePlayerOne == TB && scorePlayerTwo < TB - 1) || (scorePlayerTwo == TB && scorePlayerOne < TB - 1)
                || (scorePlayerOne + scorePlayerTwo >= (TB - 1) * 2 && Math.abs(scorePlayerOne - scorePlayerTwo) == 2));
    }

    private void lastSetTieBreak(Player player, TextView playerScore, TextView playerSet, int TB) {
        if (tieBreak) {
            scoringTieBreak(player, playerScore, playerSet, false, TB);
        } else {
            tieBreak = true;
            scoringTieBreak(player, playerScore, playerSet, true, TB);
        }
    }

    private void scoringPlayer(Player player, TextView playerScore, TextView playerSet, TextView challengerSet) {
        if (!matchFinished) {
            if (currentSet == 3 && lastSetFormat != 0) {
                switch (lastSetFormat) {
                    case 1:
                        lastSetTieBreak(player, playerScore, playerSet, 7);
                        break;
                    case 2:
                        lastSetTieBreak(player, playerScore, playerSet, 10);
                        break;
                }
            } else {
                if (tieBreak) {
                    scoringTieBreak(player, playerScore, playerSet, false, 7);
                } else {
                    int gamesPlayer = Integer.parseInt(playerSet.getText().toString());
                    int gamesChallenger = Integer.parseInt(challengerSet.getText().toString());
                    if (gamesChallenger == pointsTieBreak && gamesPlayer == pointsTieBreak) {
                        tieBreak = true;
                        scoringTieBreak(player, playerScore, playerSet, true, 7);
                    } else {
                        if (advantage) {
                            scoringWithAdvantage(player, playerScore, playerSet);
                        } else {
                            scoringWithoutAdvantage(player, playerScore, playerSet);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void closeDialog() {
        gameNotOverDialog.dismiss();
    }

    @Override
    public void gameNotOverButtons(View view) {
        switch (view.getId()) {
            case R.id.buttonCancelRecording:
                leaveRecordingDialog = new LeaveRecordingDialog();
                leaveRecordingDialog.show(getSupportFragmentManager(), "leaving dialog");
                break;
            case R.id.buttonFinishWithoutWinner:
                intent = new Intent(this, StatisticsActivity.class);
                intent.putExtra("playerOne", playerOne);
                intent.putExtra("playerTwo", playerTwo);
                intent.putExtra("setOne", setOne);
                intent.putExtra("setTwo", setTwo);
                intent.putExtra("setThree", setThree);
                intent.putExtra("origin", "done");
                intent.putExtra("matchIsDone", matchFinished);
                this.startActivity(intent);
                break;
            case R.id.buttonFPWinner:
                intent = new Intent(this, StatisticsActivity.class);
                intent.putExtra("winner", playerOne);
                intent.putExtra("playerOne", playerOne);
                intent.putExtra("playerTwo", playerTwo);
                intent.putExtra("setOne", setOne);
                intent.putExtra("setTwo", setTwo);
                intent.putExtra("setThree", setThree);
                intent.putExtra("origin", "done");
                intent.putExtra("matchIsDone", matchFinished);
                this.startActivity(intent);
                break;
            case R.id.buttonSPWinner:
                intent = new Intent(this, StatisticsActivity.class);
                intent.putExtra("winner", playerTwo);
                intent.putExtra("playerOne", playerOne);
                intent.putExtra("playerTwo", playerTwo);
                intent.putExtra("setOne", setOne);
                intent.putExtra("setTwo", setTwo);
                intent.putExtra("setThree", setThree);
                intent.putExtra("origin", "done");
                intent.putExtra("matchIsDone", matchFinished);
                this.startActivity(intent);
                break;
        }
    }

    public void onServiceClick(View view) {

        switch (view.getId()) {
            case R.id.buttonFirstServe:
                if (server.getText().equals(playerOne.getName())) {
                    firstServeFP = true;
                    firstPlayerStats.setFirstServes(firstPlayerStats.getFirstServes() + 1);
                } else {
                    firstServeSP = true;
                    secondPlayerStats.setFirstServes(secondPlayerStats.getFirstServes() + 1);
                }
                break;
        }
    }


    public void onImageButtonClick(View view) {

        switch (view.getId()) {
            case R.id.imageButtonStats:
                intent = new Intent(this, StatisticsActivity.class);
                intent.putExtra("playerOne", playerOne);
                intent.putExtra("playerTwo", playerTwo);
                intent.putExtra("origin", "imageButton");
                if (setOne != null) {
                    setOne.setPlayersStats(Arrays.asList(firstPlayerStats, secondPlayerStats));
                    setOne.setSetScoreFirstPlayer((String) firstPlayerSet_1.getText());
                    setOne.setSetScoreSecondPlayer((String) secondPlayerSet_1.getText());
                    intent.putExtra("setOne", setOne);
                }
                if (setTwo != null) {
                    setTwo.setPlayersStats(Arrays.asList(firstPlayerStats, secondPlayerStats));
                    setTwo.setSetScoreFirstPlayer((String) firstPlayerSet_2.getText());
                    setTwo.setSetScoreSecondPlayer((String) secondPlayerSet_2.getText());
                    intent.putExtra("setTwo", setTwo);
                }
                if (setThree != null) {
                    intent.putExtra("setThree", setThree);
                }
                startActivity(intent);
                break;
            case R.id.imageButtonService:
                updateServerDialog.show(getSupportFragmentManager(), "update server");
                break;
            case R.id.imageButtonRally:
                Collections.reverse(currentPlayer);
                Iterator<Integer> iterator = currentPlayer.iterator();
                int currentWinner = iterator.next();
                if (currentWinner == 1) {
                    removePoints(firstPlayerScore, firstPlayerSet_1, firstPlayerSet_2, firstPlayerSet_3, FPHadAdvantage);
                } else if (currentWinner == 2) {
                    removePoints(secondPlayerScore, secondPlayerSet_1, secondPlayerSet_2, secondPlayerSet_3, SPHadAdvantage);
                }
                iterator.remove();
                break;
        }
    }

    private void removePoints(TextView PlayerScore, TextView PlayerSet_1, TextView PlayerSet_2, TextView PlayerSet_3, boolean playerHadAdvantage) {
        switch ((String) PlayerScore.getText()) {
            case "40":
                PlayerScore.setText("30");
                break;
            case "A":
                PlayerScore.setText("40");
                break;
            case "0":
                PlayerScore.setText("0");
                /*
                if (!PlayerSet_3.getText().equals("-") && !PlayerSet_3.getText().equals("0")) {
                    PlayerSet_3.setText(String.valueOf(Integer.parseInt((String) PlayerSet_3.getText()) - 1));
                    if (playerHadAdvantage) {
                        PlayerScore.setText("A");
                    }
                    PlayerScore.setText("40");
                } else if (!PlayerSet_2.getText().equals("-") && !PlayerSet_2.getText().equals("0")) {
                    PlayerSet_2.setText(String.valueOf(Integer.parseInt((String) PlayerSet_2.getText()) - 1));
                    if (playerHadAdvantage) {
                        PlayerScore.setText("A");
                    }
                    PlayerScore.setText("40");
                } else if (!PlayerSet_1.getText().equals("-") && !PlayerSet_1.getText().equals("0")) {
                    PlayerSet_1.setText(String.valueOf(Integer.parseInt((String) PlayerSet_1.getText()) - 1));
                    if (playerHadAdvantage) {
                        PlayerScore.setText("A");
                    }
                    PlayerScore.setText("40");
                }
                 */
                break;
            default:
                int currentScore = Integer.parseInt((String) PlayerScore.getText());
                PlayerScore.setText(String.valueOf(currentScore - 15));
                break;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("firstPlayerScore", (String) firstPlayerScore.getText());
        savedInstanceState.putString("secondPlayerScore", (String) secondPlayerScore.getText());
        savedInstanceState.putInt("lastSetFormat", lastSetFormat);
        savedInstanceState.putParcelable("playerOne", playerOne);
        savedInstanceState.putParcelable("playerTwo", playerTwo);

        if (!firstPlayerSet_1.getText().equals("-")) {
            savedInstanceState.putString("firstPlayerSet_1", (String) firstPlayerSet_1.getText());
            savedInstanceState.putString("secondPlayerSet_1", (String) secondPlayerSet_1.getText());
        }
        if (!firstPlayerSet_2.getText().equals("-")) {
            savedInstanceState.putString("firstPlayerSet_2", (String) firstPlayerSet_2.getText());
            savedInstanceState.putString("secondPlayerSet_2", (String) secondPlayerSet_2.getText());
        }
        if (!firstPlayerSet_3.getText().equals("-")) {
            savedInstanceState.putString("firstPlayerSet_3", (String) firstPlayerSet_3.getText());
            savedInstanceState.putString("secondPlayerSet_3", (String) secondPlayerSet_3.getText());
        }

        if (setOne != null) {
            savedInstanceState.putParcelable("setOne", setOne);
        }
        if (setTwo != null) {
            savedInstanceState.putParcelable("setTwo", setTwo);
        }
        if (setThree != null) {
            savedInstanceState.putParcelable("setThree", setThree);
        }

    }

    @Override
    public void onUpdateServerButtonClick(View view) {
        List<TextView> sets = selectSet();
        switch (view.getId()) {
            case R.id.buttonStartOver:
                firstPlayerScore.setText(String.valueOf(0));
                secondPlayerScore.setText(String.valueOf(0));
                updateServerDialog.dismiss();
                break;
            case R.id.buttonGrantToFP:
                int currentSetScore = Integer.parseInt((String) sets.get(0).getText());
                sets.get(0).setText(String.valueOf(currentSetScore + 1));
                updateServerDialog.dismiss();
                break;
            case R.id.buttonGrantToSP:
                currentSetScore = Integer.parseInt((String) sets.get(1).getText());
                sets.get(1).setText(String.valueOf(currentSetScore + 1));
                updateServerDialog.dismiss();
                break;
        }
    }


}
