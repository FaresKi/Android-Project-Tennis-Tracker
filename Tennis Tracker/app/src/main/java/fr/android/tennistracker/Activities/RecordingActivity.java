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

public class RecordingActivity extends AppCompatActivity implements FirstServerDialog.FirstServerDialogListener, LeaveRecordingDialog.LeaveRecordingDialogListener {

    private FirstServerDialog dialogFragment;
    private LeaveRecordingDialog leaveRecordingDialog;
    private TextView firstPlayerSet_1, firstPlayerSet_2, firstPlayerSet_3, firstPlayerScore;
    private TextView secondPlayerSet_1, secondPlayerSet_2, secondPlayerSet_3, secondPlayerScore;
    private TextView server;
    private int currentSet = 1;
    private Player playerOne;
    private Player playerTwo;

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

    public void initializeElements(){
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

    public Player initializePlayer(String name, TextView tabName){
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

        switch (item.getItemId()) {
            case R.id.cancelRecordingItem:
                leaveRecordingDialog = new LeaveRecordingDialog();
                leaveRecordingDialog.show(getSupportFragmentManager(), "leaving dialog");
                break;
            case R.id.givePointToFPItem:
                scoringPlayer(firstPlayerScore, firstPlayerSet_1);
                break;
            case R.id.givePointToSPItem:
                scoringPlayer(secondPlayerScore, secondPlayerSet_1);
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

    public void onRallyClick(View view) {
        switch (view.getId()) {
            case R.id.buttonWinnerFP:
                scoringPlayer(firstPlayerScore, firstPlayerSet_1);
                break;
            case R.id.buttonWinnerSP:
                scoringPlayer(secondPlayerScore, secondPlayerSet_1);
                break;
            case R.id.buttonUnforcedErrorFP:
                scoringPlayer(secondPlayerScore, secondPlayerSet_1);
                break;
            case R.id.buttonUnforcedErrorSP:
                scoringPlayer(firstPlayerScore, firstPlayerSet_1);
                break;
            case R.id.buttonForcedErrorFP:
                scoringPlayer(secondPlayerScore, secondPlayerSet_1);
                break;
            case R.id.buttonForcedErrorSP:
                scoringPlayer(firstPlayerScore, firstPlayerSet_1);
                break;
        }
    }

    private void scoringPlayer(TextView PlayerScore, TextView PlayerGameScore) {
        switch (PlayerScore.getText().toString()) {
            case "0":
                PlayerScore.setText("15");
                break;
            case "15":
                PlayerScore.setText("30");
                break;
            case "30":
                PlayerScore.setText("40");
                break;
            case "40":
                if (firstPlayerScore.getText() == secondPlayerScore.getText() && firstPlayerScore.getText() == "40") {
                    PlayerScore.setText("A");
                } else if ((firstPlayerScore.getText() == "A" || secondPlayerScore.getText() == "A")) {
                    if (firstPlayerScore.getText() == "A") {
                        firstPlayerScore.setText("40");
                    } else {
                        secondPlayerScore.setText("40");
                    }
                } else {
                    PlayerGameScore.setText(String.valueOf(Integer.parseInt(PlayerGameScore.getText().toString()) + 1));
                    firstPlayerScore.setText("0");
                    secondPlayerScore.setText("0");
                    if (server.getText().equals(playerOne.getName())) {
                        server.setText(playerOne.getName());
                    } else {
                        server.setText(playerOne.getName());
                    }
                }
                break;
            case "A":
                PlayerGameScore.setText(String.valueOf(Integer.parseInt(PlayerGameScore.getText().toString()) + 1));
                firstPlayerScore.setText("0");
                secondPlayerScore.setText("0");
                if (server.getText().equals(playerOne.getName())) {
                    server.setText(playerOne.getName());
                } else {
                    server.setText(playerOne.getName());
                }
        }
    }
}
