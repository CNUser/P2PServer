package dboperation;

import java.sql.*;

import javax.swing.*;

public class OracleLinker implements ILDBLinker {
	
	public OracleLinker() {
		
	}

	@Override
	public Connection link(String url, String user, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getData(Connection conn, String statement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean closeConnection(ResultSet rs, Connection conn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeConnection(Connection conn) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}