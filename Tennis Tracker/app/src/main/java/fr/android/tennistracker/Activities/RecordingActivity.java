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
import fr.android.tennistracker.Fragments.FirstServerDialog;
import fr.android.tennistracker.Fragments.LeaveRecordingDialog;
import fr.android.tennistracker.Model.Player;
import fr.android.tennistracker.R;

import java.util.ArrayList;
import java.util.List;

public class RecordingActivity extends AppCompatActivity implements FirstServerDialog.FirstServerDialogListener, LeaveRecordingDialog.LeaveRecordingDialogListener {

    private FirstServerDialog dialogFragment;
    private LeaveRecordingDialog leaveRecordingDialog;
    private TextView firstPlayerSet_1, firstPlayerSet_2, firstPlayerSet_3, firstPlayerScore;
    private TextView secondPlayerSet_1, secondPlayerSet_2, secondPlayerSet_3, secondPlayerScore;
    private TextView server;
    private int currentSet = 1;
    private Player playerOne;
    private Player playerTwo;
    private boolean avantage = true;
    private int nbSets = 6;
    private int serverTB = 0;
    private boolean tieBreak = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        // Initialize first player
        String playerOneName = getIntent().getStringExtra("firstPlayerName");
        TextView tabFirstName = findViewById(R.id.tabFirstName);
        playerOne = initializePlayer(playerOneName, tabFirstName);

        // Initialize second player
        String playerTwoName = getIntent().getStringExtra("secondPlayerName");
        TextView tabSecondName = findViewById(R.id.tabSecondName);
        playerTwo = initializePlayer(playerTwoName, tabSecondName);

        initializeElements();

    }

    public void initializeElements() {
        getSupportActionBar().setTitle(R.string.recordingActivityTitle);
        server = findViewById(R.id.serverName);
        dialogFragment = new FirstServerDialog();
        dialogFragment.show(getSupportFragmentManager(), "test dialog");

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
                scoringPlayer(firstPlayerScore, firstPlayerSet, secondPlayerSet);
                break;
            case R.id.givePointToSPItem:
                scoringPlayer(secondPlayerScore, secondPlayerSet, firstPlayerSet);
                break;
        }
        return true;
    }

    @Override
    public void sendPlayerName(View view) {
        Button clickedButton = view.findViewById(view.getId());
        String playerName = clickedButton.getText().toString();
        dialogFragment.dismiss();
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
        List<TextView> sets = new ArrayList<TextView>();

        switch (currentSet) {
            case 1:
                sets.add(firstPlayerSet_1);
                sets.add(secondPlayerSet_1);
                break;
            case 2:
                sets.add(firstPlayerSet_2);
                sets.add(secondPlayerSet_2);
                break;
            case 3:
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
                scoringPlayer(firstPlayerScore, firstPlayerSet, secondPlayerSet);
                break;
            case R.id.buttonWinnerSP:
                scoringPlayer(secondPlayerScore, secondPlayerSet, firstPlayerSet);
                break;
            case R.id.buttonUnforcedErrorFP:
                scoringPlayer(secondPlayerScore, secondPlayerSet, firstPlayerSet);
                break;
            case R.id.buttonUnforcedErrorSP:
                scoringPlayer(firstPlayerScore, firstPlayerSet, secondPlayerSet);

                break;
            case R.id.buttonForcedErrorFP:
                scoringPlayer(secondPlayerScore, secondPlayerSet, firstPlayerSet);
                break;
            case R.id.buttonForcedErrorSP:
                scoringPlayer(firstPlayerScore, firstPlayerSet, secondPlayerSet);

                break;
        }
    }

    public void onAceClick(View view) {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);
        if (server.getText().equals(playerOne.getName())) {
            scoringPlayer(firstPlayerScore, firstPlayerSet, secondPlayerSet);

        } else {
            scoringPlayer(secondPlayerScore, secondPlayerSet, firstPlayerSet);
        }
    }

    public void onDoubleFaultClick(View view) {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);
        if (server.getText().equals(playerOne.getName())) {
            scoringPlayer(secondPlayerScore, secondPlayerSet, firstPlayerSet);
        } else {
            scoringPlayer(firstPlayerScore, firstPlayerSet, secondPlayerSet);

        }
    }

    private void changeServer() {
        if (server.getText().equals(playerOne.getName())) {
            server.setText(playerTwo.getName());
        } else {
            server.setText(playerOne.getName());
        }
    }

    private void winGame(TextView playerSet) {
        playerSet.setText(String.valueOf(Integer.parseInt(playerSet.getText().toString()) + 1));
        firstPlayerScore.setText("0");
        secondPlayerScore.setText("0");
        changeServer();
        isWinSet();
    }

    private void isWinSet() {
        if (setIsDone()) {
            changeSet();
        }
    }

    private void changeSet() {
        currentSet++;
        switch (currentSet) {
            case 2:
                firstPlayerSet_2.setText("0");
                secondPlayerSet_2.setText("0");
                break;
            case 3:
                firstPlayerSet_3.setText("0");
                secondPlayerSet_3.setText("0");
                break;
        }
    }

    public boolean setIsDone() {
        List<TextView> sets = selectSet();
        TextView firstPlayerSet = sets.get(0);
        TextView secondPlayerSet = sets.get(1);
        int gamesPlayerOne = Integer.parseInt((String) firstPlayerSet.getText());
        int gamesPlayerTwo = Integer.parseInt((String) secondPlayerSet.getText());

        return ((gamesPlayerOne == nbSets && gamesPlayerTwo < nbSets - 1) || (gamesPlayerOne < nbSets - 1 && gamesPlayerTwo == nbSets) ||
                (gamesPlayerOne == nbSets - 1 && gamesPlayerTwo == nbSets + 1) || (gamesPlayerOne == nbSets + 1 && gamesPlayerTwo == nbSets - 1) ||
                (gamesPlayerOne == nbSets && gamesPlayerTwo == nbSets + 1) || (gamesPlayerOne == nbSets + 1 && gamesPlayerTwo == nbSets));
    }

    public void matchIsDone() {

    }

    private void scoringWihtoutAvantage(TextView playerScore, TextView playerSet) {
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
                winGame(playerSet);
                break;
        }
    }

    private void scoringWithAvantage(TextView playerScore, TextView playerSet) {
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
                    } else {
                        secondPlayerScore.setText("40");
                    }
                } else {
                    winGame(playerSet);
                }
                break;
            case "A":
                winGame(playerSet);
        }
    }

    private void scoringTieBreak(TextView playerScore, TextView playerSet, boolean firstPoint) {
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
        if (winTieBreak()) {
            playerSet.setText(String.valueOf(Integer.parseInt(playerSet.getText().toString()) + 1));
            firstPlayerScore.setText("0");
            secondPlayerScore.setText("0");
            tieBreak = false;
            serverTB = 0;
            changeServer();
            changeSet();
        }
    }

    private boolean winTieBreak() {
        int scorePlayerOne = Integer.parseInt(firstPlayerScore.getText().toString());
        int scorePlayerTwo = Integer.parseInt(secondPlayerScore.getText().toString());

        return ((scorePlayerOne == 7 && scorePlayerTwo < 6) || (scorePlayerTwo == 7 && scorePlayerOne < 6)
                || (scorePlayerOne + scorePlayerTwo >= 12 && Math.abs(scorePlayerOne - scorePlayerTwo) == 2));
    }

    private void scoringPlayer(TextView playerScore, TextView playerSet, TextView challengerSet) {
        if (tieBreak) {
            scoringTieBreak(playerScore, playerSet, false);
        } else {
            int gamesPlayer = Integer.parseInt(playerSet.getText().toString());
            int gamesChallenger = Integer.parseInt(challengerSet.getText().toString());
            if (gamesChallenger == nbSets && gamesPlayer == nbSets) {
                tieBreak = true;
                scoringTieBreak(playerScore, playerSet, true);
            } else {
                if (avantage) {
                    scoringWithAvantage(playerScore, playerSet);
                } else {
                    scoringWihtoutAvantage(playerScore, playerSet);
                }
            }
        }

    }


}
