package com.tobitint.bohnanza;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tobitint.bohnanza.client.ClientSender;

import server.message.Message;

/**
 *
 * 로그인 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class LoginActivity extends BaseActivity {

    /**
     * 플레이어 이름을 입력받는 에디트 텍스트
     */
    EditText playerNameEditText;

    /**
     * 로그인 버튼
     */
    Button loginButton;

    /**
     * 로그인 정보가 유효하지 않을 경우 안내 메시지가 보여지는 텍스트 뷰
     */
    TextView noticeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setFinishOnTouchOutside(false);

        playerNameEditText = findViewById(R.id.playerNameEditText);
        loginButton = findViewById(R.id.loginButton);
        noticeTextView = findViewById(R.id.noticeTextView);

        playerNameEditText.setFilters(new InputFilter[]{new BaseInputFilter()});

        loginButton.setEnabled(false);

        // Add text changed listener to playerNameEditText
        playerNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable arg) {
                String playerName = arg.toString();

                if (playerName == null || playerName.trim().isEmpty()) {
                    loginButton.setEnabled(false);
                    noticeTextView.setText(null);

                    return;
                }

                // Player name duplication check
                (new ClientSender(new Message(101, "player logged in", playerName))).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    /**
     * 플레이어 이름 중복 여부 적용<br>
     * - 메시지 타입 101
     *
     * @param isDuplicated 플레이어 이름 중복 여부
     */
    public void applyPlayerNameDuplicate(boolean isDuplicated) {
        String playerName = playerNameEditText.getText().toString();

        if (playerName == null || playerName.trim().isEmpty()) {
            loginButton.setEnabled(false);
            noticeTextView.setText(null);
        } else if (isDuplicated) {
            loginButton.setEnabled(false);
            noticeTextView.setText("This ID already exists.");
        } else {
            loginButton.setEnabled(true);
            noticeTextView.setText(null);
        }
    }

    public void onLoginButtonClicked(View v) {
        String playerName = playerNameEditText.getText().toString();

        // Save player name
        SharedPreferences playerInfoPref = getSharedPreferences("playerInfoPref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = playerInfoPref.edit();
        editor.putString("player name", playerName);
        editor.commit();

        // Send message to server
        (new ClientSender(new Message(102, playerName, null))).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // Set player name in InfoApplication
        ((InfoApplication) getApplicationContext()).setPlayerName(playerName);

        setResult(RESULT_OK);

        finish();
    }

    @Override
    public void onBackPressed() { }
}
