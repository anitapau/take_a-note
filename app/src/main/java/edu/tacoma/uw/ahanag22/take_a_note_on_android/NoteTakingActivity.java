package edu.tacoma.uw.ahanag22.take_a_note_on_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.NoteContent;

/**
 * Class to implement all the functionality for taking a note
 * implements fragment interaction listner for its child fragment
 *
 * @author anita paudel & ahana ghosh
 */
public class NoteTakingActivity extends AppCompatActivity implements AddNoteFragment.NoteAddListner  {//Php url to save note
    private String URL_PART1 = "http://takenote.x10host.com/savingNote.php?note=";
    //note is passed in the url
    private String URL_PART3 = "&note=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);
        if (findViewById(R.id.note_taking_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.note_taking_container, new AddNoteFragment())
                    .commit();
        }

    }
    

    /**
     * Inflate logout menu
     *
     * @param menu menu
     * @return true if set else false
     */
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


    /**
     * Add a note to the database
     *
     * @param url for creating a note
     */
    @Override
    public void addNote(String url) {

        NoteSavingTask task = new NoteSavingTask();
        task.execute(new String[]{url.toString()});

        // Takes you back to the previous fragment by popping the current fragment out.

    }


    /**
     * Class that extends AsyncTask to implement the note taking and saving functionality through the webservices
     */
    public class NoteSavingTask extends AsyncTask<String, Void, String> {
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
            } else if (result.contains("success")) {
                Toast.makeText(getApplicationContext(), "Your note has been saved to the database", Toast.LENGTH_LONG).show();
            }
        }
    }
}
