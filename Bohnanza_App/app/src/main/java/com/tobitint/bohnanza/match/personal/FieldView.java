package com.tobitint.bohnanza.match.personal;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.R;

import java.util.ArrayList;
import java.util.List;

import game.players.Field;

/**
 *
 * 플레이어 밭 뷰 in personalInfoFragment
 *
 * @author YJH
 * @version 1.0
 *
 */
public class FieldView extends LinearLayout {

    /**
     * 플레이어 싱글 밭 리스트
     */
    ArrayList<SingleFieldView> singleFieldViews = new ArrayList<>();

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
        inflater.inflate(R.layout.personal_field, this, true);
    }

    public void setSingleFieldViews(List<Field> fields) {
        for (Field field: fields) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = ((InfoApplication) getContext().getApplicationContext()).dp2px(10);

            SingleFieldView singleFieldView = new SingleFieldView(getContext());

            if (field.getField().isEmpty()) {
                singleFieldView.setEmpty();
            } else {
                singleFieldView.setBeanImageView(((InfoApplication) getContext().getApplicationContext()).getBeanImageId(field.getField().get(0).getNumber()));
                singleFieldView.setBeanNumTextView(field.getField().size());
            }

            singleFieldView.setLayoutParams(params);

            singleFieldViews.add(singleFieldView);

            addView(singleFieldView);
        }
    }

}
