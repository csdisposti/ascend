



// I created these to ease accessing ResultSet objects.  Feel free
// to use them or not.
public class VidTable {
	public static final int MEMBER_ID_COLUMN = 1;
	public static final int FNAME = 2;
	public static final int LNAME = 3;
	public static final int STREET = 4;
	public static final int CITY = 5;
	public static final int PHONE = 6;
	public static final int START_DATE = 7;

	public static final String MEMBER_KEY = "MEMBER_ID";
	public static final String COPY_KEY = "COPY_ID";
	public static final String COPY_FK_TITLE_ID = "TITLE_ID";
	public static final String COPY_DATE = "VALID_DATE";
	public static final String COPY_STATUS = "STATUS";
	public static final String COPY_STATUS_AVAILABLE = "IN STORE";
	public static final String COPY_STATUS_CHECKED_OUT = "WITH CUSTOMER";

	public static final String RENTAL_CHECKOUT_DATE = "CHECKOUT_DATE";
	public static final String RENTAL_COPY_ID = "COPY_ID";
	public static final String RENTAL_MEMBER_ID = "MEMBER_ID";
	public static final String RENTAL_CHECKIN_DATE = "CHECKIN_DATE";

	public static final int TITLE_ID = 1;
	public static final int TITLE_NAME = 2;
	public static final int PUB_DATE = 3;
	public static final String TITLE_KEY = "TITLE_ID";
}
