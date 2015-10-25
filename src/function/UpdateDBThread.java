package function;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import dboperation.ILDBLinker;
import dboperation.MySqlLinker;
import myutil.XMLUtil;

/**
 * 更新思想：每分钟检查一次数据库
 * 1. 删除flag字段为false的元组，
 * 2. 将所有元组flag字段置为false
 * 由于客户端每15秒更新一次
 * 若一分钟后flag字段为false的元组基本可以认为断开连接
 * 所以予以删除
 */

public class UpdateDBThread implements Runnable {
	private ILDBLinker dbLinker = new MySqlLinker();
	private String urlPrefix = XMLUtil.getDBLinkURLPrefix(XMLUtil.createDocument());
	private String url = urlPrefix + XMLUtil.getDBName(XMLUtil.createDocument());
	private String user = XMLUtil.getDBUserName(XMLUtil.createDocument());
	private String password = XMLUtil.getDBPwd(XMLUtil.createDocument());
	private String dbname = XMLUtil.getDBName(XMLUtil.createDocument());
	private String tablename = XMLUtil.getUserInfoTableName(XMLUtil.createDocument());
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Connection conn = dbLinker.link(url, user, password);
			// 删除操作
			// delete from tablename
			// where flag = false
			String sqlStmt1 = "delete from " + tablename + " where flag = ?";
			PreparedStatement ps = conn.prepareStatement(sqlStmt1);
			ps.setBoolean(4, false);
			ps.executeUpdate();
			ps.close();
			
			// 更新操作
			// update tablename
			// set flag = false
			String sqlStmt2 = "update " + tablename + "set flag = ?";
			PreparedStatement ps2 = conn.prepareStatement(sqlStmt2);
			ps2.setBoolean(4, false);
			ps2.executeUpdate();
			ps2.close();
			
			dbLinker.closeConnection(conn);
			dbLinker = null;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in class UpdateDBThread in function run\n" +
					e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	

}
