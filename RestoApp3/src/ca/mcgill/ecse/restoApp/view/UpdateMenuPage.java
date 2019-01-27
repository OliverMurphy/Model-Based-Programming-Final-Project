package ca.mcgill.ecse.restoApp.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse.restoApp.application.RestoAppApplication;
import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class UpdateMenuPage extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JPanel contentPane;
  private JTextField textFieldName;
  private JTextField textFieldPrice;

 @SuppressWarnings("unchecked")
UpdateMenuPage() {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 600, 600);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    
    JComboBox<String> itemsComboBox = new JComboBox<String>();
    itemsComboBox.setBounds(37, 105, 204, 33);
    contentPane.add(itemsComboBox);
    
    List<MenuItem> completeList = new ArrayList<MenuItem>();
    RestoApp restoApp = RestoAppApplication.getRestoApp();
    ca.mcgill.ecse223.resto.model.Menu menu = restoApp.getMenu();  
    completeList = menu.getMenuItems();
    for (MenuItem item : completeList) {
      if (item.hasCurrentPricedMenuItem()) {
       String display = item.getName() + " $" + String.valueOf(item.getCurrentPricedMenuItem().getPrice());;
       itemsComboBox.addItem(display);
      }
    }
    
    @SuppressWarnings("rawtypes")
	JComboBox categoryComboBox = new JComboBox();
    categoryComboBox.setBounds(352, 337, 146, 26);
    contentPane.add(categoryComboBox);
    for (ItemCategory c : ItemCategory.values()) { 
      categoryComboBox.addItem(c.toString());
    }
    
    JLabel lblUpdateMenu = new JLabel("Update Menu");
    lblUpdateMenu.setFont(new Font("Tahoma", Font.BOLD, 30));
    lblUpdateMenu.setBounds(183, 0, 239, 62);
    contentPane.add(lblUpdateMenu);
    
    JLabel lblAllMenuItems = new JLabel("All Menu Items");
    lblAllMenuItems.setFont(new Font("Tahoma", Font.BOLD, 18));
    lblAllMenuItems.setBounds(72, 69, 150, 33);
    contentPane.add(lblAllMenuItems);
    
    JLabel lblAddNewItem = new JLabel("Add New Item");
    lblAddNewItem.setFont(new Font("Tahoma", Font.BOLD, 18));
    lblAddNewItem.setBounds(358, 225, 150, 33);
    contentPane.add(lblAddNewItem);
    
    JLabel lblName = new JLabel("Name");
    lblName.setBounds(273, 268, 69, 20);
    contentPane.add(lblName);
    
    JLabel lblPrice = new JLabel("Price");
    lblPrice.setBounds(273, 304, 69, 20);
    contentPane.add(lblPrice);
    
    JLabel lblCategory = new JLabel("Category");
    lblCategory.setBounds(273, 340, 69, 20);
    contentPane.add(lblCategory);
    
    textFieldName = new JTextField();
    textFieldName.setBounds(352, 265, 146, 26);
    contentPane.add(textFieldName);
    textFieldName.setColumns(10);
    
    textFieldPrice = new JTextField();
    textFieldPrice.setBounds(352, 301, 146, 26);
    contentPane.add(textFieldPrice);
    textFieldPrice.setColumns(10);
    
    
    JButton btnEditItem = new JButton("Edit Item");
    btnEditItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
    btnEditItem.setBounds(342, 154, 156, 29);
    contentPane.add(btnEditItem);
    btnEditItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e1) {
        @SuppressWarnings("unused")
		int index = itemsComboBox.getSelectedIndex();
        String item = itemsComboBox.getSelectedItem().toString();
        int i = item.indexOf('$')-1;
        String name = item.substring(0, i);
        
        for (MenuItem k : menu.getMenuItems()) {
          if (k.getName().equals(name)) {
            new EditMenuItemPage(k).setVisible(true);
            dispose();
          }
        } 
      }
    });
    
    
    JButton btnAddItem = new JButton("Add Item");
    btnAddItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
    btnAddItem.setBounds(352, 379, 146, 29);
    contentPane.add(btnAddItem);
    btnAddItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e1) {
        String n = textFieldName.getText();
        String pText = textFieldPrice.getText();
        try {
          if(n.equals("") || pText.equals(""))
          {
            throw new InvalidInputException("Field cannot be empty");
          }
           
        } catch (InvalidInputException e2) {
          JOptionPane.showMessageDialog(null, "Field cannot be empty.");
          return;
        } 
        
        Double p = Double.parseDouble(pText);
        try {
          if((p < 1) )
          {
            throw new InvalidInputException("Price cannot be less than $0.");
          }
             
        } catch (InvalidInputException e2) {
          JOptionPane.showMessageDialog(null, "Price cannot be less than $0.");
          return;
        }  
        
        ItemCategory c = ItemCategory.Main;
        String category = categoryComboBox.getSelectedItem().toString();
        for (ItemCategory ic : ItemCategory.values()) { 
          if (ic.toString().equals(category)) {
            c = ic;
          }
        }
        try {
          RestoAppController.addMenuItem(n, c, p);
          dispose();
          new UpdateMenuPage().setVisible(true);
        } catch (InvalidInputException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    
    
    JButton btnDeleteItem = new JButton("Delete Item");
    btnDeleteItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
    btnDeleteItem.setBounds(342, 107, 156, 29);
    contentPane.add(btnDeleteItem);
    
    JButton btnSaveChanges = new JButton("Return to app");
    btnSaveChanges.setFont(new Font("Tahoma", Font.BOLD, 16));
    btnSaveChanges.setBounds(202, 464, 156, 33);
    contentPane.add(btnSaveChanges);
    btnSaveChanges.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        dispose();
        RestoAppPage window = new RestoAppPage();
        window.frame.setVisible(true);
        new RestoAppPage().setVisible(true);
        
      }
    });
    
    
    
    
    
    btnDeleteItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("unused")
		int index = itemsComboBox.getSelectedIndex();
        String item = itemsComboBox.getSelectedItem().toString();
        int i = item.indexOf('$')-1;
        String name = item.substring(0, i);
        
        for (MenuItem k : menu.getMenuItems()) {
          if (k.getName().equals(name)) {
           RestoAppController.removeMenuItem(k);
           dispose();
           new UpdateMenuPage().setVisible(true);
          }
        }
      }
    });
    
    
    
  }
  
}
