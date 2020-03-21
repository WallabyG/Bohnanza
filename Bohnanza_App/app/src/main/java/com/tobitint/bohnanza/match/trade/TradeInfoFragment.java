package com.tobitint.bohnanza.match.trade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tobitint.bohnanza.BaseFragment;
import com.tobitint.bohnanza.R;

/**
 *
 * 거래 정보가 보이는 프래그먼트
 *
 * @author YJH
 * @version 1.0
 *
 */
public class TradeInfoFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_trade_info, container, false);

        return rootView;
    }
}
