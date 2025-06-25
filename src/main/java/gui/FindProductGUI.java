package gui;

import businessLogic.BLFacade;
import domain.Product;

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

	private JFrame thisFrame;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Price")
	};
	private JTextField jTextFieldSearch;


	public FindProductGUI() {
		tableProducts.setEnabled(false);
		thisFrame=this;
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
				thisFrame.setVisible(false);

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
					tableModelProducts.setColumnCount(3); // another column added to allocate product object

					BLFacade facade = MainGUI.getBusinessLogic();
					List<domain.Product> products=facade.getProducts(jTextFieldSearch.getText());
					if (products.isEmpty() ) jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.NoProducts"));
					else jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("FindProductsGUI.Products"));
					for (domain.Product product:products){
						Vector<Object> row = new Vector<Object>();
						row.add(product.getTitle());
						row.add(product.getPrice());
						row.add(product); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
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
		
		tableProducts.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        System.out.println("row "+row);
	            Product p=(Product) tableModelProducts.getValueAt(row, 2);
	            System.out.println("Product "+p);
	            new ShowProductGUI(p);
	            
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 && row != -1) {
		            // so we know there is a doubleclick
		            // a row has been selected before
		            // the click was inside the JTable filled with data

		            // the row number is the visual row number
		            // when filtering or sorting it is not the model's row number
		            // this line takes care of that
		            int modelRow = table.convertRowIndexToModel(row);
		            
		            System.out.println("Product "+p);

		            // your valueChanged overridden method
		        }
		    }
		});
	}
}
