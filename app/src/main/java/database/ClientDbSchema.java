package database;
//TODO: Remove hasPaid attribute.
public class ClientDbSchema {

    // Create table.
    public static final class CustomersTable {

        // Define table name.
        public static final String NAME = "customers";

        // Define table columns.
        public static final class Cols {
            public static final String CUSTOMER_ID = "_id";
            public static final String CUSTOMER_NAME = "name";
            public static final String CUSTOMER_ADDRESS = "address";
            public static final String CUSTOMER_PHONE = "phone";
            public static final String CUSTOMER_EMAIL = "email";
            public static final String PICTURE_ADDRESS = "picture";
            public static final String CARD_NUM = "card_num";
            public static final String CARD_EXP = "card_exp";
            public static final String CARD_CVV = "card_cvv";
            public static final String CARD_ZIP = "card_zip";
        }
    }

    // Create table.
    public static final class SessionsTable {

        // Define table name.
        public static final String NAME = "sessions";

        // Define table columns.
        public static final class Cols {
            public static final String SESSION_ID = "_id";
            public static final String CUSTOMER_ID = "customer_id";
            public static final String SESSION_DATE = "Session_date";
            public static final String SESSION_TIME = "session_time";
            // TODO: Need storage address for image location from signature implicit intent(?)
            public static final String SIGNATURE_ADDRESS = "signature";
            public static final String HAS_PAID = "has_paid";
        }
    }


}
