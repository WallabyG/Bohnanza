package com.tobitint.bohnanza.client;

import android.os.AsyncTask;
import android.util.Log;

import com.tobitint.bohnanza.BaseActivity;

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
public class Client extends Thread {

    private static final String serverHostIP = "147.46.211.57";
    private static final int portNumber = 55555;

    static ObjectOutputStream outStream;

    private BaseActivity activity;

    public Client(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * 통신 시작
     */
    @Override
    public void run() {
        try {
            Socket socket = new Socket(serverHostIP, portNumber);

            outStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

            (new ClientReceiver(inStream, activity)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}