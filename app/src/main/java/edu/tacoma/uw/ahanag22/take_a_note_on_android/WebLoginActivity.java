package edu.tacoma.uw.ahanag22.take_a_note_on_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;
;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.NoteContent;

/**
 * class to implement web login activity functions
 */
public class WebLoginActivity extends AppCompatActivity implements NoteFragment.OnListFragmentInteractionListener {
   //shareAction provider to allow sharing
    private ShareActionProvider mShareActionProvider;
    /**
     * Create the instance of this activity with the instancestate
     * @param savedInstanceState instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.note_container, new NoteFragment())
                .commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), NoteTakingActivity.class);
                startActivity(i);
            }
        });


    }

    //List fragment interaction for the notecontent
    @Override
    public void onListFragmentInteraction(NoteContent item) {

        NoteDetailFragment noteDetailFragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(NoteDetailFragment.NOTE_ITEM_SELECTED, item);
        noteDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.note_container, noteDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    //allows logout functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false)
                .commit();
        Intent i = new Intent(this, new SignInActivity().getClass());
        startActivity(i);
        finish();

        return true;
    }


}
