package com.example.bohnanza.client;

import com.example.bohnanza.BaseActivity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * 클라이언트 통신 기능
 *
 * @author YJH
 * @version 1.0
 *
 */
public class Client {

    private static final String serverHostIP = "124.54.164.216";
    private static final int portNumber = 55555;

    static ObjectOutputStream outStream;
    private static ObjectInputStream inStream;

    private BaseActivity activity;

    public Client(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * 통신 시작
     */
    public void start() {
        try {
            Socket socket = new Socket(serverHostIP, portNumber);

            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());

            (new ClientReceiver(inStream, activity)).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}