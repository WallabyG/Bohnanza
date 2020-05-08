package com.tobitint.bohnanza.match.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.R;

import game.cards.Deck;

/**
 *
 * 모든 플레이어에게 공통으로 보이는 프래그먼트
 *
 * @author YJH
 * @version 1.0
 *
 */
public class CommonInfoFragment extends Fragment {

    /**
     * 덱 리필 횟수가 보여지는 텍스트 뷰
     */
    TextView refillNumTextView;

    /**
     * 드로우 카드 덱이 보여지는 뷰
     */
    CardView drawCard;

    /**
     * 버려진 카드 덱이 보여지는 뷰
     */
    CardView discardedCard;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_common_info, container, false);

        refillNumTextView = rootView.findViewById(R.id.refillNumTextView);
        drawCard = rootView.findViewById(R.id.drawCard);
        discardedCard = rootView.findViewById(R.id.discardedCard);

        return rootView;
    }

    /**
     * 덱 정보가 보여지는 뷰 초기화
     *
     * @param deck 덱 정보
     */
    public void initDeckInfo(Deck deck) {
        refillNumTextView.setText(((InfoApplication) getContext().getApplicationContext()).getOrdinalNumber(deck.getRefillNum()) + "\npile");

        drawCard.setBeanImageView(-1);
        drawCard.setBeanNumTextView(deck.getLeftCardNumber());

        discardedCard.setBeanImageView(0);
        discardedCard.setBeanNumTextView(deck.getDiscardedNumber());
    }

}
