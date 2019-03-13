package com.mcds5510.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import com.mcds5510.Assignment2;
import com.mcds5510.dao.MySQLAccess;
import com.mcds5510.entity.Transaction;

public class View {
	
	private static Scanner sc = new Scanner(System.in);
	Assignment2 ass = new Assignment2();
	Logger logger = Logger.getLogger("Main");
	public void showView(Connection connection) throws SQLException{
		try {
		PrintWriter pw = new PrintWriter(new File(ass.outputFile));
		    int option;

		    System.out.println("Welcome to Transactions please choose from the following options");
		    logger.log(Level.INFO, "Welcome to Transactions please choose from the following options");
		    pw.write("Welcome to Transactions please choose from the following options\n");
		    System.out.println();
		    System.out.println("Create a transaction (1)\nUpdate a transaction (2)\nView a transaction (3)\nDelete a transaction(4)\nExit(5)");
		    logger.log(Level.INFO, "Create a transaction (1)\nUpdate a transaction (2)\nView a transaction (3)\nDelete a transaction(4)\nDelete a transaction(4)\nExit(5)");
		    pw.write("Create a transaction (1)\nUpdate a transaction (2)\nView a transaction (3)\nDelete a transaction(4)\nExit(5)");
		    System.out.println();
		    pw.close();
		    do {
	            option = checkOptions();// Use for String Input
	        } while(!selectOption(option,connection));
		}
		catch(IOException e){
			e.printStackTrace();
			logger.log(Level.SEVERE, "Exception "+e+"occured in file ");
		}
	}
	
	public int checkCardType()
	{
	 
		boolean badInput = true;
		int next = 0;
	    do {
	        try {
	            next = sc.nextInt();
	            if (next == 1 || next == 2 || next == 3) {
	                badInput = false;
	                logger.log(Level.INFO, "Card Type selected: "+next);
	                return next;
	            } else {
	            	badInput = true;
	            	System.out.println("Please input from given choices!");
	            	logger.log(Level.WARNING, "Please input from given choices!");
	            	
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Please input from given choices!");
	            logger.log(Level.WARNING, "Please input from given choices!");
	        }
	        sc.nextLine();
	    } while (badInput);

	    badInput = true;
	    return next;
	}
	
	public int checkOptions()
	{
	 
		boolean badInput = true;
		int next = 0;
	    do {
	        try {
	            next = sc.nextInt();
	            if (next == 1 || next == 2 || next == 3 || next == 4 || next == 5) {
	                badInput = false;
	                logger.log(Level.INFO, "Option Selected: "+next);
	                return next;
	            } else {
	            	badInput = true;
	            	System.out.println("Please input from given choices!");
	            	logger.log(Level.WARNING, "Please input from given choices!");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Please input from given choices!");
	            logger.log(Level.WARNING, "Please input from given choices!");
	        }
	        sc.nextLine();
	    } while (badInput);

	    badInput = true;
	    return next;
	}
	
	public int checkInteger()
	{
	 
		boolean badInput = true;
		int next = 0;
	    do {
	        try {
	            next = sc.nextInt();;
	            if (next > 0) {
	                badInput = false;
	                return next;
	            } else {
	            	badInput = true;
	            	System.out.println("Please enter a number greater than 0");
	            	logger.log(Level.WARNING, "Please enter a number greater than 0");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Please enter a number greater than 0");
	            logger.log(Level.WARNING, "Please enter a number greater than 0");
	        }
	        sc.nextLine();
	    } while (badInput);

	    badInput = true;
	    return next;
	}
	
	public int checkBigDecimal()
	{
	 
		boolean badInput = true;
		Double next = 0.0;
		int nInt = 0;
		BigDecimal b = null;
	    do {
	        try {
	            next = sc.nextDouble();
	            if (next > 0) {
	            	b = BigDecimal.valueOf(next);
	            	nInt = b.intValue();
	                badInput = false;
	                return nInt;
	            } else {
	            	badInput = true;
	            	System.out.println("Please enter a number greater than 0");
	            	logger.log(Level.WARNING, "Please enter a number greater than 0");
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Please enter a number greater than 0");
	            logger.log(Level.WARNING, "Please enter a number greater than 0");
	        }
	        sc.nextLine();
	    } while (badInput);

	    badInput = true;
	    return nInt;
	}
	
	public boolean selectOption(int option, Connection connection) throws SQLException {
		 Collection<Transaction> results = new ArrayList<Transaction>();
		 MySQLAccess newAccess = new MySQLAccess();
		 try {
		 PrintWriter pw = new PrintWriter(new FileWriter(ass.outputFile,true));
		 switch (option) {
	            case 1:
	            	boolean resultInsert = newAccess.createTrxns(connection);
	            	if(!resultInsert) {
	            		logger.log(Level.SEVERE, "Please choose update option as card already exists!");
	            		System.out.println("Please choose update option as id already exists!");
	            		pw.write("Please choose update option as id already exists!\n");
	            	}
	            	else {
	            		logger.log(Level.INFO, "Transaction successful!");
	            		pw.write("Transaction successful!\n");
	            	}
	                break;
	            case 2:
	            	boolean result = newAccess.updateTransaction(connection);
	            	if(!result) {
	            		logger.log(Level.SEVERE, "Please choose create option as card does not exist!");
	            		System.out.println("Please choose create option as id does not exist!");
	            		pw.write("Please choose create option as id does not exist!\n");
	            	}
	            	else {
	            		logger.log(Level.INFO, "Updation successful!");
	            		pw.write("Updation successful!\n");
	            	}
	            	break;
	            case 3:
	            	results = newAccess.viewTransaction(connection);
	            	if(results.size() > 0) {
	            		System.out.println(results);
	            		logger.log(Level.INFO , "Record viewed: " + results);
	            		pw.write("Record viewed: " + results+" \n");
	            	}
	            	else {
	            		System.out.println("Sorry this transaction does not exist!");
	            		logger.log(Level.SEVERE, "Sorry this transaction does not exist!");
	            		pw.write("Sorry this transaction does not exist!\n");
	            	}
	                break;
	            case 4:
	            	boolean resultDelete = newAccess.removeTransaction(connection);
	            	if(!resultDelete) {
	            		System.out.println("Sorry this transaction id does not exist!");
	            		logger.log(Level.SEVERE, "Sorry this transaction id does not exist!");
	            		pw.write("Sorry this transaction does not exist!\n");
	            	}
	            	else {
	            		logger.log(Level.INFO, "Deletion successful!");
	            		pw.write("Deletion successful!\n");
	            	}
	                break;
	            case 5:
	            	for(Handler h:logger.getHandlers())
	    		    {
	    		        h.close();   //must call h.close or a .LCK file will remain.
	    		    }
	            	System.exit(0);
	            	break;
		        default:
	                System.out.println("Invalid option, please choose another option.");
	                logger.log(Level.SEVERE, "Invalid option, please choose another option.");
	                pw.write("Invalid option, please choose another option.\n");
	                System.out.println();
	                pw.close();
	                return false;
	        }
	            System.out.println("Please continue by choosing other option from the main menu!");
	            logger.log(Level.INFO, "Please continue by choosing other option from the main menu!");
	            pw.write("Please continue by choosing other option from the main menu!\n");
	            pw.close();
		 }catch(IOException e) {
			 e.printStackTrace();
		 }
	            return false;
	    }
}
