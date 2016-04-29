package com.stoneryan.android.clientmanager;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewDebug;
import android.widget.Toast;


import java.io.File;

import database.ClientBaseHelper;
import database.ClientDbSchema;
import layout.DisplayUsernameFragment;
// TODO: Make buttons larger

public class MainActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener  {

    // Variables to open database.
    private Context mContext;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Client Manager - Home");


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        initDatabase();
    }


    // Create database and add sample data if it does not already exist.
    protected void initDatabase(){

        mContext =  getApplicationContext();
        File dbFile = mContext.getDatabasePath("clientBase.db");

        if (!dbFile.exists()) {
            mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();
            
            Customer newClient1 = new Customer();
                newClient1.setName("Ryan");
                newClient1.setAddress("123 Fake Street");
                newClient1.setPhone("727-123-4567");
                newClient1.setEmail("ryanstoneofficial@gmail.com");
                newClient1.setCard_Num("1234 5647 8456 9545");
                newClient1.setCard_CVV("123");
                newClient1.setCard_Exp("12/2016");
                newClient1.setCard_Zip("33773");
            AddCustomer(newClient1);

            Session newSession1 = new Session();
                newSession1.setCustomerId((long) 1);
                newSession1.setDate("05/01/2016");
                newSession1.setTime("6:00 PM");
            AddSession(newSession1);
            Session newSession2 = new Session();
                newSession2.setCustomerId((long) 1);
                newSession2.setDate("05/02/2016");
                newSession2.setTime("6:00 PM");
            AddSession(newSession2);

            Customer newClient2 = new Customer();
                newClient2.setName("Adrian");
                newClient2.setAddress("123 66th St.");
                newClient2.setPhone("727-555-6985");
                newClient2.setEmail("adrian@spcollege.edu");
                newClient2.setCard_Num("1234 5647 8456 9545");
                newClient2.setCard_CVV("123");
                newClient2.setCard_Exp("12/2016");
                newClient2.setCard_Zip("33773");
            AddCustomer(newClient2);

            Session newSession3 = new Session();
                newSession3.setCustomerId((long) 2);
                newSession3.setDate("05/01/2016");
                newSession3.setTime("6:00 PM");
            AddSession(newSession3);
            Session newSession4 = new Session();
                newSession4.setCustomerId((long) 2);
                newSession4.setDate("05/02/2016");
                newSession4.setTime("6:00 PM");
            AddSession(newSession4);

            Customer newClient3 = new Customer();
                newClient3.setName("Elexis");
                newClient3.setAddress("598 Gulf to Bay Blvd.");
                newClient3.setPhone("813-999-5624");
                newClient3.setEmail("elexis@spcollege.edu");
                newClient3.setCard_Num("1234 5647 8456 9545");
                newClient3.setCard_CVV("123");
                newClient3.setCard_Exp("12/2016");
                newClient3.setCard_Zip("33773");
            AddCustomer(newClient3);

            Session newSession5 = new Session();
                newSession5.setCustomerId((long) 3);
                newSession5.setDate("05/01/2016");
                newSession5.setTime("6:00 PM");
            AddSession(newSession5);
            Session newSession6 = new Session();
                newSession6.setCustomerId((long)3);
                newSession6.setDate("05/02/2016");
                newSession6.setTime("6:00 PM");
            AddSession(newSession6);
        }

    }

    // Create ContentValues class to prepare for database entry.
    private static ContentValues getContentValues(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(ClientDbSchema.CustomersTable.Cols.CUSTOMER_NAME, customer.getName().toString());
        values.put(ClientDbSchema.CustomersTable.Cols.CUSTOMER_ADDRESS, customer.getAddress().toString());
        values.put(ClientDbSchema.CustomersTable.Cols.CUSTOMER_PHONE, customer.getPhone().toString());
        values.put(ClientDbSchema.CustomersTable.Cols.CUSTOMER_EMAIL, customer.getEmail().toString());
        values.put(ClientDbSchema.CustomersTable.Cols.CARD_NUM, customer.getCard_Num().toString());
        values.put(ClientDbSchema.CustomersTable.Cols.CARD_EXP, customer.getCard_Exp().toString());
        values.put(ClientDbSchema.CustomersTable.Cols.CARD_CVV, customer.getCard_Exp().toString());
        values.put(ClientDbSchema.CustomersTable.Cols.CARD_ZIP, customer.getCard_Zip().toString());

        return values;
    }


    // Create ContentValues class to prepare for database entry for session.
    private static ContentValues getContentValues(Session session) {
        ContentValues values = new ContentValues();
        values.put(ClientDbSchema.SessionsTable.Cols.SESSION_DATE, session.getDate().toString());
        values.put(ClientDbSchema.SessionsTable.Cols.SESSION_TIME, session.getTime().toString());
        values.put(ClientDbSchema.SessionsTable.Cols.CUSTOMER_ID, session.getCustomerId().toString());


        return values;
    }

    // Adds customer to database
    public void AddCustomer(Customer client) {
        ContentValues values = getContentValues(client);
        mDatabase.insert(ClientDbSchema.CustomersTable.NAME, null, values);
    }

    // Adds session to database
    private void AddSession(Session session) {
        ContentValues values = getContentValues(session);

        mDatabase.insert(ClientDbSchema.SessionsTable.NAME, null, values);

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void CreateNewCustomerButton(View view) {
        Intent i = new Intent(MainActivity.this, CreateNewCustomerActivity.class);
        startActivity(i);
    }

    public void ViewCustomersButton(View view) {
        Intent i = new Intent(MainActivity.this, CustomerListActivity.class);
        startActivity(i);
    }


}
