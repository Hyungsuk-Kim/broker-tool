package trader.nw;

import java.io.*;
import java.net.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import trader.*;
import trader.db.*;
import trader.nw.cmd.*;

@Component("server")
public class Broker3TierMultiServer {
	@Value("5280")
    private int port;
	@Autowired
	@Qualifier("brokerModel")
	private BrokerModel broker;
    private ServerSocket serverSocket;
    
    public Broker3TierMultiServer(){}
    
    public Broker3TierMultiServer(int port, BrokerModel broker) {
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
            	while (true) {
            		skt = serverSocket.accept();
            		new ServerThread(this, skt).start();
            	}
            } catch (Exception e) {
                System.out.println("BrokerServer.acceptConnection: " + e);
            }
        }
    }

    /*private void service(Socket socket) {
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
    }*/

    class ServerThread extends Thread {
    	
    	private Broker3TierMultiServer server;
    	private Socket socket;
    	private ObjectInputStream ois;
    	private ObjectOutputStream oos;
    	
    	public ServerThread(Broker3TierMultiServer server, Socket socket) {
    		this.server = server;
    		this.socket = socket;
    	}
    	
    	@Override
    	public void run() {
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
    }
    
    public void setPort(int port){
    	this.port = port;
    }
    
    @PostConstruct
    public void init() {
    	try {
            serverSocket = new ServerSocket(port);
            System.out.println("BrokerServer created ServerSocket on port " + port);
            //** 2 Invoke the acceptConnection() method
            this.acceptConnection();
            
        } catch (Exception e) {
            System.out.println("BrokerServer constructor: " + e);
        }
    }
    
    public static void main(String[] args) {
        try {
            /*BrokerDAO brokerDAO = new BrokerDAOImpl();
            BrokerModel brokerModel = new BrokerModelImpl(brokerDAO);
            Broker3TierMultiServer server = new Broker3TierMultiServer(5280, brokerModel);*/
        	ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext_server.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
