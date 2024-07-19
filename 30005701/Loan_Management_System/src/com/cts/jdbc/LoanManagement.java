package com.cts.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoanManagement {
	static Connection con = null;
	static PreparedStatement ps = null;
	static ResultSet rs = null;
	static String query = "";
	static int count = 0;

	// Load JDBC Driver (Autoloading after JDBC version 4.0)
	// Class.forName("com.mysql.cj.jdbc.Driver");

	// JDBC driver with MySQL port number and database
	final static String url = "jdbc:mysql://localhost:3306/ctsdb";

	// MySQL Login User Name and User Password
	final static String uname = "root";
	final static String upwd = "root";

	// Creation of Add new loan accounts function
	public static void addNewLoanAccounts(Scanner sc) {
		// Establish Connection with JDBC driver,Scanner Object inside try with
		// resources block for auto resource closing
		try (Connection con = DriverManager.getConnection(url, uname, upwd)) {

			// create SQL query
			query = "INSERT INTO LOAN(LOAN_ID,CUSTOMER_ID,LOAN_AMOUNT,INTEREST_RATE,START_DATE,END_DATE,STATUS) VALUES(?,?,?,?,?,?,?)";

			// Create JDBC PrepareStatement object with query compilation
			if (con != null)
				ps = con.prepareStatement(query);

			// Take input from user
			System.out.print("Enter loan id: ");
			int loanId = sc.nextInt();
			System.out.print("Enter customer id: ");
			int customerId = sc.nextInt();
			System.out.print("Enter loan amount: ");
			double loanAmount = sc.nextDouble();
			System.out.print("Enter interest rate: ");
			double interestRate = sc.nextDouble();
			System.out.print("Enter start date in the format of (YYYY-MM-DD): ");
			String startDate = sc.next();
			System.out.print("Enter end date in the format of(YYYY-MM-DD): ");
			String endDate = sc.next();
			System.out.print("Enter status : active or closed): ");
			String status = sc.next();

			// Create Parameterized SQL queries
			if (ps != null) {
				ps.setInt(1, loanId);
				ps.setInt(2, customerId);
				ps.setDouble(3, loanAmount);
				ps.setDouble(4, interestRate);
				ps.setDate(5, Date.valueOf(startDate));
				ps.setDate(6, Date.valueOf(endDate));
				ps.setString(7, status);
			}
			// Return affected rows
			count = ps.executeUpdate();

			// Checking Loan Account Addition to the Loan table
			if (count != 0)
				System.out.println("New loan account created");

		} catch (SQLException se) {
			System.out.println("Failed in new loan account creation");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Creation of view loan details function
	public static void viewLoanDetails(Scanner sc) {
		// Establish Connection with JDBC driver,Scanner Object inside try with
		// resources block for auto resource closing
		try (Connection con = DriverManager.getConnection(url, uname, upwd);) {
			System.out.print("Enter the loan id number to display: ");

			// Take input from user
			int loanId = sc.nextInt();

			query = "SELECT * FROM LOAN WHERE LOAN_ID=?";

			// Create JDBC PrepareStatement object with query compilation
			if (con != null)
				ps = con.prepareStatement(query);

			// create parameterized sql query
			ps.setInt(1, loanId);

			// Create ResultSet object
			if (ps != null)
				rs = ps.executeQuery();

			// Display output
			if (rs != null) {
				while (rs.next()) {
					System.out.println("Loan ID: " + rs.getInt("LOAN_ID") + "\nCustomer ID: " + rs.getInt("CUSTOMER_ID")
							+ "\nLoan Amount: " + rs.getDouble("LOAN_AMOUNT") + "\nInterest Rate: "
							+ rs.getDouble("INTEREST_RATE") + "\nStart Date: " + rs.getDate("START_DATE")
							+ "\nEnd Date: " + rs.getDate("END_DATE") + "\nStatus: " + rs.getString("STATUS"));
				}
			} else
				System.out.println("Your entered loan id data is not avaiable");

		} catch (SQLException se) {
			System.out.println("Failed in loan searching");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// creation of loan information update function
	public static void updateLoanInformation(Scanner sc) {
		// Establish Connection with JDBC driver,Scanner Object inside try with
		// resources block for auto resource closing
		try (Connection con = DriverManager.getConnection(url, uname, upwd);) {
			System.out.print("Enter the loan id number to modify: ");

			// Take input from user
			int loanId = sc.nextInt();

			// Choice of User
			System.out.println("""
					Choose the field to update:
						 1. Customer ID
						 2. Loan Amount
						 3. Interest Rate
						 4. Start Date
						 5. End Date
						 6. Status
					Enter your choice: """);

			// Enter choice
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.print("Enter updated customer id: ");
				int customerId = sc.nextInt();
				query = "UPDATE LOAN SET CUSTOMER_ID = ? WHERE LOAN_ID = ?";

				// Create JDBC PrepareStatement object with query compilation
				if (con != null)
					ps = con.prepareStatement(query);

				ps.setInt(1, customerId);
				ps.setInt(2, loanId);
				break;
			case 2:
				System.out.print("Enter new loan amount: ");
				double loanAmount = sc.nextDouble();
				query = "UPDATE LOAN SET LOAN_AMOUNT = ? WHERE LOAN_ID = ?";

				// Create JDBC PrepareStatement object with query compilation
				if (con != null)
					ps = con.prepareStatement(query);

				ps.setDouble(1, loanAmount);
				ps.setInt(2, loanId);
				break;
			case 3:
				System.out.print("Enter new interest rate: ");
				double interestRate = sc.nextDouble();
				query = "UPDATE LOAN SET INTEREST_RATE = ? WHERE LOAN_ID = ?";

				// Create JDBC PrepareStatement object with query compilation
				if (con != null)
					ps = con.prepareStatement(query);

				ps.setDouble(1, interestRate);
				ps.setInt(2, loanId);
				break;
			case 4:
				System.out.print("Enter new start date in the format of (YYYY-MM-DD): ");
				String startDate = sc.next();
				query = "UPDATE LOAN SET START_DATE = ? WHERE LOAN_ID = ?";

				// Create JDBC PrepareStatement object with query compilation
				if (con != null)
					ps = con.prepareStatement(query);

				ps.setDate(1, Date.valueOf(startDate));
				ps.setInt(2, loanId);
				break;
			case 5:
				System.out.print("Enter new end date in the format of (YYYY-MM-DD): ");
				String endDate = sc.next();
				query = "UPDATE LOAN SET END_DATE = ? WHERE LOAN_ID = ?";

				// Create JDBC PrepareStatement object with query compilation
				if (con != null)
					ps = con.prepareStatement(query);

				ps.setDate(1, Date.valueOf(endDate));
				ps.setInt(2, loanId);
				break;
			case 6:
				System.out.print("Enter loan's new status whether active or closed): ");
				String status = sc.next();
				query = "UPDATE LOAN SET STATUS = ? WHERE LOAN_ID = ?";

				// Create JDBC PrepareStatement object with query compilation
				if (con != null)
					ps = con.prepareStatement(query);

				ps.setString(1, status);
				ps.setInt(2, loanId);
				break;
			default:
				System.out.println("Wrong choice !!");
			}

			//
			count = ps.executeUpdate();
			if (count != 0) {
				System.out.println("Your updation is successfull");
			} else {
				System.out.println("Your entered loan id is unavailable");
			}

		} catch (SQLException se) {
			System.out.println("Failed in loan information updation");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create remove loan account function
	public static void removeLoanAccounts(Scanner sc) {
		// Establish Connection with JDBC driver,Scanner Object inside try with
		// resources block for auto resource closing
		try (Connection con = DriverManager.getConnection(url, uname, upwd)) {
			System.out.print("Enter the loan id number you want to remove: ");

			// Take input from user
			int loanId = sc.nextInt();

			query = "DELETE FROM LOAN WHERE LOAN_ID = ?";

			// Create JDBC PrepareStatement object with query compilation
			if (con != null)
				ps = con.prepareStatement(query);

			// create parameterized sql query
			ps.setInt(1, loanId);

			count = ps.executeUpdate();
			if (count != 0) {
				System.out.println("Your entered loan id is deleted");
			} else {
				System.out.println("Your entered loan id is unavailable");
			}

		} catch (SQLException se) {
			System.out.println("Failed in loan deletion as it is a foreign key");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
