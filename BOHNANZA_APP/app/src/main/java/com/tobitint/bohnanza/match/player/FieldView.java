package com.tobitint.bohnanza.match.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tobitint.bohnanza.R;

/**
 *
 * 플레이어 밭 뷰 in playerInfoFragment
 *
 * @author YJH
 * @version 1.0
 *
 */
public class FieldView extends ConstraintLayout {

    /**
     * 첫 번째 밭
     */
    SingleFieldView firstField;

    /**
     * 두 번째 밭
     */
    SingleFieldView secondField;

    public FieldView(Context context) {
        super(context);

        init(context);
    }

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_field, this, true);

        firstField = findViewById(R.id.firstField);
        secondField = findViewById(R.id.secondField);
    }

}
