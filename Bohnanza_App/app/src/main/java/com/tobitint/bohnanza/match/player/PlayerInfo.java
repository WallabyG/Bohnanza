package com.tobitint.bohnanza.match.player;

import java.util.List;

import game.players.Field;
import game.players.Player;

/**
 *
 * 플레이어 정보
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PlayerInfo {

    /**
     * 플레이어 이름
     */
    String name;

    /**
     * 플레이어 골드
     */
    int gold;

    /**
     * 플레이어 밭
     */
    List<Field> fields;

    public PlayerInfo(Player player) {
        name = player.getName();
        gold = player.getGold();
        fields = player.getFields();
    }

}
