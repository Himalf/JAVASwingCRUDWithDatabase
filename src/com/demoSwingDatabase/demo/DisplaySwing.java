package com.demoSwingDatabase.demo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;


public class DisplaySwing implements ActionListener {
	JFrame frame;
	JTable table;
	JButton delete;
	DefaultTableModel model;
	public DisplaySwing() throws SQLException {
		frame = new JFrame();
		Connection conn = DbConnection.testConnection();
		String query = "select * from students";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery(); 
		String column[] = {"roll","name","address","classroom","age"};
		 model = new  DefaultTableModel(column,0); 
		while(rs.next()) {
			int roll = rs.getInt("roll");
			String name = rs.getString("name");
			String address = rs.getString("address");
			String classroom = rs.getString("classroom");
			String age = rs.getString("age");	
			Object[] data = {roll,name,address,classroom,age,delete};
			model.addRow(data);
		}
		
		table = new JTable(model);
		delete = new JButton("Delete");
		JScrollPane sp = new JScrollPane(table);
		frame.add(sp,"Center");
		frame.add(delete,"South");
		frame.setSize(400,400);
		frame.setVisible(true);
		delete.addActionListener(this);
		
	}
public static void main(String[] args) throws SQLException {
	new DisplaySwing();
}
@Override
public void actionPerformed(ActionEvent e) {
	int selectRow = table.getSelectedRow();
	if(selectRow!=-1) {
		int roll = (int) table.getValueAt(selectRow, 0);
		int response = JOptionPane.showConfirmDialog(delete, "Do you Want to delete");
		if(response==JOptionPane.YES_OPTION) {
			deleteRowFromDatabase(roll);
			model.removeRow(selectRow);
		}
		
	
		
	}
	
}
public void deleteRowFromDatabase(int roll) {
	Connection conn = DbConnection.testConnection();
	String query = "delete from students where roll = ?";
	try {
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, roll);
		int rowsDeleted = stmt.executeUpdate();
		
		
		if(rowsDeleted==0) {
			JOptionPane.showMessageDialog(delete, "Error in Deleting Data");
		}
		else {
			JOptionPane.showMessageDialog(delete, "Deleted Successfully!!");
		}
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(delete, e);
	}
	
	
}
}
