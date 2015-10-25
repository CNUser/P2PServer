package function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import dboperation.ILDBLinker;
import dboperation.MySqlLinker;
import message.*;
import myutil.XMLUtil;

public class RegisterForClientThread implements Runnable {
	private MessageQueue queue;
	private ILDBLinker dbLinker = new MySqlLinker();
	private String urlPrefix = XMLUtil.getDBLinkURLPrefix(XMLUtil.createDocument());
	private String url = urlPrefix + XMLUtil.getDBName(XMLUtil.createDocument());
	private String user = XMLUtil.getDBUserName(XMLUtil.createDocument());
	private String password = XMLUtil.getDBPwd(XMLUtil.createDocument());
	private String dbname = XMLUtil.getDBName(XMLUtil.createDocument());
	private String tablename = XMLUtil.getUserInfoTableName(XMLUtil.createDocument());
	
	public RegisterForClientThread(MessageQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			Connection conn = dbLinker.link(url, user, password);
			ResultSet rs = null;
			String msg = queue.getMsg();
			
			if (null != msg) {
				String[] data = msg.split(",");
				// select * 
				// from tablename
				// where ip = '...'
				String stmt = "select * from " + tablename + " where ipv4 =  " + "\'" + data[1] + "\'";
				// 数据库查询
				rs = dbLinker.getData(conn, stmt);

				// 判断是否空,看是否已经注册过
				if (false == rs.next()) {
					// 进行插入操作
					// insert into table
					// values (ip, name, port, flag)
					String sqlStmt = "insert into " + tablename + "values(?, ?, ?, ?)";
					PreparedStatement ps = conn.prepareStatement(sqlStmt);
					ps.setString(1, data[1]); // ip
					ps.setString(2, data[0]); // name
					ps.setString(3, data[3]); // port
					ps.setBoolean(4, true);   // flag  
					ps.executeUpdate();
					ps.close();
				}
				else {
					// 更新操作
					// update tablename
					// set flag = true
					// where ip = '...'
					String sqlStmt = "update " + tablename + "set flag = ? where ipv4 = ?";
					PreparedStatement ps = conn.prepareStatement(sqlStmt);
					ps.setBoolean(4, true);
					ps.setString(1, data[1]);
					ps.executeUpdate();
					ps.close();
				}
			}			
			
			dbLinker.closeConnection(rs, conn);
			dbLinker = null;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in class RegisterThread in function run\n" +
								e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
