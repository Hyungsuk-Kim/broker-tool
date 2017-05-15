package trader;

import java.io.Serializable;

public class Customer implements Serializable{
	private static final long serialVersionUID = 9009295034159605987L;
	
	private String id;
	private String name;
	private String addr;
	
	public Customer(String id, String name, String addr) {
		this.id = id;
		this.name = name;
		this.addr = addr;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", addr=" + addr + "]";
	}

	public Customer(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddr() {
		return addr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
}
