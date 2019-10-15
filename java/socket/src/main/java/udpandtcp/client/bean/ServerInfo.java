package udpandtcp.client.bean;

public class ServerInfo {
	private String sn;
	private int port;
	private String address;

	public ServerInfo(int port, String ip, String sn) {
		this.port = port;
		this.address = ip;
		this.sn = sn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ServerInfo{" +
			"sn='" + sn + '\'' +
			", port=" + port +
			", address='" + address + '\'' +
			'}';
	}
}
