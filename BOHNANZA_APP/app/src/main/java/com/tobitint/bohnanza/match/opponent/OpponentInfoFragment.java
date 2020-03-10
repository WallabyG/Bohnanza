package com.tobitint.bohnanza.match.opponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tobitint.bohnanza.R;

/**
 *
 * 상대 플레이어의 정보가 보이는 프래그먼트
 *
 * @author YJH
 * @version 1.0
 *
 */
public class OpponentInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_opponent_info, container, false);

        return rootView;
    }

}
