package com.tobitint.bohnanza;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.client.Client;
import com.tobitint.bohnanza.match.onlineMatch.OnlineMatchOptionActivity;

/**
 *
 * 메인 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class MainActivity extends BaseActivity {

    public static final int REQUEST_CODE_LOGIN = 101;

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

        // Init display metrics
        ((InfoApplication) getApplicationContext()).initDm();

        // Start communication
        (new Client(this)).start();

        // Load player name
        loadPlayerName();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK) {
            playerName = ((InfoApplication) getApplicationContext()).getPlayerName();
            playerNameTextView.setText(playerName);
        }
    }

    public void onOnlineMatchButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), OnlineMatchOptionActivity.class);

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

            // Set player name in InfoApplication
            ((InfoApplication) getApplicationContext()).setPlayerName(playerName);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Let's play a game together!");

        builder.setPositiveButton("Yes, that's good!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) { }
        });

        builder.setNegativeButton("Sorry, maybe next time..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
