package domain.user;

public enum UserType {
	USER_NORMAL,
	USER_ADVERTISER;
	
	// I honestly cannot believe Java doesn't let you cast from ints to enums automatically. Wat.
	public static int convertEnum(UserType type)
	{
		switch (type) {
		case USER_NORMAL:
			return 0;
		case USER_ADVERTISER:
			return 1;
		}
		return -1;
	}
	
	public static UserType convertInt(int type)
	{
		switch (type) {
		case 0:
			return USER_NORMAL;
		case 1:
			return USER_ADVERTISER;
		}
		return USER_NORMAL;
	}
};
