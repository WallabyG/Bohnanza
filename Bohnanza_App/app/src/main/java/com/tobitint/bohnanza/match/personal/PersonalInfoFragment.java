package com.tobitint.bohnanza.match.personal;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tobitint.bohnanza.R;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import game.cards.Beans;

/**
 *
 * 자신의 정보가 보이는 프래그먼트
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PersonalInfoFragment extends Fragment {

    /**
     * 플레이어 밭이 보여지는 뷰
     */
    FieldView field;

    /**
     * 플레이어 패가 보여지는 뷰
     */
    PlayerHandView playerHand;

    DisplayMetrics dm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_personal_info, container, false);

        field = rootView.findViewById(R.id.field);
        playerHand = rootView.findViewById(R.id.playerHand);

        dm = getResources().getDisplayMetrics();

        return rootView;
    }

    /**
     * 플레이어 패가 보여지는 뷰 초기화
     *
     * @param hands 플레이어 패
     */
    public void initPlayerHand(Queue<Beans> hands) {
        int handsNum = hands.size();

        int cardWidth = dp2px(40);
        int cardHeight = dp2px(60);
        int cardMargin = computeMargin();

        ListIterator<Beans> iterator = ((LinkedList) hands).listIterator(hands.size());

        while (iterator.hasPrevious()) {
            Beans bean = iterator.previous();


        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                dp2px(40),
                dp2px(60));


    }

    private int computeMargin() {
        return -10;
    }

    private void add

    private int dp2px(int dp) {
        return Math.round(dp * dm.density);
    }

}