package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Product;
import domain.Seller;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.ProductAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();
		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
	public Product createProduct(String title, String description, float price, int status, String sellerEmail) throws  ProductAlreadyExistException {
		dbManager.open();
		Product product=dbManager.createProduct(title, description, price, status, sellerEmail);		
		dbManager.close();
		return product;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Product> getProducts(String desc){
		dbManager.open();
		List<Product>  rides=dbManager.getProducts(desc);
		dbManager.close();
		return rides;
	}

    
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

}

