package com.tobitint.bohnanza.match.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.R;

/**
 *
 * 플레이어의 정보가 보이는 뷰<br>
 * - 리스트 뷰의 각 아이템
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PlayerItemView extends LinearLayout {

    /**
     * 플레이어 이름이 보여지는 텍스트 뷰
     */
    TextView playerNameTextView;

    /**
     * 플레이어 골드가 보여지는 텍스트 뷰
     */
    TextView playerGoldTextView;

    /**
     * 플레이어 밭이 보여지는 뷰
     */
    FieldView fieldView;

    /**
     * 플레이어 패 개수
     */
    int handsNum;

    public PlayerItemView(Context context) {
        super(context);

        init(context);
    }

    public PlayerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_item, this, true);

        playerNameTextView = findViewById(R.id.playerNameTextView);
        playerGoldTextView = findViewById(R.id.playerGoldTextView);
        fieldView = findViewById(R.id.field);
    }

    public void setPlayerNameTextView(String playerName) {
        playerNameTextView.setText(playerName);
    }

    public void setPlayerGoldTextView(int playerGold) {
        playerGoldTextView.setText(playerGold + "G");
    }

    public void setHandsNum(int handsNum) {
        this.handsNum = handsNum;
    }

}
