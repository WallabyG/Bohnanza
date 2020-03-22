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

    boolean stateShowPlayerTurn = false;

    /**
     * 게임 정보
     */
    GameInfo gameInfo;

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        personalInfoFragment.getPlayerHandViewWidth();

        // Init personalInfoFragment
        initPersonalInfoFragment(gameInfo.getPlayer(playerName));

        // Show player turn
        if (!stateShowPlayerTurn) {
            OkAlertDialog.show(this, "You are in the " + ((InfoApplication) getApplicationContext()).getOrdinalNumber(orders.indexOf(playerName)) + " order.");

            stateShowPlayerTurn = true;
        }
    }

    private void init() {
        // Get player name
        playerName = ((InfoApplication) getApplicationContext()).getPlayerName();

        // Get intent
        Intent intent = getIntent();
        gameInfo = (GameInfo) intent.getSerializableExtra("game info");

        // Init information
        orders = (ArrayList<String>) gameInfo.getOrders();

        // Init playerInfoFragment
        for (String _playerName: orders) {
            initPlayerInfoFragment(gameInfo.getPlayer(_playerName));
        }
    }

    /**
     * 자신의 정보가 보이는 프래그먼트 초기화
     *
     * @param player 플레이어
     */
    private void initPersonalInfoFragment(Player player) {
        personalInfoFragment.initPlayerHand(player.getHands());
    }

    /**
     * 모든 플레이어의 정보가 보이는 프래그먼트 초기화
     *
     * @param player 플레이어
     */
    private void initPlayerInfoFragment(Player player) {
        playerInfoFragment.addPlayer(player);
    }

    @Override
    public void onBackPressed() {
    }

}
