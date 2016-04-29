package com.stoneryan.android.clientmanager;

import android.content.ContentValues;
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
import android.widget.EditText;

import database.ClientBaseHelper;
import database.ClientDbSchema.SessionsTable;
import database.CustomerCursorWrapper;
import layout.DisplayUsernameFragment;

public class AddSessionActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener  {
    // Variables to open database.
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private Long mSessionId;
    private Long mCustomerId;
    private EditText mDate;
    private EditText mTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);

        setTitle("Add Session");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }



    }


    @Override
    public void onResume() {
        super.onResume();


        // Get row ID for customer to add session for.
        Bundle extras = getIntent().getExtras();
        mCustomerId = extras.getLong("_ID");

        mDate = (EditText)findViewById(R.id.newDate);
        mTime = (EditText)findViewById(R.id.newTime);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // Create ContentValues class to prepare for database entry.
    private static ContentValues getContentValues(Session session) {
        ContentValues values = new ContentValues();
        values.put(SessionsTable.Cols.SESSION_DATE, session.getDate().toString());
        values.put(SessionsTable.Cols.SESSION_TIME, session.getTime().toString());
        values.put(SessionsTable.Cols.CUSTOMER_ID, session.getCustomerId().toString());


        return values;
    }

    public void updateSession(Session session){
        String timeString = session.getTime().toString();
        String dateString = session.getDate().toString();
        String customerIdString = session.getCustomerId().toString();
        ContentValues values = getContentValues(session);


        mDatabase.update(SessionsTable.NAME, values, SessionsTable.Cols.SESSION_DATE + " = ?", new String[]{dateString});
        mDatabase.update(SessionsTable.NAME, values, SessionsTable.Cols.SESSION_TIME + " = ?", new String[]{timeString});
        mDatabase.update(SessionsTable.NAME, values, SessionsTable.Cols.CUSTOMER_ID + " = ?", new String[]{customerIdString});

    }

    private Session createSession(Context context) {
        Session session = new Session();
        session.setDate(mDate.getText().toString());
        session.setTime(mTime.getText().toString());
        session.setCustomerId(mCustomerId);

        return session;
    }

    private Long addSession(Session session) {
        ContentValues values = getContentValues(session);

        mSessionId = mDatabase.insert(SessionsTable.NAME, null, values);

        return mSessionId;
    }

    private CustomerCursorWrapper querySessions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                SessionsTable.NAME,
                null, // Columns, null selects all
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null //orderBy
        );

        return new CustomerCursorWrapper(cursor);
    }

    public void CancelButton(View view) {
        Intent i = new Intent(AddSessionActivity.this, ViewCustomerActivity.class);
        startActivity(i);
    }

    public void PurchaseButton(View view) {

        mContext =  getApplicationContext();
        mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();
        Session newSession = createSession(mContext);
        mSessionId = addSession(newSession);


        Intent i = new Intent(AddSessionActivity.this, ViewReceiptActivity.class);
        i.putExtra("SESSION_ID", mSessionId);
        i.putExtra("CUSTOMER_ID", mCustomerId);
        startActivity(i);
    }
}