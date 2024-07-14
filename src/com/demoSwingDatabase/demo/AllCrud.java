package com.demoSwingDatabase.demo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AllCrud {

    public AllCrud() throws SQLException {
        run();
    }

    public void run() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Enter your choice:");
            System.out.println("1. Insert");
            System.out.println("2. Display");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    insert();
                    break;
                case 2:
                    display();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

           if (!exit) {
                System.out.println("Do you want to continue? (y/n)");
                String continueChoice = scanner.next();
                if (!continueChoice.equalsIgnoreCase("y")) {
                    exit = true;
                }
            }
        }

        scanner.close();
    }

    public void insert() {
        String query = "INSERT INTO students (roll, name, address, classroom, age) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.testConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Enter Roll No:");
            int rollno = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter Name:");
            String names = sc.nextLine();
            System.out.println("Enter Address:");
            String addresses = sc.nextLine();
            System.out.println("Enter Class:");
            String classes = sc.nextLine();
            System.out.println("Enter Age:");
            String ages = sc.nextLine();

            stmt.setInt(1, rollno);
            stmt.setString(2, names);
            stmt.setString(3, addresses);
            stmt.setString(4, classes);
            stmt.setString(5, ages);
            stmt.executeUpdate();
            System.out.println("Inserted Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void display() throws SQLException {
        String query = "SELECT * FROM students";
        try (Connection conn = DbConnection.testConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("******************************************************************");
                System.out.println("Roll No.: " + rs.getInt("roll"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Class: " + rs.getString("classroom"));
                System.out.println("Age: " + rs.getString("age"));
                System.out.println("******************************************************************");
            }
        }
    }

    public void update() {
        String query = "UPDATE students SET name=?, address=?, classroom=?, age=? WHERE roll=?";
        try (Connection conn = DbConnection.testConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Enter the roll number to be updated:");
            int roll = sc.nextInt();
            sc.nextLine(); // Consume newline
            System.out.println("Enter the new Name:");
            String name = sc.nextLine();
            System.out.println("Enter the new Address:");
            String address = sc.nextLine();
            System.out.println("Enter the new Classroom:");
            String classroom = sc.nextLine();
            System.out.println("Enter the new Age:");
            String age = sc.nextLine();

            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, classroom);
            stmt.setString(4, age);
            stmt.setInt(5, roll);
            stmt.executeUpdate();
            System.out.println("Updated Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String query = "DELETE FROM students WHERE roll=?";
        try (Connection conn = DbConnection.testConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Enter the roll number to be deleted:");
            int roll = sc.nextInt();

            stmt.setInt(1, roll);
            stmt.executeUpdate();
            System.out.println("Deleted Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        new AllCrud();
    }
}
