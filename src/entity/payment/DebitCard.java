package entity.payment;

public class DebitCard extends PaymentCard {
	private String issuingBank;
	private String cardHolderNme;
	private int cardBumber;
	private String validFromDate;
	
	public DebitCard(String issuingBank, String cardHolderNme, int cardBumber, String validFromDate) {
		super();
		this.issuingBank = issuingBank;
		this.cardHolderNme = cardHolderNme;
		this.cardBumber = cardBumber;
		this.validFromDate = validFromDate;
	}

}
