package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Product;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class FindProductGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Products")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FindProductGUI.Search")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	
	private JScrollPane scrollPaneEvents = new JScrollPane();


	private JTable tableProducts= new JTable();

	private DefaultTableModel tableModelProducts;


	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("FindProductGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("FindProductGUI.Price")
	};
	private JTextField jTextFieldSearch;


	public FindProductGUI()
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("FindProductGUI.FindProducts"));
		jLabelProducts.setBounds(52, 108, 259, 16);
		this.getContentPane().add(jLabelProducts);

		jButtonClose.setBounds(new Rectangle(220, 379, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		BLFacade facade = MainGUI.getBusinessLogic();
		
		
		this.getContentPane().add(jButtonClose, null);





		

		scrollPaneEvents.setBounds(new Rectangle(52, 137, 459, 150));

		scrollPaneEvents.setViewportView(tableProducts);
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(4); // another column added to allocate ride objects

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(30);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(30);

		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPaneEvents, null);
		
		jTextFieldSearch = new JTextField();
		jTextFieldSearch.setText(ResourceBundle.getBundle("Etiquetas").getString("FindProductGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		jTextFieldSearch.setBounds(52, 56, 357, 26);
		getContentPane().add(jTextFieldSearch);
		jTextFieldSearch.setColumns(10);
		
		 jButtonSearch.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		try {
					tableModelProducts.setDataVector(null, columnNamesProducts);
					tableModelProducts.setColumnCount(3); // another column added to allocate ride objects

					BLFacade facade = MainGUI.getBusinessLogic();
					List<domain.Product> products=facade.getProducts(jTextFieldSearch.getText());
					if (products.isEmpty() ) jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.NoProducts"));
					else jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("FindProductGUI.Rides"));
					for (domain.Product product:products){
						Vector<Object> row = new Vector<Object>();
						row.add(product.getTitle());
						row.add(product.getPrice());
						row.add(product); // product object added in order to obtain it with tableModelProducts.getValueAt(i,3)
						tableModelProducts.addRow(row);		
					}


				} catch (Exception e1) {

					e1.printStackTrace();
				}
				tableProducts.getColumnModel().getColumn(0).setPreferredWidth(170);
				tableProducts.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableProducts.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable
		 		
		 	}
		 });
		jButtonSearch.setBounds(427, 56, 117, 29);
		getContentPane().add(jButtonSearch);

	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
