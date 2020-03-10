package com.tobitint.bohnanza.match.onlineMatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.tobitint.bohnanza.BaseActivity;
import com.tobitint.bohnanza.match.GameActivity;
import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.R;
import com.tobitint.bohnanza.client.ClientSender;

import game.game.GameInfo;
import server.message.Message;

/**
 *
 * 온라인 매치 접속 중인 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class WaitOnlineMatchActivity extends BaseActivity {

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

        // Get player name
        playerName = ((InfoApplication) getApplicationContext()).getPlayerName();

        // Get intent
        Intent intent = getIntent();
        matchName = intent.getStringExtra("match name");

        // Set match name TextView
        matchNameTextView.setText(matchName);

        // Request player number
        (new ClientSender(new Message(212, playerName, matchName))).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!isFinishing()) {
            exitMatch();
        }
    }

    /**
     * 매치 삭제로 인한 매치 나가기
     * - 메시지 타입 203
     */
    public void exitMatchByMatchDeleted() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Host deleted match.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                WaitOnlineMatchActivity.this.finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 플레이어 수 적용<br>
     * - 메시지 타입 212
     *
     * @param playerNumber 플레이어 수
     */
    public void applyPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;

        applyCurrentPlayerNumber(currentPlayerNumber);
    }

    /**
     * 현재 접속한 플레이어 수 적용<br>
     * - 메시지 타입 221
     *
     * @param currentPlayerNumber 현재 접속한 플레이어 수
     */
    public void applyCurrentPlayerNumber(int currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;

        currentPlayerNumberTextView.setText(currentPlayerNumber + " / " + playerNumber);
    }

    /**
     * 매치 생성 완료<br>
     * - 메시지 타입 301
     *
     * @param gameInfo 게임 정보
     */
    public void createMatchSuccess(GameInfo gameInfo) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("game info", gameInfo);

        startActivity(intent);
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
                exitMatch();

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

    /**
     * 매치 나가기
     */
    private void exitMatch() {
        (new ClientSender(new Message(213, playerName, matchName))).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void onExitMatchButtonClicked(View v) {
        showExitMatchAlertDialog();
    }

    @Override
    public void onBackPressed() {
        showExitMatchAlertDialog();
    }
}
