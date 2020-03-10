package com.tobitint.bohnanza.match.onlineMatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tobitint.bohnanza.BaseActivity;
import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.R;

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

        // Get player name
        playerName = ((InfoApplication) getApplicationContext()).getPlayerName();
    }

    public void onNewMatchButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), NewOnlineMatchOptionActivity.class);

        startActivity(intent);
    }

    public void onJoinMatchButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), JoinOnlineMatchOptionActivity.class);

        startActivity(intent);
    }

    public void onResumeButtonClicked(View v) {

    }

    public void onBackButtonClicked(View v) {
        finish();
    }

}
