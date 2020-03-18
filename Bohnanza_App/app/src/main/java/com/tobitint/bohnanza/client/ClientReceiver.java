package com.tobitint.bohnanza.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.tobitint.bohnanza.BaseActivity;
import com.tobitint.bohnanza.InfoApplication;
import com.tobitint.bohnanza.LoginActivity;
import com.tobitint.bohnanza.match.onlineMatch.CreateOnlineMatchActivity;
import com.tobitint.bohnanza.match.onlineMatch.JoinOnlineMatchOptionActivity;
import com.tobitint.bohnanza.match.onlineMatch.NewOnlineMatchOptionActivity;
import com.tobitint.bohnanza.match.onlineMatch.WaitOnlineMatchActivity;

import java.io.ObjectInputStream;

import game.game.GameInfo;
import server.message.Message;

/**
 *
 * 클라이언트 통신 - 수신 기능
 *
 * @author YJH
 * @version 1.0
 *
 */
class ClientReceiver extends AsyncTask<Void, Message, Void> {

    private ObjectInputStream inStream;

    private BaseActivity activity;

    ClientReceiver(ObjectInputStream inStream, BaseActivity activity) {
        this.inStream = inStream;
        this.activity = activity;
    }

    /**
     * 수신된 메시지 처리<br>
     * <br>
     * 메시지 타입<br>
     * <br>
     * 1 - 로그인<br>
     * 101 - 플레이어 이름 중복 여부<br>
     * <br>
     * 2 - 온라인 매치 생성/접속<br>
     * 201 - 매치 이름 중복 여부<br>
     * 203 - 온라인 매치 삭제<br>
     * 211 - 온라인 매치 접속 성공 여부<br>
     * 212 - 온라인 매치 정보<br>
     * 221 - 온라인 매치 접속 플레이어 수<br>
     * <br>
     * 3 - 온라인 매치 정보<br>
     * 301 - 온라인 매치 초기 정보
     *
     * @param message 수신된 메시지
     */
    private void processMessage(Message message) {
        Activity currentActivity = ((InfoApplication) activity.getApplicationContext()).getCurrentActivity();

        Object contents = message.getContents();

        switch (message.getMessageType()) {
            case 101:
                ((LoginActivity) currentActivity).applyPlayerNameDuplicate((boolean) contents);
                break;

            case 201:
                ((NewOnlineMatchOptionActivity) currentActivity).applyMatchNameDuplicate((boolean) contents);
                break;

            case 203:
                if (currentActivity instanceof WaitOnlineMatchActivity) {
                    ((WaitOnlineMatchActivity) currentActivity).exitMatchByMatchDeleted();
                }

                break;

            case 211:
               ((JoinOnlineMatchOptionActivity) currentActivity).applyMatchInfoValid((int) contents);
                break;

            case 212:
                ((WaitOnlineMatchActivity) currentActivity).applyPlayerNumber((int) contents);
                break;

            case 221:
                if (currentActivity instanceof CreateOnlineMatchActivity) {
                    ((CreateOnlineMatchActivity) currentActivity).applyCurrentPlayerNumber((int) contents);
                } else if (currentActivity instanceof WaitOnlineMatchActivity) {
                    ((WaitOnlineMatchActivity) currentActivity).applyCurrentPlayerNumber((int) contents);
                }

                break;

            case 301:
                if (currentActivity instanceof CreateOnlineMatchActivity) {
                    ((CreateOnlineMatchActivity) currentActivity).createMatchSuccess((GameInfo) contents);
                } else if (currentActivity instanceof WaitOnlineMatchActivity) {
                    ((WaitOnlineMatchActivity) currentActivity).createMatchSuccess((GameInfo) contents);
                }

                break;
        }
    }

    @Override
    protected void onProgressUpdate(Message... messages) {
        processMessage(messages[0]);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            while (inStream != null) {
                Message message = (Message) inStream.readObject();

                publishProgress(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
