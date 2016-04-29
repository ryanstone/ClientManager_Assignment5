package database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import database.ClientDbSchema.CustomersTable;
import database.ClientDbSchema.SessionsTable;

//TODO: Remove hasPaid attribute.
public class ClientBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    public static final String DATABASE_NAME = "clientBase.db";

    public ClientBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CustomersTable.NAME + "(" +
                        " _id  integer primary key autoincrement, " +
                        CustomersTable.Cols.CUSTOMER_NAME + ", " +
                        CustomersTable.Cols.CUSTOMER_ADDRESS + ", " +
                        CustomersTable.Cols.CUSTOMER_PHONE + ", " +
                        CustomersTable.Cols.CUSTOMER_EMAIL + ", " +
                        CustomersTable.Cols.PICTURE_ADDRESS + ", " +
                        CustomersTable.Cols.CARD_NUM + ", " +
                        CustomersTable.Cols.CARD_EXP + ", " +
                        CustomersTable.Cols.CARD_CVV + ", " +
                        CustomersTable.Cols.CARD_ZIP + ")"
            );
        db.execSQL("create table " + SessionsTable.NAME + "(" +
                        " _id  integer primary key autoincrement, " +
                        SessionsTable.Cols.CUSTOMER_ID + ", " +
                        SessionsTable.Cols.CUSTOMER_ID + "REFERENCES " + CustomersTable.NAME + ", " +
                        SessionsTable.Cols.SESSION_DATE + ", " +
                        SessionsTable.Cols.SESSION_TIME + ", " +
                        SessionsTable.Cols.SIGNATURE_ADDRESS + ", " +
                        SessionsTable.Cols.HAS_PAID + ")"
            );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
