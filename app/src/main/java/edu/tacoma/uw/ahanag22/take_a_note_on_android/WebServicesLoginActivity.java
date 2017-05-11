package edu.tacoma.uw.ahanag22.take_a_note_on_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;




public class WebServicesLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_services_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                .commit();
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
        finish();
        return true;
    }
}
