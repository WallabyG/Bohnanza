package com.tobitint.bohnanza.match;

import android.content.Intent;
import android.os.Bundle;

import com.tobitint.bohnanza.BaseActivity;
import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.OkAlertDialog;
import com.tobitint.bohnanza.R;
import com.tobitint.bohnanza.match.common.CommonInfoFragment;
import com.tobitint.bohnanza.match.player.PlayerInfoFragment;
import com.tobitint.bohnanza.match.personal.PersonalInfoFragment;
import com.tobitint.bohnanza.match.trade.TradeInfoFragment;

import java.util.ArrayList;

import game.game.GameInfo;
import game.players.Player;

/**
 *
 * 게임 진행 액티비티
 *
 * @author YJH
 * @version 1.0
 *
 */
public class GameActivity extends BaseActivity {

    /**
     * 자신의 정보가 보이는 프래그먼트
     */
    PersonalInfoFragment personalInfoFragment;

    /**
     * 모든 플레이어에게 공통으로 보이는 프래그먼트
     */
    CommonInfoFragment commonInfoFragment;

    /**
     * 모든 플레이어의 정보가 보이는 프래그먼트
     */
    PlayerInfoFragment playerInfoFragment;

    /**
     * 거래 정보가 보이는 프래그먼트
     */
    TradeInfoFragment tradeInfoFragment;

    /**
     * 플레이어 이름
     */
    String playerName;

    /**
     * 플레이어 순서
     */
    private ArrayList<String> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        personalInfoFragment = (PersonalInfoFragment) getSupportFragmentManager().findFragmentById(R.id.personalInfoFragment);
        commonInfoFragment = (CommonInfoFragment) getSupportFragmentManager().findFragmentById(R.id.commonInfoFragment);
        playerInfoFragment = (PlayerInfoFragment) getSupportFragmentManager().findFragmentById(R.id.playerInfoFragment);
        tradeInfoFragment = (TradeInfoFragment) getSupportFragmentManager().findFragmentById(R.id.tradeInfoFragment);

        init();
    }

    private void init() {


        // Get player name
        playerName = ((InfoApplication) getApplicationContext()).getPlayerName();

        // Get intent
        Intent intent = getIntent();
        GameInfo gameInfo = (GameInfo) intent.getSerializableExtra("game info");

        // Init information
        orders = (ArrayList<String>) gameInfo.getOrders();

        // Init personalInfoFragment
        initPersonalInfoFragment(gameInfo.getPlayer(playerName));

        // Init playerInfoFragment
        for (String _playerName: orders) {
            initPlayerInfoFragment(gameInfo.getPlayer(_playerName));
        }

        playerInfoFragment.reapplyPlayerInfoListView();

        // Show player turn
        String[] ordinalNum = {"1st", "2nd", "3rd", "4th", "5th"};

        OkAlertDialog.show(this, "You are in the " + ordinalNum[orders.indexOf(playerName)] + " order.");
    }

    private void initPersonalInfoFragment(Player player) {

    }

    private void initPlayerInfoFragment(Player player) {
        playerInfoFragment.addPlayer(player);
    }

}
