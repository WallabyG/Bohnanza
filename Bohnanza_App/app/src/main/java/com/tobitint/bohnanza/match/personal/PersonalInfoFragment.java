package com.tobitint.bohnanza.match.personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tobitint.bohnanza.BaseFragment;
import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.R;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import game.cards.Beans;
import game.players.Field;

/**
 *
 * 자신의 정보가 보이는 프래그먼트
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PersonalInfoFragment extends BaseFragment {

    /**
     * 플레이어 밭이 보여지는 뷰
     */
    FieldView fieldView;

    /**
     * 플레이어 패가 보여지는 뷰
     */
    PlayerHandView playerHandView;

    /**
     * 플레이어 패가 보여지는 뷰의 너비
     */
    int playerHandViewWidth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_personal_info, container, false);

        fieldView = rootView.findViewById(R.id.field);
        playerHandView = rootView.findViewById(R.id.playerHand);

        return rootView;
    }

    /**
     * 플레이어 밭이 보여지는 뷰 초기화
     *
     * @param fields 플레이어 밭
     */
    public void initFieldView(List<Field> fields) {
        fieldView.setSingleFieldViews(fields);
    }

    /**
     * 플레이어 패가 보여지는 뷰 초기화
     *
     * @param hands 플레이어 패
     */
    public void initPlayerHand(Queue<Beans> hands) {
        int handsNum = hands.size();

        int cardWidth = ((InfoApplication) getContext().getApplicationContext()).dp2px(50);
        int cardHeight = ((InfoApplication) getContext().getApplicationContext()).dp2px(75);
        int cardDefaultMargin = computeCardDefaultMargin(handsNum, cardWidth);
        int cardMargin = cardDefaultMargin * (handsNum - 1);

        playerHandView.removeAllViews();

        ListIterator<Beans> iterator = ((LinkedList) hands).listIterator(hands.size());

        while (iterator.hasPrevious()) {
            Beans bean = iterator.previous();

            addCardToPlayerHand(bean, cardWidth, cardHeight, cardMargin);

            cardMargin -= cardDefaultMargin;
        }

        playerHandView.invalidate();
    }

    private int computeCardDefaultMargin(int handsNum, int cardWidth) {
        float minimumOverlapRatio = 1.0f / 6;

        int requiredWidth = cardWidth * handsNum - Math.round(cardWidth * minimumOverlapRatio) * (handsNum - 1);

        if (playerHandViewWidth >= requiredWidth) {
            return cardWidth - Math.round(cardWidth * minimumOverlapRatio);
        }

        int overlapWidth = (int) Math.ceil((double) (cardWidth * handsNum - playerHandViewWidth) / (handsNum - 1));

        return cardWidth - overlapWidth;
    }

    private void addCardToPlayerHand(Beans bean, int width, int height, int margin) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.leftMargin = margin;

        PlayerHandCardView cardView = new PlayerHandCardView(getContext());

        cardView.setBeanImage(bean.getNumber());
        cardView.setLayoutParams(params);

        playerHandView.addView(cardView);
    }

    public void getPlayerHandViewWidth() {
        playerHandViewWidth = playerHandView.getWidth();
    }

}
