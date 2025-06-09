package exceptions;
public class ProductAlreadyExistException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public ProductAlreadyExistException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public ProductAlreadyExistException(String s)
  {
    super(s);
  }
}