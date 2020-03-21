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
import fr.android.tennistracker.R;

public class RecordingActivity extends AppCompatActivity implements FirstServerDialog.FirstServerDialogListener, LeaveRecordingDialog.LeaveRecordingDialogListener {

    private FirstServerDialog dialogFragment;
    private LeaveRecordingDialog leaveRecordingDialog;
    private TextView firstPlayerGameScore, firstPlayerSetScore, secondPlayerGameScore, secondPlayerSetScore, firstPlayerScore, secondPlayerScore, serverLabel, serverName;
    private String currentServer, firstName, secondName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        firstName = getIntent().getStringExtra("firstPlayerName");
        getSupportActionBar().setTitle(R.string.recordingActivityTitle);
        TextView tabFirstName = findViewById(R.id.tabFirstName);
        tabFirstName.setText(firstName);
        secondName = getIntent().getStringExtra("secondPlayerName");
        TextView tabSecondName = findViewById(R.id.tabSecondName);
        tabSecondName.setText(secondName);
        serverName = findViewById(R.id.serverName);
        dialogFragment = new FirstServerDialog();
        dialogFragment.show(getSupportFragmentManager(), "test dialog");
        firstPlayerScore = findViewById(R.id.tabFirstPlayerScore);
        secondPlayerScore = findViewById(R.id.tabSecondPlayerScore);
        firstPlayerGameScore = findViewById(R.id.firstPlayerGameScore);
        secondPlayerGameScore = findViewById(R.id.secondPlayerGameScore);
        firstPlayerGameScore.setText("0");
        secondPlayerGameScore.setText("0");

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
                scoringPlayer(firstPlayerScore, firstPlayerGameScore);
                break;
            case R.id.givePointToSPItem:
                scoringPlayer(secondPlayerScore, secondPlayerGameScore);
                break;
        }
        return true;
    }

    @Override
    public void sendPlayerName(View view) {
        Button clickedButton = view.findViewById(view.getId());
        String playerName = clickedButton.getText().toString();
        dialogFragment.dismiss();
        serverLabel = findViewById(R.id.serverLabel);
        currentServer = playerName;
        serverName.setText(playerName);
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
                scoringPlayer(firstPlayerScore, firstPlayerGameScore);
                break;
            case R.id.buttonWinnerSP:
                scoringPlayer(secondPlayerScore, secondPlayerGameScore);
                break;
            case R.id.buttonUnforcedErrorFP:
                scoringPlayer(secondPlayerScore, secondPlayerGameScore);
                break;
            case R.id.buttonUnforcedErrorSP:
                scoringPlayer(firstPlayerScore, firstPlayerGameScore);
                break;
            case R.id.buttonForcedErrorFP:
                scoringPlayer(secondPlayerScore, secondPlayerGameScore);
                break;
            case R.id.buttonForcedErrorSP:
                scoringPlayer(firstPlayerScore, firstPlayerGameScore);
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
                    if (serverName.getText().equals(firstName)) {
                        serverName.setText(secondName);
                    } else {
                        serverName.setText(firstName);
                    }
                }
                break;
            case "A":
                PlayerGameScore.setText(String.valueOf(Integer.parseInt(PlayerGameScore.getText().toString()) + 1));
                firstPlayerScore.setText("0");
                secondPlayerScore.setText("0");
                if (serverName.getText().equals(firstName)) {
                    serverName.setText(secondName);
                } else {
                    serverName.setText(firstName);
                }
        }
    }
}
