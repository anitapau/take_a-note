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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.NoteContent;
import edu.tacoma.uw.ahanag22.take_a_note_on_android.NoteDetailFragment.OnFragmentInteractionListener;

public class NoteTakingActivity extends AppCompatActivity implements AddNoteFragment.OnFragmentInteractionListener, AddNoteFragment.NoteAddListner, NoteFragment.OnListFragmentInteractionListener {
    private String URL_PART1 = "http://takenote.x10host.com/savingNote.php?note=";
    private String URL_PART3 = "&note=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);
        if (findViewById(R.id.fragment_container)!= null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new AddNoteFragment())
                    .commit();
        }

              this.getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .commit();
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void addNote(String url) {

        NoteSavingTask task = new NoteSavingTask();
        task.execute(new String[]{url.toString()});

        // Takes you back to the previous fragment by popping the current fragment out.


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NoteDetailFragment())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onListFragmentInteraction(NoteContent item) {
        NoteDetailFragment courseDetailFragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(NoteDetailFragment.Note_ITEM_SELECTED, item);
        courseDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, courseDetailFragment)
                .addToBackStack(null)
                .commit();
    }


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
            }
            else if(result.contains("success")) {
                Toast.makeText(getApplicationContext(), "Your note has been saved to the database", Toast.LENGTH_LONG).show();
            }
        }
    }
}
