package com.example.bohnanza.match.onlineMatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.bohnanza.BaseActivity;
import com.example.bohnanza.R;
import com.example.bohnanza.client.ClientSender;

import server.message.Message;

/**
 *
 * 온라인 매치 생성 중인 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class CreateOnlineMatchActivity extends BaseActivity {

    /**
     * 매치 이름이 보여지는 텍스트 뷰
     */
    TextView matchNameTextView;

    /**
     * 매치 비밀번호가 보여지는 텍스트 뷰
     */
    TextView matchPWTextView;

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
     * 매치 비밀번호
     */
    String matchPW;

    /**
     * 플레이어 수
     */
    int playerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_online_match);

        matchNameTextView = findViewById(R.id.matchNameTextView);
        matchPWTextView = findViewById(R.id.matchPWTextView);
        currentPlayerNumberTextView = findViewById(R.id.currentPlayerNumberTextView);

        // Get intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("player name");
        matchName = intent.getStringExtra("match name");
        matchPW = intent.getStringExtra("match PW");
        playerNumber = intent.getIntExtra("player number", 0);

        // Set match information TextView
        matchNameTextView.setText(matchName);
        matchPWTextView.setText(matchPW);

        currentPlayerNumberTextView.setText("0 / " + playerNumber);
    }

    /**
     * 매치를 삭제할지 결정하는 알림 대화상자 띄우기
     */
    private void showDeleteMatchAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Match will be deleted.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                (new ClientSender(new Message(203, playerName, matchName))).execute();

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

    public void onDeleteMatchButtonClicked(View v) {
        showDeleteMatchAlertDialog();
    }

    @Override
    public void onBackPressed() {
        showDeleteMatchAlertDialog();
    }
}
