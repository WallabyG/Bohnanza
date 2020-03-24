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
 * 플레이어 싱글 밭 뷰 in playerInfoFragment
 *
 * @author YJH
 * @version 1.0
 *
 */
public class SingleFieldView extends LinearLayout {

    /**
     * 플레이어 싱글 밭 ID
     */
    private String id;

    /**
     * 콩 이름이 보여지는 텍스트 뷰
     */
    TextView beanNameTextView;

    /**
     * 콩 가치가 보여지는 텍스트 뷰
     */
    TextView beanValueTextView;

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
        inflater.inflate(R.layout.player_single_field, this, true);

        beanNameTextView = findViewById(R.id.beanNameTextView);
        beanValueTextView = findViewById(R.id.beanValueTextView);
        beanNumTextView = findViewById(R.id.beanNumTextView);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeanNameTextView(String beanName) {
        beanNameTextView.setText(beanName);
    }

    public void setBeanValueTextView(int beanValue) {
        beanValueTextView.setText(String.valueOf(beanValue));
    }

    public void setBeanNumTextView(int beanNum) {
        beanNumTextView.setText(String.valueOf(beanNum));
    }

    public void setEmpty() {
        beanNameTextView.setText("empty");
        beanValueTextView.setText(null);
        beanNumTextView.setText(null);
    }

}
