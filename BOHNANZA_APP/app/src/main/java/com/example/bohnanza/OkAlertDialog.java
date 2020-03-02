package com.example.bohnanza;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

/**
 *
 * 확인 버튼만 존재하는 알림 대화상자
 *
 * @author YJH
 * @version 1.0
 *
 */
public class OkAlertDialog {

    /**
     * 알림 대화상자 띄우기
     *
     * @param context context
     * @param message 알림 대화상자에 띄울 메시지
     */
    public static void show(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) { }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
