package com.cts.jdbc;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoanManagementSystem {
	public static void main(String[] args) {

		int choice = 0;
		
		//Create Scanner object
		try (Scanner sc = new Scanner(System.in)) {
			
			while (true) {
				// Display options for user
				System.out.println("""
						\nLoan Management System Operations:
						1. Add New Loan Accounts
						2. View Loan Details
						3. Update Loan Information
						4. Remove Loan Accounts
						5. Record Loan Payment
						6. Calculate Interest
						7. View Payment History for a Loan
						8. Exit
						Please Enter Your Option (1-8): """);
				
				//Enter choice
				choice = sc.nextInt();
				
				//Options
				switch (choice) {
				case 1:
					LoanManagement.addNewLoanAccounts(sc);
					break;
				case 2:
					LoanManagement.viewLoanDetails(sc);
					break;
				case 3:
					LoanManagement.updateLoanInformation(sc);
					break;
				case 4:
					LoanManagement.removeLoanAccounts(sc);
					break;
				case 5:
					PaymentManagement.recordLoanPayments(sc);
					break;
				case 6:
					PaymentManagement.calculateInterest(sc);
					break;
				case 7:
					PaymentManagement.viewPaymentHistory(sc);
					break;
				case 8:
					System.out.println("Exited");
					System.exit(0);
				default:
					System.out.println("Wrong Choice!!");
				}
			}
		} catch (InputMismatchException ime) {
			System.out.println("Invalid input. Please enter a number.");
			ime.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
