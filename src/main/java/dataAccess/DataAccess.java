package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import exceptions.RideMustBeLaterThanTodayException;

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
		if  (c.isDatabaseInitialized())initializeDB();
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try {

		   //Calendar today = Calendar.getInstance();
		   
		  // int month=today.get(Calendar.MONTH);
		   //int year=today.get(Calendar.YEAR);
		   //if (month==12) { month=1; year+=1;}  
	    
		   
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
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
 	 * @throws ProductAlreadyExistException if the same ride already exists for the driver
	 */
	public Product createProduct(String title, String description,  float price, int status,  String sellerEmail) throws  ProductAlreadyExistException {
		System.out.println(">> DataAccess: createProduct=> title= "+title+" driver="+sellerEmail);
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
	 * This method retrieves the products that contain a desc text from two locations on a given date 
	 * 
	 * @param desc the text to search
	 * @return collection of products
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
