package trader.nw.cmd;

import trader.*;
import java.io.*;

public abstract class Command implements Serializable {
	private static final long serialVersionUID = -7853346418766749203L;
	
	protected Object result = null;
    protected Exception excep = null;

    public abstract void execute(BrokerModel broker);

    /**
     * @exception  BrokerException  
     *     if another record has same primary key
     *     or no record is found in the table
     */
    public Object getResult() throws Exception {
        if (this.excep != null) {
            throw this.excep;
        }
        return this.result;
    }
}
