package trader.nw;

public class TestNwClient {

    public static void main(String[] args) {
        int port = 5280;
        int count = 0;
        Object obj = null;
        NwClient nwc = new NwClient("localhost", port);
        try {
            while (true) {
                // send object
                obj = "message" + count++;
                try {
                    nwc.send(obj);
                } catch (Exception e) {
                    System.out.println("TestNwClient " + e);
                }
                // receive object
                obj = null;
                try {
                    obj = nwc.receive();
                    System.out.println("TestNwClient received " + obj);
                } catch (Exception e) {
                    System.out.println("TestNwClient receive " + e);
                }
                Thread.sleep(4000);
            }
        } catch (Exception e) {
            System.out.println("TestNwClient: " + e);
        }
    }
}
