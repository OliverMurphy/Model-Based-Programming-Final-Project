package ca.mcgill.ecse.restoApp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse.restoApp.application.RestoAppApplication;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

final class TableSeat { 
    public int  tableNum;    
    public int  seatIndex;    
}



public class OrderPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblTable;
	private JLabel lblSeats;
	private JLabel lblSelectedSeats;
	private JComboBox tableSelection;
	private JList listSeat;
	private JList list_selected;
	private JButton btnAdd;
	private JButton btnConfirm;
	private JButton btnClear;
	private DefaultListModel listModel;
	private DefaultListModel listModel_selected;
	
	ArrayList<TableSeat> tableSeatList = new ArrayList<TableSeat>();
	
	Table currentTable;
	
	/**
	 * Create the frame.
	 */
	public OrderPage(MenuItem menuItem) 
	{
		RestoApp r = RestoAppApplication.getRestoApp();
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 611, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblTable = new JLabel("Table");
		lblTable.setBounds(15, 55, 69, 20);
		contentPane.add(lblTable);
		
		
		textField = new JTextField(menuItem.getName());
		textField.setBounds(15, 0, 146, 26);
		textField.setEditable(false);
		contentPane.add(textField);
		textField.setColumns(10);
		
	
		lblSeats = new JLabel("Seats");
		lblSeats.setBounds(194, 55, 69, 20);
		contentPane.add(lblSeats);
		
	
		lblSelectedSeats = new JLabel("Selected Seats");
		lblSelectedSeats.setBounds(385, 55, 128, 20);
		contentPane.add(lblSelectedSeats);
		
		
		tableSelection = new JComboBox();
		tableSelection.setBounds(15, 91, 82, 20);
		
		List<Table> tables = r.getCurrentTables();
		for(Table table : tables)
		{
			tableSelection.addItem(table.getNumber());
		}
		contentPane.add(tableSelection);
		
		tableSelection.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                int num = (int) tableSelection.getSelectedItem();
                updateListSeatCtrl(num);
            }
        });   
		
		
		listModel = new DefaultListModel();
		listSeat = new JList(listModel);
		listSeat.setBounds(178, 91, 85, 214);
		contentPane.add(listSeat);
		
		listModel_selected = new DefaultListModel();
		list_selected = new JList(listModel_selected);
		list_selected.setBounds(385, 91, 85, 214);
		contentPane.add(list_selected);
		
	
		btnAdd = new JButton("Add");
		btnAdd.setBounds(173, 325, 90, 29);
		contentPane.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                //List selected = listSeat.getSelectedValuesList();
                //seatIndexSe = listSeat.getSelectedIndices()
                
                updateListselectedCtrl();
            }
        });   
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(275, 325, 90, 29);
		contentPane.add(btnClear);
		
		btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	ClearListselectedCtrl();
            }
        });   
	
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(380, 325, 90, 29);
		contentPane.add(btnConfirm);
		
		btnConfirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
            	ConfirmMethod(menuItem);
            }
        });   
	}
	
	private void updateListSeatCtrl(int tableNumber)
	{	
		listModel.clear();
		
		currentTable = Table.getWithNumber(tableNumber);
			
		List<Seat> seatsList = currentTable.getCurrentSeats();
		
		for(int i = 0; i < seatsList.size(); i++)
			{
				String name = "T: " + tableNumber + " S: " + i;
				listModel.addElement(name);
			}
		
		listSeat.setModel(listModel);

	}
	
	private void updateListselectedCtrl()
	{	
		//List selected = listSeat.getSelectedValuesList();
		int[] selectedIndicesArr = listSeat.getSelectedIndices();
		
		for(int i = 0; i < selectedIndicesArr.length; i++)
			{
				TableSeat aaa = new TableSeat();
				
				aaa.tableNum = currentTable.getNumber();
				aaa.seatIndex = selectedIndicesArr[i];
				
				tableSeatList.add(aaa);
				
				String name = "T: " + aaa.tableNum + " S: " + aaa.seatIndex;
				listModel_selected.addElement(name);
				
				
			}
		
		list_selected.setModel(listModel_selected);
	}
	
	private void ClearListselectedCtrl()
	{	
		tableSeatList.clear();
		
		listModel_selected.clear();
		list_selected.setModel(listModel_selected);
	}
	
	private void ConfirmMethod(MenuItem menuItem)
	{
		List<Seat> seats = new ArrayList<Seat>();
		
		for(int i = 0; i < tableSeatList.size(); i++)
		{
			int curSeatIndex = tableSeatList.get(i).seatIndex;
			int curTableNum = tableSeatList.get(i).tableNum;
			
			Table curTable = Table.getWithNumber(curTableNum);
			Seat curSeat = curTable.getCurrentSeat(curSeatIndex);
			
			seats.add(curSeat);
		}

		RestoAppController.orderMenuItem(menuItem, 1, seats);
		dispose();
	}
}