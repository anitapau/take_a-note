package edu.tacoma.uw.ahanag22.take_a_note_on_android;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.MyProperties;
import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.NoteContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author anita paudel & ahana ghosh
 */
public class NoteDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static String NOTE_DELETE_URL
            = "http://takenote.x10host.com/delete.php?id=";;
    /**
     * email id to pass in the url
     */
    private String URL_PART2 = "&userid=";
    //note id textview
    private TextView mNoteId;
    //user  id textview
    private TextView mUserId;
    //note description
    private TextView mNoteDesc;
    //note selected
    public final static String NOTE_ITEM_SELECTED = "note_selected";

    //Note object to be created
    private NoteContent mNote;
    //Listener for the fragment interaction
    private OnFragmentInteractionListener mListener;

    /**
     * Default constructor
     */
    public NoteDetailFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteDetailFragment.
     */

    public static NoteDetailFragment newInstance(String param1, String param2) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);
        mNoteId = (TextView) view.findViewById(R.id.note_item_id);
        mNoteDesc = (TextView) view.findViewById(R.id.note_desc);
        Button share = (Button) view.findViewById(R.id.edit_note_button);
        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "anitapau@uw.edu"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "this note");
                intent.putExtra(Intent.EXTRA_TEXT,mNoteDesc.getText().toString());
                try {
                    startActivity(Intent.createChooser(intent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button delete = (Button) view.findViewById(R.id.delete_note_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteTask deleteTask = new DeleteTask();
                String userid = SignInActivity.muserId;
                String finalUrl = NOTE_DELETE_URL + mNoteId.getText().toString() + URL_PART2 + userid;
                deleteTask.execute(finalUrl);
            }
        });


        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        return view;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((NoteContent) args.getSerializable(NOTE_ITEM_SELECTED));
        }
    }

    public void updateView(NoteContent noteContent) {
        if (noteContent != null) {

            mNote = noteContent;
            mNoteId.setText(noteContent.getId().toString());
           // mUserId.setText(noteContent.getUserId());
            mNoteDesc.setText(noteContent.getNoteDesc().toString());       }
    }

    @Override
    public void onDestroyView() {

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    /**
     * Extends the asynctask to implement delete data
     */
    public class DeleteTask extends AsyncTask<String, Void, String> {
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
                response = "Unable to delete. Reason is: "
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
                Toast.makeText(getActivity(), "could not delete the data either note id or userid is wrong", Toast.LENGTH_LONG)
                        .show();
                return;
            } else if (result.contains("success")) {
                Toast.makeText(getActivity(), "Your note has been deleted", Toast.LENGTH_LONG)
                        .show();

            }
        }
    }

}
