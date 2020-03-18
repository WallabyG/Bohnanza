package com.tobitint.bohnanza.match.personal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.R;

/**
 *
 * 플레이어 패가 보여지는 뷰
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PlayerHandView extends LinearLayout {

    public PlayerHandView(Context context) {
        super(context);

        init(context);
    }

    public PlayerHandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_hand, this, true);
    }

}
