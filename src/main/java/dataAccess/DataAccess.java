package dataAccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
		
			
			seller1.addProduct("futbol baloia", "oso polita, gutxi erabilita", 10, 2, "kirola", today);
			seller1.addProduct("salomon mendiko botak", "44 zenbakia, 3 ateraldi",20,  2, "kirola", today);
			seller1.addProduct("samsung 42\" telebista", "berria, erabili gabe", 175, 1, "elektronika", today);


			seller2.addProduct("imac 27", "7 urte, dena ondo dabil", 1, 200,  "elektronika", today);
			seller2.addProduct("iphone 17", "oso gutxi erabilita", 2, 400, "elektronika", today);
			seller2.addProduct("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 4,225, "kirola", today);
			seller2.addProduct("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, "kirola", today);

			seller3.addProduct("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 4,45, "etxea", today);

			
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
	public Product createProduct(String title, String description,  float price, int status,  String sellerEmail) throws  ProductAlreadyExistException {
		System.out.println(">> DataAccess: createProduct=> title= "+title+" seller="+sellerEmail);
		try {
			
			db.getTransaction().begin();
			
			Seller seller = db.find(Seller.class, sellerEmail);
			if (seller.doesProductExist(title)) {
				db.getTransaction().commit();
				throw new ProductAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ProductAlreadyExist"));
			}
			Date today = UtilDate.trim(new Date());

			Product product = seller.addProduct(title, description, price, status, today);
			//next instruction can be obviated
			db.persist(seller); 
			db.getTransaction().commit();

			return product;
		} catch (NullPointerException e) {
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

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
}
