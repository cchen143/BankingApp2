package bankingApp2.console;

import java.sql.*;
import java.util.Scanner;

import bankingApp2.controller.AccountController;
import bankingApp2.controller.CustomerController;
import bankingApp2.controller.UserController;
import bankingApp2.dao.ConnectionManager;
import io.javalin.Javalin;

public class Driver {
	
	
	/*
	DROP TABLE owners;
	DROP TABLE applicants;
	DROP TABLE applications;
	DROP TABLE accounts;
	DROP TABLE employees;
	DROP TABLE customers;
	DROP TABLE useraccts;


	CREATE TABLE employees (
			eID INTEGER PRIMARY KEY,
			name CHAR(50),
			username CHAR(50) UNIQUE
			);

	CREATE TABLE customers (
			cID INTEGER PRIMARY KEY,
			name CHAR(50),
			address CHAR(100),
			dob CHAR(8),
			username CHAR(50) UNIQUE
			);

	CREATE TABLE useraccts (
			username CHAR(50) PRIMARY KEY,
			pwd CHAR(50),
			usertype CHAR(8)
			);

	CREATE TABLE applications (
			appID INTEGER PRIMARY KEY,
			acctType CHAR(8),
			deposit NUMERIC
			);

	CREATE TABLE accounts (
			acctNum INTEGER PRIMARY KEY,
			acctType CHAR(8),
			balance NUMERIC,
			isjoint BOOLEAN
			);

	CREATE TABLE applicants (
			appID INTEGER,
			cID INTEGER,
			name CHAR(50),
			address CHAR(100),
			dob CHAR(8),
			FOREIGN KEY (appID) REFERENCES applications(appID)
			);

	CREATE TABLE owners (
			acctNum INTEGER,
			cID INTEGER,
			FOREIGN KEY (acctNum) REFERENCES accounts(acctNum),
			FOREIGN KEY (cID) REFERENCES customers(cID)
			);*/

	 
	
	
	
	public static void main(String[] args) throws Exception{
		
		Scanner sc  = new Scanner(System.in);
		System.out.println("1. API | 2. Banking app");
		String option = sc.nextLine();
		
		if (option.equals("2")) {
			try {
				Connection con = ConnectionManager.getConnection();
				Menu menu = new Menu();
				menu.main(sc, con);
				
				
			} finally {
				sc.close();
				try {
					ConnectionManager.getConnection().close();
				} catch (SQLException e) { e.printStackTrace(); }
			}
			
		} else {
			Javalin app = Javalin.create().start(7070);
			Connection con = ConnectionManager.getConnection();
			UserController control1 = new UserController(app, con);
			CustomerController control2 = new CustomerController(app, con);
			AccountController control3 = new AccountController(app, con);
			

		}
	}		
}