package edu.tacoma.uw.ahanag22.take_a_note_on_android;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginInteractionListener, LoginFragment.OnFragmentInteractionListener{

    private SharedPreferences mSharedPreferences;
    private String URL_PART1 = "http://cssgate.insttech.washington.edu/~ahanag22/login.php?userid=";
    private String URL_PART2 = "&password=";

    public class WebloginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = params[0];
            try {
                URL urlObject = new URL(url);
                urlConnection = (HttpURLConnection) urlObject.openConnection();

                InputStream content = urlConnection.getInputStream();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

            } catch (Exception e) {
                response = "Unable to Login. Reason is: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.contains("fail")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            else if(result.contains("success")) {
                Intent i = new Intent(SignInActivity.this, WebLoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        } else {
            Intent i = new Intent(this, WebLoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void login(String userId, String pwd) {
        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();

        WebloginTask loginTask = new WebloginTask();
        String finalUrl = URL_PART1+userId+URL_PART2+pwd;
        loginTask.execute(finalUrl);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

