package mainframe;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.Option;

public class ServerFrame extends JFrame implements ActionListener{
	   JPanel bkPanel;   			// 总体的panel
	   JPanel onlineListPanel;      //  左边的panel
	   JPanel onlineTigPanel;
	   JPanel loggerPanel;    		//  右边的panel 
	   JPanel loggerPanelB1;
	   JPanel timePanel;       		// 下边的时间panel
	   
	   JLabel onLineList;
	   JLabel serverLogLabel;
	   JLabel timeLabel;
	   JLabel tigLabel;
	   JLabel userNumLabel;      //在线人数
	   
	   JScrollPane leftScrollPanel;
	   DefaultListModel listmodel;
	   JList jlist;
	   
	   JScrollPane rightScrollPane;
	   JTextArea loggerTextArea;	   
	   
//	   ServerThread serverThread=null;   //服务器的线程类
	   
	   Border border0 = BorderFactory.createLineBorder(UIManager.getColor("INternalFrame.inactiveTitleBackground"), 1);
	   Border border1;      
	   
	   
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerFrame server =new ServerFrame();
	}

	 public ServerFrame(){
		 
		 try {
			
			 jbInit();
//			 serverThread=new ServerThread(loggerTextArea);
//			 serverThread.start();
			 //下面在签中动态的显示时间
//			 java.util.Timer mytimeer1=new java.util.Timer();
//			 java.util.TimerTask task1=new showTimeTask(timeLabel);
//			 mytimeer1.schedule(task1, 0,1000);
//		     ServerLoginUser userlsit=new ServerLoginUser(listmodel,userNumLabel);
//		     userlsit.updateseruserlist("");
//			// mytimeer2.schedule(task2, 0,1000); //10秒钟刷新一次
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	 }

	private void jbInit() {
		// TODO Auto-generated method stub
		bkPanel=new JPanel(new GridBagLayout());
		
		//左边的panel
		onlineListPanel = new JPanel(new BorderLayout());
		onlineListPanel.setBorder(BorderFactory.createEtchedBorder());
		onlineListPanel.setPreferredSize(new Dimension(192, 150));
		
     	onLineList = new JLabel("在线用户列表",JLabel.CENTER);
     	
		leftScrollPanel=new JScrollPane();
		
		listmodel=new DefaultListModel();
		
		jlist=new JList(listmodel);
		leftScrollPanel.getViewport().add(jlist);  //显示列表的加载
		
		onlineTigPanel=new JPanel(new GridBagLayout());
		
		tigLabel = new JLabel("在线人数:");
		userNumLabel = new JLabel();

		onlineTigPanel.add(tigLabel);
		onlineTigPanel.add(userNumLabel);
		
		onlineListPanel.add(onLineList,BorderLayout.NORTH);
		onlineListPanel.add(leftScrollPanel,BorderLayout.CENTER);
		onlineListPanel.add(onlineTigPanel,BorderLayout.SOUTH);
		
		//右边布局
		loggerPanel=new JPanel(new BorderLayout());
		loggerPanel.setBorder(BorderFactory.createEtchedBorder());
		serverLogLabel=new JLabel("服务器的日志",JLabel.LEFT);
		rightScrollPane =new JScrollPane();
		loggerTextArea=new JTextArea();
		rightScrollPane.getViewport().add(loggerTextArea);      //显示服务器日志内容
		
		loggerPanel.add(serverLogLabel,BorderLayout.NORTH);
		loggerPanel.add(rightScrollPane,BorderLayout.CENTER);
//		loggerPanel.add(loggerPanelB1,BorderLayout.SOUTH);
		
		//最下边的时间布局
	    timeLabel=new JLabel();
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setText("timeLabeltxt");
		
		bkPanel.add(onlineListPanel,new GridBagConstraints(0,0,1,1,0.3,0.9,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),10,186));
		bkPanel.add(loggerPanel,new GridBagConstraints(1,0,1,1,0.7,0.9,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),300,334));
		bkPanel.add(timeLabel,new GridBagConstraints(0,1,2,1,1.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),570,7));
		
		
		this.add(bkPanel);
		this.setTitle("P2PServer");
		this.setSize(new Dimension(640, 475));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
