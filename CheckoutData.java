import java.sql.Timestamp;


/**
 * simple utility store for the data associated with one movie that a customer has checked out.
 * @author student
 *
 */
public class CheckoutData {
	private Timestamp checkoutDate;
	private int copyId;

	public CheckoutData() {
	}

	public CheckoutData(Timestamp d, int copyId2) {
		checkoutDate = d;
		copyId = copyId2;
	}

	public Timestamp getCheckoutDate() {
		return checkoutDate;
	}

	public int getCopyId() {
		return copyId;
	}

	//not sure if I was supposed to change this, but I was unable to find another way to print getRentals() correctly in MemberPlay
	public String toString() {
		return "The copy id of this video is " + copyId + " and this video was checked out on " + checkoutDate + "\n";
	}

}