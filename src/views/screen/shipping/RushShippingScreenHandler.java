package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;
import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;

public class RushShippingScreenHandler extends BaseScreenHandler implements Initializable {
	
	@FXML
	private Label screenTitle;
	
	@FXML
	private Label errorLabel;

	@FXML
	private TextField date;

	@FXML
	private TextField month;

	@FXML
	private TextField hour;

	@FXML
	private TextField minute;

	private Order order;

	public RushShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {
		HashMap messages = order.getDeliveryInfo();
		messages.put("date", date.getText());
		messages.put("month", month.getText());
		messages.put("hour", hour.getText());
		messages.put("minute",minute.getText());
		
		try {
			// process and validate delivery info
			new PlaceRushOrderController().processRushDeliveryInfo(messages);
		} catch (InvalidDeliveryInfoException e) {
			errorLabel.setText("Please enter valid shipping information");
			return;
		}
		
		Invoice invoice = getBController().createInvoice(order);
		
		BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
		InvoiceScreenHandler.setPreviousScreen(this);
		InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
		InvoiceScreenHandler.setScreenTitle("Invoice Screen");
		InvoiceScreenHandler.setBController(getBController());
		InvoiceScreenHandler.show();
	}

	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}

}
