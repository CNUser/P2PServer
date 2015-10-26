package dboperation;

import java.sql.*;
import javax.swing.*;

public class MySqlLinker implements ILDBLinker {
	
	public MySqlLinker() {
		
	}
	
	@Override
	public Connection link(String url, String user, String password) {
		// TODO Auto-generated method stub
		
		try {
			// 加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
						
			// 建立SQL连接
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "连接数据库错误!\n" + e.toString(), "" +
					"数据库错误", JOptionPane.ERROR_MESSAGE);
		}
		
		
		return null;
		
	}

	@Override
	public ResultSet getData(Connection conn, String statement) {
		// TODO Auto-generated method stub
		
		try {
			// 执行SQL语句
			Statement stmt = conn.createStatement();			
			ResultSet rs = stmt.executeQuery(statement);
			
			return rs;
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数据查询错误!\n" + e.toString(), "" +
					"数据库错误", JOptionPane.ERROR_MESSAGE);
		}
		
		
		return null;
	}

	@Override
	public boolean closeConnection(ResultSet rs, Connection conn) {
		// TODO Auto-generated method stub
		
		try {
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "关闭错误!\n" + e.toString(), "" +
					"数据库错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	public boolean closeConnection(Connection conn) {
		// TODO Auto-generated method stub
		
		try {
			if (conn != null)
				conn.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "关闭错误!\n" + e.toString(), "" +
					"数据库错误", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
}