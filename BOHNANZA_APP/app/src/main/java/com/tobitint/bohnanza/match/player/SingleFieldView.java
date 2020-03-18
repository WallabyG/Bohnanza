package com.tobitint.bohnanza.match.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.R;

/**
 *
 * 플레이어 싱글 밭 뷰 in playerInfoFragment
 *
 * @author YJH
 * @version 1.0
 *
 */
public class SingleFieldView extends LinearLayout {

    public SingleFieldView(Context context) {
        super(context);

        init(context);
    }

    public SingleFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_single_field, this, true);
    }

}
