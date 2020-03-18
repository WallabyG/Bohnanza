package com.tobitint.bohnanza.match.personal;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.tobitint.bohnanza.InfoApplication;

/**
 *
 * 플레이어 패의 카드 뷰
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PlayerHandCardView extends AppCompatImageView {

    public PlayerHandCardView(Context context) {
        super(context);
    }

    public PlayerHandCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 해당하는 콩 이미지 설정
     */
    public void setBeanImage(int beanNumber) {
        int beanImageId = ((InfoApplication) getContext().getApplicationContext()).getBeanImageId(beanNumber);

        setImageResource(beanImageId);
    }

}
