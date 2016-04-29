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
import android.widget.Toast;

import database.ClientBaseHelper;
import database.ClientDbSchema;
import database.CustomerCursorWrapper;
import layout.DisplayUsernameFragment;
//TODO: Implement signature, save storage address to sessions table.
public class SignActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener {

    // Variables to open database.
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private Long mSessionId;
    private Long mCustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        setTitle("Sign when session complete");

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


        // Get row ID for session to be signed
        Bundle extras = getIntent().getExtras();
        mSessionId = extras.getLong("SESSION_ID");
        mCustomerId = extras.getLong("CUSTOMER_ID");

    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    // Create ContentValues class to prepare for database entry.
    private static ContentValues getContentValues(Session session) {
        ContentValues values = new ContentValues();
        values.put(ClientDbSchema.SessionsTable.Cols.SIGNATURE_ADDRESS, session.getSignatureAddress().toString());

        return values;
    }


    private Session createSession(Context context) {
        Session session = new Session();
        session.setSignatureAddress("CHECKED");

        return session;
    }

    public void updateSession(Session session){
        String SESSION_ID = mSessionId.toString();
        ContentValues values = getContentValues(session);


        mDatabase.update(ClientDbSchema.SessionsTable.NAME, values,
                "_id = ?", new String[]{SESSION_ID});

    }


    public void BackButton(View view) {
        Intent i = new Intent(SignActivity.this, ViewSessionsActivity.class);
        i.putExtra("_ID", mCustomerId);
        startActivity(i);
    }

    public void SaveButton(View view) {

        mContext =  getApplicationContext();
        mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();
        Session newSession = createSession(mContext);
        updateSession(newSession);

        Toast.makeText(SignActivity.this, R.string.session_completed, Toast.LENGTH_LONG).show();
        Intent i = new Intent(SignActivity.this, ViewSessionsActivity.class);
        i.putExtra("_ID", mCustomerId);
        startActivity(i);
    }
}