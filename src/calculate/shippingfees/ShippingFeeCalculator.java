package calculate.shippingfees;

import entity.order.Order;

public interface ShippingFeeCalculator {
	 public int calculateShippingFee(Order order);
}
