package edu.tacoma.uw.ahanag22.take_a_note_on_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.EditNoteFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNoteFragment.NoteAddListner} interface
 * to handle interaction events.
 * Use the {@link AddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNoteFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    private final static String COURSE_ADD_URL
            = "http://takenote.x10host.com/addNote.php?";

    /**
     * Edittext of noteid
     */
    private EditText mNoteId;
    /**
     * Edittext of note description
     */
    private EditText mNoteDesc;
    /**
     * Edittext of userid
     */
    private String mUserId;
    /**
     * Note add listner to add note
     */
    private NoteAddListner mListener;

    public AddNoteFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNoteFragment.
     */

    public static AddNoteFragment newInstance(String param1, String param2) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Create the instance of this activity with the instancestate
     * @param savedInstanceState instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);

        mNoteId = (EditText) v.findViewById(R.id.add_note_id);
        mUserId = SignInActivity.muserId;
        mNoteDesc = (EditText) v.findViewById(R.id.add_note_desc);

        Button addCourseButton = (Button) v.findViewById(R.id.save_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mNoteId.getText().toString())) {
                    Toast.makeText(v.getContext(), "Enter filename"
                            , Toast.LENGTH_SHORT)
                            .show();
                    mNoteId.requestFocus();
                    return;
                }
                String url = buildNoteUrl(v);
                mListener.addNote(url);

            }

        });
        return v;
    }


    /**
     * Build the url for the note adding
     *
     * @param v view
     * @return string representation of the data
     */
    private String buildNoteUrl(View v) {

        StringBuilder sb = new StringBuilder(COURSE_ADD_URL);

        try {

            String courseId = mNoteId.getText().toString();
            sb.append("id=");
            sb.append(courseId);
            String courseShortDesc = mNoteDesc.getText().toString();
            String userId = mUserId;
            sb.append("&userid=");
            sb.append(userId);
            sb.append("&longDesc=");
            sb.append(URLEncoder.encode(courseShortDesc, "UTF-8"));
            Log.i("AddNoteFragment", sb.toString());

        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NoteAddListner) {
            mListener = (NoteAddListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CourseAddListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface NoteAddListner {
        void addNote(String url);
    }

}
