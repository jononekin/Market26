package gui;

import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import businessLogic.BLFacade;
import domain.Product;


public class CreateProductGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private String sellerMail;
	private JTextField fieldTitle=new JTextField();
	private JTextField fieldDescription=new JTextField();
	
	private JLabel jLabelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Title"));
	private JLabel jLabelDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Description")); 
	private JLabel jLabelProductStatus = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Status"));
	private JLabel jLabelPrice = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.Price"));
	private JTextField jTextFieldPrice = new JTextField();



	private JScrollPane scrollPaneEvents = new JScrollPane();
	
	JComboBox<String> jComboBoxStatus = new JComboBox<String>();
	DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	List<String> status;


	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.CreateProduct"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private JFrame thisFrame;

	public CreateProductGUI(String mail) {

		thisFrame=this;
		this.sellerMail=mail;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.CreateProduct"));

		jLabelTitle.setBounds(new Rectangle(6, 56, 92, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 166, 101, 20));
		jTextFieldPrice.setBounds(new Rectangle(137, 166, 60, 20));

		
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 263, 130, 30));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelMsg.setText("");
				String error=field_Errors();
				if (error!=null) 
					jLabelMsg.setText(error);
				else
					try {
						BLFacade facade = MainGUI.getBusinessLogic();
						float price = Float.parseFloat(jTextFieldPrice.getText());
						String s=(String)jComboBoxStatus.getSelectedItem();
						int numStatus=status.indexOf(s);
						facade.createProduct(fieldTitle.getText(), fieldDescription.getText(), price, numStatus, sellerMail);
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.ProductCreated"));
					
					} catch (Exception e1) {

						// TODO Auto-generated catch block
						jLabelMsg.setText(e1.getMessage());
					}
			}
		});
		jButtonClose.setBounds(new Rectangle(275, 263, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(6, 231, 320, 20));
		jLabelError.setForeground(Color.red);
		
	    status=getStatus();
		for(String s:status) statusOptions.addElement(s);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelTitle, null);
		
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(jTextFieldPrice, null);
		
		jLabelProductStatus.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelProductStatus.setBounds(6, 187, 140, 25);
		getContentPane().add(jLabelProductStatus);
		
		jLabelDescription.setBounds(6, 81, 109, 16);
		getContentPane().add(jLabelDescription);
		
		
		fieldTitle.setBounds(128, 53, 370, 26);
		getContentPane().add(fieldTitle);
		fieldTitle.setColumns(10);
		
		
		fieldDescription.setBounds(127, 81, 371, 73);
		getContentPane().add(fieldDescription);
		fieldDescription.setColumns(10);
		
		jComboBoxStatus.setModel(statusOptions);
		jComboBoxStatus.setBounds(132, 192, 114, 27);
		getContentPane().add(jComboBoxStatus);
		
	}	 

	private String field_Errors() {
		
		try {
			if ((fieldTitle.getText().length()==0) || (fieldDescription.getText().length()==0)  || (jTextFieldPrice.getText().length()==0))
				return ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.ErrorQuery");
			else {

				// trigger an exception if the introduced string is not a number
					float price = Float.parseFloat(jTextFieldPrice.getText());
					if (price <= 0) 
						return ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.PriceMustBeGreaterThan0");
					
					else 
						return null;
			}
		} catch (java.lang.NumberFormatException e1) {

			return  ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.ErrorNumber");		
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;

		}
	}
	private ArrayList<String> getStatus() {
		String lang=Locale.getDefault().toString();
		if (lang.compareTo("en")==0) 
			return new ArrayList<String>(Arrays.asList("New","Very Good","Acceptable","Very Used"));
		if (lang.compareTo("es")==0) 
			return new ArrayList<String>(Arrays.asList("Nuevo","Muy Bueno","Aceptable","Lo ha dado todo"));
		if (lang.compareTo("eus")==0) 
			return new ArrayList<String>(Arrays.asList("Berria","Oso Ona","Egokia","Oso zaharra"));
		return null;
	}
	
}
