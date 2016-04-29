package com.stoneryan.android.clientmanager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.stoneryan.android.clientmanager.PicturesUtils;
import database.ClientBaseHelper;
import database.ClientDbSchema;
import database.CustomerCursorWrapper;
import layout.DisplayUsernameFragment;

import static database.ClientDbSchema.*;

public class CreateNewCustomerActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener   {
    // Variables to open database.
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private Long mRowId;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO = 2;


    private EditText mName;
    private EditText mAddress;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mCard_Num;
    private EditText mCard_Exp;
    private EditText mCard_CVV;
    private EditText mCard_Zip;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_customer);

        setTitle("Add Customer");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }


        mContext = getApplicationContext();
        mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();


        Long nextRow = (long)findNextRow();
        mPhotoButton = (ImageButton)findViewById(R.id.new_customer_camera);
        mPhotoView = (ImageView)findViewById(R.id.new_customer_photo);
        mPhotoFile = getPhotoFile(new Customer(nextRow));


        mName = (EditText)findViewById(R.id.newCustomerName);
        mAddress = (EditText)findViewById(R.id.newCustomerAddress);
        mPhone = (EditText)findViewById(R.id.newCustomerPhone);
        mEmail = (EditText)findViewById(R.id.newCustomerEmail);
        mCard_Num = (EditText)findViewById(R.id.newCardNum);
        mCard_Exp = (EditText)findViewById(R.id.newCardExp);
        mCard_CVV = (EditText)findViewById(R.id.newCardCVV);
        mCard_Zip = (EditText)findViewById(R.id.newCardZIP);


        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = (mPhotoFile != null && captureImage.resolveActivity(getPackageManager()) != null);
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        updatePhotoView();

    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
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
                    mPhotoFile.getPath(), CreateNewCustomerActivity.this);
            mPhotoView.setImageBitmap(bitmap);
        }
    }


    // Create ContentValues class to prepare for database entry for customer.
    private static ContentValues getContentValues(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CustomersTable.Cols.CUSTOMER_NAME, customer.getName().toString());
        values.put(CustomersTable.Cols.CUSTOMER_ADDRESS, customer.getAddress().toString());
        values.put(CustomersTable.Cols.CUSTOMER_PHONE, customer.getPhone().toString());
        values.put(CustomersTable.Cols.CUSTOMER_EMAIL, customer.getEmail().toString());
        values.put(CustomersTable.Cols.PICTURE_ADDRESS, customer.getPicture_Address().toString());
        values.put(CustomersTable.Cols.CARD_NUM, customer.getCard_Num().toString());
        values.put(CustomersTable.Cols.CARD_EXP, customer.getCard_Exp().toString());
        values.put(CustomersTable.Cols.CARD_CVV, customer.getCard_Exp().toString());
        values.put(CustomersTable.Cols.CARD_ZIP, customer.getCard_Zip().toString());

        return values;
    }


    public void updateCustomer(Customer client) {
        String nameString = client.getName().toString();
        String addressString = client.getAddress().toString();
        String phoneString = client.getPhone().toString();
        String emailString = client.getEmail().toString();
        String pictureAddressString = mPhotoFile.toString();
        String cardNumString = client.getCard_Num().toString();
        String cardExpString = client.getCard_Exp().toString();
        String cardCVVString = client.getCard_CVV().toString();
        String cardZipString = client.getCard_Zip().toString();
        ContentValues values = getContentValues(client);


        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CUSTOMER_NAME + " = ?", new String[]{nameString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CUSTOMER_ADDRESS + " = ?", new String[]{addressString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CUSTOMER_PHONE + " = ?", new String[]{phoneString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CUSTOMER_EMAIL + " = ?", new String[]{emailString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.PICTURE_ADDRESS + " = ?", new String[]{pictureAddressString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CARD_NUM + " = ?", new String[]{cardNumString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CARD_EXP + " = ?", new String[]{cardExpString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CARD_CVV + " = ?", new String[]{cardCVVString});
        mDatabase.update(CustomersTable.NAME, values, CustomersTable.Cols.CARD_ZIP + " = ?", new String[]{cardZipString});

    }

    private Customer CreateCustomer(Context context) {
        Customer client = new Customer();
        client.setId((long)findNextRow());
        client.setName(mName.getText().toString());
        client.setAddress(mAddress.getText().toString());
        client.setPhone(mPhone.getText().toString());
        client.setEmail(mEmail.getText().toString());
        client.setPicture_Address(mPhotoFile.toString());
        client.setCard_Num(mCard_Num.getText().toString());
        client.setCard_Exp(mCard_Exp.getText().toString());
        client.setCard_CVV(mCard_CVV.getText().toString());
        client.setCard_Zip(mCard_Zip.getText().toString());

        return client;
    }

    // Adds customer to database and returns rowId
    public Long AddCustomer(Customer client) {
        ContentValues values = getContentValues(client);

        Long rowId = mDatabase.insert(CustomersTable.NAME, null, values);

        return rowId;
    }

    private CustomerCursorWrapper queryCustomers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CustomersTable.NAME,
                null, // Columns, null selects all
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null //orderBy
        );

        return new CustomerCursorWrapper(cursor);
    }

    private int findNextRow() {
        int lastRow;
        CustomerCursorWrapper cursor = queryCustomers(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cursor.moveToNext();
            }
        } finally {
            lastRow = cursor.getCount();
            cursor.close();
        }

        return lastRow + 1;
    }


    // TODO: Delete photo if new customer is not saved.
    public void CancelButton(View view) {
        Intent i = new Intent(CreateNewCustomerActivity.this, MainActivity.class);
        startActivity(i);
    }

    // TODO: Validate input.
    public void SaveButton(View view) {



        Customer newClient = CreateCustomer(mContext);
        mRowId = AddCustomer(newClient);

        Toast.makeText(CreateNewCustomerActivity.this, R.string.save_customer_confirm,Toast.LENGTH_LONG).show();
        Intent i = new Intent(CreateNewCustomerActivity.this, ViewCustomerActivity.class);
        i.putExtra("_ID", mRowId);
        startActivity(i);
    }

}
