package com.stoneryan.android.clientmanager;



import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import database.ClientBaseHelper;
import database.ClientDbSchema;
import database.CustomerCursorWrapper;
import database.SessionCursorWrapper;
import layout.DisplayUsernameFragment;



public class ViewSessionsActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener {

    // Variables to open database.
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private RecyclerView mSessionRecyclerView;
    private SessionAdapter mAdapter;
    private ArrayList<Session> mSessionList;
    private Long mCustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);
        setTitle("View Sessions");

        Bundle extras = getIntent().getExtras();
        mCustomerId = extras.getLong("_ID");

        mContext = getApplicationContext();
        mDatabase = new ClientBaseHelper(mContext).getWritableDatabase();



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        mSessionRecyclerView = (RecyclerView) findViewById(R.id.session_recycler_view);
        mSessionRecyclerView.setLayoutManager(new LinearLayoutManager(ViewSessionsActivity.this));


        updateUI();


    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    private void updateUI() {

        mSessionList = getSessions(mCustomerId);
        mAdapter = new SessionAdapter(mSessionList);
        mSessionRecyclerView.setAdapter(mAdapter);
    }


    class SessionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private CheckBox mCompletedCheckBox;

        private Session mSession;

        public SessionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_session_date_text_view);
            mTimeTextView = (TextView) itemView.findViewById(R.id.list_item_session_time_text_view);
            mCompletedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_session_completed_check_box);
        }

        public void BindSession(Session session) {
            mSession = session;
            mDateTextView.setText(mSession.getDate());
            mTimeTextView.setText(mSession.getTime());
            mCompletedCheckBox.setChecked( mSession.getSignatureAddress()!=null );
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(ViewSessionsActivity.this, SignActivity.class);
            i.putExtra("SESSION_ID", mSession.getSessionId());
            i.putExtra("CUSTOMER_ID", mCustomerId);
            startActivity(i);
        }
    }

    class SessionAdapter extends RecyclerView.Adapter<SessionHolder> {

        private List<Session> mSessions;

        public SessionAdapter(List<Session> sessions) {
            mSessions = sessions;
        }

        @Override
        public SessionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(ViewSessionsActivity.this);
            View view = layoutInflater
                    .inflate(R.layout.list_item_session, parent, false);
            return new SessionHolder(view);
        }

        @Override
        public void onBindViewHolder(SessionHolder holder, int position) {
            Session session = mSessions.get(position);
            holder.BindSession(session);
        }

        @Override
        public int getItemCount() {
            return mSessions.size();
        }
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

    public ArrayList<Session> getSessions(Long customerId) {
        ArrayList<Session> sessions = new ArrayList<>();

        String whereClause = "customer_id = ?";
        String[] whereArgs = { mCustomerId.toString() };
        SessionCursorWrapper cursor = querySessions(whereClause, whereArgs);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                sessions.add(cursor.getSession());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return sessions;
    }

    public void BackButton(View view) {
        Intent i = new Intent(ViewSessionsActivity.this, ViewCustomerActivity.class);
        i.putExtra("_ID", mCustomerId);

        startActivity(i);
    }
}
