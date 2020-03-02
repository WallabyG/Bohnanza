package com.example.bohnanza.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bohnanza.R;

/**
 *
 * 모든 플레이어에게 공통으로 보이는 프래그먼트
 *
 * @author YJH
 * @version 1.0
 *
 */
public class CommonInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_common_info, container, false);

        return rootView;
    }

}
