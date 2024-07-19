package com.cts.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PaymentManagement {
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

	// Create loan payments record function
	public static void recordLoanPayments(Scanner sc) {
		// Establish Connection with JDBC driver,Scanner Object inside try with
		// resources block for auto resource closing
		try (Connection con = DriverManager.getConnection(url, uname, upwd)) {

			// Cretae SQL query
			query = "INSERT INTO PAYMENT(PAYMENT_ID,LOAN_ID,PAYMENT_AMOUNT,PAYMENT_DATE) VALUES(?,?,?,?)";

			// Create JDBC PrepareStatement object with query compilation
			if (con != null)
				ps = con.prepareStatement(query);

			// Take input from user
			System.out.print("Enter payment id: ");
			int paymentId = sc.nextInt();
			System.out.print("Enter loan id: ");
			int loanId = sc.nextInt();
			System.out.print("Enter payment amount: ");
			double paymentAmount = sc.nextDouble();
			System.out.print("Enter payment date in the format of (YYYY-MM-DD): ");
			String paymentDate = sc.next();

			// Create Parameterized SQL queries
			if (ps != null) {
				ps.setInt(1, paymentId);
				ps.setInt(2, loanId);
				ps.setDouble(3, paymentAmount);
				ps.setDate(4, Date.valueOf(paymentDate));
			}
			// Return affected rows
			if (ps != null)
				count = ps.executeUpdate();

			// Checking Loan Account Addition to the Loan table
			if (count != 0)
				System.out.println("New payment recorded");

		} catch (SQLException se) {
			System.out.println("Failed in new payment record creation");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create interest calculation method
	public static void calculateInterest(Scanner sc) {
		// Establish Connection with JDBC driver,Scanner Object inside try with
		// resources block for auto resource closing
		try (Connection con = DriverManager.getConnection(url, uname, upwd)) {
			System.out.print("Enter the loan id number to calculate interest : ");

			// Take input from user
			int loanId = sc.nextInt();

			// sql query to execute
			query = "SELECT LOAN_AMOUNT, INTEREST_RATE, START_DATE, END_DATE FROM LOAN WHERE LOAN_ID = ?";

			// Create JDBC PrepareStatement object with query compilation
			if (con != null)
				ps = con.prepareStatement(query);

			// create parameterized sql query
			if (ps != null)
				ps.setInt(1, loanId);

			// Create ResultSet object
			if (ps != null)
				rs = ps.executeQuery();

			// Display output
			if (rs != null) {
				while (rs.next()) {
					double loanAmount = rs.getDouble("LOAN_AMOUNT");
					double interestRate = rs.getDouble("INTEREST_RATE");
					Date startDate = rs.getDate("START_DATE");
					Date endDate = rs.getDate("END_DATE");

					// calculate interest logic
					long milliSecondDifference = Math.abs(endDate.getTime() - startDate.getTime());
					long milliToDays = TimeUnit.DAYS.convert(milliSecondDifference, TimeUnit.MILLISECONDS);
					double totalInterest = (loanAmount * interestRate * milliToDays) / (100 * 365);
					System.out.println("Total interest: " + totalInterest);
				}
			} else
				System.out.println("Your entered loan id if not avaiable");

		} catch (SQLException se) {
			System.out.println("Failed in loan interest calculation");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create view payment history function
	public static void viewPaymentHistory(Scanner sc) {
		// Establish Connection with JDBC driver,Scanner Object inside try with
		// resources block for auto resource closing
		try (Connection con = DriverManager.getConnection(url, uname, upwd)) {
			System.out.print("Enter the loan id number to view payment history : ");

			// Take input from user
			int loanId = sc.nextInt();

			// sql query to execute
			query = "SELECT * FROM PAYMENT WHERE LOAN_ID = ?";

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
				if (rs.next()) {
					System.out.println("Payment id: " + rs.getInt("PAYMENT_ID") + "Loan id: " + rs.getInt("LOAN_ID")
							+ "Payment amount: " + rs.getDouble("PAYMENT_AMOUNT") + "Payment date: "
							+ rs.getDate("PAYMENT_DATE"));
				}
			} else
				System.out.println("Your entered loan id is not avaiable");

		} catch (SQLException se) {
			System.out.println("Failed in viewing payment history");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
