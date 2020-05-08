package com.tobitint.bohnanza.match.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.R;

/**
 * 덱이 보여지는 뷰
 */
public class CardView extends LinearLayout {

    /**
     * 콩의 이미지가 보여지는 뷰
     */
    ImageView beanImageView;

    /**
     * 콩 카드의 개수가 보여지는 뷰
     */
    TextView beanNumTextView;

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

        beanImageView = findViewById(R.id.beanImageView);
        beanNumTextView = findViewById(R.id.beanNumTextView);
    }

    public void setBeanImageView(int beanNumber) {
        int beanImageId = ((InfoApplication) getContext().getApplicationContext()).getBeanImageId(beanNumber);

        beanImageView.setImageResource(beanImageId);
    }

    public void setBeanNumTextView(int beanNum) {
        beanNumTextView.setText(String.valueOf(beanNum));
    }

}
