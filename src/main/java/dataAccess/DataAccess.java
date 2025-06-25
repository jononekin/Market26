package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Seller;
import domain.Product;
import exceptions.ProductAlreadyExistException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
    private static final int baseSize = 160;

	private static final String basePath="src/main/resources/images/";



	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			 } else {
				 System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized()) 
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This method  initializes the database with some products and sellers.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try { 
	       
		    //Create sellers 
			Seller seller1=new Seller("seller1@gmail.com","Aitor Fernandez","Ataun");
			Seller seller2=new Seller("seller22@gmail.com","Ane Gaztañaga","Orio");
			Seller seller3=new Seller("seller3@gmail.com","Test Seller","Gernika");

			
			//Create products
			Date today = UtilDate.trim(new Date());
		
			
			seller1.addProduct("futbol baloia", "oso polita, gutxi erabilita", 10, 2, "kirola", today, null);
			seller1.addProduct("salomon mendiko botak", "44 zenbakia, 3 ateraldi",20,  2, "kirola", today, null);
			seller1.addProduct("samsung 42\" telebista", "berria, erabili gabe", 175, 1, "elektronika", today, null);


			seller2.addProduct("imac 27", "7 urte, dena ondo dabil", 1, 200,  "elektronika",today, null);
			seller2.addProduct("iphone 17", "oso gutxi erabilita", 2, 400, "elektronika", today, null);
			seller2.addProduct("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, "kirola", today, null);
			seller2.addProduct("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, "kirola", today, null);

			seller3.addProduct("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, "etxea", today, null);

			
			db.persist(seller1);
			db.persist(seller2);
			db.persist(seller3);

	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Product
 	 * @throws ProductAlreadyExistException if the same product already exists for the seller
	 */
	public Product createProduct(String title, String description,  float price, int status,  String sellerEmail, File file) throws  ProductAlreadyExistException {
		System.out.println(">> DataAccess: createProduct=> title= "+title+" seller="+sellerEmail);
		try {
			
			db.getTransaction().begin();
			
			Seller seller = db.find(Seller.class, sellerEmail);
			if (seller.doesProductExist(title)) {
				db.getTransaction().commit();
				throw new ProductAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ProductAlreadyExist"));
			}
			Date today = UtilDate.trim(new Date());

			Product product = seller.addProduct(title, description, price, status, today, file.getName());
			//next instruction can be obviated
			db.persist(seller); 
			db.getTransaction().commit();

			try {
				BufferedImage img = ImageIO.read(file);
				
				String path="src/main/resources/images/";
			    File outputfile = new File(path+file.getName());

			   ImageIO.write(img, "png", outputfile);  // ignore returned boolean
			   System.out.println("file stored "+img);
			} catch(IOException ex) {
			 //System.out.println("Write error for " + outputfile.getPath()  ": " + ex.getMessage());
			  }

			   System.out.println("hasta aqui");

			return product;
		} catch (NullPointerException e) {
			   e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Product> getProducts(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Product> res = new ArrayList<Product>();	
		TypedQuery<Product> query = db.createQuery("SELECT p FROM Product p WHERE p.title LIKE ?1",Product.class);   
		query.setParameter(1, "%"+desc+"%");
		
		List<Product> products = query.getResultList();
	 	 for (Product product:products){
		   res.add(product);
		  }
	 	return res;
	}
	
	

public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public BufferedImage getFile(String fileName) {
		File file=new File(basePath+fileName);
		BufferedImage targetImg=null;
		try {
             targetImg = rescale(ImageIO.read(file));
        } catch (IOException ex) {
            //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
		return targetImg;

	}
	
	public BufferedImage rescale(BufferedImage originalImage)
    {
		System.out.println("rescale "+originalImage);
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	
	
	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
}
