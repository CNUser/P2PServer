package function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import dboperation.ILDBLinker;
import dboperation.MySqlLinker;
import mainframe.FrameAdapter;
import myutil.FlagClass;
import myutil.XMLUtil;

/**
 * 更新思想：每分钟检查一次数据库
 * 1. 删除flag字段为false的元组，
 * 2. 将所有元组flag字段置为false
 * 由于客户端每15秒更新一次
 * 若一分钟后flag字段为false的元组基本可以认为断开连接
 * 所以予以删除
 */

public class UpdateDBThread extends Thread {
	private FrameAdapter frame;
	private Hashtable<String, String> userTable;
	private ILDBLinker dbLinker = new MySqlLinker();
	private String urlPrefix = XMLUtil.getDBLinkURLPrefix(XMLUtil.createDocument());
	private String url = urlPrefix + XMLUtil.getDBName(XMLUtil.createDocument());
	private String user = XMLUtil.getDBUserName(XMLUtil.createDocument());
	private String password = XMLUtil.getDBPwd(XMLUtil.createDocument());
	private String dbname = XMLUtil.getDBName(XMLUtil.createDocument());
	private String tablename = XMLUtil.getUserInfoTableName(XMLUtil.createDocument());
	
	public UpdateDBThread(FrameAdapter frame) {
		// TODO Auto-generated constructor stub
		this.frame = frame;
		this.userTable = frame.getUserTable();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true) {
			try {
				sleep(20000);
				
				Connection conn = dbLinker.link(url, user, password);
				ResultSet rs;
				// 查询操作：删除table中要删除的信息
				// select * 
				// from tablename
				// where flag = false
				String sqlstmt0 = "select * from " + tablename + " where flag = 0";
				String data = null;
				// 数据库查询
				rs = dbLinker.getData(conn, sqlstmt0);
					
				while (rs.next()) {
					userTable.remove(rs.getString(1));
				}
								
				// 删除操作
				// delete from tablename
				// where flag = false
				String sqlStmt1 = "delete from " + tablename + " where flag = ?";
				PreparedStatement ps = conn.prepareStatement(sqlStmt1);
				ps.setInt(1, 0);
				ps.executeUpdate();
				ps.close();

				// 更新操作
				// update tablename
				// set flag = false
				String sqlStmt2 = "update " + tablename + " set flag = ?";
				PreparedStatement ps2 = conn.prepareStatement(sqlStmt2);
				ps2.setInt(1, 0);
				ps2.executeUpdate();
				ps2.close();

				dbLinker.closeConnection(rs, conn);	
				
//				System.out.println("update db");
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error in class UpdateDBThread in function run\n" + e.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
//		dbLinker = null;
	}
	

}
