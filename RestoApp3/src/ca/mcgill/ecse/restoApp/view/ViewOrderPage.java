package ca.mcgill.ecse.restoApp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse.restoApp.application.RestoAppApplication;
import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class ViewOrderPage extends JFrame {
	private static final long serialVersionUID = 2L;
	
	private JPanel contentPane;
	
	RestoApp restoApp = RestoAppApplication.getRestoApp();

	
	public ViewOrderPage()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setBounds(100, 100, 300, 400);
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    
	    JLabel ordersForTheCostumersOfATable = new JLabel("Orders for the costumers of a table"); 
	    ordersForTheCostumersOfATable.setBounds(20,20,200,30);
	    contentPane.add(ordersForTheCostumersOfATable);
	    
	    JLabel selectATable = new JLabel("Select a table number:");
	    selectATable.setBounds(20,40,150,30);
	    contentPane.add(selectATable);
	    
	    //See line 318 in RestoAppPage
		JComboBox<Integer> tableComboBox = new JComboBox<Integer>();
		tableComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {}));
		tableComboBox.setMaximumRowCount(1000);
		tableComboBox.setBounds(162, 50, 78, 22);
		contentPane.add(tableComboBox);
		
	    RestoApp restoApp = RestoAppApplication.getRestoApp();
	    List<Table> tables = restoApp.getCurrentTables();
	    for (Table table : tables) {
	      Integer tableNumber = table.getNumber();
	      tableComboBox.addItem(tableNumber);
	    }
		
	  
	    
		JButton viewOrdersButton = new JButton("View Orders");
		viewOrdersButton.setBounds(20, 70, 115, 29);
	    contentPane.add(viewOrdersButton);
	    
	    JList<String> Orders = new JList<String>();
		Orders.setBounds(20, 130, 100, 200);
		contentPane.add(Orders);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		
	    viewOrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			//change here
			model.clear();
			Orders.setModel(model);
			
			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			int aTablenum = (int) tableComboBox.getSelectedItem();
			Table aTable = Table.getWithNumber(aTablenum);
			
			 try {
				orderItems = RestoAppController.getOrderItems(aTable);
				
			} catch (InvalidInputException e) {
				JOptionPane.showMessageDialog(null, "Error occuried");
			}
			
			for(int i = 0; i < orderItems.size(); i++)
			{
				model.addElement(orderItems.get(i).getPricedMenuItem().getMenuItem().getName());
				
			}
			 
			Orders.setModel(model);
				
			}
			
	    });
	    
	    JButton btnCancelOrderItem = new JButton("Cancel OrderItem");
		btnCancelOrderItem.setBounds(140, 80, 130, 50);
		contentPane.add(btnCancelOrderItem);
		btnCancelOrderItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				int index = Orders.getSelectedIndex();
				try {
					if(index==-1) {
						throw new InvalidInputException("Nothing was selected from the list");
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Nothing was selected from the list");
					return;
				}
				List<OrderItem> orderItems = new ArrayList<OrderItem>();
				int aTablenum = (int) tableComboBox.getSelectedItem();
				Table aTable = Table.getWithNumber(aTablenum);
				try {
					orderItems = RestoAppController.getOrderItems(aTable);
				} catch (InvalidInputException e) {
					JOptionPane.showMessageDialog(null, "Error occuried");
				}
				OrderItem gotIt = orderItems.get(index);
				
				RestoAppController.cancelOrderItem(gotIt);
				
				model.removeElementAt(index);
				
			}
		});
	}
}