package function;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JOptionPane;

import message.MessageQueue;
import myutil.XMLUtil;
import dboperation.*;
import mainframe.FrameAdapter;
import mainframe.ServerFrame;

public class MonitorThread extends Thread {
	private FrameAdapter frame;
	private ServerSocket server;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private MessageQueue queue;
	private String dividingLine = "*************************************\n";
	private Hashtable<String, String> userTable;
	
	public  MonitorThread(Socket socket, FrameAdapter frame, MessageQueue queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
		this.frame = frame;
		this.socket = socket;
		this.userTable = frame.getUserTable();
		
		try {			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		System.out.println(Thread.currentThread().getName());
		try {			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			String register = in.readLine();
			
			if (register.startsWith("register") || register.startsWith("REGISTER")) {
				// 设置frame的textarea的值
				String text = socket.getInetAddress().getHostName() + "," +
						      socket.getInetAddress().getHostAddress() + "," + " 发来注册请求\n" +
						      dividingLine;
				
				frame.setText(text);
				
				// 读取服务器数据
				String data = queryFromDB(socket.getInetAddress().getHostAddress());
				// 并传送在线用户消息给注册用户
				out.write(data);
				
				String receivePort = register.substring(8);
				
				String msg = socket.getInetAddress().getHostName() + "," +
							 socket.getInetAddress().getHostAddress() + "," +
							 receivePort;
				
				// 向队列发送注册信息
				queue.putMsg(msg);
				
				userTable.put(socket.getInetAddress().getHostAddress(), msg);
				
				// RegisterForCLientThread处理消息
				RegisterForClientThread t = new RegisterForClientThread(queue);
				t.run();
			}
			
			out.flush();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String queryFromDB(String ip) {
		ILDBLinker dbLinker = new MySqlLinker();
		String urlPrefix = XMLUtil.getDBLinkURLPrefix(XMLUtil.createDocument());
		String url = urlPrefix + XMLUtil.getDBName(XMLUtil.createDocument());
		String user = XMLUtil.getDBUserName(XMLUtil.createDocument());
		String password = XMLUtil.getDBPwd(XMLUtil.createDocument());
		String dbname = XMLUtil.getDBName(XMLUtil.createDocument());
		String tablename = XMLUtil.getUserInfoTableName(XMLUtil.createDocument());
		
		if (null == ip || ip.equals("")) {
			return "";
		}
		
		try {
			Connection conn = dbLinker.link(url, user, password);
			ResultSet rs;
			String stmt = "select * from " + tablename + " where ipv4 !=  " + "\'" + ip + "\'";
			String data = "";
			// 数据库查询
			rs = dbLinker.getData(conn, stmt);
			
			if (!rs.next()) {
				dbLinker.closeConnection(rs, conn);
				dbLinker = null;
				return "";
			}
			
			rs.beforeFirst();
			
			while (rs.next()) {
				data += (rs.getString(2) + ",");  // name
				data += (rs.getString(1) + ",");   // ip
				data += (rs.getString(3) + ","); // port 		 		
			}
			
			// 最后会多一个逗号
			if (',' == data.charAt(data.length() - 1)) {
				StringBuffer temp = new StringBuffer(data);
				temp.deleteCharAt(data.length() - 1);
				data = temp.toString();
			}
			
			dbLinker.closeConnection(rs, conn);
			dbLinker = null;
			return data.toString();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in class MonitorThread in function query\n" +
								e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return "";
		
	}

}
