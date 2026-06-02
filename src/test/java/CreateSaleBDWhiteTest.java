import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Sale;
import domain.Seller;
import exceptions.MustBeLaterThanTodayException;
import exceptions.ParamNullException;
import exceptions.SaleAlreadyExistException;
import testOperations.TestDataAccess;

public class CreateSaleBDWhiteTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	@SuppressWarnings("unused")
	private Seller seller; 
	
	// Sale defect values 
	String title="futbol baloia";
	String description="ordubete erabilita";
	String sellerName="Jon Brown";
	String sellerMail="seller1@ehu.eus";
	int status= 2;
	float price=(float) 10.5;

/*	
	@Test
	//sut.createSale:  One of the param is null (ie. description) The test must return ParamNullException. If  an Exception is returned the createSale method is not well implemented.
		public void test1() {
		Sale sale=null;
			try {

				 //Changed value
				 description=null;
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date saleDate=null;
				try {
					saleDate = sdf.parse("05/10/2026");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				
				
				//invoke System Under Test (sut)  
				sut.open();
			    sale=sut.createSale(title, description, status, price, saleDate, sellerMail, null);

				//verify the results
				assertNull(sale);
				
				
			} catch ( ParamNullException  e) {
				// TODO Auto-generated catch block
				// if the program goes to this point fail  
				assertTrue(true);   
			} catch ( SaleAlreadyExistException e) {
				// TODO Auto-generated catch block
				// if the program goes to this point fail  
				fail();
			} catch (MustBeLaterThanTodayException e) {
				// TODO Auto-generated catch block
					fail();
			} catch (Exception e) {
				// TODO Auto-generated catch block
					fail();
					
				} finally {
					sut.close();
				}
	}

	@Test
	//sut.createSale:  The Seller(seller1@ehu.eus) does not exist in the DB. The test must return null 
	//The test "supposes" that the seller1.ehu.es does not exist in the DB
	
	public void test2() {
		
		Sale sale;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date saleDate=null;
		try {
			saleDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {	
			//invoke System Under Test (sut)  
			sut.open();
		    sale=sut.createSale(title, description, status, price, saleDate, sellerMail, null);			
			assertNull(sale);
			
		} catch (ParamNullException | SaleAlreadyExistException  | MustBeLaterThanTodayException e ) {  
			fail();   
		} catch (Exception e) {
				fail();	
		} finally {
			sut.close();
		}
	} 
	
	@Test
	//sut.createSale:  the date of the ride must be later than today. The MustBeLaterThanTodayException 
	// exception must be thrown. 
	public void test3() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date saleDate=null;;
		
		boolean driverCreated=false;

		try {
			saleDate = sdf.parse("05/10/2018");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			
			//define parameters
			testDA.open();
			if (!testDA.existSeller(sellerMail)) {
				testDA.createSeller(sellerMail, sellerName);
			    driverCreated=true;
			}
			testDA.close();		
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createSale(title, description, status, price, saleDate, sellerMail, null);			
			//sut.close();
			
			fail();
			
		   } catch (MustBeLaterThanTodayException  e) {
			 //verify the results
				assertTrue(true);
		   } catch (ParamNullException | SaleAlreadyExistException e) {
				fail();
		   } finally {
				sut.close();
			    //Remove the created objects in the database (cascade removing)   
				testDA.open();
			    if (driverCreated) 
			    	testDA.removeSeller(sellerMail);
		        testDA.close();
		   }
	} 

	@Test
	//sut.createSale:  The Seller(seller1@ehu.eus) HAS  one sale with "title" 
	// and the Exception SaleAlreadyExistException must be thrown
	//The test "supposes" that the seller1.ehu.es does not exist in the DB

	public void test4() {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date saleDate=null;;
		
		boolean driverCreated=false;

		try {
			saleDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			//Create a seller and his sale
			
			testDA.open();
			testDA.addSellerWithSale(sellerMail, sellerName, title, description, status, price, saleDate, null);
			testDA.close();
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createSale(title, description, status, price, saleDate, sellerMail, null);			
			
			//if the program goes to this point fail
			fail();
		
			
		   } catch (SaleAlreadyExistException e) {
			// if the program goes to this point fail  
				assertTrue(true);
		   } catch (ParamNullException | MustBeLaterThanTodayException e) {
				fail();
				// if the program goes to this point fail  
		   } finally {
				sut.close();
				testDA.open();

				//reestablish the state of the system (remove the driver and her rides in the database)
				testDA.removeSeller(sellerMail);
				testDA.close();	      
		   } 
	}
	*/

	@Test
	//sut.createSale:  The Seller(seller1@ehu.eus) HAS NOT one sale with "title" 
	// and the sale must be created in DB
	//The test supposes that the "Driver Test" does not exist in the DB before the test

	public void test5() {		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date saleDate=null;
		try {
			saleDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Sale sale=null;
		
		testDA.open();
		testDA.createSeller(sellerMail,sellerName);
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			sale=sut.createSale(title, description, status, price, saleDate, sellerMail, null);
			sut.close();			
			System.out.println("PAsa por aqui");
			//verify the results
			assertNotNull(sale);
			
			//sale is in DB
			testDA.open();
			boolean exist=testDA.existSale(sellerMail,title);
			assertTrue(exist);
			testDA.close();
			
			} catch (ParamNullException | SaleAlreadyExistException  | MustBeLaterThanTodayException e ) { 
			// if the program goes to this point fail  
				e.printStackTrace();
			    System.out.println("Error: " + e.getMessage());
				fail();


			} catch (Exception e) {
				fail();
			} finally {   
				testDA.open();
				testDA.removeSeller(sellerMail);
				testDA.close();
		    }
	} 
}
