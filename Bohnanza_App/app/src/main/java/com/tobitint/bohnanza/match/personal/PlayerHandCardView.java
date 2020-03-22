package com.tobitint.bohnanza.match.personal;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.R;

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

        init();
    }

    public PlayerHandCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.player_hand_card_border));
    }

    /**
     * 해당하는 콩 이미지 설정
     */
    public void setBeanImage(int beanNumber) {
        int beanImageId = ((InfoApplication) getContext().getApplicationContext()).getBeanImageId(beanNumber);

        setImageResource(beanImageId);
    }

}
