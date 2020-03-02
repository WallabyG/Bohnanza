package com.example.bohnanza.client;

import android.os.AsyncTask;

import java.io.ObjectOutputStream;

import server.message.Message;

class ClientSender extends AsyncTask<Message, Void, Void> {

    ObjectOutputStream outStream;

    public ClientSender(ObjectOutputStream outStream) {
        this.outStream = outStream;
    }

    @Override
    protected Void doInBackground(Message... messages) {
        try {
            outStream.writeObject(messages[0]);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
