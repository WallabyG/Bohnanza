package com.tobitint.bohnanza.match.personal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tobitint.bohnanza.R;

/**
 *
 * 플레이어 싱글 밭 뷰 in personalInfoFragment
 *
 * @author YJH
 * @version 1.0
 *
 */
public class SingleFieldView extends LinearLayout {

    /**
     * 콩 이미지 뷰
     */
    ImageView beanImageView;

    /**
     * 콩 개수가 보여지는 텍스트 뷰
     */
    TextView beanNumTextView;

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
        inflater.inflate(R.layout.personal_single_field, this, true);

        beanImageView = findViewById(R.id.beanImageView);
        beanNumTextView = findViewById(R.id.beanNumTextView);
    }

}
