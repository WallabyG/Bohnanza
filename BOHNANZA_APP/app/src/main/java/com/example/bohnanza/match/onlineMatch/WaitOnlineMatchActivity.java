package com.example.bohnanza.match.onlineMatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bohnanza.R;
import com.example.bohnanza.client.ClientTask;

import java.util.ArrayList;

import server.message.Message;

/**
 *
 * 온라인 매치 접속 중인 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class WaitOnlineMatchActivity extends AppCompatActivity {

    /**
     * 매치 이름이 보여지는 텍스트 뷰
     */
    TextView matchNameTextView;

    /**
     * 현재 접속한 플레이어 수가 보여지는 텍스트 뷰
     */
    TextView currentPlayerNumberTextView;

    /**
     * 플레이어 이름
     */
    String playerName;

    /**
     * 매치 이름
     */
    String matchName;

    /**
     * 플레이어 수
     */
    int playerNumber;

    /**
     * 현재 접속한 플레이어 수
     */
    int currentPlayerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_online_match);

        matchNameTextView = findViewById(R.id.matchNameTextView);
        currentPlayerNumberTextView = findViewById(R.id.currentPlayerNumberTextView);

        // Get intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("player name");
        matchName = intent.getStringExtra("match name");

        // Set match information TextView
        matchNameTextView.setText(matchName);

        (new ClientTask() {
            @Override
            protected void onPostExecute(Void aVoid) {
                playerNumber = (int) ((ArrayList) returnObj).get(0);
                currentPlayerNumber = (int) ((ArrayList) returnObj).get(1);

                currentPlayerNumberTextView.setText(currentPlayerNumber + " / " + playerNumber);
            }
        }).execute(new Message(212, playerName, matchName));
    }

    /**
     * 매치를 나갈지 결정하는 알림 대화상자 띄우기
     */
    private void showExitMatchAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You will get out of the match.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                (new ClientTask()).execute(new Message(213, playerName, matchName));

                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) { }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onExitMatchButtonClicked(View v) {
        showExitMatchAlertDialog();
    }

    @Override
    public void onBackPressed() {
        showExitMatchAlertDialog();
    }
}
