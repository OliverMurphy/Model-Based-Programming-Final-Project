package ca.mcgill.ecse.restoApp.view;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse.restoApp.application.RestoAppApplication;
import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.RestoApp;

public class RatingPage extends JFrame {

	private static final long serialVersionUID = 1L;

	RestoApp restoApp = RestoAppApplication.getRestoApp();

  private JPanel contentPane;
  private JTextField textField;
  private HashMap<String,OrderItem>orderItemHM;

  /**
   * Create the frame.
   */
  public RatingPage(Order aOrder) {
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 700, 250);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel lblRating= new JLabel("Ratings");
	lblRating.setBounds(197, -12, 213, 86);
	lblRating.setFont(new Font("Tahoma", Font.BOLD, 30));
	contentPane.add(lblRating);
	
	JComboBox<String> items = new JComboBox<String>();
	items.setBounds(50, 100, 200, 30);
	items.setFont(new Font("Tahoma", Font.BOLD, 15));
	contentPane.add(items);
	
	List<OrderItem> orderItems = aOrder.getOrderItems();
	orderItemHM = new HashMap<String,OrderItem>();
	for(OrderItem orderItem:orderItems) {
		String orderItemName = orderItem.getPricedMenuItem().getMenuItem().getName();
		items.addItem(orderItemName);
		orderItemHM.put(orderItemName, orderItem);
	}
	
	textField = new JTextField();
	textField.setBounds(300, 100, 50, 30);
	contentPane.add(textField);
	textField.setColumns(10);
	
	JButton btnCreateRating = new JButton("Create the rating");
	btnCreateRating.setFont(new Font("Tahoma", Font.BOLD, 18));
	btnCreateRating.setBounds(400, 100, 205, 30);
	contentPane.add(btnCreateRating);
	btnCreateRating.addActionListener(new ActionListener() {
	  public void actionPerformed(ActionEvent e) {
		  if(items.getSelectedItem()==null || Integer.parseInt(textField.getText())==0) {
			  JOptionPane.showMessageDialog(null, "Creating the rating didn't work properly. Make sure you selected an item and a rating.");
			  return;
		  }
		  String aOrderItemName = (String) items.getSelectedItem();
		  OrderItem aOrderItem = orderItemHM.get(aOrderItemName);
		  Integer aNumber = Integer.parseInt(textField.getText());
		  try {
			RestoAppController.setRating(aNumber, aOrderItem);
		  } catch (InvalidInputException e1) {
			JOptionPane.showMessageDialog(null, "Creating the rating didn't work properly.");
			return;
		  }
		  items.removeItem(aOrderItemName);
	  }
	});  
  }
}