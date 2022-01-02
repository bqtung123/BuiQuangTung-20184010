package calculate.shippingfees;

import java.util.Random;
import java.util.logging.Logger;

import controller.PlaceOrderController;
import entity.order.Order;

public class NewShippingFeesCalculator implements ShippingFeeCalculator {
	private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
	@Override
	public int calculateShippingFee(Order order) {
		  Random rand = new Random();
	        int fees = (int)( ( (rand.nextFloat()*30)/100 ) * order.getAmount() );
	        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
	        return fees;
	}

}
