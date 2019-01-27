package ca.mcgill.ecse.restoApp.controller;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.Menu;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.Table.Status;

public class RestoAppController
{
	public RestoAppController() 
	{
	}
	
	//method to create a table
	public static void createTable(int number, int x, int y,int width, int length, int numberOfSeats) throws InvalidInputException {
		if( x <0 || y<0 || width<20 || length<20 || numberOfSeats<1) {
			throw new InvalidInputException("The inputs are not valid");
		}
		RestoApp restoApp = RestoAppApplication.getRestoApp();
		List<Table> currentTables =  restoApp.getCurrentTables();
		for(Table currentTable: currentTables) {
			try 
			{
				if(doesOverlap(x, y, width, length,currentTable) == true)
				{
					throw new InvalidInputException("Overlap");
				}
			} catch (Exception e) 
			{
				JOptionPane.showMessageDialog(null, "The new table's location is overlapping with table number "+currentTable.getNumber()+". Change the coordinate inputs.");
				throw new RuntimeException(e);
			}
		}
		Table table = new Table(number, x, y, width, length, restoApp);
		restoApp.addCurrentTable(table);
		for(int z=0; z<numberOfSeats;z++) {
			Seat seat = table.addSeat();
			table.addCurrentSeat(seat);
		}
		RestoAppApplication.save();
		JOptionPane.showMessageDialog(null, "The table "+number+" was created successfuly");
	}	
	
	//method to move a table
	public static void moveTable(Table table, int x, int y) throws InvalidInputException
	{
		if(table == null || (x < 15 || y < 15))
		{
			JOptionPane.showMessageDialog(null, "The new coordinates are invalid. Select an integer bigger than 15.");
			throw new InvalidInputException("Invalid Input");
		}

		RestoApp r = table.getRestoApp();
		List<Table> tableList = r.getCurrentTables();	
		for(int i = 0; i < tableList.size(); i++)
		{
			Table curTable = tableList.get(i);
			
			if (curTable != table) 
			{		
				try 
				{
					if(doesOverlap(x, y, table.getWidth(), table.getLength(), curTable) == true)
					{
						throw new InvalidInputException("Overlap");
					}
				} catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, "The new table's location is overlapping with table number "+curTable.getNumber()+". Change the coordinate inputs.");
					throw new RuntimeException(e);
				}
			
			}
		}
		
		table.setX(x);
		table.setY(y);
		
		RestoAppApplication.save();
		JOptionPane.showMessageDialog(null, "The table "+table.getNumber()+" was updated successfuly");
	}
	
	//method to remove a table
	public static void removeTable(Table table) throws InvalidInputException {
		//throw InvalidInputException if table is null
		if(table == null) {
			JOptionPane.showMessageDialog(null, "No table was selected.");
			throw new InvalidInputException("No table was selected");
		}
		
		//throw InvalidInputException if 
		boolean reserved = table.hasReservations();
		if(reserved) {
			JOptionPane.showMessageDialog(null, "The selected table has a reservation and can't be removed.");
			throw new InvalidInputException("Table has a reservation");
		}
		
		RestoApp r = RestoAppApplication.getRestoApp();
		
		//List<Order> currentOrders = r.getCurrentOrders();
		java.util.List<Order> currentOrders = r.getCurrentOrders();
		
		for(Order order : currentOrders) {
			java.util.List<Table> tables = order.getTables();
			boolean inUse = tables.contains(table);
			if(inUse) {
				JOptionPane.showMessageDialog(null, "The selected table has an order and can't be removed.");
				throw new InvalidInputException("Table has an order");
			}
		}
		Integer tableNumber = table.getNumber();
		r.removeCurrentTable(table);
		RestoAppApplication.save();
		JOptionPane.showMessageDialog(null, "The table with number "+tableNumber+" is being removed correctly.");
		
	}
	
	//method to update a table
	public static void updateTable(Table table, int newNumber, int numberOfSeats) throws InvalidInputException
	{
		String error;
		if(table == null)
		{
			error = "There is no selected table.";
			JOptionPane.showMessageDialog(null, "There is no table selected.");
			throw new InvalidInputException(error);
		}
		if(newNumber <= 0)
		{
			error = "The table index can't be zero or negative.";
			JOptionPane.showMessageDialog(null, "The table index can't be zero or negative. Change the input.");
			throw new InvalidInputException(error);
		}
		if(numberOfSeats <= 0)
		{
			error = "There can't be less than one seat";
			JOptionPane.showMessageDialog(null, "The number of seats can't be zero or negative. Change the input.");
			throw new InvalidInputException(error);
		}
		
		Boolean reserved = table.hasReservations();
		if(reserved ==true)
		{
			JOptionPane.showMessageDialog(null, "This table has already been reserved for this time slot.");
			throw new InvalidInputException("This table has already been reserved.");
		}
		
		RestoApp r = RestoAppApplication.getRestoApp();
		
		List<Order> currentOrders = r.getCurrentOrders(); //have to change cast type
		
		for(int i = 0; i < currentOrders.size(); i++)
		{
			List<Table> tables = currentOrders.get(i).getTables();
			
			Boolean inUse = tables.contains(table);
			
			if(inUse == true)
			{
				JOptionPane.showMessageDialog(null, "This table number is already in use. Change the inputs.");
				throw new InvalidInputException("This table number is already in use");
			}
		}
		
		table.setNumber(newNumber);
		
		int n =table.numberOfCurrentSeats();
		
				
		for(int j = 1; j <= (numberOfSeats - n); j++)
		{
			Seat seat = table.addSeat();
			
			table.addCurrentSeat(seat);
		}
		
		for(int k = 1; k <= (n-numberOfSeats); k++)
		{
			Seat seat = table.getCurrentSeat(0);
			
			table.removeCurrentSeat(seat);
			
		}
		
		RestoAppApplication.save();
	}
	
	//create the menu
	public static ArrayList<MenuItem> getMenuItems(MenuItem.ItemCategory itemCategory){
		   
	   List<MenuItem> completeList = new ArrayList<MenuItem>();
	   RestoApp restoApp = RestoAppApplication.getRestoApp();
	   ca.mcgill.ecse223.resto.model.Menu menu = restoApp.getMenu();
	   
	   ArrayList<MenuItem> categoryList = new ArrayList<MenuItem>();
	   
	   completeList = menu.getMenuItems();
	   
	   for (MenuItem item : completeList) {
	     boolean current = item.hasCurrentPricedMenuItem();
	     MenuItem.ItemCategory myItemCategory = item.getItemCategory();
	     if (current && myItemCategory.equals(itemCategory)){
	       categoryList.add(item);
	     }
	   }
	  return categoryList;	   
	}
	
	//method to start an order
	public static void startOrder(List<Table> tables) throws InvalidInputException {
		try {
			if(tables == null) 
			{
				throw new InvalidInputException("The list of tables is null");

			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "The list of tables is null. Change the inputs.");
		}
		
		RestoApp r = RestoAppApplication.getRestoApp();
		
		List<Table> currentTables = r.getCurrentTables();
		
		boolean current = true;
		for(Table table : tables) 
		{
			current = currentTables.contains(table);
		}
		try {
			if(current == false)
			{
				throw new InvalidInputException("There is a table that is not part of currentTables");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "There is a table that is not part of currentTables. Change the inputs.");
		}

		boolean orderCreated = false;
		Order newOrder = null;
		Order lastOrder;
		for(Table table: tables)
		{
			if(orderCreated)
			{
				table.addToOrder(newOrder);
			}
			else
			{
				lastOrder = null;
				if(table.numberOfOrders()>0)
				{
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}
				
				table.startOrder();
	
				if(table.numberOfOrders()>0 && !table.getOrder(table.numberOfOrders()-1).equals(lastOrder))
				{
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders()-1);
				}
			}	
		}
		
		try {
			if(orderCreated == false)
			{
				throw new InvalidInputException("No order was created");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No order was created.");
		}	
		r.addCurrentOrder(newOrder);
		RestoAppApplication.save();
		JOptionPane.showMessageDialog(null, "The table was assigned and the order was added successfuly");
	}
	
	//method to end an order
	public static void endOrder(Order order)throws InvalidInputException
	{
		//throw InvalidInputException if order is null
		try {
			if(order == null) 
			{
				throw new InvalidInputException("There is no order for this table.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "There is no order for this table.");
			return;
		}
		
		RestoApp r = RestoAppApplication.getRestoApp();
		
		List<Order> currentOrders = r.getCurrentOrders();
		
		Boolean current = currentOrders.contains(order);
		
		try {
			if(current == false) 
			{
				throw new InvalidInputException("There is no order for this table.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "There is no order for this table");
			return;
		}
		
		List<Table> tables = new ArrayList<Table>();
		for(Table table:order.getTables()) {
			tables.add(table);
		}
		
		for(Table table:tables)
		{	
			if (table.numberOfOrders() > 0 && table.getOrder(table.numberOfOrders()-1).equals(order)) 
			{
				table.endOrder(order);
			}
		}
		
		
		if(allTablesAvailibleOrDifferentCurrentOrder(tables,order) == true)
		{
			r.removeCurrentOrder(order);
		}
				
		RestoAppApplication.save();
		//JOptionPane.showMessageDialog(null, "The order was ended successfuly");
	}
	
	public static Boolean allTablesAvailibleOrDifferentCurrentOrder(List<Table> tables, Order order)//verify
	{
		Boolean x;
		x = false;
		
		for(Table table:tables)
		{
			if(table.getStatus() == Status.Available || table.getOrder(table.numberOfOrders()-1) != order)
			{
				x = true;
			}
			else
			{
				x = false;
				break;
			}
		}
		return x;
	}
	
	public static void createReservation(Date date, Time time, int numberInParty, String contactName, String contactEmailAddress, String contactPhoneNumber, List<Table> tables) throws InvalidInputException
    {
        RestoApp restoApp = RestoAppApplication.getRestoApp();
        List<Table> currentTables = restoApp.getCurrentTables();
        int seatCapacity = 0;
        
        for(int j=0; j<tables.size();j++) {
        	Table table = tables.get(j);
            boolean current = currentTables.contains(table);  
            try {
                if(current == false)
                {
                    throw new InvalidInputException("is not contained");
                }
            } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "is not contained");
              return;
            }
        	seatCapacity+=table.numberOfCurrentSeats();
        }
        try {
            if(seatCapacity < numberInParty)
            {
                throw new InvalidInputException("There is not enough space for everybody at those tables");
            }
	        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "There is not enough space for everybody at those tables");
              return;   
	    }
        for(int i = 0; i < tables.size(); i++)
        {
            Table table = tables.get(i);
            
            boolean current = currentTables.contains(table);
            
            
            try {
                if(current == false)
                {
                    throw new InvalidInputException("is not contained");
                }
            } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "is not contained");
              return;
            }
             
            List<Reservation> reservations = table.getReservations();
            
            boolean overlaps = false;
            for(Reservation reservation : reservations)
            {
                overlaps = reservation.doesOverlap(date, time);
                try {
                    if(overlaps == true)
                    {
                        throw new InvalidInputException("A reservation already exists during that time");
                    }
                } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "A reservation already exists during that time");
                    return;
                }
                
                

            }
        }
        
        Table[] tableArray = new Table[tables.size()];
        int i=0;
        for(Table table: tables) 
        {
            tableArray[i] = table;
            i++;
        }
  
        RestoApp r = RestoAppApplication.getRestoApp();
        Reservation res = new Reservation(date, time, numberInParty, contactName, contactEmailAddress, contactPhoneNumber, r, tableArray);
        JOptionPane.showMessageDialog(null, "Reservation Number: " + res.getReservationNumber()+" was created successfuly");
        RestoAppApplication.save();
        return;
    }
    
   
	
	public static boolean doesOverlap(int x, int y, int width, int length, Table aTable) throws InvalidInputException
	{
		  
		  int topLeftX = aTable.getX() - 15;
		  int topLeftY = aTable.getY() -15;
		  
		  int bottomrRightX = aTable.getX() + aTable.getWidth() + 15;
		  int bottomrRightY = aTable.getY() + aTable.getLength() + 15;
				  
		  //one rectangle is on the left of the other one
		  if(x -15 >= bottomrRightX || topLeftX >= (x + width + 15))
			  return false;
		  
		  //one rectangle is above the other one
		  if(bottomrRightY <= y - 15 || y + length + 15 <= topLeftY)
			  return false;
	  
		  return true;
	}
	
	//set the new rating of an order item
	public static void setRating(Integer aNumber, OrderItem aOrderItem) throws InvalidInputException
	{
		if(aNumber<=0 || aNumber>5 || aNumber==null) {
			JOptionPane.showMessageDialog(null, "The rating must be an integer between 1 and 5. Change the input.");
			throw new InvalidInputException("The inputs are not valid");
		}
		PricedMenuItem item = aOrderItem.getPricedMenuItem();
		if(item==null) {
			JOptionPane.showMessageDialog(null, "The current item is no longer part of the priced items.");
			throw new InvalidInputException("The inputs are not valid");
		}
		aOrderItem.setRating(aNumber);
		JOptionPane.showMessageDialog(null, "Setting the rating worked properly.");
		RestoAppApplication.save();
	}
	
	//returns the overall rating of a menu item
	public static int getOverall(MenuItem aMenuItem) throws InvalidInputException {
		if(aMenuItem==null) {
			throw new InvalidInputException("No Menu item selected");
		}
		PricedMenuItem pricItem = aMenuItem.getCurrentPricedMenuItem();
		if(pricItem==null) {
			throw new InvalidInputException("The menu item doesn't have a current priced menu item.");
		}
		List<OrderItem> orderItems = pricItem.getOrderItems();
		if(orderItems==null) {
			throw new InvalidInputException("The order items don't exist.");
		}
		else if(orderItems.isEmpty()) {
			return 0;
		}
		else {
			int totalRating = 0;
			int counter = 0;
			for(OrderItem orderItem : orderItems) {
				totalRating += orderItem.getRating();
				counter++;
			}
			int overallRating = totalRating/counter;
			return overallRating;
		}
	}
	
	//method to view the orders of the customers of a table
	public static List<OrderItem> getOrderItems(Table table) throws InvalidInputException
	{
		RestoApp r = RestoAppApplication.getRestoApp();
		
		List<Table> currentTables = r.getCurrentTables();
		
		boolean current = currentTables.contains(table);
		
		if(current == false)
		{
			throw new InvalidInputException("table is not a current table");
		}
		
		Status status = table.getStatus();
		
		if(status == Status.Available)
		{
			throw new InvalidInputException("The table is available, and so that means that there are no orders associated to the table");
		}
		
		Order lastOrder = null;
		if(table.numberOfOrders() > 0)
		{
			lastOrder = table.getOrder(table.numberOfOrders() - 1);
		}
		else
		{
			throw new InvalidInputException("table has no orders");
		}
		
		List<Seat> currentSeats = table.getCurrentSeats();
		
		List<OrderItem> result = new ArrayList<OrderItem>();
		
		for(Seat seat : currentSeats)
		{
			List<OrderItem> orderItems = seat.getOrderItems();
			
			for(OrderItem orderItem : orderItems)
			{
				Order order = orderItem.getOrder();
				
				if(lastOrder.equals(order) && !result.contains(orderItem))
				{
					result.add(orderItem);
				}
			}
		}
		
		return result;
	}
	
	public static void orderMenuItem(MenuItem menuItem, int quantity, List<Seat> seats)
	{
		RestoApp r = RestoAppApplication.getRestoApp();
		
		//////null input
		try 
		{
			if(menuItem == null || seats == null || quantity < 0)
			{
				throw new InvalidInputException("null menu or null seats or negative quantity");
			}
		} catch (InvalidInputException e) {
			  JOptionPane.showMessageDialog(null, "null menu or null seats or negative quantity");
			  return;
		}
		
		///////menu item has a price
		boolean current = menuItem.hasCurrentPricedMenuItem();
		try 
		{
            if(current == false)
            {
                throw new InvalidInputException("no priced menu item");
            }
        } 
		catch (Exception e) 
		{
          JOptionPane.showMessageDialog(null, "no priced menu item");
          return;
        }
		
		//////check if all seats/tables are contained in table
		//////check if all seats have same last order
		List<Table> currentTables = r.getCurrentTables();
		Order lastOrder = null;
		
		for(Seat seat : seats)
		{
			Table table = seat.getTable();
			boolean tableContained = currentTables.contains(table);
			
			try 
			{
	            if(tableContained == false)
	            {
	                throw new InvalidInputException("table not contained");
	            }
	        } 
			catch (Exception e)
			{
	          JOptionPane.showMessageDialog(null, "table not contained");
	          return;
	        }
			
			List<Seat> currentSeats = table.getCurrentSeats();
			boolean seatContained = currentSeats.contains(seat);
			
			try 
			{
	            if(seatContained == false)
	            {
	                throw new InvalidInputException("seat not contained");
	            }
	        } 
			catch (Exception e) 
			{
	          JOptionPane.showMessageDialog(null, "seat not contained");
	          return;
	        }
			
		//////check if all seats have same last order
			if(lastOrder == null)
			{
				if(table.numberOfOrders() > 0)
				{
					lastOrder = table.getOrder(table.numberOfOrders()-1);
				}
				else
				{
					try 
					{
						throw new InvalidInputException("error");
					} 
					catch (InvalidInputException e) 
					{
						JOptionPane.showMessageDialog(null, "no order on the table");
				        return;
					}
				}
			}
			else
			{
				Order comparedOrder = null;
				
				if (table.numberOfOrders() > 0) 
				{
					comparedOrder = table.getOrder(table.numberOfOrders() - 1);
				}
				else 
				{
					try 
					{
						throw new InvalidInputException("error");
					} 
					catch (InvalidInputException e) 
					{
						JOptionPane.showMessageDialog(null, "error1");
						return;
					}
				}
				
				if(!comparedOrder.equals(lastOrder))
				{
					try 
					{
						throw new InvalidInputException("error");
					} 
					catch (InvalidInputException e) 
					{
						JOptionPane.showMessageDialog(null, "seats don't have same last order");
						return;
					}
				}
			}
		}
		
		
		if(lastOrder.equals(null))
		{
			try 
			{
				throw new InvalidInputException("null order");
			} 
			catch (InvalidInputException e) 
			{
				JOptionPane.showMessageDialog(null, "null order");
				return;
			}
		}
		
		//creates order item 
		PricedMenuItem pmi = menuItem.getCurrentPricedMenuItem();
		boolean itemCreated = false;
		OrderItem newItem = null;
		
		for(Seat seat : seats)
		{
			Table table = seat.getTable();
			
			if(itemCreated)
			{
				table.addToOrderItem(newItem, seat);
			}
			else
			{
				OrderItem lastItem = null;
				if(lastOrder.numberOfOrderItems() > 0)
				{
					lastItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1);
				}
				
				table.orderItem(quantity, lastOrder, seat, pmi);
				
				if(lastOrder.numberOfOrderItems() > 0 && !lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1).equals(lastItem))
				{
					itemCreated = true;
					newItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems()-1);	
				}
				
			}
			
		}
		
		RestoAppApplication.save();
		JOptionPane.showMessageDialog(null, "item added");
	}
	
	//cancel an order item
	public static void cancelOrderItem(OrderItem orderItem)
	{
		try {
			if(orderItem == null) 
			{
				throw new InvalidInputException("OrderItem is empty");
			}
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "OrderItem is empty");
			return;
		}
		
		List<Seat> seats = orderItem.getSeats();
		Order order = orderItem.getOrder();
		List<Table> tables = new ArrayList<Table>();
		for(Seat seat: seats)
		{
			Table table = seat.getTable();
			Order lastOrder = null;
			if(table.numberOfOrders() > 0)
			{
				lastOrder = table.getOrder(table.numberOfOrders()-1);
			}
			else
			{
				try
				{
					throw new InvalidInputException("No orders on table");
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "No orders on table.");
					return;
				}
				
			}
						
			if(lastOrder.equals(order) && !tables.contains(table))
			{
				tables.add(table); 
			}
		}
		
		for(Table table: tables)
		{
			table.cancelOrderItem(orderItem);
		}
		JOptionPane.showMessageDialog(null, "Success");
		RestoAppApplication.save();
		
	}
	
	//cancel an order
	public static void cancelOrder(Table table)
	{
		try
		{
			if(table == null)
			{
				throw new InvalidInputException("Table has no order.");
			}
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Table has no order");
			return;
		}
		RestoApp r = RestoAppApplication.getRestoApp();
		List<Table> currentTables = r.getCurrentTables();
		Boolean current = currentTables.contains(table);
		try
		{
			if(current == false)
			{
				throw new InvalidInputException("Selected table doesn't exist.");
			}
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Selected table doesn't exist.");
			return;
		}
		table.cancelOrder();
		JOptionPane.showMessageDialog(null, "Success");
		RestoAppApplication.save();
	}
	
	//update menu part
	public static void removeMenuItem(MenuItem menuItem) {
		  if (menuItem.hasCurrentPricedMenuItem()) {
		    menuItem.setCurrentPricedMenuItem(null);
		  }
		  RestoAppApplication.save();
		  return;
		}
		
		public static void addMenuItem(String name, ItemCategory category, double price) throws InvalidInputException{
		  RestoApp r = RestoAppApplication.getRestoApp();
		  Menu menu = r.getMenu();
		  for (MenuItem k : menu.getMenuItems()) {
		    try {
	          if(name.equals(k.getName()) && k.hasCurrentPricedMenuItem())
	          {
	            throw new InvalidInputException("An item with the same name already exists in the menu!");
	          }  
	        } catch (InvalidInputException e1) {
	          JOptionPane.showMessageDialog(null, "An item with the same name already exists in the menu!");
	          return;
	        }      
	       } 
		  MenuItem menuItem = new MenuItem(name, menu);
		  menuItem.setItemCategory(category);
	      PricedMenuItem pmi = new PricedMenuItem(price, r, menuItem);
	      menuItem.setCurrentPricedMenuItem(pmi);
	      RestoAppApplication.save();
	      return;
	      }
		  
		public static void editMenuItem(MenuItem item, String newName, ItemCategory newCategory, double newPrice) {
		  RestoApp r = RestoAppApplication.getRestoApp();
	      Menu menu = r.getMenu();
	      String oldName = item.getName();
	      ItemCategory oldCategory = item.getItemCategory();
	      if ((oldName.equals(newName) && (!oldCategory.equals(newCategory) || newPrice != item.getCurrentPricedMenuItem().getPrice()))) {
	        item.setName(newName);
	        item.setItemCategory(newCategory);
	        PricedMenuItem npmi = new PricedMenuItem(newPrice, r, item);
	        item.setCurrentPricedMenuItem(npmi); 
	        RestoAppApplication.save();
	        return;
	      }
	      for (MenuItem k : menu.getMenuItems()) {
	        try {
	          if(newName.equals(k.getName()) && k.hasCurrentPricedMenuItem())
	          {
	            throw new InvalidInputException("An item with the same name already exists in the menu!");
	          }  
	        } catch (InvalidInputException e3) {
	          JOptionPane.showMessageDialog(null, "An item with the same name already exists in the menu!");
	          return;
	        }      
	       } 
	        item.setName(newName);
	        item.setItemCategory(newCategory);
	        if (newPrice != item.getCurrentPricedMenuItem().getPrice()) {
	          PricedMenuItem npmi = new PricedMenuItem(newPrice, r, item);
	            item.setCurrentPricedMenuItem(npmi);   
	        }
	        
		  RestoAppApplication.save();
		  return;
		}
		
		//issue a bill
		public static void issueBill(List<Seat> seats) throws InvalidInputException {
			if (seats == null) {
				throw new InvalidInputException("Error: null seat list");
			}
			if (seats.isEmpty()) {
				throw new InvalidInputException("Error: empty seat list");
			}
			RestoApp r = RestoAppApplication.getRestoApp();
			List<Table> currentTables = r.getCurrentTables();
			Order lastOrder = null;
			for (Seat s : seats) {

				Table table = s.getTable();
				if (currentTables.contains(table) == false) {
					throw new InvalidInputException("Error: one of seats entered belongs to a non current table");
				}
				List<Seat> currentSeats = table.getCurrentSeats();

				if (currentSeats.contains(s) == false) {
					throw new InvalidInputException("Error: one of seats entered is not a current seat");
				}
				if (lastOrder == null) {
					if (table.numberOfOrders() > 0) {
						lastOrder = table.getOrder(table.numberOfOrders() - 1);
					} else {
						throw new InvalidInputException("Error: table has no orders 1");
					}
				} else {
					Order comparedOrder = null;
					if (table.numberOfOrders() > 0) {
						comparedOrder = table.getOrder(table.numberOfOrders() - 1);
					} else {
						throw new InvalidInputException("Error: table has no orders 2");
					}
					if (!comparedOrder.equals(lastOrder)) {
						throw new InvalidInputException("Error: compared order is not equal to last order");
					}
				}
			}
			if (lastOrder == null) {
				throw new InvalidInputException("Error: last order is null");
			}
			boolean billCreated = false;
			Bill newBill = null;

			for (Seat s : seats) {
				Table table = s.getTable();
				if (billCreated) {
					table.addToBill(newBill, s);
				} else {
					Bill lastBill = null;

					if (lastOrder.numberOfBills() > 0) {
						lastBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);

					}
					table.billForSeat(lastOrder, s);
					if (lastOrder.numberOfBills() > 0
							&& !lastOrder.getBill(lastOrder.numberOfBills() - 1).equals(lastBill)) {
						billCreated = true;
						newBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);

					}
				}
			}
			if (billCreated == false) {
				throw new InvalidInputException("Error: no bill created");
			}
			RestoAppApplication.save();
			JOptionPane.showMessageDialog(null, "A bill was issued correctly!");			
		}

}	
