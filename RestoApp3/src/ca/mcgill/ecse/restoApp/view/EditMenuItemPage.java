package ca.mcgill.ecse.restoApp.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.MenuItem.ItemCategory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class EditMenuItemPage extends JFrame {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JPanel contentPane;
  @SuppressWarnings("unused")
private JTextField textField;
  @SuppressWarnings("unused")
private JTextField textField_1;
  private MenuItem thisItem;
 
  @SuppressWarnings("unchecked")
public EditMenuItemPage(MenuItem item) {
    thisItem = item;
    String name = item.getName();
    String price = Double.toString(item.getCurrentPricedMenuItem().getPrice());
    String category = item.getItemCategory().toString();
    
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 454, 479);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    JLabel lblEditItem = new JLabel("Edit Item");
    lblEditItem.setFont(new Font("Tahoma", Font.BOLD, 18));
    lblEditItem.setBounds(169, 16, 118, 32);
    contentPane.add(lblEditItem);
    
    JLabel lblNewName = new JLabel("New name");
    lblNewName.setBounds(65, 67, 102, 20);
    contentPane.add(lblNewName);
    
    JLabel lblNewPrice = new JLabel("New price");
    lblNewPrice.setBounds(65, 109, 102, 20);
    contentPane.add(lblNewPrice);
    
    JLabel lblNewCategory = new JLabel("New category");
    lblNewCategory.setBounds(65, 145, 102, 20);
    contentPane.add(lblNewCategory);
    
    JTextField newNameTextField = new JTextField();
    newNameTextField.setBounds(179, 64, 146, 26);
    contentPane.add(newNameTextField);
    newNameTextField.setColumns(10);
    newNameTextField.setText(name);
    
    
    JTextField newPriceTextField = new JTextField();
    newPriceTextField.setBounds(179, 106, 146, 26);
    contentPane.add(newPriceTextField);
    newPriceTextField.setColumns(10);
    newPriceTextField.setText(price);
    
    @SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
    comboBox.setBounds(179, 145, 146, 26);
    contentPane.add(comboBox);
    for (ItemCategory c : ItemCategory.values()) { 
      comboBox.addItem(c.toString());
      if(c.toString().equals(category)) {
        comboBox.setSelectedIndex(comboBox.getItemCount()-1);
      }
    }
    
    JButton btnSave = new JButton("Save");
    btnSave.setBounds(196, 206, 115, 29);
    contentPane.add(btnSave);
    btnSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e1) {
        String n = newNameTextField.getText();
        String pText = newPriceTextField.getText();
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
        String category = comboBox.getSelectedItem().toString();
        for (ItemCategory ic : ItemCategory.values()) { 
          if (ic.toString().equals(category)) {
            c = ic;
          }
        }
        RestoAppController.editMenuItem(thisItem, n, c, p);
        new UpdateMenuPage().setVisible(true);
        dispose();
        
      }
    });
  }

}