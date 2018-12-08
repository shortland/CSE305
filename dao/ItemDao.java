package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Auction;
import model.Bid;
import model.Employee;
import model.Item;

public class ItemDao {
	
	public List<Item> getItems() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of all the items has to be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 */

		List<Item> items = new ArrayList<Item>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ItemData");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				item.setNumCopies(rs.getInt("AmountInStock"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}
	
	public List<Item> getBestsellerItems() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of the bestseller items has to be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 */

		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.ItemID, I.ItemDescription, I.ItemName, I.ItemType, COUNT(A.ItemID) as AmountSold FROM AuctionData A, ItemData I WHERE I.ItemID = A.ItemID AND A.ClosingBid IS NOT NULL AND A.ClosingBid >= A.Reserve GROUP BY A.ItemID ORDER BY COUNT(ItemID) DESC");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				item.setNumCopies(rs.getInt("AmountSold"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}

	public List<Item> getSummaryListing(String searchKeyword) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of summary listing of revenue generated by a particular item or item type must be implemented
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 * Store the revenue generated by an item in the soldPrice attribute, using setSoldPrice method of each "item" object
		 */

		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.*, SUM(A.ClosingBid) as Profits FROM AuctionData A, ItemData I, CustomerData C WHERE A.ItemID = I.ItemID AND (I.ItemName LIKE '%" + searchKeyword + "%' OR I.ItemType LIKE '%" + searchKeyword + "%' OR (C.FirstName LIKE '%" + searchKeyword + "%' AND C.CustomerID = A.BuyerID)) AND A.ClosingBid IS NOT NULL AND A.ClosingBid >= A.Reserve GROUP BY I.ItemID");
			while (rs.next()) {
				Item item = new Item();
				item.setName(rs.getString("ItemName"));
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setSoldPrice(rs.getInt("Profits"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}

	public List<Item> getItemSuggestions(String customerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch item suggestions for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom the item suggestions are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 */

		List<Item> items = new ArrayList<Item>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.* FROM ItemData I WHERE I.ItemType IN (SELECT DISTINCT I.ItemType FROM ItemData I WHERE I.ItemID IN (SELECT DISTINCT I.ItemID FROM AuctionData A, ItemData I, CustomerData C WHERE A.BuyerID = '"+customerID+"' AND A.ItemID = I.ItemID))");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				item.setNumCopies(rs.getInt("AmountInStock"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}

	public List getItemsBySeller(String sellerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch items sold by a given seller, indicated by sellerID, must be implemented
		 * sellerID, which is the Sellers's ID who's items are fetched, is given as method parameter
		 * The bid and auction details of each of the items should also be fetched
		 * The bid details must have the highest current bid for the item
		 * The auction details must have the details about the auction in which the item is sold
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each bid record is required to be encapsulated as a "Bid" class object and added to the "bids" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items, bids and auctions Lists have to be added to the "output" List and returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Bid> bids = new ArrayList<Bid>();
		List<Auction> auctions = new ArrayList<Auction>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			//David Changed the SQL Bcus they want to get the highest bid in the auction which is the B.Value = A.CurrentBid
			ResultSet rs = st.executeQuery("SELECT A.*, I.*, B.* FROM AuctionData A, ItemData I, Bid B, CustomerData C WHERE A.SellerID = '" + sellerID + "' AND A.ItemID = I.ItemID AND B.AuctionID = A.AuctionID AND B.Value = A.CurrentBid GROUP BY A.AuctionID, B.BidNum");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				items.add(item);
				
				Bid bid = new Bid();
				bid.setCustomerID(rs.getString("BuyerID"));
				bid.setBidPrice(rs.getInt("CurrentBid"));
				bids.add(bid);
				
				Auction auction = new Auction();
				auction.setMinimumBid(rs.getInt("Reserve"));
				auction.setBidIncrement(rs.getInt("Increment"));
				auction.setAuctionID(rs.getInt("AuctionID"));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		output.add(items);
		output.add(bids);
		output.add(auctions);
		
		return output;
	}

	public List<Item> getItemTypes() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList
		 * A query to fetch the unique item types has to be implemented
		 * Each item type is to be added to the "item" object using setType method
		 */
		
		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINCT ItemType FROM ItemData");
			while (rs.next()) {
				Item item = new Item();
				item.setType(rs.getString("ItemType"));
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}

	public List getItemsByName(String itemName) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The itemName, which is the item's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch items containing itemName in their name has to be implemented
		 * Each item's corresponding auction data also has to be fetched
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.*, A.* FROM ItemData I, AuctionData A WHERE I.ItemID = A.ItemID AND I.ItemName LIKE '%" + itemName + "%' AND A.ClosingBidID IS NULL");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				items.add(item);
				
				Auction auction = new Auction();
				auction.setMinimumBid(rs.getInt("Reserve"));
				auction.setBidIncrement(rs.getInt("Increment"));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		output.add(items);
		output.add(auctions);
		
		return output;
	}

	public List getItemsByType(String itemType) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The itemType, which is the item's type on which the query has to be implemented, is given as method parameter
		 * Query to fetch items containing itemType as their type has to be implemented
		 * Each item's corresponding auction data also has to be fetched
		 * Each item record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Each auction record is required to be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			// David Changed bcuz fetch items containing itemType as their type and each item's corresponding auction data also has to be fetched
			// David Changed the Method to run 2 different SQL
			ResultSet rs = st.executeQuery("SELECT I.* FROM ItemData I, AuctionData A WHERE I.ItemType LIKE '%"+itemType+"%' GROUP BY I.ItemID");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				items.add(item);
			}
			rs= st.executeQuery("SELECT A.* FROM ItemData I, AuctionData A WHERE I.ItemType LIKE '%"+itemType+"%' AND A.ItemID = I.ItemID AND A.ClosingBidID IS NULL");
			while (rs.next()) {
				Auction auction = new Auction();
				auction.setMinimumBid(rs.getInt("Reserve"));
				auction.setBidIncrement(rs.getInt("Increment"));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		output.add(items);
		output.add(auctions);
		
		return output;
	}

	public List<Item> getBestsellersForCustomer(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" ArrayList.
		 * Query to get the Best-seller list of items for a particular customer, indicated by the customerID, has to be implemented
		 * The customerID, which is the customer's ID for whom the Best-seller items have to be fetched, is given as method parameter
		 */

		List<Item> items = new ArrayList<Item>();
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://138.197.50.244:3306/LittleBobbyTablesAuctionHouse",  "littlebobbytables", "bestcse305group");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT I.ItemID, I.ItemDescription, I.ItemName, I.ItemType, COUNT(A.ItemID) as AmountSold FROM AuctionData A, ItemData I WHERE I.ItemID = A.ItemID AND A.ClosingBid IS NOT NULL AND A.ClosingBid >= A.Reserve GROUP BY A.ItemID ORDER BY COUNT(ItemID) DESC");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setDescription(rs.getString("ItemDescription"));
				item.setType(rs.getString("ItemType"));
				item.setName(rs.getString("ItemName"));
				item.setNumCopies(rs.getInt("AmountSold")); 
				items.add(item);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return items;
	}
}