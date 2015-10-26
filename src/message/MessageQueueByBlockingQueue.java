package message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueueByBlockingQueue implements MessageQueue {
	private BlockingQueue<String> msgQueue = new ArrayBlockingQueue(20);
	
	public void putMsg(String msg) {
		try {
			msgQueue.put(msg);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public String getMsg() {
		if (!msgQueue.isEmpty()) {
			try {
				String msg = msgQueue.take();
				return msg;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		}
		
		return null;
	}

}
