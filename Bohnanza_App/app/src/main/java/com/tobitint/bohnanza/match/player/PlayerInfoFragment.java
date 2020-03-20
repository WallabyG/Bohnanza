package com.tobitint.bohnanza.match.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tobitint.bohnanza.BaseFragment;
import com.tobitint.bohnanza.R;

import java.util.HashMap;

import game.players.Player;

/**
 *
 * 모든 플레이어의 정보가 보이는 프래그먼트
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PlayerInfoFragment extends BaseFragment {

    /**
     * 각 플레이어의 PlayerItemView 객체를 저장하는 맵
     */
    private HashMap<String, PlayerInfoView> playerItemViewMap = new HashMap<>();

    /**
     * 플레이어의 정보가 보이는 레이아웃
     */
    LinearLayout playerInfoLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_player_info, container, false);

        playerInfoLayout = rootView.findViewById(R.id.playerInfoLayout);

        initDm();

        return rootView;
    }

    /**
     * 플레이어 추가
     *
     * @param player 플레이어
     */
    public void addPlayer(Player player) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = dp2px(20);

        PlayerInfoView playerInfoView = new PlayerInfoView(getContext());

        playerInfoView.setPlayerNameTextView(player.getName());
        playerInfoView.setPlayerGoldTextView(player.getGold());
        playerInfoView.setHandsNum(player.getHands().size());

        playerInfoLayout.addView(playerInfoView);
    }

    /**
     * 플레이어의 정보가 보이는 뷰 재적용
     */
    public void reapplyPlayerInfoView(String playerName) {
        playerItemViewMap.get(playerName).invalidate();
    }

}
