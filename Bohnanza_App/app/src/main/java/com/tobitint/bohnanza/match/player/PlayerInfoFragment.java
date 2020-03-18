package com.tobitint.bohnanza.match.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
public class PlayerInfoFragment extends Fragment {

    /**
     * 각 플레이어의 playerItemView 객체를 저장하는 맵
     */
    private HashMap<String, PlayerItemView> playerItemViewMap = new HashMap<>();

    /**
     * 플레이어의 정보가 보이는 리스트 뷰를 관리하는 어댑터
     */
    PlayerAdapter playerAdapter = new PlayerAdapter();

    /**
     * 플레이어의 정보가 보이는 리스트 뷰
     */
    ListView playerInfoListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_player_info, container, false);

        playerInfoListView = rootView.findViewById(R.id.playerInfoListView);

        playerInfoListView.setAdapter(playerAdapter);

        return rootView;
    }

    /**
     * 플레이어 추가
     *
     * @param player 플레이어
     */
    public void addPlayer(Player player) {
        PlayerItemView playerItemView = new PlayerItemView(getContext());

        playerItemView.setPlayerNameTextView(player.getName());
        playerItemView.setPlayerGoldTextView(player.getGold());
        playerItemView.setHandsNum(player.getHands().size());

        playerAdapter.addItemView(playerItemView);
    }

    /**
     * 플레이어의 정보가 보이는 리스트 뷰 재적용
     */
    public void reapplyPlayerInfoListView() {
        playerAdapter.notifyDataSetChanged();
    }

}
