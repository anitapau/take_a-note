package edu.tacoma.uw.ahanag22.take_a_note_on_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.EditNoteFragment;
import edu.tacoma.uw.ahanag22.take_a_note_on_android.Note.NoteContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView mNoteId;
    private TextView mNoteDesc;
    public final static String Note_ITEM_SELECTED = "note_selected";

    private NoteContent mNote;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
       // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
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
    public static final String DETAIL_PARAM = "detail_param";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_note_detail, container, false);
        mNoteId = (TextView) view.findViewById(R.id.note_item_id);
        mNoteDesc = (TextView) view.findViewById(R.id.note_desc);
        Button editNoteButton = (Button) view.findViewById(R.id.edit_note_button);
        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditNoteFragment editNoteFragment = new EditNoteFragment();
                Bundle args = new Bundle();
                args.putSerializable(NoteDetailFragment.Note_ITEM_SELECTED, mNote);
                editNoteFragment.setArguments(args);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, editNoteFragment)
                        .addToBackStack(null)
                        .commit();
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
            updateView((NoteContent) args.getSerializable(Note_ITEM_SELECTED));
        }
    }
    public void updateView(NoteContent noteContent) {
        if (noteContent != null) {

            mNote = noteContent;
            mNoteId.setText(noteContent.getId());
            mNoteDesc.setText(noteContent.getNoteDesc());
        }
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
}
