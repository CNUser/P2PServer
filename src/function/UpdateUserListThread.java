package function;

import java.util.Hashtable;

import mainframe.*;
import myutil.FlagClass;

public class UpdateUserListThread extends Thread {
	private FrameAdapter frame;
	private Hashtable<String, String> userTable;
	
	public UpdateUserListThread(FrameAdapter frame) {
		// TODO Auto-generated constructor stub
		this.frame = frame;
		this.userTable = frame.getUserTable();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (FlagClass.Flag) {
			frame.updateUserList();	
			
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

		
}
