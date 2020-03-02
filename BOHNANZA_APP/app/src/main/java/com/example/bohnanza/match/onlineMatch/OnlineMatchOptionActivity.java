package com.example.bohnanza.match.onlineMatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bohnanza.BaseActivity;
import com.example.bohnanza.R;

/**
 *
 * 온라인 매치 관련 기능<br>
 * - 매치 생성<br>
 * - 매치 접속<br>
 * - 이어하기
 *
 * @author YJH
 * @version 1.0
 *
 */
public class OnlineMatchOptionActivity extends BaseActivity {

    /**
     * 플레이어 이름
     */
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_match_option);

        setFinishOnTouchOutside(false);

        // Get intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("player name");
    }

    public void onNewMatchButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), NewOnlineMatchOptionActivity.class);
        intent.putExtra("player name", playerName);

        startActivity(intent);
    }

    public void onJoinMatchButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), JoinOnlineMatchOptionActivity.class);
        intent.putExtra("player name", playerName);

        startActivity(intent);
    }

    public void onResumeButtonClicked(View v) {

    }

    public void onBackButtonClicked(View v) {
        finish();
    }

}
