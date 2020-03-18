package com.tobitint.bohnanza.match.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.R;

public class CardView extends LinearLayout {

    public CardView(Context context) {
        super(context);

        init(context);
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.common_card, this, true);
    }

}
