package mainframe;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.Set;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.Option;

import function.MonitorThread;
import function.UpdateDBThread;
import function.UpdateUserListThread;
import message.MessageQueue;
import message.MessageQueueByBlockingQueue;
import myutil.FlagClass;
import myutil.ShowTimeTask;
import myutil.XMLUtil;

public class ServerFrame extends FrameAdapter implements Runnable {
	   private JPanel bkPanel;   			// 总体的panel
	   private JPanel onlineListPanel;      //  左边的panel
	   private JPanel onlineTigPanel;
	   private JPanel loggerPanel;    		//  右边的panel 
	   private JPanel loggerPanelB1;
	   private JPanel timePanel;       		// 下边的时间panel
	   private JLabel TagLabelForonLineList;
	   private JLabel serverLogLabel;
	   private JLabel timeLabel;
	   private JLabel tigLabel;
	   private JLabel userNumLabel;      //在线人数
	   
	   private JScrollPane leftScrollPanel;
	   private DefaultListModel<String> listmodel;
	   private JList<String> jlist;
	   
	   private JScrollPane rightScrollPane;
	   private JTextArea loggerTextArea;	   
	   
//	   ServerThread serverThread=null;   //服务器的线程类
	   
//	   Border border0 = BorderFactory.createLineBorder(UIManager.getColor("INternalFrame.inactiveTitleBackground"), 1);
//	   Border border1;  
	   
	   private MessageQueue queue = new MessageQueueByBlockingQueue();
	   private Hashtable<String, String> userTable = new Hashtable<String, String>();

	 public ServerFrame(){
		 
		 try {
			
			 Init();
//			 serverThread=new ServerThread(loggerTextArea);
//			 serverThread.start();
			 //下面在签中动态的显示时间
			 java.util.Timer mytimeer1 = new java.util.Timer();
			 java.util.TimerTask task1 = new ShowTimeTask(timeLabel); 
			 mytimeer1.schedule(task1, 0,1000);
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	 }

	private void Init() {
		// TODO Auto-generated method stub
		bkPanel = new JPanel(new GridBagLayout());
		
		//左边的panel
		onlineListPanel = new JPanel(new BorderLayout());
		onlineListPanel.setBorder(BorderFactory.createEtchedBorder());
		onlineListPanel.setPreferredSize(new Dimension(192, 150));
		
		TagLabelForonLineList = new JLabel("在线用户列表",JLabel.CENTER);
     	
		leftScrollPanel = new JScrollPane();
		
		listmodel = new DefaultListModel();
		
		jlist = new JList(listmodel);
		leftScrollPanel.getViewport().add(jlist);  //显示列表的加载
		
		onlineTigPanel=new JPanel(new GridBagLayout());
		
		tigLabel = new JLabel("在线人数:");
		userNumLabel = new JLabel();

		onlineTigPanel.add(tigLabel);
		onlineTigPanel.add(userNumLabel);
		
		onlineListPanel.add(TagLabelForonLineList,BorderLayout.NORTH);
		onlineListPanel.add(leftScrollPanel,BorderLayout.CENTER);
		onlineListPanel.add(onlineTigPanel,BorderLayout.SOUTH);
		
		//右边布局
		loggerPanel = new JPanel(new BorderLayout());
		loggerPanel.setBorder(BorderFactory.createEtchedBorder());
		serverLogLabel = new JLabel("服务器的日志",JLabel.LEFT);
		rightScrollPane = new JScrollPane();
		loggerTextArea = new JTextArea();
		loggerTextArea.setEditable(false);
		
		rightScrollPane.getViewport().add(loggerTextArea);      //显示服务器日志内容
		
		loggerPanel.add(serverLogLabel,BorderLayout.NORTH);
		loggerPanel.add(rightScrollPane,BorderLayout.CENTER);
//		loggerPanel.add(loggerPanelB1,BorderLayout.SOUTH);
		
		//最下边的时间布局
	    timeLabel = new JLabel();
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setText("timeLabeltxt");
		
		bkPanel.add(onlineListPanel,new GridBagConstraints(0,0,1,1,0.3,0.9,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),10,186));
		bkPanel.add(loggerPanel,new GridBagConstraints(1,0,1,1,0.7,0.9,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),300,334));
		bkPanel.add(timeLabel,new GridBagConstraints(0,1,2,1,1.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),570,7));
		
		
		this.add(bkPanel);
		this.setTitle("P2PServer");
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("icon/frame.png"));
		this.setSize(new Dimension(640, 475));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				queue = null;
			}
			
		});
	}
	
	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		loggerTextArea.append(text);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	private MessageQueue getMsgQueue() {
		return this.queue;
	}
	
	public Hashtable<String, String> getUserTable() {
		return this.userTable;
	}
	
	public void updateUserList() {
		listmodel.removeAllElements();
		Set<String> keys = userTable.keySet();
		for (String key : keys) {
			listmodel.addElement(userTable.get(key));
		}
		
		tigLabel.setText("在线人数：" + keys.size());
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		ServerFrame frame =new ServerFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UpdateDBThread t1 = new UpdateDBThread(frame);		
		t1.start();
		
		UpdateUserListThread t2 = new UpdateUserListThread(frame);
		t2.start();		
		
		int port = Integer.parseInt(XMLUtil.getServerPort(XMLUtil.createDocument()));
		ServerSocket serverSocket = new ServerSocket(port);
		String dividingLine = "*************************************\n";
		frame.setText("启动服务，端口" + port +"\n");
		frame.setText(dividingLine);

		while (true) {
			MonitorThread t = new MonitorThread(serverSocket.accept(), frame, frame.getMsgQueue());
			t.start();
		}
		
	}	

}
