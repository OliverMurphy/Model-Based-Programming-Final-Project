package ca.mcgill.ecse.restoApp.view;

import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse.restoApp.application.RestoAppApplication;
import ca.mcgill.ecse.restoApp.controller.InvalidInputException;
import ca.mcgill.ecse.restoApp.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

final class TableSeat1 { 
    public int  tableNum;    
    public int  seatIndex;    
}



public class BillPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblSeats;
	private JLabel lblSelectedSeats;
	private JComboBox tableSelection;
	private JList listSeat;
	private JList list_selected;
	private JButton btnAdd;
	private JButton btnIssueBill;
	private JButton btnClear;
	private JButton btnEx;
	private DefaultListModel listModel;
	private DefaultListModel listModel_selected;
	
	ArrayList<TableSeat1> tableSeatList = new ArrayList<TableSeat1>();
	
	Table currentTable;
	
	/**
	 * Create the frame.
	 */
	public BillPage() 
	{
		RestoApp r = RestoAppApplication.getRestoApp();		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
	    
		JLabel lblTable = new JLabel("Table:");
	    lblTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    lblTable.setBounds(15, 55, 69, 20);
	    contentPane.add(lblTable);		
	
		lblSeats = new JLabel("Seats");
	    lblSeats.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSeats.setBounds(124, 55, 69, 20);
		contentPane.add(lblSeats);
		
		lblSelectedSeats = new JLabel("Selected Seats");
	    lblSelectedSeats.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelectedSeats.setBounds(240, 55, 128, 20);
		contentPane.add(lblSelectedSeats);
		
		 JLabel lblReservations = new JLabel("Create Bill");
		    lblReservations.setBounds(137, -12, 213, 86);
		    lblReservations.setFont(new Font("Tahoma", Font.BOLD, 30));
		    contentPane.add(lblReservations);
			
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
		listSeat.setBounds(128, 91, 85, 214);
		contentPane.add(listSeat);
		
		listModel_selected = new DefaultListModel();
		list_selected = new JList(listModel_selected);
		list_selected.setBounds(240, 91, 85, 214);
		contentPane.add(list_selected);
		
	
		btnAdd = new JButton("Add");
		btnAdd.setBounds(23, 325, 90, 29);
		contentPane.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                updateListselectedCtrl();
            }
        });   
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(125, 325, 90, 29);
		contentPane.add(btnClear);
		
		btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	ClearListselectedCtrl();
            }
        });   
	
		btnEx = new JButton("Exit");
		btnEx.setBounds(320, 325, 90, 29);
		contentPane.add(btnEx);
		
		btnEx.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	dispose();
            }
        }); 
		
		btnIssueBill = new JButton("Issue Bill");
		btnIssueBill.setBounds(220, 325, 90, 29);
		contentPane.add(btnIssueBill);
		
		btnIssueBill.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
        		List<Seat> seating = new ArrayList<Seat>();
        		
        		for(int i = 0; i < tableSeatList.size(); i++)
        		{
        			int curSeatIndex = tableSeatList.get(i).seatIndex;
        			int curTableNum = tableSeatList.get(i).tableNum;
        			
        			Table curTable = Table.getWithNumber(curTableNum);
        			Seat curSeat = curTable.getCurrentSeat(curSeatIndex);
        			
        			seating.add(curSeat);
        		}

        		try {
					RestoAppController.issueBill(seating);
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, "Issuing a bill didn't work properly");
				}         		
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
				String name = "Tab-" + tableNumber + ", St- " + i;
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
				TableSeat1 aaa = new TableSeat1();
				
				aaa.tableNum = currentTable.getNumber();
				aaa.seatIndex = selectedIndicesArr[i];
				
				tableSeatList.add(aaa);
				
				String name = "Tab-" + aaa.tableNum + ", St- " + aaa.seatIndex;
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

}


   
  

