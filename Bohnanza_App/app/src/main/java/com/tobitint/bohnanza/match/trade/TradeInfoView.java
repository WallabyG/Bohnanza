package com.tobitint.bohnanza.match.trade;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.R;

public class TradeInfoView extends LinearLayout {


    public TradeInfoView(Context context) {
        super(context);

        init(context);
    }

    public TradeInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_info, this, true);
    }
}
