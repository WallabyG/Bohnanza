package com.tobitint.bohnanza.match.opponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.R;

public class OpponentPlayerItemView extends LinearLayout {

    TextView playerNameTextView;
    LinearLayout firstFieldLayout;
    LinearLayout secondFieldLayout;

    public OpponentPlayerItemView(Context context) {
        super(context);

        init(context);
    }

    public OpponentPlayerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.opponent_player_item, this, true);

        playerNameTextView = findViewById(R.id.playerNameTextView);
        firstFieldLayout = findViewById(R.id.firstFieldLayout);
        secondFieldLayout = findViewById(R.id.secondFieldLayout);
    }

    public void setPlayerNameTextView(String playerName) {
        playerNameTextView.setText(playerName);
    }
}
