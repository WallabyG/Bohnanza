package com.tobitint.bohnanza.match.player;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 플레이어의 정보가 보이는 리스트 뷰를 관리하는 어댑터
 *
 * @author YJH
 * @version 1.0
 *
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerItemViewHolder> {

    /**
     *
     * 플레이어의 정보가 보이는 뷰를 저장하는 뷰홀더
     *
     * @author YJH
     * @version 1.0
     *
     */
    public class PlayerItemViewHolder extends RecyclerView.ViewHolder {
        public PlayerItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public PlayerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerItemViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

/*
public class a extends BaseAdapter {

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
*/