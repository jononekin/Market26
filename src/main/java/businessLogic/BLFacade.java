package businessLogic;

import java.io.File;
import java.util.List;

import domain.Product;
import exceptions.ProductAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.awt.image.BufferedImage;
import java.awt.Image;

 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	 */
   @WebMethod
	public Product createProduct(String title, String description, float price, int status, String sellerEmail, File file) throws  ProductAlreadyExistException;
	
	
	/**
	 * This method retrieves the products that contain desc
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc 
	 */
	@WebMethod public List<Product> getProducts(String desc);
	
	/**
	 * This method calls the data access to initialize the database with some sellers and products.
	 * It is only invoked  when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
		
	@WebMethod public Image downloadImage(String imageName);
	

	
}
