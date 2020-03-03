package com.example.bohnanza.client;

import android.os.AsyncTask;

import java.io.ObjectOutputStream;

import server.message.Message;

/**
 *
 * 클라이언트 통신 - 송신 기능
 *
 * @author YJH
 * @version 1.0
 *
 */
public class ClientSender extends AsyncTask<Void, Void, Void> {

    /**
     * 송신할 메시지
     */
    private Message message;

    private ObjectOutputStream outStream;

    public ClientSender(Message message) {
        this.message = message;
        this.outStream = Client.outStream;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            outStream.writeObject(message);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
