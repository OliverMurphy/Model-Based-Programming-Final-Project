package ca.mcgill.ecse.restoApp.view;


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ca.mcgill.ecse.restoApp.application.RestoAppApplication;
import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Table;
import ca.mcgill.ecse223.resto.model.RestoApp;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ReservationPage extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

RestoApp restoApp = RestoAppApplication.getRestoApp();

  private JPanel contentPane;
  private JTextField textField;
  private JTextField textField_1;
  private JTextField textField_2;
  private JTextField textField_3;
  private JTextField textField_4;
  private JTextField textField_5;
  private JTextField textField_6;
  private JTextField textField_7;
  private JTextField textField_8;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ReservationPage frame = new ReservationPage();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public ReservationPage() {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 600, 600);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    JLabel lblReservations = new JLabel("Reservations");
    lblReservations.setBounds(197, -12, 213, 86);
    lblReservations.setFont(new Font("Tahoma", Font.BOLD, 30));
    contentPane.add(lblReservations);
    
    JLabel lblCreateNew = new JLabel("Create New");
    lblCreateNew.setFont(new Font("Tahoma", Font.BOLD, 20));
    lblCreateNew.setBounds(34, 39, 137, 47);
    contentPane.add(lblCreateNew);
    
    JLabel lblDate = new JLabel("Date (dd/mm/yy):");
    lblDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblDate.setBounds(34, 79, 171, 20);
    contentPane.add(lblDate);
    
    JLabel lblTime = new JLabel("Time(hr/min):");
    lblTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblTime.setBounds(34, 115, 137, 20);
    contentPane.add(lblTime);
    
    JLabel lblNoOfPeople = new JLabel("No. of People:");
    lblNoOfPeople.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblNoOfPeople.setBounds(34, 151, 137, 20);
    contentPane.add(lblNoOfPeople);
    
    JLabel lblTable = new JLabel("Table:");
    lblTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblTable.setBounds(34, 339, 69, 20);
    contentPane.add(lblTable);
    
    JLabel lblName = new JLabel("Name:");
    lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblName.setBounds(34, 223, 69, 20);
    contentPane.add(lblName);
    
    JLabel lblEmail = new JLabel("Email:");
    lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblEmail.setBounds(34, 295, 69, 20);
    contentPane.add(lblEmail);
    
    JLabel lblPhone = new JLabel("Phone:");
    lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 18));
    lblPhone.setBounds(34, 259, 69, 20);
    contentPane.add(lblPhone);
    
    textField = new JTextField();
    textField.setBounds(209, 293, 191, 26);
    contentPane.add(textField);
    textField.setColumns(10);
    
    textField_1 = new JTextField();
    textField_1.setBounds(209, 257, 190, 26);
    contentPane.add(textField_1);
    textField_1.setColumns(10);
    
    textField_2 = new JTextField();
    textField_2.setBounds(209, 221, 190, 26);
    contentPane.add(textField_2);
    textField_2.setColumns(10);
    
    textField_3 = new JTextField();
    textField_3.setBounds(209, 149, 190, 26);
    contentPane.add(textField_3);
    textField_3.setColumns(10);
    
    textField_4 = new JTextField();
    textField_4.setBounds(209, 77, 43, 26);
    contentPane.add(textField_4);
    textField_4.setColumns(10);
    
    textField_5 = new JTextField();
    textField_5.setBounds(267, 77, 43, 26);
    contentPane.add(textField_5);
    textField_5.setColumns(10);
    
    textField_6 = new JTextField();
    textField_6.setBounds(324, 77, 43, 26);
    contentPane.add(textField_6);
    textField_6.setColumns(10);
    
    textField_7 = new JTextField();
    textField_7.setBounds(209, 113, 43, 26);
    contentPane.add(textField_7);
    textField_7.setColumns(10);
    
    textField_8 = new JTextField();
    textField_8.setBounds(267, 113, 43, 26);
    contentPane.add(textField_8);
    textField_8.setColumns(10);
    
    JComboBox<Integer> tableComboBox = new JComboBox<Integer>();
    tableComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {}));
    
    contentPane.add(tableComboBox);
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	final JList list = new JList(tableComboBox.getModel());
    
    RestoApp restoApp = RestoAppApplication.getRestoApp();
    List<Table> tables = restoApp.getCurrentTables();
    for (Table table : tables) {
      Integer tablenumber = table.getNumber();
      tableComboBox.addItem(tablenumber);
    }
    list.setBounds(34, 375, 107, 350);
    contentPane.add(list);
   
    
    JButton btnSave = new JButton("Save");
    btnSave.setFont(new Font("Tahoma", Font.BOLD, 18));
    btnSave.setBounds(252, 335, 115, 29);
    contentPane.add(btnSave);
    btnSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        @SuppressWarnings("deprecation")
		Date date = new Date (((Integer.parseInt(textField_6.getText()))-1900),(Integer.parseInt(textField_5.getText())),Integer.parseInt(textField_4.getText()));//-1
        @SuppressWarnings("deprecation")
		Time time = new Time(Integer.parseInt(textField_7.getText()),Integer.parseInt(textField_8.getText()),0);
        int noOfSeats = Integer.parseInt(textField_3.getText());
        String name = textField_2.getText();
        String email = textField.getText();
        String phone = textField_1.getText();
        
        @SuppressWarnings("unchecked")
		List<Integer> testTables = list.getSelectedValuesList();
        List<Table> myTables = new ArrayList<Table>();
         for (int i = 0; i < testTables.size(); i++) {
          myTables.add(Table.getWithNumber(testTables.get(i)));
        }
        
        Date nowDate = new Date(Calendar.getInstance().getTimeInMillis());
       
          try {
            if(name.equals("") || email.equals("") || phone.equals(""))
            {
              throw new InvalidInputException("Field cannot be empty");
            }
             
          } catch (InvalidInputException e1) {
            JOptionPane.showMessageDialog(null, "Field cannot be empty.");
            return;
          } 
          try {
            if((noOfSeats < 1) )
            {
              throw new InvalidInputException("Number of seats needs to be greater than 0");
            }
               
          } catch (InvalidInputException e2) {
            JOptionPane.showMessageDialog(null, "Number of seats needs to be greater than 0.");
            return;
          }  
            try {
              if(date.before(nowDate))
              {
                throw new InvalidInputException("Can't make a reservation in the past");
              }
                 
            } catch (InvalidInputException e3) {
              JOptionPane.showMessageDialog(null, "Can't make a reservation in the past.");
              return;
            
          }
          try {
            RestoAppController.createReservation(date,time,noOfSeats,name,email,phone,myTables);
           
            
          } catch (InvalidInputException e4) {
            // TODO Auto-generated catch block
            e4.printStackTrace();
          }
         
      }
  });
   
  }
}
