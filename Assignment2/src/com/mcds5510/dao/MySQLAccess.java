package com.mcds5510.dao;

/**
 * Original source code from 
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
**/

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.mcds5510.Assignment2;
import com.mcds5510.entity.Transaction;
import com.mcds5510.view.View;

public class MySQLAccess {
	Logger logger = Logger.getLogger("Main");
	Assignment2 ass = new Assignment2();
    private String varcharPatt = "[^;:!@#$%^*+?<>]+";
    private String masterCardPatt = "5[1-5][0-9]{14}";
    private String visaCardPatt = "4[0-9]{15}";
    private String amexPatt = "34[0-9]{13}|37[0-9]{13}";
	private static Scanner sc = new Scanner(System.in);
	public Collection<Transaction> getAllTransactions(Connection connection) {
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = new ArrayList<Transaction>();
		// Result set get the result of the SQL query
		try {
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction.transaction");
			//results = createTrxns(resultSet);
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statement = null;
			resultSet = null;
		}
		return results;

	}
	
	public Collection<Transaction> viewTransaction(Connection connection) throws SQLException  {
		
    	Statement statement = null;
    	ResultSet resultSet = null;
    	Collection<Transaction> results = new ArrayList<Transaction>();
    	Transaction trxn = new Transaction();
		// Result set get the result of the SQL query
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(ass.outputFile, true));
			// DO the update SQL here
			View newView = new View();
			int trnxId;
	    	System.out.println("Please enter the id of the transaction!");
	    	logger.log(Level.INFO, "Please enter the id of the transaction!");
	    	pw.write("Please enter the id of the transaction!\n");
	    	pw.close();
	    	do {
	    		trnxId = newView.checkInteger();// Use for String Input
		        } while(!(trnxId > 0));
	    	logger.log(Level.INFO, "Transaction ID entered: "+trnxId);
			
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			String sql = "Select * from transaction.transaction where ID = "+ trnxId;
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
			trxn.setNameOnCard(resultSet.getString("NameOnCard"));
			trxn.setCardNumber(resultSet.getString("CardNumber"));
			trxn.setCardName(resultSet.getString("CardType"));
			trxn.setExpDate(resultSet.getString("ExpDate"));
			trxn.setUnitPrice(resultSet.getInt("UnitPrice"));
			trxn.setQty(resultSet.getInt("Quantity"));
			trxn.setTotalPrice(resultSet.getInt("TotalPrice"));
			trxn.setCreatedBy(resultSet.getString("CreatedBy"));
			results.add(trxn);
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			

		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			statement = null;
			resultSet = null;
		}
		return results;
	}
	
    public boolean createTrxns(Connection connection) throws SQLException {
		Collection<Transaction> results = new ArrayList<Transaction>();
		Transaction trxn = new Transaction();
		View newView = new View();
		Statement statement = null;
    	ResultSet resultSet = null;
		String nameOnCard ="",cardNumber = "",expiryDate="",createdBy="", cardName="";
    	int unitPrice = 0, quantity = 0, cardType = 0, ID;
		int totalPrice= 0;
    	try {
    	PrintWriter pw = new PrintWriter(new FileWriter(ass.outputFile, true));
    	System.out.println("Please select the type of card from the choices listed below:\nMaster Card (1)\nVisa (2)\nAmerican Express (3)\n");
    	logger.log(Level.INFO, "Please select the type of card from the choices listed below:\nMaster Card (1)\nVisa (2)\nAmerican Express (3)\n");
    	pw.write("Please select the type of card from the choices listed below:\nMaster Card (1)\nVisa (2)\nAmerican Express (3)\n");
    	do {
    		cardType = newView.checkCardType();// Use for String Input
	       } while(!(cardType > 0));
    	cardName = cardType == 1?"MasterCard":(cardType !=2?"American Express":"Visa");
    	trxn.setCardType(cardType);
    	trxn.setCardName(cardName);
    	logger.log(Level.INFO, "Card Type Selected "+cardName);
    	System.out.println("Please enter the ID of the transaction!");
    	logger.log(Level.INFO, "Please enter the ID of the transaction!");
    	pw.write("Please enter the ID of the transaction!\n");
    	do {
    		ID = newView.checkInteger();// Use for Integer Input
    		} while(!(ID > 0));
    	trxn.setID(ID);
    	logger.log(Level.INFO, "ID Entered "+ID);
    	do {
    	System.out.println("Please enter the name on card!");
    	logger.log(Level.INFO, "Please enter the name on card!");
    	pw.write("Please enter the name on card!\n");
    	nameOnCard = sc.nextLine();
    	} while(!nameOnCard.matches(varcharPatt));
    	trxn.setNameOnCard(nameOnCard);
    	logger.log(Level.INFO, "Name Entered "+nameOnCard);
    	if(cardType == 1) {
			do {
				System.out.println("Enter the Master Card number"); 
				logger.log(Level.INFO, "Enter the Master Card number");
				pw.write("Enter the Master Card number\n");
				cardNumber = sc.next();
			}while(!cardNumber.matches(masterCardPatt));
		}
		
		if(cardType == 2) {
			do {
				System.out.println("Enter the Visa Card number"); 
				logger.log(Level.INFO, "Enter the Visa Card number");
				pw.write("Enter the Visa Card number\n");
				cardNumber = sc.next();
			}while(!cardNumber.matches(visaCardPatt));
		}
		
		if(cardType == 3) {
			do {
				System.out.println("Enter the American Express Card number"); 
				logger.log(Level.INFO, "Enter the American Express Card number");
				pw.write("Enter the American Express Card number\n");
				cardNumber = sc.next();
			}while(!cardNumber.matches(amexPatt));
		}
    	
    	trxn.setCardNumber(cardNumber);
    	logger.log(Level.INFO, "Card Number Entered "+cardNumber);
    	System.out.println("Please enter the unit price!");
    	logger.log(Level.INFO, "Please enter the unit price!");
    	pw.write("Please enter the unit price!\n");
    	do {
    		unitPrice = newView.checkBigDecimal();// Use for String Input
	        } while(!(unitPrice > 0));
    	trxn.setUnitPrice(unitPrice);
    	logger.log(Level.INFO, "Unit Price Entered "+unitPrice);
    	sc.nextLine();
    	System.out.println("Please enter the quantity!");
    	logger.log(Level.INFO, "Please enter the quantity!");
    	pw.write("Please enter the quantity!\n");
    	do {
    		quantity = newView.checkInteger();// Use for String Input
	        } while(!(quantity > 0));
    	trxn.setQty(quantity);
    	logger.log(Level.INFO, "Quantity Entered "+quantity);
    	System.out.println("Please enter the total price!");
    	logger.log(Level.INFO, "Please enter the total price!");
    	pw.write("Please enter the total price!\n");
    	do {
    		totalPrice = newView.checkBigDecimal();// Use for String Input
	        } while(!(totalPrice > 0));
    	trxn.setTotalPrice(totalPrice);
    	logger.log(Level.INFO, "Total Price Entered "+totalPrice);
    	do {
    	System.out.println("Please enter the expiry date!");
    	logger.log(Level.INFO, "Please enter the expiry date!");
    	pw.write("Please enter the expiry date!\n");
    	expiryDate = sc.nextLine();
    	}while(!expiryDate.matches("[^;:!@#$%^*+?<>]+") || !expiryDate.matches("^((0[1-9]|1[0-2])/(201[6-9]|202[0-9]|203[0-1]))"));
    	trxn.setExpDate(expiryDate);
    	logger.log(Level.INFO, "Expiry Date Entered "+expiryDate);
    	createdBy = System.getProperty("user.name");
    	trxn.setCreatedBy(createdBy);
    	results.add(trxn);
		// Result set get the result of the SQL query
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			String sql = "Select * from transaction.transaction where ID = "+trxn.getID();
            resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
            	String sqlInsert = "INSERT INTO transaction.transaction (ID, NameOnCard, CardNumber, UnitPrice, Quantity, TotalPrice, ExpDate, CreatedBy,CardType) VALUES ("+trxn.getID()+",'"+trxn.getNameOnCard()+"','"+trxn.getCardNumber()+"',"+trxn.getUnitPrice()+","+trxn.getQty()+","+trxn.getTotalPrice()+",'"+trxn.getExpDate()+"','"+trxn.getCreatedBy()+"','"+trxn.getCardName()+"')";
            	statement.executeUpdate(sqlInsert);
				resultSet.close();
			}
			else {
				statement.close();
				pw.close();
				return false;
			}
			
			if (statement != null) {
				statement.close();
			}
			pw.close();

		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statement = null;
		}
		return true;

	}

	public boolean updateTransaction(Connection connection) throws SQLException  {
		// DO the update SQL here
		Collection<Transaction> results = new ArrayList<Transaction>();
		Transaction trxn = new Transaction();
		View newView = new View();
		String nameOnCard ="",cardNumber = "",expiryDate="",createdBy="", cardName="";
    	int unitPrice = 0, quantity = 0, cardType = 0, totalPrice= 0, ID;
    	Statement statement = null;
    	ResultSet resultSet = null;
    	try {
        PrintWriter pw = new PrintWriter(new FileWriter(ass.outputFile, true));
    	System.out.println("Please enter the ID of the transaction!");
    	logger.log(Level.INFO, "Please enter the ID of the transaction!");
    	pw.write("Please enter the ID of the transaction!\n");
    	do {
    		ID = newView.checkInteger();// Use for String Input
    		} while(!(ID > 0));
    	trxn.setID(ID);
    	logger.log(Level.INFO, "Transaction ID Entered "+ID);
    	System.out.println("Please select the type of card from the choices listed below:\nMaster Card (1)\nVisa (2)\nAmerican Express (3)\n");
    	logger.log(Level.INFO, "Please select the type of card from the choices listed below:\\nMaster Card (1)\\nVisa (2)\\nAmerican Express (3)\\n");
    	pw.write("Please select the type of card from the choices listed below:\nMaster Card (1)\nVisa (2)\nAmerican Express (3)\n");
    	do {
    		cardType = newView.checkCardType();// Use for String Input
	       } while(!(cardType > 0));
    	cardName = cardType == 1?"MasterCard":(cardType !=2?"American Express":"Visa");
    	trxn.setCardType(cardType);
    	trxn.setCardName(cardName);
    	logger.log(Level.INFO, "Card Type Selected "+cardName);
    	do {
    	System.out.println("Please enter the name on card!");
    	logger.log(Level.INFO, "Please enter the name on card!");
    	pw.write("Please enter the name on card!\n");
    	nameOnCard = sc.nextLine();
    	}while(!nameOnCard.matches(varcharPatt));
    	trxn.setNameOnCard(nameOnCard);
    	logger.log(Level.INFO, "Name on card Entered "+nameOnCard);
    	if(cardType == 1) {
			do {
				System.out.println("Enter the Master Card number"); 
				logger.log(Level.INFO, "Enter the Master Card number");
				pw.write("Enter the Master Card number\n");
				cardNumber = sc.next();
			}while(!cardNumber.matches(masterCardPatt));
		}
		
		if(cardType == 2) {
			do {
				System.out.println("Enter the Visa Card number");
				logger.log(Level.INFO, "Enter the Visa Card number");
				pw.write("Enter the Visa Card number\n");
				cardNumber = sc.next();
			}while(!cardNumber.matches(visaCardPatt));
		}
		
		if(cardType == 3) {
			do {
				System.out.println("Enter the American Express Card number");
				logger.log(Level.INFO, "Enter the American Express Card number");
				pw.write("Enter the American Express Card number\n");
				cardNumber = sc.next();
			}while(!cardNumber.matches(amexPatt));
		}
    	trxn.setCardNumber(cardNumber);
    	logger.log(Level.INFO, "Card Number Entered "+cardNumber);
    	System.out.println("Please enter the unit price!");
    	logger.log(Level.INFO, "Please enter the unit price!");
    	pw.write("Please enter the unit price!\n");
    	do {
    		unitPrice = newView.checkBigDecimal();// Use for String Input
	        } while(!(unitPrice > 0));
    	trxn.setUnitPrice(unitPrice);
    	logger.log(Level.INFO, "Unit Price Entered "+unitPrice);
    	System.out.println("Please enter the quantity!");
    	logger.log(Level.INFO, "Please enter the quantity!");
    	pw.write("Please enter the quantity!\n");
    	do {
    		quantity = newView.checkInteger();// Use for String Input
	        } while(!(quantity > 0));
    	trxn.setQty(quantity);
    	logger.log(Level.INFO, "Quantity Entered "+quantity);
    	System.out.println("Please enter the total price!");
    	logger.log(Level.INFO, "Please enter the total price!");
    	pw.write("Please enter the total price!\n");
    	do {
    		totalPrice = newView.checkBigDecimal();// Use for String Input
	        } while(!(totalPrice > 0));
    	trxn.setTotalPrice(totalPrice);
    	logger.log(Level.INFO, "Total Price Entered "+totalPrice);
    	sc.nextLine();
    	do {
        	System.out.println("Please enter the expiry date!");
        	logger.log(Level.INFO, "Please enter the expiry date!");
        	pw.write("Please enter the expiry date!\n");
        	expiryDate = sc.nextLine();
        	}while(!expiryDate.matches("[^;:!@#$%^*+?<>]+") || !expiryDate.matches("^((0[1-9]|1[0-2])/(201[6-9]|202[0-9]|203[0-1]))"));
    	trxn.setExpDate(expiryDate);
    	logger.log(Level.INFO, "Expiry Date Entered "+expiryDate);
        createdBy = System.getProperty("user.name");
        trxn.setCreatedBy(createdBy);
    	results.add(trxn);
		// Result set get the result of the SQL query
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			String sql = "Select * from transaction.transaction where ID = "+trxn.getID();
			resultSet = statement.executeQuery(sql);
			//results = createTrxns(resultSet);
			if (resultSet.next()) {
				String sqlUpdate = "UPDATE transaction.transaction SET NameOnCard = '"+trxn.getNameOnCard()+"', UnitPrice = "+trxn.getUnitPrice()+", Quantity = "+trxn.getQty()+", TotalPrice= "+trxn.getTotalPrice()+", ExpDate='"+trxn.getExpDate()+"', CreatedBy='"+trxn.getCreatedBy()+"', CardType = '"+trxn.getCardName()+"'" + 
						"WHERE ID = "+trxn.getID();
				System.out.println(sqlUpdate);
				statement.executeUpdate(sqlUpdate);
				resultSet.close();
			}
			else {
				statement.close();
				pw.close();
				return false;
			}
			if (statement != null) {
				statement.close();
			}
			pw.close();

		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			statement = null;
			
		}
		return true;
	}

	public boolean removeTransaction(Connection connection) throws SQLException  {
		// DO the update SQL here
		View newView = new View();
		int trnxId;
		Statement statement = null;
    	ResultSet resultSet = null;
		try {
	    PrintWriter pw = new PrintWriter(new FileWriter(ass.outputFile, true));
    	System.out.println("Please enter the id of the transaction!");
    	logger.log(Level.INFO, "Please enter the id of the transaction!");
    	pw.write("Please enter the id of the transaction!\n");
    	do {
    		trnxId = newView.checkInteger();// Use for String Input
	        } while(!(trnxId > 0));
    	logger.log(Level.INFO, "Transaction ID Entered: "+trnxId);
    	
		// Result set get the result of the SQL query
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			String sql = "Select count(*) from transaction.transaction where ID = "+ trnxId;
			resultSet = statement.executeQuery(sql);
			if (resultSet != null) {
				String sqlDelete = "DELETE FROM transaction.transaction WHERE ID = "+ trnxId;
				statement.executeUpdate(sqlDelete);
				resultSet.close();
			}
			else {
				statement.close();
				pw.close();
				return false;
			}
			if (statement != null) {
				statement.close();
			}
			pw.close();

		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			statement = null;
			
		}
		return true;
	}
}
