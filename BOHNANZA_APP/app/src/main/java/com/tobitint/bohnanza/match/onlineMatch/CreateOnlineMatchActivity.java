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

        // Get player name
        playerName = ((InfoApplication) getApplicationContext()).getPlayerName();

        // Get intent
        Intent intent = getIntent();
        matchName = intent.getStringExtra("match name");
        matchPW = intent.getStringExtra("match PW");
        playerNumber = intent.getIntExtra("player number", 0);

        // Set match information TextView
        matchNameTextView.setText(matchName);
        matchPWTextView.setText(matchPW);

        currentPlayerNumberTextView.setText("1 / " + playerNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!isFinishing()) {
            deleteMatch();
        }
    }

    /**
     * 현재 접속한 플레이어 수 적용<br>
     * - 메시지 타입 221
     *
     * @param currentPlayerNumber 현재 접속한 플레이어 수
     */
    public void applyCurrentPlayerNumber(int currentPlayerNumber) {
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
     * 매치를 삭제할지 결정하는 알림 대화상자 띄우기
     */
    private void showDeleteMatchAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Match will be deleted.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteMatch();

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
     * 매치 삭제
     */
    private void deleteMatch() {
        (new ClientSender(new Message(203, playerName, matchName))).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void onDeleteMatchButtonClicked(View v) {
        showDeleteMatchAlertDialog();
    }

    @Override
    public void onBackPressed() {
        showDeleteMatchAlertDialog();
    }
}
