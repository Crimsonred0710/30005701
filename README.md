# Loan Management System

## Objective

Develop a menu-based console application to assess proficiency in Core Java, MySQL, and JDBC. The application simulates a loan management system for a financial institution.

## Functionalities

### Loan Management
- Add new loan accounts
- View loan details
- Update loan information
- Remove loan accounts

### Payment Management
- Record loan payments
- Calculate interest
- View payment history for a loan

## Database Schema

### Loan Table
- `loan_id` (Primary Key)
- `customer_id` (Foreign Key references Customer Table)
- `loan_amount`
- `interest_rate`
- `start_date`
- `end_date`
- `status` (Active, Closed)

### Payment Table
- `payment_id` (Primary Key)
- `loan_id` (Foreign Key references Loan Table)
- `payment_amount`
- `payment_date`

## Requirements

- JDK 8 or higher
- MySQL 5.7 or higher
- JDBC Driver 4.0 for MySQL (`com.mysql.cj.jdbc.Driver`)

## Setup Instructions

### Prerequisites

1. **JDK**: Ensure JDK 8 or higher is installed.
2. **MySQL**: Ensure MySQL 5.7 or higher is installed.
3. **JDBC Driver**: Ensure the MySQL Connector/J (jar file) is available.

### Adding MySQL Connector/J to CLASSPATH

1. **Windows**:
    - Go to **This PC** -> Right Click -> **Properties** -> **Advanced System Settings** -> **Environment Variables** -> **System Variables** -> **CLASSPATH** -> **Edit**.
    - Add the path to the MySQL Connector/J JAR file.
    - Click **Apply**.

### Setting Up the Database

1. **Install MySQL**: Ensure MySQL database software is installed.
2. **Create the Database**:
    ```sql
    CREATE DATABASE <Database_Name>;
    ```

3. **Create Tables**:
    ```sql
    USE <Database_Name>;

    CREATE TABLE CUSTOMER (
        CUSTOMER_ID INT PRIMARY KEY,
        CNAME VARCHAR(20),
        CADDR VARCHAR(20),
        CPHONE VARCHAR(20)
    );

    CREATE TABLE LOAN (
        LOAN_ID INT PRIMARY KEY,
        CUSTOMER_ID INT,
        LOAN_AMOUNT DECIMAL(10,2),
        INTEREST_RATE DECIMAL(4,2),
        START_DATE DATE,
        END_DATE DATE,
        STATUS VARCHAR(6),
        FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER (CUSTOMER_ID)
    );

    CREATE TABLE PAYMENT (
        PAYMENT_ID INT PRIMARY KEY,
        LOAN_ID INT,
        PAYMENT_AMOUNT DECIMAL(10,2),
        PAYMENT_DATE DATE,
        FOREIGN KEY (LOAN_ID) REFERENCES LOAN (LOAN_ID)
    );
    ```

### Database Connection Configuration

Update the database connection settings in your Java application:
```java
String URL = "jdbc:mysql://localhost:3306/<Database_Name>";
String USER = "Your_database_username";
String PASSWORD = "Your_database_password";
```
Usage Instructions 

Adding a Loan:

Select option 1 from the menu.
Enter the required details: customer ID, loan amount, interest rate, start date, and end date.
Viewing a Loan:

Select option 2 from the menu.
Enter the loan ID to view the loan details.
Updating a Loan:

Select option 3 from the menu.
Enter the loan ID, new loan amount, and new interest rate.
Removing a Loan:

Select option 4 from the menu.
Enter the loan ID to remove the loan.
Recording a Payment:

Select option 5 from the menu.
Enter the loan ID, payment amount, and payment date.
Viewing Payment History:

Select option 6 from the menu.
Enter the loan ID to view the payment history.
Calculating Interest:

Select option 7 from the menu.
Enter the principal amount, interest rate, and number of years to calculate the interest.
Exiting the Application:

Select option 8 from the menu to exit the application.
