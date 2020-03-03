package com.example.bohnanza.match.onlineMatch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.bohnanza.BaseActivity;
import com.example.bohnanza.OkAlertDialog;
import com.example.bohnanza.R;
import com.example.bohnanza.client.ClientSender;

import java.util.ArrayList;
import java.util.Arrays;

import server.message.Message;

/**
 *
 * 온라인 매치 접속에 필요한 옵션을 입력받는 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class JoinOnlineMatchOptionActivity extends BaseActivity {

    /**
     * 매치 이름을 입력받는 에디트 텍스트
     */
    EditText matchNameEditText;

    /**
     * 매치 비밀번호를 입력받는 에디트 텍스트
     */
    EditText matchPWEditText;

    /**
     * 매치 접속 버튼
     */
    Button joinMatchButton;

    /**
     * 매치 이름
     */
    private String matchName;

    /**
     * 매치 비밀번호
     */
    private String matchPW;

    /**
     * 플레이어 이름
     */
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_online_match_option);

        matchNameEditText = findViewById(R.id.matchNameEditText);
        matchPWEditText = findViewById(R.id.matchPWEditText);

        joinMatchButton = findViewById(R.id.joinMatchButton);

        // Set activity size
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) (size.x * 0.6);
        int height = (int) (size.y * 0.8);

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        // Get intent
        Intent intent = getIntent();
        playerName = intent.getStringExtra("player name");

        // Set textWatcher
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable arg) {
                setJoinMatchButtonState();
            }
        };


        // Set matchNameEditText TextChangedListener
        matchNameEditText.addTextChangedListener(textWatcher);

        // Set matchPWEditText TextChangedListener
        matchPWEditText.addTextChangedListener(textWatcher);
    }

    public void onJoinMatchButtonClicked(View v) {
        // Match information validation check
        (new ClientSender(new Message(211, playerName, new ArrayList<>(Arrays.asList(matchName, matchPW))))).execute();
    }

    /**
     * 매치 정보 유효 여부 적용
     *
     * @param state 매치 정보 유효 여부
     */
    public void applyMatchInfoValid(int state) {
        // Match join success
        if (state == 0) {
            Intent intent = new Intent(getApplicationContext(), WaitOnlineMatchActivity.class);
            intent.putExtra("player name", playerName);
            intent.putExtra("match name", matchName);

            startActivity(intent);
        }

        // Match name mismatch
        else if (state == 1) {
            OkAlertDialog.show(JoinOnlineMatchOptionActivity.this, "The match does not exist.");
        }

        // Match full
        else if (state == 2) {
            OkAlertDialog.show(JoinOnlineMatchOptionActivity.this, "The match is full.");
        }

        // Match PW mismatch
        else if (state == 3) {
            OkAlertDialog.show(JoinOnlineMatchOptionActivity.this, "The password does not match.");
        }
    }

    /**
     * 매치 정보가 유효한지 체크
     *
     * @return 매치 정보가 유효한지 여부
     */
    private boolean checkMatchInfoValid() {
        return !(matchName == null || matchName.trim().isEmpty()) && !(matchPW == null || matchPW.trim().isEmpty());
    }

    /**
     * 매치 접속 버튼 활성화/비활성화 설정
     */
    private void setJoinMatchButtonState() {
        matchName = matchNameEditText.getText().toString();
        matchPW = matchPWEditText.getText().toString();

        if (checkMatchInfoValid()) {
            joinMatchButton.setEnabled(true);
        } else {
            joinMatchButton.setEnabled(false);
        }
    }

    public void onBackButtonClicked(View v) {
        finish();
    }

}
