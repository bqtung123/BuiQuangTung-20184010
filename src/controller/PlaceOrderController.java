package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import calculate.shippingfees.ShippingFeeCalculator;
import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
   

	private ShippingFeeCalculator shippingFeeCalculator;
	
	 public PlaceOrderController(ShippingFeeCalculator shippingFeeCalculator) {
			super();
			this.shippingFeeCalculator = shippingFeeCalculator;
		}
	 
	 public PlaceOrderController() {
		 
	 }

	 
    public ShippingFeeCalculator getShippingFeeCalculator() {
		return shippingFeeCalculator;
	}


	public void setShippingFeeCalculator(ShippingFeeCalculator shippingFeeCalculator) {
		this.shippingFeeCalculator = shippingFeeCalculator;
	}


	/**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	if(!validateAddress(info.get("address"))) {
    		throw new InvalidDeliveryInfoException();
    	}
    	if(!validateName(info.get("name"))) {
    		throw new InvalidDeliveryInfoException();
    	}
    	if(!validatePhoneNumber(info.get("phone"))) {
    		throw new InvalidDeliveryInfoException();
    	}
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	// TODO: your work
    	if (!phoneNumber.startsWith("0")) {
			return false;
		}
    	if(phoneNumber.length()!=10) return false;
    	try {
    		Integer.parseInt(phoneNumber);
		} catch (Exception e) {
			return false;
		}
    	
    	return true;
    }
    
    public boolean validateName(String name) {
    	// TODO: your work
     if(name == null) return false;
	 if(name.isEmpty() || name.isBlank()) return false;
   	 for(int i=0; i < name.length(); i++) {
   		 if(!Character.isLetter(name.charAt(i))  && name.charAt(i) != ' ') {
   			 return false;
   		 }
   	 }
   	return true;
    }
    
    public boolean validateAddress(String address) {
    	// TODO: your work
    	if(address == null) return false;
    	if(address.isEmpty() || address.isBlank()) return false;
    	 for(int i=0; i < address.length(); i++) {
    		 if(!Character.isDigit(address.charAt(i)) && !Character.isLetter(address.charAt(i))  && address.charAt(i) != ' ') {
    			 return false;
    		 }
    	 }
    	return true;
    }
    
    public boolean checkRushDeliveryStatus(Order order) {
    	return order.checkRushDeliveryStatus();
    }
    
  
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
//    public int calculateShippingFee(Order order){
//        Random rand = new Random();
//        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
//        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
//        return fees;
//    }
}
