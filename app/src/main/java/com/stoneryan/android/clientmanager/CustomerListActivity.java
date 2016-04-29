package com.stoneryan.android.clientmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import database.ClientBaseHelper;
import database.ClientDbSchema;
import database.CustomerCursorWrapper;
import layout.DisplayUsernameFragment;

// TODO: Populate database with dummy data.

public class CustomerListActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener  {
    // Variables to open database.
    private Context mContext;
    private SQLiteDatabase mDatabase;
    List<Customer> mCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        mContext = getApplicationContext();
        mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();
        mCustomers = getCustomers();

        setTitle("Customer List");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ListView CustomerList = (ListView)findViewById(R.id.listViewCustomers);

        ArrayAdapter<Customer> arrayAdapter =
                new ArrayAdapter<Customer>(this,android.R.layout.simple_list_item_1, mCustomers);
        CustomerList.setAdapter(arrayAdapter);

        CustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CustomerListActivity.this, ViewCustomerActivity.class);
                i.putExtra("_ID", mCustomers.get(position).getId());
                startActivity(i);
            }
        });


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

    // TODO: Implement RecyclerView for CustomerList
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        CustomerCursorWrapper cursor = queryCustomers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                customers.add(cursor.getCustomer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return customers;
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }



    public void BackButton(View view) {
        Intent i = new Intent(CustomerListActivity.this, MainActivity.class);
        startActivity(i);
    }
}
