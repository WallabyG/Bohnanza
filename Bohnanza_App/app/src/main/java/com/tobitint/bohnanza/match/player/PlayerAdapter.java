package com.tobitint.bohnanza.match.player;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 *
 * 플레이어의 정보가 보이는 리스트 뷰를 관리하는 어댑터
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PlayerAdapter extends BaseAdapter {

    ArrayList<PlayerItemView> itemViews = new ArrayList<>();

    @Override
    public int getCount() {
        return itemViews.size();
    }

    public void addItemView(PlayerItemView itemView) {
        itemViews.add(itemView);
    }

    @Override
    public Object getItem(int pos) {
        return null;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        return itemViews.get(pos);
    }

}
