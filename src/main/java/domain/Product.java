package domain;

import java.io.*;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Product implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer productNumber;
	private String title;
	private String description;
	private int  status;
	private String category;
	private float price;
	private Date publicationDate;
	
	private Seller seller;  
	
	public Product(){
		super();
	}
	
	public Product(Integer productNumber, String title, String description, float price, int status, String category, Date pubDate, Seller seller) {
		super();
		this.productNumber = productNumber;
		this.title = title;
		this.description = description;
		this.status = status;
		this.category=category;
		this.price=price;
		this.publicationDate=pubDate;
		this.seller = seller;
	}

	

	public Product(String title, String description,  float price,int status, String category, Date pubDate, Seller seller) {
		super();

		this.title = title;
		this.description = description;
		this.status = status;
		this.category=category;
		this.price=price;
		this.publicationDate=pubDate;
		this.seller = seller;
	}
	
	/**
	 * Get the number of the product
	 * 
	 * @return the product number
	 */
	public Integer getProductNumber() {
		return productNumber;
	}

	
	/**
	 * Set a number to a product
	 * 
	 * @param product Number to be set	 */
	
	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}


	/**
	 * Get the title  of the product
	 * 
	 * @return the title
	 */

	public String getTitle() {
		return title;
	}


	/**
	 * Set the title of the product
	 * 
	 * @param title to be set
	 */	
	
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the description of the product
	 * 
	 * @return the product description
	 */

	public String getDescription() {
		return description;
	}


	/**
	 * Set the description of the product
	 * 
	 * @param description to be set
	 */	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	/**
	 * Get the status of the product
	 * 
	 * @return the product status
	 */

	
	public int getStatus() {
		return status;
	}


	/**
	 * Set the status of the product
	 * 
	 * @param status to be set
	 */	
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Get the category of the product
	 * 
	 * @return the category description
	 */

	public String getCategory() {
		return category;
	}


	/**
	 * Set the category of the product
	 * 
	 * @param category to be set
	 */	
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Get the price of the product
	 * 
	 * @return the price description
	 */

	public float getPrice() {
		return price;
	}

	/**
	 * Set the price of the product
	 * 
	 * @param price to be set
	 */	
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
	/**
	 * Get the publication date  of the product
	 * 
	 * @return the publication date  
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}
	/**
	 * Set the publication date  of the product
	 * 
	 * @param publication date to be set
	 */	
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}


	/**
	 * Get the seller of a product
	 * 
	 * @return the associated seller
	 */
	public Seller getSeller() {
		return seller;
	}

	/**
	 * Set the seller of a product
	 * 
	 * @param seller to assign to the product
	 */
	public void setSeller(Seller seller) {
		this.seller = seller;
	}


	public String toString(){
		return productNumber+";"+";"+title+";"+price;  
	}




	
}
