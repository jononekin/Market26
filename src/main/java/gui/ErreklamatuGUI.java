package gui;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;
import java.util.ResourceBundle;

import businessLogic.BLFacade;


public class ErreklamatuGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnEnviar;
    private JTextArea txtIruzkina;
    private JSlider slider;
    private JLabel lblNewLabel;
    private JButton atxikitu;
    private File selectedFile;
    private String irudia;
    private JButton btnClose;


    public ErreklamatuGUI() {
        setBounds(100, 100, 400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        slider = new JSlider(1, 3, 1);
        slider.setBounds(169, 61, 150, 60);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);  

        contentPane.add(slider);
        contentPane.setSize(300, 100);
        contentPane.setVisible(true);

        JLabel lblIruzkina = new JLabel("BaloratuGUI.Iruzkina");
        lblIruzkina.setBounds(32, 118, 100, 20);
        contentPane.add(lblIruzkina);

        txtIruzkina = new JTextArea();
        txtIruzkina.setBounds(1, 10, 318, 45);
        contentPane.add(txtIruzkina);
        
        txtIruzkina.setLineWrap(true);
        txtIruzkina.setWrapStyleWord(true);
        txtIruzkina.setText("Bidaia ez zen helmugara iritsi. Bidai erdian istripua gertatu zen eta ez dut aukera izan bukaera arte iristeko. Lesio larriak sortu dizki gainera. Nire dirua bueltan nahi dut.");

        JScrollPane scrollPane = new JScrollPane(txtIruzkina);
        scrollPane.setBounds(32, 148, 320, 56);
        contentPane.add(scrollPane);
        
        lblNewLabel = new JLabel("AdminGUI.Larritasuna"); 
        lblNewLabel.setBounds(32, 76, 88, 13);
        contentPane.add(lblNewLabel);
        
        btnEnviar = new JButton("ErreklamatuGUI.Bidali");
        btnEnviar.setBounds(107, 214, 150, 30);
        contentPane.add(btnEnviar);
        
        atxikitu = new JButton("ErreklamatuGUI.Atxikitu");

		atxikitu.setBounds(32, 21, 117, 26);
		contentPane.add(atxikitu);
		selectedFile = null;
		
		atxikitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF", "jpg", "gif");
				fileChooser.setFileFilter(filter);
		        int result = fileChooser.showOpenDialog(null);  

		        fileChooser.setBounds(30, 148, 320, 80);


		        if (result == JFileChooser.APPROVE_OPTION) {
		            selectedFile = fileChooser.getSelectedFile();
		            irudia = selectedFile.getAbsolutePath();
		        }
			}
		});
		
		btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int puntuazioa = slider.getValue();
                String iruzkina = txtIruzkina.getText();
                BLFacade facade = MainGUI.getBusinessLogic();
                //facade.createReclam(puntuazioa, iruzkina, irudia, nork, esk);
                txtIruzkina.setText("ErreklamatuGUI.bidalita");
            }
        });
		
		btnClose = new JButton("ManageQueriesGUI.btnClose");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		btnClose.setBounds(250, 21, 96, 27);
		contentPane.add(btnClose);

 
    }
    
    private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

