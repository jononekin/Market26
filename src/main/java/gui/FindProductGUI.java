package gui;

import businessLogic.BLFacade;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class FindProductGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.Products")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.Search")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	
	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();

	private DefaultTableModel tableModelProducts;


	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Price")
	};
	private JTextField jTextFieldSearch;


	public FindProductGUI() {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.FindProducts"));
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
		
		this.getContentPane().add(jButtonClose, null);

		scrollPanelProducts.setBounds(new Rectangle(52, 137, 459, 150));

		scrollPanelProducts.setViewportView(tableProducts);
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(3); // another column added to allocate ride objects

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(170);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(30);

		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(2)); // not shown in JTable

		this.getContentPane().add(scrollPanelProducts, null);
		
		jTextFieldSearch = new JTextField();
		jTextFieldSearch.setText(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.Search")); //$NON-NLS-1$ //$NON-NLS-2$
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
					if (products.isEmpty() ) jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.NoProducts"));
					else jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.Products"));
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
				tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(2)); // not shown in JTable
		 		
		 	}
		 });
		jButtonSearch.setBounds(427, 56, 117, 29);
		getContentPane().add(jButtonSearch);

	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
