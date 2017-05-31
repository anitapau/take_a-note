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

/**
 * Implements the functions to allow users to signin
 *
 * @author anita paudel & ahana ghosh
 */
public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginInteractionListener, LoginFragment.OnFragmentInteractionListener {

    private SharedPreferences mSharedPreferences;

    //php url to login with id
    private String URL_PART1 = "http://takenote.x10host.com/login.php?user=";
    //password passed in url
    private String URL_PART2 = "&password=";
    //static variable to save userid
    public static String muserId;

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
                Toast.makeText(getApplicationContext(), "unable to login either the userid or password is wrong", Toast.LENGTH_LONG)
                        .show();
                mSharedPreferences
                        .edit()
                        .putBoolean(getString(R.string.LOGGEDIN), false)
                        .commit();

                return;

            } else if (result.contains("success")) {
                mSharedPreferences
                        .edit()
                        .putBoolean(getString(R.string.LOGGEDIN), true)
                        .commit();
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

    /**
     * Allows users to login
     *
     * @param userId userid
     * @param pwd    password to login
     */
    @Override
    public void login(String userId, String pwd) {
        SignInActivity.muserId=userId;
        WebloginTask loginTask = new WebloginTask();
        String finalUrl = URL_PART1 + userId + URL_PART2 + pwd;
        loginTask.execute(finalUrl);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}