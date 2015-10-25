package function;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import message.MessageQueue;
import myutil.XMLUtil;
import dboperation.*;
import mainframe.FrameAapter;
import mainframe.ServerFrame;

public class MonitorThread extends Thread {
	private FrameAapter frame;
	private ServerSocket server;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private MessageQueue queue;
	private String dividingLine = "*************************************\n";
	private int port = Integer.parseInt(XMLUtil.getServerPort(XMLUtil.createDocument()));
	
	public  MonitorThread(FrameAapter frame, MessageQueue queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
		this.frame = frame;
		
		try {			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName());
		try {
			server = new ServerSocket(port);
			frame.setText("�������񣬶˿�" + port +"\n");
			frame.setText(dividingLine);
			socket = server.accept();
			
			// ��Ӧ����ͻ���ͬʱҪ��
			Thread newThread = new MonitorThread(this.frame, this.queue);
			newThread.start();
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			String register = in.readLine();
			
			if (register.equalsIgnoreCase("register")) {
				String msg = socket.getInetAddress().getHostName() + "," +
							 socket.getInetAddress().getHostAddress() + "," +
							 socket.getPort();
				
				// ����frame��textarea��ֵ
				String text = socket.getInetAddress().getHostName() + "," +
						      socket.getInetAddress().getHostAddress() + "," + " ����ע������\n" +
						      dividingLine;
				frame.setText(text);
				
				// ����з���ע����Ϣ
				queue.putMsg(msg);
				// ��ȡ����������
				String data = queryFromDB(socket.getInetAddress().getHostAddress());
				// �����������û���Ϣ��ע���û�
				out.write(data);
				// RegisterForCLientThread������Ϣ
				new RegisterForClientThread(queue);
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
			// ���ݿ��ѯ
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
			
			// �����һ������
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
