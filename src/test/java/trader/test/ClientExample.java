package trader.test;

import java.io.*;
import java.net.*;

class ClientExample {

    public static void main(String[] args) {
        String host;
        int port;
        Socket skt;
        InputStream is;
        ObjectInputStream ois;
        OutputStream os;
        ObjectOutputStream oos;
        Object obj = null;
        int count = 0;

        // intialize connection parameters
        host = "localhost";
        port = 5280;
        try {
            // establish connection
            skt = new Socket(host, port);
            is = skt.getInputStream();
            ois = null;
            os = skt.getOutputStream();
            oos = new ObjectOutputStream(os);

            while (true) {
                // send object
                obj = "message" + count++;
                try {
                    oos.writeObject(obj);
                    System.out.println("Client sent " + obj);
                } catch (Exception e) {
                    System.out.println("Client send " + e);
                    throw e;
                }
                // receive object
                obj = null;
                try {
                    if (ois == null) {
                        ois = new ObjectInputStream(is);
                    }
                    obj = ois.readObject();
                    System.out.println("Client received " + obj);
                } catch (Exception e) {
                    System.out.println("Client receive " + e);
                    throw e;
                }
                Thread.sleep(4000);
            }
        } catch (Exception e) {
            System.out.println("ClientExample: " + e.toString());
        }
    }
}
