package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.stoneryan.android.clientmanager.Customer;
import com.stoneryan.android.clientmanager.Session;

import database.ClientDbSchema.SessionsTable;

import static java.lang.Boolean.*;

/**
 * Created by Ryan on 4/24/2016.
 */
public class SessionCursorWrapper extends CursorWrapper {
    public SessionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Session getSession() {
        Long sessionIdLong = getLong(getColumnIndex(SessionsTable.Cols.SESSION_ID));
        Long customerIdLong = getLong(getColumnIndex(SessionsTable.Cols.CUSTOMER_ID));
        String dateString = getString(getColumnIndex(SessionsTable.Cols.SESSION_DATE));
        String timeString = getString(getColumnIndex(SessionsTable.Cols.SESSION_TIME));
        String signatureAddressString = getString(getColumnIndex(SessionsTable.Cols.SIGNATURE_ADDRESS));
        String hasPaidString = getString(getColumnIndex(SessionsTable.Cols.HAS_PAID));

        Session session =  new Session();

        session.setSessionId(sessionIdLong);
        session.setCustomerId(customerIdLong);
        session.setDate(dateString);
        session.setTime(timeString);
        session.setSignatureAddress(signatureAddressString);
        session.setHasPaid(hasPaidString);

        return session;
    }
}
