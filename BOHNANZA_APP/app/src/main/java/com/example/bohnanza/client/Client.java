package com.example.bohnanza.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private static final String serverHostIP = "124.54.164.216";
    private static final int portNumber = 55555;

    public void start() {
        try {
            Socket socket = new Socket(serverHostIP, portNumber);

            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
