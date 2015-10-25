package myutil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import javax.swing.JLabel;

public class ShowTimeTask extends TimerTask {
	private JLabel showTime =null;

  
	public ShowTimeTask(JLabel showTime) {
		// TODO Auto-generated constructor stub
		this.showTime = showTime;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Date time = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:dd:ss");
		String timeinfo = format.format(time);
		showTime.setText("现在时间："+timeinfo+"   ");
	}


}
