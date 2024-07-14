package com.demoSwingDatabase.demo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class StudentCrud {
	
public StudentCrud()  {
//	insert();
	delete();
}
public void insert()  {
	
	Connection con = DbConnection.testConnection();
	String query = "insert into studetail(roll,name,address,classes) values (?,?,?,?)";

		try {
		PreparedStatement stmt = con.prepareStatement(query);
	
		stmt.setInt(1, 31);
		stmt.setString(2, "gopi");
		stmt.setString(3, "Batauli");
		stmt.setString(4, "13");
		stmt.executeUpdate();
	
	} catch (SQLException e) {
		// TODO: handle exception
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	
}
public void delete() {
	Connection conn = DbConnection.testConnection();
	String query = "delete from studetail where roll=?";
	try {
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, 31);
		stmt.executeUpdate();
	} catch (Exception e) {
		// TODO: handle exception
	}
}
public static void main(String[] args) throws SQLException {
new StudentCrud();
}
}