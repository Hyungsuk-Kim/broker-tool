package trader.nw;

import java.io.*;
import java.net.*;
import trader.*;
import trader.db.*;
import trader.nw.cmd.*;

public class Broker3TierServer {

    private int port;
    private ServerSocket serverSocket;
    private BrokerModel broker;

    public Broker3TierServer(int port, BrokerModel broker) {
        //** 1. Initialize the corresponding port and broker attribute
        this.port = port;
        this.broker = broker;
        
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("BrokerServer created ServerSocket on port " + port);
            //** 2 Invoke the acceptConnection() method
            this.acceptConnection();
            
        } catch (Exception e) {
            System.out.println("BrokerServer constructor: " + e);
        }
    }

    public void acceptConnection() {
        Socket skt;
        System.out.println("BrokerServer.acceptConnection called");
        while (true) {
            try {
                skt = serverSocket.accept(); // listening;
                System.out.println("Connection + accepted");
                //** 3 Invoke the service() method with skt as param.
                this.service(skt);
                
            } catch (Exception e) {
                System.out.println("BrokerServer.acceptConnection: " + e);
            }
        }
    }

    private void service(Socket socket) {
        InputStream is;
        ObjectInputStream ois;
        OutputStream os;
        ObjectOutputStream oos;
		Command cmd = null;
        try {
            is = socket.getInputStream();
            ois = new ObjectInputStream(is);
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            while (true) {
                //** 4 Use the ois instance to call the readObject() method
                //** and retrieve the Command.
            	cmd = (Command) ois.readObject();
                
                System.out.println("BrokerServer.service " + cmd);
                //** 5 Invoking execute(broker) method on the cmd object
                cmd.execute(broker);
                //** 6 Invoking writeObject(cmd) method on the oos object
                oos.writeObject(cmd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            BrokerDAO brokerDAO = new BrokerDAOImpl();
            BrokerModel brokerModel = new BrokerModelImpl(brokerDAO);
            Broker3TierServer server = new Broker3TierServer(5280, brokerModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
