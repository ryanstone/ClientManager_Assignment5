package com.stoneryan.android.clientmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import database.ClientBaseHelper;
import database.ClientDbSchema;
import database.CustomerCursorWrapper;
import database.SessionCursorWrapper;
import layout.DisplayUsernameFragment;
// TODO: Add button to return to to ViewCustomerActivity
public class ViewReceiptActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener  {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private Customer mCustomer;
    private Long mCustomerId;
    private Session mSession;
    private Long mSessionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);

        setTitle("View Receipt");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        mContext = getApplicationContext();
        mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();

        // Get row ID for customer/session to be displayed.
        Bundle extras = getIntent().getExtras();
        mCustomerId = extras.getLong("CUSTOMER_ID");
        mSessionId = extras.getLong("SESSION_ID");

        mCustomer = getCustomer(mCustomerId);
        mSession = getSession(mSessionId);

        setAttributesDisplay(mCustomer, mSession);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private CustomerCursorWrapper queryCustomers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ClientDbSchema.CustomersTable.NAME,
                null, // Columns, null selects all
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null //orderBy
        );

        return new CustomerCursorWrapper(cursor);
    }

    public Customer getCustomer(Long rowId) {
        Customer myCustomer;

        String whereClause = "_id = " + rowId;
        CustomerCursorWrapper cursor = queryCustomers(whereClause, null);

        try {
            cursor.moveToFirst();
            myCustomer = cursor.getCustomer();
        } finally {
            cursor.close();
        }
        return myCustomer;
    }

    private SessionCursorWrapper querySessions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ClientDbSchema.SessionsTable.NAME,
                null, // Columns, null selects all
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null //orderBy
        );

        return new SessionCursorWrapper(cursor);
    }


    public Session getSession(Long rowId) {
        Session mySession;

        String whereClause = "_id = " + rowId;
        SessionCursorWrapper cursor = querySessions(whereClause, null);

        try {
            cursor.moveToFirst();
            mySession = cursor.getSession();
        } finally {
            cursor.close();
        }
        return mySession;
    }


    public void setAttributesDisplay(Customer client, Session session) {

        TextView nameTV = (TextView)findViewById(R.id.view_name);
        TextView dateTV = (TextView)findViewById(R.id.view_date);
        TextView timeTV = (TextView)findViewById(R.id.view_time);


        nameTV.setText(client.getName());
        dateTV.setText(session.getDate());
        timeTV.setText(session.getTime());
    }


    public String getReceiptInfo(Customer customer, Session session) {
        String myReceiptInfo = customer.printCustomer()
                + session.printSession() + "\nThank you for your business";
        return myReceiptInfo;
    }


    public void EmailButton(View view) {

        String uriText =
                "mailto:" + mCustomer.getEmail() +
                        "?subject=" + Uri.encode("Receipt for new session") +
                        "&body=" + Uri.encode(getReceiptInfo(mCustomer, mSession));
        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        startActivity(Intent.createChooser(sendIntent, "Send email"));
    }

    // Only works with Samsung print service.
    // Needs HTML source file to work.
    public void PrintButton(View view) {
        // TODO: Implement print intent
        //Intent intent = new Intent("com.sec.print.mobileprint.action.PRINT");
        //Uri uri = Uri.parse("http://www.samsung.com");

        //intent.putExtra("com.sec.print.mobileprint.extra.CONTENT", uri );
        //intent.putExtra("com.sec.print.mobileprint.extra.CONTENT_TYPE", "WEBPAGE");
        //intent.putExtra("com.sec.print.mobileprint.extra.OPTION_TYPE", "DOCUMENT_PRINT");
        //intent.putExtra("com.sec.print.mobileprint.extra.JOB_NAME", "Untitled");
        //startActivity(intent);
    }
}
