package com.example.bohnanza;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.bohnanza.match.onlineMatch.OnlineMatchOptionActivity;

/**
 *
 * 메인 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_LOGIN = 101;
    public static final int REQUEST_CODE_ONLINE_MATCH = 102;

    /**
     * 플레이어 이름
     */
    String playerName;

    /**
     * 플레이어 이름이 보여지는 텍스트 뷰
     */
    TextView playerNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        playerNameTextView = findViewById(R.id.playerNameTextView);

        // Load player name
        loadPlayerName();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            playerName = data.getExtras().getString("player name");
            playerNameTextView.setText(playerName);
        }
    }

    public void onOnlineMatchButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnlineMatchOptionActivity.class);
        intent.putExtra("player name", playerName);

        startActivity(intent);
    }

    /**
     * 애플리케이션 내부에 저장되어 있는 플레이어 이름 불러오기
     */
    private void loadPlayerName() {
        SharedPreferences playerInfoPref = getSharedPreferences("playerInfoPref", Activity.MODE_PRIVATE);

        // Player name doesn't exist
        if (playerInfoPref == null || !(playerInfoPref.contains("player name"))) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE_LOGIN);
        }

        // Player name exists
        else {
            playerName = playerInfoPref.getString("player name", "");
            playerNameTextView.setText(playerName);
        }
    }

}
