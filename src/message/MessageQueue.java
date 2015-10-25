package message;

public interface MessageQueue {
	public void putMsg(String msg);
	public String getMsg();
	
}
