package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;

public class PlaceRushOrderController extends PlaceOrderController {
	
	  private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

	  
	  /**
	     * This method takes responsibility for processing the shipping info from user
	     * @param info
	     * @throws InterruptedException
	     * @throws IOException
	     */
	    public void processRushDeliveryInfo(HashMap info) throws InterruptedException, IOException{
	        LOGGER.info("Process Delivery Info");
	        LOGGER.info(info.toString());
	        validateRushDeliveryInfo(info);
	    }
	    
	    /**
	   * The method validates the info
	   * @param info
	   * @throws InterruptedException
	   * @throws IOException
	   */
	    public void validateRushDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
	    	if(!validateDate(info.get("date"))) {
	    		throw new InvalidDeliveryInfoException();
	    	}
	    	if(!validateMonth(info.get("month"))) {
	    		throw new InvalidDeliveryInfoException();
	    	}
	    	if(!validateHour(info.get("hour"))) {
	    		throw new InvalidDeliveryInfoException();
	    	}
	    	if(!validateMinute(info.get("minute"))) {
	    		throw new InvalidDeliveryInfoException();
	    	}
	    }
	    
	    
	    public boolean validateDate(String date) {
	    	if(date == null) return false;
	    	if(date.isEmpty() || date.isBlank()) return false;
	    	try {
	    	int dateInt = Integer.parseInt(date);
	    	if(dateInt<=0||dateInt>31) return false; 
			} catch (Exception e) {
				return false;
			}
			return true;
	    	
	    	
	    }
	    
	    public boolean validateMonth(String month) {
	    	if(month == null) return false;
	    	if(month.isEmpty() || month.isBlank()) return false;
	    	try {
	    	int monthInt = Integer.parseInt(month);
	    	if(monthInt<=0||monthInt>12) return false; 
			} catch (Exception e) {
				return false;
			}
			return true;
	    	
	    }
	    
	    public boolean validateHour(String hour) {
	    	if(hour == null) return false;
	    	if(hour.isEmpty() || hour.isBlank()) return false;
	    	try {
	    	int hourInt = Integer.parseInt(hour);
	    	if(hourInt<0||hourInt>23) return false; 
			} catch (Exception e) {
				return false;
			}
			return true;
	    	
	    }
	    
	    public boolean validateMinute(String minute) {
	    	if(minute == null) return false;
	    	if(minute.isEmpty() || minute.isBlank()) return false;
	    	try {
	    	int minuteInt = Integer.parseInt(minute);
	    	if(minuteInt<0||minuteInt>59) return false; 
			} catch (Exception e) {
				return false;
			}
			return true;
	    	
	    }
}
