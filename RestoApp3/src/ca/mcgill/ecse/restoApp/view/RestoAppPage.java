package ca.mcgill.ecse.restoApp.view;
import ca.mcgill.ecse.restoApp.application.RestoAppApplication;


import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RestoAppPage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private HashMap<Integer,LayoutVisualizer> rectangles;
	private HashMap<Integer,List<LayoutVisualizer>> seats;
	private HashMap<Integer,JLabel>rectangleId;
	//public static int lastSelectedTableNumber;
	
	public void changeColor(int tableNumber){
		if(Table.getWithNumber(tableNumber).getStatusFullName().equals("Available")){
			rectangleId.get(tableNumber).setBackground(Color.BLACK);
			rectangleId.get(tableNumber).setOpaque(true);
		}
		else{
			rectangleId.get(tableNumber).setBackground(Color.RED);
			rectangleId.get(tableNumber).setOpaque(true);
		}
	}
	
	/**
	 * Create the application.
	 */
	public RestoAppPage() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() { 
		RestoApp r = RestoAppApplication.getRestoApp();
		List<Table> tables = r.getCurrentTables();
		rectangles = new HashMap<Integer, LayoutVisualizer>();
		seats = new HashMap<Integer, List<LayoutVisualizer>>();
		rectangleId = new HashMap<Integer,JLabel>();
          
		frame = new JFrame();
		frame.setBounds(000, 000, 1400, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//upper left panel of the UI, used to display the restaurant layout
		JPanel panel1 = new JPanel();
		panel1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel1.setBounds(0, 0, 700, 425);
		panel1.setLayout(null);
		panel1.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panel1);
			
		//lower right panel of the UI, used to display the change table options
		JPanel panel2 = new JPanel();
		panel2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel2.setBounds(700, 425, 700, 425);
		frame.getContentPane().add(panel2);
		panel2.setLayout(null);
		panel2.setBackground(Color.LIGHT_GRAY);
		
		//lower left panel of the UI, used to display the menu of the restaurant
		JPanel panel3 = new JPanel();
		panel3.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel3.setBounds(0, 425, 700, 425);
		frame.getContentPane().add(panel3);
		panel3.setLayout(null);
		panel3.setBackground(Color.YELLOW);
		
		JLabel menuLabel = new JLabel("Menu:");
		menuLabel.setBounds(47, 13, 246, 37);
		menuLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel3.add(menuLabel);
		
		JLabel appetizersLabel = new JLabel("Appetizers");
		appetizersLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
	    appetizersLabel.setBounds(47, 53, 246, 37);
	    panel3.add(appetizersLabel);
	    
		JComboBox<String> appetizersComboBox = new JComboBox<String>();
		for(int i = 0; i < RestoAppController.getMenuItems(ItemCategory.Appetizer).size();i++) {
		  int rating = 0;
		  try {
			rating = RestoAppController.getOverall(RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i));
		  } catch (InvalidInputException e1) {
			  JOptionPane.showMessageDialog(null, "Calculate overall rating for the "+RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i).getName()+" didn't work properly.");
		  }
          String display4 = RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i).getName() + " $" + String.valueOf(RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i).getPricedMenuItem(0).getPrice() + "  Rating: "+ rating);
          appetizersComboBox.addItem(display4);
          }
		appetizersComboBox.setBounds(47, 83, 246, 37);
	    appetizersComboBox.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	          
	      }
	      
	    });
	    panel3.add(appetizersComboBox);
	    
	    
	    JLabel mainsLabel = new JLabel("Mains");
        mainsLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        mainsLabel.setBounds(47, 123, 246, 37);
        panel3.add(mainsLabel);
        
        JComboBox<String>mainsComboBox = new JComboBox<String>();
        for(int i = 0; i < RestoAppController.getMenuItems(ItemCategory.Main).size();i++) {
          int rating = 0;
  		  try {
  			rating = RestoAppController.getOverall(RestoAppController.getMenuItems(ItemCategory.Main).get(i));
  		  } catch (InvalidInputException e1) {
  			  JOptionPane.showMessageDialog(null, "Calculate overall rating for the "+RestoAppController.getMenuItems(ItemCategory.Main).get(i).getName()+" didn't work properly.");
  		  }
          String display4 = RestoAppController.getMenuItems(ItemCategory.Main).get(i).getName() + " $" + String.valueOf(RestoAppController.getMenuItems(ItemCategory.Main).get(i).getPricedMenuItem(0).getPrice() + "  Rating: "+ rating);
          mainsComboBox.addItem(display4);
          }
        mainsComboBox.setBounds(47, 153, 246, 37);
        mainsComboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            
          }
        });
        panel3.add(mainsComboBox);
        
        
        JLabel dessertsLabel = new JLabel("Desserts");
        dessertsLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        dessertsLabel.setBounds(47, 193, 246, 37);
        panel3.add(dessertsLabel);
        
        JComboBox<String>dessertsComboBox = new JComboBox<String>();
        for(int i = 0; i < RestoAppController.getMenuItems(ItemCategory.Dessert).size();i++) {
    	  int rating = 0;
		  try {
			rating = RestoAppController.getOverall(RestoAppController.getMenuItems(ItemCategory.Dessert).get(i));
		  } catch (InvalidInputException e1) {
			  JOptionPane.showMessageDialog(null, "Calculate overall rating for the "+RestoAppController.getMenuItems(ItemCategory.Dessert).get(i).getName()+" didn't work properly.");
		  }
          String display4 = RestoAppController.getMenuItems(ItemCategory.Dessert).get(i).getName() + " $" + String.valueOf(RestoAppController.getMenuItems(ItemCategory.Dessert).get(i).getPricedMenuItem(0).getPrice() + "  Rating: "+ rating);
          dessertsComboBox.addItem(display4);
          }
          dessertsComboBox.setBounds(47, 223, 246, 37);
          dessertsComboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
          }
        });
        panel3.add(dessertsComboBox);
	    
        
        JLabel alcBeveragesLabel = new JLabel("Alcoholic Beverages");
        alcBeveragesLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        alcBeveragesLabel.setBounds(400, 53, 246, 37);
        panel3.add(alcBeveragesLabel);
        
        JComboBox<String> alcBeveragesComboBox = new JComboBox<String>();
        for(int i = 0; i < RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).size();i++) {
          int rating = 0;
  		  try {
  			rating = RestoAppController.getOverall(RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).get(i));
  		  } catch (InvalidInputException e1) {
  			  JOptionPane.showMessageDialog(null, "Calculate overall rating for the "+RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).get(i).getName()+" didn't work properly.");
  		  }
          String display4 = RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).get(i).getName() + " $" + String.valueOf(RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).get(i).getPricedMenuItem(0).getPrice()+ "  Rating: "+ rating);
          alcBeveragesComboBox.addItem(display4);
          }
        alcBeveragesComboBox.setBounds(400, 83, 246, 37);
        alcBeveragesComboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
          }
        });
        panel3.add(alcBeveragesComboBox);
        
        
        JLabel nonAlcBeveragesLabel = new JLabel("Non-Alcoholic Beverages");
        nonAlcBeveragesLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        nonAlcBeveragesLabel.setBounds(400, 123, 246, 37);
        panel3.add(nonAlcBeveragesLabel);
        
		JComboBox<String> nonAlcBeveragesComboBox = new JComboBox<String>();
        for(int i = 0; i < RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).size();i++) {
    	  int rating = 0;
		  try {
			rating = RestoAppController.getOverall(RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).get(i));
		  } catch (InvalidInputException e1) {
			  JOptionPane.showMessageDialog(null, "Calculate overall rating for the "+RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).get(i).getName()+" didn't work properly.");
		  }
          String display5 = RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).get(i).getName() + " $" + String.valueOf(RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).get(i).getPricedMenuItem(0).getPrice()+ "  Rating: "+ rating);
          nonAlcBeveragesComboBox.addItem(display5);
          }
        nonAlcBeveragesComboBox.setBounds(400, 153, 246, 37);
        nonAlcBeveragesComboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
          }
        });
        panel3.add(nonAlcBeveragesComboBox);
		
        //add an item to an order part
        JButton btnAddAppetizer= new JButton("Add");
		btnAddAppetizer.setBounds(293, 83, 65, 37);
		panel3.add(btnAddAppetizer);
		
		btnAddAppetizer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				
				String name = (String) appetizersComboBox.getSelectedItem();
				for(int i=0; i<name.length();i++) {
					if(name.charAt(i)==' ') {
						if(name.charAt(i+1)=='$') {
							name = name.substring(0,i);
						}
					}
				}
				MenuItem appetizer = MenuItem.getWithName(name);
				
				new OrderPage(appetizer).setVisible(true);	
			}
		});
		
		//add main
		JButton btnAddMain = new JButton("Add");
		btnAddMain.setBounds(293, 153, 65, 37);
		panel3.add(btnAddMain);
		
		btnAddMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				
				String name = (String) mainsComboBox.getSelectedItem();
				for(int i=0; i<name.length();i++) {
					if(name.charAt(i)==' ') {
						if(name.charAt(i+1)=='$') {
							name = name.substring(0,i);
						}
					}
				}
				MenuItem main = MenuItem.getWithName(name);
				
				new OrderPage(main).setVisible(true);		
			}
		});
		
		//add dessert
		JButton btnAddDessert = new JButton("Add");
		btnAddDessert.setBounds(293, 223, 65, 37);
		panel3.add(btnAddDessert);
		
		btnAddDessert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				
				String name = (String) dessertsComboBox.getSelectedItem();
				for(int i=0; i<name.length();i++) {
					if(name.charAt(i)==' ') {
						if(name.charAt(i+1)=='$') {
							name = name.substring(0,i);
						}
					}
				}
				MenuItem dessert = MenuItem.getWithName(name);
				
				new OrderPage(dessert).setVisible(true);			
			}
		});
		
		//add alc
		JButton btnAddAlc = new JButton("Add");
		btnAddAlc.setBounds(646, 83, 65, 37);
		panel3.add(btnAddAlc);
		
		btnAddAlc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				
				String name = (String) alcBeveragesComboBox.getSelectedItem();
				for(int i=0; i<name.length();i++) {
					if(name.charAt(i)==' ') {
						if(name.charAt(i+1)=='$') {
							name = name.substring(0,i);
						}
					}
				}
				MenuItem alc = MenuItem.getWithName(name);
				
				new OrderPage(alc).setVisible(true);			
			}
		});
		
		//add nalc
		JButton btnAddNAlc = new JButton("Add");
		btnAddNAlc.setBounds(646, 153, 65, 37);
		panel3.add(btnAddNAlc);
		
		btnAddNAlc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				
				String name = (String) nonAlcBeveragesComboBox.getSelectedItem();
				for(int i=0; i<name.length();i++) {
					if(name.charAt(i)==' ') {
						if(name.charAt(i+1)=='$') {
							name = name.substring(0,i);
						}
					}
				}
				MenuItem nalc = MenuItem.getWithName(name);
				
				new OrderPage(nalc).setVisible(true);		
			}
		});
		
        //bottom right panel
		JLabel lblChangeTable = new JLabel("Change Table:");
		lblChangeTable.setBounds(47, 13, 246, 37);
		lblChangeTable.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel2.add(lblChangeTable);
		
		JLabel lblNewX = new JLabel("New X Coordinate :");
		lblNewX.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewX.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewX.setBounds(232, 74, 215, 25);
		panel2.add(lblNewX);
		
		JLabel lblNewY = new JLabel("New Y Coordinate :");
		lblNewY.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewY.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewY.setBounds(232, 112, 215, 25);
		panel2.add(lblNewY);
		
		JLabel lblNewTableNumber = new JLabel("New Table Number :");
		lblNewTableNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewTableNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewTableNumber.setBounds(232, 150, 215, 25);
		panel2.add(lblNewTableNumber);
		
		JLabel lblNewNumberOf = new JLabel("New Number Of Seats :");
		lblNewNumberOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewNumberOf.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewNumberOf.setBounds(232, 188, 215, 25);
		panel2.add(lblNewNumberOf);
		
		JLabel lblCurrentTable = new JLabel("Current Table :");
		lblCurrentTable.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentTable.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCurrentTable.setBounds(12, 78, 116, 22);
		panel2.add(lblCurrentTable);
		
		//description of ordering table
		JLabel lblBlue = new JLabel("BLACK");
		lblBlue.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBlue.setBounds(10, 120, 60, 15);
		lblBlue.setForeground(Color.BLACK);
		panel2.add(lblBlue);
		
		JLabel lblIsOrdering = new JLabel("=Table is available");
		lblIsOrdering.setHorizontalAlignment(SwingConstants.CENTER);
		lblIsOrdering.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIsOrdering.setBounds(60, 120, 160, 15);
		panel2.add(lblIsOrdering);
		
		//description of in use table
		JLabel lblRed = new JLabel("RED");
		lblRed.setHorizontalAlignment(SwingConstants.CENTER);
		lblRed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRed.setBounds(10, 140, 60, 15);
		lblRed.setForeground(Color.RED);
		panel2.add(lblRed);
		
		JLabel lblIsInUse = new JLabel("=Table is in use");
		lblIsInUse.setHorizontalAlignment(SwingConstants.CENTER);
		lblIsInUse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIsInUse.setBounds(60, 140, 160, 15);
		panel2.add(lblIsInUse);	
		
		//new X
		textField_6 = new JTextField();
		textField_6.setText("1");
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setColumns(10);
		textField_6.setBounds(469, 74, 116, 22);
		panel2.add(textField_6);
		
		//new Y
		textField_7 = new JTextField();
		textField_7.setText("2");
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setColumns(10);
		textField_7.setBounds(469, 112, 116, 22);
		panel2.add(textField_7);
		
		//New table number
		textField_8 = new JTextField();
		textField_8.setText("3");
		textField_8.setHorizontalAlignment(SwingConstants.CENTER);
		textField_8.setColumns(10);
		textField_8.setBounds(469, 150, 116, 22);
		panel2.add(textField_8);
		
		//New number of seats
		textField_9 = new JTextField();
		textField_9.setText("1");
		textField_9.setHorizontalAlignment(SwingConstants.CENTER);
		textField_9.setColumns(10);
		textField_9.setBounds(469, 188, 116, 22);
		panel2.add(textField_9);

		//combo box in which all the current tables are contained
		JComboBox<Integer> comboBox = new JComboBox<Integer>();
		comboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {}));
		comboBox.setMaximumRowCount(1000);
		comboBox.setBounds(142, 78, 78, 22);
		panel2.add(comboBox);
		
		final JList<Integer> list = new JList<Integer>(comboBox.getModel());//added <integer> rhs

		list.setBounds(350, 260, 50, 150);
		list.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel2.add(list);
		
		//Update menu button
		JButton btnUpdateMenu= new JButton("Update Menu");
        btnUpdateMenu.setBounds(400, 223, 246, 37);
        panel3.add(btnUpdateMenu);
        btnUpdateMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) { 
              
              new UpdateMenuPage().setVisible(true);  
              frame.setVisible(false);   
            }
        });
		
        //pops up the bill page
        JButton btnBill = new JButton("Bill");
		btnBill.setBounds(35, 175, 135, 40);
		panel2.add(btnBill);
		btnBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				new BillPage().setVisible(true);
			}
		});
        
		//cancel the order on a table button
		JButton btnCancelOrder = new JButton("Cancel Order");
		btnCancelOrder.setBounds(35, 365, 135, 40);
		panel2.add(btnCancelOrder);
		btnCancelOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				int tableNum = (int) comboBox.getSelectedItem();
				Table table = Table.getWithNumber(tableNum);
				RestoAppController.cancelOrder(table);
				
			}
		});
		
		//this button will assign a table and its seats
		JButton btnAssign= new JButton("Assign tables");
		btnAssign.setBounds(469, 296, 135, 40);
		panel2.add(btnAssign);
		btnAssign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				List<Integer> testTables = list.getSelectedValuesList();
		        List<Table> myTables = new ArrayList<Table>();
		        for (int i = 0; i < testTables.size(); i++) {
		        	myTables.add(Table.getWithNumber(testTables.get(i)));
		        }
				try {
					RestoAppController.startOrder(myTables);
					for(Table myTable: myTables) {
						changeColor(myTable.getNumber());
					}
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		//this button pop up reservation page
		JButton btnReservation = new JButton("Reservation");
		btnReservation.setBounds(469, 356, 135, 40);
		panel2.add(btnReservation);
		btnReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				new ReservationPage().setVisible(true);
			}
		});
		
		//this button pops up the view order page
		JButton buttonViewOrders = new JButton("View Orders");
		buttonViewOrders.setBounds(400,350,135,40);
		panel3.add(buttonViewOrders);
		buttonViewOrders.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				new ViewOrderPage().setVisible(true);
			}
		});
		
		JButton buttonDisplayOrder = new JButton("Display in order");
		buttonDisplayOrder.setBounds(200,350,135,40);
		panel3.add(buttonDisplayOrder);
		buttonDisplayOrder.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				appetizersComboBox.removeAllItems();
				mainsComboBox.removeAllItems();
				alcBeveragesComboBox.removeAllItems();
				nonAlcBeveragesComboBox.removeAllItems();
				dessertsComboBox.removeAllItems();
				
				//Non alcohol beverage part
				for (int j = 5; j>=0; j--) {
					for (int i = 0; i < RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).size(); i++) {
						int rating = 0;
						try {
							rating = RestoAppController.getOverall(
									RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).get(i));
						} catch (InvalidInputException e1) {
							JOptionPane.showMessageDialog(null,
									"Calculate overall rating for the " + RestoAppController
											.getMenuItems(ItemCategory.NonAlcoholicBeverage).get(i).getName()
											+ " didn't work properly.");
						}
						if(rating==j) {
							String display5 = RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage).get(i)
									.getName() + " $"
									+ String.valueOf(RestoAppController.getMenuItems(ItemCategory.NonAlcoholicBeverage)
											.get(i).getPricedMenuItem(0).getPrice() + "  Rating: " + rating);
							nonAlcBeveragesComboBox.addItem(display5);
						}
					} 
				}
				
				//Alcohol beverage part
				for (int j = 5; j >=0; j--) {
					for (int i = 0; i < RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).size(); i++) {
						int rating = 0;
						try {
							rating = RestoAppController
									.getOverall(RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).get(i));
						} catch (InvalidInputException e1) {
							JOptionPane.showMessageDialog(null,
									"Calculate overall rating for the " + RestoAppController
											.getMenuItems(ItemCategory.AlcoholicBeverage).get(i).getName()
											+ " didn't work properly.");
						}
						if(j==rating) {
							String display4 = RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).get(i)
									.getName() + " $"
									+ String.valueOf(RestoAppController.getMenuItems(ItemCategory.AlcoholicBeverage).get(i)
											.getPricedMenuItem(0).getPrice() + "  Rating: " + rating);
							alcBeveragesComboBox.addItem(display4);
						}	
					} 
				}
				
				//Dessert part
				for (int j = 5; j >=0; j--) {
					for (int i = 0; i < RestoAppController.getMenuItems(ItemCategory.Dessert).size(); i++) {
						int rating = 0;
						try {
							rating = RestoAppController
									.getOverall(RestoAppController.getMenuItems(ItemCategory.Dessert).get(i));
						} catch (InvalidInputException e1) {
							JOptionPane.showMessageDialog(null,
									"Calculate overall rating for the "
											+ RestoAppController.getMenuItems(ItemCategory.Dessert).get(i).getName()
											+ " didn't work properly.");
						}
						if(j==rating) {
							String display4 = RestoAppController.getMenuItems(ItemCategory.Dessert).get(i).getName() + " $"
									+ String.valueOf(RestoAppController.getMenuItems(ItemCategory.Dessert).get(i)
											.getPricedMenuItem(0).getPrice() + "  Rating: " + rating);
							dessertsComboBox.addItem(display4);
						}	
					} 
				}
				
				//Main part
				for (int j = 5; j >=0; j--) {
					for (int i = 0; i < RestoAppController.getMenuItems(ItemCategory.Main).size(); i++) {
						int rating = 0;
						try {
							rating = RestoAppController
									.getOverall(RestoAppController.getMenuItems(ItemCategory.Main).get(i));
						} catch (InvalidInputException e1) {
							JOptionPane.showMessageDialog(null,
									"Calculate overall rating for the "
											+ RestoAppController.getMenuItems(ItemCategory.Main).get(i).getName()
											+ " didn't work properly.");
						}
						if(j==rating) {
							String display4 = RestoAppController.getMenuItems(ItemCategory.Main).get(i).getName() + " $"
									+ String.valueOf(RestoAppController.getMenuItems(ItemCategory.Main).get(i)
											.getPricedMenuItem(0).getPrice() + "  Rating: " + rating);
							mainsComboBox.addItem(display4);
						}	
					} 
				}
				
				//Appetizer part
				for (int j = 5; j >=0; j--) {
					for (int i = 0; i < RestoAppController.getMenuItems(ItemCategory.Appetizer).size(); i++) {
						int rating = 0;
						try {
							rating = RestoAppController
									.getOverall(RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i));
						} catch (InvalidInputException e1) {
							JOptionPane.showMessageDialog(null,
									"Calculate overall rating for the "
											+ RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i).getName()
											+ " didn't work properly.");
						}
						if(j==rating) {
							String display4 = RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i).getName()
									+ " $" + String.valueOf(RestoAppController.getMenuItems(ItemCategory.Appetizer).get(i)
											.getPricedMenuItem(0).getPrice() + "  Rating: " + rating);
							appetizersComboBox.addItem(display4);
						}	
					} 
				}
				revalidate();
			}
		});
		
		//button to end an order
		JButton btnEndOrder = new JButton("End Table Order");
		btnEndOrder.setBounds(35, 300, 135, 37);
		panel2.add(btnEndOrder);
		btnEndOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				Integer selectedTableNumber = (Integer) comboBox.getSelectedItem();
				rectangleId.get(selectedTableNumber).setBackground(Color.BLACK);
				rectangleId.get(selectedTableNumber).setOpaque(true);
				JOptionPane.showMessageDialog(null, "The order was ended successfuly");
				//lastSelectedTableNumber = selectedTableNumber;
				List<Order> orders = Table.getWithNumber(selectedTableNumber).getOrders();
				Order currentOrder;
				if(orders.size()!=0) {
					currentOrder = Table.getWithNumber(selectedTableNumber).getOrder(orders.size()-1);
				}
				else {
					currentOrder = null;
				}
				new RatingPage(currentOrder).setVisible(true);
				try {
					RestoAppController.endOrder(currentOrder);
				} catch (InvalidInputException e) {
					JOptionPane.showMessageDialog(null, "End order didn't work properly");
				}
				RestoApp restoApp = RestoAppApplication.getRestoApp();
				List<Table> currentTables =  restoApp.getCurrentTables();
				for(Table myTable: currentTables) {
					//changeColor(myTable.getNumber());
				}
			}
		});
		
		
		//this button remove the selected table from the restoApp and UI
		JButton btnRemoveTable = new JButton("Remove Table");
		btnRemoveTable.setBounds(35, 235, 135, 37);
		panel2.add(btnRemoveTable);
		btnRemoveTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Integer selectedTableNumber = (Integer) comboBox.getSelectedItem();
				try {
					RestoAppController.removeTable(Table.getWithNumber(selectedTableNumber));
					comboBox.removeItem(selectedTableNumber);
					comboBox.setSelectedIndex(-1);
					LayoutVisualizer component = rectangles.get(selectedTableNumber);
					List<LayoutVisualizer> componentSeat = seats.get(selectedTableNumber);
					for(int i=0;i<Table.getWithNumber(selectedTableNumber).numberOfCurrentSeats();i++) {
						LayoutVisualizer specificSeat = componentSeat.get(i);
						panel1.remove(specificSeat);
					}
					panel1.remove(component);
					panel1.repaint();
					rectangles.remove(selectedTableNumber);
					seats.remove(selectedTableNumber);
					panel1.remove(rectangleId.get(selectedTableNumber));
					panel1.revalidate();
					panel1.repaint();
					rectangleId.remove(selectedTableNumber);
				}
				catch(InvalidInputException e){
				}
			}
		});
		
		//this button confirm all new changes did on the selected table(coordinate, number, number of seats)
		JButton btnConfirmChanges = new JButton("Confirm Changes");
		btnConfirmChanges.setBounds(469, 220, 135, 37);
		panel2.add(btnConfirmChanges);
		btnConfirmChanges.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int Num = (Integer) comboBox.getSelectedItem();			
				int newNumber =  -1;
				int numberOfSeats = -1;
				
				/*------------------------------------------------------------------*/
				RestoApp restoApp = RestoAppApplication.getRestoApp();
				List<Order> currentOrders = r.getCurrentOrders();
				for(int i = 0; i < currentOrders.size(); i++)
				{
					List<Table> tables = currentOrders.get(i).getTables();
					
					Boolean inUse = tables.contains(Table.getWithNumber(Num));
					
					if(inUse == true)
					{
						try {
							throw new InvalidInputException("This table number is already in use");
						} catch (InvalidInputException e) {
							JOptionPane.showMessageDialog(null, "The table with number "+Num+" is already in use.");
							return;
						}
					}
				}
				
				Boolean reserved = Table.getWithNumber(Num).hasReservations();
				if(reserved ==true)
				{
					try {
						throw new InvalidInputException("This table has already been reserved.");
					} catch (InvalidInputException e) {
						JOptionPane.showMessageDialog(null, "The table with number "+Num+" is already reserved.");
						return;
					}
				}
				
				/*------------------------------------------------------------------------*/
				
				try {
					newNumber = Integer.parseInt(textField_8.getText());
					numberOfSeats = Integer.parseInt(textField_9.getText());
					if(newNumber<=0||numberOfSeats<=0) {
						throw new InvalidInputException("Invalid input");
					}
				}
				catch(InvalidInputException invalidInputException){
					JOptionPane.showMessageDialog(null, "The number of the table and the number of seats must be positive integers. Change the inputs.");
					return;
				}
				catch(NumberFormatException numberFormatException) {
					JOptionPane.showMessageDialog(null, "The number of the table and the number of seats must be positive integers. Change the inputs.");
					return;
				}
				
				int curX = -1;
				int curY = -1;
				try 
				{
					curX = Integer.parseInt(textField_6.getText());
					curY = Integer.parseInt(textField_7.getText());
					
					if (curX < 15 || curY < 15) 
					{
						throw new InvalidInputException("The new coordinates are invalid. Select an integer bigger than 15.");
					}
				} 
				catch (InvalidInputException e1) 
				{
					JOptionPane.showMessageDialog(null, "The new coordinates are invalid. Select an integer bigger than 15.");
					return;
				}
				catch (NumberFormatException e2)
				{
					JOptionPane.showMessageDialog(null, "The new coordinates are invalid. Select an integer bigger than 15.");
					return;
				}
				//-----------------------------------------------------------------
				List<LayoutVisualizer> componentSeat = seats.get(Num);
				for(int i=0;i<Table.getWithNumber(Num).numberOfCurrentSeats();i++) {
					LayoutVisualizer specificSeat = componentSeat.get(i);
					panel1.remove(specificSeat);
				}
				seats.remove(Num);
				//------------------------------------------------------------------
				int numberOfTable = restoApp.numberOfTables();
				Table goodTable = null;
				
				for(int i = 0; i < numberOfTable; i++)
				{
					Table curTable = restoApp.getTable(i);
					
					if(curTable.getNumber() == Num)
					{
						goodTable = curTable;
					}
				}
				
				int xCoordinate;
				int yCoordinate;
				if(curX == goodTable.getX()){
					if(curY==goodTable.getY()){
						xCoordinate = goodTable.getX();
						yCoordinate = goodTable.getY();
					}
					else {
						xCoordinate = goodTable.getX();
						yCoordinate = curY;
					}											
				}
				else {
					if(curY==goodTable.getY()){
						xCoordinate = curX;
						yCoordinate = goodTable.getY();
					}
					else {
						xCoordinate = curX;
						yCoordinate = curY;
					}
				}				
				if(newNumber==goodTable.getNumber()) {

				}
				else {
					if((Table.getWithNumber(Num).getStatusFullName().equals("Available"))==false){

					}
					else {
						seats.remove(goodTable.getNumber());
						rectangleId.get(goodTable.getNumber()).setText(""+ newNumber);
						rectangleId.put(newNumber, rectangleId.get(goodTable.getNumber()));
						rectangleId.remove(goodTable.getNumber());
					}
				}
				try 
				{
					RestoAppController.moveTable(goodTable, curX, curY);				
				} catch (InvalidInputException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				try {
					RestoAppController.updateTable(goodTable, newNumber, numberOfSeats);
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				comboBox.removeItem(Num);
				comboBox.setSelectedIndex(-1);
				comboBox.addItem(newNumber);
			
				//LayoutVisualizer component = new LayoutVisualizer(W,L);
				LayoutVisualizer component = rectangles.get(Num);	
				rectangleId.get(goodTable.getNumber()).setLocation(goodTable.getX(),goodTable.getY());
	            component.setLocation(goodTable.getX(),goodTable.getY());	
	            panel1.revalidate();
	            panel1.repaint();
	            
	            List<LayoutVisualizer> seatsss =new ArrayList<LayoutVisualizer>();
	               int W = goodTable.getWidth();
	               int L = goodTable.getLength();
	               int xCoord = xCoordinate;//goodTable.getX();
	               int yCoord = yCoordinate;//goodTable.getY();
	               
	               int availableUpWidth = W;
	               int availableDownWidth = W;
	               int availableLeftLength = L;
	               int availableRightLength = L;
	               
	               //upper row of seats
	               int nextXCoordSpot1 = xCoord;
	               int nextYCoordSpot1 = yCoord -13;
	               
	               //right column of seats
	               int nextXCoordSpot2 = xCoord+W+1;
	               int nextYCoordSpot2 = yCoord;
	               
	               //bottom row of seats
	               int nextXCoordSpot3 = xCoord;
	               int nextYCoordSpot3 = yCoord+1+L;
	               
	               //Left column of seats
	               int nextXCoordSpot4 = xCoord-13;
	               int nextYCoordSpot4 = yCoord;
	               
	               for(int i=0;i<numberOfSeats;i++) {
	            	   LayoutVisualizer componentSeat7 = new LayoutVisualizer(12,12);
	            	   
	            	   if(availableUpWidth>=12) {
	            		   componentSeat7.setLocation(nextXCoordSpot1,nextYCoordSpot1); 
	            		   componentSeat7.setSize(componentSeat7.getPreferredSize());
	            		   panel1.add(componentSeat7);
	                	   panel1.repaint();
	                	   seatsss.add(componentSeat7);
	            		   nextXCoordSpot1 = nextXCoordSpot1+13;
	            		   availableUpWidth=availableUpWidth-13;
	            		   continue;
	            	   }
	            	   
	            	   if(availableRightLength>=12 && availableUpWidth<12){
	            		   componentSeat7.setLocation(nextXCoordSpot2,nextYCoordSpot2);
	            		   componentSeat7.setSize(componentSeat7.getPreferredSize());
	            		   panel1.add(componentSeat7);
	                	   panel1.repaint();
	                	   seatsss.add(componentSeat7);
	            		   nextYCoordSpot2 = nextYCoordSpot2+13;
	            		   availableRightLength = availableRightLength-13;
	            		   continue;
	            	   }
	            	   
	            	   
	            	   if(availableDownWidth>=12 && availableRightLength<12 && availableUpWidth<12){
	            		   componentSeat7.setLocation(nextXCoordSpot3,nextYCoordSpot3);
	            		   componentSeat7.setSize(componentSeat7.getPreferredSize());
	            		   panel1.add(componentSeat7);
	                	   panel1.repaint();
	                	   seatsss.add(componentSeat7);
	            		   nextXCoordSpot3 = nextXCoordSpot3+13;
	            		   availableDownWidth = availableDownWidth-13;
	            		   continue;
	            	   }
	            	   
	            	   if(availableLeftLength>=12 && availableDownWidth<12 && availableRightLength<12 && availableUpWidth<12){
	            		   componentSeat7.setLocation(nextXCoordSpot4,nextYCoordSpot4);
	            		   componentSeat7.setSize(componentSeat7.getPreferredSize());
	            		   panel1.add(componentSeat7);
	                	   panel1.repaint();
	                	   seatsss.add(componentSeat7);
	            		   nextYCoordSpot4 = nextYCoordSpot4+13;
	            		   availableLeftLength = availableLeftLength-13;
	            		   continue;
	            	   } 
	            	   if(availableLeftLength<12) {
	            		   JOptionPane.showMessageDialog(null, "There are too many seats assigned to this table. Change the inputs.");
	            		   break;
	            	   }
	            	 
	               }
	               seats.put(newNumber, seatsss);
	        
	        	//rectangles.remove(Num);
				rectangles.put(newNumber, component);
				}
			
		});
		
		//upper right corner panel of the UI, used to display the new table options
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(700, 0, 700, 425);
		panel.setBackground(Color.YELLOW);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NewTable:");
		lblNewLabel.setBounds(46, 50, 161, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblNewLabel);
		
		//table number of the new table label
		JLabel lblTableNumber = new JLabel("Table Number:");
		lblTableNumber.setBounds(2, 136, 250, 25);
		lblTableNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTableNumber.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTableNumber);
		
		//decrease the table number of the new table button
		JButton button_6 = new JButton("-");
		button_6.setBounds(235, 136, 50, 25);
		button_6.setBackground(Color.RED);
		panel.add(button_6);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = Integer.parseInt(textField_5.getText());
				i = i-1;
				if(i==1) {
					button_6.setVisible(false);
				}
				String newText = Integer.toString(i);
				textField_5.setText(newText);	
			}
		});
		
		//table number of the new table textField
		textField_5 = new JTextField();
		textField_5.setBounds(289, 137, 116, 22);
		textField_5.setHorizontalAlignment(SwingConstants.CENTER);
		textField_5.setText("2");
		textField_5.setColumns(10);
		textField_5.setEditable(false);
		panel.add(textField_5);
		
		//increase the table number of the new table button
		JButton button_10 = new JButton("+");
		button_10.setBounds(410, 136, 50, 25);
		button_10.setBackground(Color.GREEN);
		panel.add(button_10);
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_6.setVisible(true);
				int i = Integer.parseInt(textField_5.getText());
				i = i+1;
				String newText = Integer.toString(i);
				textField_5.setText(newText);
			}
		});
		
		//number of seats of the new table label
		JLabel lblNumberOfSeats = new JLabel("Number of seats:");
		lblNumberOfSeats.setBounds(2, 168, 250, 25);
		lblNumberOfSeats.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNumberOfSeats.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNumberOfSeats);
		
		//decrease the number of seats of the new table button
		JButton button_5 = new JButton("-");
		button_5.setBounds(235, 168, 50, 25);
		button_5.setBackground(Color.RED);
		panel.add(button_5);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = Integer.parseInt(textField_4.getText());
				i = i-1;
				if(i==1) {
					button_5.setVisible(false);
				}
				String newText = Integer.toString(i);
				textField_4.setText(newText);	
			}
		});
		
		//number of seats of the new table textField
		textField_4 = new JTextField();
		textField_4.setBounds(289, 169, 116, 22);
		textField_4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_4.setText("2");
		textField_4.setColumns(10);
		textField_4.setEditable(false);
		panel.add(textField_4);
		
		//increase the number of seat of the new table button
		JButton button_9 = new JButton("+");
		button_9.setBounds(410, 168, 50, 25);
		button_9.setBackground(Color.GREEN);
		panel.add(button_9);
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_5.setVisible(true);
				int i = Integer.parseInt(textField_4.getText());
				i = i+1;
				String newText = Integer.toString(i);
				textField_4.setText(newText);
			}
		});
		
		//X coordinate of the new table label
		JLabel lblXComponent = new JLabel("X component:");
		lblXComponent.setBounds(2, 200, 250, 25);
		lblXComponent.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblXComponent.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblXComponent);
		
		//decrease the X coordinate of the new table button
		JButton button_4 = new JButton("-");
		button_4.setBounds(235, 200, 50, 25);
		button_4.setBackground(Color.RED);
		panel.add(button_4);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = Integer.parseInt(textField_3.getText());
				i = i-10;
				if(i<=20) {
					button_4.setVisible(false);
				}
				String newText = Integer.toString(i);
				textField_3.setText(newText);
			}
		});
		
		//X coordinate of the new table textField
		textField_3 = new JTextField();
		textField_3.setBounds(289, 201, 116, 22);
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setText("100");
		textField_3.setColumns(10);
		textField_3.setEditable(false);
		panel.add(textField_3);
		
		//increase X coordinate of the new table button
		JButton button_8 = new JButton("+");
		button_8.setBounds(410, 200, 50, 25);
		button_8.setBackground(Color.GREEN);
		panel.add(button_8);
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_4.setVisible(true);
				int i = Integer.parseInt(textField_3.getText());
				i = i+10;
				String newText = Integer.toString(i);
				textField_3.setText(newText);
			}
		});
		
		//Y coordinate of the new table label
		JLabel lblYComponent = new JLabel("Y component:");
		lblYComponent.setBounds(2, 232, 250, 25);
		lblYComponent.setHorizontalAlignment(SwingConstants.CENTER);
		lblYComponent.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblYComponent);
		
		//decrease Y coordinate of the new table button
		JButton button_3 = new JButton("-");
		button_3.setBounds(235, 232, 50, 25);
		button_3.setBackground(Color.RED);
		panel.add(button_3);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = Integer.parseInt(textField_2.getText());
				i = i-10;
				if(i<=20) {
					button_3.setVisible(false);
				}
				String newText = Integer.toString(i);
				textField_2.setText(newText);	
			}
		});
		
		//Y coordinate of the new table textField
		textField_2 = new JTextField();
		textField_2.setBounds(289, 233, 116, 22);
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setText("100");
		textField_2.setColumns(10);
		textField_2.setEditable(false);
		panel.add(textField_2);
		
		//increase the Y coordinate of the new table button
		JButton button_7 = new JButton("+");
		button_7.setBounds(410, 232, 50, 25);
		button_7.setBackground(Color.GREEN);
		panel.add(button_7);
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_3.setVisible(true);
				int i = Integer.parseInt(textField_2.getText());
				i = i+10;
				String newText = Integer.toString(i);
				textField_2.setText(newText);
			}
		});
		
		//table width label
		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setBounds(2, 264, 250, 25);
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblWidth);
		
		//decrease the table width button
		JButton button_2 = new JButton("-");
		button_2.setBounds(235, 264, 50, 25);
		button_2.setBackground(Color.RED);
		panel.add(button_2);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = Integer.parseInt(textField_1.getText());
				i = i-5;
				if(i<=25) {
					button_2.setVisible(false);
				}
				String newText = Integer.toString(i);
				textField_1.setText(newText);					
			}
		});
		
		//table width textField
		textField_1 = new JTextField();
		textField_1.setBounds(289, 265, 116, 22);
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("30");
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		panel.add(textField_1);
		
		//increase the table width button
		JButton button = new JButton("+");
		button.setBounds(410, 264, 50, 25);
		button.setBackground(Color.GREEN);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_2.setVisible(true);
				int i = Integer.parseInt(textField_1.getText());
				i = i+5;
				String newText = Integer.toString(i);
				textField_1.setText(newText);
			}
		});
		
		//table length label
		JLabel lblLength = new JLabel("Length:");
		lblLength.setBounds(2, 296, 250, 25);
		lblLength.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLength.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblLength);
		
		//decrease the table length button
		JButton button_1 = new JButton("-");
		button_1.setBounds(235, 296, 50, 25);
		button_1.setBackground(Color.RED);
		panel.add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = Integer.parseInt(textField.getText());
				i = i-5;
				if(i<=25) {
					button_1.setVisible(false);
				}
				String newText = Integer.toString(i);
				textField.setText(newText);					
			}
		});
		
		//table length textField
		textField = new JTextField();
		textField.setBounds(289, 297, 116, 22);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("50");
		textField.setEditable(false);
		textField.setColumns(10);
		panel.add(textField);
		
		//increase the table length
		JButton btnNewButton = new JButton("+");
		btnNewButton.setBounds(410, 296, 50, 25);
		btnNewButton.setBackground(Color.GREEN);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				button_1.setVisible(true);
				int i = Integer.parseInt(textField.getText());
				i = i+5;
				String newText = Integer.toString(i);
				textField.setText(newText);
			}
		});
		
		//this button will create the table with its features
		JButton btnCreateTheTable = new JButton("Create the table");
		btnCreateTheTable.setBounds(484, 359, 177, 33);
		btnCreateTheTable.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCreateTheTable.addActionListener( new ActionListener(){
           public void actionPerformed(ActionEvent event)
           {
        	   //get all necessary integer from the UI
        	   int number = Integer.parseInt(textField_5.getText());
               int xCoord = Integer.parseInt(textField_3.getText());
               int yCoord = Integer.parseInt(textField_2.getText());
               int W = Integer.parseInt(textField_1.getText());
               int L = Integer.parseInt(textField.getText());
               int nbOfSeats = Integer.parseInt(textField_4.getText());
               
               //creates the new table in the restoApp
               try {
   				RestoAppController.createTable(number, xCoord, yCoord, W, L, nbOfSeats);
		   			} catch (InvalidInputException e) {
		   				// TODO Auto-generated catch block
		   				e.printStackTrace();
		   			}
               JLabel idLabel = new JLabel(""+number, SwingConstants.CENTER);
               rectangleId.put(number, idLabel);
               idLabel.setBounds(xCoord, yCoord, W, L);
               idLabel.setForeground(Color.white);
               panel1.add(idLabel);
                             
               //create the component on the visualizer
               LayoutVisualizer component = new LayoutVisualizer(W,L);
               component.setLocation(xCoord,yCoord);
               component.setSize(component.getPreferredSize());
               rectangles.put(number, component);
               panel1.add(component);
               panel1.repaint();
               
               List<LayoutVisualizer> seatss =new ArrayList<LayoutVisualizer>();
               
               
               int availableUpWidth = W;
               int availableDownWidth = W;
               int availableLeftLength = L;
               int availableRightLength = L;
               
               //upper row of seats
               int nextXCoordSpot1 = xCoord;
               int nextYCoordSpot1 = yCoord -13;
               
               //right column of seats
               int nextXCoordSpot2 = xCoord+W+1;
               int nextYCoordSpot2 = yCoord;
               
               //bottom row of seats
               int nextXCoordSpot3 = xCoord;
               int nextYCoordSpot3 = yCoord+1+L;
               
               //Left column of seats
               int nextXCoordSpot4 = xCoord-13;
               int nextYCoordSpot4 = yCoord;
               
               for(int i=0;i<nbOfSeats;i++) {
            	   LayoutVisualizer componentSeat = new LayoutVisualizer(12,12);
            	   
            	   if(availableUpWidth>=12) {
            		   componentSeat.setLocation(nextXCoordSpot1,nextYCoordSpot1); 
            		   componentSeat.setSize(componentSeat.getPreferredSize());
            		   panel1.add(componentSeat);
                	   panel1.repaint();
                	   seatss.add(componentSeat);
            		   nextXCoordSpot1 = nextXCoordSpot1+13;
            		   availableUpWidth=availableUpWidth-13;
            		   continue;
            	   }
            	   
            	   if(availableRightLength>=12 && availableUpWidth<12){
            		   componentSeat.setLocation(nextXCoordSpot2,nextYCoordSpot2);
            		   componentSeat.setSize(componentSeat.getPreferredSize());
            		   panel1.add(componentSeat);
                	   panel1.repaint();
                	   seatss.add(componentSeat);
            		   nextYCoordSpot2 = nextYCoordSpot2+13;
            		   availableRightLength = availableRightLength-13;
            		   continue;
            	   }
            	   
            	   
            	   if(availableDownWidth>=12 && availableRightLength<12 && availableUpWidth<12){
            		   componentSeat.setLocation(nextXCoordSpot3,nextYCoordSpot3);
            		   componentSeat.setSize(componentSeat.getPreferredSize());
            		   panel1.add(componentSeat);
                	   panel1.repaint();
                	   seatss.add(componentSeat);
            		   nextXCoordSpot3 = nextXCoordSpot3+13;
            		   availableDownWidth = availableDownWidth-13;
            		   continue;
            	   }
            	   
            	   if(availableLeftLength>=12 && availableDownWidth<12 && availableRightLength<12 && availableUpWidth<12){
            		   componentSeat.setLocation(nextXCoordSpot4,nextYCoordSpot4);
            		   componentSeat.setSize(componentSeat.getPreferredSize());
            		   panel1.add(componentSeat);
                	   panel1.repaint();
                	   seatss.add(componentSeat);
            		   nextYCoordSpot4 = nextYCoordSpot4+13;
            		   availableLeftLength = availableLeftLength-13;
            		   continue;
            	   } 
            	   if(availableLeftLength<12) {
            		   JOptionPane.showMessageDialog(null, "There are too many seats assigned to this table. Change the inputs.");
            		   break;
            	   }
            	 
               }
               seats.put(number, seatss);
               
               
               //add the new table to the dropBox
               DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>)comboBox.getModel();
               model.addElement(number);
          
           }
        });
		panel.add(btnCreateTheTable);
		
		//Refresh method
		if(tables != null)
		{
			LayoutVisualizer layoutVisualizer;
			for(Table table : tables) {
				JLabel idLabel = new JLabel(""+table.getNumber(), SwingConstants.CENTER);
				idLabel.setBounds(table.getX(),table.getY(),table.getWidth(),table.getLength());
				idLabel.setForeground(Color.white);
				rectangleId.put(table.getNumber(), idLabel);
				panel1.add(idLabel);
				layoutVisualizer = new LayoutVisualizer(table.getWidth(),table.getLength());
				layoutVisualizer.setLocation(table.getX(), table.getY());
				layoutVisualizer.setSize(layoutVisualizer.getPreferredSize());
				rectangles.put(table.getNumber(),layoutVisualizer);
				panel1.add(layoutVisualizer);
				panel1.repaint();
				DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>)comboBox.getModel();
				model.addElement(table.getNumber());
				List<LayoutVisualizer> seatss = new ArrayList<LayoutVisualizer>();
				changeColor(table.getNumber());
				panel1.repaint();
	            
				
				int W = table.getWidth();
				int L = table.getLength();
				int xCoord = table.getX();
				int yCoord = table.getY();
				
	               int availableUpWidth = W;
	               int availableDownWidth = W;
	               int availableLeftLength = L;
	               int availableRightLength = L;
	               
	               //upper row of seats
	               int nextXCoordSpot1 = xCoord;
	               int nextYCoordSpot1 = yCoord -13;
	               
	               //right column of seats
	               int nextXCoordSpot2 = xCoord+W+1;
	               int nextYCoordSpot2 = yCoord;
	               
	               //bottom row of seats
	               int nextXCoordSpot3 = xCoord;
	               int nextYCoordSpot3 = yCoord+1+L;
	               
	               //Left column of seats
	               int nextXCoordSpot4 = xCoord-13;
	               int nextYCoordSpot4 = yCoord;
	               
	               for(int i=0;i<table.numberOfCurrentSeats();i++) {
	            	   LayoutVisualizer componentSeat = new LayoutVisualizer(12,12);
	            	   
	            	   if(availableUpWidth>=12) {
	            		   componentSeat.setLocation(nextXCoordSpot1,nextYCoordSpot1); 
	            		   componentSeat.setSize(componentSeat.getPreferredSize());
	            		   panel1.add(componentSeat);
	                	   panel1.repaint();
	                	   seatss.add(componentSeat);
	            		   nextXCoordSpot1 = nextXCoordSpot1+13;
	            		   availableUpWidth=availableUpWidth-13;
	            		   continue;
	            	   }
	            	   
	            	   if(availableRightLength>=12 && availableUpWidth<12){
	            		   componentSeat.setLocation(nextXCoordSpot2,nextYCoordSpot2);
	            		   componentSeat.setSize(componentSeat.getPreferredSize());
	            		   panel1.add(componentSeat);
	                	   panel1.repaint();
	                	   seatss.add(componentSeat);
	            		   nextYCoordSpot2 = nextYCoordSpot2+13;
	            		   availableRightLength = availableRightLength-13;
	            		   continue;
	            	   }
	            	   
	            	   
	            	   if(availableDownWidth>=12 && availableRightLength<12 && availableUpWidth<12){
	            		   componentSeat.setLocation(nextXCoordSpot3,nextYCoordSpot3);
	            		   componentSeat.setSize(componentSeat.getPreferredSize());
	            		   panel1.add(componentSeat);
	                	   panel1.repaint();
	                	   seatss.add(componentSeat);
	            		   nextXCoordSpot3 = nextXCoordSpot3+13;
	            		   availableDownWidth = availableDownWidth-13;
	            		   continue;
	            	   }
	            	   
	            	   if(availableLeftLength>=12 && availableDownWidth<12 && availableRightLength<12 && availableUpWidth<12){
	            		   componentSeat.setLocation(nextXCoordSpot4,nextYCoordSpot4);
	            		   componentSeat.setSize(componentSeat.getPreferredSize());
	            		   panel1.add(componentSeat);
	                	   panel1.repaint();
	                	   seatss.add(componentSeat);
	            		   nextYCoordSpot4 = nextYCoordSpot4+13;
	            		   availableLeftLength = availableLeftLength-13;
	            		   continue;
	            	   } 
	            	   if(availableLeftLength<12) {
	            		   JOptionPane.showMessageDialog(null, "There are too many seats assigned to this table. Change the inputs.");
	            		   break;
	            	   }
	            	 
	               }
	               seats.put(table.getNumber(), seatss);
			}
		}
	}
}
