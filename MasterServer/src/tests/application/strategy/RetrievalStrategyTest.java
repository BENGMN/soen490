package tests.application.strategy;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import domain.user.UserType;
import junit.framework.TestCase;

public class RetrievalStrategyTest extends TestCase {
	// Data members for a Message
	protected BigInteger mid = new BigInteger("159949857935");
	protected final byte[] message = {0,1,2,3,4,5};
	protected final float speed = 10.0f;
	protected final double latitude = 20.0;
	protected final double longitude = 10.0;
	protected Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
	protected final int userRating = -1;
	protected int limit = 10;
	// Data members for a User
	protected final BigInteger uid = new BigInteger("3425635465657");
	protected final String email = "example@example.com";
	protected final String password = "password";
	protected final UserType userType = UserType.USER_NORMAL;
	protected final int version = 1;
	
	// Nothing to test since all methods are protected
	public void testRetrieve() {
		
	}
}
