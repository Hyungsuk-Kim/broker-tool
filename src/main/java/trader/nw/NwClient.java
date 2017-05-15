package trader.nw;

import java.io.*;
import java.net.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("client")
class NwClient {
	@Value("localhost")
    private String host;
	@Value("5280")
    private int port;
    private Socket skt;
    private InputStream is;
    private ObjectInputStream ois;
    private OutputStream os;
    private ObjectOutputStream oos;
    private int retry;
    
    public NwClient() {}
    
    public NwClient(String host, int port) {
        //** 1 Initialize the corresponding host attribute
        this.host = host;
        //** 2 Initialize the corresponding port attribute
        this.port = port;
        //** 3 Invoke the connect() method
        connect();
    }

    public void send(Object obj) throws Exception {
        retry = 10;
        while (retry > 0) {
            try {
                //** 1. Invoke the writeObject method of oos with
                //**    obj as parameter
            	oos.writeObject(obj);
                retry = 0;
                System.out.println("NwClient.send: " + obj + "sent");
            } catch (Exception e) {
                System.out.println("NwClient.send: " + e);
                retry--;
                if (retry == 0) {
                    throw e;
                }
                Thread.sleep(6000);
                connect();
            }
        }
    }

    public Object receive() throws Exception {
        Object obj = null;
        try {
            if (ois == null) {
                //** 1. Create a new ObjectInputStream object with is
                //**    as the input parameter. Assign this to ois.
                //Q? Why is ois created here, rather than in the connect
                //   method, where all the other streams are created?
                ois = new ObjectInputStream(is);
            }
            //** 2. Invoke the readObject method of ois and assign the
            //**    return value to obj
            obj = ois.readObject();
        } catch (Exception e) {
            System.out.println("NwClient.receive: " + e);
            connect();
            throw e;
        }
        return obj;
    }

    public void connect() {
        try {
            //** 1. Create a new Socket with host, port as parameters
            //**    and assign this socket to skt.
            skt = new Socket(host, port);
            //** 2. Invoke getInputStream method on skt and assign the
            //**    return value to is.
            is = skt.getInputStream();
            //** 3. Assign null to ois.
            //Q? Why is ois not created here, but instead assigned to null?
            ois = null;
            //** 4. Invoke getOutputStream method on skt and assign
            //**     the return value to os.
            os = skt.getOutputStream();
            //** 5. Create a new ObjectOutputStream object with os as
            //**    parameter and assign this newly created object to oos.
            oos = new ObjectOutputStream(os);
        } catch (Exception e) {
            System.out.println("NwClient.connect: " + e);
        }
    }

    public void connect(String host, int port) {
        //** 1 Initialize the corresponding host attribute
    	this.host = host;
        //** 2 Initialize the corresponding port attribute
        this.port = port;
        //** 3 Invoke the connect() method
        connect();
    }

    public void close() {
		if (skt != null) {
			try {
				skt.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
    }
    
    @PostConstruct
    public void init() {
    	this.connect();
    }
    
    public void setPort(int port) {
    	this.port = port;
    }
    
    public void setHost(String host) {
    	this.host = host;
    }
}
