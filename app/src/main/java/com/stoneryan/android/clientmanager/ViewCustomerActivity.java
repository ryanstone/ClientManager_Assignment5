package com.stoneryan.android.clientmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import database.ClientBaseHelper;
import database.ClientDbSchema;
import database.CustomerCursorWrapper;
import layout.DisplayUsernameFragment;

public class ViewCustomerActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener   {
    // Variables to open database.
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private Long mRowId;
    private Customer myCustomer;

    private ImageView mPhotoView;
    private File mPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);

        setTitle("View Customer");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        mContext = getApplicationContext();
        mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();

        // Get row ID for customer to be displayed.
        Bundle extras = getIntent().getExtras();
        mRowId = extras.getLong("_ID");

        myCustomer = getCustomer(mRowId);
        setAttributesDisplay(myCustomer);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
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

    // TODO: Implement masking for cardNum
    public void setAttributesDisplay(Customer client) {
        mPhotoView = (ImageView)findViewById(R.id.new_customer_photo);
        mPhotoFile = getPhotoFile(client);
        TextView nameTV = (TextView)findViewById(R.id.viewCustomerName);
        TextView addressTV = (TextView)findViewById(R.id.viewCustomerAddress);
        TextView phoneTV = (TextView)findViewById(R.id.viewCustomerPhone);
        TextView emailTV = (TextView)findViewById(R.id.viewCustomerEmail);
        TextView cardNumTV = (TextView)findViewById(R.id.viewCustomerCardNum);
        TextView cardExpTV = (TextView)findViewById(R.id.viewCustomerCardExp);
        TextView cardCVVTV = (TextView)findViewById(R.id.viewCustomerCardCVV);
        TextView cardZipTV = (TextView)findViewById(R.id.viewCustomerCardZip);

        nameTV.setText(client.getName());
        addressTV.setText(client.getAddress());
        phoneTV.setText(client.getPhone());
        emailTV.setText(client.getEmail());
        cardNumTV.setText(client.getCard_Num());
        cardExpTV.setText(client.getCard_Exp());
        cardCVVTV.setText(client.getCard_CVV());
        cardZipTV.setText(client.getCard_Zip());
        updatePhotoView();

    }

    public File getPhotoFile(Customer client) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, client.getPicture_Address());
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PicturesUtils.getScaledBitmap(
                    mPhotoFile.getPath(), ViewCustomerActivity.this);
            mPhotoView.setImageBitmap(bitmap);
        }
    }
    // TODO: Make button to update Customer Table rows.
    // TODO: Disable ViewSessionsButton if no sessions have been added.
    public void ViewSessionsButton(View view) {
        Intent i = new Intent(ViewCustomerActivity.this, ViewSessionsActivity.class);
        i.putExtra("_ID", mRowId);
        startActivity(i);
    }

    public void AddSessionButton(View view) {
        Intent i = new Intent(ViewCustomerActivity.this, AddSessionActivity.class);
        i.putExtra("_ID", mRowId);
        startActivity(i);
    }

}
