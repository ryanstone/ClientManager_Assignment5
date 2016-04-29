package com.stoneryan.android.clientmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import layout.DisplayUsernameFragment;

public class LogoffActivity extends AppCompatActivity implements DisplayUsernameFragment.OnFragmentInteractionListener {
    // TODO: Implement LogoffActivity as dialog.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoff);

        setTitle("ClientManager - Logout");



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new DisplayUsernameFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }

    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    public void LogoutButton(View view) {
        SharedPreferences username = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = username.edit();
        editor.remove("username");
        editor.commit();

        Intent i = new Intent(LogoffActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void BackButton(View view) {
        Intent i = new Intent(LogoffActivity.this, MainActivity.class);
        startActivity(i);
    }

}