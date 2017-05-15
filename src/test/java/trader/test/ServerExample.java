package trader.test;

import java.io.*;
import java.net.*;

public class ServerExample {

    private int port;
    private ServerSocket serverSocket;
    private Socket skt;

    public ServerExample(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server created SeverSocket on port " + port);
            acceptConnection();
        } catch (Exception e) {
            System.out.println("Server.constructor: " + e);
        }
    }

    public void acceptConnection() {
        System.out.println("NwServer.acceptConnection called");
        while (true) {
            try {
                skt = serverSocket.accept(); // listening;
                System.out.println("Server.acceptCon + accepted");
                service(skt);
            } catch (Exception e) {
                System.out.println("NwServer.acceptCon " + e);
            }
        }
    }

    private void service(Socket socket) {
        InputStream is;
        ObjectInputStream ois;
        OutputStream os;
        ObjectOutputStream oos;
        try {
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            while (true) {
                Object obj = ois.readObject();
                System.out.println("Server.service " + obj);
                oos.writeObject("reply " + obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerExample server = new ServerExample(5280);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
