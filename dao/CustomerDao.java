package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Customer;

import java.util.stream.IntStream;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */
	
	/**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers(String searchKeyword) {
		/*
		 * This method fetches one or more customers based on the searchKeyword and returns it as an ArrayList
		 */
		
		List<Customer> customers = new ArrayList<Customer>();

		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		/*Sample data begins*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CustomerData WHERE FirstName LIKE \'%" + searchKeyword +"%\'"
					+ "or LastName LIKE \'%" + searchKeyword + "%\'");
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("EmailAddress"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCard"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/
		
		return customers;
	}


	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		Customer customer = new Customer();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT C.*, SUM(Z.ClosingBid) as Profits FROM AuctionData Z, ItemData I, CustomerData C WHERE Z.ItemID = I.ItemID AND Z.SellerID = C.CustomerID AND Z.ClosingBid IS NOT NULL AND Z.ClosingBid >= Z.Reserve GROUP BY C.CustomerID LIMIT 1");
			
			while (rs.next()) {
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("EmailAddress"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCard"));
				customer.setRating(rs.getInt("Rating"));
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	
		return customer;
		
	}

	public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 * Each customer record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs= st.executeQuery("SELECT * FROM CustomerData");
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(Integer.toString(rs.getInt("CustomerID")));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("EmailAddress"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customers.add(customer);	
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return customers;
	}

	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		
		/*Sample data begins*/
		Customer customer = new Customer();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from CustomerData where CustomerID = '" + customerID +"'");
			while (rs.next()) {
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("EmailAddress"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCard"));
				customer.setRating(rs.getInt("Rating"));
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		

		/*Sample data ends*/
		
		return customer;
	}
	
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */
		
		//David Haven't test the DELETE yet. Not sure if this way works.
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			st.executeUpdate("DELETE FROM CustomerData WHERE CustomerID= '"+customerID+"'");
		}catch(Exception e) {
			System.out.println(e);
			return "failure";
		}

		//David Check commit
		return "success";

		
	}


	public String getCustomerID(String username) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */

		String customerID = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select CustomerID from CustomerData where EmailAddress = '" + username +"'");
			while (rs.next()) {
				customerID = (rs.getString("CustomerID"));
			}
		} catch(Exception e) {
			System.out.println(e);
		}
				
		return customerID;
	}


	public List<Customer> getSellers() {
		
		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		List<Customer> customers = new ArrayList<Customer>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs= st.executeQuery("SELECT C.* FROM CustomerData C, AuctionData A WHERE C.CustomerID= A.SellerID GROUP BY CustomerID");
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(Integer.toString(rs.getInt("CustomerID")));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("EmailAddress"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return customers;

	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		try {
			String sql="INSERT INTO CustomerData (LastName, FirstName, Address, City, State, ZipCode, Telephone, EmailAddress, CreditCard, Rating) VALUES ('"+customer.getLastName()+"', '"+customer.getFirstName()+"', '"+customer.getAddress()+"', '"+customer.getCity()+"', '"+customer.getState()+"', '"+customer.getZipCode()+"', '"+customer.getTelephone()+ "', '"+customer.getEmail()+"', '"+customer.getCreditCard()+"', '"+customer.getRating()+"')";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			st.executeUpdate(sql);
		}catch(Exception e) {
			System.out.println(e);
			return "failure";
		}	
		return "success";

	}

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		try {
			
			// David TODO Assuming CustomerID(SSN) is not able to changed and we use it to make sure which customer to edit
			String sql=;
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			st.executeUpdate("UPDATE CustomerData SET Address = '"+customer.getAddress()+"', City ='"+customer.getCity()+"', CreditCard = '"+customer.getCreditCard()+"', EmailAddress ='"+customer.getEmail()+"', FirstName ='"+customer.getFirstName()+"', LastName = '"+customer.getLastName()+"', Rating ='"+customer.getRating()+"', State = '"+customer.getState()+"', Telephone = '"+customer.getTelephone()+"', ZipCode = '"+customer.getZipCode()+
					"' WHERE CustomerID='"+customer.getCustomerID()+"'");
		}catch(Exception e) {
			System.out.println(e);
			return "failure";
		}	
		return "success";

	}

}