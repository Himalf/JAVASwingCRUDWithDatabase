package com.demoSwingDatabase.demo;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

public class SwingData implements ActionListener {
	JFrame frame,frame2,frame3;
	JLabel roll,name,address,classroom,age;
	JTextField rl,nm,ad,cl,ag;
	JButton  button1,button2,btn,dbtn,ubtn,update;
	
	JTable table;
	DefaultTableModel model;
	
	public SwingData() {
		frame = new JFrame();
		roll =new JLabel("Roll No");
		name = new JLabel("Name");
		address = new JLabel("Address");
		classroom = new JLabel("Class");
		age= new JLabel("Age");
		rl = new JTextField(20);
		nm = new JTextField(20);
		ad = new JTextField(20);
		cl = new JTextField(20);
		ag = new JTextField(20);
		button1 = new JButton("Submit");
		button2 = new JButton("Get");
		frame.add(roll);
		frame.add(rl);
		frame.add(name);
		frame.add(nm);
		frame.add(address);
		frame.add(ad);
		frame.add(classroom);
		frame.add(cl);
		frame.add(age);
		frame.add(ag);
		frame.add(button1);
		frame.add(button2);
		frame.setSize(500,500);		
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.setLocationRelativeTo(null);
		button1.addActionListener(this);
		button2.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource()==button1) {
	String roll = rl.getText();
	String name = nm.getText();
	String address = ad.getText();
	String classroom = cl.getText();
	String age = ag.getText();
	try {
		insert(roll,name,address,classroom ,age);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
if(e.getSource()==button2) {
	frame.dispose();
	frame2 = new JFrame("Display");
	frame2.setVisible(true);
	frame2.setSize(500,500);
	frame2.setLayout(new FlowLayout());
	frame2.setLocationRelativeTo(null);
	frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	try {
		display();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
}
if(e.getSource()==btn) {
	frame2.dispose();
	frame.setVisible(true);
	
}
if(e.getSource()==ubtn) {
	int selectRow = table.getSelectedRow();
	if(selectRow==-1) {
		JOptionPane.showMessageDialog(ubtn, "Please select a Row to update");
		return;
	}
	populateUpdateForm(selectRow);
	
}
if(e.getSource()==dbtn) {
	int selectedRow = table.getSelectedRow();
if(selectedRow==-1) {
	JOptionPane.showMessageDialog(dbtn, "Please select the row you want to delete!");
}
	if(selectedRow!=-1) {
		int roll = (int) table.getValueAt(selectedRow, 0);
		int response = JOptionPane.showConfirmDialog(dbtn, "Do you want to delete");
		if(response==JOptionPane.YES_OPTION) {
			try {
				deleteRowFromDatabase(roll);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			model.removeRow(selectedRow);
		}
	}
}

if(e.getSource()==update) {
	String roll = rl.getText();
	String name = nm.getText();
	String address = ad.getText();
	String classroom = cl.getText();
	String age = ag.getText();
	try {
		UpdateData(roll,name,address,classroom,age);
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}

}


	

private void populateUpdateForm(int selectedRow) {
	frame2.dispose();
	frame3 = new JFrame("Update");
	roll =new JLabel("Roll No");
	name = new JLabel("Name");
	address = new JLabel("Address");
	classroom = new JLabel("Class");
	age= new JLabel("Age");
	rl = new JTextField(20);
	nm = new JTextField(20);
	ad = new JTextField(20);
	cl = new JTextField(20);
	ag = new JTextField(20);
	update = new JButton("Update");
	button2 = new JButton("Get");
	frame3.add(roll);
	frame3.add(rl);
	frame3.add(name);
	frame3.add(nm);
	frame3.add(address);
	frame3.add(ad);
	frame3.add(classroom);
	frame3.add(cl);
	frame3.add(age);
	frame3.add(ag);
	frame3.add(update);
	frame3.add(button2);
	frame3.setSize(500,500);		
	frame3.setVisible(true);
	frame3.setLayout(new FlowLayout());
	frame3.setLocationRelativeTo(null);
	update.addActionListener(this);
	button2.addActionListener(this);
	rl.setText(model.getValueAt(selectedRow, 0).toString());
	rl.setEditable(false);
	nm.setText(model.getValueAt(selectedRow, 1).toString());
	ad.setText(model.getValueAt(selectedRow, 2).toString());
	cl.setText(model.getValueAt(selectedRow, 3).toString());
	ag.setText(model.getValueAt(selectedRow, 4).toString());
}

public void UpdateData(String roll, String name, String address, String classroom, String age) throws SQLException {

	Connection conn = DbConnection.testConnection();
	String query = "update students set name=?,address=?,classroom=?,age=? where roll=?";
	PreparedStatement stmt = conn.prepareStatement(query);
	stmt.setString(1, name);
	stmt.setString(2, address);
	stmt.setString(3, classroom);
	stmt.setString(4, age);
	stmt.setString(5, roll);
	int res = stmt.executeUpdate();
	if(res==0) {
		JOptionPane.showMessageDialog(update, "Error updating");
	}
	else {
		JOptionPane.showMessageDialog(update, "Updated successfully");
	}
}



public void deleteRowFromDatabase(int roll) throws SQLException {
	Connection conn = DbConnection.testConnection();
	String query = "delete from students where roll=?";
	PreparedStatement stmt = conn.prepareStatement(query);
	stmt.setInt(1, roll);
	int status = stmt.executeUpdate();
	if(status==0) {
		JOptionPane.showMessageDialog(dbtn, "Error in deleting");
	}
	else {
		JOptionPane.showMessageDialog(dbtn, "Data deleted Successfully");
	}
	
}

public void display() throws SQLException {
	Connection conn = DbConnection.testConnection();
	String query = "select * from students";
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	String column[] = {"roll","name","address","class","age"};
	model = new DefaultTableModel(column,0);
	while(rs.next()) {
		int roll  = rs.getInt("roll");
		String name = rs.getString("name");
		String address = rs.getString("address");
		String classroom = rs.getString("classroom");
		String age = rs.getString("age");
		Object[] data = {roll,name,address,classroom,age};
		model.addRow(data);
	}
	table = new JTable(model);
	JScrollPane sp = new JScrollPane(table);
	frame2.add(sp,"center");
	 btn = new JButton("Insert");
	 dbtn = new JButton("Delete");
	 ubtn = new JButton("Update");
	frame2.add(btn,"south");
	frame2.add(dbtn,"south");
	frame2.add(ubtn,"south");
	btn.addActionListener(this);
	dbtn.addActionListener(this);
	ubtn.addActionListener(this);
	frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
}


public void insert(String roll,String name,String address,String classroom ,String age) throws SQLException {
	Connection conn = DbConnection.testConnection();
	String query = "insert into students(roll,name,address,classroom ,age) values(?,?,?,?,?)";
	PreparedStatement stmt = conn.prepareStatement(query);
	stmt.setString(1, roll);
	stmt.setString(2, name);
	stmt.setString(3, address);
	stmt.setString(4, classroom);
	stmt.setString(5, age);
	int s = stmt.executeUpdate();
	if(s!=0) {
		
		JOptionPane.showMessageDialog( button1, "Inserted Successfully");
	}
}

public static void main(String[] args) {
	new SwingData();
}
}
