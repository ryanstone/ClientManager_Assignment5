package database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.widget.CursorAdapter;

import com.stoneryan.android.clientmanager.Customer;

/**
 * Created by Ryan on 4/21/2016.
 */
public class CustomerCursorWrapper extends CursorWrapper {
    public CustomerCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Customer getCustomer() {
        Long idString = getLong(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CUSTOMER_ID));
        String nameString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CUSTOMER_NAME));
        String addressString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CUSTOMER_ADDRESS));
        String phoneString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CUSTOMER_PHONE));
        String emailString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CUSTOMER_EMAIL));
        String pictureAddressString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.PICTURE_ADDRESS));
        String cardNumString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CARD_NUM));
        String cardExpString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CARD_EXP));
        String cardCVVString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CARD_CVV));
        String cardZipString = getString(getColumnIndex(ClientDbSchema.CustomersTable.Cols.CARD_ZIP));

        Customer customer = new Customer();
        customer.setId(idString);
        customer.setName(nameString);
        customer.setAddress(addressString);
        customer.setPhone(phoneString);
        customer.setEmail(emailString);
        customer.setPicture_Address(pictureAddressString);
        customer.setCard_Num(cardNumString);
        customer.setCard_Exp(cardExpString);
        customer.setCard_CVV(cardCVVString);
        customer.setCard_Zip(cardZipString);
        return customer;
    }
}
