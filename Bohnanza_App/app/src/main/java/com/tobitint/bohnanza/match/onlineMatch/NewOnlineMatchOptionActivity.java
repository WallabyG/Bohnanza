package com.tobitint.bohnanza.match.onlineMatch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tobitint.bohnanza.BaseActivity;
import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.OkAlertDialog;
import com.tobitint.bohnanza.R;
import com.tobitint.bohnanza.client.ClientSender;

import java.util.ArrayList;
import java.util.Arrays;

import server.message.Message;

/**
 *
 * 온라인 매치 생성에 필요한 옵션을 입력받는 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class NewOnlineMatchOptionActivity extends BaseActivity {

    /**
     * 가능한 플레이어 수
     */
    Integer[] playerNumberItems = {2, 3, 4, 5};

    /**
     * 매치 이름을 입력받는 에디트 텍스트
     */
    EditText matchNameEditText;

    /**
     * 매치 비밀번호를 입력받는 에디트 텍스트
     */
    EditText matchPWEditText;

    /**
     * 플레이어 수를 선택하는 스피너
     */
    Spinner playerNumberSpinner;

    /**
     * 매치 생성 버튼
     */
    Button createMatchButton;

    /**
     * 매치 이름
     */
    private String matchName;

    /**
     * 매치 비밀번호
     */
    private String matchPW;

    /**
     * 플레이어 수
     */
    private int playerNumber;

    /**
     * 플레이어 이름
     */
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_online_match_option);

        setFinishOnTouchOutside(false);

        matchNameEditText = findViewById(R.id.matchNameEditText);
        matchPWEditText = findViewById(R.id.matchPWEditText);
        playerNumberSpinner = findViewById(R.id.playerNumberSpinner);
        createMatchButton = findViewById(R.id.createMatchButton);

        matchNameEditText.setFilters(new InputFilter[]{new BaseInputFilter()});
        matchPWEditText.setFilters(new InputFilter[]{new BaseInputFilter()});

        // Get player name
        playerName = ((InfoApplication) getApplicationContext()).getPlayerName();

        // Set playerNumberSpinner
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, playerNumberItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerNumberSpinner.setAdapter(adapter);

        playerNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                playerNumber = playerNumberItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        // Set textWatcher
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable arg) {
                setCreateMatchButtonState();
            }
        };

        // Set matchNameEditText TextChangedListener
        matchNameEditText.addTextChangedListener(textWatcher);

        // Set matchPWEditText TextChangedListener
        matchPWEditText.addTextChangedListener(textWatcher);
    }

    public void onCreateMatchButtonClicked(View v) {
        // Match name duplication check
        (new ClientSender(new Message(201, playerName, matchName))).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 매치 이름 중복 여부 적용<br>
     * - 메시지 타입 201
     *
     * @param isDuplicated 매치 이름 중복 여부
     */
    public void applyMatchNameDuplicate(boolean isDuplicated) {
        if (isDuplicated) {
            OkAlertDialog.show(NewOnlineMatchOptionActivity.this, "Match with same name exists.");
        } else {
            // Send message to server
            (new ClientSender(new Message(202, playerName, new ArrayList<>(Arrays.asList(matchName, matchPW, playerNumber))))).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            // Start CreateOnlineMatchActivity
            Intent intent = new Intent(getApplicationContext(), CreateOnlineMatchActivity.class);
            intent.putExtra("match name", matchName);
            intent.putExtra("match PW", matchPW);
            intent.putExtra("player number", playerNumber);

            startActivity(intent);
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
     * 매치 생성 버튼 활성화/비활성화 설정
     */
    private void setCreateMatchButtonState() {
        matchName = matchNameEditText.getText().toString();
        matchPW = matchPWEditText.getText().toString();

        if (checkMatchInfoValid()) {
            createMatchButton.setEnabled(true);
        } else {
            createMatchButton.setEnabled(false);
        }
    }

    public void onBackButtonClicked(View v) {
        finish();
    }

}
