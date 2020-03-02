package com.example.bohnanza.client;

import android.os.AsyncTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.message.Message;

/**
 *
 * 서버로 메시지 전송 기능
 *
 * @author YJH
 * @version 1.0
 *
 */
public class ClientTask extends AsyncTask<Message, Void, Void> {

    public static final String serverHostIP = "124.54.164.216";
    public static final int portNumber = 55555;

    /**
     * 서버로부터 수신한 응답
     */
    protected Object returnObj = null;

    @Override
    protected Void doInBackground(Message... messages) {
        try {
            Socket socket = new Socket(serverHostIP, portNumber);

            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(messages[0]);
            outStream.flush();

            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            returnObj = inStream.readObject();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
